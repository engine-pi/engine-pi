package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Color;

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
    private final ImageFont imageFont;

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
        this.imageFont = imageFont.setLineWidth(lineWidth)
                .setAlignment(alignment).setColor(color)
                .setPixelMultiplication(pixelMultiplication);
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
     * Setzt den <b>Textinhalt</b> neu.
     *
     * @param content Der Textinhalt, der in das Bild geschrieben werden soll.
     */
    public void setContent(String content)
    {
        setImage(imageFont.render(content));
    }

    @Override
    public String toString()
    {
        return String.format("ImageFontText[\n  %s,\n  %s]", imageFont,
                super.toString());
    }
}
