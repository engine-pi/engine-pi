package rocks.friedrich.engine_omega.actor;

import java.util.ArrayList;
import java.util.List;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;

import java.util.Arrays;

public class Group
{
    private final List<Actor> actors;

    private Scene scene;

    public Group(Actor... actors)
    {
        this.actors = new ArrayList<Actor>(Arrays.asList(actors));
    }

    public Group(Scene scene)
    {
        this();
        this.scene = scene;
    }

    public void addtoScene(Scene scene)
    {
        this.scene = scene;
        actors.forEach((actor) -> scene.add(actor));
    }

    public void add(Actor... actors)
    {
        this.actors.addAll(Arrays.asList(actors));
        if (scene != null)
        {
            this.actors.forEach((actor) -> scene.add(actor));
        }
    }

    /**
     * Verschiebt die Gruppe ohne Bedingungen auf der Zeichenebene.
     *
     * @param vector Der Vektor, der die Verschiebung der Gruppe angibt.
     * @see Vector
     * @see #moveBy(double, double)
     */
    @API
    public final void moveBy(Vector vector)
    {
        actors.forEach((actor) -> actor.moveBy(vector));
    }

    /**
     * Verschiebt die Gruppe.
     *
     * <p>
     * Hierbei wird nichts anderes gemacht, als <code>moveBy(new
     * Vector(dx, dy))</code> auszuführen. Insofern ist diese Methode dafür gut,
     * sich nicht mit der Klasse {@link Vector} auseinandersetzen zu müssen.
     *
     * @param dX Die Verschiebung in Richtung X.
     * @param dY Die Verschiebung in Richtung Y.
     * @see #moveBy(Vector)
     */
    @API
    public final void moveBy(double dX, double dY)
    {
        moveBy(new Vector(dX, dY));
    }
}
