package blockly_robot.robot.logic.menu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskListTest
{
    TaskList list;

    @BeforeEach
    void reset()
    {
        list.reset();
    }

    public TaskListTest() throws IOException
    {
        list = TaskList.readFromResources();
    }

    @Test
    void readFromResources() throws IOException
    {
        TaskList list = TaskList.readFromResources();
        assertTrue(list.size() > 0);
    }

    @Test
    void readFromMenu() throws IOException
    {
        TaskList list = TaskList.readFromMenu();
        assertEquals(list.size(), 6);
    }

    @Test
    void getId()
    {
        assertTrue(list.getId(0) instanceof String);
    }

    @Test
    void next()
    {
        assertTrue(list.next().indexOf("/") > 0);
    }

    @Test
    void previous()
    {
        assertTrue(list.next().indexOf("/") > 0);
    }
}
