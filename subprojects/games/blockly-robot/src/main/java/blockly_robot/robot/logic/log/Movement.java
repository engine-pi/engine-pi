package blockly_robot.robot.logic.log;

import blockly_robot.robot.logic.navigation.Compass;
import blockly_robot.robot.logic.navigation.Coords;
import blockly_robot.robot.logic.navigation.DirectionalCoords;
import blockly_robot.robot.logic.robot.ErrorMessages;
import blockly_robot.robot.logic.robot.VirtualRobot;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Represents a movement made by a robot.
 */
public class Movement extends Action
{
    private VirtualRobot robot;

    /**
     * Represented the point at which the robot is located before the movement.
     */
    private DirectionalCoords from;

    /**
     * Represented the point at which the robot is located after the movement.
     */
    private DirectionalCoords to;

    /**
     * Indicates whether a change in location has occurred or not.
     */
    private boolean relocated;

    /**
     * 1 ist 90 Grad nach rechts, -1 ist 90 Grad nach links, 2 ist 180 Grad nach
     * rechts.
     */
    private int rotation;

    private Movement next;

    /**
     * Constructs a Movement object with the specified name and robot.
     *
     * @param name the name of the movement
     * @param robot the virtual robot
     */
    public Movement(String name, VirtualRobot robot)
    {
        super(name);
        from = new DirectionalCoords(robot.row(), robot.col(), robot.dir);
        this.robot = robot;
    }

    @Getter
    public DirectionalCoords to()
    {
        return to;
    }

    /**
     * Sets the 'to' location of the movement and calculates the relocation and
     * rotation values.
     *
     * @return the updated Movement object
     */
    public Movement updateTo()
    {
        return updateTo(robot.row(), robot.col(), robot.dir);
    }

    public Movement updateTo(int toRow, int toCol, Compass toDir)
    {
        to = new DirectionalCoords(toRow, toCol, toDir);
        relocated = from.row() != toRow || from.col() != toCol;
        rotation = ((toDir.number() - from.dir().number() + 1) % 4) - 1;
        return this;
    }

    public boolean hasNext()
    {
        return next != null;
    }

    @Setter
    public void next(Movement next)
    {
        this.next = next;
    }

    @Getter
    public Movement next()
    {
        return next;
    }

    @Getter
    public int rotation()
    {
        return rotation;
    }

    @Setter
    public Movement to(int toRow, int toCol)
    {
        return updateTo(toRow, toCol, from.dir());
    }

    @Setter
    public Movement to(Coords to)
    {
        return to(to.row(), to.col());
    }

    public boolean isRelocated()
    {
        return relocated;
    }

    @Setter
    public Movement error(ErrorMessages error)
    {
        updateTo();
        this.error = error;
        return this;
    }

    @Getter
    @Override
    public String name()
    {
        if (!relocated && !name.equals("turnLeft") && !name.equals("turnRight")
                && !name.equals("turnAround"))
        {
            return "!%s".formatted(name);
        }
        return name;
    }

    /**
     * Returns a string representation of the Movement object.
     *
     * @return a string representation of the Movement object
     *
     * @hidden
     */
    public String toString()
    {
        if (error != null)
        {
            return "Movement [name=%s, error=%s]".formatted(name, error);
        }
        return "Movement [name=%s, from=%s, to=%s, relocated=%s, rotation=%s]"
            .formatted(name, from.summary(), to.summary(), relocated, rotation);
    }
}
