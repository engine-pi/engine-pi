package blockly_robot.robot.logic.robot;

import static blockly_robot.robot.logic.navigation.Compass.EAST;
import static blockly_robot.robot.logic.navigation.Compass.NORTH;
import static blockly_robot.robot.logic.navigation.Compass.SOUTH;
import static blockly_robot.robot.logic.navigation.Compass.WEST;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import blockly_robot.robot.data.model.ItemData;
import blockly_robot.robot.logic.Task;
import blockly_robot.robot.logic.context.Context;
import blockly_robot.robot.logic.item.Item;
import blockly_robot.robot.logic.item.StackedItems;
import blockly_robot.robot.logic.level.Level;
import blockly_robot.robot.logic.log.Action;
import blockly_robot.robot.logic.log.ActionLog;
import blockly_robot.robot.logic.log.ItemRelocation;
import blockly_robot.robot.logic.log.Movement;
import blockly_robot.robot.logic.navigation.Compass;
import blockly_robot.robot.logic.navigation.Coords;
import blockly_robot.robot.logic.navigation.DirectionalCoords;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Ein Roboter der nicht grafisch dargestellt ist, sondern der sich nur im
 * Speicher befindet. Er kann durch Unit-Tests getestet werden.
 */
public class VirtualRobot implements Robot
{
    private List<Predicate<Compass>> movementListeners = new ArrayList<>();

    public ActionLog actionLog;

    public Level level;

    public Context context;

    /**
     * Die Zeile, in der sich der Roboter aktuell befindet.
     */
    private int row;

    /**
     * Die Spalte, in der sich der Roboter aktuell befindet.
     */
    private int col;

    public Compass dir;

    public DirectionalCoords initPosition;

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2456">blocklyRobot_lib-1.1.js
     *     L2456</a>
     */
    public int numberOfMovements;

    /**
     * Gibt an, ob die letzte Bewegung erfolgreich war.
     */
    public boolean movementSuccessful;

    public VirtualRobot(Level level)
    {
        this.level = level;
        actionLog = new ActionLog();
    }

    @Setter
    public void context(Context context)
    {
        this.context = context;
    }

    @Getter
    public int row()
    {
        return row;
    }

    @Getter
    public int col()
    {
        return col;
    }

    @Getter
    public Task task()
    {
        return level.task();
    }

    @Getter
    public Coords coords()
    {
        return new Coords(row, col);
    }

    @Setter
    public void initPosition(ItemData init)
    {
        row = init.row;
        col = init.col;
        dir = Compass.fromNumber(init.dir);
        initPosition = new DirectionalCoords(row, col, dir);
    }

    public void resetInitPosition()
    {
        row = initPosition.row();
        col = initPosition.col();
        dir = initPosition.dir();
    }

    public void addMovementListener(Predicate<Compass> listener)
    {
        movementListeners.add(listener);
    }

    public void addGridEdgesMovementListener()
    {
        addMovementListener((Compass direction) -> {
            switch (direction)
            {
            case EAST:
                return col < context.cols() - 1;

            case SOUTH:
                return row < context.rows() - 1;

            case WEST:
                return col > 0;

            case NORTH:
                return row > 0;

            default:
                return true;
            }
        });
    }

    protected boolean isInFrontOfObstacle(Compass direction)
    {
        int rowMovement = 0;
        int colMovement = 0;
        switch (direction)
        {
        case EAST:
            colMovement = 1;
            break;

        case SOUTH:
            rowMovement = 1;
            break;

        case WEST:
            colMovement = -1;
            break;

        case NORTH:
            rowMovement = -1;
            break;

        default:
        }
        return context.isObstacle(row + rowMovement, col + colMovement);
    }

    public boolean isInFrontOfObstacle()
    {
        return isInFrontOfObstacle(dir);
    }

    public void addObstaclesMovementListener()
    {
        addMovementListener(
            (Compass direction) -> !isInFrontOfObstacle(direction));
    }

