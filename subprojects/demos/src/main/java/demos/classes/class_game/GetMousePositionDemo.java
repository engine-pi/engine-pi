/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package demos.classes.class_game;

import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.actor.Text;
import pi.event.FrameUpdateListener;
import pi.util.TextUtil;

/**
 * Demonstriert die Methode {@link Game#getMousePosition()}.
 */
public class GetMousePositionDemo extends Scene implements FrameUpdateListener
{
    private final Text x;

    private final Text y;

    public GetMousePositionDemo()
    {
        addText("x:").setPosition(-3, 0);
        x = addText("");
        x.setPosition(-2, 0);
        addText("y:").setPosition(1, 0);
        y = addText("");
        y.setPosition(2, 0);
        setBackgroundColor("white");
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector position = Game.getMousePosition();
        x.setContent(TextUtil.roundNumber(position.getX()));
        y.setContent(TextUtil.roundNumber(position.getY()));
    }

    public static void main(String[] args)
    {
        Game.start(new GetMousePositionDemo());
    }
}
