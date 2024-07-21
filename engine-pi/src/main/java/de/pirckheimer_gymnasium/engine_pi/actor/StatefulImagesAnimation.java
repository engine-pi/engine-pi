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

import java.awt.image.BufferedImage;

/**
 * Eine animierte Figur mit mehreren Zuständen, deren Animationen durch Angabe
 * der <b>Einzelbilder</b> erzeugt wird.
 *
 * @param <State> Typ der Zustände, zwischen denen in der Animation gewechselt
 *                werden soll.
 *
 * @author Josef Friedrich
 *
 * @since 0.26.0
 */
public class StatefulImagesAnimation<State> extends StatefulAnimation<State>
{
    /**
     * @param width         Die Breite in Meter der animierten Figur.
     * @param height        Die Höhe in Meter der animierten Figur.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     */
    public StatefulImagesAnimation(double width, double height,
            double frameDuration)
    {
        super(width, height, frameDuration);
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state         Der Zustand, unter dem die Animation gespeichert
     *                      wird.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     * @param images        Die bereits in den Speicher geladenen Bilder, die
     *                      als Einzelbilder verwendet werden sollen.
     */
    public void addState(State state, double frameDuration,
            BufferedImage... images)
    {
        addState(state, Animation.createFromImages(frameDuration, width, height,
                images));
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state  Der Zustand, unter dem die Animation gespeichert wird.
     * @param images Die bereits in den Speicher geladenen Bilder, die als
     *               Einzelbilder verwendet werden sollen.
     */
    public void addState(State state, BufferedImage... images)
    {
        addState(state, frameDuration, images);
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state         Der Zustand, unter dem die Animation gespeichert
     *                      wird.
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *                      bleiben.
     * @param filePaths     Die einzelnen Dateipfade der zu verwendenden
     *                      Einzelbilder.
     */
    public void addState(State state, double frameDuration, String... filePaths)
    {
        addState(state, Animation.createFromImages(frameDuration, width, height,
                filePaths));
    }

    /**
     * Fügt der Animation einen neuen Zustand hinzu.
     *
     * @param state     Der Zustand, unter dem die Animation gespeichert wird.
     * @param filePaths Die einzelnen Dateipfade der zu verwendenden
     *                  Einzelbilder.
     */
    public void addState(State state, String... filePaths)
    {
        addState(state, frameDuration, filePaths);
    }
}
