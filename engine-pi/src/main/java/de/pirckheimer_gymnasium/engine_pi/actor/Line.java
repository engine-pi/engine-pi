package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;

/**
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class Line extends Actor
{
    public Line()
    {
        super(() -> FixtureBuilder.rectangle(300, 300));
    }

    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        Stroke stroke = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_MITER);
        g.setStroke(stroke);
        g.setColor(Color.GREEN);
        g.drawLine(120, 50, 360, 50);
    }

    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                add(new Line());
            }
        });
    }
}
