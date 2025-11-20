package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.image;
import static de.pirckheimer_gymnasium.engine_pi.graphics.boxes.Box.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/de/pirckheimer_gymnasium/engine_pi/graphics/boxes/ImageBox.java

public class ImageBoxDemo extends Graphics2DComponent
{
    public void render(Graphics2D g)
    {

        vertical(image("dude/box/obj_box001.png"),
                image("dude/box/obj_box001.png").size(300, 200)).render(g);
    }

    public static void main(String[] args)
    {
        new ImageBoxDemo().show();
    }
}
