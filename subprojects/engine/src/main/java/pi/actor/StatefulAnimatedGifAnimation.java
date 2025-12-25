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
package pi.actor;

/**
 * Erzeugt eine Animation aus einer animierter GIF-Datei.
 *
 * @param <State> Typ der Zustände, zwischen denen in der Animation gewechselt
 *     werden soll.
 *
 * @author Josef Friedrich
 *
 * @since 0.27.0
 */
public class StatefulAnimatedGifAnimation<State>
        extends StatefulAnimation<State>
{
    /**
     * @param width Die Breite in Meter der animierten Figur.
     * @param height Die Höhe in Meter der animierten Figur.
     */
    public StatefulAnimatedGifAnimation(double width, double height)
    {
        super(width, height);
    }

    /**
     * @param state Der Zustand, unter dem die Animation gespeichert wird.
     * @param filepath Der Dateipfad der GIF-Datei.
     */
    public void addState(State state, String filepath)
    {
        addState(state,
                Animation.createFromAnimatedGif(filepath, width, height));
    }
}
