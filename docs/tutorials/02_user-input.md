# Nutzereingaben[^engine-alpha-wiki:user-input]

[^engine-alpha-wiki:user-input]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input

## Tastatureingaben erstellen

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/KeyStrokeCounter.png)
/// caption
Der Counter im Gange
///

Der [folgende
Code](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyStrokeCounterDemo.java)
implementiert einen einfachen Zähler, der die Anzahl an gedrückten Tasten
(vollkommen egal, welche) festhält.

Quellcode: [demos/input/keyboard/KeyStrokeCounterDemo.java#L21-L60](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyStrokeCounterDemo.java#L21-L60)

```java
public class KeyStrokeCounterDemo extends Scene
{
    public KeyStrokeCounterDemo()
    {
        add(new CounterText());
    }

    private class CounterText extends Text implements KeyStrokeListener
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

**Das Interface `KeyStrokeListener`**

Eine Klasse, die auf Tastatur-Eingaben des Nutzers reagieren soll, implementiert
das Interface
[KeyStrokeListener](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/event/KeyStrokeListener.html).
Die Engine nutzt das
[Observer(Beobachter)-Entwurfsmuster](<https://de.wikipedia.org/wiki/Beobachter_(Entwurfsmuster)>),
um auf alle eingehenden Ereignisse reagieren zu können.

Die korrekte Anweisung, um das Interface einzubinden, lautet:

```java
import pi.event.KeyStrokeListener
```

Die Anmeldung des `KeyStrokeListener`-Interfaces hat automatisch stattgefunden,
als das Objekt der Klasse `CounterText` über `add(...)` angemeldet wurde. Ab
diesem Zeitpunkt wird die [onKeyDown(KeyEvent
e)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/event/KeyStrokeListener.html#onKeyDown(java.awt.event.KeyEvent)>)
-Methode bei jedem Tastendruck aufgerufen.

Soll reagiert werden, wenn eine Taste losgelassen wird, kann die
[onKeyUp(KeyEvent
e)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/event/KeyStrokeListener.html#onKeyDown(java.awt.event.KeyEvent)>)-Methode
implementiert werden.

Alle Informationen über den Tastendruck sind im Objekt `keyEvent` der Klasse
[java.awt.event.KeyEvent](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html)
gespeichert. Die Engine nutzt hier dieselbe Schnittstelle wie Java.

Im [folgendem
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyEventDemo.java)
wird mit Hilfe der vier Cursor-Tasten ein kleines Rechteck bewegt:

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/KeyEventDemo.png)
/// caption
Das rote Rechteck bewegt sich mit WASD
///

Quellcode: [demos/input/keyboard/KeyEventDemo.java#L23-L69](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyEventDemo.java#L23-L68)

```java
import java.awt.event.KeyEvent;

import pi.Game;
import pi.Scene;
import pi.actor.Actor;
import pi.actor.Rectangle;
import pi.event.KeyStrokeListener;

public class KeyEventDemo extends Scene implements KeyStrokeListener
{
    Actor rectangle;

    public KeyEventDemo()
    {
        rectangle = new Rectangle(2, 2).setColor("blue");
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

Java ordnet jeder Taste eine Ganzzahl, einen sogenannten `KeyCode`, zu. Mit der
Methode
[KeyEvent#getKeyCode()](<https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html#getKeyCode()>)
kann dieser Code abgefragt werden. Außerdem stellt die Klasse `KeyEvent` eine
Vielzahl von statischen Attributen bzw. Klassenattributen bereit, dessen Name
`VK_` vorangestellt ist. `VK` steht dabei für [`Virtual
Key`](https://stackoverflow.com/a/70191567). Diese Klassenattribute können in
einer `switch`-Kontrollstruktur zur Fallunterscheidung verwendet werden.

Das [nächste
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyEventDisplayDemo.java)
zeigt den entsprechenden Namen des `VK`-Klassenattributs an, nachdem eine Taste
gedrückt wurde. Wird zum Beispiel die Leertaste gedrückt, erscheint der Text
`VK_SPACE`.

Quellcode: [demos/input/keyboard/KeyEventDisplayDemo.java#L10-L40](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyEventDisplayDemo.java#L10-L40)

```java
public class KeyEventDisplayDemo extends Scene
{
    public KeyEventDisplayDemo()
    {
        add(new KeyText());
    }

    private class KeyText extends Text implements KeyStrokeListener
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

## Mauseingaben erstellen[^engine-alpha-wiki:user-input-mouse-click-listener]

[^engine-alpha-wiki:user-input-mouse-click-listener]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input#MouseClickListener

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/PaintingCirclesDemo.gif)
/// caption
Auf Mausklick reagieren: Kreise malen
///

Das [folgende
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/mouse/PaintingCirclesDemo.java)
malt bei jedem Knopfdruck einen Kreis.[^mausklick-kreise-malen]

[^mausklick-kreise-malen]: https://engine-alpha.org/wiki/v4.x/User_Input#Auf_Mausklick_reagieren:_Kreise_malen

Quellcode: [demos/input/mouse/PaintingCirclesDemo.java#L23-L54](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/mouse/PaintingCirclesDemo.java#L23-L54)

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

## Schnittstelle `MouseClickListener`

Das Interface
[MouseClickListener](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/event/MouseClickListener.html)
ermöglicht das Reagieren auf Mausklicks des Nutzers. Ebenso ermöglicht es das
Reagieren auf Loslassen der Maus.

Bei einem Mausklick (egal ob linke, rechte, oder sonstige Maustaste) wird ein
Kreis an der Position des Klicks erstellt:

```java
@Override
public void onMouseDown(Vector position, MouseButton mouseButton)
{
    paintCircleAt(position.getX(), position.getY(), 1);
}
```

## `Vector`

Statt zwei `double`-Parametern für die X/Y-Koordinaten des Klicks, nutzt die
Engine hier die interne Klasse
[Vector](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/Vector.html).
Die Klasse `Vector` wird in der Engine durchgehend verwendet und ist essentiell
für die Arbeit mit der Engine.[^engine-alpha-wiki:vector]

[^engine-alpha-wiki:vector]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input#Vector

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/PaintingCirclesAdvancedDemo.gif)
/// caption
Ein besseres Kreismalen: Auswählbare Größe und Farbe über ein kleines Menü
///

Quellcode: [demos/input/mouse/PaintingCirclesAdvancedDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/mouse/PaintingCirclesAdvancedDemo.java)
