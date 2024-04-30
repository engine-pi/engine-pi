<!-- https://github.com/softwaremill/maven-badges -->
[![Maven Central](https://img.shields.io/maven-central/v/rocks.friedrich.engine_omega/engine-omega.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/rocks.friedrich.engine_omega/engine-omega)
[![javadoc](https://javadoc.io/badge2/rocks.friedrich.engine_omega/engine-omega/javadoc.svg)](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega)

# Engine Omega

![](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/src/main/resources/assets/logo.png)

Mein persönlicher Fork der [Engine Alpha](https://github.com/engine-alpha/engine-alpha)

[PR-Requests](https://github.com/engine-alpha/engine-alpha) bitte gegen das Upstream-Projekt stellen.

## Nutzereingaben

https://engine-alpha.org/wiki/v4.x/User_Input

### Tastatureingaben

Der folgende Code implementiert einen einfachen Zähler, der die Anzahl an
gedrückten Tasten (vollkommen egal, welche) festhält.

```java
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyStrokeCounterExample extends Scene
{
    public KeyStrokeCounterExample()
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

    public static void main(String[] args)
    {
        Game.start(700, 200, new KeyStrokeCounterExample());
    }
}
```

__Das Interface `KeyListener`__

Eine Klasse, die auf Tastatur-Eingaben des Nutzers reagieren soll,
implementiert das Interface
[rocks.friedrich.engine_omega.event.KeyListener](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html).
Die Engine nutzt das
[Observer(Beobachter)-Entwurfsmuster](https://de.wikipedia.org/wiki/Beobachter_(Entwurfsmuster)),
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
Ab diesem Zeitpunkt wird die [onKeyDown(KeyEvent e)](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html#onKeyDown(java.awt.event.KeyEvent))
-Methode bei jedem Tastendruck
aufgerufen.

Soll reagiert werden, wenn eine Taste losgelassen wird, kann die [onKeyUp(KeyEvent e)](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html#onKeyDown(java.awt.event.KeyEvent))-Methode
implementiert werden.

Alle Informationen über den Tastendruck sind im Objekt
`keyEvent` der Klasse [java.awt.event.KeyEvent](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html)
gespeichert. Die Engine nutzt hier dieselbe Schnittstelle wie Java.

Im folgendem Beispiel wird mit Hilfe der vier Cursor-Tasten ein kleines Rechteck bewegt:

```java
import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyEventExample extends Scene implements KeyListener
{
    Rectangle rectangle;

    public KeyEventExample()
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

    public static void main(String[] args)
    {
        Game.start(600, 400, new KeyEventExample());
    }
}
```

Java ordnet jeder Taste eine Ganzzahl, einen sogenannten `KeyCode`, zu. Mit der Methode
[KeyEvent#getKeyCode()](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html#getKeyCode())
kann dieser Code abgefragt werden.
Außerdem stellt die Klasse `KeyEvent` eine Vielzahl von statischen Attributen bzw. Klassenattributen bereit, dessen Name `VK_` vorangestellt ist.
`VK` steht dabei für
[`Virtual Key`](https://stackoverflow.com/a/70191567).
Diese Klassenattribute können in einer `switch`-Kontrollstruktur zur Fallunterscheidung
verwendet werden.

Das nächste Beispiel zeigt den entsprechenden Namen des `VK`-Klassenattributs an,
nachdem eine Taste gedrückt wurde. Wird zum Beispiel die Leertaste gedrückt,
erscheint der Text `VK_SPACE`.

```java
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyEventDisplayExample extends Scene
{
    public KeyEventDisplayExample()
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

    public static void main(String[] args)
    {
        Game.start(600, 400, new KeyEventDisplayExample());
    }
}
```

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
