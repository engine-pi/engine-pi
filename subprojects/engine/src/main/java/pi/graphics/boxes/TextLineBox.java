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
package pi.graphics.boxes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import pi.resources.font.FontUtil;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/TextLineBoxDemo.java

/**
 * Eine einzeilige <b>Text</b>box.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class TextLineBox extends TextBox
{
    /**
     * @since 0.38.0
     */
    private int baseline;

    /**
     * Erzeugt eine <b>Text</b>box.
     *
     * @param content Der <b>Inhalt</b> der Textbox als Zeichenkette.
     *
     * @since 0.39.0
     */
    public TextLineBox(Object content)
    {
        super(content);
    }

    protected void calculateDimension()
    {
        var bounds = FontUtil.getStringBounds(content, font);
        width = bounds.getWidth();
        height = bounds.getHeight();
        baseline = bounds.getBaseline();
    }

    @Override
    void draw(Graphics2D g)
    {
        Color oldColor = null;
        Font oldFont = g.getFont();
        if (color != null)
        {
            oldColor = g.getColor();
            g.setColor(color);
        }
        g.setFont(font);
        g.drawString(content, x, y + baseline);
        g.setColor(oldColor);
        g.setFont(oldFont);
    }

    @Override
    public String toString()
    {
        return getToStringFormatter().format();
    }
}
