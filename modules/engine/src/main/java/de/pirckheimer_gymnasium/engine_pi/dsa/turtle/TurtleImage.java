package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.images;
import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.Animation;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Verwaltet die grafische Repräsentation der Schildkröte. Die Schildkröte kann
 * verschieden dargestellt werden: Als animiertes Bild, als Pfeil oder als
 * Punkt.
 */
class TurtleImage
{
    /**
     * Die <b>graphische Repräsentation</b> der Schildkröte.
     */
    private Actor image;

    private Scene scene;

    private TurtleDress dress;

    /**
     * Die Größe der Schildkröte in Meter.
     */
    private double size = 2;

    TurtleImage(Scene scene)
    {
        this(scene, TurtleDress.ANIMATED_IMAGE, 2);
    }

    TurtleImage(Scene scene, TurtleDress dress, double size)
    {
        this.scene = scene;
        this.size = size;
        setDress(dress);
    }

    /**
     * Setzt die Figur die die Schildkröte graphisch repräsentiert neu.
     *
     * Ob die Schildkröte durch ein Bild gezeichnet werden soll. Falls falsch,
     * dann wird eine Dreieck gezeichnet.
     *
     * @since 0.40.0
     */
    void setDress(TurtleDress dress)
    {
        if (image != null)
        {
            scene.remove(image);
        }
        switch (dress)
        {
        case DOT:
            image = new Circle(size / 10);
            break;

        case ARROW:
            image = new Polygon(v(-size / 4, size / 4), v(size, 0),
                    v(-size / 4, -size / 4));
            image.setColor("green");
            break;

        case ANIMATED_IMAGE:
            Animation animation = Animation.createFromImages(0.1, size, size,
                    images.get("turtle.png"), images.get("turtle2.png"));
            animation.enableManualMode();
            image = animation;
            break;
        }
        this.dress = dress;
        scene.add(image);
    }

    void setNextDress()
    {
        TurtleDress[] dresses = TurtleDress.values();
        int i = 0;
        for (; i < dresses.length; i++)
        {
            if (dresses[i] == dress)
                break;
        }
        setDress(dresses[(i + 1) % dresses.length]);
    }

    void showNextAnimation()
    {
        if (image instanceof Animation)
        {
            Animation animation = (Animation) image;
            animation.showNext();
        }
    }

    void setPosition(Vector position)
    {
        image.setCenter(position);
    }

    void setRotation(double rotation)
    {
        image.setRotation(rotation);
    }

    /**
     * <b>Blendet</b> die Schildkröte <b>aus</b>.
     *
     * @since 0.40.0
     */
    void hide()
    {
        image.hide();
    }

    /**
     * <b>Blendet</b> die Schildkröte <b>ein</b>.
     *
     * @since 0.40.0
     */
    void show()
    {
        image.show();
    }
}
