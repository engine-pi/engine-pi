package blockly_robot.robot.logic.item;

import blockly_robot.robot.data.model.ItemData;

/**
 * Ein Gegenstand auf dem Gitter.
 *
 * @see ItemData
 */
public class Item
{
    private ItemData data;

    private ItemController controller;

    private int row;

    private int col;

    public Item(ItemData data)
    {
        try
        {
            this.data = (ItemData) data.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
    }

    public void setPosition(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    /**
     * Gibt die Nummer (ID) des Gegenstands.
     */
    public int getNum()
    {
        return data.num;
    }

    /**
     * Gibt den eindeutigen Namen, der den Gegenstand identifiziert. Zum
     * Beispiel: <code>candle</code>
     */
    public String getType()
    {
        return data.type;
    }

    /**
     * Gibt an, ob der Gegenstand eine Kiste darstellt.
     */
    public boolean isContainer()
    {
        return data.isContainer;
    }

    /**
     * Returns the file path of the item.
     *
     * @return The file path in the format "images//contexts/{img}".
     */
    public String getFilePath()
    {
        if (data.img == null)
        {
            return null;
        }
        return "images/contexts/%s".formatted(data.img);
    }

    /**
     * Gibt an, ob der Gegenstand einen Ausgang darstellt.
     */
    public boolean isExit()
    {
        return data.isExit;
    }

    /**
     * Gibt an, ob der Gegenstand einen Laser darstellt.
     */
    public boolean isLaser()
    {
        return data.isLaser;
    }

    /**
     * Gibt an, ob der Gegenstand ein Hindernis darstellt.
     */
    public boolean isObstacle()
    {
        return data.isObstacle;
    }

    public boolean isPaint()
    {
        return data.isPaint;
    }

    /**
     * Gibt an, ob der Gegenstand einen Wurfkörper darstellt.
     */
    public boolean isProjectile()
    {
        return data.isProjectile;
    }

    /**
     * Gibt an, ob der Gegenstand eine Kiste darstellt.
     */
    public boolean isPushable()
    {
        return data.isPushable;
    }

    public void setController(ItemController controller)
    {
        if (this.controller != null)
        {
            throw new IllegalStateException("Controller already set");
        }
        this.controller = controller;
    }

    /**
     * Gibt an, ob der Gegenstand einen Roboter darstellt.
     */
    public boolean isRobot()
    {
        return data.isRobot;
    }

    /**
     * Gibt an, ob der Gegenstand eingesammelt werden kann.
     */
    public boolean isWithdrawable()
    {
        return data.isWithdrawable;
    }

    public boolean isAutoWithdrawable()
    {
        return isWithdrawable() && data.autoWithdraw;
    }

    public void add(int row, int col)
    {
        if (controller != null)
        {
            controller.add(row, col);
        }
    }

    public void add()
    {
        if (controller != null)
        {
            controller.add(row, col);
        }
    }

    public void move(int row, int col)
    {
        if (controller != null)
        {
            controller.move(row, col);
        }
    }

    public void withdraw()
    {
        if (controller != null)
        {
            controller.withdraw();
        }
    }
}
