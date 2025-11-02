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
package de.pirckheimer_gymnasium.tetris.debug;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.event.MouseButton;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener;
import de.pirckheimer_gymnasium.tetris.Tetris;
import de.pirckheimer_gymnasium.tetris.scenes.BaseScene;
import de.pirckheimer_gymnasium.tetris.tetrominos.FilledRowRange;
import de.pirckheimer_gymnasium.tetris.tetrominos.Grid;
import de.pirckheimer_gymnasium.tetris.tetrominos.Tetromino;

/**
 * Debug-Szene um die Klasse {@link Grid} testen zu können.
 *
 * <p>
 * Tastenkürzel:
 * </p>
 *
 * <p>
 * Tasten: {@code F1 - F4} erzeugt eine bestimmte Vorbelegung mit Tetrominos
 * </p>
 *
 * <ul>
 * <li>Taste {@code F1}: Die zweite Zeile ({@code from = 1} und {@code to = 1})
 * kann getilgt werden.</li>
 * <li>Taste {@code F2}: Die ersten beiden Zeilen ({@code from = 0} und
 * {@code to = 1}) können getilgt werden.
 * <li>Taste {@code F3}: Die ersten vier Zeilen ({@code from = 0} und
 * {@code to = 3}) können getilgt werden.
 * <li>Taste {@code F4}: Keine Zeile ({@code range = null}) kann getilgt
 * werden.</li>
 * </ul>
 *
 * <p>
 * Tasten {@code 1 - 3}: Löscht schrittweise einen Bereich aus dem Gitter.
 * </p>
 *
 * <ul>
 * <li>Taste {@code 1}: {@link Grid#getFilledRowRange()}</li>
 * <li>Taste {@code 2}: {@link Grid#removeFilledRowRange(FilledRowRange)}</li>
 * <li>Taste {@code 3}: {@link Grid#triggerLandslide(FilledRowRange)}</li>
 * </ul>
 *
 * <p>
 * Durch einen Mausklick auf eine Zeile, kann diese eine Zeile getilgt werden.
 * </p>
 *
 * @author Josef Friedrich
 */
