package blockly_robot.robot.logic.menu;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import blockly_robot.robot.data.JsonLoader;

/**
 * Represents a menu that contains main and sub menus. The menu data is loaded
 * from a JSON file.
 */
public class Menu
{
    private LinkedHashMap<String, LinkedHashMap<String, String>> data;

    /**
     * Constructs a Menu object and loads the menu data from a JSON file.
     */
    public Menu()
    {
        try
        {
            data = JsonLoader.loadMenu();
        }
        catch (StreamReadException e)
        {
            e.printStackTrace();
        }
        catch (DatabindException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main menu.
     *
     * @return the main menu as a LinkedHashMap
     */
    public LinkedHashMap<String, LinkedHashMap<String, String>> getMain()
    {
        return data;
    }

    /**
     * Returns the sub menu for the specified main menu.
     *
     * @param menu the main menu
     *
     * @return the sub menu as a LinkedHashMap
     */
    public LinkedHashMap<String, String> getSub(String menu)
    {
        return data.get(menu);
    }

    /**
     * Returns the ID of the specified submenu in the specified main menu.
     *
     * @param menu the main menu
     * @param submenu the submenu
     *
     * @return the ID of the submenu as a String
     */
    public String getId(String menu, String submenu)
    {
        return data.get(menu).get(submenu);
    }
}
