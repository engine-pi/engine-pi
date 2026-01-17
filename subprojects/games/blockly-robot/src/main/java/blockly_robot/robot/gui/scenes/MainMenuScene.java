package blockly_robot.robot.gui.scenes;

import pi.Game;
import pi.Scene;
import pi.Rectangle;
import pi.Text;
import pi.graphics.geom.Bounds;
import blockly_robot.robot.gui.Color;
import blockly_robot.robot.gui.Controller;
import blockly_robot.robot.gui.State;
import blockly_robot.robot.gui.TextMaker;

public class MainMenuScene extends Scene implements WindowScene
{
    private final double FONT_SIZE = 0.8f;

    private final Color AREA_COLOR = new Color("#99d422");

    private final double INITIAL_X = 0;

    private final double INITIAL_Y = 0;

    private final double RECTANGLE_WIDTH = 20;

    /**
     * aktuelle y-Position.
     */
    private double y = INITIAL_X;

    /**
     * aktuelle x-Position.
     */
    private double x = INITIAL_Y;

    class ColoredArea
    {
        private final String main;

        public ColoredArea(String main, double x, double y)
        {
            this.main = main;
            Rectangle rectangle = createRectangle();
            Text text = createText(main);
            rectangle.anchor(x - 1, y - FONT_SIZE / 2);
            text.anchor(x, y);
            add(rectangle, text);
        }

        private Rectangle createRectangle()
        {
            Rectangle rectangle = new Rectangle(RECTANGLE_WIDTH, FONT_SIZE * 2);
            double BORDER_RADIUS = 0.3f;
            rectangle.borderRadius(BORDER_RADIUS);
            rectangle.color(AREA_COLOR);
            rectangle.addMouseClickListener((vector, mouseButton) -> {
                if (rectangle.contains(vector))
                {
                    Controller
                        .launchScene((WindowScene) new SubMenuScene(main));
                }
            });
            rectangle.addFrameUpdateListener((deltaSeconds) -> {
                if (rectangle.contains(Game.mousePosition()))
                {
                    rectangle.opacity(0.5f);
                }
                else
                {
                    rectangle.opacity(1f);
                }
            });
            return rectangle;
        }

        private Text createText(String content)
        {
            Text text = new Text(content);
            text.height(FONT_SIZE).font(TextMaker.bold).color(Color.BLACK);
            return text;
        }
    }

    public MainMenuScene()
    {
        State.menu.getMain().forEach((main, submenu) -> {
            new ColoredArea(main, x, y);
            y -= (float) (2.5 * FONT_SIZE);
        });
    }

    public Bounds getWindowBounds()
    {
        return new Bounds(INITIAL_X - 2, y, RECTANGLE_WIDTH + 2,
                INITIAL_Y - y + 2);
    }

    public String getTitle()
    {
        return "Trainingsaufgaben";
    }

    public static void launch()
    {
        Controller.launchScene((WindowScene) new MainMenuScene());
    }

    public static void main(String[] args)
    {
        launch();
    }
}
