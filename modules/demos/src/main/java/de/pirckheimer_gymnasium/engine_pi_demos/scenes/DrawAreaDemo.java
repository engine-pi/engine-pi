package de.pirckheimer_gymnasium.engine_pi_demos.scenes;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class DrawAreaDemo extends Scene
{
    Graphics2D g;

    public void draw(int x, int y)
    {
        if (g == null)
        {
            g = getDrawArea();
            g.setColor(colors.get("red"));
        }
        g.fillOval(x, y, 4, 4);
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
