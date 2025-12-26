package blockly_robot.robot.data;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import blockly_robot.robot.data.model.GridInfosData;
import blockly_robot.robot.data.model.MenuData;
import blockly_robot.robot.data.model.TaskData;
import pi.resources.ResourceLoader;

public class JsonLoader
{
    public static TaskData loadTask(String filePath)
            throws StreamReadException, DatabindException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(ResourceLoader.loadAsStream(filePath),
                TaskData.class);
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> loadMenu()
            throws StreamReadException, DatabindException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        var menu = mapper.readValue(
                ResourceLoader.loadAsStream("data/menu.json"), MenuData.class);
        return menu.menu;
    }

    public static LinkedHashMap<String, GridInfosData> loadContexts()
            throws StreamReadException, DatabindException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(
                ResourceLoader.loadAsStream("data/contextParams.json"),
                new TypeReference<LinkedHashMap<String, GridInfosData>>()
                {
                });
    }
}
