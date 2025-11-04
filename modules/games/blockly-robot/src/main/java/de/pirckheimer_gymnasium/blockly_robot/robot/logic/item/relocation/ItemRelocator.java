package de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.relocation;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.context.Context;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.Item;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.ItemCreator;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.log.ItemRelocation;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation.Coords;

abstract class ItemRelocator
{
    protected Context context;

    protected ItemCreator itemCreator;

    public ItemRelocator(Context context)
    {
        this.context = context;
        this.itemCreator = context.getItemCreator();
    }

    protected ItemRelocation reportItemRelocation(String name, Item item)
    {
        var action = new ItemRelocation(name, item);
        return action;
    }

    protected Item createItem(int num)
    {
        return itemCreator.create(num);
    }

    protected Item createItem(String type)
    {
        return itemCreator.create(type);
    }

    protected Item drop(Coords coords, int itemNum)
    {
        return drop(coords, itemCreator.create(itemNum));
    }

    protected Item drop(Coords coords, String type)
    {
        return drop(coords, itemCreator.create(type));
    }

    protected Item drop(Coords coords, Item item)
    {
        item.setPosition(coords.getRow(), coords.getCol());
        context.get(coords.getRow(), coords.getCol()).add(item);
        return item;
    }
}
