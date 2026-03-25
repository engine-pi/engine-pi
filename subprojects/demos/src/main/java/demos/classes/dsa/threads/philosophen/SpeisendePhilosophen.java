package demos.classes.dsa.threads.philosophen;

import java.util.ArrayList;

import pi.actor.Circle;
import pi.actor.Text;

/**
 * Visualisierung des Problems der speisenden Philosophen
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class SpeisendePhilosophen
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
     * instruierender Text oben im Zeichenfenster
     */
    private Text anleitung;

    /**
     * Beteiligte Objekte (Philosophen, Teller, Gabeln, ...) werden passend
     * erstellt und die Philosophenthreads gestartet.
     */
    SpeisendePhilosophen()
    {
        gabeln = new ArrayList<Gabel>();
        teller = new ArrayList<Circle>();
        tellerfarben = new ArrayList<String>();
        tellerfarben.add("rot");
        tellerfarben.add("blau");
        tellerfarben.add("grün");
        tellerfarben.add("magenta");
        tellerfarben.add("grau");
        philosophen = new ArrayList<Philosoph>();
        anleitung = new Text("");
        anleitung.content(
            "Abgelegte Gabeln sind schwarz, aufgenommene Gabeln haben die Farbe ihres aktuellen Besitzers.");
        anleitung.anchor(10, 50);
        anleitung.height(17);

        for (int zähler = 0; zähler < 5; zähler++)
        {
            gabeln.add(new Gabel(zähler));
            gabeln.get(zähler).SymbolGeben().size(100, 10);
            gabeln.get(zähler)
                .SymbolGeben()
                .anchor(
                    350 + (int) (150
                            * Math.cos(Math.toRadians(54 + 72 * zähler))),
                    295 - (int) (150
                            * Math.sin(Math.toRadians(54 + 72 * zähler))));
            gabeln.get(zähler).SymbolGeben().rotation(54 + 72 * zähler);

            teller.add(new Circle());
            teller.get(zähler);
            teller.get(zähler).color(tellerfarben.get(zähler));
            teller.get(zähler)
                .anchor(
                    400 + (int) (175
                            * Math.cos(Math.toRadians(18 + 72 * zähler))),
                    300 - (int) (175
                            * Math.sin(Math.toRadians(18 + 72 * zähler))));
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
        new SpeisendePhilosophen();
    }
}
