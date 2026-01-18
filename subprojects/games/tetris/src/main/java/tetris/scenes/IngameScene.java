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
package tetris.scenes;

import java.awt.event.KeyEvent;
import java.util.Random;

import pi.Controller;
import pi.Rectangle;
import pi.event.KeyStrokeListener;
import pi.event.PeriodicTaskExecutor;
import pi.event.PressedKeyRepeater;
import tetris.Tetris;
import tetris.tetrominos.FilledRowRange;
import tetris.tetrominos.Grid;
import tetris.tetrominos.SoftDrop;
import tetris.tetrominos.Tetromino;
import tetris.text.NumberDisplay;

/**
 * Die Hauptspiel-Szene.
 *
 * @author Josef Friedrich
 */
public class IngameScene extends BaseScene implements KeyStrokeListener
{
    private final Grid grid;

    /**
     * Der Zufallsgenerator wird benötigt, um zufällig neue Tetrominos zu
     * erzeugen. Wir verwenden die Method {@code Random#nextInt()} um zufällig
     * Zahlen von {@code 0} bis einschließlich {@code 6} zu bekommen.
     *
     * @see #createNextTetromino()
     */
    private static final Random random = new Random();

    /**
     * Die Nummer des nächsten Tetrominos.
     */
    private int nextTetromino;

    /**
     * Das aktuelle Tetromino, das gesteuert werden kann und automatisch nach
     * unten fällt.
     */
    private Tetromino tetromino;

    /**
     * Das Vorschaubild des nächsten Tetrominos im linken unteren Bereich.
     */
    private Tetromino previewTetromino;

    /**
     * Die Gesamtpunktezahl. Diese Nummernanzeige ist mit SCORE beschriftet und
     * ist oben rechts platziert.
     */
    private final NumberDisplay score;

    /**
     * In welchem Level wir uns gerade befinden. Das erste Level ist 0. Diese
     * Nummernanzeige ist mit LEVEL beschriftet und ist rechts in der Mitte
     * platziert.
     */
    private final NumberDisplay level;

    /**
     * Wie viele Zeilen bisher getilgt wurden. Diese Nummernanzeige ist mit
     * LINES beschriftet und ist unter der Level-Anzeige platziert.
     */
    private final NumberDisplay clearedLines;

    /**
     * Ein Feld, das die Anzahl an Einzelbilder enthält, nach denen eine
     * Tetromino eine Zeile weiter nach unten rutscht.
     *
     * <p>
     * Die Zahl an der Index-Position {@code 0} gibt die Anzahl an Einzelbilder
     * des {@code 0}-ten Levels an, die {@code 1} die Anzahl des {@code 1}-ten
     * Level, etc.
     * </p>
     *
     * <p>
     * Nach wie vielen Einzelbildern ein Tetromino eine Zeile weiter nach unten
     * gesetzt wird und zwar im Verhältnis zum aktuellen Level.
     * </p>
     *
     * <p>
     * Quelle: <a href=
     * "https://harddrop.com/wiki/Tetris_%28Game_Boy%29">harddrop.com</a>
     * </p>
     */
    private final int[] GB_FRAMES_PER_ROW = { 53, 49, 45, 41, 37, 33, 28, 22,
            17, 11, 10, 9, 8, 7, 6, 6, 5, 5, 4, 4, 3 };

    protected PressedKeyRepeater keyRepeater;

    PeriodicTaskExecutor periodicTask;

    /**
     * Dadurch kann die Bewegung der Tetrominos gesperrt werden, wenn sich das
     * Spiel gerade in einer Animation (z.B. Tilgung von Zeilen) befindet.
     */
    private boolean isInAnimation = false;

    /**
     * Gibt an, ob sich das Tetromino in einer Soft-Drop-Bewegung befindet. Als
     * Soft-Drop bezeichnet man die schnellere nach unten gerichtete Bewegung
     * des Tetromino.
     */
    private SoftDrop softDrop = null;

