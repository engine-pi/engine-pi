package de.pirckheimer_gymnasium.engine_pi_demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;

/**
 * Demonstriert die Methode
 * {@link de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener#onFrameUpdate(double)}.
 */
public class FrameUpdateListenerDemo extends Scene
        implements FrameUpdateListener
{
    public FrameUpdateListenerDemo()
    {
        add(new TextActor());
        addLayer(new MyLayer());
    }

    private class MyLayer extends Layer implements FrameUpdateListener
    {
        @Override
        public void onFrameUpdate(double pastTime)
        {
            System.out.println("Layer: " + pastTime);
        }
    }

    private class TextActor extends Text implements FrameUpdateListener
    {
        public TextActor()
        {
            super("Text Actor", 2);
            setCenter(0, 0);
        }

        @Override
        public void onFrameUpdate(double pastTime)
        {
            System.out.println("Actor: " + pastTime);
        }
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        System.out.println("Scene: " + pastTime);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(700, 200, new FrameUpdateListenerDemo());
    }
}
