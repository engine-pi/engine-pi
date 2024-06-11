/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/BallThrowDemo.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi.demos;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.BodyType;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionEvent;
import de.pirckheimer_gymnasium.engine_pi.event.CollisionListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

/**
 * Eine einfache Demonstration der Engine-Physik durch eine
 * Ball-Wurf-Simulation. Es wird ein Ball (durch Wirkung eines Impulses)
 * geworfen.
 *
 * <h3>Nutzung der Simulation</h3>
 *
 * <p>
 * Die Simulation kann gesteuert werden durch:
 * </p>
 * <ul>
 * <li>S-Key: Startet Simulation</li>
 * <li>R-Key: Setzt Simulation zurück</li>
 * <li>Die Keyn Z und U ändern den Zoom auf die Umgebung (rudimentär
 * implementiert)</li>
 * <li>D-Key: Toggelt den Debug-Modus (zeigt das Pixel-Raster)</li>
 * </ul>
 *
 * <h3>Anpassung der Parameter</h3>
 *
 * <p>
 * Die Simulation arbeitet mit einigen physikalischen Parametern, die sich
 * ändern lassen. Folgende Parameter sind als Konstanten im Code definiert und
 * können im angepasst werden:
 * </p>
 * <ul>
 * <li><code>DURCHMESSER</code>: Der Durchmesser des Circlees (hat keinen
 * Einfluss auf die Masse.</li>
 * <li><code>HOEHE_UEBER_BODEN</code>: Abstand zwischen dem untersten Punkt des
 * Balls und dem Boden</li>
 * <li><code>MASSE</code>: Masse des Balls</li>
 * <li><code>IMPULS: Impuls, der auf den Ball angewandt wird.</code></li>
 * <li><code>WINKEL</code>: Winkel, in dem der Impuls auf den Ball angewandt
 * wird. 0° = parallel zum Boden, 90° = gerade nach oben</li>
 * </ul>
 * Created by Michael on 11.04.2017.
 */
public class BallThrowDemo extends ShowcaseDemo
        implements CollisionListener<Actor>, KeyStrokeListener
{
    /**
     * Der Circle. Auf ihn wird ein Impuls gewirkt.
     */
    private Circle ball;

    /**
     * Der Boden.
     */
    private Rectangle ground;

    /**
     * Der Startzeitpunkt der Simulation. Für Zeitmessung
     */
    private long startTime;

    /**
     * Die Konstanten für die Umsetzung der Simulation
     * <p>
     * Einheiten sind:
     * <ul>
     * <li>Distanz: Meter</li>
     * <li>Masse: KG</li>
     * <li>Impuls: Ns</li>
     * <li>Winkel: Grad (nicht Bogenmaß)</li>
     * </ul>
     */
    private static final double DURCHMESSER = 0.2, HOEHE_UEBER_BODEN = 1,
            MASSE = 1, IMPULS = 10, WINKEL = 60;

    /**
     * Die PPM-Berechnungskonstante
     */
    private static final int PIXEL_PER_METER = 100;

    private static final float GROUND_DEPTH = 700;

    private static final float DISTANCE_LEFT = 50;

    public BallThrowDemo(Scene parent)
    {
        super(parent);
        ball = new Circle(DURCHMESSER * PIXEL_PER_METER);
        add(ball);
        ball.setColor(Color.RED);
        ball.setBodyType(BodyType.DYNAMIC);
        ball.setDensity(MASSE);
        ball.setCenter(DISTANCE_LEFT,
                GROUND_DEPTH + (HOEHE_UEBER_BODEN * PIXEL_PER_METER
                        + 0.5 * DURCHMESSER * PIXEL_PER_METER));
        // Den Boden erstellen
        ground = new Rectangle(100, 20);
        ground.setPosition(0, GROUND_DEPTH);
        add(ground);
        ground.setColor(Color.WHITE);
        ground.setBodyType(BodyType.STATIC);
        // Kollision zwischen Ball und Boden beobachten (Code ist uns egal, wir
        // kennen nur einen Kollisionsfall)
        ball.addCollisionListener(ground, this);
        getCamera().setMeter(3);
        getCamera().setFocus(ground);
    }

    /**
     * Wird bei jedem Tastendruck ausgeführt.
     *
     * @param e KeyEvent
     */
    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_S:
            start();
            break;

        case KeyEvent.VK_R:
            reset();
            break;
        }
    }

    /**
     * Startet die Simulation, indem ein Impuls auf den Ball gewirkt wird. Ab
     * diesem Moment beginnt die Zeitmessung
     */
    private void start()
    {
        // Zeitmessung beginnen = Startzeit erheben
        startTime = System.currentTimeMillis();
        // Schwerkraft auf den Ball wirken lassen
        setGravity(new Vector(0, -9.81));
        // Impuls berechnen und auf den Ball wirken lassen
        Vector impuls = new Vector(Math.cos(Math.toRadians(WINKEL)) * IMPULS,
                Math.sin(Math.toRadians(WINKEL)) * IMPULS);
        ball.applyImpulse(impuls);
    }

    /**
     * Setzt die Simulation zurück. Die Schwerkraft auf den Ball wird
     * deaktiviert, die Position des Balls wird zurückgesetzt und der Ball wird
     * in Ruhe versetzt.
     */
    private void reset()
    {
        setGravity(new Vector(0, 0)); // Schwerkraft deaktivieren
        ball.setCenter(DISTANCE_LEFT, // Ballposition zurücksetzen
                GROUND_DEPTH - (HOEHE_UEBER_BODEN * PIXEL_PER_METER
                        + 0.5 * DURCHMESSER * PIXEL_PER_METER));
        ball.resetMovement(); // Ball in Ruhe versetzen
    }

    /**
     * Wird bei jeder Kollision zwischen <b>mit diesem Interface
     * angemeldeten</b> {@link Actor}-Objekten aufgerufen.
     */
    @Override
    public void onCollision(CollisionEvent<Actor> e)
    {
        // Kollision bedeutet, dass der Ball auf den Boden gefallen ist =>
        // Zeitmessung abschließen
        long endzeit = System.currentTimeMillis();
        long zeitdifferenz = endzeit - startTime;
        // Zurückgelegte Distanz seit Simulationsstart ausmessen
        // (Pixel-Differenz ausrechnen und auf Meter umrechnen)
        double distanz = (ball.getCenter().getX() - DISTANCE_LEFT)
                / PIXEL_PER_METER;
        // Messungen angeben
        System.out.println(
                "Der Ball ist auf dem Boden aufgeschlagen. Seit Simulationsstart sind "
                        + +(zeitdifferenz / 1000) + " Sekunden und "
                        + (zeitdifferenz % 1000) + " Millisekunden vergangen.\n"
                        + "Der Ball diese Distanz zurückgelegt: " + distanz
                        + " m");
    }

    public static void main(String[] args)
    {
        Game.start(Showcases.WIDTH, Showcases.HEIGHT, new BallThrowDemo(null));
    }
}