    public IngameScene()
    {
        super("ingame");
        // Das I-Tetromino ragt einen Block über das sichtbare Spielfeld hinaus,
        // wenn es in der Startposition gedreht wird, deshalb machen wir das
        // Blockgitter um eine Zeile höher.
        grid = new Grid(Tetris.GRID_WIDTH, Tetris.HEIGHT + 1);
        createNextTetromino();
        score = new NumberDisplay(this, 11, 14, 6);
        level = new NumberDisplay(this, 12, 10, 4);
        clearedLines = new NumberDisplay(this, 12, 7, 4);
        periodicTask = repeat(calculateDownInterval(), (counter) -> {
            if (softDrop == null)
            {
                moveDown();
            }
        });
        keyRepeater = new PressedKeyRepeater();
        keyRepeater.addListener(KeyEvent.VK_DOWN, () -> {
            softDrop = new SoftDrop(tetromino);
        }, this::moveDown, () -> {
            softDrop = null;
        });
        keyRepeater.addListener(KeyEvent.VK_RIGHT, this::moveRight);
        keyRepeater.addListener(KeyEvent.VK_LEFT, this::moveLeft);
        Sound.playKorobeiniki();
    }

    private void createNextTetromino()
    {
        // Beim ersten Mal müssen zwei zufällige Tetrominos erzeugt werden.
        // Wir müssen also zweimal eine Zufallszahl generieren.
        if (previewTetromino == null)
        {
            nextTetromino = random.nextInt(7);
        }
        tetromino = Tetromino.create(this, grid, nextTetromino, 4, 16);
        nextTetromino = random.nextInt(7);
        // Entfernen des alten Vorschaubildes, falls vorhanden.
        if (previewTetromino != null)
        {
            previewTetromino.remove();
        }
        // Das Vorschaubild liegt außerhalb des Blockgitters. Wir übergeben der
        // Methode null.
        previewTetromino = Tetromino.create(this, null, nextTetromino, 14, 3);
    }

    /**
     * Setzt drei verschiedene Zahlen, die den Spielstand angeben.
     * <p>
     * Siehe <a href="https://tetris.wiki/Scoring">Tetris-Wiki</a>.
     * </p>
     *
     * @param lines Die Anzahl an getilgten Zeilen.
     */
    private void setScores(int lines)
    {
        int s = 40;
        if (lines == 2)
        {
            s = 100;
        }
        else if (lines == 3)
        {
            s = 300;
        }
        else if (lines == 4)
        {
            s = 1200;
        }
        clearedLines.add(lines);
        level.set(clearedLines.get() / 10);
        int result = s * (level.get() + 1);
        assert result > 0;
        // Nach 10 getilgten Zeilen erhöht sich das Level.
        score.add(result);
    }

    /**
     * Berechnet das Zeitintervall in Sekunden, wie lange es dauert, bis sich
     * das aktuelle Tetromino von einer Zeile zur darunterliegenden bewegt.
     *
     * <p>
     * Wir berechnen das Intervall mit Hilfe des Dreisatzes, hier mit konkreten
     * Werte für das 0-te Level:
     * </p>
     *
     * {@code interval / 53 = 1 / 59.73} gibt {@code interval = 1 / 59.73 * 53}
     *
     * <p>
     * Mit Variablen
     * </p>
     *
     * {@code interval / GB_FRAMES_PER_ROW[level] = 1 / GB_FRAME_RATE} gibt
     * {@code interval = 1 / GB_FRAME_RATE * GB_FRAMES_PER_ROW[level] }
     */
    private double calculateDownInterval()
    {
        // Die Bildwiederholungsrate des originalen Gameboys pro Sekunde.
        // Quelle: <a href=
        // "https://harddrop.com/wiki/Tetris_%28Game_Boy%29">harddrop.com</a>
        double GB_FRAME_RATE = 59.73;
        return 1.0 / GB_FRAME_RATE * GB_FRAMES_PER_ROW[level.get()];
    }

