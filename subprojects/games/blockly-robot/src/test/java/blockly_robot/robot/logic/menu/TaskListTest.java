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
    public void reset()
    {
        list.reset();
    }

    public TaskListTest() throws IOException
    {
        list = TaskList.readFromResources();
    }

    @Test
    public void readFromResources() throws IOException
    {
        TaskList list = TaskList.readFromResources();
        assertTrue(list.size() > 0);
    }

    @Test
    public void readFromMenu() throws IOException
    {
        TaskList list = TaskList.readFromMenu();
        assertEquals(list.size(), 6);
    }

    @Test
    public void getId()
    {
        assertTrue(list.getId(0) instanceof String);
    }

    @Test
    public void next()
    {
        assertTrue(list.next().indexOf("/") > 0);
    }

    @Test
    public void previous()
    {
        assertTrue(list.next().indexOf("/") > 0);
    }
}
