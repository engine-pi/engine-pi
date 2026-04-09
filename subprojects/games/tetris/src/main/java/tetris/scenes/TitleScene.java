/*
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
package tetris.scenes;

import static tetris.Tetris.COLOR_SCHEME_GREEN;

import java.awt.event.KeyEvent;

import pi.actor.ImageText;
import pi.event.KeyStrokeListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;
import pi.graphics.boxes.HAlign;
import pi.graphics.geom.Vector;
import tetris.Tetris;
import tetris.text.Font;

/**
 * @author Josef Friedrich
 */
public class TitleScene extends BaseScene
        implements KeyStrokeListener, MouseClickListener
{
    public TitleScene()
    {
        super("title_blank");
        Sound.playTitle();
        addCenteredText("press any key", 3);
        addCenteredText("©2024 PGN-DEVS", 1);
    }

    private void addCenteredText(String content, int y)
    {
        ImageText actor = new ImageText(Font.getFont()).content(content)
            .lineWidth(20)
            .hAlign(HAlign.CENTER)
            .color(COLOR_SCHEME_GREEN.getBlack());
        actor.anchor(-2, y);
        add(actor);
    }

    private void startIngameScene()
    {
        Tetris.start(new IngameScene());
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        startIngameScene();
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        startIngameScene();
    }

    public static void main(String[] args)
    {
        Tetris.start(new TitleScene());
    }
}
