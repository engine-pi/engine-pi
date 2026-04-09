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
package demos.classes.actor.image_text;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageText;
import pi.actor.ImageText.Font;
import pi.actor.ImageText.CaseSensitivity;

import pi.event.KeyStrokeListener;
import pi.util.TextAlignment;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/image-text.md

/**
 * Demonstiert die Methode {@link ImageText#content(String)} der Klasse
 * {@link ImageText}.
 *
 * <p>
 * Bei jedem Aufruf der Methode wird eine neues Bilder erzeugt.
 * </p>
 *
 * @author Josef Friedrich
 */
public class ContentDemo extends Scene implements KeyStrokeListener
{
    ImageText textField;

    String text1 = "Hello, World. Hello Universe";

    String text2 = "Short Text";

    String text3 = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    public ContentDemo()
    {
        Font font = new Font("main-classes/actor/image-text/tetris")
            .supportsCase(CaseSensitivity.UPPER);
        textField = new ImageText(font)
            .content("Hello, World. Lorem ipsum. Lorem ipsum. Lorem ipsum")
            .lineWidth(15)
            .alignment(TextAlignment.LEFT)
            .pixelMultiplication(4);
        add(textField);
        camera().focus(10, 8);
        backgroundColor("white");
    }

    private void setContent(String content)
    {
        textField.content(content);
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
        Controller.instantMode(false);
        Controller.start(new ContentDemo());
    }
}
