package blockly_robot.robot.logic.navigation;

import pi.Direction;

public class DirectionalCoords extends Coords
{
    private Compass dir;

    public DirectionalCoords(int row, int col, Compass dir)
    {
        super(row, col);
        this.dir = dir;
    }

    public Compass getDir()
    {
        return dir;
    }

    public Direction getDirection()
    {
        return Compass.toDirection(dir);
    }

    public String toString()
    {
        return "DirectionalPoint [row=%s, col=%s, dir=%s]".formatted(row, col,
                dir);
    }

    public String getSummary()
    {
        return "(%s,%s;%s)".formatted(row, col, dir);
    }
}
