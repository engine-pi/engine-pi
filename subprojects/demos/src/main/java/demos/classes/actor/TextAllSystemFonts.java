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

import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;

/**
 * Demonstiert <b>alle Schriftarten</b>, die auf dem System installiert sind.
 *
 * <p>
 * Alle Schriftarten auf einmal zu laden ist sehr langsam.
 * </p>
 */
public class TextAllSystemFonts extends Scene implements KeyStrokeListener
{
    /**
     * Wie viele Seiten angezeigt werden können.
     */
    private int pageCount;

    /**
     * Die aktuelle Seite.
     */
    private int page;

    private String[] systemFonts;

    private Text[] fonts;

    private final int fontsCountPerPage = 10;

    public TextAllSystemFonts()
    {
        Text überschrift = (Text) new Text("Alle System-Schriftarten")
                .height(2);
        überschrift.anchor(-12, 3);
        überschrift.color("black");
        fonts = new Text[fontsCountPerPage];
        add(überschrift);
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        systemFonts = ge.getAvailableFontFamilyNames();
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

        pageCount = systemFonts.length / fontsCountPerPage;
        int startIndex = page * fontsCountPerPage;
        int x = 0;
        for (int i = startIndex; i < startIndex + fontsCountPerPage; i++)
        {
            String fontName = systemFonts[i];
            Text text = (Text) new Text(fontName).font(fontName);
            text.anchor(-12, -1 * x);
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
        Controller.start(new TextAllSystemFonts(), 1020, 520);
        Controller.title("Alle Schriftarten");
    }
}
