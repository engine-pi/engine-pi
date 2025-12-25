package de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.relocation;

import static de.pirckheimer_gymnasium.blockly_robot.TestHelper.loadBagPacker;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BagPackerTest
{
    BagPacker bagPacker = loadBagPacker("loops_excercises/securing_the_road");

    @Test
    void testGetBagSize()
    {
        assertEquals(bagPacker.getBagSize(), 200);
    }
}
