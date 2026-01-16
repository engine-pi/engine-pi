package pi.actor;

import static pi.Controller.colorScheme;

import java.awt.Color;
import java.awt.Graphics2D;

import pi.annotations.API;
import pi.annotations.Internal;
import pi.graphics.boxes.TextBlockBox;
import pi.physics.FixtureBuilder;

public class TextBlock extends Geometry
{

    TextBlockBox box;

    /**
     * Erstellt einen <b>Text</b> mit spezifischem <b>Inhalt</b>, <b>Höhe</b>,
     * <b>Schriftart</b>, und <b>Schriftstil</b>.
     *
     * @param content Der <b>Textinhalt</b>, der dargestellt werden soll.
     */
    @API
    public TextBlock(Object content)
    {
        super(null);
        box = new TextBlockBox(content);
        Color color = colorScheme.get().white();
        box.color(color);
        box.fontSize(1000);
        color(color);
        syncAttributes();
    }

    /**
     * @hidden
     */
    @Internal
    private void syncAttributes()
    {
        box.measure();

        // box.width();

        // box.height();

        fixture(() -> FixtureBuilder.rectangle(0, 0));
    }

    /**
     * @hidden
     */
    @Override
    @Internal
    public void render(Graphics2D g, double pixelPerMeter)
    {

        box.render(g);
    }
}
