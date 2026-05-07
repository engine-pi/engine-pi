/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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

import pi.Scene;
import pi.graphics.geom.Bounds;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/ActorCreateCageDemo.java

/**
 * <b>Erzeugt Figuren</b> und fügt sie in einen Szene ein.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class ActorCreator
{
    /**
     * Dieser private Konstruktor dient dazu, den öffentlichen Konstruktor zu
     * verbergen. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls eine Instanz der Klasse
     *     erzeugt wird.
     */
    private ActorCreator()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * <b>Erzeugt</b> einen <b>Käfig</b> bestehend aus vier Rechtecken um den
     * sichtbaren Kamera-Bereich einer Szene und fügt diese vier Rechtecke in
     * eine Szene ein.
     *
     * <p>
     * In diesem Käfig können z.B. dynamische Figuren dann abprallen.
     * </p>
     *
     * @since 0.45.0
     */
    public static Group<Rectangle> createCage(Scene scene)
    {
        Bounds bounds = scene.visibleArea();

        Rectangle top = new Rectangle(bounds.width() + 2, 1);
        top.anchor(bounds.xLeft() - 1, bounds.yTop());

        Rectangle right = new Rectangle(1, bounds.height());
        right.anchor(bounds.xRight(), bounds.yBottom());

        Rectangle bottom = new Rectangle(bounds.width() + 2, 1);
        bottom.anchor(bounds.xLeft() - 1, bounds.yBottom() - 1);

        Rectangle left = new Rectangle(1, bounds.height());
        left.anchor(bounds.xLeft() - 1, bounds.yBottom());

        Group<Rectangle> group = new Group<>(top, right, bottom, left);

        group.forEach(actor -> actor.restitution(1).makeStatic());

        group.addToScene(scene);
        return group;
    }
}
