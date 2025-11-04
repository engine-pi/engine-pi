package de.pirckheimer_gymnasium.blockly_robot.robot.gui;

import java.awt.Font;

import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.Resources;

public class TextMaker
{
    public static Font loadTitillium(String style)
    {
        return Resources.fonts
                .get("fonts/titilium/TitilliumWeb-%s.ttf".formatted(style));
    }

    public static Font regular = loadTitillium("Regular");

    public static Font bold = loadTitillium("Bold");

    public static Text createText(String content, double fontSize)
    {
        Text text = new Text(content, fontSize);
        text.setFont(regular);
        text.setColor(Color.BLACK);
        return text;
    }

    public static Text createText(String text)
    {
        return createText(text, 1);
    }
}
