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
package demos.docs.resources.color;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.actor.Actor;
import pi.actor.Square;
import pi.event.KeyStrokeListener;
import pi.resources.color.PredefinedColorScheme;
import pi.resources.color.ColorUtil;

/**
 * Demonstriert die Methode
 * {@link pi.resources.color.ColorUtil#changeBrightness(Color, double)
 * ColorUtil#changeBrightness(Color, double)}.
 *
 * @author Josef Friedrich
 */
public class ColorVariationsDemo extends Scene implements KeyStrokeListener
{
    private final Actor[][] COLOR_AREAS;

    private final PredefinedColorScheme[] COLOR_SCHEMES = PredefinedColorScheme
            .values();

    private int currentColorScheme = -1;

    private final Text NAME;

    public ColorVariationsDemo()
    {
        info().title("Farbvariationen-Demo").description(
                "Demonstriert die Methode ColorUtil#changeBrightness(Color, double).")
                .help("Ein beliebiger Tastendruck schaltet zum nächsten Farbschema weiter.");
        COLOR_AREAS = new Actor[12][5];

        for (int i = 0; i < COLOR_AREAS.length; i++)
        {
            for (int j = 0; j < COLOR_AREAS[i].length; j++)
            {
                Square square = new Square();
                COLOR_AREAS[i][j] = square;
                square.position(j, i);
                add(square);
            }
        }
        NAME = new Text("");
        NAME.position(2, -2);
        NAME.color("white");
        add(NAME);
        camera().focus(4, 4);
        setNextColorScheme();
        backgroundColor("#444444");
    }

    private PredefinedColorScheme getNextColorScheme()
    {
        currentColorScheme++;
        if (currentColorScheme >= COLOR_SCHEMES.length)
        {
            currentColorScheme = 0;
        }
        return COLOR_SCHEMES[currentColorScheme];
    }

    private void setColorScheme(PredefinedColorScheme selection)
    {
        NAME.content(selection.name());
        var scheme = selection.getScheme();
        int i = 0;

        for (Color color : scheme.wheelColors())
        {
            COLOR_AREAS[i][0].color(ColorUtil.setBrightness(color, 0.2));
            COLOR_AREAS[i][1].color(ColorUtil.setBrightness(color, 0.4));
            COLOR_AREAS[i][2].color(ColorUtil.setBrightness(color, 0.6));
            COLOR_AREAS[i][3].color(ColorUtil.setBrightness(color, 0.8));
            COLOR_AREAS[i][4].color(ColorUtil.setBrightness(color, 1));
            i++;
        }
    }

    private void setNextColorScheme()
    {
        setColorScheme(getNextColorScheme());
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        setNextColorScheme();
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ColorVariationsDemo(), 520, 520);
    }
}