    /**
     * Bewegt das aktuelle Tetromino nach <b>links</b>.
     */
    private void moveLeft()
    {
        if (isInAnimation)
        {
            return;
        }
        if (tetromino.moveLeft())
        {
            Sound.playBlockMove();
        }
    }

    /**
     * Bewegt das aktuelle Tetromino nach <b>rechts</b>.
     */
    private void moveRight()
    {
        if (isInAnimation)
        {
            return;
        }
        if (tetromino.moveRight())
        {
            Sound.playBlockMove();
        }
    }

    /**
     * Bewegt das aktuelle Tetromino um eine Zeile nach unten.
     */
    private void moveDown()
    {
        if (isInAnimation)
        {
            return;
        }
        // Wenn sich das Tetromino nicht mehr weiter nach unten bewegen kann.
        if (!tetromino.moveDown())
        {
            if (softDrop != null)
            {
                // Muss oberhalb von keyRepeater.stop() stehen.
                score.add(softDrop.getDistance());
            }
            // Wir stoppen alle Tastenwiederholer (z.B. ausgelöst durch einen
            // Soft drop), wenn sich ein Tetromino nicht
            // mehr weiter nach unten bewegen kann. Würden wir den Wiederholer
            // nicht stoppen, dann hätte das neue Tetromino gleich nach dem
            // Erscheinen ein erhöhtes Falltempo.
            keyRepeater.stop();
            Sound.playBlockDrop();
            softDrop = null;
            var range = grid.getFilledRowRange();
            if (range != null)
            {
                clearLines(range);
            }
            else
            {
                // In der Methode clearLines() wird dann am Ende der Animation
                // createNextTetromino() aufgerufen.
                // Wenn keine Zeilen zu tilgen sind, wird keine Animation
                // stattfinden und wir können gleich das nächste Tetromino
                // erzeugen.
                createNextTetromino();
            }
        }
    }

    private void rotate()
    {
        if (isInAnimation)
        {
            return;
        }
        if (tetromino.rotate())
        {
            Sound.playBlockRotate();
        }
    }

    /**
     * Tilgt gefüllte Zeilen und führt eine Animation aus.
     *
     * <p>
     * Diese Methode wird ausgeführt, wenn es ausgefüllte Zeilen gibt, die
     * getilgt werden müssen.
     * </p>
     */
    private void clearLines(FilledRowRange range)
    {
        isInAnimation = true;
        Rectangle overlay = new Rectangle(10, range.getRowCount());
        overlay.anchor(0, range.getFrom());
        overlay.color(Tetris.COLOR_SCHEME_GREEN.getLight());
        overlay.visible(false);
        add(overlay);
        periodicTask.pause();
        if (range.getRowCount() < 4)
        {
            Sound.playRowClear1to3();
        }
        else
        {
            Sound.playRowClear4();
        }
        repeat(0.167, 8, (counter) -> {
            // 1. grau
            // 2. Zeile sichtbar
            // 3. grau
            // 4. Zeile sichtbar
            // 5. grau
            // 6. Zeile sichtbar
            // 7. Zeile getilgt
            // 8. Zeilen oberhalb nach unten gerutscht
            switch (counter)
            {
            case 1:
            case 3:
            case 5:
                overlay.visible(true);
                break;

            case 2:
            case 4:
            case 6:
                overlay.visible(false);
                break;

            case 7:
                grid.removeFilledRowRange(range);
                break;

            case 8:
                grid.triggerLandslide(range);
                remove(overlay);
                createNextTetromino();
                periodicTask.resume();
                setScores(range.getRowCount());
                Sound.playBlockDrop();
                periodicTask.setInterval(calculateDownInterval());
                isInAnimation = false;
                break;
            }
        });
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE)
        {
            rotate();
        }
        if (Controller.isDebug())
        {
            grid.print();
        }
    }

    public static void main(String[] args)
    {
        Tetris.start(new IngameScene());
    }
}
