/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.actor;

import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.debug.ToStringFormatter;

/**
 * Ein ganzzahliger Zähler.
 *
 * @author Josef Friedrich
 *
 * @since 0.35.0
 */
public class Counter extends TextActor
{

    /**
     * Der Zähler, der den aktuellen Wert speichert.
     */
    private int counter;

    /**
     * Initialisiert den Zähler mit dem Wert 0 und setzt die Anzeige auf "0".
     */
    public Counter()
    {
        super("0");
        counter = 0;
    }

    /**
     * Aktualisiert den Inhalt des Counters, indem der aktuelle Zählerwert
     * (counter) gesetzt wird.
     */
    private void updateContent()
    {
        setContent(counter);
    }

    /**
     * <b>Setzt</b> den Zähler auf den angegebenen Wert und aktualisiert den
     * Inhalt.
     *
     * @param counter Der neue Wert für den Zähler.
     */
    public void setCounter(int counter)
    {
        this.counter = counter;
        updateContent();
    }

    /**
     * <b>Gibt</b> den aktuellen <b>Wert des Zählers</b> zurück.
     *
     * @return Der aktuelle Wert des Zählers.
     */
    public int getCounter()
    {
        return counter;
    }

    /**
     * <b>Erhöht</b> den Zähler um eins, aktualisiert den Inhalt und gibt den
     * neuen Zählerwert zurück.
     *
     * @return Der neue Wert des Zählers nach der Erhöhung.
     */
    public int increase()
    {
        counter++;
        updateContent();
        return counter;
    }

    /**
     * <b>Verringert</b> den Zähler um eins, aktualisiert den Inhalt und gibt
     * den neuen Zählerwert zurück.
     *
     * @return Der neue Wert des Zählers nach der Verringerung.
     */
    public int decrease()
    {
        counter--;
        updateContent();
        return counter;
    }

    /**
     * Setzt den Zähler auf <b>0</b> zurück und aktualisiert den Inhalt.
     */
    public void reset()
    {
        counter = 0;
        updateContent();
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Counter");
        formatter.append("counter", Integer.toString(counter));
        return formatter.format();
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Game.start(new Scene()
        {
            {
                Counter c = new Counter();
                c.setColor("white");
                add(c);

                addKeyStrokeListener((event -> {
                    switch (event.getKeyCode())
                    {
                    case KeyEvent.VK_1 -> c.setCounter(1);
                    case KeyEvent.VK_2 -> c.setCounter(2);
                    case KeyEvent.VK_3 -> c.setCounter(3);
                    case KeyEvent.VK_4 -> c.setCounter(4);
                    case KeyEvent.VK_5 -> c.setCounter(5);
                    case KeyEvent.VK_6 -> c.setCounter(6);
                    case KeyEvent.VK_7 -> c.setCounter(7);
                    case KeyEvent.VK_8 -> c.setCounter(8);
                    case KeyEvent.VK_9 -> c.setCounter(9);
                    case KeyEvent.VK_0 -> c.setCounter(0);
                    case KeyEvent.VK_R -> c.reset();
                    case KeyEvent.VK_UP -> c.increase();
                    case KeyEvent.VK_DOWN -> c.decrease();
                    }
                    System.out.println(c);
                }));
            }
        });
    }
}
