package de.pirckheimer_gymnasium.engine_pi.dsa.turtle.graphics;

import de.pirckheimer_gymnasium.engine_pi.dsa.turtle.TurtleGraphics;

/**
 * <p>
 * Mit rekursiven Algorithmen kannst du wunderbare Grafiken erzeugen. Du gehst
 * von folgender Anleitung aus: Zeichne ausgehend von A ein Quadrat ABCD mit
 * Basisseite AD
 * </p>
 *
 * <p>
 * Füge ein rechtwinkliges, gleichschenkliges BD1C Dreieck an der Seite BC an
 * </p>
 *
 * <p>
 * Zeichne den Baum erneut ausgehend von den Quadraten A1D1 und A2D2 als
 * Basisseiten
 * </p>
 *
 *
 * <p>
 * Es ist bekannt, dass die Umsetzung in ein rekursives Programm ungewohnt ist.
 * Darum erhältst du hier eine ausführliche Anleitung, wie du vorgehen musst.
 * </p>
 *
 * <p>
 * Definiere einen Befehl square(s), mit dem die Turtle ein Quadrat mit der
 * Seitenlänge s zeichnet und wieder in die Anfangsposition mit
 * Anfangsblickrichtung zurückkehrt
 * </p>
 *
 * <p>
 * Definiere den Befehl tree(s), welcher einen Baum ausgehend von einem Quadrat
 * der Seitenlänge s zeichnet. In der Definition darfst du tree() wieder
 * verwenden. Wichtig: Nach dem Zeichnen des Baums ist die Turtle wieder in der
 * Anfangsposition mit Anfangsblickrichtung. Du überlegst schrittweise, als ob
 * du die Turtle wärst (das neu Hinzugefügte ist grau unterlegt).
 * </p>
 *
 * <p>
 * Du zeichnest zuerst vom Punkt A aus ein Quadrat mit der Seitenlänge s:
 * </p>
 *
 * <p>
 * Du fährst zur Ecke B des Quadrats, drehst 45 Grad nach links und betrachtest
 * dies als Startpunkt eines neuen Baums mit verkleinertem Parameter s1. Es gilt
 * nach dem Satz von Pythagoras:
 * </p>
 *
 * <p>
 * Da du ja voraussetzt, dass du nach dem Zeichnen des Baums wieder am
 * Startpunkt mit der Startblickrichtung landest, befindest du dich wieder in B
 * und schaust in Richtung B1. Du drehst dich um 90 Grad nach rechts und fährst
 * die Strecke s1 vorwärts. Jetzt bist du im Punkt D1 und hast die Blickrichtung
 * zu B2. Von hier aus zeichnest du den Baum erneut.
 * </p>
 *
 *
 * <p>
 * Jetzt musst du nur noch an den Anfangsort A mit der Anfangsblickrichtung
 * zurückkehren. Dazu bewegst du dich um s1 rückwärts, drehst dich um 45 Grad
 * nach links und fährst um s rückwärts.
 * </p>
 * <a href=
 * "https://programmierkonzepte.ch/engl/index.php?inhalt_links=&inhalt_mitte=turtle/rekursionen.inc.php">https://programmierkonzepte.ch</a>
 */
public class PythagorasTreeTurtle extends TurtleGraphics
{

    public PythagorasTreeTurtle()
    {
        initalState.speed(1000).position(0, -7).direction(90).warpMode(false);
    }

    public void draw()
    {
        drawTree(3);
    }

    private void drawTree(double sideLength)
    {
        if (sideLength < 0.3)
        {
            return;
        }
        drawSquare(sideLength);
        turtle.forward(sideLength);
        double s1 = sideLength / Math.sqrt(2);
        turtle.left(45);
        drawTree(s1);
        turtle.right(90);
        turtle.forward(s1);
        drawTree(s1);
        turtle.backward(s1);
        turtle.left(45);
        turtle.backward(sideLength);
    }

    private void drawSquare(double sideLength)
    {
        turtle.lowerPen();
        for (int i = 0; i < 4; i++)
        {
            turtle.forward(sideLength);
            turtle.right(90);
        }
        turtle.liftPen();
    }

    public static void main(String[] args)
    {
        new PythagorasTreeTurtle().start();
    }
}
