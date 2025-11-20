/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Graphics2D;
import java.util.Collections;
import java.util.Iterator;

/**
 * Eine Box, die nur eine einzige <b>Kind-Box</b> enthält.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
abstract class ChildBox extends Box
{
    /**
     * Die <b>Kind-Box</b>, dieser übergeordneten Box.
     */
    protected Box child;

    public ChildBox()
    {
        this(null);
    }

    @Override
    public Iterator<Box> iterator()
    {
        return child == null ? Collections.<Box>emptyIterator()
                : Collections.singleton(child).iterator();
    }

    /**
     * Erzeugt eine Box, die nur eine einzige <b>Kind-Box</b> enthält und setzt
     * dabei sich als Elternbox der Kind-Box.
     *
     * @param child Die Kind-Box.
     *
     * @since 0.38.0
     */
    public ChildBox(Box child)
    {
        addChild(child);
    }

    protected void addChild(Box child)
    {
        this.child = child;
        if (child != null)
        {
            child.parent = this;
        }
    }

    public int numberOfChilds()
    {
        return 1;
    }

    @Override
    void draw(Graphics2D g)
    {
        child.draw(g);
    }
}
