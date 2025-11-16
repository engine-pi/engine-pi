package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import de.pirckheimer_gymnasium.engine_pi.Vector;

// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java

public class TurtleController implements TurtleDrawControl
{
    protected TurtleScene scene;

    public TurtleBackgroundController background;

    public TurtlePen pen;

    public TurtleController()
    {
        scene = new TurtleScene();
        background = new TurtleBackgroundController(scene);
        pen = scene.pen;
    }

    @Override
    public void move()
    {
        scene.move();
    }

    @Override
    public void move(double distance)
    {
        scene.move(distance);
    }

    @Override
    public void setPosition(Vector position)
    {
        scene.setPosition(position);
    }

    @Override
    public void setPosition(double x, double y)
    {
        scene.setPosition(x, y);
    }

    @Override
    public void rotate(double rotation)
    {
        scene.rotate(rotation);
    }

    @Override
    public void setDirection(double direction)
    {
        scene.setDirection(direction);
    }

    @Override
    public void lowerPen()
    {
        scene.lowerPen();
    }

    @Override
    public void liftPen()
    {
        scene.liftPen();
    }
}
