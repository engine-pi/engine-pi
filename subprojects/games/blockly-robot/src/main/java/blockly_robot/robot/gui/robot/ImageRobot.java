package blockly_robot.robot.gui.robot;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import blockly_robot.robot.gui.State;
import blockly_robot.robot.gui.level.AssembledLevel;
import blockly_robot.robot.logic.item.Item;
import blockly_robot.robot.logic.log.ItemRelocation;
import blockly_robot.robot.logic.log.Movement;
import blockly_robot.robot.logic.navigation.Coords;
import blockly_robot.robot.logic.robot.Robot;
import blockly_robot.robot.logic.robot.VirtualRobot;
import pi.Vector;
import pi.actor.Image;
import pi.animation.Interpolator;
import pi.animation.ValueAnimator;
import pi.animation.interpolation.SinusDouble;

public class ImageRobot extends Image implements Robot
{
    private VirtualRobot virtual;

    private AssembledLevel level;

    /**
     * Damit keine neue Bewegung gestartet werden kann, bevor nicht die alte
     * fertig abgelaufen ist.
     */
    private boolean inMotion = false;

    protected double speed = 1f;

    public ImageRobot(String filepath, VirtualRobot virtual,
            AssembledLevel level)
    {
        super(filepath, 1, 1);
        this.virtual = virtual;
        this.level = level;
    }

    public int getRow()
    {
        return virtual.getRow();
    }

    public int getCol()
    {
        return virtual.getCol();
    }

    public Coords getCoords()
    {
        return virtual.getCoords();
    }

    public String[] reportActions()
    {
        return virtual.reportActions();
    }

    public void printActions()
    {
        virtual.printActions();
    }

    public boolean onExit()
    {
        return virtual.onExit();
    }

    public boolean onPaint()
    {
        return virtual.onPaint();
    }

    public boolean obstacleInFront()
    {
        return virtual.obstacleInFront();
    }

    public boolean platformInFront()
    {
        return virtual.platformInFront();
    }

    public boolean platformAbove()
    {
        return virtual.platformAbove();
    }

    private void relocateAnimated(Vector to)
    {
        if (inMotion)
        {
            return;
        }
        inMotion = true;
        Vector from = getCenter();
        Vector vector = new Vector(from, to);
        double duration = (float) 1 / speed / 2;
        animate(duration, progress -> {
            setCenter(from.add(vector.multiply(progress)));
        });
        inMotion = false;
    }

    private Movement performMovement(Movement movement)
    {
        if (movement.getRotation() != 0)
        {
            rotateByAnimated(movement.getRotation() * -90);
        }
        if (movement.isRelocated())
        {
            relocateAnimated(level.translate.toVector(movement.getTo()));
        }
        if (movement.hasNext())
        {
            performMovement(movement.getNext());
        }
        if (movement.hasError())
        {
            wiggleAnimated();
        }
        return movement;
    }

    /**
     * Gehe einen Pixelmeter in Richtung der aktuellen Rotation.
     */
    public Movement forward()
    {
        return performMovement(virtual.forward());
    }

    public Movement backwards()
    {
        return performMovement(virtual.backwards());
    }

    public Movement east()
    {
        return performMovement(virtual.east());
    }

    public Movement south()
    {
        return performMovement(virtual.south());
    }

    public Movement west()
    {
        return performMovement(virtual.west());
    }

    public Movement north()
    {
        return performMovement(virtual.north());
    }

    public boolean onContainer()
    {
        return virtual.onContainer();
    }

    public boolean onWithdrawable()
    {
        return virtual.onWithdrawable();
    }

    private void wait(double seconds)
    {
        try
        {
            Thread.sleep((long) (1000 * seconds));
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    private void wiggleAnimated()
    {
        this.wiggleAnimated(0.3f);
    }

    private void wiggleAnimated(double duration)
    {
        if (inMotion)
        {
            return;
        }
        inMotion = true;
        double rotation = getRotation();
        Vector center = getCenter();
        wait(0.1);
        animate(duration, progress -> {
            setRotation(rotation + progress);
            setCenter(center);
        }, new SinusDouble(0, 45));
        wait(0.1);
        inMotion = false;
    }

    private void rotateByAnimated(double degree)
    {
        if (inMotion)
        {
            return;
        }
        inMotion = true;
        Vector center = getCenter();
        // case EAST: 0;
        // case NORTH: 90;
        // case WEST: 180;
        // case SOUTH: 270;
        // To avoid high rotation numbers
        double start = getRotation() % 360;
        setRotation(start);
        double duration = 1 / speed / 4;
        animate(duration, progress -> {
            setRotation(start + progress * degree);
            setCenter(center);
        });
        inMotion = false;
    }

    public Movement jump()
    {
        return performMovement(virtual.jump());
    }

    public ItemRelocation withdraw()
    {
        return virtual.withdraw();
    }

    private Item paintItem(Item item)
    {
        if (item != null)
        {
            item.setController(level.getItemController(item));
            item.add();
        }
        return item;
    }

    private ItemRelocation performItemRelocation(ItemRelocation relocation)
    {
        if (relocation != null)
        {
            wait(0.5 / speed);
            paintItem(relocation.getItem());
            wait(0.5 / speed);
        }
        return relocation;
    }

    public ItemRelocation dropWithdrawable(int itemNum)
    {
        ItemRelocation action = virtual.dropWithdrawable(itemNum);
        paintItem(action.getItem());
        return action;
    }

    public ItemRelocation drop()
    {
        ItemRelocation action = virtual.drop();
        action.getItem().add();
        return action;
    }

    public ItemRelocation dropPlatformInFront()
    {
        return performItemRelocation(virtual.dropPlatformInFront());
    }

    public ItemRelocation dropPlatformAbove()
    {
        return performItemRelocation(virtual.dropPlatformAbove());
    }

    /**
     * Drehe um 90 Grad nach links.
     */
    public Movement turnLeft()
    {
        return performMovement(virtual.turnLeft());
    }

    /**
     * Drehe um 90 Grad nach rechts.
     */
    public Movement turnRight()
    {
        return performMovement(virtual.turnRight());
    }

    public Movement turnAround()
    {
        return performMovement(virtual.turnAround());
    }

    private void animate(double duration, Consumer<Double> setter,
            Interpolator<Double> interpolator)
    {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ValueAnimator<Double> animator = new ValueAnimator<>(duration, setter,
                interpolator, this);
        animator.addCompletionListener(value -> {
            setter.accept(value);
            future.complete(null);
        });
        addFrameUpdateListener(animator);
        try
        {
            future.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void animate(double duration, Consumer<Double> setter)
    {
        animate(duration, setter, State.interpolator);
    }
}
