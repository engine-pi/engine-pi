package de.pirckheimer_gymnasium.engine_pi.event;

public interface PeriodicTask
{
    /**
     * Die Methode wird bei jeder Wiederholung der Aufgabe ausgeführt.
     *
     * @param counter Ein Zähler, der angibt, wieoft die Aufgabe bereits
     *                ausgeführt wurde.
     */
    public void run(int counter);
}