public class GridDebugScene extends BaseScene
        implements KeyStrokeListener, FrameUpdateListener, MouseClickListener
{
    private final Grid GRID;

    private final Rectangle ROW_OVERLAY;

    private FilledRowRange range;

    private final Rectangle RANGE_OVERLAY;

    private boolean showRangeOverlay = true;

    public GridDebugScene()
    {
        super("ingame");
        GRID = new Grid(Tetris.GRID_WIDTH, Tetris.HEIGHT + 1);
        ROW_OVERLAY = addOverlayRectangle("green");
        RANGE_OVERLAY = addOverlayRectangle("blue");
        fillGrid1();
    }

    private Rectangle addOverlayRectangle(String color)
    {
        Rectangle rectangle = new Rectangle(10, 1);
        rectangle.setColor(color);
        rectangle.setOpacity(0.7);
        rectangle.setLayerPosition(2);
        add(rectangle);
        return rectangle;
    }

    private void createTetromino(String name, int rotation, int x, int y)
    {
        Tetromino tetromino = Tetromino.create(this, null, name, x, y);
        for (int i = 0; i < rotation; i++)
        {
            tetromino.rotate();
        }
        tetromino.addGrid(GRID);
    }

    private void L(int rotation, int x, int y)
    {
        createTetromino("L", rotation, x, y);
    }

    private void J(int rotation, int x, int y)
    {
        createTetromino("J", rotation, x, y);
    }

    private void I(int rotation, int x, int y)
    {
        createTetromino("I", rotation, x, y);
    }

    private void O(int rotation, int x, int y)
    {
        createTetromino("O", rotation, x, y);
    }

    private void Z(int rotation, int x, int y)
    {
        createTetromino("Z", rotation, x, y);
    }

    private void S(int rotation, int x, int y)
    {
        createTetromino("S", rotation, x, y);
    }

    private void T(int rotation, int x, int y)
    {
        createTetromino("T", rotation, x, y);
    }

    /**
     * Die zweite Zeile ({@code from = 1} und {@code to = 1}) kann getilgt
     * werden.
     */
    private void fillGrid1()
    {
        GRID.clear();
        I(0, 1, 0);
        S(0, 6, 1);
        O(0, 8, 1);
        I(0, 3, 1);
        S(0, 1, 2);
    }

    /**
     * Die ersten beiden Zeilen ({@code from = 0} und {@code to = 1}) können
     * getilgt werden.
     */
    private void fillGrid2()
    {
        GRID.clear();
        O(0, 0, 1);
        O(0, 2, 1);
        O(0, 4, 1);
        O(0, 6, 1);
        O(0, 8, 1);
    }

    /**
     * Die ersten vier Zeilen ({@code from = 0} und {@code to = 3}) können
     * getilgt werden.
     *
     * <p>
     * Füllt das Gitter nach einem Screenshot eines Spielstands auf <a href=
     * "https://www.retroplace.com/de/spiele/174256--tetris">retroplace.com</a>.
     * Ein I-Tetromino wurde zusätzlich eingefügt, sodass vier Zeilen getilgt
     * werden können.
     * </p>
     */
    private void fillGrid3()
    {
        GRID.clear();
        T(2, 4, 0);
        S(0, 2, 1);
        S(1, 6, 1);
        J(1, 9, 1);
        Z(1, 8, 1);
        T(3, 1, 2);
        Z(0, 3, 3);
        S(0, 7, 3);
        L(2, 5, 3);
        L(2, 4, 4);
        I(1, 9, 4);
        J(1, 8, 5);
        O(0, 6, 6);
        Z(1, 3, 5);
        T(3, 4, 6);
        T(2, 6, 7);
        O(0, 8, 8);
        I(1, 1, 5);
        I(1, 2, 7);
        // nicht im Screenshot der Vorlage
        // hinzugefügt, dass vier Zeilen getilgt werden können.
        I(1, 0, 1);
    }

    /**
     * Keine Zeile ({@code range = null}) kann getilgt werden.
     *
     * <p>
     * Füllt das Gitter nach einem Screenshot eines Spielstands auf <a href=
     * "https://www.retroplace.com/de/spiele/174256--tetris">retroplace.com</a>.
     * Keine Zeile kann getilgt werden.
     * </p>
     */
    private void fillGrid4()
    {
        GRID.clear();
        T(2, 4, 0);
        S(0, 2, 1);
        S(1, 6, 1);
        J(1, 9, 1);
        Z(1, 8, 1);
        T(3, 1, 2);
        Z(0, 3, 3);
        S(0, 7, 3);
        L(2, 5, 3);
        L(2, 4, 4);
        I(1, 9, 4);
        J(1, 8, 5);
        O(0, 6, 6);
        Z(1, 3, 5);
        T(3, 4, 6);
        T(2, 6, 7);
        O(0, 8, 8);
        I(1, 1, 5);
        I(1, 2, 7);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_ENTER -> {
            RANGE_OVERLAY.setVisible(false);
            range = GRID.getFilledRowRange();
            if (range != null)
            {
                // 1. grau
                // 2. Zeile sichtbar
                // 3. grau
                // 4. Zeile sichtbar
                // 5. grau
                // 6. Zeile sichtbar
                // 7. Zeile getilgt
                // 8. Zeilen oberhalb nach unten gerutscht
                Rectangle overlay = addRectangle(10, range.getRowCount());
                overlay.setPosition(0, range.getFrom());
                overlay.setColor(Tetris.COLOR_SCHEME_GREEN.getLight());
                overlay.setVisible(false);
                showRangeOverlay = false;
                repeat(0.167, 8, (counter) -> {
                    System.out.println(counter);
                    switch (counter)
                    {
                    case 1:
                    case 3:
                    case 5:
                        System.out.println("Zeige graue Überblendung");
                        overlay.setVisible(true);
                        break;

                    case 2:
                    case 4:
                    case 6:
                        System.out.println("Zeige zu tilgende Zeilen.");
                        overlay.setVisible(false);
                        break;

                    case 7:
                        System.out.println("Tilge Reihen.");
                        System.out.println(range);
                        GRID.removeFilledRowRange(range);
                        break;
                    }
                }, (counter) -> {
                    System.out.println(counter);
                    System.out.println("Löse Erdrutsch aus.");
                    GRID.triggerLandslide(range);
                    remove(overlay);
                    range = null;
                });
            }
        }
        case KeyEvent.VK_F1 -> fillGrid1();
        case KeyEvent.VK_F2 -> fillGrid2();
        case KeyEvent.VK_F3 -> fillGrid3();
        case KeyEvent.VK_F4 -> fillGrid4();
        case KeyEvent.VK_1 -> {
            showRangeOverlay = true;
            range = GRID.getFilledRowRange();
        }
        case KeyEvent.VK_2 -> {
            showRangeOverlay = true;
            GRID.removeFilledRowRange(range);
        }
        case KeyEvent.VK_3 -> {
            showRangeOverlay = true;
            GRID.triggerLandslide(range);
            range = null;
        }
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        // Markiert eine Zeile über der sich die Maus befindet. Diese kann dann
        // durch einen Link-Klick getilgt werden.
        Vector position = Game.getMousePosition();
        ROW_OVERLAY.setY((int) position.getY());
        // Markiert die Zeilen, die getilgt werden können.
        if (range != null && showRangeOverlay)
        {
            RANGE_OVERLAY.setVisible(true);
            RANGE_OVERLAY.setHeight(range.getRowCount());
            RANGE_OVERLAY.setY(range.getFrom());
        }
        else
        {
            RANGE_OVERLAY.setVisible(false);
        }
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        // Durch einen Linksklick kann eine Zeile - egal ob ausgefüllt oder
        // nicht - getilgt werden.
        if (button == MouseButton.LEFT)
        {
            GRID.clearRow((int) position.getY());
        }
    }

    public static void main(String[] args)
    {
        Tetris.start(new GridDebugScene());
    }
}
