package de.pirckheimer_gymnasium.blockly_robot.robot.gui.map;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.context.Context;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.Item;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.item.StackedItems;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Image;

public class ItemMapPainter
{
    private Context context;

    private CoordinateSystemTranslator translate;

    public ItemMapPainter(Context context)
    {
        this.context = context;
        translate = new CoordinateSystemTranslator(context.getRows(),
                context.getCols());
    }

    public void paint(Scene scene, double x, double y)
    {
        translate.setPosition(x, y);
        for (int row = 0; row < context.getRows(); row++)
        {
            for (int col = 0; col < context.getCols(); col++)
            {
                paintStackedItems(scene, col, row, context.get(row, col));
            }
        }
    }

    public void paint(Scene scene)
    {
        paint(scene, 0, 0);
    }

    private void paintStackedItems(Scene scene, int col, int row,
            StackedItems items)
    {
        var vector = translate.toVector(row, col);
        for (Item item : items)
        {
            paintItem(scene, vector.getX(), vector.getY(), item);
        }
    }

    private void paintItem(Scene scene, double x, double y, Item item)
    {
        Image image = createImage(item, scene);
        if (image == null)
        {
            return;
        }
        image.setPosition(x, y);
        scene.add(image);
    }

    private Image createImage(Item item, Scene scene)
    {
        String filePath = item.getFilePath();
        if (filePath != null)
        {
            Image image = new Image(filePath, 1, 1);
            item.setController(
                    new GraphicalItemController(item, image, translate, scene));
            return image;
        }
        return null;
    }
}
