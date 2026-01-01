/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/DebugConfiguration.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.configuration;

import pi.Game;

/**
 * Configuration class for debug settings.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
@ConfigurationGroupInfo(prefix = "debug_", debug = true)
public class DebugConfiguration extends ConfigurationGroup
{
    private boolean debugEnabled = false;

    private boolean renderBoundingBoxes = false;

    private boolean renderCollisionBoxes = false;

    private boolean renderDebugMouse = false;

    private boolean renderEntityNames = false;

    private boolean renderGuiComponentBoundingBoxes = false;

    private boolean renderHitBoxes = false;

    private boolean showMouseTargetMetric = true;

    private boolean showTilesMetric = false;

    private boolean trackRenderTimes = false;

    /**
     * Constructs a new DebugConfiguration with default settings.
     */
    DebugConfiguration()
    {
        super();
    }

    /**
     * Checks if debug mode is enabled.
     *
     * @return true if debug mode is enabled, false otherwise.
     */
    public boolean isDebugEnabled()
    {
        return Game.isDebug() && this.debugEnabled;
    }

    /**
     * Checks if debug mouse rendering is enabled.
     *
     * @return true if debug mouse rendering is enabled, false otherwise.
     */
    public boolean isRenderDebugMouse()
    {
        return this.isDebugEnabled() && this.renderDebugMouse;
    }

    /**
     * Checks if bounding boxes rendering is enabled.
     *
     * @return true if bounding boxes rendering is enabled, false otherwise.
     */
    public boolean renderBoundingBoxes()
    {
        return this.isDebugEnabled() && this.renderBoundingBoxes;
    }

    /**
     * Checks if collision boxes rendering is enabled.
     *
     * @return true if collision boxes rendering is enabled, false otherwise.
     */
    public boolean renderCollisionBoxes()
    {
        return this.isDebugEnabled() && this.renderCollisionBoxes;
    }

    /**
     * Checks if entity names rendering is enabled.
     *
     * @return true if entity names rendering is enabled, false otherwise.
     */
    public boolean renderEntityNames()
    {
        return this.isDebugEnabled() && this.renderEntityNames;
    }

    /**
     * Checks if hit boxes rendering is enabled.
     *
     * @return true if hit boxes rendering is enabled, false otherwise.
     */
    public boolean renderHitBoxes()
    {
        return this.isDebugEnabled() && this.renderHitBoxes;
    }

    /**
     * Checks if GUI component bounding boxes rendering is enabled.
     *
     * @return true if GUI component bounding boxes rendering is enabled, false
     *     otherwise.
     */
    public boolean renderGuiComponentBoundingBoxes()
    {
        return this.isDebugEnabled() && this.renderGuiComponentBoundingBoxes;
    }

    /**
     * Checks if mouse target metric is shown.
     *
     * @return true if mouse target metric is shown, false otherwise.
     */
    public boolean showMouseTargetMetric()
    {
        return this.isDebugEnabled() && this.showMouseTargetMetric;
    }

    /**
     * Checks if tiles metric is shown.
     *
     * @return true if tiles metric is shown, false otherwise.
     */
    public boolean showTilesMetric()
    {
        return this.isDebugEnabled() && this.showTilesMetric;
    }

    /**
     * Checks if render times tracking is enabled.
     *
     * @return true if render times tracking is enabled, false otherwise.
     */
    public boolean trackRenderTimes()
    {
        return this.isDebugEnabled() && this.trackRenderTimes;
    }

    /**
     * Sets whether debug mode is enabled.
     *
     * @param debugEnabled true to enable debug mode, false to disable.
     */
    public void setDebugEnabled(final boolean debugEnabled)
    {
        this.set("debugEnabled", debugEnabled);
    }

    /**
     * Sets whether bounding boxes rendering is enabled.
     *
     * @param renderBoundingBoxes true to enable bounding boxes rendering, false
     *     to disable.
     */
    public void setRenderBoundingBoxes(final boolean renderBoundingBoxes)
    {
        this.set("renderBoundingBoxes", renderBoundingBoxes);
    }

    /**
     * Sets whether collision boxes rendering is enabled.
     *
     * @param renderCollisionBoxes true to enable collision boxes rendering,
     *     false to disable.
     */
    public void setRenderCollisionBoxes(final boolean renderCollisionBoxes)
    {
        this.set("renderCollisionBoxes", renderCollisionBoxes);
    }

    /**
     * Sets whether debug mouse rendering is enabled.
     *
     * @param renderDebugMouse true to enable debug mouse rendering, false to
     *     disable.
     */
    public void setRenderDebugMouse(final boolean renderDebugMouse)
    {
        this.set("renderDebugMouse", renderDebugMouse);
    }

    /**
     * Sets whether entity names rendering is enabled.
     *
     * @param renderEntityNames true to enable entity names rendering, false to
     *     disable.
     */
    public void setRenderEntityNames(final boolean renderEntityNames)
    {
        this.set("renderEntityNames", renderEntityNames);
    }

    /**
     * Sets whether hit boxes rendering is enabled.
     *
     * @param renderHitBoxes true to enable hit boxes rendering, false to
     *     disable.
     */
    public void setRenderHitBoxes(final boolean renderHitBoxes)
    {
        this.set("renderHitBoxes", renderHitBoxes);
    }

    /**
     * Sets whether mouse target metric is shown.
     *
     * @param showMouseTargetMetric true to show mouse target metric, false to
     *     hide.
     */
    public void setShowMouseTargetMetric(final boolean showMouseTargetMetric)
    {
        this.set("showMouseTargetMetric", showMouseTargetMetric);
    }

    /**
     * Sets whether tiles metric is shown.
     *
     * @param showTilesMetric true to show tiles metric, false to hide.
     */
    public void setShowTilesMetric(final boolean showTilesMetric)
    {
        this.set("showTilesMetric", showTilesMetric);
    }

    /**
     * Sets whether GUI component bounding boxes rendering is enabled.
     *
     * @param renderGuiComponentBoundingBoxes true to enable GUI component
     *     bounding boxes rendering, false to disable.
     */
    public void setRenderGuiComponentBoundingBoxes(
            boolean renderGuiComponentBoundingBoxes)
    {
        this.set("renderGuiComponentBoundingBoxes",
                renderGuiComponentBoundingBoxes);
    }

    /**
     * Sets whether render times tracking is enabled.
     *
     * @param trackRenderTimes true to enable render times tracking, false to
     *     disable.
     */
    public void setTrackRenderTimes(boolean trackRenderTimes)
    {
        this.set("trackRenderTimes", trackRenderTimes);
    }
}
