package blockly_robot.robot.gui;

import static pi.Controller.fonts;

import java.awt.Font;

import pi.Text;

public class TextMaker
{
    public static Font loadTitillium(String style)
    {
        return fonts.get("fonts/titilium/TitilliumWeb-%s.ttf".formatted(style));
    }

    public static Font regular = loadTitillium("Regular");

    public static Font bold = loadTitillium("Bold");

    public static Text createText(String content, double fontSize)
    {
        Text text = new Text(content, fontSize);
        text.font(regular);
        text.color(Color.BLACK);
        return text;
    }

    public static Text createText(String text)
    {
        return createText(text, 1);
    }
}
