/*
 * Copyright (c) 2013, Daniel Murphy
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jbox2d.collision.broadphase;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.callbacks.TreeCallback;
import org.jbox2d.callbacks.TreeRayCastCallback;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.RayCastInput;
import org.jbox2d.common.BufferUtils;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Settings;
import org.jbox2d.common.Vec2;

/**
 * @author Daniel Murphy
 */
public class DynamicTreeFlatNodes implements BroadPhaseStrategy
{
    public static final int MAX_STACK_SIZE = 64;

    public static final int NULL_NODE = -1;

    public static final int INITIAL_BUFFER_LENGTH = 16;

    public int root;

    public AABB[] memberAabb;

    public Object[] userData;

    protected int[] parent;

    protected int[] child1;

    protected int[] child2;

    protected int[] height;

    private int nodeCount;

    private int nodeCapacity;

    private int freeList;

    private final Vec2[] drawVecs = new Vec2[4];

    public DynamicTreeFlatNodes()
    {
        root = NULL_NODE;
        nodeCount = 0;
        nodeCapacity = 16;
        expandBuffers(0, nodeCapacity);
        for (int i = 0; i < drawVecs.length; i++)
        {
            drawVecs[i] = new Vec2();
        }
    }

    private void expandBuffers(int oldSize, int newSize)
    {
        memberAabb = BufferUtils
            .reallocateBuffer(AABB.class, memberAabb, oldSize, newSize);
        userData = BufferUtils
            .reallocateBuffer(Object.class, userData, oldSize, newSize);
        parent = BufferUtils.reallocateBuffer(parent, oldSize, newSize);
        child1 = BufferUtils.reallocateBuffer(child1, oldSize, newSize);
        child2 = BufferUtils.reallocateBuffer(child2, oldSize, newSize);
        height = BufferUtils.reallocateBuffer(height, oldSize, newSize);
        // Build a linked list for the free list.
        for (int i = oldSize; i < newSize; i++)
        {
            memberAabb[i] = new AABB();
            parent[i] = (i == newSize - 1) ? NULL_NODE : i + 1;
            height[i] = -1;
            child1[i] = -1;
            child2[i] = -1;
        }
        freeList = oldSize;
    }

    @Override
    public final int createProxy(final AABB aabb, Object userData)
    {
        final int node = allocateNode();
        // Fatten the aabb
        final AABB nodeAABB = memberAabb[node];
        nodeAABB.lowerBound.x = aabb.lowerBound.x - Settings.aabbExtension;
        nodeAABB.lowerBound.y = aabb.lowerBound.y - Settings.aabbExtension;
        nodeAABB.upperBound.x = aabb.upperBound.x + Settings.aabbExtension;
        nodeAABB.upperBound.y = aabb.upperBound.y + Settings.aabbExtension;
        this.userData[node] = userData;
        insertLeaf(node);
        return node;
    }

    @Override
    public final void destroyProxy(int proxyId)
    {
        assert (0 <= proxyId && proxyId < nodeCapacity);
        assert (child1[proxyId] == NULL_NODE);
        removeLeaf(proxyId);
        freeNode(proxyId);
    }

