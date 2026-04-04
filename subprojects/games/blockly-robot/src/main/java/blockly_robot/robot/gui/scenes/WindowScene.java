package blockly_robot.robot.gui.scenes;

import pi.annotations.Getter;
import pi.graphics.geom.Bounds;

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
    @Getter
    public String title();

    /**
     * @return Die Begrenzungen, die das Fenster umschließen soll.
     */
    @Getter
    public Bounds windowBounds();
}
