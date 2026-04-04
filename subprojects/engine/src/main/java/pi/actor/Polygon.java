/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Polygon.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.function.Supplier;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.graphics.geom.Vector;
import pi.physics.FixtureBuilder;
import pi.util.Graphics2DUtil;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/polygon.md

/**
 * Beschreibt eine beliebige <b>polygonale</b> geometrische Form.
 *
 * @author Michael Andonie
 * @author Josef Friedrich
 */
@API
public class Polygon extends Actor
{
    /**
     * Der Streckenzug an Punkten, der das Polygon beschreibt.
     */
    private Vector[] points;

    /**
     * Erstellt ein neues Polygon. Seine Position ist der <b>Ursprung</b>.
     *
     * @param points Der Streckenzug an Punkten, der das Polygon beschreibt.
     */
    @API
    public Polygon(Vector... points)
    {
        super(null);
        this.points = points;
        points(points);
    }

    /**
     * Setzt den Streckenzug neu, der dieses Polygon beschreibt.
     *
     * <p>
     * <b>Ändert die physikalischen Eigenschaften</b> des Polygons. <i>Konkave
     * Streckenzüge werden durch die kleinste konvexe Körperform beschrieben,
     * die den Streckenzug umspannt.</i> Komplexere Formen können über
     * {@code setFixtures(Supplier)} physikalisch präzise umgesetzt werden.
     * </p>
     *
     * @param points Neuer Streckenzug.
     *
     * @see pi.actor.Actor#fixtures(Supplier)
     */
    @API
    @Setter
    @ChainableMethod
    public Polygon points(Vector... points)
    {
        if (points.length < 3)
        {
            throw new IllegalArgumentException(
                    "Der Streckenzug muss mindestens aus 3 Punkten bestehen, um ein gültiges Polygon zu beschreiben.");
        }
        fixture(() -> FixtureBuilder.polygon(points));
        return this;
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        AffineTransform oldTransform = g.getTransform();
        g.scale(1, -1);
        g.setColor(color());
        Graphics2DUtil.fillPolygon(g, pixelPerMeter, points);
        g.setTransform(oldTransform);
    }
}
