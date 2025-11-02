package de.pirckheimer_gymnasium.engine_pi.instant;

import java.awt.Font;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;

/**
 * Beschreibt die <b>Instant-Variante</b> eines <b>Textes</b>.
 *
 * @see de.pirckheimer_gymnasium.engine_pi.actor.Text
 * @see ActorAdder
 *
 * @author Josef Friedrich
 *
 * @since 0.34.0
 */
public class Text extends de.pirckheimer_gymnasium.engine_pi.actor.Text
        implements InstantActor
{

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> in <b>normaler,
     * serifenfreier Standardschrift</b> mit <b>einem Meter Höhe</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     *
     * @since 0.34.0
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Text#Text(String)
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String)
     */
    @API
    public Text(String content)
    {
        this(content, 1);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b> und <b>Höhe</b>
     * in <b>normaler, serifenfreier Standardschrift</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Text#Text(String, double)
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String,
     *     double)
     *
     * @since 0.34.0
     */
    @API
    public Text(String content, double height)
    {
        this(content, height, Font.SANS_SERIF);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>
     * und <b>Schriftart</b> in <b>nicht fettem und nicht kursiven
     * Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     * @param fontName Der Name der <b>Schriftart</b>, in der der Text
     *     dargestellt werden soll und nicht der Name der Schrift-Datei.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Text#Text(String, double,
     *     String)
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String,
     *     double, String)
     *
     * @since 0.34.0
     */
    @API
    public Text(String content, double height, String fontName)
    {
        this(content, height, fontName, 0);
    }

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>,
     * <b>Schriftart</b>, und <b>Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     * @param height Die <b>Höhe</b> des Textes in Meter.
     * @param fontName Der Name der <b>Schriftart</b>, in der der Text
     *     dargestellt werden soll und nicht der Name der Schrift-Datei.
     * @param style Der Stil der Schriftart (<b>fett, kursiv, oder fett und
     *     kursiv</b>).
     *     <ul>
     *     <li>{@code 0}: Normaler Text</li>
     *     <li>{@code 1}: Fett</li>
     *     <li>{@code 2}: Kursiv</li>
     *     <li>{@code 3}: Fett und Kursiv</li>
     *     </ul>
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Text#Text(String, double,
     *     String, int)
     * @see de.pirckheimer_gymnasium.engine_pi.actor.ActorAdder#addText(String,
     *     double, String, int)
     *
     * @since 0.34.0
     */
    @API
    public Text(String content, double height, String fontName, int style)
    {
        super(content, height, fontName, style);
        Controller.addActors(this);
    }

    /**
     * @since 0.34.0
     */
    public Scene getMainScene()
    {
        return Controller.getMainScene();
    }

    public static void main(String[] args)
    {
        new Text("Hello, World").setColor("white");
    }
}