    @Override
    public final boolean moveProxy(int proxyId, final AABB aabb,
            Vec2 displacement)
    {
        assert (0 <= proxyId && proxyId < nodeCapacity);
        assert (child1[proxyId] == NULL_NODE);
        final AABB nodeAABB = memberAabb[proxyId];
        // if (nodeAABB.contains(aabb)) {
        if (nodeAABB.lowerBound.x <= aabb.lowerBound.x
                && nodeAABB.lowerBound.y <= aabb.lowerBound.y
                && aabb.upperBound.x <= nodeAABB.upperBound.x
                && aabb.upperBound.y <= nodeAABB.upperBound.y)
        {
            return false;
        }
        removeLeaf(proxyId);
        // Extend AABB
        final Vec2 lowerBound = nodeAABB.lowerBound;
        final Vec2 upperBound = nodeAABB.upperBound;
        lowerBound.x = aabb.lowerBound.x - Settings.aabbExtension;
        lowerBound.y = aabb.lowerBound.y - Settings.aabbExtension;
        upperBound.x = aabb.upperBound.x + Settings.aabbExtension;
        upperBound.y = aabb.upperBound.y + Settings.aabbExtension;
        // Predict AABB displacement.
        final float dx = displacement.x * Settings.aabbMultiplier;
        final float dy = displacement.y * Settings.aabbMultiplier;
        if (dx < 0.0f)
        {
            lowerBound.x += dx;
        }
        else
        {
            upperBound.x += dx;
        }
        if (dy < 0.0f)
        {
            lowerBound.y += dy;
        }
        else
        {
            upperBound.y += dy;
        }
        insertLeaf(proxyId);
        return true;
    }

    @Override
    public final Object getUserData(int proxyId)
    {
        assert (0 <= proxyId && proxyId < nodeCount);
        return userData[proxyId];
    }

    @Override
    public final AABB getFatAABB(int proxyId)
    {
        assert (0 <= proxyId && proxyId < nodeCount);
        return memberAabb[proxyId];
    }

    private int[] nodeStack = new int[20];

    private int nodeStackIndex;

    @Override
    public final void query(TreeCallback callback, AABB aabb)
    {
        nodeStackIndex = 0;
        nodeStack[nodeStackIndex++] = root;
        while (nodeStackIndex > 0)
        {
            int node = nodeStack[--nodeStackIndex];
            if (node == NULL_NODE)
            {
                continue;
            }
            if (AABB.testOverlap(memberAabb[node], aabb))
            {
                int child1 = this.child1[node];
                if (child1 == NULL_NODE)
                {
                    boolean proceed = callback.treeCallback(node);
                    if (!proceed)
                    {
                        return;
                    }
                }
                else
                {
                    if (nodeStack.length - nodeStackIndex - 2 <= 0)
                    {
                        nodeStack = BufferUtils.reallocateBuffer(nodeStack,
                            nodeStack.length,
                            nodeStack.length * 2);
                    }
                    nodeStack[nodeStackIndex++] = child1;
                    nodeStack[nodeStackIndex++] = child2[node];
                }
            }
        }
    }

    private final Vec2 r = new Vec2();

    private final AABB aabb = new AABB();

    private final RayCastInput subInput = new RayCastInput();

