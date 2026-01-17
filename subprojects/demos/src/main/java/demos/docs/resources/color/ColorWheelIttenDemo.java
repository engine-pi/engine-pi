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
package demos.docs.resources.color;

import static pi.Controller.colorScheme;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Rectangle;
import pi.Scene;
import pi.Text;
import pi.actor.Actor;
import pi.actor.Polygon;
import pi.event.KeyStrokeListener;
import pi.graphics.geom.Vector;
import pi.resources.color.ColorScheme;

/**
 * Demonstiert die Farbschemata, die die Engine Pi mitliefert anhand des
 * <b>Farbkreises von Itten</b>.
 *
 * <p>
 * Folgende Grafik von Wikicommons diente als Vorlage für dieses Demo:
 * </p>
 *
 * <p>
 * <img src=
 * "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Farbkreis_Itten_1961.svg/500px-Farbkreis_Itten_1961.svg.png"
 * alt="Farbkreis Itten 1961">
 * </p>
 *
 * @author Josef Friedrich
 */
public class ColorWheelIttenDemo extends Scene implements KeyStrokeListener
{
    private final int NUMBER_SEGMENTS = 12;

    private final double SEGMENT_ANGLE = 360.0 / NUMBER_SEGMENTS;

    private final double INNER_RADIUS = 5.0;

    /**
     * Die farbigen Segmente in der Form eines Trapezes mit einer der zwölf
     * Farben des Farbkreises von Itten. Zwölf Segmente ergeben einen Kreis.
     *
     * @see Polygon
     */
    private final Actor[] WHEEL_AREAS;

    /**
     * @see Polygon
     */
    private final Actor[] PRIMARY_AREAS;

    /**
     * @see Polygon
     */
    private final Actor[] SECONDARY_AREAS;

    /**
     * @see Rectangle
     */
    private final Actor[] EXTRA_AREAS;

    /**
     * Der Name des Farbschemas
     */
    private final Text NAME;

    public ColorWheelIttenDemo()
    {
        info().title("Farbschemata-Demo")
            .description(
                "Demonstiert die Farbschemata, die die Engine Pi mitliefert anhand des Farbkreises von Itten.")
            .help(
                "Ein beliebiger Tastendruck schaltet zum nächsten Farbschema weiter.");
        WHEEL_AREAS = drawWheelColors();
        // Zuerst Primär, denn die müssen übermalt werden.
        PRIMARY_AREAS = drawPrimaryColors();
        SECONDARY_AREAS = drawSecondaryColors();
        EXTRA_AREAS = drawExtraColors();
        NAME = new Text("");
        NAME.anchor(-8, 7);
        NAME.color("white");
        add(NAME);
        setNextColorScheme();
        backgroundColor("#444444");
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
     * Zeichnet alle zwölf Farben des <b>Farbkreises</b>.
     *
     * @see Polygon
     */
    private Actor[] drawWheelColors()
    {
        Actor[] areas = new Actor[NUMBER_SEGMENTS];
        for (int i = 0; i < NUMBER_SEGMENTS; i++)
        {
            double angle = (i * SEGMENT_ANGLE * -1) + 90;
            Vector textPosition = getCirclePoint(7.5, angle);
            add(new Text(i + "").height(0.5)
                .anchor(textPosition.x(), textPosition.y())
                .color("weiß"));
            areas[i] = createWheelArea(i, angle);
        }
        return areas;
    }

    /**
     * Zeichnet die drei <b>Sekundärfarben</b>.
     *
     * @see Polygon
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
     * Zeichnet die drei <b>Pimärfarben</b>.
     *
     * @see Polygon
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
     *
     * @see Rectangle
     */
    private Actor[] drawExtraColors()
    {
        Actor[] areas = new Actor[4];
        for (int i = 0; i < 4; i++)
        {
            Rectangle rectange = new Rectangle(1, 1);
            rectange.anchor(-8 + i, -8);
            areas[i] = rectange;
            add(rectange);
        }
        return areas;
    }

    private void setColorScheme(ColorScheme scheme)
    {
        NAME.content(scheme.name());
        int i = 0;
        for (Color color : scheme.wheelColors())
        {
            WHEEL_AREAS[i].color(color);
            i++;
        }
        i = 0;
        for (Color color : scheme.primaryColors())
        {
            PRIMARY_AREAS[i].color(color);
            i++;
        }
        i = 0;
        for (Color color : scheme.secondaryColors())
        {
            SECONDARY_AREAS[i].color(color);
            i++;
        }
        i = 0;
        for (Color color : scheme.extraColors())
        {
            EXTRA_AREAS[i].color(color);
            i++;
        }
    }

    private void setNextColorScheme()
    {
        setColorScheme(colorScheme.next());
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        setNextColorScheme();
    }

    public void cycle()
    {
        repeat(1, () -> {
            setNextColorScheme();
        });
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        ColorWheelIttenDemo demo = new ColorWheelIttenDemo();
        Controller.start(demo, 520, 520);
    }
}
