/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.dsa.turtle;

import static pi.Controller.colorScheme;
import static pi.Controller.images;
import static pi.graphics.geom.Vector.v;

import pi.Scene;
import pi.actor.Actor;
import pi.actor.Animation;
import pi.Circle;
import pi.actor.Polygon;
import pi.annotations.API;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.geom.Vector;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Steuert die <b>Kleidung</b> der Schildkröte, d. h. das Aussehen der
 * Schildkröte.
 *
 * <p>
 * Die Klasse verwaltet die grafische Repräsentation der Schildkröte. Die
 * Schildkröte kann verschieden dargestellt werden: als animiertes Bild, als
 * Pfeil oder als Punkt.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
class TurtleDressController
{
    /**
     * Die <b>grafische Repräsentation</b> der Schildkröte.
     *
     * @since 0.40.0
     */
    private Actor image;

    /**
     * @since 0.40.0
     */
    private Scene scene;

    /**
     * @since 0.40.0
     */
    private TurtleDressType dressType;

    /**
     * Die Größe der Schildkröte in Meter.
     */
    private double size;

    /**
     * @since 0.40.0
     */
    TurtleDressController(Scene scene)
    {
        this(scene, TurtleDressType.ANIMATED_IMAGE, 2);
    }

    /**
     * @since 0.40.0
     */
    TurtleDressController(Scene scene, TurtleDressType dress, double size)
    {
        this.scene = scene;
        this.size = size;
        dress(dress);
    }

    /**
     * Setzt die Figur, die die Schildkröte grafisch repräsentiert neu.
     *
     * <p>
     * Ob die Schildkröte durch ein Bild gezeichnet werden soll. Falls falsch,
     * dann wird ein Dreieck gezeichnet.
     * </p>
     *
     * @since 0.40.0
     */
    @Setter
    public void dress(TurtleDressType dress)
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
            image.color(colorScheme.get().green());
            break;

        case ANIMATED_IMAGE:
            Animation animation = Animation.createFromImages(0.1, size, size,
                    images.get("turtle.png"), images.get("turtle2.png"));
            animation.enableManualMode();
            image = animation;
            break;
        }
        this.dressType = dress;
        scene.add(image);
    }

    /**
     * @since 0.40.0
     */
    @API
    @Setter
    public void nextDress()
    {
        TurtleDressType[] dresses = TurtleDressType.values();
        int i = 0;
        for (; i < dresses.length; i++)
        {
            if (dresses[i] == dressType)
                break;
        }
        dress(dresses[(i + 1) % dresses.length]);
    }

    /**
     * @since 0.40.0
     */
    @Internal
    void showNextAnimation()
    {
        if (image instanceof Animation)
        {
            Animation animation = (Animation) image;
            animation.showNext();
        }
    }

    /**
     * @since 0.40.0
     */
    @Internal
    @Setter
    void position(Vector position)
    {
        image.center(position);
    }

    /**
     * @since 0.40.0
     */
    @Internal
    @Setter
    void direction(double rotation)
    {
        image.rotation(rotation);
    }

    /**
     * <b>Blendet</b> die Schildkröte <b>aus</b>.
     *
     * @since 0.40.0
     */
    @API
    public void hide()
    {
        image.hide();
    }

    /**
     * <b>Blendet</b> die Schildkröte <b>ein</b>.
     *
     * @since 0.40.0
     */
    @API
    public void show()
    {
        image.show();
    }
}
