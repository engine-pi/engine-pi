package blockly_robot.robot.gui.map;

import blockly_robot.robot.gui.level.LevelAssembler;
import blockly_robot.robot.logic.item.Item;
import blockly_robot.robot.logic.item.ItemController;
import pi.Scene;
import pi.actor.Image;

public class GraphicalItemController implements ItemController
{
    Image image;

    Item item;

    Scene scene;

    CoordinateSystemTranslator translate;

    public GraphicalItemController(Item item, Image image,
            CoordinateSystemTranslator translator, Scene scene)
    {
        this.item = item;
        this.image = image;
        this.scene = scene;
        translate = translator;
    }

    public GraphicalItemController(Item item,
            CoordinateSystemTranslator translator, Scene scene)
    {
        this.item = item;
        this.scene = scene;
        translate = translator;
    }

    @Override
    public void add(int row, int col)
    {
        var vector = translate.toVector(row, col);
        if (image == null)
        {
            image = new Image(item.getFilePath(), 1, 1);
        }
        image.position(Math.round(vector.x()) - LevelAssembler.SHIFT,
                Math.round(vector.y()) - LevelAssembler.SHIFT);
        scene.add(image);
    }

    public void remove()
    {
        scene.remove(image);
    }

    @Override
    public void move(int row, int col)
    {
    }

    public void withdraw()
    {
        remove();
    }
}
