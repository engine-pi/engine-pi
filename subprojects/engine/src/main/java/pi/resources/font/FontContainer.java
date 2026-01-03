/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/Fonts.java
 *
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/io/FontLoader.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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
package pi.resources.font;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.resources.ResourceLoader;
import pi.resources.ResourcesContainer;

/**
 * Ein Speicher für <b>Schriftarten</b> des Datentyps {@link Font}.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public final class FontContainer extends ResourcesContainer<Font>
{
    /**
     * Die Standardschriftgröße.
     */
    private static final int DEFAULT_SIZE = 12;

    private static GraphicsEnvironment ge = GraphicsEnvironment
            .getLocalGraphicsEnvironment();

    /**
     * Folgende Pfade führen zu den mitgelieferten Schriftstilen der
     * Standardschrift.
     */
    String[] defaultFontFiles = { "fonts/Cantarell-Regular.ttf",
            "fonts/Cantarell-Bold.ttf", "fonts/Cantarell-Italic.ttf",
            "fonts/Cantarell-BoldItalic.ttf" };

    /**
     * Alle möglichen Schriftnamen des Systems, auf dem man sich gerade
     * befindet.
     *
     * <p>
     * Hiernach werden Überprüfungen gemacht, ob die gewünschte Schriftart auf
     * dem System vorhanden ist.
     * </p>
     */
    public static final String[] systemFonts;
    static
    {
        systemFonts = ge.getAvailableFontFamilyNames();
    }

    /**
     * Prüft, ob eine Schriftart auf diesem <b>System</b> vorhanden ist.
     *
     * @param fontName Der <b>Name</b> der zu überprüfenden Schriftart.
     *
     * @return <code>true</code>, falls die Schriftart auf dem System existiert,
     *     sonst <code>false</code>.
     */
    @API
    public static boolean isSystemFont(String fontName)
    {
        for (String s : systemFonts)
        {
            if (s.equals(fontName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Gibt eine Namesliste der <b>Systemschriftarten</b> zurück.
     *
     * @return Liste mit Systemschriftarten.
     */
    @API
    public static String[] getSystemFonts()
    {
        return systemFonts.clone();
    }

    /**
     * Lädt eine <b>Systemschriftart</b> basierend auf dem Namen.
     *
     * @param fontName Der Name der Systemschriftart.
     *
     * @return Die Systemschriftart.
     */
    public static Font loadSystemFontByName(String fontName)
    {
        return new Font(fontName, Font.PLAIN, DEFAULT_SIZE);
    }

    /**
     * Gibt eine Schriftart zurück, die durch einen <b>Namen</b> oder
     * <b>Dateipfad</b> ermittelt wird.
     *
     * @param name Der Name einer Systemschriftart (z. B. {@code "Arial"}) oder
     *     der Dateipfad zu einer Schriftdatei (z. B.
     *     {@code "fonts/Cantarell-Regular.ttf"}).
     *
     * @return Die Schriftart.
     */
    public Font get(String name)
    {
        if (isSystemFont(name))
        {
            return loadSystemFontByName(name);
        }
        return super.get(name);
    }

    /**
     * Gibt eine Schriftart zurück, die durch einen <b>Namen</b> oder
     * <b>Dateipfad</b> ermittelt wird, und mit den gewünschten
     * <b>Stil</b>attribut versehen ist.
     *
     * @param name Der Name einer Systemschriftart (z. B. {@code "Arial"}) oder
     *     der Dateipfad zu einer Schriftdatei (z. B.
     *     {@code "fonts/Cantarell-Regular.ttf"}).
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @return Die Schriftart, die die angegebenen Stileinstellungen besitzt.
     */
    public Font get(String name, int style)
    {
        return get(name).deriveFont(style);
    }

    /**
     * Gibt eine Schriftart zurück, die durch einen <b>Namen</b> oder
     * <b>Dateipfad</b> ermittelt wird, und mit den gewünschten
     * <b>Größen</b>attribut versehen ist.
     *
     * @param name Der Name einer Systemschriftart (z. B. {@code "Arial"}) oder
     *     der Dateipfad zu einer Schriftdatei (z. B.
     *     {@code "fonts/Cantarell-Regular.ttf"}).
     * @param size Die Schriftgröße in Punkten (pt, Points), z. B. {@code 12.0}
     *     für <em>12pt</em>.
     *
     * @return Die Schriftart, die die angegebenen Größeneinstellungen besitzt.
     */
    public Font get(String name, double size)
    {
        return get(name).deriveFont((float) size);
    }

    /**
     * Gibt eine Schriftart zurück, die durch einen <b>Namen</b> oder
     * <b>Dateipfad</b> ermittelt wird, und mit den gewünschten <b>Stil</b>- und
     * <b>Größen</b>attributen versehen ist.
     *
     * @param name Der Name einer Systemschriftart (z. B. {@code "Arial"}) oder
     *     der Dateipfad zu einer Schriftdatei (z. B.
     *     {@code "fonts/Cantarell-Regular.ttf"}).
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     * @param size Die Schriftgröße in Punkten (pt, Points), z. B. {@code 12.0}
     *     für <em>12pt</em>.
     *
     * @return Die Schriftart, die die angegebenen Stil- und Größeneinstellungen
     *     besitzt.
     */
    public Font get(String name, int style, double size)
    {
        return get(name).deriveFont(style, (float) size);
    }

    /**
     * Gibt die mit der Engine Pi mitgelieferte <b>Standardschrift</b> aus.
     * Dabei muss der <b>Schriftstil als Aufzählungstyp</b> angegeben werden.
     *
     * @param style Der <b>Schriftstil als Aufzählungstyp</b>.
     *
     * @return Die mitgelieferte <b>Standardschrift</b>.
     *
     * @since 0.37.0
     */
    @Getter
    public Font defaultFont(FontStyle style)
    {
        return super.get(defaultFontFiles[style.getStyle()])
                .deriveFont(style.getStyle());
    }

    /**
     * Gibt die mit der Engine Pi mitgelieferte <b>Standardschrift</b> aus.
     * Dabei muss der <b>Schriftstil als Ganzzahl</b> angegeben werden.
     *
     * @param style Der <b>Schriftstil als Ganzzahl</b>.
     *
     * @return Die mitgelieferte <b>Standardschrift</b>.
     *
     * @since 0.39.0
     */
    @Getter
    public Font defaultFont(int style)
    {
        return defaultFont(FontStyle.getStyle(style));
    }

    /**
     * Gibt die mit der Engine Pi mitgelieferte <b>Standardschrift</b> im
     * <b>normalen</b> Schriftstil aus.
     *
     * @return Die mitgelieferte <b>Standardschrift</b>.
     *
     * @since 0.37.0
     */
    @Getter
    public Font defaultFont()
    {
        return defaultFont(FontStyle.PLAIN);
    }

    /**
     * Lädt eine TrueType-Schriftart aus der angegebenen URL und gibt das
     * resultierende {@link Font}-Objekt zurück.
     *
     * Es wird versucht, über {@link ResourceLoader#get(URL)} einen
     * {@link InputStream} zur Ressource zu öffnen. Aus diesem Stream wird
     * mittels {@link Font#createFont(Font.TRUETYPE_FONT, InputStream)} ein
     * {@link Font}-Objekt erzeugt.
     *
     * Falls die Schriftart nicht gefunden wird oder beim Lesen/Parsen der
     * Schriftart eine Ausnahme auftritt, wird eine {@link RuntimeException} mit
     * einer entsprechenden Fehlermeldung geworfen.
     *
     * @param resourceName URL der Schriftressource
     *
     * @return Die geladene Schriftart.
     *
     * @throws RuntimeException wenn die Ressource nicht geladen werden kann
     *     oder beim Erstellen der Schrift ein Fehler auftritt
     */
    @Override
    protected Font load(URL resourceName)
    {
        try (final InputStream fontStream = ResourceLoader.get(resourceName))
        {
            if (fontStream == null)
            {
                throw new RuntimeException(String.format(
                        "Die Schrift %s konnte nicht geladen werden.",
                        resourceName));
            }
            return Font.createFont(Font.TRUETYPE_FONT, fontStream);
        }
        catch (final FontFormatException | IOException e)
        {
            throw new RuntimeException(
                    String.format("Die Schrift %s konnte nicht geladen werden.",
                            resourceName));
        }
    }
}
