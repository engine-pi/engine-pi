package demos.classes.dsa.threads.philosophen;

import java.util.ArrayList;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;

/**
 * Visualisierung des Problems der speisenden Philosophen
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class SpeisendePhilosophen extends Scene
{
    /**
     * verwaltet alle Gabeln
     */
    private ArrayList<Gabel> gabeln;

    /**
     * verwaltet alle Teller
     */
    private ArrayList<Circle> teller;

    /**
     * verwaltet alle Tellerfarben
     */
    private ArrayList<String> tellerfarben;

    /**
     * verwaltet alle Philosophen
     */
    private ArrayList<Philosoph> philosophen;

    /**
     * Beteiligte Objekte (Philosophen, Teller, Gabeln, ...) werden passend
     * erstellt und die Philosophenthreads gestartet.
     */
    SpeisendePhilosophen()
    {
        info().description(
            "Abgelegte Gabeln sind schwarz, aufgenommene Gabeln haben die Farbe ihres aktuellen Besitzers.");
        gabeln = new ArrayList<Gabel>();
        teller = new ArrayList<Circle>();
        tellerfarben = new ArrayList<String>();
        tellerfarben.add("rot");
        tellerfarben.add("blau");
        tellerfarben.add("grün");
        tellerfarben.add("magenta");
        tellerfarben.add("grau");
        philosophen = new ArrayList<Philosoph>();

        for (int zähler = 0; zähler < 5; zähler++)
        {
            gabeln.add(new Gabel(this, zähler));
            gabeln.get(zähler).SymbolGeben().size(100, 10);
            gabeln.get(zähler)
                .SymbolGeben()
                .anchor(
                    350 + (int) (150
                            * Math.cos(Math.toRadians(54 + 72 * zähler))),
                    295 - (int) (150
                            * Math.sin(Math.toRadians(54 + 72 * zähler))));
            gabeln.get(zähler).SymbolGeben().rotation(54 + 72 * zähler);

            Circle circle = new Circle();
            add(circle);
            circle.color(tellerfarben.get(zähler));
            teller.add(new Circle());

            circle.anchor(
                400 + (int) (175 * Math.cos(Math.toRadians(18 + 72 * zähler))),
                300 - (int) (175 * Math.sin(Math.toRadians(18 + 72 * zähler))));

            teller.add(circle);
        }

        for (int zähler = 0; zähler < 5; zähler++)
        {
            philosophen.add(new Philosoph(zähler, teller.get(zähler),
                    tellerfarben.get(zähler), gabeln.get((zähler - 1 + 5) % 5),
                    gabeln.get(zähler)));
            philosophen.get(zähler).start();
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new SpeisendePhilosophen());
    }
}
