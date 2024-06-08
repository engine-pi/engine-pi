package de.pirckheimer_gymnasium.engine_pi.demos.event;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Layer;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;

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
        public void onFrameUpdate(double deltaSeconds)
        {
            System.out.println("Layer: " + deltaSeconds);
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
        public void onFrameUpdate(double deltaSeconds)
        {
            System.out.println("Actor: " + deltaSeconds);
        }
    }

    @Override
    public void onFrameUpdate(double deltaSeconds)
    {
        System.out.println("Scene: " + deltaSeconds);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(700, 200, new FrameUpdateListenerDemo());
    }
}
