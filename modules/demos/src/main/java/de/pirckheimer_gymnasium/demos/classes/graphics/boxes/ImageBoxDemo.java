package de.pirckheimer_gymnasium.demos.classes.graphics.boxes;

import static pi.graphics.boxes.Boxes.image;
import static pi.graphics.boxes.Boxes.vertical;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.demos.graphics2d.Graphics2DComponent;
import pi.graphics.boxes.ImageBox;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/engine/src/main/java/pi/graphics/boxes/ImageBox.java

public class ImageBoxDemo extends Graphics2DComponent
{

    private ImageBox box()
    {
        return image("dude/box/obj_box001.png");
    }

    private ImageBox car()
    {
        return image("car/truck-240px.png");
    }

    public void render(Graphics2D g)
    {
        vertical(box(), box().width(300).height(200),
                box().width(16).height(16)).render(g).debug();

        vertical(car(), car().flippedVertically(), car().flippedHorizontally(),
                car().flippedVertically().flippedHorizontally()).anchor(400, 0)
                .render(g).debug();
    }

    public static void main(String[] args)
    {
        new ImageBoxDemo().show();
    }
}
