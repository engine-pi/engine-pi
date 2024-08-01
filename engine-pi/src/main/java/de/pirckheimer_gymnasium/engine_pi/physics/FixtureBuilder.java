/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/FixtureBuilder.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2018 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.jbox2d.collision.shapes.CircleShape;
import de.pirckheimer_gymnasium.jbox2d.collision.shapes.PolygonShape;
import de.pirckheimer_gymnasium.jbox2d.collision.shapes.Shape;
import de.pirckheimer_gymnasium.jbox2d.common.Vec2;

import de.pirckheimer_gymnasium.engine_pi.Vector;

/**
 * Sammlungen von statischen Methoden, die verschiedene Halterungen (englisch
 * Fixture) für verschieden geformte
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Actor Actor}-Objekte
 * erstellen.
 *
 * <p>
 * Halterungen werden verwendet, um die Größe, Form und Materialeigenschaften
 * eines Objekts in der Physikszene zu beschreiben.
 */
public final class FixtureBuilder
{
    /**
     * Erstellt eine <i>einfache</i> rechteckige Form. Einfach bedeutet: Sie
     * beginnt immer bei (0|0) und die Breite und die Höhe ist parallel zu den
     * Koordinatenachsen.
     *
     * @param width  Die Breite der rechteckigen Form.
     * @param height Die Höhe der rechteckigen Form.
     */
    public static FixtureData rectangle(double width, double height)
    {
        PolygonShape shape = new PolygonShape();
        shape.set(
                new Vec2[]
                { new Vec2(0, 0), new Vec2(0, (float) height),
                        new Vec2((float) width, (float) height),
                        new Vec2((float) width, 0) },
                4);
        shape.m_centroid.set(new Vec2((float) width / 2, (float) height / 2));
        return new FixtureData(shape);
    }

    /**
     * Erschafft eine kreisförmige Form.
     *
     * @param mx     Der Mittelpunkt des Kreises, X-Koordinate.
     * @param my     Der Mittelpunkt des Kreises, Y-Koordinate.
     * @param radius Der Radius des Kreises.
     */
    public static FixtureData circle(double mx, double my, double radius)
    {
        CircleShape circleShape = new CircleShape();
        circleShape.m_p.set((float) mx, (float) my);
        circleShape.setRadius((float) radius);
        return new FixtureData(circleShape);
    }

    /**
     * Erstellt eine polygonale Form. Kann nur konvexe Formen erstellen. Konkave
     * Formen werden automatisch zur umspannenden konvexen Form formatiert.
     *
     * @param points Eine Reihe an Punkten, die nacheinander diese Form
     *               beschreiben (mindestens 3 Punkte).
     */
    public static FixtureData polygon(Vector... points)
    {
        if (points.length < 3)
        {
            throw new IllegalArgumentException(
                    "Eine polygonale Shape benötigt mindestens 3 Punkte.");
        }
        Vec2[] vec2s = new Vec2[points.length];
        for (int i = 0; i < points.length; i++)
        {
            vec2s[i] = points[i].toVec2();
        }
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.set(vec2s, vec2s.length);
        return new FixtureData(polygonShape);
    }

    /**
     * Erstellt eine rechteckige Form, die parallel zu den Koordinatenachsen
     * läuft.
     *
     * @param sx     Die x-Koordinate der linken unteren Ecke.
     * @param sy     Die y-Koordinate der linken unteren Ecke.
     * @param width  Breite der rechteckigen Form.
     * @param height Höhe der rechteckigen Form.
     */
    public static Shape axisParallelRectangular(double sx, double sy,
            double width, double height)
    {
        PolygonShape rectShape = new PolygonShape();
        rectShape
                .set(new Vec2[]
                { new Vec2((float) sx, (float) sy),
                        new Vec2((float) sx, (float) (sy + height)),
                        new Vec2((float) (sx + width), (float) (sy + height)),
                        new Vec2((float) (sx + width), (float) sy) }, 4);
        return rectShape;
    }

    /**
     * Erstellt einen Supplier für Halterungen (Fixture) basierend auf einer
     * Zeichenkette.
     *
     * @param code Eine Minisprache, die die Halterung definiert. Alle Werte
     *             sind in der Einheit Meter anzugeben. Die Koordinatenangaben
     *             beziehen sich dabei auf den Ankerpunkt der Figur links unten.
     *             <ul>
     *             <li>Die Formen werden getrennt durch "&amp;"</li>
     *             <li>Rechteck: <code>R0.5,0.5,4,5</code> Rechteck mit
     *             Startpunkt (0.5|0.5), Breite 4 Meter, Höhe 5 Meter</li>
     *             <li>Polygon: <code>P4,4,5,5,1,2</code> Polygon mit drei
     *             Punkten: (4|4), (5|5), (1|2)</li>
     *             <li>Kreis: <code>C1,1,4</code> Kreis mit Mittelpunkt (1|1)
     *             und Radius 4</li>
     *             </ul>
     *
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Actor#setFixtures(String)
     */
    public static Supplier<List<FixtureData>> fromString(String code)
    {
        try (Scanner scanner = new Scanner(code.replace(" ", "")))
        {
            scanner.useDelimiter("&");
            ArrayList<FixtureData> shapeList = new ArrayList<>();
            while (scanner.hasNext())
            {
                String line = scanner.next();
                Shape shape = fromLine(line);
                shapeList.add(new FixtureData(shape));
            }
            return () -> shapeList;
        }
    }

    private static Shape fromLine(String line)
    {
        char shape = line.charAt(0);
        line = line.substring(1);
        String[] split = line.split(",");
        switch (shape)
        {
        case 'R':
            if (split.length != 4)
            {
                throw new IllegalArgumentException("Fehlerhafte Eingabe");
            }
            double sx = Double.parseDouble(split[0]);
            double sy = Double.parseDouble(split[1]);
            double width = Double.parseDouble(split[2]);
            double height = Double.parseDouble(split[3]);
            return axisParallelRectangular(sx, sy, width, height);

        case 'P':
            if (split.length % 2 != 0)
            {
                throw new IllegalArgumentException("Fehlerhafte Eingabe");
            }
            Vec2[] polyPoints = new Vec2[split.length / 2];
            for (int i = 0; i < polyPoints.length; i++)
            {
                double px = Double.parseDouble(split[2 * i]);
                double py = Double.parseDouble(split[2 * i + 1]);
                polyPoints[i] = new Vec2((float) px, (float) py);
            }
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.set(polyPoints, polyPoints.length);
            return polygonShape;

        case 'C':
            if (split.length != 3)
            {
                throw new IllegalArgumentException("Fehlerhafte Eingabe");
            }
            CircleShape circleShape = new CircleShape();
            circleShape.m_p.set(Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]));
            circleShape.setRadius(Float.parseFloat(split[2]));
            return circleShape;

        default:
            throw new IllegalArgumentException("Fehlerhafte Eingabe!");
        }
    }
}
