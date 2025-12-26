package blockly_robot.robot.logic.navigation;

import static pi.Direction.DOWN;
import static pi.Direction.LEFT;
import static pi.Direction.RIGHT;
import static pi.Direction.UP;

import pi.Direction;

/**
 * Rough direction of the four main cardinal points /
 *
 * <i>Grobe Himmelsrichtung der vier Haupthimmelsrichtungen</i>
 */
public enum Compass
{
    /**
     * 0 in <a href=
     * "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3355">blocklyRobot_lib-1.1.js</a>
     */
    EAST(0),
    /**
     * 1 in <a href=
     * "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3341">blocklyRobot_lib-1.1.js</a>
     */
    SOUTH(1),
    /**
     * 2 in <a href=
     * "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3369">blocklyRobot_lib-1.1.js</a>
     */
    WEST(2),
    /**
     * 3 in <a href=
     * "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3327">blocklyRobot_lib-1.1.js</a>
     */
    NORTH(3);

    private int number;

    private Compass(int number)
    {
        this.number = number;
    }

    /**
     * Returns the number associated with the cardinal point (0: EAST, 1: SOUTH,
     * 2: WEST; 3: NORTH).
     *
     * @return the number associated with the cardinal point
     */
    public int getNumber()
    {
        return number;
    }

    /**
     * Konvertiere eine Himmelrichtungsnummer in den Aufzählungstyp.
     */
    public static Compass fromNumber(int number)
    {
        switch (number)
        {
        case 0:
            return EAST;

        case 1:
            return SOUTH;

        case 2:
            return WEST;

        case 3:
            return NORTH;

        default:
            throw new Error("Unknown direction number");
        }
    }

    /**
     * @param diff Positive Werte im Uhrzeigersinn.
     */
    public Compass rotate(int diff)
    {
        if (diff < 0)
        {
            throw new Error(
                    "Specify only positive value to specify the rotation.");
        }
        return Compass.fromNumber((number + diff) % 4);
    }

    /**
     * Konvertiere eine Himmelrichtungsnummer in den Aufzählungstyp.
     */
    public static Direction toDirection(Compass compass)
    {
        switch (compass)
        {
        case EAST:
            return RIGHT;

        case SOUTH:
            return DOWN;

        case NORTH:
            return UP;

        case WEST:
            return LEFT;

        default:
            throw new Error("Unknown compass direction");
        }
    }
}
