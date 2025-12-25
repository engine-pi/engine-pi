package de.pirckheimer_gymnasium.blockly_robot.robot.logic.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import de.pirckheimer_gymnasium.blockly_robot.robot.data.model.ItemData;

import java.util.Iterator;

/**
 * Aufeinander gestapelte Gegenstände.
 */
public class StackedItems implements Iterable<Item>
{
    private List<Item> items;

    public StackedItems()
    {
        this.items = new ArrayList<>();
    }

    public StackedItems(Item item)
    {
        this();
        add(item);
    }

    public StackedItems(ItemData itemData)
    {
        this();
        add(itemData);
    }

    @Override
    public Iterator<Item> iterator()
    {
        return items.iterator();
    }

    public void add(Item item)
    {
        if (item != null)
        {
            items.add(item);
        }
    }

    public void add(ItemData itemData)
    {
        if (itemData != null)
        {
            add(new Item(itemData));
        }
    }

    public boolean has(Predicate<Item> predicate)
    {
        for (Item item : items)
        {
            if (predicate.test(item))
            {
                return true;
            }
        }
        return false;
    }

    public boolean isContainer()
    {
        return has(item -> item.isContainer());
    }

    public boolean isExit()
    {
        return has(item -> item.isExit());
    }

    public boolean isObstacle()
    {
        return has(item -> item.isObstacle());
    }

    public boolean isPaint()
    {
        return has(item -> item.isPaint());
    }

    public boolean isWithdrawable()
    {
        return has(item -> item.isWithdrawable());
    }

    public Item bottom()
    {
        if (!items.isEmpty())
        {
            return items.get(0);
        }
        return null;
    }

    public Item top()
    {
        if (!items.isEmpty())
        {
            return items.get(items.size() - 1);
        }
        return null;
    }

    private Item withdrawUsingPredicate(Predicate<Item> predicate)
    {
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext())
        {
            Item item = iterator.next();
            if (predicate.test(item))
            {
                iterator.remove();
                return item;
            }
        }
        return null;
    }

    public Item withdraw()
    {
        return withdrawUsingPredicate(item -> item.isWithdrawable());
    }

    public Item withdrawAuto()
    {
        return withdrawUsingPredicate(item -> item.isAutoWithdrawable());
    }
}
