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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import de.pirckheimer_gymnasium.jbox2d.collision.shapes.CircleShape;
import de.pirckheimer_gymnasium.jbox2d.collision.shapes.Shape;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureData;
import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

/**
 * Beschreibt einen <b>Knoten</b>, der zur Visualisualisierung von Listen,
 * Bäumen oder Graphen verwendet werden kann.
 *
 * @author Josef Friedrich
 */
public class LabeledNode extends Geometry
{
    /**
     * Die <b>Größe</b> des Knotens. Bei einem Kreis handelt es sich um den
     * Durchmesser.
     */
    private double size;

    /**
     * Die <b>Bezeichnung</b> des Knotens.
     */
    private String label;

    private Rectangle2D cachedFontStringBounds;

    private Font font;

    @API
    public LabeledNode()
    {
        this(null, 1);
    }

    @API
    public LabeledNode(String label)
    {
        this(label, 1);
    }

    @API
    public LabeledNode(String label, double size)
    {
        super(() -> new FixtureData(createCircleShape(size)));
        this.label = label;
        this.size = size;
        font = Resources.FONTS.get("XXXXXX");
        cachedFontStringBounds = FontUtil.getStringBounds(label, font);
        setColor("blue");
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        // in Pixel
        int nodeSize = (int) (size * pixelPerMeter);

        // Circle
        g.setColor(getColor());
        g.fillOval(0, -nodeSize, nodeSize, nodeSize);

        // label
        AffineTransform pre = g.getTransform();
        Font oldFont = g.getFont();
        g.setColor(Resources.COLORS.get("white"));
        // g.scale(cachedScaleFactor * pixelPerMeter,
        // cachedScaleFactor * pixelPerMeter);
        g.setFont(font);

        var b = cachedFontStringBounds;
        int fontPosX = (nodeSize - (int) b.getWidth()) / 2;
        // TODO getY ist Unterlänge ? Schrift jedoch zu weit oben
        int fontPoxY = (nodeSize - (int) (b.getHeight() + b.getY())) / 2;
        g.drawString(label, fontPosX, -fontPoxY);
        g.setFont(oldFont);
        g.setTransform(pre);
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
        Game.start(scene -> {
            LabeledNode node = new LabeledNode("Node");
            scene.add(node);
        });
    }
}
