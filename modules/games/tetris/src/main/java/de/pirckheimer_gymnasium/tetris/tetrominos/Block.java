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
package de.pirckheimer_gymnasium.tetris.tetrominos;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;
import de.pirckheimer_gymnasium.tetris.ImageLoader;

/**
 * @author Josef Friedrich
 */
public class Block
{
    private int x;

    private int y;

    private final Image image;

    private final String name;

    /**
     * Eine Referenz auf die {@link Scene Szene}, in der der Block angezeigt
     * werden soll.
     *
     * <p>
     * Wird benötigen diese Referenz, um den Block in die {@link Scene Szene}
     * einzufügen ({@link Scene#add}) bzw. aus der {@link Scene Szene} zu
     * entfernen ({@link Scene#remove}).
     */
    private final Scene scene;

    /**
     * @param scene Eine Referenz auf die {@link Scene Szene}, in der der Block
     *     angezeigt werden soll.
     * @param imageName Das Hauptbild angegeben als Dateiname ohne die
     *     Dateierweiterung, z. B. {@code "L"} oder {@code "I_h_left"}.
     * @param x Die X-Koordinate der Startposition, auf die der Block gesetzt
     *     werden soll.
     * @param y Die Y-Koordinate der Startposition, auf die der Block gesetzt
     *     werden soll.
     */
    public Block(Scene scene, String imageName, int x, int y)
    {
        this.scene = scene;
        name = imageName;
        image = ImageLoader.get("images/blocks/" + imageName + ".png");
        image.setPosition(x, y);
        this.x = x;
        this.y = y;
        scene.add(image);
    }

    /**
     * Gibt den Namen des Blocks zurück.
     *
     * @return Der Name des Blocks z. B. {@code "L"} oder {@code "I_h_left"}.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gibt den ersten Buchstaben des Blocknamens zurück.
     *
     * @return Der erste Buchstabe des Blocknamens des Blocks z. B. {@code 'L'}
     *     oder {@code 'I'}.
     */
    public char getChar()
    {
        return name.charAt(0);
    }

    public int getX()
    {
        assert (int) image.getX() == x;
        return x;
    }

    public int getY()
    {
        assert (int) image.getY() == y;
        return y;
    }

    public void moveBy(Vector vector)
    {
        image.moveBy(vector);
        x = x + (int) vector.getX();
        y = y + (int) vector.getY();
        // trigger assert
        image.getX();
        image.getY();
    }

    public void moveBy(int dX, int dY)
    {
        moveBy(new Vector(dX, dY));
    }

    public void moveLeft()
    {
        moveBy(-1, 0);
    }

    public void moveRight()
    {
        moveBy(1, 0);
    }

    public void moveDown()
    {
        moveBy(0, -1);
    }

    public void remove()
    {
        scene.remove(image);
    }
}
