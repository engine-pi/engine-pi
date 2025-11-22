package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.image.BufferedImage;

/**
 *
 *
 * @since 0.41.0
 */
public final class Boxes
{

    public static AlignBox align(Box child)
    {
        return new AlignBox(child);
    }

    /**
     * Unterlegt eine Kind-Box mit einer <b>Hintergrundfarbe</b>.
     *
     * @since 0.40.0
     */
    public static BackgroundBox background(Box child)
    {
        return new BackgroundBox(child);
    }

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     *
     * @see BorderBox#BorderBox(Box)
     */
    public static BorderBox border(Box child)
    {
        return new BorderBox(child);
    }

    public static DimensionBox dimension(Box child)
    {
        return new DimensionBox(child);
    }

    public static EmptyBox empty()
    {
        return new EmptyBox();
    }

    public static EmptyBox[] empty(int number)
    {
        EmptyBox[] boxes = new EmptyBox[number];
        for (int i = 0; i < number; i++)
        {
            boxes[i] = new EmptyBox();
        }
        return boxes;
    }

    /**
     * Erzeugt einen neuen Rahmen durch die Angabe der enthaltenen Kind-Box.
     *
     * @since 0.40.0
     */
    public static FramedTextBox framedText(String content)
    {
        return new FramedTextBox(content);
    }

    public static GridBox grid(Box... childs)
    {
        return new GridBox(childs);
    }

    /**
     * Erzeugt eine neue <b>horizontale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>horizontal</b> von links nach
     *     rechts angeordnet werden sollen.
     *
     * @since 0.39.0
     *
     * @see HorizontalBox#HorizontalBox(Box...)
     */
    public static HorizontalBox horizontal(Box... childs)
    {
        return new HorizontalBox(childs);
    }

    public static ImageBox image(BufferedImage image)
    {
        return new ImageBox(image);
    }

    public static ImageBox image(String image)
    {
        return new ImageBox(image);
    }

    /**
     * Erzeugt einen neuen <b>Außenabstand</b> durch die Angabe der enthaltenen
     * Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     *
     * @see MarginBox#MarginBox(Box)
     */
    public static MarginBox margin(Box child)
    {
        return new MarginBox(child);
    }

    public static TextBlockBox textBlock(String content)
    {
        return new TextBlockBox(content);
    }

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     *
     * @see TextLineBox#TextLineBox(String)
     */
    public static TextLineBox textLine(String content)
    {
        return new TextLineBox(content);
    }

    /**
     * Erzeugt eine neue <b>vertikale</b> Box.
     *
     * @param childs Die Kinder-Boxen, die <b>vertikal</b> von oben nach unten
     *     angeordnet werden sollen.
     *
     * @since 0.39.0
     *
     * @see VerticalBox#VerticalBox(Box...)
     */
    public static VerticalBox vertical(Box... childs)
    {
        return new VerticalBox(childs);
    }
}
