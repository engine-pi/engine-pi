package de.pirckheimer_gymnasium.blockly_robot.robot.gui;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty;
import pi.Scene;
import pi.actor.Text;

public class Painter
{
    public static void paintVersionHeading(Scene scene, double x, double y,
            Difficulty difficulty)
    {
        Text text = TextMaker.createText(
                "Version " + "*".repeat(difficulty.getIndex() + 2), 1);
        text.setPosition(x, y);
        scene.add(text);
    }
}
