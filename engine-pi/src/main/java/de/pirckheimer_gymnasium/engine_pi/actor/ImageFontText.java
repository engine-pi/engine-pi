package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Color;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

/**
 * Zur Darstellung von <b>Texten</b> durch eine <b>Bilderschriftart</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.23.0
 */
public class ImageFontText extends Image
{
    /**
     * Die Bilderschriftart.
     */
    private final ImageFont imageFont;

    /**
     * Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    private String content;

    /**
     * Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen kann.
     */
    private int lineWidth;

    /**
     * Die Textausrichtung.
     */
    private TextAlignment alignment;

    /**
     * Die Farbe in der die schwarze Farbe der Ausgangsbilder umgefärbt werden
     * soll.
     */
    private Color color;

    /**
     * Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise verwandelt
     * die Zahl {@code 3} ein Pixel in {@code 9} Pixel der Abmessung
     * {@code 3x3}.
     */
    private int pixelMultiplication;

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont           Die Bilderschriftart.
     * @param content             Der Textinhalt, der in das Bild geschrieben
     *                            werden soll.
     * @param lineWidth           Die maximale Anzahl an Zeichen, die eine Zeile
     *                            aufnehmen kann.
     * @param alignment           Die Textausrichtung.
     * @param color               Die Farbe in der die schwarze Farbe der
     *                            Ausgangsbilder umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *                            Beispielsweise verwandelt die Zahl {@code 3}
     *                            ein Pixel in {@code 9} Pixel der Abmessung
     *                            {@code 3x3}.
     * @param pixelPerMeter       Wie viele Pixel ein Meter des resultierenden
     *                            Bilds groß sein soll.
     */
    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication,
            int pixelPerMeter)
    {
        super(imageFont.render(content, lineWidth, alignment, color,
                pixelMultiplication), pixelPerMeter);
        this.imageFont = imageFont;
        this.content = content;
        this.lineWidth = lineWidth;
        this.alignment = alignment;
        this.color = color;
        this.pixelMultiplication = pixelMultiplication;
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont           Die Bilderschriftart.
     * @param content             Der Textinhalt, der in das Bild geschrieben
     *                            werden soll.
     * @param lineWidth           Die maximale Anzahl an Zeichen, die eine Zeile
     *                            aufnehmen kann.
     * @param alignment           Die Textausrichtung.
     * @param color               Die Farbe, in der die schwarze Farbe der
     *                            Ausgangsbilder umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *                            Beispielsweise verwandelt die Zahl {@code 3}
     *                            ein Pixel in {@code 9} Pixel der Abmessung
     *                            {@code 3x3}.
     */
    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication)
    {
        this(imageFont, content, lineWidth, alignment, color,
                pixelMultiplication, imageFont.getGlyphWidth());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content   Der Textinhalt, der in das Bild geschrieben werden soll.
     * @param lineWidth Die maximale Anzahl an Zeichen, die eine Zeile aufnehmen
     *                  kann.
     * @param alignment Die Textausrichtung.
     */
    public ImageFontText(ImageFont imageFont, String content, int lineWidth,
            TextAlignment alignment)
    {
        this(imageFont, content, lineWidth, alignment, imageFont.getColor(),
                imageFont.getPixelMultiplication());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param imageFont Die Bilderschriftart.
     * @param content   Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    public ImageFontText(ImageFont imageFont, String content)
    {
        this(imageFont, content, imageFont.getLineWidth(),
                imageFont.getAlignment());
    }

    /**
     * Erzeugt einen neuen <b>Text</b>, der durch eine <b>Bilderschriftart</b>
     * dargestellt wird.
     *
     * @param content             Der Textinhalt, der in das Bild geschrieben
     *                            werden soll.
     * @param lineWidth           Die maximale Anzahl an Zeichen, die eine Zeile
     *                            aufnehmen kann.
     * @param alignment           Die Textausrichtung.
     * @param color               Die Farbe, in der die schwarze Farbe der
     *                            Ausgangsbilder umgefärbt werden soll.
     * @param pixelMultiplication Wie oft ein Pixel vervielfältigt werden soll.
     *                            Beispielsweise verwandelt die Zahl {@code 3}
     *                            ein Pixel in {@code 9} Pixel der Abmessung
     *                            {@code 3x3}.
     */
    public void setContent(String content, int lineWidth,
            TextAlignment alignment, Color color, int pixelMultiplication)
    {
        setImage(imageFont.render(content, lineWidth, alignment, color,
                pixelMultiplication));
        this.content = content;
        this.lineWidth = lineWidth;
        this.alignment = alignment;
        this.color = color;
        this.pixelMultiplication = pixelMultiplication;
    }

    /**
     * Setzt den <b>Textinhalt</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    public void setContent(String content)
    {
        setContent(content, lineWidth, alignment, color,
                getPixelMultiplication());
    }

    /**
     * Gibt den Textinhalt, der in das Bild geschrieben werden soll, zurück.
     *
     * @return Der Textinhalt, der in das Bild geschrieben werden soll.
     *
     * @since 0.25.0
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Gibt zurück, wie oft ein Pixel vervielfältigt werden soll.
     *
     * @return Wie oft ein Pixel vervielfältigt werden soll. Beispielsweise
     *         verwandelt die Zahl {@code 3} ein Pixel in {@code 9 Pixel} der
     *         Abmessung {@code 3x3}.
     *
     * @since 0.25.0
     */
    public int getPixelMultiplication()
    {
        if (pixelMultiplication > 1)
        {
            return pixelMultiplication;
        }
        return Game.getPixelMultiplication();
    }

    @Override
    public String toString()
    {
        return String.format("ImageFontText[\n  %s,\n  %s\n]", imageFont,
                super.toString());
    }
}
