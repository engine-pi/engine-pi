/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/GameConfiguration.java
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
 * This class contains all default {@code ConfigurationGroups} that are provided
 * by the LITIENGINE. Additionally, it can be used to register and manage custom
 * settings that are specific to your game.
 *
 * @see ConfigurationGroup
 * @see ConfigurationLoader#add(ConfigurationGroup)
 * @see ConfigurationLoader#getConfigurationGroup(Class)
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
public class MainConfiguration extends ConfigurationLoader
{
    private final GameConfiguration game;

    private final GraphicsConfiguration graphics;

    private final DebugConfiguration debug;

    private final SoundConfiguration sound;

    public MainConfiguration(final ConfigurationGroup... groups)
    {
        super(groups);
        game = new GameConfiguration();
        graphics = new GraphicsConfiguration();
        debug = new DebugConfiguration();
        sound = new SoundConfiguration();
        getConfigurationGroups().add(sound);
        getConfigurationGroups().add(graphics);
        getConfigurationGroups().add(debug);
    }

    public GameConfiguration game()
    {
        return this.game;
    }

    /**
     * Gets the configuration group with all default graphics settings. Elements
     * in this group will allow you to adjust the game's rendering behavior.
     *
     * @return The graphics configuration.
     */
    public GraphicsConfiguration graphics()
    {
        return this.graphics;
    }

    /**
     * Gets the configuration group with all default debugging settings.
     *
     * @return The debugging configuration.
     */
    public DebugConfiguration debug()
    {
        return this.debug;
    }

    /**
     * Gets the configuration group with all default sound settings.
     *
     * @return The sound configuration.
     */
    public SoundConfiguration sound()
    {
        return this.sound;
    }

    public static void main(String[] args)
    {
        MainConfiguration game = new MainConfiguration();
        game.load();
        int width = game.graphics().windowWidth();
        System.out.println(width);
    }
}
