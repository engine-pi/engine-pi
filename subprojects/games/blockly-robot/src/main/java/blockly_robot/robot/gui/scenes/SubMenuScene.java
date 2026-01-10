package blockly_robot.robot.gui.scenes;

import pi.Game;
import pi.Scene;
import pi.Text;
import pi.graphics.geom.Bounds;
import blockly_robot.robot.gui.Color;
import blockly_robot.robot.gui.Controller;
import blockly_robot.robot.gui.State;
import blockly_robot.robot.gui.TextMaker;

public class SubMenuScene extends Scene implements WindowScene
{
    private final double FONT_SIZE = 0.8f;

    private final double INITIAL_X = 0;

    private final double INITIAL_Y = 0;

    /**
     * aktuelle x-Position.
     */
    private double x = INITIAL_X;

    /**
     * aktuelle y-Position.
     */
    private double y = INITIAL_Y;

    private final String main;

    public SubMenuScene(String main)
    {
        this.main = main;
        State.menu.getSub(main).forEach((sub, id) -> {
            Text text = new Text(sub, FONT_SIZE);
            text.font(TextMaker.regular);
            if (id != null)
            {
                text.color(Color.BLACK);
                text.addMouseClickListener((vector, mouseButton) -> {
                    if (text.contains(vector))
                    {
                        LevelsScene.launch(id);
                    }
                });
                text.addFrameUpdateListener((deltaSeconds) -> {
                    if (text.contains(Game.mousePosition()))
                    {
                        text.opacity(0.5f);
                    }
                    else
                    {
                        text.opacity(1f);
                    }
                });
            }
            else
            {
                text.color(Color.GRAY);
            }
            text.anchor(x, y);
            add(text);
            y -= 2 * FONT_SIZE;
        });
    }

    public Bounds getWindowBounds()
    {
        return new Bounds(INITIAL_X - 2, y, 12, INITIAL_Y - y + 2);
    }

    public String getTitle()
    {
        return main;
    }

    public static void main(String[] args)
    {
        Controller.launchScene((WindowScene) new SubMenuScene(
                "Bedingte Anweisungen – Übungen"));
    }
}
