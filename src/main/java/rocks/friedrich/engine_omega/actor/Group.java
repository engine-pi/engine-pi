package rocks.friedrich.engine_omega.actor;

import java.util.ArrayList;
import java.util.List;

import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;

import java.util.Arrays;

/**
 * Eine Gruppe bestehend aus mehreren {@link Actor}-Objekten.
 */
public class Group implements Actable
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

    @API
    public final void moveBy(Vector vector)
    {
        actors.forEach((actor) -> actor.moveBy(vector));
    }

    public final void moveBy(double dX, double dY)
    {
        moveBy(new Vector(dX, dY));
    }
}
