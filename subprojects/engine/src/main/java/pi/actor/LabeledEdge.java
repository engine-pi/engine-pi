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
package pi.actor;

import static pi.Controller.colorScheme;
import static pi.Controller.fonts;

import java.awt.Font;

import pi.Controller;
import pi.Vector;
import pi.annotations.Setter;

/**
 * Eine beschriftete Kante
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class LabeledEdge extends Line
{
    private Vector from;

    private Vector to;

    /**
     * Der Bezeichner der Kante (z.B. das Kantengewicht).
     */
    private String label;

    /**
     * Der Abstand der Kantenbezeichnung zur Kantenlinie in Meter.
     */
    public static double LABEL_LINE_DISTANCE = 0.25;

    /**
     * Die <b>Schriftgröße</b> des Bezeichners in Punkten (z.B. 12pt).
     */
    public static double FONT_SIZE = 12;

    public static Font FONT = fonts.get("fonts/Cantarell-Regular.ttf")
            .deriveFont((float) FONT_SIZE);

    public LabeledEdge(double fromX, double fromY, double toX, double toY,
            String label)
    {
        this(new Vector(fromX, fromY), new Vector(toX, toY), label);
    }

    public LabeledEdge(double fromX, double fromY, double toX, double toY)
    {
        this(fromX, fromY, toX, toY, null);
    }

    public LabeledEdge(Vector from, Vector to)
    {
        this(from, to, null);
    }

    public LabeledEdge(Vector from, Vector to, String label)
    {
        super(from, to);
        end1.arrow(ArrowType.CHEVERON);
        end2.arrow(ArrowType.CHEVERON);
        offset(1);
        strokeWidth(0.05);
        this.label = label;
        color(colorScheme.get().gray());
    }

    @Setter
    public void label(String label)
    {
        this.label = label;
    }

    /**
     * @hidden
     */

    // @Internal
    // @Override
    // public void render(Graphics2D g, double pixelPerMeter)
    // {
    // int fromX = from.x(pixelPerMeter);
    // int fromY = from.y(pixelPerMeter) * -1;
    // // In Pixel mit umgedrehter y-Richtung
    // Vector fromPx = new Vector(fromX, fromY);

    // int toX = to.x(pixelPerMeter);
    // int toY = to.y(pixelPerMeter) * -1;
    // // In Pixel mit umgedrehter y-Richtung
    // Vector toPx = new Vector(toX, toY);

    // Stroke oldStroke = g.getStroke();
    // Stroke stroke = new BasicStroke(2, BasicStroke.CAP_ROUND,
    // BasicStroke.JOIN_MITER);
    // g.setStroke(stroke);
    // g.setColor(color());

    // if (label != null)
    // {
    // AffineTransform affineTransform = new AffineTransform();
    // FontStringBounds labelBounds = FontUtil.getStringBounds(label,
    // FONT);

    // // Der Differenzvektor
    // Vector edge = toPx.subtract(fromPx);

    // double edgeLength = edge.length();
    // double labelMargin = (edgeLength - labelBounds.getWidth()) / 2;

    // Vector labelLineDistance = Vector.ofAngle(edge.angle() - 90)
    // .multiply(LABEL_LINE_DISTANCE * pixelPerMeter);

    // Vector labelAnchor = fromPx
    // .add(edge.multiply(labelMargin / edgeLength))
    // .add(labelLineDistance);
    // affineTransform.rotate(edge.radians(), 0, 0);
    // Font rotatedFont = FONT.deriveFont(affineTransform);
    // g.setFont(rotatedFont);
    // g.drawString(label, (int) labelAnchor.x(), (int) labelAnchor.y());
    // }
    // g.drawLine(fromX, fromY, toX, toY);
    // g.setStroke(oldStroke);
    // }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.debug();
        Controller.start((scene) -> {
            LabeledEdge e1, e2;
            e1 = new LabeledEdge(1, 1, 4, 5, "label");

            e2 = new LabeledEdge(-1, -1, -4, -5,
                    "e2: A very, very, very long label");
            scene.add(e1, e2);
        });
    }
}
