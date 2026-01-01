/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/InputConfiguration.java
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

/**
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
@ConfigurationGroupInfo(prefix = "input_")
public class InputConfiguration extends ConfigurationGroup
{
    private float mouseSensitivity;

    private boolean gamepadSupport;

    private float gamepadAxisDeadzone;

    private float gamepadTriggerDeadzone;

    private float gamepadStickDeadzone;

    /**
     * Constructs a new InputConfiguration with default settings.
     */
    InputConfiguration()
    {
        this.setMouseSensitivity(1.0F);
        this.setGamepadSupport(false);
        this.setGamepadAxisDeadzone(0.3f);
        this.setGamepadTriggerDeadzone(0.1f);
        this.setGamepadStickDeadzone(0.15f);
    }

    /**
     * Gets the current mouse sensitivity.
     *
     * @return the mouse sensitivity.
     */
    public float getMouseSensitivity()
    {
        return mouseSensitivity;
    }

    /**
     * Gets the current gamepad axis deadzone.
     *
     * @return the gamepad axis deadzone.
     */
    public float getGamepadAxisDeadzone()
    {
        return gamepadAxisDeadzone;
    }

    /**
     * Gets the current gamepad trigger deadzone.
     *
     * @return the gamepad trigger deadzone.
     */
    public float getGamepadTriggerDeadzone()
    {
        return gamepadTriggerDeadzone;
    }

    /**
     * Gets the current gamepad stick deadzone.
     *
     * @return the gamepad stick deadzone.
     */
    public float getGamepadStickDeadzone()
    {
        return gamepadStickDeadzone;
    }

    /**
     * Checks if gamepad support is enabled.
     *
     * @return true if gamepad support is enabled, false otherwise.
     */
    public boolean isGamepadSupport()
    {
        return gamepadSupport;
    }

    /**
     * Sets the mouse sensitivity.
     *
     * @param mouseSensitivity the new mouse sensitivity.
     */
    public void setMouseSensitivity(final float mouseSensitivity)
    {
        this.set("mouseSensitivity", mouseSensitivity);
    }

    /**
     * Sets the gamepad support.
     *
     * @param gamepadSupport the new gamepad support status.
     */
    public void setGamepadSupport(boolean gamepadSupport)
    {
        this.set("gamepadSupport", gamepadSupport);
    }

    /**
     * Sets the gamepad axis deadzone.
     *
     * @param gamepadAxisDeadzone the new gamepad axis deadzone.
     */
    public void setGamepadAxisDeadzone(float gamepadAxisDeadzone)
    {
        this.set("gamepadAxisDeadzone", gamepadAxisDeadzone);
    }

    /**
     * Sets the gamepad trigger deadzone.
     *
     * @param gamepadTriggerDeadzone the new gamepad trigger deadzone.
     */
    public void setGamepadTriggerDeadzone(float gamepadTriggerDeadzone)
    {
        this.set("gamepadTriggerDeadzone", gamepadTriggerDeadzone);
    }

    /**
     * Sets the gamepad stick deadzone.
     *
     * @param gamepadStickDeadzone the new gamepad stick deadzone.
     */
    public void setGamepadStickDeadzone(float gamepadStickDeadzone)
    {
        this.set("gamepadStickDeadzone", gamepadStickDeadzone);
    }
}