    @Override
    public void raycast(TreeRayCastCallback callback, RayCastInput input)
    {
        final Vec2 p1 = input.p1;
        final Vec2 p2 = input.p2;
        float p1x = p1.x, p2x = p2.x, p1y = p1.y, p2y = p2.y;
        float vx, vy;
        float rx, ry;
        float absVx, absVy;
        float cx, cy;
        float hx, hy;
        float tempX, tempY;
        r.x = p2x - p1x;
        r.y = p2y - p1y;
        assert ((r.x * r.x + r.y * r.y) > 0f);
        r.normalize();
        rx = r.x;
        ry = r.y;
        // v is perpendicular to the segment.
        vx = -1f * ry;
        vy = rx;
        absVx = MathUtils.abs(vx);
        absVy = MathUtils.abs(vy);
        // Separating axis for segment (Gino, p80).
        // |dot(v, p1 - c)| > dot(|v|, h)
        float maxFraction = input.maxFraction;
        // Build a bounding box for the segment.
        final AABB segAABB = aabb;
        // Vec2 t = p1 + maxFraction * (p2 - p1);
        // before inline
        // temp.set(p2).subLocal(p1).mulLocal(maxFraction).addLocal(p1);
        // Vec2.minToOut(p1, temp, segAABB.lowerBound);
        // Vec2.maxToOut(p1, temp, segAABB.upperBound);
        tempX = (p2x - p1x) * maxFraction + p1x;
        tempY = (p2y - p1y) * maxFraction + p1y;
        segAABB.lowerBound.x = Math.min(p1x, tempX);
        segAABB.lowerBound.y = Math.min(p1y, tempY);
        segAABB.upperBound.x = Math.max(p1x, tempX);
        segAABB.upperBound.y = Math.max(p1y, tempY);
        // end inline
        nodeStackIndex = 0;
        nodeStack[nodeStackIndex++] = root;
        while (nodeStackIndex > 0)
        {
            int node = nodeStack[--nodeStackIndex] = root;
            if (node == NULL_NODE)
            {
                continue;
            }
            final AABB nodeAABB = memberAabb[node];
            if (!AABB.testOverlap(nodeAABB, segAABB))
            {
                continue;
            }
            // Separating axis for segment (Gino, p80).
            // |dot(v, p1 - c)| > dot(|v|, h)
            // node.aabb.getCenterToOut(c);
            // node.aabb.getExtentsToOut(h);
            cx = (nodeAABB.lowerBound.x + nodeAABB.upperBound.x) * .5f;
            cy = (nodeAABB.lowerBound.y + nodeAABB.upperBound.y) * .5f;
            hx = (nodeAABB.upperBound.x - nodeAABB.lowerBound.x) * .5f;
            hy = (nodeAABB.upperBound.y - nodeAABB.lowerBound.y) * .5f;
            tempX = p1x - cx;
            tempY = p1y - cy;
            float separation = MathUtils.abs(vx * tempX + vy * tempY)
                    - (absVx * hx + absVy * hy);
            if (separation > 0.0f)
            {
                continue;
            }
            int child1 = this.child1[node];
            if (child1 == NULL_NODE)
            {
                subInput.p1.x = p1x;
                subInput.p1.y = p1y;
                subInput.p2.x = p2x;
                subInput.p2.y = p2y;
                subInput.maxFraction = maxFraction;
                float value = callback.raycastCallback(subInput, node);
                if (value == 0.0f)
                {
                    // The client has terminated the ray cast.
                    return;
                }
                if (value > 0.0f)
                {
                    // Update segment bounding box.
                    maxFraction = value;
                    // temp.set(p2).subLocal(p1).mulLocal(maxFraction).addLocal(p1);
                    // Vec2.minToOut(p1, temp, segAABB.lowerBound);
                    // Vec2.maxToOut(p1, temp, segAABB.upperBound);
                    tempX = (p2x - p1x) * maxFraction + p1x;
                    tempY = (p2y - p1y) * maxFraction + p1y;
                    segAABB.lowerBound.x = Math.min(p1x, tempX);
                    segAABB.lowerBound.y = Math.min(p1y, tempY);
                    segAABB.upperBound.x = Math.max(p1x, tempX);
                    segAABB.upperBound.y = Math.max(p1y, tempY);
                }
            }
            else
            {
                nodeStack[nodeStackIndex++] = child1;
                nodeStack[nodeStackIndex++] = child2[node];
            }
        }
    }

    @Override
    public final int computeHeight()
    {
        return computeHeight(root);
    }

    private int computeHeight(int node)
    {
        assert (0 <= node && node < nodeCapacity);
        if (child1[node] == NULL_NODE)
        {
            return 0;
        }
        int height1 = computeHeight(child1[node]);
        int height2 = computeHeight(child2[node]);
        return 1 + MathUtils.max(height1, height2);
    }

    /**
     * Validate this tree. For testing.
     */
    public void validate()
    {
        validateStructure(root);
        validateMetrics(root);
        int freeCount = 0;
        int freeNode = freeList;
        while (freeNode != NULL_NODE)
        {
            assert (0 <= freeNode && freeNode < nodeCapacity);
            freeNode = parent[freeNode];
            ++freeCount;
        }
        assert (getHeight() == computeHeight());
        assert (nodeCount + freeCount == nodeCapacity);
    }

