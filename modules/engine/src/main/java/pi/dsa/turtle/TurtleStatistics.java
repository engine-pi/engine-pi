/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.dsa.turtle;

import static pi.Resources.colors;

import java.awt.Graphics2D;

import pi.Resources;
import pi.Turtle;
import pi.graphics.boxes.FramedBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.TextTableBox;
import pi.resources.FontStyle;
import pi.util.TextUtil;

/**
 * Sammelt statistische Information, wie zum Beispiel die zurückgelegte
 * Wegstrecker der Schilkröte und erstellt Textboxen, die dann in die Szene
 * eingezeichnet werden können.
 *
 * @author Josef Friedrich
 *
 * @since 0.40.0
 */
public class TurtleStatistics
{
    /**
     * Die zurückgelegte Distanz der Schildkröte in Meter.
     *
     * @since 0.40.0
     */
    private double traveledDistance;

    private final TextTableBox table;

    private final FramedBox framedTable;

    private final TurtlePenController pen;

    /**
     * @since 0.40.0
     */
    public TurtleStatistics(TurtlePenController pen)
    {
        this.pen = pen;
        traveledDistance = 0;
        table = new TextTableBox("Entfernung:",
                TextUtil.roundNumber(traveledDistance), "aktuelle Ausrichtung:",
                "000.00000");
        table.allCells(cell -> {
            cell.text.color("black");
            cell.container.hAlign(HAlign.RIGHT);
        });
        table.padding(3);
        table.column(0, cell -> cell.text.font(Resources.fonts
                .getDefault(FontStyle.BOLD).deriveFont((float) 16)));
        framedTable = new FramedBox(table);
        framedTable.background.color(colors.get("grey", 50));
        framedTable.padding.allSides(5);
        framedTable.anchor(20, 20);
    }

    /**
     * Addierte eine Entfernung in Meter zu der bereits zurückgelegten Strecke
     * der Schildkröte.
     *
     * @param distance Eine Entfernung in Meter
     *
     * @since 0.40.0
     */
    public void addTraveledDistance(double distance)
    {
        traveledDistance += distance;
    }

    /**
     * @since 0.40.0
     */
    public void render(Graphics2D g)
    {
        table.cell(0, 1, b -> b.text
                .content(TextUtil.roundNumber(traveledDistance) + " m"));
        table.cell(1, 1, b -> b.text
                .content(TextUtil.roundNumber(pen.direction) + " °"));
        framedTable.measure();
        framedTable.render(g);
    }

    public static void main(String[] args)
    {
        Turtle turtle = new Turtle();
        for (int i = 0; i < 3; i++)
        {
            turtle.lowerPen();
            turtle.forward(3);
            turtle.left(120);
        }
    }
}
