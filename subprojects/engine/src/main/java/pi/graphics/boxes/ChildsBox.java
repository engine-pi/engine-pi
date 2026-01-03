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
import java.util.function.Consumer;

import pi.annotations.Getter;
import pi.debug.ToStringFormatter;

/**
 * Eine Box, die <b>mehrere untergeordnete</b> Kinder-Boxen enthält und alle
 * Kinder-Boxen werden in eine {@link CellBox} eingebettet.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class ChildsBox<T extends Box> extends Box
{
    /**
     * @since 0.42.0
     */
    public ChildsBox(Box... childs)
    {
        measureDimensionTwice = true;
        for (Box child : childs)
        {
            addChild(child);
        }
    }

    public void addChild(Box child)
    {
        if (child != null)
        {
            CellBox container = new CellBox(child);
            this.childs.add(container);
            container.parent = this;
        }
    }

    /**
     * Wendet eine {@link Consumer}-Funktion auf alle hinzugefügten Kinder-Boxen
     * an, nicht jedoch auf die {@link CellBox}en, die die einzelnen
     * Kinder-Boxen enthalten.
     */
    @SuppressWarnings("unchecked")
    public ChildsBox<T> forEachChild(Consumer<T> consumer)
    {
        for (Box box : childs)
        {
            consumer.accept((T) box.childs.get(0));
        }
        return this;
    }

    /**
     * Wendet eine {@link Consumer}-Funktion auf alle {@link CellBox}en, die die
     * einzelnen Kinder-Boxen enthalten.
     */
    public ChildsBox<T> forEachCell(Consumer<PopulatedCell<T>> consumer)
    {
        for (Box box : childs)
        {
            consumer.accept(new PopulatedCell<T>((CellBox) box));
        }
        return this;
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
    @Getter
    public ToStringFormatter toStringFormatter()
    {
        var formatter = super.toStringFormatter();
        if (numberOfChilds() > 0)
        {
            formatter.prepend("numberOfChilds", numberOfChilds());
        }
        return formatter;
    }
}
