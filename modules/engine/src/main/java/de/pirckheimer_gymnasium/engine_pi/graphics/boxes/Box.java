package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

public abstract class Box
{
    /**
     * Die übergeordnet Box, in der diese Box enthalten ist. Ist null, wenn
     * keine Elternbox vorhanden ist.
     */
    protected Box parent;

    /**
     * @return Die Breite in Pixel.
     */
    public abstract int getWidth();

    /**
     * @return Die Höhe in Pixel.
     */
    public abstract int getHeight();

}
