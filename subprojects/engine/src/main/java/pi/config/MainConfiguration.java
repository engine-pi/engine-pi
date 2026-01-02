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
package pi.config;

/**
 * Diese Klasse enthält alle Standard-{@link ConfigurationGroup
 * Konfigurationsgruppen}, die von der Engine Pi bereitgestellt werden.
 *
 * <p>
 * Darüber hinaus kann diese Klasse zum Registrieren und Verwalten von
 * benutzerdefinierten {@link ConfigurationGroup Konfigurationsgruppen}
 * verwendet werden.
 * </p>
 *
 * @see ConfigurationGroup
 * @see ConfigurationLoader#add(ConfigurationGroup...)
 * @see ConfigurationLoader#getConfigurationGroup(Class)
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class MainConfiguration extends ConfigurationLoader
{
    public final GameConfiguration game;

    public final GraphicsConfiguration graphics;

    public final SoundConfiguration sound;

    public final DebugConfiguration debug;

    public final CoordinatesystemConfiguration coordinatesystem;

    public MainConfiguration(final ConfigurationGroup... groups)
    {
        super(groups);
        game = new GameConfiguration();
        graphics = new GraphicsConfiguration();
        sound = new SoundConfiguration();
        debug = new DebugConfiguration();
        coordinatesystem = new CoordinatesystemConfiguration();
        add(game, graphics, sound, debug, coordinatesystem);
    }

    public GameConfiguration game()
    {
        return game;
    }

    public GraphicsConfiguration graphics()
    {
        return graphics;
    }

    public SoundConfiguration sound()
    {
        return sound;
    }

    public DebugConfiguration debug()
    {
        return debug;
    }

    public CoordinatesystemConfiguration coordinatesystem()
    {
        return coordinatesystem;
    }

    public static void main(String[] args)
    {
        MainConfiguration game = new MainConfiguration();
        game.load();
        int width = game.graphics().windowWidth();
        System.out.println(width);
    }
}
