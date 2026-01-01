/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/GraphicConfiguration.java
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

import java.awt.Dimension;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Represents the graphic configuration settings. This class extends the
 * ConfigurationGroup to provide specific settings for graphics.
 *
 * @author Josef Friedrich
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
@ConfigurationGroupInfo(prefix = "gfx_")
public class GraphicsConfiguration extends ConfigurationGroup
{
    /**
     * Die <b>Breite</b> des Fensters in Pixel.
     *
     * @since 0.42.0
     */
    private int windowWidth = 800;

    /**
     * Die <b>Höhe</b> des Fensters in Pixel.
     *
     * @since 0.42.0
     */
    private int windowHeight = 600;

    /**
     * Gibt die <b>Breite</b> des Fensters in Pixel zurück.
     *
     * @return Die <b>Breite</b> des Fensters in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public int windowWidth()
    {
        return windowWidth;
    }

    /**
     * Setzt die <b>Breite</b> des Fensters in Pixel.
     *
     * @param windowWidth Die <b>Breite</b> des Fensters in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B.
     *     {@code graphic.windowWidth(..).windowHeight(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public GraphicsConfiguration windowWidth(int windowWidth)
    {
        set("windowWidth", windowWidth);
        return this;
    }

    /**
     * Gibt die <b>Höhe</b> des Fensters in Pixel zurück.
     *
     * @return Die <b>Höhe</b> des Fensters in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public int windowHeight()
    {
        return windowHeight;
    }

    /**
     * Setzt die <b>Höhe</b> des Fensters in Pixel.
     *
     * @param windowHeight Die <b>Höhe</b> des Fensters in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B.
     *     {@code graphic.windowWidth(..).windowHeight(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public GraphicsConfiguration windowHeight(int windowHeight)
    {
        set("windowHeight", windowHeight);
        return this;
    }

    /**
     * Gibt die <b>Abmessung</b>, also die <b>Breite</b> und die <b>Höhe</b>,
     * des Fensters in Pixel zurück.
     *
     * @return Die <b>Abmessung</b>, also die <b>Breite</b> und die <b>Höhe</b>,
     *     des Fensters in Pixel.
     *
     * @since 0.42.0
     */
    @Getter
    @API
    public Dimension windowDimension()
    {
        return new Dimension(windowWidth, windowHeight);
    }

    /**
     * Setzt die <b>Abmessung</b>, also die <b>Breite</b> und die <b>Höhe</b>,
     * des Fensters in Pixel.
     *
     * @param windowWidth Die <b>Breite</b> des Fensters in Pixel.
     * @param windowHeight Die <b>Höhe</b> des Fensters in Pixel.
     *
     * @return Eine Referenz auf die eigene Instanz der Konfigurationsgruppe,
     *     damit nach dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der
     *     Konfigurationsgruppe durch aneinander gekettete Setter festgelegt
     *     werden können, z. B.
     *     {@code graphic.windowWidth(..).windowHeight(..)}.
     *
     * @see pi.Controller#windowDimension(int, int)
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public GraphicsConfiguration windowDimension(int windowWidth,
            int windowHeight)
    {
        set("windowWidth", windowWidth);
        set("windowHeight", windowHeight);
        return this;
    }
}
