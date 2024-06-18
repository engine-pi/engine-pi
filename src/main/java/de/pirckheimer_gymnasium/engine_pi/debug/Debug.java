package de.pirckheimer_gymnasium.engine_pi.debug;

public class Debug
{
    /**
     * Ob, die Koordinaten der Figurpositionen gezeichnet werden sollen.
     */
    public static boolean SHOW_POSITIONS = false;

    public static boolean toogleShowPositions()
    {
        SHOW_POSITIONS = !SHOW_POSITIONS;
        return SHOW_POSITIONS;
    }
}
