package blockly_robot.robot.gui.map;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import blockly_robot.robot.logic.navigation.Coords;
import pi.graphics.geom.Vector;

class CoordinateSystemTranslatorTest
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
        assertEquals(point.row(), row);
        assertEquals(point.col(), col);
    }

    private void assertVector(Vector vector, int x, int y)
    {
        assertEquals(vector.x(), x);
        assertEquals(vector.y(), y);
    }

    @Test
    void rowsAndCols()
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
        void allPositive()
        {
            assertPoint(translate(3, 4, 3, 2).toPoint(5, 3), 1, 2);
        }

        @Test
        void xYnegative()
        {
            assertPoint(translate(3, 4, -3, -2).toPoint(-1, -1), 1, 2);
        }

        @Test
        void yNegative()
        {
            assertPoint(translate(3, 4, 5, -5).toPoint(7, -4), 1, 2);
        }

        @Test
        void xY0()
        {
            assertPoint(translate(3, 4, 0, 0).toPoint(2, 1), 1, 2);
        }

        @Test
        void differentRowsCols()
        {
            assertPoint(translate(10, 10, -5, -5).toPoint(4, 4), 0, 9);
        }
    }

    @Nested
    @DisplayName("Translate a Blockly Robot Point to a Engine Alpha Vector.")
    class TranslateToVector
    {
        @Test
        void allPositive()
        {
            assertVector(translate(3, 4, 3, 2).toVector(2, 0), 3, 2);
        }

        @Test
        void xYnegative()
        {
            assertVector(translate(3, 4, -3, -2).toVector(2, 0), -3, -2);
        }

        @Test
        void xY0()
        {
            assertVector(translate(3, 4, 0, 0).toVector(0, 0), 0, 2);
        }
    }
}
