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

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * Eine Box, die <b>mehrere untergeordnete</b> Boxen enthält.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
abstract class MultipleChildBoxContainer extends Box
{
    /**
     * @since 0.38.0
     */
    protected ArrayList<Box> childs;

    /**
     * @since 0.38.0
     */
    public MultipleChildBoxContainer(Box... childs)
    {
        this.childs = new ArrayList<Box>();
        for (Box child : childs)
        {
            this.childs.add(child);
            child.parent = this;
        }
    }

    @Override
    void calculateAnchors()
    {
        for (Box child : childs)
        {
            child.calculateAnchors();
        }
    }

    @Override
    void draw(Graphics2D g)
    {
        for (Box child : childs)
        {
            child.draw(g);
        }
    }
}
