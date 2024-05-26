package rocks.friedrich.engine_omega.examples;

import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Random;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.BodyType;
import rocks.friedrich.engine_omega.actor.Circle;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Eine kleine Demo zum Verhalten vieler partikelähnlicher Physik-Objekte in der
 * Engine.
 * <p>
 * Created by Michael on 11.04.2017.
 */
public class MarbleDemo extends ShowcaseDemo implements KeyListener
{
    class Funnel
    {
        /**
         * Dicke des Trichters
         */
        private static final int THICKNESS = 10;

        /*
         * Länge der schrängen Wand.
         */
        private static final int LENGTH_SLANTED = 200;

        private static final int LENGTH_VERTICAL = 120;

        private static final int NARROW_RADIUS = 50;

        public Funnel()
        {
            // slanted
            Rectangle slantedLeft = new Rectangle(THICKNESS, LENGTH_SLANTED);
            slantedLeft.setPosition(-NARROW_RADIUS + THICKNESS * 0.25,
                    LENGTH_VERTICAL - THICKNESS * 0.75);
            Rectangle slatedRight = new Rectangle(THICKNESS, LENGTH_SLANTED);
            slatedRight.setPosition(NARROW_RADIUS, LENGTH_VERTICAL);
            // vertical
            Rectangle verticalLeft = new Rectangle(THICKNESS, LENGTH_VERTICAL);
            verticalLeft.setPosition(-NARROW_RADIUS, 0);
            Rectangle verticalRight = new Rectangle(THICKNESS, LENGTH_VERTICAL);
            verticalRight.setPosition(NARROW_RADIUS, 0);
            Rectangle[] allRectangles = new Rectangle[] { slantedLeft,
                    slatedRight, verticalLeft, verticalRight };
            for (Rectangle r : allRectangles)
            {
                r.setColor(Color.WHITE);
                add(r);
                r.setBodyType(BodyType.STATIC);
            }
            setGravity(new Vector(0, -30));
            slantedLeft.setRotation(45);
            slatedRight.setRotation(-45);
        }
    }

    /**
     * Der Boden des Trichters. Kann durchlässig gemacht werden.
     */
    private final Rectangle ground;

    public MarbleDemo(Scene parent)
    {
        super(parent);
        // Trichter
        new Funnel();
        repeat(0.2, () -> {
            Circle marble = makeMarble();
            add(marble);
            marble.setBodyType(BodyType.DYNAMIC);
            marble.setPosition(0, 500);
            marble.applyImpulse(new Vector(Random.range() * 200 - 100,
                    Random.range() * -300 - 100));
        });
        ground = new Rectangle(Funnel.NARROW_RADIUS * 2 + Funnel.THICKNESS,
                Funnel.THICKNESS);
        ground.setPosition(-Funnel.NARROW_RADIUS, -Funnel.THICKNESS);
        ground.setBodyType(BodyType.STATIC);
        add(ground);
        getCamera().setZoom(0.5);
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_X)
        {
            if (ground.getBodyType() == BodyType.STATIC)
            {
                ground.setBodyType(BodyType.SENSOR);
                ground.setColor(new Color(255, 255, 255, 100));
            }
            else
            {
                ground.setBodyType(BodyType.STATIC);
                ground.setColor(Color.WHITE);
            }
        }
        else if (e.getKeyCode() == KeyEvent.VK_Y)
        {
            setGravity(new Vector(40, 0));
        }
    }

    @Override
    public void onKeyUp(KeyEvent e)
    {
        // Ignore.
    }

    /**
     * Erstellt eine neue Murmel.
     *
     * @return eine Murmel. Farbe und Größe variieren.
     */
    public Circle makeMarble()
    {
        class Marble extends Circle implements FrameUpdateListener
        {
            public Marble(float diameter)
            {
                super(diameter);
            }

            @Override
            public void onFrameUpdate(double deltaSeconds)
            {
                if (this.getCenter().getLength() > 1000)
                {
                    MarbleDemo.this.remove(this);
                }
            }
        }
        Circle marble = new Marble(Random.range(20) + 10);
        marble.setBodyType(BodyType.DYNAMIC);
        marble.setGravityScale(2);
        marble.setColor(new Color(Random.range(255), Random.range(255),
                Random.range(255)));
        return marble;
    }

    public static void main(String[] args)
    {
        Game.start(1000, 800, new MarbleDemo(null));
    }
}
