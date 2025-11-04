package de.pirckheimer_gymnasium.blockly_robot.robot.gui.scenes;

import de.pirckheimer_gymnasium.engine_pi.Bounds;

/**
 * Szene, die Informationen mitliefert, in welchem Fenster die Szene geöffnet
 * werden soll.
 */
public interface WindowScene
{
    /**
     * @return Eine Zeichenkette, die als Title im Fenster angezeigt werden
     *     soll.
     */
    public String getTitle();

    /**
     * @return Die Begrenzungen, die das Fenster umschließen soll.
     */
    public Bounds getWindowBounds();
}
