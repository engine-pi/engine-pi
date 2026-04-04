package blockly_robot.robot.logic.log;

import blockly_robot.robot.logic.item.Item;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Withdrawing or dropping an item.
 */
public class ItemRelocation extends Action
{
    private Item item;

    public ItemRelocation(String name, Item item)
    {
        this(name);
        this.item = item;
    }

    public ItemRelocation(String name)
    {
        super(name);
    }

    @Getter
    public Item item()
    {
        return item;
    }

    @Setter
    public ItemRelocation item(Item item)
    {
        this.item = item;
        return this;
    }
}
