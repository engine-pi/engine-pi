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

import static pi.Resources.images;
import static pi.Vector.v;

import pi.Scene;
import pi.Vector;
import pi.actor.Actor;
import pi.actor.Animation;
import pi.Circle;
import pi.actor.Polygon;
import pi.annotations.API;
import pi.annotations.Internal;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

/**
 * Steuert die <b>Kleidung</b> der Schildkröte, d. h. das Aussehen der
 * Schildkröte.
 *
 * <p>
 * Die Klasse verwaltet die grafische Repräsentation der Schildkröte. Die
 * Schildkröte kann verschieden dargestellt werden: Als animiertes Bild, als
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
     * Die <b>graphische Repräsentation</b> der Schildkröte.
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
    private double size = 2;

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
        setDressType(dress);
    }

    /**
     * Setzt die Figur die die Schildkröte graphisch repräsentiert neu.
     *
     * Ob die Schildkröte durch ein Bild gezeichnet werden soll. Falls falsch,
     * dann wird eine Dreieck gezeichnet.
     *
     * @since 0.40.0
     */

    public void setDressType(TurtleDressType dressType)
    {
        if (image != null)
        {
            scene.remove(image);
        }
        switch (dressType)
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
        this.dressType = dressType;
        scene.add(image);
    }

    /**
     * @since 0.40.0
     */
    @API
    public void setNextDress()
    {
        TurtleDressType[] dresses = TurtleDressType.values();
        int i = 0;
        for (; i < dresses.length; i++)
        {
            if (dresses[i] == dressType)
                break;
        }
        setDressType(dresses[(i + 1) % dresses.length]);
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
    void setPosition(Vector position)
    {
        image.setCenter(position);
    }

    /**
     * @since 0.40.0
     */
    @Internal
    void setDirection(double rotation)
    {
        image.setRotation(rotation);
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
