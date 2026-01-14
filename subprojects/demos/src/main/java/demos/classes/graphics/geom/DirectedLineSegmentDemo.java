/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025, 2026 Josef Friedrich and contributors.
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
package demos.classes.graphics.geom;

import java.awt.Graphics2D;

import pi.Circle;
import pi.Controller;
import pi.Scene;
import pi.actor.Line;
import pi.event.FrameUpdateListener;
import pi.graphics.boxes.TextTableBox;
import pi.graphics.geom.DirectedLineSegment;
import pi.graphics.geom.Vector;
import pi.util.TextUtil;

/**
 * Demonstiert die Klasse {@link DirectedLineSegment}.
 */
public class DirectedLineSegmentDemo extends Scene
        implements FrameUpdateListener
{
    /**
     * Einen Linie mit Pfeil und die gerichtete Strecke zu verdeutlichen.
     */
    private final Line arrowedLine;

    /**
     * Ein sehr lange Linie, die über das Spielfenster hinausragt. Sie soll die
     * Strecke verlängern.
     */
    private final Line longLine;

    private final DirectedLineSegment lineSegment;

    /**
     * Zeigt die aktuellen Attribute der Klasse {@link DirectedLineSegment} als
     * Tabelle an.
     */
    private final TextTableBox table;

    /**
     * Ein Punkt in der Entfernung {@code 0.5} vom Ursprung.
     */
    private final Circle point_0_5;

    /**
     * Ein Punkt in der Entfernung {@code 2} vom Ursprung.
     */
    private final Circle point_2;

    /**
     * Ein Punkt in der Entfernung {@code -1} vom Ursprung.
     */
    private final Circle point_minus_1;

    /* Rotated */

    /* relative */

    /**
     * @see DirectedLineSegment#relativeRotatedPoint(double, double)
     */
    private final Line plusRelativeRotated;

    /**
     * @see DirectedLineSegment#relativeRotatedPoint(double, double)
     */
    private final Line minusRelativeRotated;

    /* fixed */

    /**
     * Die um einen positiven Winkel gedrehte Line;
     *
     * @see DirectedLineSegment#fixedRotatedPoint(double, double)
     */
    private final Line plusFixedRotated;

    /**
     * Die um einen negativen Winkel gedrehte Line;
     *
     * @see DirectedLineSegment#fixedRotatedPoint(double, double)
     */
    private final Line minusFixedRotated;

    /* Vertical */

    /* relative */

    /**
     * @see DirectedLineSegment#relativeVerticalPoint(double, double)
     */
    private final Line plusRelativeVertical;

    /**
     * @see DirectedLineSegment#relativeVerticalPoint(double, double)
     */
    private final Line minusRelativeVertical;

    /* fixed */

    /**
     * @see DirectedLineSegment#fixedVerticalPoint(double, double)
     */
    private final Line plusFixedVertical;

    /**
     * @see DirectedLineSegment#fixedVerticalPoint(double, double)
     */
    private final Line minusFixedVertical;

    public DirectedLineSegmentDemo()
    {
        info().title("Demonstriert die Klasse DirectedLineSegment");
        Vector from = new Vector(0, 0);
        Vector to = new Vector(1, 1);

        lineSegment = new DirectedLineSegment(from, to);

        // Zuerst die lange Line, damit sie überdeckt wird von der gerichteten
        // Strecke.
        longLine = new Line(from, to);
        longLine.strokeWidth(0.04).dashed(true).color("gray");
        add(longLine);

        point_0_5 = createPoint();
        point_2 = createPoint();
        point_minus_1 = createPoint();

        add(point_0_5, point_2, point_minus_1);

        arrowedLine = new Line(from, to);
        arrowedLine.end2.arrow(true).arrowSideLength(0.5);
        add(arrowedLine);

        plusFixedRotated = createRotatedLine(from, to, "green");
        minusFixedRotated = createRotatedLine(from, to, "green");
        add(plusFixedRotated, minusFixedRotated);

        plusRelativeRotated = createRotatedLine(from, to, "yellow");
        minusRelativeRotated = createRotatedLine(from, to, "yellow");
        add(plusRelativeRotated, minusRelativeRotated);

        plusRelativeVertical = createRotatedLine(from, to, "brown");
        minusRelativeVertical = createRotatedLine(from, to, "brown");
        add(plusRelativeVertical, minusRelativeVertical);

        plusFixedVertical = createRotatedLine(from, to, "red");
        minusFixedVertical = createRotatedLine(from, to, "red");
        add(plusFixedVertical, minusFixedVertical);

        table = new TextTableBox("length():", "0.0", "angle():", "0.0", "to():",
                "(0|0)");
        table.padding(3);
        syncPoints(to);
    }

    private Circle createPoint()
    {
        return new Circle(0.3);
    }

    private Line createRotatedLine(Vector from, Vector to, String color)
    {
        Line line = new Line(from, to);
        line.color(color);
        line.strokeWidth(0.02);
        return line;
    }

    private void syncPoints(Vector to)
    {
        lineSegment.to(to);
        arrowedLine.point2(to);
        longLine.point1(lineSegment.fixedPoint(-20));
        longLine.point2(lineSegment.fixedPoint(20));

        // Fixed rotated
        plusFixedRotated.point2(lineSegment.fixedRotatedPoint(-2, 45));
        minusFixedRotated.point2(lineSegment.fixedRotatedPoint(-2, -45));

        // Proportional rotated
        plusRelativeRotated.point2(lineSegment.relativeRotatedPoint(-0.5, 20));
        minusRelativeRotated
                .point2(lineSegment.relativeRotatedPoint(-0.5, -20));

        plusFixedVertical.point1(lineSegment.fixedPoint(1));
        plusFixedVertical.point2(lineSegment.fixedVerticalPoint(1, 1));
        minusFixedVertical.point1(lineSegment.fixedPoint(-2));
        minusFixedVertical.point2(lineSegment.fixedVerticalPoint(-2, -2));

        plusRelativeVertical.point1(lineSegment.relativePoint(0.75));
        plusRelativeVertical
                .point2(lineSegment.relativeVerticalPoint(0.75, 0.25));
        minusRelativeVertical.point1(lineSegment.relativePoint(-0.5));
        minusRelativeVertical
                .point2(lineSegment.relativeVerticalPoint(-0.5, -0.1));

        point_0_5.center(lineSegment.relativePoint(0.5));
        point_2.center(lineSegment.relativePoint(2));
        point_minus_1.center(lineSegment.relativePoint(-1));

        table.forBox(0, 1, cell -> cell.box
                .content(TextUtil.roundNumber(lineSegment.length(), 2) + " m"));
        table.forBox(1, 1, cell -> cell.box
                .content(TextUtil.roundNumber(lineSegment.angle(), 2) + " °"));
        table.forBox(2, 1, cell -> cell.box.content(lineSegment.to().format()));
    }

    @Override
    public void renderOverlay(Graphics2D g, int width, int height)
    {
        table.render(g);
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        syncPoints(mousePosition());
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DirectedLineSegmentDemo());
    }
}
