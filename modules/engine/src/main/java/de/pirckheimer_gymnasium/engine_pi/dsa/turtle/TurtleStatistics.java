package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FramedTextBox;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

public class TurtleStatistics
{
    private double traveledDistance;

    private FramedTextBox textBox;

    public TurtleStatistics()
    {
        traveledDistance = 0;
        textBox = new FramedTextBox(String.valueOf(traveledDistance))
                .borderThickness(0).backgroundColor(colors.get("grey", 50))
                .textColor(colors.get("black"));
        textBox.anchor(20, 20);
    }

    public void addTraveledDistance(double distance)
    {
        traveledDistance += distance;
    }

    public void render(Graphics2D g)
    {
        textBox.content(TextUtil.roundNumber(traveledDistance));
        textBox.measure();
        textBox.render(g);
    }

}
