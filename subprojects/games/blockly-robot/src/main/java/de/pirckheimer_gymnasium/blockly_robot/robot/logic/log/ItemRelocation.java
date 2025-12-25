package de.pirckheimer_gymnasium.blockly_robot.robot.logic.log;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.Item;

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

    public ItemRelocation setItem(Item item)
    {
        this.item = item;
        return this;
    }

    public Item getItem()
    {
        return item;
    }
}
