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

import pi.Controller;
import pi.Scene;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;

/**
 * Ein ganzzahliger <b>Zähler</b>.
 *
 * <p>
 * Diese Klasse ist eine spezialisierte {@link Text}-Figur, die einen
 * numerischen Zähler verwaltet und darstellt.
 * </p>
 *
 * <p>
 * Der Zähler kann um einen konfigurierbaren Betrag erhöht oder verringert
 * werden. Die Anzeige des Zählers kann durch ein Präfix, ein Template mit
 * Platzhalter und ein Suffix angepasst werden.
 * </p>
 *
 * <p>
 * <b>Verwendungsbeispiel:</b>
 * </p>
 *
 * <pre>
 * Counter counter = new Counter().prefix("Zähler: ")
 *     .template("{counter}")
 *     .suffix(" Items");
 * counter.increase();
 * counter.decrease();
 * counter.reset();
 * </pre>
 *
 * @see Text
 *
 * @author Josef Friedrich
 *
 * @since 0.35.0
 */
public class Counter extends Text
{
    /**
     * Initialisiert den Zähler mit dem Wert 0 und setzt die Anzeige auf "0".
     */
    public Counter()
    {
        super("");
        update();
    }

    /**
     * Initialisiert den Zähler mit einem Startwert.
     *
     * @param counter Der Startwert des Zählers.
     * @param prefix Eine Zeichenkette, die <b>vor</b> den Zähler angefügt wird.
     * @param template Eine Zeichenkette, in dem die Zeichenkette
     *     {@code {counter}} mit dem aktuellen Zähler <b>ersetzt</b> wird.
     * @param suffix Eine Zeichenkette, die <b>nach</b> dem Zähler angehängt
     *     wird.
     */
    public Counter(int counter, String prefix, String template, String suffix)
    {
        super("");
        this.counter = counter;
        this.prefix = prefix;
        this.template = template;
        this.suffix = suffix;
        update();
    }

    /**
     * Aktualisiert den Inhalt des Zählers, indem der aktuelle Zählerwert
     * (counter) gesetzt wird.
     *
     * @hidden
     */
    @Override
    public void update()
    {
        super.update();
        String content;

        if (template != null)
        {
            if (template.indexOf("{counter}") == -1)
            {
                throw new RuntimeException(
                        "Die Zeichenkette enthält keinen Platzhalter {counter} für den Zähler.");
            }
            content = template.replaceAll("\\{counter\\}",
                String.valueOf(counter));

        }
        else
        {
            content = String.valueOf(counter);
        }

        if (prefix != null)
        {
            content = prefix + content;
        }

        if (suffix != null)
        {
            content = content + suffix;
        }

        content(content);
    }

    /* counter */

    /**
     * Der <b>Zähler</b>, der den aktuellen Wert speichert.
     */
    private int counter;

    /**
     * <b>Gibt</b> den aktuellen <b>Wert des Zählers</b> zurück.
     *
     * @return Der aktuelle Wert des Zählers.
     */
    @API
    @Getter
    public int counter()
    {
        return counter;
    }

    /**
     * <b>Setzt</b> den Zähler auf den angegebenen Wert und aktualisiert den
     * Inhalt.
     *
     * @param counter Der neue Wert für den Zähler.
     */
    @API
    @Setter
    @ChainableMethod
    public Counter counter(int counter)
    {
        this.counter = counter;
        update();
        return this;
    }

    /* amount */

    /**
     * Der <b>Betrag</b>, um den der Zähler erhöht oder erniedrigt wird.
     */
    private int amount = 1;

    /**
     * Gibt den <b>Betrag</b>, um den der Zähler erhöht oder erniedrigt wird.
     *
     * @return Der <b>Betrag</b>, um den der Zähler erhöht oder erniedrigt wird.
     */
    @API
    @Getter
    public int amount()
    {
        return amount;
    }

    /**
     * Setzt den <b>Betrag</b>, um den der Zähler erhöht oder erniedrigt wird.
     *
     * @param amount Der <b>Betrag</b>, um den der Zähler erhöht oder erniedrigt
     *     wird.
     */
    @API
    @Setter
    @ChainableMethod
    public Counter amount(int amount)
    {
        this.amount = amount;
        update();
        return this;
    }

    /* prefix */

    /**
     * Die Zeichenkette, die <b>vor</b> den Zähler angefügt wird.
     *
     * @since 0.43.0
     */
    private String prefix;

    /**
     * <b>Gibt</b> die Zeichenkette, die <b>vor</b> den Zähler angefügt wird,
     * zurück.
     *
     * @return Die Zeichenkette, die <b>vor</b> den Zähler angefügt wird.
     *
     * @since 0.43.0
     */
    @API
    @Getter
    public String prefix()
    {
        return prefix;
    }

