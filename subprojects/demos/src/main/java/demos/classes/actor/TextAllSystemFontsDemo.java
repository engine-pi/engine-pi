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
package demos.classes.actor;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;
import pi.resources.font.FontContainer;

/**
 * Demonstiert <b>alle Schriftarten</b>, die auf dem System installiert sind.
 *
 * <p>
 * Alle Schriftarten auf einmal zu laden ist sehr langsam.
 * </p>
 *
 * @author Josef Friedrich
 */
public class TextAllSystemFontsDemo extends Scene implements KeyStrokeListener
{
    /**
     * Wie viele Seiten angezeigt werden können.
     */
    private int pageCount;

    /**
     * Die aktuelle Seite.
     */
    private int page;

    /**
     * Die Namensliste der Systemschriftarten.
     */
    private String[] systemFonts;

    /**
     * Die aktuellen Schriftarten einer Seite.
     */
    private Text[] fonts;

    private static final int FONTS_COUNT_PER_PAGE = 10;

    public TextAllSystemFontsDemo()
    {
        info().title("Alle Systemschriftarten")
            .help(
                "Mit den Pfeiltasten (nach oben, nach unten) durch alle Schriftarten blättern.");
        Text heading = new Text("Alle System-Schriftarten");
        heading.height(2).anchor(-10, 6).color("black");
        fonts = new Text[FONTS_COUNT_PER_PAGE];
        add(heading);
        systemFonts = FontContainer.systemFonts();
        setFontsOfCurrentPage();
        backgroundColor("white");
    }

    private void setFontsOfCurrentPage()
    {
        for (Text text : fonts)
        {
            if (text != null)
            {
                text.remove();
            }
        }

        pageCount = systemFonts.length / FONTS_COUNT_PER_PAGE;
        int startIndex = page * FONTS_COUNT_PER_PAGE;
        int x = 0;
        for (int i = startIndex; i < startIndex + FONTS_COUNT_PER_PAGE; i++)
        {
            String fontName = systemFonts[i];
            Text text = (Text) new Text(fontName).font(fontName);
            text.anchor(-10, -1.0 * x + 3);
            text.color("black");
            fonts[x] = text;
            x++;
            add(text);
        }
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_UP)
        {
            if (page == 0)
            {
                page = pageCount - 1;
            }
            else
            {
                page--;
            }
        }
        else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN)
        {
            if (page == pageCount - 1)
            {
                page = 0;
            }
            else
            {
                page++;
            }
        }
        setFontsOfCurrentPage();
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextAllSystemFontsDemo());
        Controller.title("Alle Schriftarten");
    }
}