    @Override
    public int getHeight()
    {
        if (root == NULL_NODE)
        {
            return 0;
        }
        return height[root];
    }

    @Override
    public int getMaxBalance()
    {
        int maxBalance = 0;
        for (int i = 0; i < nodeCapacity; ++i)
        {
            if (height[i] <= 1)
            {
                continue;
            }
            assert (child1[i] != NULL_NODE);
            int child1 = this.child1[i];
            int child2 = this.child2[i];
            int balance = MathUtils.abs(height[child2] - height[child1]);
            maxBalance = MathUtils.max(maxBalance, balance);
        }
        return maxBalance;
    }

    @Override
    public float getAreaRatio()
    {
        if (root == NULL_NODE)
        {
            return 0.0f;
        }
        final int root = this.root;
        float rootArea = memberAabb[root].getPerimeter();
        float totalArea = 0.0f;
        for (int i = 0; i < nodeCapacity; ++i)
        {
            if (height[i] < 0)
            {
                // Free node in pool
                continue;
            }
            totalArea += memberAabb[i].getPerimeter();
        }
        return totalArea / rootArea;
    }
    // /**
    // * Build an optimal tree. Very expensive. For testing.
    // */
    // public void rebuildBottomUp() {
    // int[] nodes = new int[nodeCount];
    // int count = 0;
    //
    // // Build array of leaves. Free the rest.
    // for (int i = 0; i < nodeCapacity; ++i) {
    // if (nodes[i].height < 0) {
    // // free node in pool
    // continue;
    // }
    //
    // DynamicTreeNode node = nodes[i];
    // if (node.isLeaf()) {
    // node.parent = null;
    // nodes[count] = i;
    // ++count;
    // } else {
    // freeNode(node);
    // }
    // }
    //
    // AABB b = new AABB();
    // while (count > 1) {
    // float minCost = Float.MAX_VALUE;
    // int iMin = -1, jMin = -1;
    // for (int i = 0; i < count; ++i) {
    // AABB aabbi = nodes[nodes[i]].aabb;
    //
    // for (int j = i + 1; j < count; ++j) {
    // AABB aabbj = nodes[nodes[j]].aabb;
    // b.combine(aabbi, aabbj);
    // float cost = b.getPerimeter();
    // if (cost < minCost) {
    // iMin = i;
    // jMin = j;
    // minCost = cost;
    // }
    // }
    // }
    //
    // int index1 = nodes[iMin];
    // int index2 = nodes[jMin];
    // DynamicTreeNode child1 = nodes[index1];
    // DynamicTreeNode child2 = nodes[index2];
    //
    // DynamicTreeNode parent = allocateNode();
    // parent.child1 = child1;
    // parent.child2 = child2;
    // parent.height = 1 + MathUtils.max(child1.height, child2.height);
    // parent.aabb.combine(child1.aabb, child2.aabb);
    // parent.parent = null;
    //
    // child1.parent = parent;
    // child2.parent = parent;
    //
    // nodes[jMin] = nodes[count - 1];
    // nodes[iMin] = parent.id;
    // --count;
    // }
    //
    // root = nodes[nodes[0]];
    //
    // validate();
    // }

    private int allocateNode()
    {
        if (freeList == NULL_NODE)
        {
            assert (nodeCount == nodeCapacity);
            nodeCapacity *= 2;
            expandBuffers(nodeCount, nodeCapacity);
        }
        assert (freeList != NULL_NODE);
        int node = freeList;
        freeList = parent[node];
        parent[node] = NULL_NODE;
        child1[node] = NULL_NODE;
        height[node] = 0;
        ++nodeCount;
        return node;
    }

    /**
     * returns a node to the pool
     */
    private void freeNode(int node)
    {
        assert (node != NULL_NODE);
        assert (0 < nodeCount);
        parent[node] = freeList != NULL_NODE ? freeList : NULL_NODE;
        height[node] = -1;
        freeList = node;
        nodeCount--;
    }

