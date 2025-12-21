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

import java.awt.Graphics2D;

import pi.debug.ToStringFormatter;

/**
 * Eine Box, die <b>mehrere untergeordnete</b> Kinder-Boxen enthält.
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
abstract class ChildsBox extends Box
{
    /**
     * @since 0.38.0
     */
    public ChildsBox(Box... childs)
    {
        for (Box child : childs)
        {
            addChild(child);
        }
    }

    public void addChild(Box child)
    {
        if (child != null)
        {
            this.childs.add(child);
            child.parent = this;
        }
    }

    public int numberOfChilds()
    {
        return childs.size();
    }

    @Override
    protected void calculateDimension()
    {
    }

    @Override
    protected void calculateAnchors()
    {
    }

    @Override
    void draw(Graphics2D g)
    {
        // do nothing
    }

    @Override
    public ToStringFormatter getToStringFormatter()
    {
        var formatter = super.getToStringFormatter();
        if (numberOfChilds() > 0)
        {
            formatter.prepend("numberOfChilds", numberOfChilds());
        }
        return formatter;
    }
}
