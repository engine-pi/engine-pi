package pi.graphics.boxes_ng;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class DimensionBoxTest
{
    @Test
    void emptyConstructorKeepsZeroDimensionsUntilMeasured()
    {
        DimensionBox box = new DimensionBox();

        assertEquals(0, box.width());
        assertEquals(0, box.height());
    }

    @Test
    void constructorSetsDefinedWidthAndHeight()
    {
        DimensionBox box = new DimensionBox(12.5, 7.25);
        box.measure();

        assertEquals(13, box.width());
        assertEquals(7, box.height());
    }

    @Test
    void measureUsesDefinedDimensions()
    {
        DimensionBox box = new DimensionBox(18, 9);
        box.measure();

        assertEquals(18, box.width());
        assertEquals(9, box.height());
    }

    @Test
    void createBuildsRequestedNumberOfBoxes()
    {
        DimensionBox[] boxes = DimensionBox.create(3);

        assertNotNull(boxes);
        assertEquals(3, boxes.length);
        for (DimensionBox box : boxes)
        {
            assertNotNull(box);
            assertEquals(0, box.width());
            assertEquals(0, box.height());
        }
    }

    @Test
    void widthAndHeightCanBeSetAfterCreation()
    {
        DimensionBox box = new DimensionBox();

        box.width(20).height(40);
        box.measure();

        assertEquals(20, box.width());
        assertEquals(40, box.height());
    }
}
