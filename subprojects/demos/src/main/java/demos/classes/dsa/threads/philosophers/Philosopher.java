/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.classes.dsa.threads.philosophers;

import java.util.Random;

import pi.Controller;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Image;
import pi.annotations.Getter;
import pi.graphics.geom.Vector;

/**
 * Ein speisender Philosoph.
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Philosopher extends Thread
{
    /**
     * Die ID des Philosophen.
     */
    private int id;

    /**
     * Die Zeitangabe in ms als Grundlage für die Bestimmung zufälliger Ess- und
     * Wartezeiten.
     */
    private int waitingTime;

    /**
     * Die Farbe des Tellers.
     */
    private String color;

    /**
     * Die linke Gabel.
     */
    private Fork leftFork;

    /**
     * Die rechte Gabel.
     */
    private Fork rightFork;

    /**
     * Der Zufallsgenerator.
     */
    private Random random;

    /**
     * Der Zeitpunkt, an dem der Philosoph zuletzt gegessen hat.
     */
    private long lastMeal;

    /**
     * Ein Bild, das den Philosophen darstellt.
     */
    private Image image;

    /**
     * Konstruktor für Objekte der Klasse Philosoph.
     *
     * @param id Die ID des Philosophen.
     * @param color Die Farbe des Tellers.
     * @param left Die linke Gabel, die der Philosoph nutzt.
     * @param right Die rechte Gabel, die der Philosoph nutzt.
     */
    Philosopher(Scene scene, int id, String name, String color, Fork left,
            Fork right)
    {
        waitingTime = 50;
        this.id = id;
        this.color = color;
        leftFork = left;
        rightFork = right;
        random = new Random();

        // Teller
        scene.add(new Circle(1).color(color)
            .center(Vector.ofAngle((double) 72 * id).multiply(3)));
        // Bild des Philosophen

        image = (Image) new Image("philosophers/" + name + ".png")
            .pixelPerMeter(30)
            .center(Vector.ofAngle((double) 72 * id).multiply(8))
            .label(name);

        scene.addFrameUpdateListener(
            deltaTime -> image.opacity(isStarving() ? 0.5 : 1));

        scene.add(image);

    }

    /**
     * Gibt die ID des Philosophen aus.
     *
     * @return Die ID des Philosophen.
     */
    @Getter
    public int id()
    {
        return id;
    }

    /**
     * Gibt den Zeitpunkt aus, an dem der Philosoph zuletzt gegessen hat.
     *
     * @return Der Zeitpunkt, an dem der Philosoph zuletzt gegessen hat.
     */
    @Getter
    public long lastMeal()
    {
        return lastMeal;
    }

    /**
     * Gibt den Zeitpunkt aus, an dem der Philosoph zuletzt gegessen hat.
     *
     * @return Der Zeitpunkt, an dem der Philosoph zuletzt gegessen hat.
     */
    public boolean isStarving()
    {
        return System.currentTimeMillis() - lastMeal > 1000;
    }

    /**
     * Der Philosoph wartet eine zufällig Zeit lang.
     */
    private void waitRandomly()
    {
        try
        {
            sleep(random.nextInt(waitingTime));
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Der Philosoph denkt.
     */
    private void think()
    {
        waitRandomly();
    }

    /**
     * Der Philosoph isst.
     */
    private void eat()
    {
        waitRandomly();
        lastMeal = System.currentTimeMillis();
    }

    /**
     * Erfülle alle Coffman-Bedingungen.
     */
    public void fullfilCoffman()
    {
        // denken
        think();

        // essen
        leftFork.pickUp(color);
        rightFork.pickUp(color);
        eat();
        leftFork.putDown();
        rightFork.putDown();
    }

    /**
     * Verletzte die Coffman-Bedingung 4 („Zyklisches Warten”)
     */
    public void violateCoffman4()
    {
        // denken
        think();

        // essen
        leftFork.pickUp(color);
        if (rightFork.id() > leftFork.id())
        {
            rightFork.pickUp(color);
        }
        else
        {
            leftFork.putDown();
            rightFork.putDown();
            leftFork.putDown();
        }
        eat();
        leftFork.putDown();
        rightFork.putDown();
    }

    /**
     * Verletze die Coffman-Bedingung 2 („Halten und Warten“)
     */
    public void violateCoffman2()
    {
        // denken
        think();

        // essen
        leftFork.pickUp(color);
        if (rightFork.tryPickUp(color))
        {
            eat();
            rightFork.putDown();
        }
        leftFork.putDown();
    }

    /**
     * Die Arbeitsmethode des Threads mit einer Endlosschleife: Der Philosoph
     * nimmt nach einer Zeit des Denkens die Gabeln auf, isst und legt sie dann
     * wieder ab.
     */
    @Override
    public void run()
    {
        while (true)
        {
            // fullfilCoffman();
            // violateCoffman4();
            violateCoffman2();
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new DiningPhilosophers(), 800, 800);
    }

}
