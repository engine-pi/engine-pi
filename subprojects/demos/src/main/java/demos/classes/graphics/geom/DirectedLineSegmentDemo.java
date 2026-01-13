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

    /**
     * Die um einen positiven Winkel gedrehte Line;
     *
     * @see DirectedLineSegment#fixedRotatedPoint(double, double)
     */
    private final Line positiveFixedRotated;

    /**
     * Die um einen positiven Winkel gedrehte Line
     *
     * @see DirectedLineSegment#fixedRotatedPoint(double, double)
     */
    private final Line negativeFixedRotated;

    private final Line positiveProportionalRotated;

    private final Line negativeProportionalRotated;

    public DirectedLineSegmentDemo()
    {
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

        positiveFixedRotated = createRotatedLine(from, to, "green");
        negativeFixedRotated = createRotatedLine(from, to, "green");
        add(positiveFixedRotated, negativeFixedRotated);

        positiveProportionalRotated = createRotatedLine(from, to, "yellow");
        negativeProportionalRotated = createRotatedLine(from, to, "yellow");
        add(positiveProportionalRotated, negativeProportionalRotated);

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

        positiveFixedRotated.point2(lineSegment.fixedRotatedPoint(-2, 45));
        negativeFixedRotated.point2(lineSegment.fixedRotatedPoint(-2, -45));

        positiveProportionalRotated
                .point2(lineSegment.proportionalRotatedPoint(-0.5, 20));
        negativeProportionalRotated
                .point2(lineSegment.proportionalRotatedPoint(-0.5, -20));

        point_0_5.center(lineSegment.proportionalPoint(0.5));
        point_2.center(lineSegment.proportionalPoint(2));
        point_minus_1.center(lineSegment.proportionalPoint(-1));

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
