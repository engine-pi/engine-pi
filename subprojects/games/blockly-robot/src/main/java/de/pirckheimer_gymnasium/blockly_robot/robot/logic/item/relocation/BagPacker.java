package de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.relocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.context.Context;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.Item;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.log.ItemRelocation;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation.Coords;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot.ErrorMessages;

/**
 * Gegenstände in die Tasche (Bag) packen.
 */
public class BagPacker extends ItemRelocator implements Iterable<Item>
{
    /**
     * Behälter in dem Objekte eingesammelt (withdraw) werden können.
     *
     * https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2458-L2478
     */
    private List<Item> bag = new ArrayList<>();

    public BagPacker(Context context)
    {
        super(context);
        var bagInit = context.getTask().getBagInit();
        if (bagInit != null)
        {
            for (int i = 0; i < bagInit.count; i++)
            {
                bag.add(createItem(bagInit.type));
            }
        }
    }

    @Override
    public Iterator<Item> iterator()
    {
        return bag.iterator();
    }

    public int getBagSize()
    {
        return bag.size();
    }

    private ItemRelocation dropWithdrawable(Coords coords, Item item,
            String actionName)
    {
        var action = reportItemRelocation(actionName, item);
        if (context.get(coords).isContainer())
        {
            drop(coords, item);
        }
        else
        {
            action.setItem(null);
        }
        return action;
    }

    public ItemRelocation dropWithdrawable(Coords coords, int itemNum)
    {
        return dropWithdrawable(coords, createItem(itemNum),
                "dropWithdrawable");
    }

    public ItemRelocation dropFromBag(Coords coords)
    {
        if (bag.size() > 0)
        {
            Item item = bag.remove(bag.size() - 1);
            return dropWithdrawable(coords, item, "dropWithdrawableFromBag");
        }
        return null;
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3125-L3164">blocklyRobot_lib-1.1.js
     *     L3125-L3164</a>
     */
    private ItemRelocation withdraw(Coords coords, boolean auto)
    {
        Item item;
        String name;
        if (auto)
        {
            item = context.get(coords).withdrawAuto();
            name = "withdrawAuto";
        }
        else
        {
            item = context.get(coords).withdraw();
            name = "withdraw";
        }
        var action = reportItemRelocation(name, item);
        if (item == null)
        {
            return (ItemRelocation) action
                    .setError(ErrorMessages.WITHDRAWABLES_NOTHING_TO_PICK_UP);
        }
        if (context.getTask().getBagSize() < bag.size() + 1)
        {
            return (ItemRelocation) action
                    .setError(ErrorMessages.WITHDRAWABLES_TOO_MANY_OBJECTS);
        }
        item.withdraw();
        bag.add(item);
        return action;
    }

    public ItemRelocation withdraw(Coords coords)
    {
        return withdraw(coords, false);
    }

    public ItemRelocation withdrawAuto(Coords coords)
    {
        return withdraw(coords, true);
    }
}
