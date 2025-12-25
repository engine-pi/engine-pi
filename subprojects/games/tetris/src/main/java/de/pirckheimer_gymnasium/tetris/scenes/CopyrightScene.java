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
package de.pirckheimer_gymnasium.tetris.scenes;

import java.awt.event.KeyEvent;

import pi.actor.ImageFontText;
import pi.event.KeyStrokeListener;
import pi.util.TextAlignment;
import de.pirckheimer_gymnasium.tetris.Tetris;
import de.pirckheimer_gymnasium.tetris.text.Font;

import static de.pirckheimer_gymnasium.tetris.Tetris.COLOR_SCHEME_GREEN;

/**
 * Das ist der <b>erste Bildschirm</b>, der beim Starten des Spiels angezeigt
 * wird. Zeigt einen <b>Copyright</b>-Hinweis.
 *
 * @author Josef Friedrich
 */
public class CopyrightScene extends BaseScene implements KeyStrokeListener
{
    public CopyrightScene()
    {
        super(null);
        setBackgroundColor(COLOR_SCHEME_GREEN.getWhite());
        String origText = "\"TM and ©1987 ELORG,\n" + //
                "Tetris licensed to\n" + //
                "Bullet-Proof\n" + //
                "software and\n" + //
                "sub-licensed to\n" + //
                "Nintendo.\n" + //
                "\n" + //
                "©1989 Bullet-Proof\n" + //
                "software\n" + //
                "©1989 Nintendo\n" + //
                "\n" + //
                "All rights reserved.\n" + //
                "\n" + //
                "original concept\n" + //
                "design and programm\n" + //
                // Im Original: by Alexey Pazhitnov."
                // ." kann mit ImageFont nicht als ein Zeichen dargestellt
                // werden.
                // U+E000..U+F8FF BMP (0) Private Use Area
                "by Alexey Pazhitnov\uE000\n" + "\n";
        ImageFontText text = new ImageFontText(Font.getFont(), origText, 21,
                TextAlignment.CENTER);
        text.setPosition(-2, 0);
        add(text);
        delay(4, this::startTitleScene);
    }

    public void startTitleScene()
    {
        Tetris.start(new TitleScene());
    }

    /**
     * Wenn eine beliebige Taste gedrückt wird, wird zum nächsten Bildschirm,
     * der Titelszene, gesprungen.
     */
    public void onKeyDown(KeyEvent keyEvent)
    {
        startTitleScene();
    }

    public static void main(String[] args)
    {
        Tetris.start(new CopyrightScene());
    }
}
