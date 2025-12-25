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
package demos.classes.resources;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.actor.Actor;
import pi.actor.Polygon;
import pi.actor.Text;
import pi.event.KeyStrokeListener;
import pi.resources.ColorSchemeSelection;

/**
 * https://commons.wikimedia.org/wiki/File:Farbkreis_Itten_1961.svg
 */
public class ColorWheelIttenDemo extends Scene implements KeyStrokeListener
{
    private final int NUMBER_SEGMENTS = 12;

    private final double SEGMENT_ANGLE = 360.0 / NUMBER_SEGMENTS;

    private final double INNER_RADIUS = 5.0;

    private final Actor[] WHEEL_AREAS;

    private final Actor[] PRIMARY_AREAS;

    private final Actor[] SECONDARY_AREAS;

    private final Actor[] EXTRA_AREAS;

    private final ColorSchemeSelection[] COLOR_SCHEMES = ColorSchemeSelection
            .values();

    private int currentColorScheme = -1;

    private final Text NAME;

    public ColorWheelIttenDemo()
    {
        WHEEL_AREAS = drawWheelColors();
        // Zuerst Primär, denn die müssen übermalt werden.
        PRIMARY_AREAS = drawPrimaryColors();
        SECONDARY_AREAS = drawSecondaryColors();
        EXTRA_AREAS = drawExtraColors();
        NAME = addText("");
        NAME.setPosition(-8, 7);
        NAME.setColor("white");
        setNextColorScheme();
        setBackgroundColor("#444444");
    }

    /**
     * Berechnet einen Punkt auf der Kreislinie.
     *
     * @param radius Der Radius des Kreises.
     * @param angle Der Winkel, der die Lage des Punktes angibt (0 = rechts, 90
     *     = oben, 180 = links, 270 = unten).
     *
     * @return Ein Punkt, der auf der Kreislinie liegt.
     *
     */
    private Vector getCirclePoint(double radius, double angle)
    {
        return Vector.ofAngle(angle).multiply(radius);
    }

    /**
     * Ein farbiges Segment in der Form eines Trapezes mit einer der zwölf
     * Farben des Farbkreises von Itten. Zwölf Segmente ergeben einen Kreis.
     *
     * @param index Der Farbindex. 0 = gelb
     * @param angle Der Winkel deutet auf die Mitte des Segments.
     */
    private Actor createWheelArea(int index, double angle)
    {
        // Erster Winkel
        double HALF_SEGMENT_ANGLE = SEGMENT_ANGLE / 2.0;
        double start = angle - HALF_SEGMENT_ANGLE;
        // Zweiter Winkel
        double end = angle + HALF_SEGMENT_ANGLE;
        double OUTER_RADIUS = 7.0;
        Polygon polygon = new Polygon(getCirclePoint(OUTER_RADIUS, start),
                getCirclePoint(INNER_RADIUS, start),
                getCirclePoint(INNER_RADIUS, end),
                getCirclePoint(OUTER_RADIUS, end));
        add(polygon);
        return polygon;
    }

    /**
     * Zeichnet alle zwölf Farben des Farbkreises.
     */
    private Actor[] drawWheelColors()
    {
        Actor[] areas = new Actor[NUMBER_SEGMENTS];
        for (int i = 0; i < NUMBER_SEGMENTS; i++)
        {
            double angle = (i * SEGMENT_ANGLE * -1) + 90;
            Vector textPosition = getCirclePoint(7.5, angle);
            addText(i + "", 0.5)
                    .setPosition(textPosition.getX(), textPosition.getY())
                    .setColor("weiß");
            areas[i] = createWheelArea(i, angle);
        }
        return areas;
    }

    /**
     * Zeichnet die drei Sekundärfarben.
     */
    private Actor[] drawSecondaryColors()
    {
        Actor[] areas = new Actor[3];
        // 90 Grad ist oben
        int START_ANGLE = 90;
        // 0, 4, 8 -> erste Ecke des Dreiecks
        for (int i = 0; i < NUMBER_SEGMENTS; i += 4)
        {
            double radius = INNER_RADIUS - 0.2;
            int angle = START_ANGLE - (i * 30);
            // Zeichnen des Dreiecks
            Polygon area = new Polygon(getCirclePoint(radius, angle),
                    getCirclePoint(radius, angle - 60),
                    getCirclePoint(radius, angle - 120));
            add(area);
            areas[i / 4] = area;
        }
        return areas;
    }

    /**
     * Zeichnet die drei Pimärfarben.
     */
    private Actor[] drawPrimaryColors()
    {
        Actor[] areas = new Actor[3];
        // 90 Grad ist oben
        int START_ANGLE = 90;
        // 0, 4, 8 -> Spitze
        for (int i = 0; i < NUMBER_SEGMENTS; i += 4)
        {
            double radius = INNER_RADIUS - 0.2;
            int angle = START_ANGLE - (i * 30);
            // Zeichnen eines Vierecks
            Polygon area = new Polygon(getCirclePoint(radius, angle + 60),
                    getCirclePoint(radius, angle),
                    getCirclePoint(radius, angle - 60), new Vector(0, 0));
            add(area);
            areas[i / 4] = area;
        }
        return areas;
    }

    /**
     * Zeichnet die vier <b>zusätzlichen</b> Farben: Braun, Weiß, Grau und
     * Schwarz.
     */
    private Actor[] drawExtraColors()
    {
        Actor[] areas = new Actor[4];
        for (int i = 0; i < 4; i++)
        {
            areas[i] = addRectangle(1, 1);
            areas[i].setPosition(-8 + i, -8);
        }
        return areas;
    }

    private ColorSchemeSelection getNextColorScheme()
    {
        currentColorScheme++;
        if (currentColorScheme >= COLOR_SCHEMES.length)
        {
            currentColorScheme = 0;
        }
        return COLOR_SCHEMES[currentColorScheme];
    }

    private void setColorScheme(ColorSchemeSelection selection)
    {
        NAME.setContent(selection.name());
        var scheme = selection.getScheme();
        int i = 0;
        for (Color color : scheme.getWheelColors())
        {
            WHEEL_AREAS[i].setColor(color);
            i++;
        }
        i = 0;
        for (Color color : scheme.getPrimaryColors())
        {
            PRIMARY_AREAS[i].setColor(color);
            i++;
        }
        i = 0;
        for (Color color : scheme.getSecondaryColors())
        {
            SECONDARY_AREAS[i].setColor(color);
            i++;
        }
        i = 0;
        for (Color color : scheme.getExtraColors())
        {
            EXTRA_AREAS[i].setColor(color);
            i++;
        }
    }

    private void setNextColorScheme()
    {
        setColorScheme(getNextColorScheme());
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        setNextColorScheme();
    }

    public static void main(String[] args)
    {
        Game.start(new ColorWheelIttenDemo(), 520, 520);
    }
}
