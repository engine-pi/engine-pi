/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/HUDDisplay.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi_demos.small_games.dude;

import java.util.Arrays;
import java.util.List;

import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.Tile;
import de.pirckheimer_gymnasium.engine_pi.actor.TileRegistration;
import de.pirckheimer_gymnasium.engine_pi.actor.TileMap;

public class HUDDisplay
{
    private static final int VALUE_LENGTH = 10;

    private static final double SCALE = 1.5;

    private final TileRegistration background;

    private final TileRegistration lines;

    private final TileMap[] lineSources = { //
            TileMap.createFromImage("dude/hud/orb_red.png", 8, 16), // Line
                                                                    // 0
                                                                    // ->
                                                                    // Health
            TileMap.createFromImage("dude/hud/orb_blue.png", 8, 16), // Line
                                                                     // 1
                                                                     // ->
                                                                     // Power
            TileMap.createFromImage("dude/hud/orb_green.png", 8, 16) // Line
                                                                     // 2
                                                                     // ->
                                                                     // XP
    };

    private static final Tile TILE_BACK = TileMap
            .createFromImage("dude/hud/back.png");

    private static final TileMap NUM_BLACK = TileMap
            .createFromImage("dude/hud/num_black.png", 8, 16);

    private static final TileMap ORB_ORANGE = TileMap
            .createFromImage("dude/hud/orb_orange.png", 8, 16);

    /**
     * Konstruktor für Objekte der Klasse ActorGroup
     */
    public HUDDisplay(double x, double y)
    {
        background = new TileRegistration(VALUE_LENGTH + 7, 4, 8 * SCALE,
                16 * SCALE);
        // Initialize Standard-Parts of backgrounds.
        for (int i = 0; i < 4; i++)
        {
            background.setTile(0, i, ORB_ORANGE.getTile(0, 0));
            background.setTile(1, i, ORB_ORANGE.getTile(1, 0));
            background.setTile(4, i, ORB_ORANGE.getTile(1, 0));
            for (int j = 0; j < VALUE_LENGTH; j++)
            {
                background.setTile(5 + j, i, TILE_BACK);
            }
            background.setTile(VALUE_LENGTH + 5, i, ORB_ORANGE.getTile(1, 0));
            background.setTile(VALUE_LENGTH + 6, i, ORB_ORANGE.getTile(2, 0));
        }
        // Line 0: HEALTH
        background.setTile(2, 0, NUM_BLACK.getTile(6, 2));
        background.setTile(3, 0, NUM_BLACK.getTile(7, 2));
        // Line 1: POWER
        background.setTile(2, 1, NUM_BLACK.getTile(8, 2));
        background.setTile(3, 1, NUM_BLACK.getTile(9, 2));
        // Line 2: XP
        background.setTile(2, 2, NUM_BLACK.getTile(4, 2));
        background.setTile(3, 2, NUM_BLACK.getTile(5, 2));
        // Line 3: Monneeeeyyy
        background.setTile(2, 3, NUM_BLACK.getTile(2, 2));
        background.setTile(3, 3, NUM_BLACK.getTile(3, 2));
        // LINE CONTENT
        lines = new TileRegistration(VALUE_LENGTH, 4, 8 * SCALE, 16 * SCALE);
        background.setPosition(x, y);
        lines.setPosition(x, y);
        lines.moveBy(5 * 8 * SCALE, 0);
        setLineValue(0, 10, true);
        setLineValue(1, 0, false);
        setLineValue(2, 0, false);
        for (int k = 0; k < 5; k++)
        {
            setNumberOf(k, 0);
        }
    }

    private void setNumberOf(int numIndex, int numValue)
    {
        if (numValue < 0 || numValue > 9)
        {
            return;
        }
        int x = (numValue % 5) * 2;
        int y = numValue / 5;
        lines.setTile(numIndex * 2, 3, NUM_BLACK.getTile(x, y));
        lines.setTile(numIndex * 2 + 1, 3, NUM_BLACK.getTile(x + 1, y));
    }

    /**
     * Ändert den anzuzeigenden Wert eines der Attribute im HUD.
     *
     * @param lineIndex Index der zu ändernden Zeile
     * @param lineValue Wert der zu ändernden Zeile.
     * @param fullCapFinal true: Soll der letzte Wert voll dargestellt werden
     *     oder halb?
     */
    private void setLineValue(int lineIndex, int lineValue,
            boolean fullCapFinal)
    {
        for (int i = 0; i < VALUE_LENGTH; i++)
        {
            if (i + 1 < lineValue)
            {
                // Voll ausgemaltes HUD
                lines.setTile(i, lineIndex,
                        lineSources[lineIndex].getTile(1, 0));
            }
            else if (i + 1 == lineValue)
            {
                // Ende des Striches
                if (fullCapFinal)
                {
                    lines.setTile(i, lineIndex,
                            lineSources[lineIndex].getTile(1, 0));
                }
                else
                {
                    lines.setTile(i, lineIndex,
                            lineSources[lineIndex].getTile(2, 0));
                }
            }
            else
            {
                // Nicht gebraucht -> unsichtbar
                lines.setTile(i, lineIndex, null);
            }
        }
    }

    public void setLineDisplay(int lineNo, double rel)
    {
        if (lineNo < 0 || lineNo > 2 || rel < 0 || rel > 1)
        {
            return;
        }
        int doublePrecision = (int) (rel * ((VALUE_LENGTH + 1) * 2));
        setLineValue(lineNo, doublePrecision / 2, doublePrecision % 2 == 1);
    }

    public void setDisplayNumber(int value)
    {
        for (int z = 1; z <= 5; z++)
        {
            int baseValue = value % 10;
            setNumberOf(5 - z, baseValue);
            value /= 10;
        }
    }

    public List<Actor> getActors()
    {
        return Arrays.asList(background, lines);
    }
}