    /**
     * <b>Setzt</b> die Zeichenkette, die <b>vor</b> den Zähler angefügt wird.
     *
     * @param prefix Die Zeichenkette, die <b>vor</b> den Zähler angefügt wird.
     *
     * @since 0.43.0
     */
    @API
    @Setter
    @ChainableMethod
    public Counter prefix(String prefix)
    {
        this.prefix = prefix;
        update();
        return this;
    }

    /* template */

    /**
     * Eine Zeichenkette, in dem die Zeichenkette {@code {counter}} mit dem
     * aktuellen Zähler <b>ersetzt</b> wird.
     *
     * @since 0.43.0
     */
    private String template;

    /**
     * <b>Gibt</b> eine Zeichenkette, in dem die Zeichenkette {@code {counter}}
     * mit dem aktuellen Zähler ersetzt wird.
     *
     * @return Eine Zeichenkette, in dem die Zeichenkette {@code {counter}} mit
     *     dem aktuellen Zähler ersetzt wird.
     *
     * @since 0.43.0
     */
    @API
    @Getter
    public String template()
    {
        return template;
    }

    /**
     * <b>Setzt</b> eine Zeichenkette, in dem die Zeichenkette {@code {counter}}
     * mit dem aktuellen Zähler ersetzt wird.
     *
     * @param template Eine Zeichenkette, in dem die Zeichenkette
     *     {@code {counter}} mit dem aktuellen Zähler ersetzt wird.
     *
     * @since 0.43.0
     */
    @API
    @Setter
    @ChainableMethod
    public Counter template(String template)
    {
        this.template = template;
        update();
        return this;
    }

    /* suffix */

    /**
     * Eine Zeichenkette, die <b>nach</b> dem Zähler angehängt wird.
     *
     * @since 0.43.0
     */
    private String suffix;

    /**
     * <b>Gibt</b> eine Zeichenkette, die <b>nach</b> dem Zähler angehängt wird.
     *
     * @return Eine Zeichenkette, die <b>nach</b> dem Zähler angehängt wird.
     *
     * @since 0.43.0
     */
    @API
    @Getter
    public String suffix()
    {
        return suffix;
    }

    /**
     * <b>Setzt</b> eine Zeichenkette, die <b>nach</b> dem Zähler angehängt
     * wird.
     *
     * @param suffix Eine Zeichenkette, die <b>nach</b> dem Zähler angehängt
     *     wird.
     *
     * @since 0.43.0
     */
    @API
    @Setter
    @ChainableMethod
    public Counter suffix(String suffix)
    {
        this.suffix = suffix;
        update();
        return this;
    }

    /* Ende Getter Setter */

    /**
     * <b>Erhöht</b> den Zähler um eins, aktualisiert den Inhalt und gibt den
     * neuen Zählerwert zurück.
     *
     * @return Der neue Wert des Zählers nach der Erhöhung.
     */
    @API
    public int increase()
    {
        counter += amount;
        update();
        return counter;
    }

    /**
     * <b>Verringert</b> den Zähler um eins, aktualisiert den Inhalt und gibt
     * den neuen Zählerwert zurück.
     *
     * @return Der neue Wert des Zählers nach der Verringerung.
     */
    @API
    public int decrease()
    {
        counter -= amount;
        update();
        return counter;
    }

    /**
     * Setzt den Zähler auf <b>0</b> zurück und aktualisiert den Inhalt.
     */
    @API
    @ChainableMethod
    public Counter reset()
    {
        counter = 0;
        update();
        return this;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter("Counter");
        formatter.append("counter", Integer.toString(counter));

        if (amount != 1)
        {
            formatter.append("amount", amount);
        }

        if (prefix != null)
        {
            formatter.append("prefix", prefix);
        }

        if (template != null)
        {
            formatter.append("template", template);
        }

        if (suffix != null)
        {
            formatter.append("suffix", suffix);
        }
        return formatter.format();
    }

    /**
     * @hidden
     */
    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new Scene()
        {
            {
                Counter c = new Counter().prefix("„")
                    .template("{counter}. Zähler")
                    .suffix("“");
                c.color("white");
                add(c);

                addKeyStrokeListener((event -> {
                    switch (event.getKeyCode())
                    {
                    case KeyEvent.VK_1 -> c.counter(1);
                    case KeyEvent.VK_2 -> c.counter(2);
                    case KeyEvent.VK_3 -> c.counter(3);
                    case KeyEvent.VK_4 -> c.counter(4);
                    case KeyEvent.VK_5 -> c.counter(5);
                    case KeyEvent.VK_6 -> c.counter(6);
                    case KeyEvent.VK_7 -> c.counter(7);
                    case KeyEvent.VK_8 -> c.counter(8);
                    case KeyEvent.VK_9 -> c.counter(9);
                    case KeyEvent.VK_0 -> c.counter(0);
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
