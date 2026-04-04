package blockly_robot.robot.data.model;

import blockly_robot.robot.logic.level.Difficulty;
import pi.annotations.Getter;

/**
 * Die Daten einer Version einer Trainingsaufgabe.
 *
 * <p>
 * In der JSON-Datei sind die Daten beispielsweise repräsentiert:
 *
 * <pre>{@code
 *   {
 *     "tiles": [
 *       [2, 2, 2, 2, 2, 2, 2],
 *       [2, 2, 2, 2, 3, 2, 2],
 *       [2, 1, 1, 1, 1, 1, 2],
 *       [2, 2, 2, 2, 2, 2, 2]
 *     ],
 *     "initItems": [
 *       {
 *         "row": 2,
 *         "col": 1,
 *         "dir": 0,
 *         "type": "robot"
 *       }
 *     ]
 *   }
 * }</pre>
 */
public class LevelData
{
    public int[][] tiles;

    public ItemData[] initItems;

    public Difficulty difficulty;

    public int testIndex;

    @Getter
    public int cols()
    {
        return tiles[0].length;
    }

    @Getter
    public int rows()
    {
        return tiles.length;
    }

    @Getter
    public ItemData initItem()
    {
        return initItems[0];
    }
}
