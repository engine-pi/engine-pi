package de.pirckheimer_gymnasium.blockly_robot.robot.data.model;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "conceptViewer", "languageStrings",
        "limitedUses", "includeBlocks", "blocklyColourTheme",
        "checkEndEveryTurn", "ignoreInvalidMoves", "actionDelay",
        "multiple_marbles", "additionalBlocksByLevel", "cellSide", "newBlocks",
        "checkEndCondition" })
public class GridInfosData
{
    public boolean hasGravity;

    /**
     * <a href=
     * "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L1619-L1620">blocklyRobot_lib-1.1.js
     * L1619-L1620"</a>
     */
    public int maxFallAltitude;

    /**
     * Ist in
     * https://jwinf.de/tasks/jwinf/_common/modules/pemFioi/blocklyRobot_lib-1.1.js
     * definiert
     */
    public String backgroundColor;

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L2580-L2582">blocklyRobot_lib-1.1.js
     *     L2580-L2582</a>
     */
    public String borderColor;

    /**
     * @see <a href=
     *     "https://github.com/France-ioi/bebras-modules/blob/ec1baf055c7f1c383ce8dfa5d27998463ef5be59/pemFioi/blocklyRobot_lib-1.1.js#L3140-L3140">blocklyRobot_lib-1.1.js
     *     L3140</a>
     */
    public int bagSize;

    /**
     * Nicht in der blocky-robot-lib definiert auf GridInfosData Ebene.
     */
    public boolean autoWithdraw;

    /**
     * For example „labyrinth“. Hier wird der Kontext definiert. Mit dem Kontext
     * werden ItemTypes und Aufgabenspezifische Übersetzungen geladen.
     */
    public String contextType;

    public int nbPlatforms;

    /**
     * Gibt an, wie viele Blöcke für welche Versionen maximal zur Verfügung
     * stehen
     */
    public MaxInstructionsData maxInstructions;

    public Map<String, ItemData> itemTypes;

    /**
     * Found in contextParams.json
     */
    public int containerSize;

    /**
     * Found in contextParams.json
     */
    public BagInit bagInit;

    /**
     * Found in contextParams.json
     */
    public boolean noBorders;

    /**
     * Found in contextParams.json
     */
    public boolean ignoreBag;

    /**
     * Found in contextParams.json
     */
    public boolean blockingFilter;

    /**
     * Found in contextParams.json
     */
    public int maxWireLength;

    /**
     * Found in contextParams.json
     */
    public int maxTotalLength;

    /**
     * Gibt an, ob die Programme gespeichert und geladen werden können über das
     * Menu auf der rechten Seite. Für Wettbewerbe immer auf true setzen
     */
    public boolean hideSaveOrLoad;
}
