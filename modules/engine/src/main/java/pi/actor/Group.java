/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package pi.actor;

import java.util.ArrayList;
import java.util.List;

import pi.Scene;
import pi.Vector;
import pi.annotations.API;

import java.util.Arrays;

/**
 * Eine <b>Gruppe</b> bestehend aus mehreren {@link Actor}-Objekten.
 *
 * @author Josef Friedrich
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
