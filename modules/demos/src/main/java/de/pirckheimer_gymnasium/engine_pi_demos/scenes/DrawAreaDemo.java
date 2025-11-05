package de.pirckheimer_gymnasium.engine_pi_demos.scenes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.event.MouseButton;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener;

public class DrawAreaDemo extends Scene implements MouseClickListener
{
    Graphics2D g;

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {

        if (g == null)
        {
            g = getDrawArea();
        }

        Vector positionPx = getMainLayer()
                .translateWorldPointToFramePxCoordinates(position);

        // Der linke Mausklick zeichnet einen blauen Kreis, der rechte einen
        // grünen.
        Color color;
        if (button == MouseButton.LEFT)
        {
            color = colors.get("blue");
        }
        else
        {
            color = colors.get("green");
        }
        g.setColor(color);

        drawCenterCircle(positionPx, 20);
        System.out.println(positionPx);
    }

    private void drawCenterCircle(int x, int y, int circleSize)
    {
        g.fillOval(x - circleSize / 2, y - circleSize / 2, circleSize,
                circleSize);
    }

    private void drawCenterCircle(Vector position, int circleSize)
    {
        drawCenterCircle((int) position.getX(), (int) position.getY(),
                circleSize);
    }

    public void draw(int x, int y)
    {
        if (g == null)
        {
            g = getDrawArea();
        }
        g.setColor(colors.get("red"));
        drawCenterCircle(x, y, 2);
    }

    public static void main(String[] args)
    {
        DrawAreaDemo scene = new DrawAreaDemo();
        Game.start(scene);
        Game.getRenderPanel().addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
                // ignore
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                System.out.println(e);
                scene.draw(e.getX(), e.getY());
            }

        });

    }

}
