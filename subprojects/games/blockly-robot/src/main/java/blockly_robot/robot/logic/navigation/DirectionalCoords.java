package blockly_robot.robot.logic.navigation;

import pi.annotations.Getter;
import pi.graphics.geom.Direction;

public class DirectionalCoords extends Coords
{
    private Compass dir;

    public DirectionalCoords(int row, int col, Compass dir)
    {
        super(row, col);
        this.dir = dir;
    }

    @Getter
    public Compass dir()
    {
        return dir;
    }

    @Getter
    public Direction direction()
    {
        return Compass.toDirection(dir);
    }

    @Getter
    public String summary()
    {
        return "(%s,%s;%s)".formatted(row, col, dir);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        return "DirectionalPoint [row=%s, col=%s, dir=%s]"
            .formatted(row, col, dir);
    }
}
