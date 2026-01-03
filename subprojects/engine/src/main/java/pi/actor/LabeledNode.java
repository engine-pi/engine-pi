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
package pi.actor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import pi.Game;
import pi.Resources;
import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.physics.FixtureData;
import pi.resources.font.FontStringBounds;
import pi.resources.font.FontUtil;
import de.pirckheimer_gymnasium.jbox2d.collision.shapes.CircleShape;
import de.pirckheimer_gymnasium.jbox2d.collision.shapes.Shape;

/**
 * Beschreibt einen <b>Knoten (node)</b> mit einer <b>Bezeichnung (label)</b>,
 * der zur Visualisualisierung von Listen, Bäumen oder Graphen verwendet werden
 * kann.
 *
 * <p>
 * Einige Eigenschaften dieser Knoten-Klassen haben sowohl ein statisches als
 * auch ein nicht statisches Attribut. Die statischen Attribute werden groß
 * geschrieben (z.B. {@code SIZE}), die nicht-statische bzw. Objekt-Attribute
 * klein (z.B. {@code size}). Mit Hilfe der statischen Attribute kann dann das
 * Aussehen aller Knoten auf einmal geändert werden. Zu den statischen und
 * nicht-statischen Attribut-Paar gesellt sich eine Getter-Methode, die auf die
 * Methode zurückgreift, falls das nicht-statische Attribut nicht gesetzt wird.
 * </p>
 *
 * Objekt Attribute
 *
 * label
 *
 * Statische Attribute
 *
 * <ol>
 * <li>SIZE</li>
 * <li>FONT_SIZE</li>
 * <li>FONT</li>
 * <li>COLOR</li>
 * </ol>
 *
 * @author Josef Friedrich
 */
public class LabeledNode extends Geometry
{
    /**
     * Die <b>Bezeichnung</b> des Knotens.
     */
    private String label;

    /**
     * Die <b>Größe</b> des Knotens in Meter.
     *
     * <p>
     * Standardmäßig wird der Knoten als Kreis gezeichnet. Bei einem Kreis
     * stellt {@code size} den Durchmesser dar.
     * </p>
     */
    public static double SIZE = 1;

    /**
     * Die <b>Größe</b> des Knotens in Meter.
     *
     * <p>
     * Standardmäßig wird der Knoten als Kreis gezeichnet. Bei einem Kreis
     * stellt {@code size} den Durchmesser dar.
     * </p>
     */
    private double size = 0;

    /**
     * Die <b>Schriftgröße</b> des Bezeichners in Punkten (z.B. 12pt).
     */
    public static double FONT_SIZE = 16;

    public static Font FONT = Resources.fonts.get("fonts/Cantarell-Regular.ttf")
            .deriveFont((float) FONT_SIZE);

    /**
     * Die <b>Hintergrundfarbe</b> des Knotens.
     */
    public static Color COLOR = Resources.colors.get("blue");

    private FontStringBounds cachedFontStringBounds;

    /**
     * Die Schriftart der Knotenbezeichnung.
     */
    private Font font;

    @API
    public LabeledNode()
    {
        this(null);
    }

    /**
     * @param label Die <b>Bezeichnung</b> des Knotens.
     */
    @API
    public LabeledNode(String label)
    {
        this(label, SIZE, 0, 0);
    }

    /**
     * @param label Die <b>Bezeichnung</b> des Knotens.
     * @param x Die <b>x-Koordinate des Mittelpunkts</b> (nicht die linke untere
     *     Ecke).
     * @param y Die <b>x-Koordinate des Mittelpunkts</b> (nicht die linke untere
     *     Ecke).
     */
    @API
    public LabeledNode(String label, double x, double y)
    {
        this(label, SIZE, x, y);
    }

    /**
     * @param label Die <b>Bezeichnung</b> des Knotens.
     * @param size Die <b>Größe</b> des Knotens in Meter.
     * @param x Die <b>x-Koordinate des Mittelpunkts</b> (nicht die linke untere
     *     Ecke).
     * @param y Die <b>x-Koordinate des Mittelpunkts</b> (nicht die linke untere
     *     Ecke).
     */
    @API
    public LabeledNode(String label, double size, double x, double y)
    {
        super(() -> new FixtureData(createCircleShape(size)));
        this.label = label;
        this.size = size;
        updateLabel();
        // Damit zuerst auf das statische Attribut COLOR zurückgegriffen wird.
        color = null;
        center(x, y);
    }

