package demos.classes.class_scene;

import static pi.Resources.colors;

import java.awt.Color;
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Vector;
import pi.dsa.turtle.PaintingSurfaceScene;
import pi.event.KeyStrokeListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;

public class PaintingSurfaceDemo extends PaintingSurfaceScene
        implements MouseClickListener, KeyStrokeListener
{

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_C:
            getPaintingSurface().clear();
            break;

        case KeyEvent.VK_R:
            getPaintingSurface().fill("red");
            break;

        default:
            break;
        }
    }

    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {

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

        getPaintingSurface().drawCircle(position, 20, color);
    }

    public static void main(String[] args)
    {
        Game.start(new PaintingSurfaceDemo());
    }

}
