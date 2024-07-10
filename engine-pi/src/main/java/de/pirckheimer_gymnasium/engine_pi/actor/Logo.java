package de.pirckheimer_gymnasium.engine_pi.actor;

import static de.pirckheimer_gymnasium.engine_pi.Vector.v;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Zeichnet das Engine Pi Logo in eine Szene.
 *
 * @author Josef Friedrich
 */
public class Logo implements ActorAdder
{
    private final Scene scene;

    private final double factor;

    private final Vector anchor;

    public Rectangle rectangleP;

    public Triangle triangleP;

    public Circle circleP;

    public Rectangle rectangleI;

    public Circle circleI;

    public Logo(Scene scene, Vector anchor, double factor)
    {
        this.scene = scene;
        this.factor = factor;
        this.anchor = anchor;
        draw();
    }

    @Override
    public Scene getScene()
    {
        return scene;
    }

    public void applyPhysicSettings(Actor actor)
    {
        actor.makeStatic();
        actor.setElasticity(0.97);
    }

    private Vector shift(double x, double y)
    {
        return v(x, y).multiply(factor).add(anchor);
    }

    private Vector shift()
    {
        return shift(0, 0);
    }

    private Rectangle drawRectangle()
    {
        Rectangle rectangle = addRectangle(1.0 * factor, 2.0 * factor);
        applyPhysicSettings(rectangle);
        return rectangle;
    }

    private Circle drawCircle()
    {
        Circle circle = addCircle(1.0 * factor);
        applyPhysicSettings(circle);
        return circle;
    }

    private void draw()
    {
        int iX = 2;
        // Rechteck des Ps
        rectangleP = drawRectangle();
        rectangleP.setPosition(shift());
        // Dreieck des Ps
        triangleP = addTriangle(3 * factor);
        applyPhysicSettings(triangleP);
        triangleP.rotateBy(-90).setPosition(shift(-0.25, 4));
        // Kreis des Ps
        circleP = drawCircle();
        circleP.setPosition(shift(0, 2));
        // Rechteck des Is
        rectangleI = drawRectangle();
        rectangleI.setPosition(shift(iX, 0));
        // Kreis des Is
        circleI = drawCircle();
        circleI.setPosition(shift(iX, 2.8));
    }
}
