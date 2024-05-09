package rocks.friedrich.engine_omega.examples.event;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.Layer;

public class FrameUpdateListenerExample extends Scene
        implements FrameUpdateListener
{
    public FrameUpdateListenerExample()
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
        Game.start(700, 200, new FrameUpdateListenerExample());
    }
}
