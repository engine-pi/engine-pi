package demos.edu_projects.concurrency.philosophers;

import pi.Scene;
import pi.actor.Circle;
import pi.actor.Image;
import pi.actor.label.TextLabel;
import pi.graphics.boxes.VAlign;
import pi.graphics.geom.Vector;

/**
 * Ein Sitzplatz am Tisch.
 *
 * Der Sitzplatz hat eine Sitzplatznummer und ihm wird ein Philosoph zugeordnet.
 */
public class Seat
{
    Table table;

    /**
     * Die Sitzplatznummer beginnend bei 0.
     */
    int no;

    Philosopher philosopher;

    public Seat(Table table, int no, Philosopher philosopher)
    {
        this.table = table;
        this.no = no;
        this.philosopher = philosopher;
    }

    public void addToScene(Scene scene)
    {
        // Teller
        scene.add(new Circle(1).color(philosopher.color())
            .center(Vector.ofAngle(rotation()).multiply(3)));

        String philospherName = philosopher.name();

        // Bild des Philosophen
        scene.add(new Image("philosophers/" + philospherName + ".png")
            .pixelPerMeter(30)
            .center(Vector.ofAngle(rotation()).multiply(9))
            .label(philospherName)
            .label(new TextLabel(philosopher.lifeTime()).fontSize(8)
                .vAlign(VAlign.TOP)));
    }

    public double rotation()
    {
        return table.rotation() * no;
    }

}
