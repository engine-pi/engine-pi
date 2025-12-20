package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.graphics.boxes.Boxes.border;
import static pi.graphics.boxes.Boxes.grid;
import static pi.graphics.boxes.Boxes.textBlock;
import static pi.graphics.boxes.HAlign.CENTER;
import static pi.graphics.boxes.HAlign.LEFT;
import static pi.graphics.boxes.HAlign.RIGHT;

import java.awt.Graphics2D;
import java.util.function.Consumer;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.TextBlockBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/TextBlockBox.java

public class TextBlockBoxDemo extends Graphics2DComponent
{
    String lorem = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    private Box text(String content, Consumer<TextBlockBox> consumer)
    {
        var text = textBlock(content);
        var b = border(text);
        if (consumer != null)
        {
            consumer.accept(text);
        }
        return b;
    }

    private Box text(String content)
    {
        return text(content, null);
    }

    public void render(Graphics2D g)
    {
        grid(// width(300)
                text("Breite: 300px: " + lorem, box -> box.width(300)),
                // width(200)
                text("Breite: 200px: " + lorem, box -> box.width(200)),
                // \n
                text("Manueller\nUmbruch\ndurch\n\\n"),
                // LEFT
                text("Linksbündig: " + lorem, box -> box.hAlign(LEFT)),
                // CENTER
                text("Zentriert: " + lorem, box -> box.hAlign(CENTER)),
                // RIGHT
                text("Rechtsbündig: " + lorem, box -> box.hAlign(RIGHT)))
                .anchor(10, 10).render(g);
    }

    public static void main(String[] args)
    {
        new TextBlockBoxDemo().show();
    }
}