    private final AABB combinedAABB = new AABB();

    private void insertLeaf(int leaf)
    {
        if (root == NULL_NODE)
        {
            root = leaf;
            parent[root] = NULL_NODE;
            return;
        }
        // find the best sibling
        AABB leafAABB = memberAabb[leaf];
        int index = root;
        while (child1[index] != NULL_NODE)
        {
            final int node = index;
            int child1 = this.child1[node];
            int child2 = this.child2[node];
            final AABB nodeAABB = memberAabb[node];
            float area = nodeAABB.getPerimeter();
            combinedAABB.combine(nodeAABB, leafAABB);
            float combinedArea = combinedAABB.getPerimeter();
            // Cost of creating a new parent for this node and the new leaf
            float cost = 2.0f * combinedArea;
            // Minimum cost of pushing the leaf further down the tree
            float inheritanceCost = 2.0f * (combinedArea - area);
            // Cost of descending into child1
            float cost1;
            AABB child1AABB = memberAabb[child1];
            if (this.child1[child1] == NULL_NODE)
            {
                combinedAABB.combine(leafAABB, child1AABB);
                cost1 = combinedAABB.getPerimeter() + inheritanceCost;
            }
            else
            {
                combinedAABB.combine(leafAABB, child1AABB);
                float oldArea = child1AABB.getPerimeter();
                float newArea = combinedAABB.getPerimeter();
                cost1 = (newArea - oldArea) + inheritanceCost;
            }
            // Cost of descending into child2
            float cost2;
            AABB child2AABB = memberAabb[child2];
            if (this.child1[child2] == NULL_NODE)
            {
                combinedAABB.combine(leafAABB, child2AABB);
                cost2 = combinedAABB.getPerimeter() + inheritanceCost;
            }
            else
            {
                combinedAABB.combine(leafAABB, child2AABB);
                float oldArea = child2AABB.getPerimeter();
                float newArea = combinedAABB.getPerimeter();
                cost2 = newArea - oldArea + inheritanceCost;
            }
            // Descend according to the minimum cost.
            if (cost < cost1 && cost < cost2)
            {
                break;
            }
            // Descend
            if (cost1 < cost2)
            {
                index = child1;
            }
            else
            {
                index = child2;
            }
        }
        int sibling = index;
        int oldParent = parent[sibling];
        final int newParent = allocateNode();
        parent[newParent] = oldParent;
        userData[newParent] = null;
        memberAabb[newParent].combine(leafAABB, memberAabb[sibling]);
        height[newParent] = height[sibling] + 1;
        if (oldParent != NULL_NODE)
        {
            // The sibling was not the root.
            if (child1[oldParent] == sibling)
            {
                child1[oldParent] = newParent;
            }
            else
            {
                child2[oldParent] = newParent;
            }
            child1[newParent] = sibling;
            child2[newParent] = leaf;
            parent[sibling] = newParent;
            parent[leaf] = newParent;
        }
        else
        {
            // The sibling was the root.
            child1[newParent] = sibling;
            child2[newParent] = leaf;
            parent[sibling] = newParent;
            parent[leaf] = newParent;
            root = newParent;
        }
        // Walk back up the tree fixing heights and AABBs
        index = parent[leaf];
        while (index != NULL_NODE)
        {
            index = balance(index);
            int child1 = this.child1[index];
            int child2 = this.child2[index];
            assert (child1 != NULL_NODE);
            assert (child2 != NULL_NODE);
            height[index] = 1 + MathUtils.max(height[child1], height[child2]);
            memberAabb[index].combine(memberAabb[child1], memberAabb[child2]);
            index = parent[index];
        }
        // validate();
    }

