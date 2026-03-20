package demos.classes.dsa.threads.eierautomat;

import pi.Controller;
import pi.Scene;

/**
 * Befüllen und Entleeren eines ländlichen Eierautomaten
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Landleben extends Scene
{
    /**
     * Konstruktor für Objekte der Klasse Landleben
     */
    Landleben()
    {
        Eierautomat automat = new Eierautomat(this);
        // Eierautomat automat = new BessererEierautomat(this);
        Baeuerin baeuerin = new Baeuerin(this, automat);
        Leo leo = new Leo(this, automat);
        baeuerin.start();
        leo.start();
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new Landleben());
    }
}
