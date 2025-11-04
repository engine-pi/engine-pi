package de.pirckheimer_gymnasium.blockly_robot.robot.logic.robot;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.log.ItemRelocation;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.log.Movement;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation.Coords;

public interface Robot
{
    /**
     * Returns the row number of the robot's current position. |
     *
     * DE: <i>Die Reihe, in der sich die Figur im Gitter befindet.</i>
     *
     * @return the row number of the robot's current position
     */
    public int getRow();

    /**
     * Returns the column of the robot's current position. |
     *
     * DE: <i>Die Spalte, in der sich die Figur im Gitter befindet.</i>
     *
     * @return the column of the robot's current position
     */
    public int getCol();

    /**
     * Returns the current position of the robot as a Point object. deutsch u
     *
     * @return the current position of the robot
     */
    public Coords getCoords();

    public String[] reportActions();

    public void printActions();

    public boolean onExit();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L4477-L4502">blocklyRobot_lib-1.1.js
     *     L4477-L4502</a>
     */
    public boolean onPaint();
    // public void hasOn();
    // public void setIndexes();
    // public void getItemsOn();
    // public void isOn();
    // public void isInFront();
    // public void isInGrid();
    // public void tryToBeOn();
    // public void coordsInFront();
    // public void isCrossing();
    // public void moveItem();
    // public void moveProjectile();
    // public void destroy();

    /**
     *
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3104-L3123">blocklyRobot_lib-1.1.js
     *     L3104-L3123</a>
     */
    public Movement jump();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3125-L3164">blocklyRobot_lib-1.1.js
     *     L3125-L3164</a>
     */
    public ItemRelocation withdraw();
    // public void checkContainer();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L1927-L1934">blocklyRobot_lib-1.1.js
     *     L1927-L1934</a>
     */
    public boolean onContainer();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L1918-L1925">blocklyRobot_lib-1.1.js
     *     L1918-L1925</a>
     */
    public boolean onWithdrawable();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3197-L3233">blocklyRobot_lib-1.1.js
     *     L3197-L3233</a>
     */
    public ItemRelocation drop();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3235-L3263">blocklyRobot_lib-1.1.js
     *     L3235-L3263</a>
     */
    public ItemRelocation dropWithdrawable(int itemNum);

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2312-L2328">blocklyRobot_lib-1.1.js
     *     L2312-L2328</a>
     */
    public ItemRelocation dropPlatformInFront();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2330-L2346">blocklyRobot_lib-1.1.js
     *     L2330-L2346</a>
     */
    public ItemRelocation dropPlatformAbove();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3265-L3268">blocklyRobot_lib-1.1.js
     *     L3265-L3268</a>
     */
    public Movement turnLeft();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3270-L3273">blocklyRobot_lib-1.1.js
     *     L3270-L3273</a>
     */
    public Movement turnRight();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3275-L3278">blocklyRobot_lib-1.1.js
     *     L3275-L3278</a>
     */
    public Movement turnAround();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3280-L3297">
     *     blocklyRobot_lib-1.1.js L3280-L3297</a>
     *
     */
    public Movement forward();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3299-L3316">
     *     blocklyRobot_lib-1.1.js L3299-L3316</a>
     *
     */
    public Movement backwards();

    /**
     * Gehe nach rechts in Richtung Osten.
     *
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3346-L3358">blocklyRobot_lib-1.1.js
     *     L3346-L3358</a>
     */
    public Movement east();

    /**
     * Gehe nach oben in Richtung Norden.
     *
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3318-L3330">blocklyRobot_lib-1.1.js
     *     L3318-L3330</a>
     */
    public Movement north();

    /**
     * Gehe nach links in Richtung Westen.
     *
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3360-L3372">blocklyRobot_lib-1.1.js
     *     L3360-L3372</a>
     */
    public Movement west();

    /**
     * Gehe nach unten in Richtung Süden.
     *
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3332-L3344">blocklyRobot_lib-1.1.js
     *     L3332-L3344</a>
     */
    public Movement south();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3374-L3376">blocklyRobot_lib-1.1.js
     *     L3374-L3376</a>
     */
    public boolean obstacleInFront();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3378-L3381">blocklyRobot_lib-1.1.js
     *     L3378-L3381</a>
     */
    public boolean platformInFront();

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3383-L3386">blocklyRobot_lib-1.1.js
     *     L3383-L3386</a>
     */
    public boolean platformAbove();
    // public void writeNumber();
    // public void readNumber();
    // public void pushObject();
    // public void shoot();
    // public void connect();
}