    private void removeLeaf(int leaf)
    {
        if (leaf == root)
        {
            root = NULL_NODE;
            return;
        }
        int parent = this.parent[leaf];
        int grandParent = this.parent[parent];
        int parentChild1 = child1[parent];
        int parentChild2 = child2[parent];
        int sibling;
        if (parentChild1 == leaf)
        {
            sibling = parentChild2;
        }
        else
        {
            sibling = parentChild1;
        }
        if (grandParent != NULL_NODE)
        {
            // Destroy parent and connect sibling to grandParent.
            if (child1[grandParent] == parent)
            {
                child1[grandParent] = sibling;
            }
            else
            {
                child2[grandParent] = sibling;
            }
            this.parent[sibling] = grandParent;
            freeNode(parent);
            // Adjust ancestor bounds.
            int index = grandParent;
            while (index != NULL_NODE)
            {
                index = balance(index);
                int child1 = this.child1[index];
                int child2 = this.child2[index];
                memberAabb[index].combine(memberAabb[child1],
                    memberAabb[child2]);
                height[index] = 1
                        + MathUtils.max(height[child1], height[child2]);
                index = this.parent[index];
            }
        }
        else
        {
            root = sibling;
            this.parent[sibling] = NULL_NODE;
            freeNode(parent);
        }
        // validate();
    }

    // Perform a left or right rotation if node A is imbalanced.
    // Returns the new root index.
    private int balance(int iA)
    {
        assert (iA != NULL_NODE);
        if (child1[iA] == NULL_NODE || height[iA] < 2)
        {
            return iA;
        }
        int iB = child1[iA];
        int iC = child2[iA];
        assert (0 <= iB && iB < nodeCapacity);
        assert (0 <= iC && iC < nodeCapacity);
        int balance = height[iC] - height[iB];
        // Rotate C up
        if (balance > 1)
        {
            int iF = child1[iC];
            int iG = child2[iC];
            // assert (F != null);
            // assert (G != null);
            assert (0 <= iF && iF < nodeCapacity);
            assert (0 <= iG && iG < nodeCapacity);
            // Swap A and C
            child1[iC] = iA;
            int cParent = parent[iC] = parent[iA];
            parent[iA] = iC;
            // A's old parent should point to C
            if (cParent != NULL_NODE)
            {
                if (child1[cParent] == iA)
                {
                    child1[cParent] = iC;
                }
                else
                {
                    assert (child2[cParent] == iA);
                    child2[cParent] = iC;
                }
            }
            else
            {
                root = iC;
            }
            // Rotate
            if (height[iF] > height[iG])
            {
                child2[iC] = iF;
                child2[iA] = iG;
                parent[iG] = iA;
                memberAabb[iA].combine(memberAabb[iB], memberAabb[iG]);
                memberAabb[iC].combine(memberAabb[iA], memberAabb[iF]);
                height[iA] = 1 + MathUtils.max(height[iB], height[iG]);
                height[iC] = 1 + MathUtils.max(height[iA], height[iF]);
            }
            else
            {
                child2[iC] = iG;
                child2[iA] = iF;
                parent[iF] = iA;
                memberAabb[iA].combine(memberAabb[iB], memberAabb[iF]);
                memberAabb[iC].combine(memberAabb[iA], memberAabb[iG]);
                height[iA] = 1 + MathUtils.max(height[iB], height[iF]);
                height[iC] = 1 + MathUtils.max(height[iA], height[iG]);
            }
            return iC;
        }
        // Rotate B up
        if (balance < -1)
        {
            int iD = child1[iB];
            int iE = child2[iB];
            assert (0 <= iD && iD < nodeCapacity);
            assert (0 <= iE && iE < nodeCapacity);
            // Swap A and B
            child1[iB] = iA;
            int bParent = parent[iB] = parent[iA];
            parent[iA] = iB;
            // A's old parent should point to B
            if (bParent != NULL_NODE)
            {
                if (child1[bParent] == iA)
                {
                    child1[bParent] = iB;
                }
                else
                {
                    assert (child2[bParent] == iA);
                    child2[bParent] = iB;
                }
            }
            else
            {
                root = iB;
            }
            // Rotate
            if (height[iD] > height[iE])
            {
                child2[iB] = iD;
                child1[iA] = iE;
                parent[iE] = iA;
                memberAabb[iA].combine(memberAabb[iC], memberAabb[iE]);
                memberAabb[iB].combine(memberAabb[iA], memberAabb[iD]);
                height[iA] = 1 + MathUtils.max(height[iC], height[iE]);
                height[iB] = 1 + MathUtils.max(height[iA], height[iD]);
            }
            else
            {
                child2[iB] = iE;
                child1[iA] = iD;
                parent[iD] = iA;
                memberAabb[iA].combine(memberAabb[iC], memberAabb[iD]);
                memberAabb[iB].combine(memberAabb[iA], memberAabb[iE]);
                height[iA] = 1 + MathUtils.max(height[iC], height[iD]);
                height[iB] = 1 + MathUtils.max(height[iA], height[iE]);
            }
            return iB;
        }
        return iA;
    }

