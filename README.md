<!-- https://github.com/softwaremill/maven-badges -->

[![Maven Central](https://img.shields.io/maven-central/v/rocks.friedrich.engine_omega/engine-omega.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/rocks.friedrich.engine_omega/engine-omega)
[![javadoc](https://javadoc.io/badge2/rocks.friedrich.engine_omega/engine-omega/javadoc.svg)](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega)

# Engine Omega

![](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/src/main/resources/assets/logo.png)

Mein persönlicher Fork der [Engine Alpha](https://github.com/engine-alpha/engine-alpha)

[PR-Requests](https://github.com/engine-alpha/engine-alpha) bitte gegen das Upstream-Projekt stellen.

## Koordinatensystem

Das Koordinatensystem ist mittig zentriert. Die x-Achse zeigt wie im
Mathematikunterricht nach rechts und die y-Achse nach oben.
1 Längeneinheit entspricht 1 Meter. Die verwendete Physik-Engine rechnet
intern mit Einheiten aus der realen Welt, deshalb bietet sich Meter als Maßheit für das Koordinatensystem an.
[^zeichnen-grafikfenster]

[^zeichnen-grafikfenster]: https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster#Zeichnen_im_Grafikfenster

## Nutzereingaben

https://engine-alpha.org/wiki/v4.x/User_Input


### Tastatureingaben

Der [folgende Code](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/keyboard/KeyStrokeCounterDemo.java) implementiert einen einfachen Zähler, der die Anzahl an
gedrückten Tasten (vollkommen egal, welche) festhält.

```java
public class KeyStrokeCounterDemo extends Scene
{
    public KeyStrokeCounterDemo()
    {
        add(new CounterText());
    }

    private class CounterText extends Text implements KeyListener
    {
        private int counter = 0;

        public CounterText()
        {
            super("You pressed 0 keys.", 2);
            setCenter(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            counter++;
            setContent("You pressed " + counter + " keys.");
            setCenter(0, 0);
        }
    }
}
```

**Das Interface `KeyListener`**

Eine Klasse, die auf Tastatur-Eingaben des Nutzers reagieren soll,
implementiert das Interface
[rocks.friedrich.engine_omega.event.KeyListener](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html).
Die Engine nutzt das
[Observer(Beobachter)-Entwurfsmuster](<https://de.wikipedia.org/wiki/Beobachter_(Entwurfsmuster)>),
um auf alle eingehenden Ereignisse reagieren zu können.

Dieses Interface hat denselben Namen wie das Standard-Java Interface
[java.awt.event.KeyListener](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyListener.html).
Achte darauf, dass du das richtige Interface einbindest.
Die korrekte Anweisung, um das Interface einzubinden, lautet:

```java
import rocks.friedrich.engine_omega.event.KeyListener
```

Die Anmeldung des `KeyListener`-Interfaces hat automatisch stattgefunden, als
das Objekt der Klasse `CounterText` über `add(...)` angemeldet wurde.
Ab diesem Zeitpunkt wird die [onKeyDown(KeyEvent e)](<https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html#onKeyDown(java.awt.event.KeyEvent)>)
-Methode bei jedem Tastendruck
aufgerufen.

Soll reagiert werden, wenn eine Taste losgelassen wird, kann die [onKeyUp(KeyEvent e)](<https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html#onKeyDown(java.awt.event.KeyEvent)>)-Methode
implementiert werden.

Alle Informationen über den Tastendruck sind im Objekt
`keyEvent` der Klasse [java.awt.event.KeyEvent](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html)
gespeichert. Die Engine nutzt hier dieselbe Schnittstelle wie Java.

Im [folgendem Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/keyboard/KeyEventDemo.java) wird mit Hilfe der vier Cursor-Tasten ein kleines Rechteck bewegt:

```java
public class KeyEventDemo extends Scene implements KeyListener
{
    Rectangle rectangle;

    public KeyEventDemo()
    {
        rectangle = new Rectangle(2, 2);
        rectangle.setColor(Color.BLUE);
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_UP:
            rectangle.moveBy(0, 1);
            break;

        case KeyEvent.VK_RIGHT:
            rectangle.moveBy(1, 0);
            break;

        case KeyEvent.VK_DOWN:
            rectangle.moveBy(0, -1);
            break;

        case KeyEvent.VK_LEFT:
            rectangle.moveBy(-1, 0);
            break;
        }
    }
}
```

Java ordnet jeder Taste eine Ganzzahl, einen sogenannten `KeyCode`, zu. Mit der Methode
[KeyEvent#getKeyCode()](<https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html#getKeyCode()>)
kann dieser Code abgefragt werden.
Außerdem stellt die Klasse `KeyEvent` eine Vielzahl von statischen Attributen bzw. Klassenattributen bereit, dessen Name `VK_` vorangestellt ist.
`VK` steht dabei für
[`Virtual Key`](https://stackoverflow.com/a/70191567).
Diese Klassenattribute können in einer `switch`-Kontrollstruktur zur Fallunterscheidung
verwendet werden.

Das [nächste Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/keyboard/KeyEventDisplayDemo.java) zeigt den entsprechenden Namen des `VK`-Klassenattributs an,
nachdem eine Taste gedrückt wurde. Wird zum Beispiel die Leertaste gedrückt,
erscheint der Text `VK_SPACE`.

```java
public class KeyEventDisplayDemo extends Scene
{
    public KeyEventDisplayDemo()
    {
        add(new KeyText());
    }

    private class KeyText extends Text implements KeyListener
    {
        public KeyText()
        {
            super("Press a key", 1);
            setCenter(0, 0);
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            String text = KeyEvent.getKeyText(keyEvent.getKeyCode());
            text = text.replace(" ", "_");
            text = text.toUpperCase();
            setContent("VK_" + text);
            setCenter(0, 0);
        }
    }
}
```

### Mauseeingaben

https://engine-alpha.org/wiki/v4.x/User_Input#Auf_Mausklick_reagieren:_Kreise_malen

Auf Mausklick reagieren: Kreise malen

Das folgende Beispiel malt bei jedem Knopfdruck einen Kreis.

```java
public class PaintingCirclesDemo extends Scene implements MouseClickListener
{
    public PaintingCirclesDemo()
    {
        addMouseClickListener(this);
    }

    private void paintCircleAt(double mX, double mY, double diameter)
    {
        Circle circle = new Circle(diameter);
        circle.setCenter(mX, mY);
        add(circle);
    }

    @Override
    public void onMouseDown(Vector position, MouseButton mouseButton)
    {
        paintCircleAt(position.getX(), position.getY(), 1);
    }
}
```

MouseClickListener

Das Interface ea.event.MouseClickListener ermöglicht das Reagieren auf Mausklicks des Nutzers. Ebenso ermöglicht es das Reagieren auf Loslassen der Maus. Mehr dazu in der Dokumentation.

Bei einem Mausklick (egal ob linke, rechte, oder sonstige Maustaste) wird ein Kreis an der Position des Klicks erstellt:

@Override
public void onMouseDown(Vector position, MouseButton mouseButton) {
    paintCircleAt(position.getX(), position.getY(), 1);
}

Vector

Statt zwei float-Parametern für die X/Y-Koordinaten des Klicks, nutzt die Engine hier die interne Klasse ea.Vector. Diese ist einfach ein 2D-Vektor und macht den Code übersichtlicher. Die Klasse Vector wird in der Engine durchgehend verwendet und ist essentiell für die Arbeit mit der Engine.

### Zeitsteuerung

```java
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.FrameUpdateListener;
import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;

public class RepeatExample extends Scene
{
    public RepeatExample()
    {
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        private int counter = 0;

        FrameUpdateListener listener;

        public CounterText()
        {
            super("0", 2);
            setCenter(0, 0);
            start();
            addKeyListener((e) -> {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if (listener == null)
                    {
                        start();
                    }
                    else
                    {
                        stop();
                    }
                }
            });
        }

        public void start()
        {
            listener = repeat(1, () -> {
                counter++;
                setContent(String.valueOf(counter));
            });
        }

        public void stop()
        {
            removeFrameUpdateListener(listener);
            listener = null;
        }
    }

    public static void main(String[] args)
    {
        Game.start(400, 200, new RepeatExample());
    }
}
```

## Deutsche Übersetzungen von englischen Klassennamen

| englisch       | deutsch          |
| -------------- | ---------------- |
| RobeJoint      | Seilverbindung   |
| DistanceJoint  | Stabverbindung   |
| Joint          | Verbindung       |
| PrismaticJoint | Federverbindung  |
| RevoluteJoint  | Gelenkverbindung |
| WeldJoint      | Schweißnaht      |
| Fixture        | Halterung        |
