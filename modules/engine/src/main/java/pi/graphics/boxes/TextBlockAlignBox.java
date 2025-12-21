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

public class TextBlockAlignBox extends CombinedChildBox
{
    public ContainerBox container;

    public TextBlockBox text;

    public TextBlockAlignBox()
    {
        this("");
    }

    public TextBlockAlignBox(Object content)
    {
        text = new TextBlockBox(content);
        container = new ContainerBox(text);
        addChild(container);
        supportsDefinedDimension = true;
    }

    public int innerWidth()
    {
        return text.width;
    }

    public int innerHeight()
    {
        return text.height;
    }

    public TextBlockAlignBox width(int width)
    {
        definedWidth = width;
        container.width(width);
        return this;
    }

    public TextBlockAlignBox height(int height)
    {
        definedHeight = height;
        container.height(height);
        return this;
    }

    public TextBlockAlignBox hAlign(HAlign hAlign)
    {
        container.hAlign(hAlign);
        text.hAlign(hAlign);
        return this;
    }

    public TextBlockAlignBox vAlign(VAlign vAlign)
    {
        container.vAlign(vAlign);
        return this;
    }

    @Override
    public String toString()
    {
        var formatter = getToStringFormatter();
        return formatter.format();
    }
}
