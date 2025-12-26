package blockly_robot.robot.logic.log;

import blockly_robot.robot.logic.navigation.Compass;
import blockly_robot.robot.logic.navigation.Coords;
import blockly_robot.robot.logic.navigation.DirectionalCoords;
import blockly_robot.robot.logic.robot.ErrorMessages;
import blockly_robot.robot.logic.robot.VirtualRobot;

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
        from = new DirectionalCoords(robot.getRow(), robot.getCol(), robot.dir);
        this.robot = robot;
    }

    public DirectionalCoords getTo()
    {
        return to;
    }

    /**
     * Sets the 'to' location of the movement and calculates the relocation and
     * rotation values.
     *
     * @return the updated Movement object
     */
    public Movement setTo()
    {
        return setTo(robot.getRow(), robot.getCol(), robot.dir);
    }

    public Movement setTo(int toRow, int toCol, Compass toDir)
    {
        to = new DirectionalCoords(toRow, toCol, toDir);
        relocated = from.getRow() != toRow || from.getCol() != toCol;
        rotation = ((toDir.getNumber() - from.getDir().getNumber() + 1) % 4)
                - 1;
        return this;
    }

    public boolean hasNext()
    {
        return next != null;
    }

    public void setNext(Movement next)
    {
        this.next = next;
    }

    public Movement getNext()
    {
        return next;
    }

    public int getRotation()
    {
        return rotation;
    }

    public Movement setTo(int toRow, int toCol)
    {
        return setTo(toRow, toCol, from.getDir());
    }

    public Movement setTo(Coords to)
    {
        return setTo(to.getRow(), to.getCol());
    }

    public boolean isRelocated()
    {
        return relocated;
    }

    public Movement setError(ErrorMessages error)
    {
        setTo();
        this.error = error;
        return this;
    }

    /**
     * Returns a string representation of the Movement object.
     *
     * @return a string representation of the Movement object
     */
    public String toString()
    {
        if (error != null)
        {
            return "Movement [name=%s, error=%s]".formatted(name, error);
        }
        return "Movement [name=%s, from=%s, to=%s, relocated=%s, rotation=%s]"
                .formatted(name, from.getSummary(), to.getSummary(), relocated,
                        rotation);
    }

    @Override
    public String getName()
    {
        if (!relocated && !name.equals("turnLeft") && !name.equals("turnRight")
                && !name.equals("turnAround"))
        {
            return "!%s".formatted(name);
        }
        return name;
    }
}
