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
package demos.classes.actor;

import pi.Game;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.event.KeyStrokeListener;
import pi.util.TextAlignment;

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
        camera().focus(10, 8);
        backgroundColor("white");
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
