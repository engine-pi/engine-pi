package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.graphics.boxes.FramedTextBox;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 * @since 0.40.0
 */
public class TurtleStatistics
{

    /**
     * @since 0.40.0
     */
    private double traveledDistance;

    /**
     * @since 0.40.0
     */
    private FramedTextBox textBox;

    /**
     * @since 0.40.0
     */
    public TurtleStatistics()
    {
        traveledDistance = 0;
        textBox = new FramedTextBox(String.valueOf(traveledDistance))
                .borderThickness(0).backgroundColor(colors.get("grey", 50))
                .textColor(colors.get("black"));
        textBox.anchor(20, 20);
    }

    /**
     * @since 0.40.0
     */
    public void addTraveledDistance(double distance)
    {
        traveledDistance += distance;
    }

    /**
     * @since 0.40.0
     */
    public void render(Graphics2D g)
    {
        textBox.content(TextUtil.roundNumber(traveledDistance));
        textBox.measure();
        textBox.render(g);
    }

}
