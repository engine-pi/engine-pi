package rocks.friedrich.engine_omega.actor;

import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.annotations.API;

public interface Actable
{
    /**
     * Verschiebt die Gruppe ohne Bedingungen auf der Zeichenebene.
     *
     * @param vector Der Vektor, der die Verschiebung der Gruppe angibt.
     * @see Vector
     * @see #moveBy(double, double)
     */
    @API
    public void moveBy(Vector vector);

    /**
     * Verschiebt die Gruppe.
     *
     * <p>
     * Hierbei wird nichts anderes gemacht, als <code>moveBy(new
     * Vector(dx, dy))</code> auszuführen. Insofern ist diese Methode dafür gut,
     * sich nicht mit der Klasse {@link Vector} auseinandersetzen zu müssen.
     *
     * @param dX Die Verschiebung in Richtung X.
     * @param dY Die Verschiebung in Richtung Y.
     * @see #moveBy(Vector)
     */
    @API
    public void moveBy(double dX, double dY);
}
