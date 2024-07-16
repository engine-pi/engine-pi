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
package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFont;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontCaseSensitivity;
import de.pirckheimer_gymnasium.engine_pi.actor.ImageFontText;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.util.TextAlignment;

import java.awt.event.KeyEvent;

/**
 * Demonstiert die Methode {@link ImageFontText#setContent(String)} der Klasse
 * {@link ImageFontText}. Bei jedem Aufruf der Methode wird eine neues Bilder
 * erzeugt.
 *
 * @author Josef Friedrich
 */
public class ImageFontTextSetContentDemo extends Scene
        implements KeyStrokeListener
{
    ImageFontText textField;

    String text1 = "Hello, World. Hello Universe";

    String text2 = "Short Text";

    String text3 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    public ImageFontTextSetContentDemo()
    {
        ImageFont font = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
        textField = new ImageFontText(font,
                "Hello, World. Lorem ipsum. Lorem ipsum. Lorem ipsum", 15,
                TextAlignment.LEFT);
        add(textField);
        getCamera().setPostion(10, 8);
        setBackgroundColor("white");
    }

    private void setContent(String content)
    {
        textField.setContent(content);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_1 -> setContent(text1);
        case KeyEvent.VK_2 -> setContent(text2);
        case KeyEvent.VK_3 -> setContent(text3);
        }
    }

    public static void main(String[] args)
    {
        Game.start(new ImageFontTextSetContentDemo());
    }
}
