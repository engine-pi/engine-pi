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
package pi;

import java.awt.image.BufferedImage;

import pi.annotations.API;

/**
 * Ein <b>Bild</b> als grafische Repräsentation einer Bilddatei, die gezeichnet
 * werden kann.
 *
 * <p class="development-note">
 * Diese Klasse ist identisch mit {@link pi.actor.Image}. Sie steht hier, damit
 * sie über das Hauptpaket importiert werden kann, also {@code import pi.Image;}
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class Image extends pi.actor.Image
{
    /**
     * Erzeugt ein Bild durch Angabe des <b>Verzeichnispfads</b> und der
     * <b>Abmessungen</b> in <b>Meter</b>.
     *
     * <p>
     * <b>Entsprechen die Eingabeparameter für Breite und Höhe nicht den
     * Abmessungen des Bildes, dann wird das Bild verzerrt dargestellt.</b>
     * </p>
     *
     * @param filepath Der Verzeichnispfad des Bilds, das geladen werden soll.
     * @param width Die Breite des Bilds in Meter.
     * @param height Die Höhe des Bilds in Meter.
     */
    @API
    public Image(String filepath, double width, double height)
    {
        super(filepath, width, height);
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param filepath Der Verzeichnispfad des Bildes, das geladen werden soll.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @API
    public Image(String filepath, final double pixelPerMeter)
    {
        super(filepath, pixelPerMeter);
    }

    /**
     * Konstruktor für ein Bildobjekt.
     *
     * @param image Ein bereits im Speicher vorhandenes Bild vom Datentyp
     *     {@link BufferedImage}.
     * @param pixelPerMeter Gibt an, wie viele Pixel ein Meter misst.
     */
    @API
    public Image(BufferedImage image, final double pixelPerMeter)
    {
        super(image, pixelPerMeter);
    }

}
