package de.pirckheimer_gymnasium.blockly_robot.robot.gui.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation.Coords;
import de.pirckheimer_gymnasium.engine_pi.Vector;

public class CoordinateSystemTranslatorTest
{
    private CoordinateSystemTranslator translate(int rows, int cols, int x,
            int y)
    {
        return new CoordinateSystemTranslator(rows, cols, x, y);
    }

    private CoordinateSystemTranslator create(int rows, int cols)
    {
        return translate(rows, cols, 0, 0);
    }

    private void assertPoint(Coords point, int row, int col)
    {
        assertEquals(point.getRow(), row);
        assertEquals(point.getCol(), col);
    }

    private void assertVector(Vector vector, int x, int y)
    {
        assertEquals(vector.getX(), x);
        assertEquals(vector.getY(), y);
    }

    @Test
    public void testRowsAndCols()
    {
        var translator = create(4, 5);
        assertEquals(translator.rows, 4);
        assertEquals(translator.cols, 5);
    }

    @Nested
    @DisplayName("Translate Engine-Alpha Coordinate to Point")
    class TranslateToPoint
    {
        @Test
        void testAllPositive()
        {
            assertPoint(translate(3, 4, 3, 2).toPoint(5, 3), 1, 2);
        }

        @Test
        void testXYnegative()
        {
            assertPoint(translate(3, 4, -3, -2).toPoint(-1, -1), 1, 2);
        }

        @Test
        void testYNegative()
        {
            assertPoint(translate(3, 4, 5, -5).toPoint(7, -4), 1, 2);
        }

        @Test
        void testXY0()
        {
            assertPoint(translate(3, 4, 0, 0).toPoint(2, 1), 1, 2);
        }

        @Test
        void testDifferentRowsCols()
        {
            assertPoint(translate(10, 10, -5, -5).toPoint(4, 4), 0, 9);
        }
    }

    @Nested
    @DisplayName("Translate a Blockly Robot Point to a Engine Alpha Vector.")
    class TranslateToVector
    {
        @Test
        void testAllPositive()
        {
            assertVector(translate(3, 4, 3, 2).toVector(2, 0), 3, 2);
        }

        @Test
        void testXYnegative()
        {
            assertVector(translate(3, 4, -3, -2).toVector(2, 0), -3, -2);
        }

        @Test
        void testXY0()
        {
            assertVector(translate(3, 4, 0, 0).toVector(0, 0), 0, 2);
        }
    }
}
