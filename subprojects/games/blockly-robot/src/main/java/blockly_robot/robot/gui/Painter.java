package blockly_robot.robot.gui;

import blockly_robot.robot.logic.level.Difficulty;
import pi.Scene;
import pi.Text;

public class Painter
{
    public static void paintVersionHeading(Scene scene, double x, double y,
            Difficulty difficulty)
    {
        Text text = TextMaker.createText(
                "Version " + "*".repeat(difficulty.getIndex() + 2), 1);
        text.position(x, y);
        scene.add(text);
    }
}
