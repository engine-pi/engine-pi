package blockly_robot.robot.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

class JsonLoaderTest
{
    @Test
    void testLoadTask()
            throws StreamReadException, DatabindException, IOException
    {
        var task = JsonLoader.loadTask(
            "data/tasks/conditionals_excercises/light_all_candles.json");
        assertEquals(task.id, "20-DE-13-Kerzen-einfach");
    }

    @Test
    void testLoadMenu()
            throws StreamReadException, DatabindException, IOException
    {
        var menu = JsonLoader.loadMenu();
        assertEquals(
            menu.get("Bedingte Anweisungen – Übungen")
                .get("Zünde alle Kerzen an"),
            "conditionals_excercises/light_all_candles");
    }

    @Test
    void testLoadContexts()
            throws StreamReadException, DatabindException, IOException
    {
        var contexts = JsonLoader.loadContexts();
        assertEquals(contexts.get("castle").backgroundColor, "#1f003c");
    }
}
