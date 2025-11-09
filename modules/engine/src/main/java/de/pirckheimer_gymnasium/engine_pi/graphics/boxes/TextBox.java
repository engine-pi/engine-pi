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
package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Font;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.util.FontUtil;

/**
 * Eine einzeilige <b>Text</b>box.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
public class TextBox extends Box
{

    /**
     * @since 0.38.0
     */
    private String content;

    /**
     * @since 0.38.0
     */
    private Font font;

    /**
     * @since 0.38.0
     */
    private int width;

    /**
     * @since 0.38.0
     */
    private int height;

    /**
     * @since 0.38.0
     */
    private int baseline;

    /**
     * @since 0.38.0
     */
    public TextBox(String content, Font font)
    {
        this.content = content;
        this.font = font;
        var bounds = FontUtil.getStringBoundsNg(content, font);
        width = bounds.getWidth();
        height = bounds.getHeight();
        baseline = bounds.getBaseline();
    }

    @Override
    int width()
    {
        return width;
    }

    @Override
    int height()
    {
        return height;
    }

    @Override
    void draw(Graphics2D g)
    {
        g.setFont(font);
        g.drawString(content, x(), y() + baseline);
    }
}
