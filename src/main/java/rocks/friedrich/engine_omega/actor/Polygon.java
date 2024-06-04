/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Polygon.java
 *
 * Engine Omega ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega.actor;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.function.Supplier;

import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;
import rocks.friedrich.engine_omega.annotations.Internal;
import rocks.friedrich.engine_omega.physics.FixtureBuilder;

/**
 * Beschreibt eine beliebige polygonale geometrische Form.
 *
 * @author Michael Andonie
 */
@API
public class Polygon extends Geometry
{
    /**
     * Die x-Koordinate der Punkte, die das Polygon beschreiben, in Meter.
     */
    private double[] px;

    /**
     * Die y-Koordinate der Punkte, die das Polygon beschreiben, in Meter.
     */
    private double[] py;

    /**
     * Die x-Koordinate der Punkte, die das Polygon beschreiben, in Pixel.
     */
    private int[] scaledPx;

    /**
     * Die y-Koordinate der Punkte, die das Polygon beschreiben, in Pixel.
     */
    private int[] scaledPy;

    /**
     * Erstellt ein neues Polygon. Seine Position ist der <b>Ursprung</b>.
     *
     * @param points Der Streckenzug an Punkten, der das Polygon beschreibt.
     *               Alle
     */
    @API
    public Polygon(Vector... points)
    {
        super(() -> FixtureBuilder.polygone(points));
        resetPoints(points);
    }

    /**
     * Setzt den Streckenzug neu, der dieses Polygon beschreibt. <b>Ändert die
     * physikalischen Eigenschaften</b> des Polygons. <i>Konkave Streckenzüge
     * werden durch die kleinste konvexe Körperform beschrieben, die den
     * Streckenzug umspannt.</i> Komplexere Formen können über
     * {@code setFixtures(Supplier)} physikalisch präzise umgesetzt werden.
     *
     * @param points Neuer Streckenzug.
     *
     * @see rocks.friedrich.engine_omega.actor.Actor#setFixtures(Supplier)
     */
    @API
    public void resetPoints(Vector... points)
    {
        if (points.length < 3)
        {
            throw new RuntimeException(
                    "Der Streckenzug muss mindestens aus 3 Punkten bestehen, um ein gültiges Polygon zu beschreiben.");
        }
        px = new double[points.length];
        py = new double[points.length];
        scaledPx = new int[points.length];
        scaledPy = new int[points.length];
        for (int i = 0; i < points.length; i++)
        {
            px[i] = points[i].getX();
            py[i] = points[i].getY();
        }
        setFixture(() -> FixtureBuilder.polygone(points));
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        for (int i = 0; i < scaledPx.length; i++)
        {
            scaledPx[i] = (int) (px[i] * pixelPerMeter);
            scaledPy[i] = (int) (py[i] * pixelPerMeter);
        }
        AffineTransform at = g.getTransform();
        g.scale(1, -1);
        g.setColor(getColor());
        g.fillPolygon(scaledPx, scaledPy, scaledPx.length);
        g.setTransform(at);
    }
}