    /**
     * Diese Hilfsmethode muss jedesmal ausgeführt werden, wenn die Bezeichnung
     * des Knotens geändert wird.
     */
    private void updateLabel()
    {
        if (label != null)
        {
            cachedFontStringBounds = FontUtil.getStringBounds(label, font());
        }
    }

    @Setter
    public void label(String label)
    {
        this.label = label;
        updateLabel();
    }

    /**
     * Gibt die <b>Größe</b> des Knotens in Meter zurück.
     *
     * <p>
     * Standardmäßig wird der Knoten als Kreis gezeichnet. Bei einem Kreis
     * stellt {@code size} den Durchmesser dar.
     * </p>
     *
     * @return Die <b>Größe</b> des Knotens in Meter.
     */
    @Getter
    private double size()
    {
        if (size == 0)
        {
            return SIZE;
        }
        return size;
    }

    /**
     * Setzt die <b>Schriftgröße</b> des Bezeichners in Punkten (z.B. 12pt).
     *
     * @param fontSize Die <b>Schriftgröße</b> des Bezeichners in Punkten (z.B.
     *     12pt).
     */
    @Setter
    public void fontSize(double fontSize)
    {
        font(font().deriveFont((float) fontSize));
    }

    /**
     * Setzt die <b>Schriftart</b> der Knotenbezeichnung.
     *
     * @param font Die <b>Schriftart</b> der Knotenbezeichnung.
     */
    @Setter
    public void font(Font font)
    {
        this.font = font;
    }

    /**
     * Gibt die <b>Schriftart</b> der Knotenbezeichnung zurück.
     *
     * @return Die <b>Schriftart</b> der Knotenbezeichnung.
     */
    @Getter
    private Font font()
    {
        if (font == null)
        {
            return FONT;
        }
        return font;
    }

    /**
     * Gibt die <b>Hintergrundfarbe</b> des Knotens zurück.
     *
     * @return Die <b>Hintergrundfarbe</b> des Knotens
     */
    public Color color()
    {
        if (color == null)
        {
            return COLOR;
        }
        return color;
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        // Wir rechnen in float damit der Text nicht springt beim raus- und
        // reinzoomen.
        // Text kann auch an float-Koordinaten eingezeichnet werden. Nicht
        // sicher ob das was bringt?
        // Die Größe des Knotens in Pixel
        float nodeSize = (float) (size() * pixelPerMeter);

        // Die x-Koordinate der linken oberen Ecke.
        float upperLeftX = 0;
        // Die y-Koordinate der linken oberen Ecke. Wir nehmen hier {@code
        // -nodeSize},
        // damit der Anker des Knotens dann links unten auf {@code (0|0)} steht.
        float upperLeftY = -nodeSize;

        // Kreis
        g.setColor(color());
        g.fillOval((int) upperLeftX, (int) upperLeftY, (int) nodeSize,
                (int) nodeSize);

        // Bezeichnung
        if (label != null)
        {
            AffineTransform pre = g.getTransform();
            Font oldFont = g.getFont();
            g.setColor(Resources.colors.get("white"));
            g.setFont(font());
            var b = cachedFontStringBounds;

            // Der obere Abstand des Schriftrahmen zum Knotenrahmen.
            float topMargin = (nodeSize - b.height) / 2;
            // Der linke Abstand des Schriftrahmen zum Knotenrahmen.
            float leftMargin = (nodeSize - b.width) / 2;

            // Im Debug-Modus wird der Textrahmen um die Knotenbezeichnung
            // eingezeichnet.
            if (Game.isDebug())
            {
                g.drawRect((int) (upperLeftX + leftMargin),
                        (int) (upperLeftY + topMargin), b.width, b.height);
            }
            g.drawString(label, upperLeftX + leftMargin,
                    upperLeftY + topMargin + b.baseline);
            g.setFont(oldFont);
            g.setTransform(pre);
        }
    }

    /**
     * @hidden
     */
    @Internal
    private static Shape createCircleShape(double diameter)
    {
        CircleShape shape = new CircleShape();
        shape.radius = (float) diameter / 2;
        shape.p.set(shape.radius, shape.radius);
        return shape;
    }

    public static void main(String[] args)
    {
        Game.start(s -> {
            LabeledNode n1, n2, n3;
            LabeledNode.SIZE = 2;
            n1 = new LabeledNode("Node 1");
            s.add(n1);

            LabeledNode.COLOR = Resources.colors.get("orange");
            n2 = new LabeledNode("Node 2", 5, 5);
            n2.color("green");
            s.add(n2);

            n3 = new LabeledNode("Node 3", 5, -5);
            s.add(n3);
        });
    }
}
