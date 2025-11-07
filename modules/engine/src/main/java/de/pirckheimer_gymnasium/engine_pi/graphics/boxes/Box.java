package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

import java.awt.Graphics2D;

public abstract class Box
{

    /**
     * Die x-Koordinate der linken oberen Ecke.
     */
    protected int x = Integer.MIN_VALUE;

    /**
     * Die y-Koordinate der linken oberen Ecke.
     */
    protected int y = Integer.MIN_VALUE;

    /**
     * Die übergeordnet Box, in der diese Box enthalten ist. Ist null, wenn
     * keine Elternbox vorhanden ist.
     */
    protected Box parent;

    public int x()
    {
        if (x == Integer.MIN_VALUE)
        {
            throw new RuntimeException(
                    "Die x-Koordinate wurde noch nicht gesetzt.");
        }
        return x;
    }

    public Box x(int x)
    {
        this.x = x;
        return this;
    }

    public Box y(int y)
    {
        this.y = y;
        return this;
    }

    public Box anchor(int x, int y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public int y()
    {
        if (y == Integer.MIN_VALUE)
        {
            throw new RuntimeException(
                    "Die y-Koordinate wurde noch nicht gesetzt.");
        }
        return y;
    }

    /**
     * @return Die Breite in Pixel.
     */
    public abstract int width();

    /**
     * @return Die Höhe in Pixel.
     */
    public abstract int height();

    /**
     * Berechnet rekursiv alle Ankerpunkte (linkes oberes Eck) der
     * untergeordneten Kindboxen. Die inneren Blattboxen brauchen diese Methode
     * nicht zu implementieren.
     */
    public void calculateAnchors()
    {

    }

    public abstract void render(Graphics2D g);

}
