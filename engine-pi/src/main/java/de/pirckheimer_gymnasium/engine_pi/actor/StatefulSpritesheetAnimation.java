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
package de.pirckheimer_gymnasium.engine_pi.actor;

/**
 * @param <State> Typ der Zustände, zwischen denen in der Animation gewechselt
 *                werden soll.
 *
 * @author Josef Friedrich
 *
 * @since 0.27.0
 */
public class StatefulSpritesheetAnimation<State>
        extends StatefulAnimation<State>
{
    /**
     * @param width         Die Breite in Meter der animierten Figur.
     * @param height        Die Höhe in Meter der animierten Figur.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     */
    public StatefulSpritesheetAnimation(double width, double height,
            double frameDuration)
    {
        super(width, height, frameDuration);
    }

    /**
     * @param state         Der Zustand, unter dem die Animation gespeichert
     *                      wird.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     * @param filePath      Der Dateipfad des Spritesheets.
     * @param x             Die Anzahl an Sprites in x-Richtung.
     * @param y             Die Anzahl an Sprites in y-Richtung.
     * @param width         Die Breite der Animation in Meter.
     * @param height        Die Höhe der Animation in Meter.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     */
    public void addState(State state, String filepath, int x, int y)
    {
        addState(state, Animation.createFromSpritesheet(frameDuration, filepath,
                x, y, width, height));
    }

    /**
     * @param state         Der Zustand, unter dem die Animation gespeichert
     *                      wird.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     * @param filePath      Der Dateipfad des Spritesheets.
     * @param spriteWidth   Die Breite des Sprites in Pixel.
     * @param spriteHeight  Die Höhe des Sprites in Pixel.
     * @param width         Die Breite der Animation in Meter.
     * @param height        Die Höhe der Animation in Meter.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     */
    public void addState(State state, int spriteWidth, int spriteHeight,
            String filePath)
    {
        addState(state, Animation.createFromSpritesheet(frameDuration, filePath,
                width, height, spriteWidth, spriteHeight));
    }
}
