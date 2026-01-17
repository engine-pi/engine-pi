/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.docs.main_classes.actor.text;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/actor/text.md

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;
import pi.resources.font.FontContainer;
import pi.resources.font.FontStyle;

/**
 * Demonstriert die <b>Schriftstile</b> der Figur „Text“.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class TextStyleDemo extends Scene implements KeyStrokeListener
{
    private String[] systemFonts;

    private int currentSystemFont = 0;

    private Text fontName;

    private Text fontNameClear;

    private Text plain;

    private Text bold;

    private Text italic;

    private Text boldItalic;

    public TextStyleDemo()
    {
        info().title("Schriftstil-Demo")
            .description(
                "Die Demo zeigt zurerst den Schriftnamen, dann die vier Schriftstile „normal”, „fett”, „kursiv” und „fett und kursiv”.")
            .help(
                "Mit einer beliebigen Taste kann durch alle Systemschriftarten in alphabetischer Reihenfolge gegangen werden.");
        systemFonts = FontContainer.systemFonts();

        fontName = createText("Times New Roman");
        fontName.style(FontStyle.BOLD).y(6);

        fontNameClear = createText("(Times New Roman)");
        fontNameClear.height(1).font("Arial").y(5);

        plain = createText("Normal");
        bold = createText("Fett");
        italic = createText("Kursiv");
        boldItalic = createText("Fett und kursiv");

        // Mithilfe des Aufzählungstyps FontStyle
        plain.style(FontStyle.PLAIN);
        bold.style(FontStyle.BOLD);
        italic.style(FontStyle.ITALIC);
        boldItalic.style(FontStyle.BOLD_ITALIC);

        // Oder als Ganzzahl
        plain.style(0);
        bold.style(1);
        italic.style(2);
        boldItalic.style(3);

        plain.y(0);
        bold.y(-3);
        italic.y(-6);
        boldItalic.y(-9);

        backgroundColor("#666666");
    }

    private Text createText(String content)
    {
        Text text = new Text(content);
        text.height(3).font("Times New Roman").x(-11);
        add(text);
        return text;
    }

    private void setFont(String fontName)
    {
        Color random = colors.random();

        this.fontName.content(fontName).font(fontName).color(random);
        fontNameClear.content("(" + fontName + ")");
        plain.font(fontName).color(random);
        bold.font(fontName).color(random);
        italic.font(fontName).color(random);
        boldItalic.font(fontName).color(random);

    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        setFont(systemFonts[currentSystemFont]);
        if (currentSystemFont == systemFonts.length - 1)
        {
            currentSystemFont = 0;
        }
        else
        {
            currentSystemFont++;
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextStyleDemo());
    }
}