    public void addDefaultMovementListener()
    {
        addGridEdgesMovementListener();
        addObstaclesMovementListener();
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2923-L2944">blocklyRobot_lib-1.1.js
     *     L2923-L2944</a>
     */
    public boolean tryToBeOn(Compass direction)
    {
        boolean result = true;
        for (Predicate<Compass> listener : this.movementListeners)
        {
            if (!listener.test(direction))
            {
                result = false;
                break;
            }
        }
        movementSuccessful = result;
        return result;
    }

    /**
     * @param dir Die Himmelsrichtung
     * @param mult Multiplikation
     *
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2946-L2958">blocklyRobot_lib-1.1.js
     *     L2946-L2958</a>
     */
    public Coords coordsInFront(Compass dir, int mult)
    {
        int[][] delta = new int[][] { new int[] { 0, 1 }, new int[] { 1, 0 },
                new int[]
                { 0, -1 }, new int[] { -1, 0 } };
        return new Coords(row + delta[dir.number()][0] * mult,
                col + delta[dir.number()][1] * mult);
    }

    public Coords coordsInFront(Compass dir)
    {
        return coordsInFront(dir, 1);
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2872-L2880">blocklyRobot_lib-1.1.js
     *     L2872-L2880</a>
     */
    private boolean hasOn(int row, int col, Predicate<Item> predicate)
    {
        return context.get(row, col).has(predicate);
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2872-L2880">blocklyRobot_lib-1.1.js
     *     L2872-L2880</a>
     */
    private boolean hasOn(Coords point, Predicate<Item> predicate)
    {
        return hasOn(point.row(), point.col(), predicate);
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2908-L2911">blocklyRobot_lib-1.1.js
     *     L2908-L2911</a>
     */
    private boolean isInFront(Predicate<Item> predicate)
    {
        return hasOn(coordsInFront(dir), predicate);
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3374-L3376">blocklyRobot_lib-1.1.js
     *     L3374-L3376</a>
     */
    public boolean obstacleInFront()
    {
        return isInFront(item -> item.isObstacle());
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3378-L3381">blocklyRobot_lib-1.1.js
     *     L3378-L3381</a>
     */
    public boolean platformInFront()
    {
        Coords coords = coordsInFront(dir);
        return hasOn(coords.row() + 1, coords.col(), item -> item.isObstacle());
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3383-L3386">blocklyRobot_lib-1.1.js
     *     L3383-L3386</a>
     */
    public boolean platformAbove()
    {
        return hasOn(row - 1, col, item -> item.isObstacle());
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3084-L3102">blocklyRobot_lib-1.1.js
     *     L3084-L3102</a>
     */
    private Movement fall(Coords from)
    {
        var mov = reportMovement("fall");
        int fallRow = from.row();
        int startRow = from.row();
        boolean canFall = context.canFall(fallRow + 1, from.col());
        while (canFall)
        {
            fallRow++;
            canFall = context.canFall(fallRow + 1, from.col());
        }
        if (!context.isInGrid(fallRow + 1, from.col()))
        {
            return mov.error(ErrorMessages.FALL_FALLS);
        }
        if (fallRow - startRow > task().maxFallAltitude())
        {
            return mov.error(ErrorMessages.FALL_WILL_FALL_AND_CRASH);
        }
        return mov.updateTo(fallRow, from.col(), dir);
    }

    public Movement jump()
    {
        var mov = reportMovement("jump");
        if (!level.task().hasGravity())
        {
            return mov.error(ErrorMessages.JUMP_WITHOUT_GRAVITY);
        }
        if (!context.isInGrid(row - 1, col))
        {
            return mov.error(ErrorMessages.JUMP_OUTSIDE_GRID);
        }
        if (hasOn(row - 2,
            col,
            item -> item.isObstacle() || item.isProjectile()))
        {
            return mov.error(ErrorMessages.JUMP_OBSTACLE_BLOCKING);
        }
        if (!hasOn(row - 1, col, item -> item.isObstacle()))
        {
            return mov.error(ErrorMessages.JUMP_OBSTACLE_BLOCKING);
        }
        move(row - 2, col, dir);
        return mov.updateTo();
    }

    public ItemRelocation withdrawAuto()
    {
        return log(context.bagPacker().withdrawAuto(coords()));
    }

    public ItemRelocation withdraw()
    {
        return log(context.bagPacker().withdraw(coords()));
    }

    public ItemRelocation dropWithdrawable(int itemNum)
    {
        return log(context.bagPacker().dropWithdrawable(coords(), itemNum));
    }

    public ItemRelocation drop()
    {
        return log(context.bagPacker().dropFromBag(coords()));
    }

    private <T> T log(T action)
    {
        actionLog.add((Action) action);
        return action;
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2312-L2328">blocklyRobot_lib-1.1.js
     *     L2312-L2328</a>
     */
    public ItemRelocation dropPlatformInFront()
    {
        return log(context.platformBuilder()
            .dropPlatform(coordsInFront(dir).south(), "dropPlatformInFront"));
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2330-L2346">blocklyRobot_lib-1.1.js
     *     L2330-L2346</a>
     */
    public ItemRelocation dropPlatformAbove()
    {
        return log(context.platformBuilder()
            .dropPlatform(coords().north(), "dropPlatformAbove"));
    }

    public Movement turnLeft()
    {
        var mov = reportMovement("turnLeft");
        dir = dir.rotate(3);
        return mov.updateTo();
    }

    public Movement turnRight()
    {
        var mov = reportMovement("turnRight");
        dir = dir.rotate(1);
        return mov.updateTo();
    }

    public Movement turnAround()
    {
        var mov = reportMovement("turnAround");
        dir = dir.rotate(2);
        return mov.updateTo();
    }

    /**
     * Zeichne die Bewegung auf, die der Roboter macht.
     */
    public Movement reportMovement(String name)
    {
        var mov = new Movement(name, this);
        actionLog.add(mov);
        return mov;
    }

    public String[] reportActions()
    {
        return actionLog.toArray();
    }

    public void printActions()
    {
        actionLog.printActions();
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2979-L3023">blocklyRobot_lib-1.1.js
     *     L2979-L3023</a>
     */
    private void move(int newRow, int newCol, Compass newDir)
    {
        row = newRow;
        col = newCol;
        dir = newDir;
        if (task().autoWithdraw())
        {
            withdrawAuto();
        }
    }

    private Movement forOrBackwards(String name, Compass direction)
    {
        var mov = reportMovement(name);
        if (tryToBeOn(dir))
        {
            Coords inFront = coordsInFront(direction);
            if (task().hasGravity())
            {
                Movement fallMov = fall(inFront);
                if (fallMov.to() != null && fallMov.to().row() != inFront.row())
                {
                    mov.next(fallMov);
                    move(fallMov.to().row(), fallMov.to().col(), dir);
                    return mov.to(inFront);
                }
            }
            move(inFront.row(), inFront.col(), dir);
            numberOfMovements++;
        }
        return mov.updateTo();
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3280-L3297">
     *     blocklyRobot_lib-1.1.js L3280-L3297</a>
     */
    public Movement forward()
    {
        return forOrBackwards("forward", dir);
    }

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3299-L3316">
     *     blocklyRobot_lib-1.1.js L3299-L3316</a>
     *
     */
    public Movement backwards()
    {
        return forOrBackwards("backwards", WEST);
    }

    private Movement moveInDir(String name, Compass dir, int newRow, int newCol)
    {
        var mov = reportMovement(name);
        if (tryToBeOn(dir))
        {
            move(newRow, newCol, dir);
            numberOfMovements++;
        }
        return mov.updateTo();
    }

    public Movement east()
    {
        return moveInDir("east", EAST, row, col + 1);
    }

    public Movement north()
    {
        return moveInDir("north", NORTH, row - 1, col);
    }

    public Movement west()
    {
        return moveInDir("west", WEST, row, col - 1);
    }

    public Movement south()
    {
        return moveInDir("south", SOUTH, row + 1, col);
    }

    /**
     * Gib das Ding zurück, auf dem sich der Roboter gerade befindet.
     */
    private StackedItems getOnItems()
    {
        return context.get(row, col);
    }

    public boolean onContainer()
    {
        return getOnItems().isContainer();
    }

    public boolean onWithdrawable()
    {
        return getOnItems().isWithdrawable();
    }

    public boolean onExit()
    {
        return getOnItems().isExit();
    }

    public boolean onPaint()
    {
        return getOnItems().isPaint();
    }

    /**
     * @hidden
     */
    public String toString()
    {
        return "VirtualRobot [row=%s, col=%s, dir=%s]".formatted(row, col, dir);
    }
}
