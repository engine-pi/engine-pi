package blockly_robot.robot.logic.robot;

public enum ErrorMessages
{
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3106">blocklyRobot_lib-1.1.js
     *     L3106</a>
     */
    JUMP_WITHOUT_GRAVITY("Can't jump without gravity", ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L337">blocklyRobot_lib-1.1.js
     *     L337</a>
     */
    JUMP_OUTSIDE_GRID("The robot tries to jump outside of the grid!", ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#338">blocklyRobot_lib-1.1.js
     *     L338</a>
     */
    JUMP_OBSTACLE_BLOCKING("The robot tries to jump but an obstacle blacks it",
            ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L339">blocklyRobot_lib-1.1.js
     *     L339</a>
     */
    JUMP_NO_PLATFORM("The robot tries to jump but there is no platform above!",
            ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L333">blocklyRobot_lib-1.1.js
     *     L333</a>
     */
    WITHDRAWABLES_NOTHING_TO_PICK_UP("There is nothing to puck up!", ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L340">blocklyRobot_lib-1.1.js
     *     L340</a>
     */
    WITHDRAWABLES_TOO_MANY_OBJECTS(
            "The robot tries to transport too many objects at a time!", ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L335">blocklyRobot_lib-1.1.js
     *     L335</a>
     */
    FALL_FALLS("The robot will leap into the void", ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L336">blocklyRobot_lib-1.1.js
     *     L336</a>
     */
    FALL_WILL_FALL_AND_CRASH("The robot will jump from a high point and crash!",
            ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L361">blocklyRobot_lib-1.1.js
     *     L361</a>
     */
    PLATFORMS_FAILURE_NOT_ENOUGH_PLATFORM("Not enough platforms", ""),
    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L358">blocklyRobot_lib-1.1.js
     *     L358</a>
     */
    PLATFORMS_FAILURE_DROP_PLATFORM("You can't drop an object here", "");

    private String en;

    private String de;

    ErrorMessages(String en, String de)
    {
        this.en = en;
        this.de = de;
    }

    public String getEn()
    {
        return en;
    }

    public String getDe()
    {
        return de;
    }
}
