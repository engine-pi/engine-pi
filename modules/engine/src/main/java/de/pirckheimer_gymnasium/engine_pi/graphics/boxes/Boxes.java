package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.image.BufferedImage;
import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

/**
 * Eine Sammlung von statischen Methoden, um {@link Box}-Objekte zu erzeugen.
 *
 * <p>
 * Diese Methoden können über einen statischen Import eingebunden werden, womit
 * etwas Schreibarbeit entfällt. Statt ...
 * </p>
 *
 * <pre>
 * {@code
 * import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.TextBlockBox;
 *
 * TextBlockBox text = new TextBlockBox("text");
 * }
 * </pre>
 *
 * <p>
 * kann dann geschrieben werden ....
 * </p>
 *
 * <pre>
 * {@code
 * import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Boxes.textBlock;
 *
 * var text = textBlock("text");
 * }
 * </pre>
 *
 * <p>
 * Außerdem setzen manche dieser Methoden gezielt Attribute des initialisierten
 * {@link Box}-Objekts. Standardmäßig sind die Attribute und Konstruktoren der
 * {@link Box}-Klassen so gewählt, dass die {@link Box}en oftmals gar nicht
 * sichtbar sind. Zum Beispiel hat die {@link BackgroundBox} keine
 * Hintergrundfarbe gesetzt.
 * </p>
 *
 * @since 0.41.0
 */
public final class Boxes
{

    public static ContainerBox container(Box child)
    {
        return new ContainerBox(child);
    }

    /**
     * Unterlegt eine Kind-Box mit einer roten <b>Hintergrundfarbe</b>.
     *
     * @since 0.40.0
     */
    public static BackgroundBox background(Box child)
    {
        return new BackgroundBox(child).color(colors.get("red", 100));
    }

    /**
     * Erzeugt einen neuen Rahmen mit einer <b>Linienstärke von einem Pixel</b>
     * um die enthaltene Kind-Box.
     *
     * @param child Die <b>Kind-Box</b>, die umrahmt werden soll.
     *
     * @since 0.40.0
     *
     * @see BorderBox#BorderBox(Box)
     */
    public static BorderBox border(Box child)
    {
        return new BorderBox(child).thickness(1);
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

    /**
     * Erzeugte einen Anordnung, in der die Kinder-Boxen in einem Gitter von
     * links oben nach rechts unten angeordnet werden. Der Innenabstand wird auf
     * 10 Pixel gesetzt.
     */
    public static GridBox grid(Box... childs)
    {
        GridBox box = new GridBox(childs);
        box.padding(10);
        return box;
    }

    /**
     * Erzeugt eine neue <b>horizontale</b> Box. Der Innenabstand wird auf 10
     * Pixel gesetzt.
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
        HorizontalBox box = new HorizontalBox(childs);
        box.padding(10);
        return box;
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
     * Erzeugt eine neue <b>vertikale</b> Box. Der Innenabstand wird auf 10
     * Pixel gesetzt.
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
        VerticalBox box = new VerticalBox(childs);
        box.padding(10);
        return box;
    }
}
