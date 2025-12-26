package pi.graphics;

import java.awt.Graphics2D;

import pi.annotations.API;
import pi.annotations.Setter;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.TextBlockBox;
import pi.graphics.boxes.TextBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VerticalBox;
import pi.resources.FontStyle;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class SceneInfoOverlay
{
    /**
     * Der <b>Titel</b> der Szene.
     *
     * <p>
     * Der Titel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox title;

    /**
     * Der <b>Untertitel</b> der Szene.
     *
     * <p>
     * Der Untertitel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox subtitle;

    /**
     * Ein längerer <b>Beschreibungstext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox description;

    /**
     * Ein <b>Hilfetext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox help;

    private VerticalBox<Box> wrapperBox;

    public SceneInfoOverlay()
    {
        this(null, null, null, null);
    }

    public SceneInfoOverlay(String title, String subtitle, String description,
            String help)
    {
        this.title = new TextLineBox(title).fontStyle(FontStyle.BOLD)
                .fontSize(18);
        this.subtitle = new TextLineBox(subtitle)
                .fontStyle(FontStyle.BOLD_ITALIC);
        this.description = new TextBlockBox(description).fontSize(12);

        this.help = new TextBlockBox(help).fontSize(12);
        wrapperBox = new VerticalBox<>(this.title, this.subtitle,
                this.description, this.help);
        wrapperBox.forEachCell(cell -> cell.box.disable(true));
        wrapperBox.padding(5);
    }

    /**
     * Setzt den <b>Titel</b> der Szene.
     *
     * <p>
     * Der Titel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param title Der <b>Titel</b> der Szene.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay title(String title)
    {
        this.title.enable();
        this.title.content(title);
        return this;
    }

    /**
     * Setzt den <b>Untertitel</b> der Szene.
     *
     * <p>
     * Der Untertitel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param subtitle Der <b>Untertitel</b> der Szene.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay subtitle(String subtitle)
    {
        this.subtitle.enable();
        this.subtitle.content(subtitle);
        return this;
    }

    /**
     * Setzt den längerer <b>Beschreibungstext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param description Ein längerer <b>Beschreibungstext</b> zur Szene.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay description(String description)
    {
        this.description.enable();
        this.description.content(description);
        return this;
    }

    /**
     * Setzt den <b>Hilfetext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param help Ein <b>Hilfetext</b> zur Szene.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay help(String help)
    {
        this.help.enable();
        this.help.content(help);
        return this;
    }

    public void render(Graphics2D g)
    {
        wrapperBox.render(g);
    }
}
