package de.pirckheimer_gymnasium.blockly_robot.robot.logic.level;

import java.util.HashMap;
import java.util.Map;

/**
 * Der Schwierigkeitsgrad
 */
public enum Difficulty
{
    /**
     * Leichter Schwierigkeitsgrad: Zweistern-Version (<code>Version**</code>,
     * <em>easy</em>).
     */
    EASY(0),
    /**
     * Mittlerer Schwierigkeitsgrad: Dreistern-Version (<code>Version***</code>,
     * <em>medium</em>)
     */
    MEDIUM(1),
    /**
     * Schwerer Schwierigkeitsgrad: Vierstern-Version (<code>Version****</code>,
     * <em>hard</em>).
     */
    HARD(2);

    private final int index;

    private static final Map<Integer, Difficulty> map = new HashMap<>();

    private Difficulty(int index)
    {
        this.index = index;
    }

    static
    {
        for (Difficulty level : Difficulty.values())
        {
            map.put(level.index, level);
        }
    }

    public static Difficulty indexOf(int difficulty)
    {
        return (Difficulty) map.get(difficulty);
    }

    public static Difficulty indexOf(String difficulty)
    {
        difficulty = difficulty.toLowerCase();
        switch (difficulty)
        {
        case "**":
        case "easy":
            return Difficulty.EASY;

        case "***":
        case "medium":
            return Difficulty.MEDIUM;

        case "****":
        case "hard":
            return Difficulty.HARD;

        default:
            throw new IllegalArgumentException(
                    "Unknown difficulty level %s".formatted(difficulty));
        }
    }

    public static Difficulty indexOf(Object difficulty)
    {
        if (difficulty instanceof Integer)
        {
            return indexOf((int) difficulty);
        }
        else if (difficulty instanceof String)
        {
            return indexOf((String) difficulty);
        }
        else
        {
            return EASY;
        }
    }

    /**
     * Returns the index (0: EASY, 1: MEDIUM, 2: HARD) of the difficulty level.
     *
     * @return the index of the difficulty level
     */
    public int getIndex()
    {
        return index;
    }
}
