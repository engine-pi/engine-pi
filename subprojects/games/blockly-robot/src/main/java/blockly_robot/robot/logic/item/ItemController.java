package blockly_robot.robot.logic.item;

public interface ItemController
{
    public void add(int row, int col);

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3025-L3052">blocklyRobot_lib-1.1.js
     *     L3025-L3052</a>
     */
    public void move(int row, int col);

    public void withdraw();
}
