package blockly_robot.robot.logic.item;

import static blockly_robot.TestHelper.loadItemCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ItemCreatorTest
{
    ItemCreator items = loadItemCreator(
        "conditionals_excercises/find_the_destination");

    @Test
    public void testCreateItemByNum()
    {
        assertEquals(items.create(2).getNum(), 2);
    }

    @Test
    public void testCreateItemByName()
    {
        assertEquals(items.create("wall").getNum(), 2);
    }
}
