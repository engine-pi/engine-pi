package rocks.friedrich.engine_omega.event;

import rocks.friedrich.engine_omega.annotations.API;

/**
 * Beschreibt ein Objekt, dass auf jedes Frame-Update reagieren kann.
 */
@API
public interface FrameUpdateListener
{
    /**
     * Diese Methode wird bei einem (angemeldeten) Objekt jeden Frame erneut
     * aufgerufen.
     *
     * @param deltaSeconds Die Zeit <b>in Sekunden</b>, die seit dem letzten
     *                     Update vergangen ist.
     */
    @API
    void onFrameUpdate(double deltaSeconds);
}