    private void validateStructure(int node)
    {
        if (node == NULL_NODE)
        {
            return;
        }
        assert node != root || (parent[node] == NULL_NODE);
        int child1 = this.child1[node];
        int child2 = this.child2[node];
        if (child1 == NULL_NODE)
        {
            assert (child2 == NULL_NODE);
            assert (height[node] == 0);
            return;
        }
        assert 0 <= child1 && child1 < nodeCapacity;
        assert (0 <= child2 && child2 < nodeCapacity);
        assert (parent[child1] == node);
        assert (parent[child2] == node);
        validateStructure(child1);
        validateStructure(child2);
    }

    private void validateMetrics(int node)
    {
        if (node == NULL_NODE)
        {
            return;
        }
        int child1 = this.child1[node];
        int child2 = this.child2[node];
        if (child1 == NULL_NODE)
        {
            assert (child2 == NULL_NODE);
            assert (height[node] == 0);
            return;
        }
        assert 0 <= child1 && child1 < nodeCapacity;
        assert (child2 != child1 && 0 <= child2 && child2 < nodeCapacity);
        int height1 = height[child1];
        int height2 = height[child2];
        int height;
        height = 1 + MathUtils.max(height1, height2);
        assert (this.height[node] == height);
        AABB aabb = new AABB();
        aabb.combine(memberAabb[child1], memberAabb[child2]);
        assert (aabb.lowerBound.equals(memberAabb[node].lowerBound));
        assert (aabb.upperBound.equals(memberAabb[node].upperBound));
        validateMetrics(child1);
        validateMetrics(child2);
    }

    @Override
    public void drawTree(DebugDraw argDraw)
    {
        if (root == NULL_NODE)
        {
            return;
        }
        int height = computeHeight();
        drawTree(argDraw, root, 0, height);
    }

    private final Color3f color = new Color3f();

    private final Vec2 textVec = new Vec2();

    public void drawTree(DebugDraw argDraw, int node, int spot, int height)
    {
        AABB a = memberAabb[node];
        a.getVertices(drawVecs);
        color.set(1,
            (height - spot) * 1f / height,
            (height - spot) * 1f / height);
        argDraw.drawPolygon(drawVecs, 4, color);
        argDraw.getViewportTransform().getWorldToScreen(a.upperBound, textVec);
        argDraw.drawString(textVec.x,
            textVec.y,
            node + "-" + (spot + 1) + "/" + height,
            color);
        int c1 = child1[node];
        int c2 = child2[node];
        if (c1 != NULL_NODE)
        {
            drawTree(argDraw, c1, spot + 1, height);
        }
        if (c2 != NULL_NODE)
        {
            drawTree(argDraw, c2, spot + 1, height);
        }
    }
}
