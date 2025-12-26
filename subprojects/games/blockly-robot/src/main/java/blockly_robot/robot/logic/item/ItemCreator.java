package blockly_robot.robot.logic.item;

import java.util.HashMap;
import java.util.Map;

import blockly_robot.robot.data.model.ItemData;

/**
 * Ein Speicher für die Daten der Gegenstände (ItemData). Eine Trainingsaufgabe
 * (Task) bedient sich eines Gegenständedatenspeichers (ItemDataStore).
 */
public class ItemCreator
{
    private HashMap<String, ItemData> itemsByName;

    private HashMap<Integer, ItemData> itemsByNumber;

    public ItemCreator(Map<String, ItemData> items)
    {
        itemsByName = new HashMap<>();
        itemsByNumber = new HashMap<>();
        items.forEach((type, itemData) -> {
            if (itemData.type == null)
            {
                itemData.type = type;
            }
        });
        for (ItemData item : items.values())
        {
            if (item.type != null)
            {
                itemsByName.put(item.type, item);
            }
            if (item.num != 0)
            {
                itemsByNumber.put(item.num, item);
            }
        }
    }

    private ItemData get(int num)
    {
        return itemsByNumber.get(num);
    }

    private ItemData get(String type)
    {
        return itemsByName.get(type);
    }

    /**
     * Creates a new Item object based on the provided ItemData.
     *
     * @param itemData The ItemData object to create the Item from.
     *
     * @return The newly created Item object, or null if itemData is null or
     *     cloning is not supported.
     */
    private Item createItem(ItemData itemData)
    {
        if (itemData == null)
        {
            return null;
        }
        return new Item(itemData);
    }

    /**
     * Creates an item with the given number.
     *
     * @param num the number of the item
     *
     * @return the created item
     */
    public Item create(int num)
    {
        return createItem(get(num));
    }

    /**
     * Creates an item with the given name.
     *
     * @param type the name of the item
     *
     * @return the created item
     */
    public Item create(String type)
    {
        return createItem(get(type));
    }
}
