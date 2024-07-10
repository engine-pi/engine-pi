[![Maven Central](https://img.shields.io/maven-central/v/de.pirckheimer-gymnasium/engine-pi.svg?style=flat)](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)
[![javadoc](https://javadoc.io/badge2/de.pirckheimer-gymnasium/engine-pi/javadoc.svg)](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi)
demos:
[![Maven Central](https://img.shields.io/maven-central/v/de.pirckheimer-gymnasium/engine-pi-demos.svg?style=flat)](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi-demos)
[![javadoc](https://javadoc.io/badge2/de.pirckheimer-gymnasium/engine-pi-demos/javadoc.svg)](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi-demos)

# Über diese Game Engine

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/engine-pi/src/main/resources/logo/logo.png)

Diese Game Engine ist ein Fork der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) von
[Michael Andonie](https://github.com/andonie)
und [Niklas Keller](https://github.com/kelunik) und zwar ein Fork der Core Engine v4.x.
Die Engine-Alpha-Edu Version mit deutschen Java Bezeichnern wurde nicht geforkt.

Da die [Engine-Alpha](https://github.com/engine-alpha/engine-alpha) momentan keine
Audio-Wiedergabe unterstützt, wurde Code der Sound-Engine der
[LITIENGINE](https://github.com/gurkenlabs/litiengine) in die Engine Pi übernommen.
Die LITIENGINE ist eine Java-2D-Game-Engine der bayerischen Entwickler
[Steffen Wilke](https://github.com/steffen-wilke)
[Matthias Wilke](https://github.com/nightm4re94). Neben der Sound-Engine kommen
viele Klassen zur Resourcen-Verwaltung, einige Hilfsklassen sowie das
Tweening-Paket aus der LITIENGINE in der Engine Pi zum Einsatz.

Diese README-Datei verwendet Dokumentationen, Tutorials und Bilder aus dem
[Engine Alpha Wiki](https://engine-alpha.org), die unter der
[Creative Commons „Namensnennung, Weitergabe unter gleichen Bedingungen“](https://creativecommons.org/licenses/by-sa/3.0/)
Lizenz stehen.

## Koordinatensystem

Das Koordinatensystem ist mittig zentriert. Die x-Achse zeigt wie im
Mathematikunterricht nach rechts und die y-Achse nach oben. 1 Längeneinheit
entspricht 1 Meter. Die verwendete Physik-Engine rechnet intern mit Einheiten
aus der realen Welt, deshalb bietet sich Meter als Maßheit für das
Koordinatensystem an.[^zeichnen-grafikfenster]

[^zeichnen-grafikfenster]: https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster#Zeichnen_im_Grafikfenster

## Figur (Actor)

Eine Figur (engl. Actor) ist ein grafisches Objekt, das sich bewegt bzw. das
bewegt werden kann. In der Engine Pi gibt es eine Vielzahl verschiedener
Figurenarten (z. B. Image, Text, Rectangle, Circle). Alle diese
Spezialisierungen sind abgeleitet von der Oberklasse
[Actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/Actor.html)
Die API-Dokumentation des Pakets [de.pirckheimer_gymnasium.engine_pi.actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/package-summary.html)
listet alle verfügbaren Actor-Klassen auf.

Nachdem eine Figur erzeugt und zur Szene hinzugefügt wurde, befindet sie sich an
der Koordinate (0|0), d. h. die linke untere Ecke der Figur - ihr Ankerpunkt -
liegt an dem Punkt im Koordinatensystem, das 0 sowohl für den x- als auch den
y-Wert der Koordinate hat.

## Tutorial: Hello World

https://engine-alpha.org/wiki/v4.x/Hello_World

<!-- ### Schritt 1: Grundlegender Aufbau -->

Das grundlegendste Hello World sieht so aus:
Das (noch wenig spannende) Ergebnis des Codes

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion1.java#L23-L41](https://github.com/engine-pi/engine-pi/blob/123719a158c4d268875630251b67fefe448a5b66/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion1.java#L23-L41)

```java
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

public class HelloWorldVersion1 extends Scene
{
    public HelloWorldVersion1()
    {
        Text helloWorld = new Text("Hello, World!", 2);
        helloWorld.setCenter(0, 1);
        add(helloWorld);
        Game.setDebug(true);
    }

    public static void main(String[] args)
    {
        Game.start(400, 300, new HelloWorldVersion1());
    }
}
```

<!-- #### Scene -->

Die `HelloWorldVersion1`-Klasse leitet sich aus der Klasse `Scene` der Engine
ab. Szenen in der Engine sind eigenständige Spielbereiche. Jede Szene hat ihre
eigenen grafischen (und sonstige) Objekte; Szenes werden unabhängig voneinander
berechnet. Ein Spiel besteht aus einer oder mehreren Szenen und wir erstellen
eine Szene, in der „Hello World“ dargestellt werden soll:

```java
public class HelloWorldVersion1 extends Scene
```

<!-- #### Text -->

Wir wollen den Text „Hello, World!“ darstellen. Die Klasse `Text` ist dafür
zuständig. Ein Text mit Inhalt „Hello, World!“ und Höhe 2 wird erstellt:

```java
Text helloWorld = new Text("Hello, World!", 2);
```

Der Text wird an Position (0|1) zentriert:

```java
helloWorld.setCenter(0, 1);
```

Der Text wird an der Szene angemeldet:

```java
add(helloWorld);
```

Der letzte Schritt ist nötig, damit das Objekt auch sichtbar wird. In jeder
Szene werden nur die Objekte gerendert, die auch an der Szene angemeldet
sind.

<!-- #### Debug Mode -->

Der Debug-Modus zeigt das Koordinatensystem und weitere hilfreiche Informationen.

Um Überblick zu behalten und die Grafikebene zu verstehen, ist der Debug-Modus
der Engine hilfreich. Diese Zeile aktiviert den Debug Modus:

```java
Game.setDebug(true);
```

Die Klasse `Game` enthält neben Debug-Modus weitere Features, die die
Spielumgebung global betreffen.

Die Klasse `Game` kontrolliert auch den Spielstart. Dazu muss lediglich die
(zuerst) darzustellende Szene angegeben werden, sowie die Fenstermaße (in diesem
Fall 400 px Breite und 300 px Höhe):

```java
Game.start(400, 300, new HelloWorldVersion1());
```

<!-- ### Schritt 2: Geometrie und Farbe -->

Beim nächste Codebeispiel handelt es sich um eine Erweiterung der Version 1 um
geometrischen Figuren und Farbe.

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion2.java#L23-L55](https://github.com/engine-pi/engine-pi/blob/d46b39b8f2ea0cc1bcdaa63cbeefec6fe42d6de9/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion2.java#L23-L55)

```java
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

public class HelloWorldVersion2 extends Scene
{
    public HelloWorldVersion2()
    {
        Text helloworld = new Text("Hello, World!", 2);
        helloworld.setCenter(0, 1);
        add(helloworld);
        helloworld.setColor("black");
        // Ein grünes Rechteck als Hintergrund
        Rectangle background = new Rectangle(12, 3);
        background.setColor("green");
        background.setCenter(0, 1);
        background.setLayerPosition(-1);
        // Ein blauer Kreis
        Circle circle = new Circle(8);
        circle.setColor("blue");
        circle.setCenter(0, 1);
        circle.setLayerPosition(-2);
        add(background, circle);
        getCamera().setMeter(20);
    }

    public static void main(String[] args)
    {
        Game.start(400, 300, new HelloWorldVersion2());
    }
}
```

<!-- #### Geometrische Figuren -->

Die Engine unterstützt diverse geometrische Figuren. Dazu gehören Rechtecke und
Kreise. Der Code erstellt ein Rechteck mit Breite 12 und Höhe 3 sowie einen
Kreis mit Durchmesser 8.

```java
Rectangle background = new Rectangle(12, 3);
Circle circle = new Circle(8);
```

<!-- #### Farbe -->

Einige Objekte in der Engine können beliebig gefärbt werden. Text und
geometrische Figuren gehören dazu. Mit `setColor(Color)` kann die Farbe als
AWT-Color-Objekt übergeben werden oder einfacher als
[Zeichenkette](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/resources/ColorContainer.html):

```java
background.setColor("green");
circle.setColor("blue");
```

<!-- #### Layer Position -->

So würde das Bild aussehen, wenn die Ebenen-Position nicht explizit gesetzt
werden würde.

Wir wollen explizit, dass der Text vor allen anderen Objekten dargestellt wird.
Außerdem soll der Kreis noch hinter dem Rechteck sein. Um das sicherzustellen,
kann die Ebenen-Position explizit angegeben werden: Je höher die
Ebenen-Position, desto weiter im Vordergrund ist das Objekt.

```java
background.setLayerPosition(-1);
circle.setLayerPosition(-2);
```

## Nutzereingaben

https://engine-alpha.org/wiki/v4.x/User_Input

### Tastatureingaben erstellen

![Der Counter im Gange](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/input/KeyStrokeCounter.png)

Der [folgende Code](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyStrokeCounterDemo.java) implementiert einen einfachen Zähler, der die Anzahl an
gedrückten Tasten (vollkommen egal, welche) festhält.

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyStrokeCounterDemo.java#L21-L60](https://github.com/engine-pi/engine-pi/blob/ddae75531cadc170a95cc6e9b4dca0ad18a34327/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyStrokeCounterDemo.java#L21-L60)

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

Eine Klasse, die auf Tastatur-Eingaben des Nutzers reagieren soll,
implementiert das Interface
[KeyStrokeListener](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/KeyStrokeListener.html).
Die Engine nutzt das
[Observer(Beobachter)-Entwurfsmuster](<https://de.wikipedia.org/wiki/Beobachter_(Entwurfsmuster)>),
um auf alle eingehenden Ereignisse reagieren zu können.

Die korrekte Anweisung, um das Interface einzubinden, lautet:

```java
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener
```

Die Anmeldung des `KeyStrokeListener`-Interfaces hat automatisch stattgefunden, als
das Objekt der Klasse `CounterText` über `add(...)` angemeldet wurde.
Ab diesem Zeitpunkt wird die [onKeyDown(KeyEvent e)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/KeyStrokeListener.html#onKeyDown(java.awt.event.KeyEvent)>)
-Methode bei jedem Tastendruck
aufgerufen.

Soll reagiert werden, wenn eine Taste losgelassen wird, kann die
[onKeyUp(KeyEvent
e)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/KeyStrokeListener.html#onKeyDown(java.awt.event.KeyEvent)>)-Methode
implementiert werden.

Alle Informationen über den Tastendruck sind im Objekt `keyEvent` der Klasse
[java.awt.event.KeyEvent](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html)
gespeichert. Die Engine nutzt hier dieselbe Schnittstelle wie Java.

Im [folgendem
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyEventDemo.java)
wird mit Hilfe der vier Cursor-Tasten ein kleines Rechteck bewegt:

![Das rote Rechteck bewegt sich mit WASD](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/input/KeyEventDemo.png)

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyEventDemo.java#L23-L69](https://github.com/engine-pi/engine-pi/blob/ddae75531cadc170a95cc6e9b4dca0ad18a34327/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyEventDemo.java#L23-L69)

```java
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

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
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyEventDisplayDemo.java)
zeigt den entsprechenden Namen des `VK`-Klassenattributs an, nachdem eine Taste
gedrückt wurde. Wird zum Beispiel die Leertaste gedrückt, erscheint der Text
`VK_SPACE`.

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyEventDisplayDemo.java#L10-L40](https://github.com/engine-pi/engine-pi/blob/ddae75531cadc170a95cc6e9b4dca0ad18a34327/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/keyboard/KeyEventDisplayDemo.java#L10-L40)

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

### Mauseingaben erstellen

https://engine-alpha.org/wiki/v4.x/User_Input#MouseClickListener

![Auf Mausklick reagieren: Kreise malen](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/input/PaintingCirclesDemo.gif)

Das [folgende
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/mouse/PaintingCirclesDemo.java)
malt bei jedem Knopfdruck einen Kreis.[^mausklick-kreise-malen]

[^mausklick-kreise-malen]: https://engine-alpha.org/wiki/v4.x/User_Input#Auf_Mausklick_reagieren:_Kreise_malen

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/mouse/PaintingCirclesDemo.java#L23-L54](https://github.com/engine-pi/engine-pi/blob/ddae75531cadc170a95cc6e9b4dca0ad18a34327/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/mouse/PaintingCirclesDemo.java#L23-L54)

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

<!-- #### Schnittstelle `MouseClickListener` -->

Das Interface
[MouseClickListener](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/MouseClickListener.html)
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

<!-- #### `Vector` -->

Statt zwei `double`-Parametern für die X/Y-Koordinaten des Klicks, nutzt die
Engine hier die interne Klasse
[Vector](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/Vector.html).
Die Klasse `Vector` wird in der Engine durchgehend verwendet und ist essentiell
für die Arbeit mit der Engine.

https://engine-alpha.org/wiki/v4.x/User_Input#Vector

![Ein besseres Kreismalen: Auswählbare Größe und Farbe über ein kleines Menü](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/input/PaintingCirclesAdvancedDemo.gif)

[PaintingCirclesAdvancedDemo.java](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/input/mouse/PaintingCirclesAdvancedDemo.java)

### Voreingestellte Tastenkürzel und Steuerungsmöglichkeiten

Die Engine registriert im Auslieferungszustand einige wenige [grundlegenden
Maus- und
Tastatur-Steuermöglichkeiten](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/DefaultControl.html).

Diese sind hoffentlich beim Entwickeln hilfreich. Mit den statischen Methoden
[Game.removeDefaultControl()](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/Game.html#removeDefaultControl()>)
können diese Kürzel entfernt oder mit
[Game.setDefaultControl(DefaultControl)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/Game.html#setDefaultControl(de.pirckheimer_gymnasium.engine_pi.event.DefaultListener)>)
neue Kürzel gesetzt werden.

- `ESCAPE` zum Schließen des Fensters.
- `ALT + a` zum An- und Abschalten der Figuren-Zeichenroutine (Es
  werden nur die Umrisse gezeichnet, nicht die Füllung).
- `ALT + d` zum An- und Abschalten des Debug-Modus.
- `ALT + p` zum Ein- und Ausblenden der Figuren-Positionen (sehr
  ressourcenintensiv).
- `ALT + s` zum Speichern eines Bildschirmfotos (unter `~/engine-pi`).
- `ALT + Pfeiltasten` zum Bewegen der Kamera.
- `ALT + Mausrad` zum Einstellen des Zoomfaktors.

## Game Loop

https://engine-alpha.org/wiki/v4.x/Game_Loop

<!-- ### How-To Engine Code: Der Game Loop -->

Das Snake-Spiel ist ein erstes interaktives Spiel. Es nutzt den Game Loop der
Engine. Dieser funktioniert folgendermaßen:

![Der Engine Pi Game Loop](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/GameLoop.png)

Ein Film besteht aus 24 bis 60 Bildern pro Sekunde, die schnell hintereinander
abgespielt werden, um die Illusion von Bewegung zu erzeugen. Ähnlich werden bei
(den meisten) Computerspielen 30 bis 60 Bilder pro Sekunde in Echtzeit
gerendert, um die selbe Illusion von Bewegung zu erzeugen. Nach jedem Bild
berechnet die Engine intern die nächsten Schritte und gibt die relevanten
Ereignisse (Tastendruck, Kollision, Frame-Update) an die entsprechenden Listener
weiter.

Alle Spiel-Logik ist also in den Listener-Interfaces geschrieben. Guter
Engine-Code ist verpackt in Interfaces nach Spiel-Logik.

<!-- ### Snake ohne Körper -->

Das folgende Program implementiert ein einfaches Snake-Spiel mit einem
Steuerbaren Kreis und dem Ziel, Goodies zu sammeln.

![Das Snake-Spiel: Der Kreis jagt die willkürlich auftauchenden Texte](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/Snake_Minimal.gif)

```java
public class SnakeMinimal extends Scene
{
    private Text scoreText = new Text("Score: 0", 1.4);

    private int score = 0;

    private Snake snake = new Snake();

    public SnakeMinimal()
    {
        add(snake);
        scoreText.setPosition(-9, 5);
        add(scoreText);
        placeRandomGoodie();
    }

    public void setScore(int score)
    {
        this.score = score;
        scoreText.setContent("Score: " + score);
    }

    public void increaseScore()
    {
        setScore(score + 1);
    }

    public void placeRandomGoodie()
    {
        double x = Random.range() * 10 - 5;
        double y = Random.range() * 10 - 5;
        Goodie goodie = new Goodie();
        goodie.setCenter(x, y);
        add(goodie);
        goodie.addCollisionListener(snake, goodie);
    }

    private class Snake extends Circle
            implements FrameUpdateListener, KeyStrokeListener
    {
        private Vector movement = new Vector(0, 0);

        public Snake()
        {
            super(1);
            setColor(Color.GREEN);
        }

        @Override
        public void onFrameUpdate(double timeInS)
        {
            moveBy(movement.multiply(timeInS));
        }

        @Override
        public void onKeyDown(KeyEvent keyEvent)
        {
            switch (keyEvent.getKeyCode())
            {
            case KeyEvent.VK_W:
                movement = new Vector(0, 5);
                break;

            case KeyEvent.VK_A:
                movement = new Vector(-5, 0);
                break;

            case KeyEvent.VK_S:
                movement = new Vector(0, -5);
                break;

            case KeyEvent.VK_D:
                movement = new Vector(5, 0);
                break;
            }
        }
    }

    private class Goodie extends Text implements CollisionListener<Snake>
    {
        public Goodie()
        {
            super("Eat Me!", 1);
            setColor(Color.RED);
        }

        @Override
        public void onCollision(CollisionEvent<Snake> collisionEvent)
        {
            increaseScore();
            remove();
            placeRandomGoodie();
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new SnakeMinimal());
    }
}

```

<!-- ### Snake: Frame-Weise Bewegung -->

Die Snake ist der spielbare Charakter. Sie soll sich jeden Frame in eine der
vier Himmelsrichtungen bewegen.

Die Bewegung der Snake soll möglichst flüssig sein. Daher wird die Bewegung in
jedem einzelnen Frame ausgeführt, um maximal sauber auszusehen. Dazu
implementiert die Snake das Engine-Interface FrameUpdateListener, um in jedem
Frame seine Bewegungslogik auszuführen.

Hierzu kennt die Snake ihre aktuelle Geschwindigkeit als gerichteten Vektor (in
Meter/Sekunde). Ein Frame ist deutlich kürzer als eine Sekunde. Mathematik zur
Hilfe! `v = s/t` und damit `s = v\*t`. Jeden Frame erhält die Snake die
tatsächlich vergangene Zeit `t` seit dem letzten Frame-Update und verrechnet
diese mit ihrer aktuellen Geschwindigkeit `v`:

```java
@Override
public void onFrameUpdate(double timeInS)
{
    moveBy(movement.multiply(timeInS));
}
```

<!-- ### Bewegungsgeschwindigkeit festlegen -->

Was die tatsächliche Bewegungsgeschwindigkeit der Snake ist, hängt davon ab,
welche Taste der Nutzer zuletzt runtergedrückt hat und ist in der Snake über
`KeyStrokeListener` gelöst wie im vorigen Tutorial:

```java
@Override
public void onKeyDown(KeyEvent keyEvent)
{
    switch (keyEvent.getKeyCode())
    {
    case KeyEvent.VK_W:
        movement = new Vector(0, 5);
        break;

    case KeyEvent.VK_A:
        movement = new Vector(-5, 0);
        break;

    case KeyEvent.VK_S:
        movement = new Vector(0, -5);
        break;

    case KeyEvent.VK_D:
        movement = new Vector(5, 0);
        break;
    }
}
```

<!-- ### Auf Kollisionen reagieren: Goodies -->

Die Schlange bewegt sich. Als nächstes braucht sie ein Ziel, auf das sie sich
zubewegt. Hierzu schreiben wir eine Klasse für Goodies.

Ein Goodie wartet nur darauf, gegessen zu werden. Damit nicht jeden Frame "von
Hand" überprüft werden muss, ob die Schlange das Goodie berührt, lässt sich das
ebenfalls über ein Listener-Interface lösen: `CollisionListener`. Das
Interface ist mit Java Generics umgesetzt, daher die spitzen Klammern. Einige
Vorteile hiervon kannst du in der Dokumentation durchstöbern.

Wenn das Goodie mit der Schlange kollidiert, so soll der Punktestand geändert,
das Goodie entfernt, und ein neues Goodie platziert werden.

```java
@Override
public void onCollision(CollisionEvent<Snake> collisionEvent)
{
    increaseScore();
    remove();
    placeRandomGoodie();
}
```

In der `placeRandomGoodie()`-Methode wird ein neues Goodie erstellt und mit
`Random` an einer zufälligen Stelle auf dem Spielfeld platziert. Weil das
Goodie nur auf Kollision mit der Schlange reagieren soll (und nicht z.B. auf
Kollision mit dem "Score"-Text), wird es abschließend als Collision-Listener
spezifisch mit der Schlange angemeldet:

```java
public void placeRandomGoodie()
{
    double x = Random.range() * 10 - 5;
    double y = Random.range() * 10 - 5;
    Goodie goodie = new Goodie();
    goodie.setCenter(x, y);
    add(goodie);
    goodie.addCollisionListener(snake, goodie);
}
```

<!-- ### Anregung zum Experimentieren -->

![Eine Snake, die mit jedem Pickup wächst](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/Snake_Advanced.gif)

- Deadly Pickups: Es gibt noch keine Gefahr für die Schlange. Ein giftiges
  Pick-Up tötet die Schlange und beendet das Spiel (oder zieht der Schlange
  einen von mehreren Hit Points ab).
- Smoother Movement: Die aktuelle Implementierung für die Bewegung der Schlange
  ist sehr steif und die Schlange kann nicht stehen bleiben. Vielleicht möchtest
  du dem Spieler mehr Kontrolle über die Schlange geben: Statt des
  KeyStrokeListener-Interfaces, kann die Schlange in ihrer
  onFrameUpdate(float)-Methode abfragen, ob gerade der W/A/S/D-Key
  heruntergedrückt ist und sich entsprechend dessen weiter bewegen. Tipp: Die
  Methode ea.Game.isKeyPressed(int keycode) ist hierfür hilfreich.
- Escalating Difficulty: Je mehr Pick-Ups gesammelt wurden (und damit desto
  höher der Score), desto schneller bewegt sich die Schlange. Actual Snake: Wenn
  du Lust auf eine Herausforderung hast, kannst du aus dem Spiel ein echtes
  Snake machen: Beim aufnehmen eines Pick-Ups wird die Schlange um ein Glied
  länger. Die Glieder bewegen sich versetzt mit der Schlange weiter. Wenn die
  Schlange sich selbst berührt, ist das Spiel beendet.

## Szenen

https://engine-alpha.org/wiki/v4.x/Scenes

Ein Spiel hat oftmals mehrere verschiedene „Teile“, zwischen denen der Spieler
während des Spielens wechselt. Zum Beispiel gibt es neben der Hauptdarstellung,
Pausenmenüs, Inventare, Hauptmenüs, etc. Es wäre unnötig komplex, für den
Wechsel zwischen diesen Szenen stets alle grafischen Objekte zu zerstören und
wieder neu aufzubauen. Stattdessen werden alle grafischen Objekte in einer
`Scene` hinzugefügt. Dies passiert - wie in den vorigen Tutorials - über die
Methode `add(...)`.

Über die Klasse `Game` kann schnell zwischen Szenen gewechselt werden. Dazu gibt
es die Methode `Game.transitionToScene(Scene)`.

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/scenes/Scene_Demonstration.png)

<!-- #### Ein Pausenmenü -->

Das folgende Beispiel enthält zwei Szenen: Eine einfache Animation und ein
Pausenmenü. Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück

```java
public class MainScene extends Scene implements KeyStrokeListener
{
    private PauseMenu pauseMenu;

    public MainScene()
    {
        pauseMenu = new PauseMenu(this);
        Rectangle toAnimate = new Rectangle(5, 2);
        toAnimate.setCenter(0, -5);
        toAnimate.setColor("orange");
        CircleAnimation animation = new CircleAnimation(toAnimate,
                new Vector(0, 0), 8, true, true);
        addFrameUpdateListener(animation);
        add(toAnimate);
        addKeyStrokeListener(this);
        Text info = new Text("Pause mit P", 0.5);
        info.setCenter(-7, -5);
        add(info);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        if (keyEvent.getKeyCode() == KeyEvent.VK_P)
        {
            gotoPause();
        }
    }

    private void gotoPause()
    {
        Game.transitionToScene(pauseMenu);
    }

    private class PauseMenu extends Scene
    {
        private Scene mainScene;

        public PauseMenu(Scene mainScene)
        {
            this.mainScene = mainScene;
            MenuItem back = new MenuItem(new Vector(0, -5), "Zurück");
            add(back, back.label);
            Text headline = new Text("Mach mal Pause.", 2);
            headline.setCenter(0, 3);
            add(headline);
        }

        private class MenuItem extends Rectangle
                implements MouseClickListener, FrameUpdateListener
        {
            private Text label;

            public MenuItem(Vector center, String labelText)
            {
                super(10, 1.5);
                label = new Text(labelText, 1);
                label.setLayerPosition(1);
                label.setColor("black");
                label.setCenter(center);
                setLayerPosition(0);
                setColor("blueGreen");
                setCenter(center);
            }

            @Override
            public void onMouseDown(Vector clickLoc, MouseButton mouseButton)
            {
                if (contains(clickLoc))
                {
                    Game.transitionToScene(mainScene);
                }
            }

            @Override
            public void onFrameUpdate(double pastTime)
            {
                if (contains(Game.getMousePositionInCurrentScene()))
                {
                    setColor("blue");
                }
                else
                {
                    setColor("blueGreen");
                }
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(600, 400, new MainScene());
    }
}
```

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/scenes/Tutorial_Pause_Menu.gif)

<!-- ### Die zwei Szenen -->

Die Hauptszene ist `MainScene`. Hier könnte ein Game Loop für ein
Spiel stattfinden. Dieses Tutorial zeigt stattdessen eine kleine Animation.

Die zweite Szene heißt `PauseMenu`. In ihr gibt es eine Textbotschaft und einen
kleinen Knopf, um das Menü wieder zu verlassen.

```java
public class MainScene extends Scene
{
    private Scene pauseMenu;
    //...
}

private class PauseMenu extends Scene
{
    private Scene mainScene;
    //...
}
```

Die Haupt-Szene wird per Knopfdruck pausiert. Wird der P-Knopf gedrückt, wird
die Transition ausgeführt:

```java
private void gotoPause()
{
    Game.transitionToScene(pauseMenu);
}
```

Das Pausenmenü wird statt mit Tastatur per Mausklick geschlossen. Im internen
Steuerelement `MenuItem` wird dafür die entsprechende Methode aufgerufen, wann
immer ein Mausklick auf dem Element landet - dies wird durch die Methode
`contains(Vector)` geprüft:

```java
@Override
public void onMouseDown(Vector clickLoc, MouseButton mouseButton)
{
    if (contains(clickLoc))
    {
        Game.transitionToScene(mainScene);
    }
}
```

<!-- ### Kosmetische Kleinigkeiten -->

In der Hauptszene findet eine interpolierte Rotationsanimation statt. Diese
rotiert ein oranges Rechteck wiederholend um den Punkt (0|0). Eine volle
Rotation im Uhrzeigersinn dauert 8 Sekunden.

```java
Rectangle toAnimate = new Rectangle(5, 2);
toAnimate.setCenter(0, -5);
toAnimate.setColor("orange");
CircleAnimation animation = new CircleAnimation(toAnimate,
        new Vector(0, 0), 8, true, true);
addFrameUpdateListener(animation);
add(toAnimate);
```

Das Pausenmenü hat einen Hover-Effekt. Hierzu wird in jeden Einzelbild
überprüft, ob die Maus derzeit innerhalb des Steuerelementes liegt und abhängig
davon die Rechtecksfarbe ändert. Hierzu wird die Methode
`Game.getMousePositionInCurrentScene()` genutzt:

```java
@Override
public void onFrameUpdate(double pastTime)
{
    if (contains(Game.getMousePositionInCurrentScene()))
    {
        setColor("blue");
    }
    else
    {
        setColor("blueGreen");
    }
}
```

<!-- ### Anmerkungen und Beobachtungen -->

Die Kreisrotation in der Hauptszene geht nicht weiter, solange das Pausenmenü
die aktive Szene ist. Dies liegt daran, dass die Animation als
`FrameUpdateListener` in der Hauptszene angemeldet wurde
(`addFrameUpdateListener(animation)`). Alle Beobachter einer Szene können nur
dann aufgerufen werden, wenn die Szene aktiv ist.
Deshalb lässt sich das Pausenmenü nicht durch drücken von P beenden. Der
`KeyStrokeListener`, der bei Druck von P zum Pausenmenü wechselt, ist in der
Hauptszene angemeldet.

## Physics

https://engine-alpha.org/wiki/v4.x/Physics

Die Engine Pi nutzt eine [Java-Version](http://jbox2d.org/) von [Box2D](https://box2d.org/). Diese mächtige
und effiziente Physics-Engine ist in der Engine Pi leicht zu bedienen und
ermöglicht es, mit wenig Aufwand mechanische Phänomene in ein Spiel zu bringen.

Die Physics Engine basiert auf den Prinzipien der [klassischen
Mechanik](https://de.wikipedia.org/wiki/Klassische_Mechanik).

### Tutorial: Dominosteine umwerfen

Um die Grundlagen der Engine Pi Physics zu testen, bauen wir eine einfache
Kettenreaktion: Ein Ball wird gegen eine Reihe von Dominos geworfen.

Bevor wir die Physik einschalten, bauen wir das Spielfeld mit allen Objekten auf:

https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/DominoesDemo.java

```java
public class DominoesDemo extends Scene
        implements FrameUpdateListener, MouseClickListener
{
    private Rectangle ground;

    private Rectangle wall;

    private Circle ball;

    private Rectangle angle;

    public DominoesDemo()
    {
        setupBasicObjects();
        makeDominoes(20, 0.4, 3);
    }

    private void setupBasicObjects()
    {
        // Boden auf dem die Dominosteine stehen
        ground = new Rectangle(200, 2);
        ground.setCenter(0, -5);
        ground.setColor(Color.WHITE);
        add(ground);
        // Der Ball, der die Dominosteine umwerfen soll.
        ball = new Circle(0.5);
        ball.setColor(Color.RED);
        ball.setPosition(-10, -2);
        add(ball);
        // Eine senkrechte Wand links der Simulation
        wall = new Rectangle(1, 40);
        wall.setPosition(-14, -4);
        wall.setColor(Color.WHITE);
        add(wall);
    }

    private void makeDominoes(int num, double width, double height)
    {
        for (int i = 0; i < num; i++)
        {
            Rectangle domino = new Rectangle(width, height);
            domino.setPosition(i * 3 * width, -4);
            domino.setColor(Color.BLUE);
            add(domino);
        }
    }
}
```

Dieser Code baut ein einfaches Spielfeld auf: Ein roter Ball, ein paar
Dominosteine, und ein weißer Boden mit Wand.

![Das Spielbrett ist aufgebaut, allerdings passiert noch nichts interessantes. Zeit für Physik!](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/Dominos_1-statisch.png)

Wir erwarten verschiedenes Verhalten von den physikalischen Objekten. Dies
drückt sich in verschiedenen `BodyTypes` aus:

- Der Ball und die Dominos sollen sich verhalten wie normale physische Objekte:
  Der Ball prallt an den Dominos ab und die Steine fallen um. Diese `Actors` haben
  einen dynamischen Körper.
- Aber der Boden und die Wand sollen nicht wie die Dominos umfallen. Egal mit
  wie viel Kraft ich den Ball gegen die Wand werfe, sie wird niemals nachgeben.
  Diese `Actors` haben einen statischen Körper.

Mit der Methode `Actor.setBodyType(BodyType)` wird das grundlegende Verhalten
eines `Actors` bestimmt. Zusätzlich wird mit `Scene.setGracity(Vector)` eine
Schwerkraft gesetzt, die auf den Ball und die Dominos wirkt.
Jetzt wirkt Schwerkraft auf die dynamischen Objekte und der statische Boden
hält den Fall

In einer `setupPhysics()`-Methode werden die Body Types für die Actors gesetzt und
die Schwerkraft (standardmäßige `9,81 m/s^2`, gerade nach unten) aktiviert:

```java
    private void setupPhysics()
    {
        ground.makeStatic();
        wall.makeDynamic();
        ball.makeDynamic();
        setGravityOfEarth();
    }
```

Zusätzlich werden die Dominos in `makeDominoes()` mit `domino.makeDynamic();`
eingerichtet.

![Jetzt wirkt Schwerkraft auf die dynamischen Objekte und der statische Boden hält den Fall](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/Dominos_2-dynamisch.gif)

Dynamische und statische Körper sind die essentiellsten Body Types in der
Engine, allerdings nicht die einzigen. Du findest einen Umriss aller Body Types
in der Dokumentation von `BodyType` und eine vergleichende Übersicht in der
dedizierten Wikiseite Den Ball Werfen Mit einem Methodenaufruf fliegt der Ball

Zeit, die Dominos umzuschmeißen! Die Methode
`applyImpulse(Vector)` erlaubt, den Ball physikalisch korrekt zu
'werfen'.

Mit der Zeile `ball.applyImpulse(new Vector(15, 12));` kannst der erste
Ballwurf getestet werden.

![Mit einem Methodenaufruf fliegt der Ball](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/Dominos_3-Wurf.gif)

Um hieraus eine Spielmechanik zu bauen, soll der Spieler Richtung und Stärke des
Wurfes mit der Maus kontrollieren können: Per Mausklick wird der Ball in
Richtung des Mauscursors katapultiert. Das Angle-Objekt hilft dem Spieler

Hierzu wird ein weiteres Rechteck angle eingeführt, das die Richtung des
Impulses markiert:

```java
    private void setupAngle()
    {
        angle = new Rectangle(1, 0.1);
        angle.setColor(Color.GREEN);
        add(angle);
    }
```

![Visualisierung des Wurfwinkels](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/Dominos_4-Wurfwinkel-Visualisierung.gif)

Wir wollen, dass das Rechteck stets Ball und Maus verbindet. Die einfachste
Methode hierzu ist, in jedem Frame das Rechteck erneut an die Maus anzupassen.
Dafür implementiert die Dominoes-Klasse das Interface `FrameUpdateListener` und
berechnet frameweise anhand der aktuellen Mausposition die korrekte Länge und
den korrekten Winkel, um die visuelle Hilfe richtig zu positionieren:

```java
    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector mousePosition = getMousePosition();
        Vector ballCenter = ball.getCenter();
        Vector distance = ballCenter.getDistance(mousePosition);
        angle.setPosition(ball.getCenter());
        angle.setWidth(distance.getLength());
        double rot = Vector.RIGHT.getAngle(distance);
        angle.setRotation(rot);
    }
```

Zuletzt muss der Ballwurf bei Mausklick umgesetzt werden. Hierzu wird noch das
Interface `MouseClickListener` implementiert:

```java
    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        Vector impulse = ball.getCenter().getDistance(position).multiply(5);
        ball.applyImpulse(impulse);
    }
```

- Von Dominos zu Kartenhaus: Mehrere Schichten von Dominos, mit quer gelegten
  Steinen als Fundament zwischen den Schichten, sorgen für mehr Spaß bei der
  Zerstörung.

- Reset Button: Ein Knopfdruck setzt den Ball auf seine Ursprüngliche Position
  (und Geschwindigkeit) zurück; dabei werden all Dominos wieder neu aufgesetz.

### Schwerkraft

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/single_aspects/GravityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/single_aspects/GravityDemo.java)

```java
public class GravityDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public GravityDemo()
    {
        setGravity(0, -9.81);
        createBorder(-5, 4, false);
        createBorder(-5, -5, false);
        createBorder(-5, -5, true);
        createBorder(4, -5, true);
        rectangle = new Rectangle(1, 1);
        rectangle.makeDynamic();
        add(rectangle);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 1)
                : new Rectangle(1, 10);
        rectangle.setPosition(x, y);
        rectangle.makeStatic();
        add(rectangle);
        return rectangle;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> setGravity(0, 9.81);
        case KeyEvent.VK_DOWN -> setGravity(0, -9.81);
        case KeyEvent.VK_RIGHT -> setGravity(9.81, 0);
        case KeyEvent.VK_LEFT -> setGravity(-9.81, 0);
        }
    }
}
```

### Elastizität

Wir setzen die Elastizität auf 0, damit beim ersten Kreis mit der
Stoßzahl 0 demonstriert werden kann, dass dieser nicht abprallt.

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/single_aspects/ElasticityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/single_aspects/ElasticityDemo.java)

```java
public class ElasticityDemo extends Scene
{
    private final Rectangle ground;

    public ElasticityDemo()
    {
        getCamera().setZoom(20);
        // Ein Reckteck als Boden, auf dem die Kreise abprallen.
        ground = new Rectangle(24, 1);
        ground.setPosition(-12, -16);
        ground.setElasticity(0);
        ground.makeStatic();
        setGravity(0, -9.81);
        add(ground);
        double elasticity = 0;
        for (double x = -11.5; x < 12; x += 2)
        {
            createCircle(x, elasticity);
            elasticity += 0.1;
        }
    }

    private void createCircle(double x, double elasticity)
    {
        Circle circle = new Circle(1);
        add(circle);
        circle.setElasticity(elasticity);
        circle.setPosition(x, 5);
        circle.makeDynamic();
        // Eine Beschriftung mit der Stoßzahl unterhalb des Kollisionsrechtecks
        DecimalFormat df = new DecimalFormat("0.00");
        Text label = new Text(df.format(elasticity), 0.8);
        label.setPosition(x, -17);
        label.makeStatic();
        add(label);
    }

}
```

### Dichte

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/single_aspects/DensityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/physics/single_aspects/DensityDemo.java)

```java
public class DensityDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle ground;

    private final Circle[] circles;

    private final Text[] densityLables;

    public DensityDemo()
    {
        circles = new Circle[3];
        densityLables = new Text[3];
        int density = 10;
        int x = -5;
        for (int i = 0; i < 3; i++)
        {
            circles[i] = createCircle(x, density);
            densityLables[i] = createDensityLables(x, density);
            x += 5;
            density += 10;
        }
        setGravity(0, -9.81);
        ground = new Rectangle(20, 1);
        ground.setPosition(-10, -5);
        ground.makeStatic();
        add(ground);
    }

    private Circle createCircle(double x, double density)
    {
        Circle circle = new Circle(1);
        circle.setPosition(x, 5);
        circle.setDensity(density);
        circle.makeDynamic();
        add(circle);
        return circle;
    }

    private Text createDensityLables(int x, int density)
    {
        Text text = new Text(density + "", 1);
        text.setPosition(x, -7);
        text.makeStatic();
        add(text);
        return text;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        for (Circle circle : circles)
        {
            circle.applyImpulse(0, 100);
        }
    }
}
```

## Stateful Animation

https://engine-alpha.org/wiki/v4.x/Stateful_Animation

Die
[StatefulAnimation](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/StatefulAnimation.html)
ist eine elegante Möglichkeit, komplexe Spielfiguren mit wenig Aufwand
umzusetzen.

Nehmen wir dieses Beispiel:

| Zustand | Animiertes GIF                                                                                               |
| ------- | ------------------------------------------------------------------------------------------------------------ |
| Idle    | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/idle.gif)         |
| Jumping | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/jump_1up.gif)     |
| Midair  | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/jump_2midair.gif) |
| Falling | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/jump_3down.gif)   |
| Landing | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/jump_4land.gif)   |
| Walking | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/walk.gif)         |
| Running | ![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/traveler/run.gif)          |

<!-- ### Zustandsübergangsdiagramm für die Figur -->

Ein mögliches Zustandsübergangsdiagramm für die Figur:

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/TransitionDiagram.png)

<!-- Die Zustände als Enumeration -->

Zustände einer Figur werden in der Engine
stets als [enum](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html) implementiert.

Diese enum definiert die Spielerzustände und speichert gleichzeitig die
Dateipfade der zugehörigen GIF-Dateien.

```java
public enum PlayerState
{
    IDLE("idle"), WALKING("walk"), RUNNING("run"), JUMPING("jump_1up"),
    MIDAIR("jump_2midair"), FALLING("jump_3down"), LANDING("jump_4land");

    private String filename;

    PlayerState(String filename)
    {
        this.filename = filename;
    }

    public String getGifFileLocation()
    {
        return "traveler/" + filename + ".gif";
    }
}
```

Ist beispielsweise das GIF des Zustandes
`JUMPING` gefragt, so ist es jederzeit mit `JUMPING.getGifFileLocation()`
erreichbar. Dies macht den Code deutlich wartbarer.

<!-- Die Klasse für den Player Character -->

Mit den definierten Zuständen in `PlayerState` kann nun die Implementierung der
eigentlichen Spielfigur beginnen:

```java
public class StatefulPlayerCharacter extends StatefulAnimation<PlayerState>
{

    public StatefulPlayerCharacter()
    {
        // Alle Bilder haben die Abmessung 64x64px und deshalb die gleiche Breite
        // und Höhe. Wir verwenden drei Meter.
        super(3, 3);
        setupPlayerStates();
        setupAutomaticTransitions();
        setupPhysics();
    }

    private void setupPlayerStates()
    {
        for (PlayerState state : PlayerState.values())
        {
            Animation animationOfState = Animation
                    .createFromAnimatedGif(state.getGifFileLocation(), 3, 3);
            addState(state, animationOfState);
        }
    }

    private void setupAutomaticTransitions()
    {
        setStateTransition(PlayerState.MIDAIR, PlayerState.FALLING);
        setStateTransition(PlayerState.LANDING, PlayerState.IDLE);
    }

    private void setupPhysics()
    {
        makeDynamic();
        setRotationLocked(true);
        setElasticity(0);
        setFriction(30);
        setLinearDamping(.3);
    }
}
```

In `setupPlayerStates()` werden alle in `PlayerState` definierten Zustände der
Spielfigur eingepflegt, inklusive des Einladens der animierten GIFs.

Zwei der Zustände bestehen nur aus einen Animationszyklus. Danach sollen sie in
einen anderen Zustand übergehen: `MIDAIR` geht über zu `FALLING` und `LANDING`
geht über zu `IDLE`. Diese Übergänge können direkt über die Methode
[setStateTransition(...)](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/StatefulAnimation.html)
umgesetzt werden.

Schließlich wird in `setupPhysics()` die Figur über die Engine-Physik noch
dynamisch gesetzt und bereit gemacht, sich als Platformer-Figur der Schwerkraft
auszusetzen. Der hohe Reibungswert `setFriction(30)` sorgt dafür, dass die Figur
später schnell auf dem Boden abbremsen kann, sobald sie sich nicht mehr bewegt.
Ein Verhalten, dass bei den meisten Platformern erwünscht ist.

<!-- Testbed -->

Damit die Figur getestet werden kann, schreiben wir ein schnelles Testbett für
sie. In einer `Scene` bekommt sie einen Boden zum Laufen:

![Der Zwischenstand: Noch passiert nicht viel.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_First_Testbed.gif)

```java
public class StatefulAnimationDemo extends Scene
{
    public StatefulAnimationDemo()
    {
        StatefulPlayerCharacter character = new StatefulPlayerCharacter();
        setupGround();
        add(character);
        getCamera().setFocus(character);
        setGravityOfEarth();
    }

    private void setupGround()
    {
        Rectangle ground = makePlatform(200, 0.2);
        ground.setCenter(0, -5);
        ground.setColor(new Color(255, 195, 150));
        makePlatform(12, 0.3).setCenter(16, -1);
        makePlatform(7, 0.3).setCenter(25, 2);
        makePlatform(20, 0.3).setCenter(35, 6);
        makeBall(5).setCenter(15, 3);
    }


    public static void main(String[] args)
    {
        Game.start(1200, 820, new StatefulAnimationDemo());
    }
}
```

Die Figur bleibt im IDLE-Zustand hängen. Nun gilt es, die übrigen
Zustandsübergänge zu implementieren.

<!-- Implementieren der Zustände & Übergänge -->

<!-- Springen -->

![Wir fokussieren uns nun auf die Übergänge zum Springen.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/TransitionDiagram_jumpstates.png)

Auf Tastendruck (Leertaste) soll die Spielfigur
springen, wenn sie auf festem Boden steht. Die Spielfigur implementiert nun
zusätzlich den `KeyStrokeListener` und führt auf Leertastendruck die Sprungroutine aus:

![Die Figur kann springen, aber nicht landen.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_First_Jump.gif)

```java
private void attemptJump()
{
    PlayerState state = getCurrentState();
    if (state == PlayerState.IDLE || state == PlayerState.WALKING
            || state == PlayerState.RUNNING)
    {
        if (isGrounded())
        {
            applyImpulse(new Vector(0, JUMP_IMPULSE));
            setState(PlayerState.JUMPING);
        }
    }
}
```

<!-- Fallen und Landen -->

![Die nächsten Übergänge, die wir umsetzen, sind für das Fallen und Landen.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/TransitionDiagram_vy_states.png)

Als nächstes sorgen wir dafür, dass die Figur landen kann und schließlich zurück
in den `IDLE`-Zustand kommt. Dafür ist die Geschwindigkeit der Figur in
Y-Richtung wichtig. Im Zustandsübergangsdiagramm haben wir dafür `v_y < 0` als
Fallen definiert und `v_y = 0` als Stehen. Das ist im Modell in Ordnung,
allerdings ist die Physik mit Fließkomma-Zahlen nicht ideal für „harte“
Schwellwerte. Stattdessen definieren wir einen Grenzwert, innerhalb dessen wir
auf 0 runden. (`private static final float THRESHOLD = 0.01;`).

Unsere Spielfigur soll in jedem Einzelbild ihre eigene Y-Geschwidingkeit
überprüfen. Dazu implementiert sie nun zusätzlich `FrameUpdateListener` und
prüft in jedem Frame entsprechend unseres Zustandsübergangsdiagrammes:

![Die Figur hat jetzt einen vollen Sprungzyklus](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_Full_Jump2.gif)

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L108-L133](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L108-L133)

```java
@Override
    public void onFrameUpdate(double dT)
    {
        Vector velocity = getVelocity();
        PlayerState state = getCurrentState();
        if (velocity.getY() < -THRESHOLD)
        {
            switch (state)
            {
            case JUMPING:
                setState(PlayerState.MIDAIR);
                break;

            case IDLE:
            case WALKING:
            case RUNNING:
                setState(PlayerState.FALLING);
                break;

            default:
                break;
            }
        }
        else if (velocity.getY() < THRESHOLD && state == PlayerState.FALLING)
        {
            setState(PlayerState.LANDING);
        }

    }
```

<!-- Player Movement -->

Die letzten zu implementierenden Zustände sind die Bewegung des Spielers. Durch
die Physik-Engine gibt es viele Möglichkeiten, Bewegung im Spiel zu simulieren.
Ein physikalisch korrekte Implementierung ist die kontinuierliche Anwendung
einer Bewegungskraft:

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_Player_Movement.png)

Die (je nach Tastendruck gerichtete) Kraft beschleunigt die Spielfigur, bis die
Reibung die wirkende Kraft ausgleicht. In der Methode `setupPhysics()` wurden
bereits folgende Reibung für die Figur aktiviert:

- Luftreibung (gesetzt mit `setLinearDamping(.3)`)
- Kontaktreibung, z. B mit Platformen (gesetzt mit `setFriction(30)`)

Die Maximalgeschwindigkeit sowie die konstant wirkende Kraft setzen wir als
Konstanten in der Klasse der Figur, um diese Werte schnell ändern zu können:

```java
private static final Float MAX_SPEED = 20;
private static final float FORCE = 16000;
```

Um die Kraft und die Geschwindigkeit frameweise zu implementieren, wird die
Methode `onFrameUpdate(double pastTime)` erweitert:

![Die Figur kann sich bewegen, jedoch resultiert dies noch nicht in
Zustandsänderung.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_Movement_Base.gif)

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L134-L146](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L134-L146)

```java
//In: onFrameUpdate(double pastTime)

if (Math.abs(velocity.getX()) > MAX_SPEED)
{
    setVelocity(new Vector(Math.signum(velocity.getX()) * MAX_SPEED,
            velocity.getY()));
}
if (Game.isKeyPressed(KeyEvent.VK_A))
{
    applyForce(new Vector(-FORCE, 0));
}
else if (Game.isKeyPressed(KeyEvent.VK_D))
{
    applyForce(new Vector(FORCE, 0));
}
```

<!-- Die Übergänge IDLE - WALKING - RUNNING -->

![Die letzten zu implementierenden Zustandsübergänge hängen von der
Spielerbewegung ab.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/TransitionDiagram_vx_states.png)

Die Figur kann jetzt voll gesteuert werden. Die Zustände `WALKING` und `RUNNING`
können nun eingebracht werden. Ist die Figur in einem der drei „bodenständigen“
Zustände (`IDLE`, `WALKING`, `RUNNING`), so hängt der Übergang zwischen diesen
Zuständen nur vom Betrag ihrer Geschindigkeit ab:

- Bewegt sich die Figur „langsam“, so ist sie `WALKING`.
- Bewegt sich die Figur „schnell“, so ist sie `RUNNING`.
- Bewegt sich die Figur „gar nicht“, so ist sie `IDLE`.

Um die Begriffe „langsam“ und „schnell“ greifbar zu machen, ist einen Grenzwert
nötig. Dazu definieren wir Konstanten in der Figur:

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L37-L39](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L37-L39)

```java
private static final double RUNNING_THRESHOLD = 10;

private static final double WALKING_THRESHOLD = 1;
```

Sobald sich die Figur mindestens 1 Meter pro Sekunde bewegt, zählt sie als `WALKING`,
sobald sie sich mindestens 10 Meter pro Sekunde bewegt (die Hälfte der maximalen
Geschwindigkeit), so zählt sie als `RUNNING`.

Auf diese Grenzwerte wird jeden Frame in der `onFrameUpdate(...)` der Spielfigur
geprüft, genauso wie zuvor die Y-Geschwindigkeit implementiert wurde. Damit ist
die neue `onFrameUpdate(...)`:

![Die Figur ist mit ihren Zuständen und Übergängen
vollständig implementiert.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_Movement_Full.gif)

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L107-L172](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L107-L172)

```java
@Override
public void onFrameUpdate(double dT)
{
    Vector velocity = getVelocity();
    PlayerState state = getCurrentState();
    if (velocity.getY() < -THRESHOLD)
    {
        switch (state)
        {
        case JUMPING:
            setState(PlayerState.MIDAIR);
            break;

        case IDLE:
        case WALKING:
        case RUNNING:
            setState(PlayerState.FALLING);
            break;

        default:
            break;
        }
    }
    else if (velocity.getY() < THRESHOLD && state == PlayerState.FALLING)
    {
        setState(PlayerState.LANDING);
    }
    if (Math.abs(velocity.getX()) > MAX_SPEED)
    {
        setVelocity(new Vector(Math.signum(velocity.getX()) * MAX_SPEED,
                velocity.getY()));
    }
    if (Game.isKeyPressed(KeyEvent.VK_A))
    {
        applyForce(new Vector(-FORCE, 0));
    }
    else if (Game.isKeyPressed(KeyEvent.VK_D))
    {
        applyForce(new Vector(FORCE, 0));
    }
    if (state == PlayerState.IDLE || state == PlayerState.WALKING
            || state == PlayerState.RUNNING)
    {
        double velXTotal = Math.abs(velocity.getX());
        if (velXTotal > RUNNING_THRESHOLD)
        {
            changeState(PlayerState.RUNNING);
        }
        else if (velXTotal > WALKING_THRESHOLD)
        {
            changeState(PlayerState.WALKING);
        }
        else
        {
            changeState(PlayerState.IDLE);
        }
    }
    if (velocity.getX() > 0)
    {
        setFlipHorizontal(false);
    }
    else if (velocity.getX() < 0)
    {
        setFlipHorizontal(true);
    }
}
```

Die letzte Überprüfung der X-Geschwindigkeit dient dazu, die Bewegungsrichtung
festzustellen. Mit dieser Info kann zum richtigen Zeitpunkt über
`setFlipHorizontal(boolean flip)` die Blickrichtung der Figur angepasst werden.

## Zeitsteuerung

```java
import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

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
            addKeyStrokeListener((e) -> {
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

## Kollisionen-Erkennung

https://engine-alpha.org/wiki/v4.x/Collision

<!-- ### Spielkonzept und grundlegender Aufbau -->

Ein Frosch soll fröhlich durch das Spiel springen und sich vom Boden abstoßen,
wenn immer er die Chance dazu hat.

Dieser Frosch soll durch das Spiel springen:
![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/froggy/Frog.png)

In der Scene `FroggyJump` kann der Spieler ein
Objekt der Klasse `Frog` steuern. Zusätzlich geben Objekte der Klasse `Platform`
halt.

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/collision/FroggyJump.java](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/collision/FroggyJump.java)

Damit ergibt sich das Codegerüst für das Spiel:

```java
public class FroggyJump extends Scene
{
    private Frog frog;

    public FroggyJump()
    {
        frog = new Frog();
        add(frog);
        setGravity(Vector.DOWN.multiply(10));
        Camera camera = getCamera();
        camera.setFocus(frog);
        camera.setOffset(new Vector(0, 4));
        makeLevel(40);
        makePlatforms(10);
    }

    private void makePlatforms(int heightLevel)
    {
        for (int i = 0; i < heightLevel; i++)
        {
            Platform platform = new Platform(5, 1);
            platform.setPosition(0, i * 4);
            add(platform);
        }
    }
}

class Frog extends Image implements FrameUpdateListener
{
    private boolean canJump = true;

    private static double MAX_SPEED = 4;

    public Frog()
    {
        super("froggy/Frog.png", 25);
        makeDynamic();
        setRotationLocked(true);
    }

    public void setJumpEnabled(boolean jumpEnabled)
    {
        this.canJump = jumpEnabled;
    }

    public void kill()
    {
        Game.transitionToScene(new DeathScreen());
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector velocity = this.getVelocity();
        // A: Die Blickrichtung des Frosches steuern
        if (velocity.getX() < 0)
        {
            setFlipHorizontal(true);
        }
        else
        {
            setFlipHorizontal(false);
        }
        // B: Horizontale Bewegung steuern
        if (Game.isKeyPressed(KeyEvent.VK_A))
        {
            if (velocity.getX() > 0)
            {
                setVelocity(new Vector(0, velocity.getY()));
            }
            applyForce(Vector.LEFT.multiply(600));
        }
        else if (Game.isKeyPressed(KeyEvent.VK_D))
        {
            if (velocity.getX() < 0)
            {
                setVelocity(new Vector(0, velocity.getY()));
            }
            applyForce(Vector.RIGHT.multiply(600));
        }
        if (Math.abs(velocity.getX()) > MAX_SPEED)
        {
            setVelocity(new Vector(MAX_SPEED * Math.signum(velocity.getX()),
                    velocity.getY()));
        }
        // C: Wenn möglich den Frosch springen lassen
        if (isGrounded() && velocity.getY() <= 0 && canJump)
        {
            setVelocity(new Vector(velocity.getX(), 0));
            applyImpulse(Vector.UP.multiply(180));
        }
    }
}

class Platform extends Rectangle implements CollisionListener<Frog>
{
    public Platform(double width, double height)
    {
        super(width, height);
        makeStatic();
    }
}
```

Der Frosch kann sich bewegen, knallt aber unangenehmerweise noch gegen die Decke

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/FrogTutorial1.gif)

Ein paar Erklärungen zum Codegerüst für `FroggyJump`:

<!-- ### Physikalische Eigenschaften -->

Wie im Physics-Tutorial beschrieben, werden die physikalischen Eigenschaften der
Spielobjekte und ihrer Umgebung bestimmt:

- Platformen sind **statische** Objekte: Sie ignorieren Schwerkraft und können
  nicht durch andere Objekte verschoben werden (egal mit wie viel Kraft der
  Frosch auf sie fällt).
- Der Frosch ist ein **dynamisches** Objekt: Er lässt sich von der Schwerkraft
  beeinflussen und wird von den statischen Platformen aufgehalten.
- In der Scene `FroggyJump` existiert eine Schwerkraft von 10 m/s^2. Sie wird
  mit `setGravity(Vector)` gesetzt.

<!-- ### Bewegung des Frosches -->

Die Bewegung des Frosches wird in jedem Frame kontrolliert. Wie im Game Loop
Tutorial beschrieben, wird hierzu das Interface `FrameUpdateListener` genutzt.

In jedem Frame wird die Bewegung des Frosches in dreierlei hinsicht kontrolliert:

- Teil A: Blickrichtung des Frosches: Das Bild des Frosches wird gespiegelt,
  falls er sich nach links bewegt.
- Teil B: Horizontale Bewegung des Frosches: Jeden Frame, in dem der Spieler den
  Frosch (per Tastendruck) nach links oder rechts steuern möchte, wird eine
  Bewegungskraft auf den Frosch angewendet. Wird der Frosch in die Gegenrichtung
  seiner aktuellen Bewegung gesteuert, wird seine horizontale Geschwindigkeit
  zuvor auf 0 gesetzt, um ein langsames Abbremsen zu verhindern. Das ermöglicht
  schnelle Reaktion auf Nutzereingabe und ein besseres Spielgefühl. Zusätzlich
  wird seine Geschwindigkeit auf die Konstante `MAX_SPEED` begrenzt.
- Teil C: Springe, wenn möglich: Mit der Funktion `isGrounded()` bietet die
  Engine einen einfachen Test, um sicherzustellen, dass der Frosch Boden unter
  den Füßen hat. Wenn dies gegeben ist, wird ein Sprungimpuls auf den Frosch
  angewandt. Zuvor wird die vertikale Komponente seiner Geschwindigkeit auf 0
  festgesetzt - das garantiert, dass der Frosch jedes mal die selbe Sprunghöhe
  erreicht.

Die Kamera folgt dem Frosch

Der Frosch soll stets sichtbar bleiben. Hierzu werden zwei Funktionen der
Engine-Kamera genutzt:

1. Der Frosch wird mit `Camera.setFocus(Actor)` in den Mittelpunkt der Kamera
   gesetzt. Sie folgt dem Frosch.
2. Gleichzeitig soll der Frosch nicht exakt im Mittelpunkt des Bildschirms sein:
   Weil das Spielziel ist, sich nach oben zu bewegen, braucht der Spieler mehr
   Blick nach oben als nach unten. Mit `Camera.setOffset(Vector)` wird die
   Kamera nach oben verschoben.

<!-- ### Durch Platformen Springen: Kollisionen kontrollieren -->

Das Interface
[CollisionListener](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/CollisionEvent.html) wurde bereits in seiner grundlegenden Form im Nutzereingabe-Tutorial benutzt.

`CollisionListener` kann mehr als nur melden, wenn zwei Actor-Objekte sich
überschneiden. Um das `FroggyJump`-Spiel zu implementieren, nutzen wir weitere
Features.

Unser Frosch soll fähig sein, von unten „durch die Platform hindurch“ zu
springen. Von oben fallend soll er natürlich auf der Platform stehen bleiben.

Um diesen Effekt zu erzeugen, müssen Kollisionen zwischen Frosch und Platform
unterschiedlich behandelt werden:

1. Kollidiert der Frosch von unten, so soll die Kollision ignoriert werden.
   Er prallt so nicht von der Decke ab und kann weiter nach oben Springen.
2. Kollidiert der Frosch von oben, so soll die Kollision normal aufgelöst werden,
   sodass er nicht durch den Boden fällt.

Hierzu stellt das `CollisionEvent`-Objekt in der `onCollision`-Methode Funktionen bereit.

```java
class Platform extends Rectangle implements CollisionListener<Frog>
{
    public Platform(double width, double height)
    {
        super(width, height);
        makeStatic();
        addCollisionListener(Frog.class, this);
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent)
    {

        double frogY = collisionEvent.getColliding().getPosition().getY();
        if (frogY < getY())
        {
            collisionEvent.ignoreCollision();
            collisionEvent.getColliding().setJumpEnabled(false);
        }
    }

    @Override
    public void onCollisionEnd(CollisionEvent<Frog> collisionEvent)
    {
        collisionEvent.getColliding().setJumpEnabled(true);
    }
}
```

## Farben

In der ersten Reihe sind mehrere Bilder zu sehen, in der
Reihe unterhalb Rechtecke mit der Durchschnittsfarbe der Bilder, in der letzten
Reihe die Komplementärfarben der entsprechenden Bilder.

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/color-complementary/Images_derived_complementary-color.png)

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/actor/ImageAverageColorDemo.java](https://github.com/engine-pi/engine-pi/blob/main/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/actor/ImageAverageColorDemo.java)

```java
import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

public class ImageAverageColorDemo extends Scene
{
    public ImageAverageColorDemo()
    {
        getCamera().setMeter(90);
        double x = -4;
        for (String filepath : new String[] { "car/background-color-grass.png",
                "car/wheel-back.png", "car/truck-240px.png",
                "dude/background/snow.png", "dude/box/obj_box001.png",
                "dude/moon.png" })
        {
            createImageWithAverageColor(filepath, x);
            x = x + 1.2;
        }
    }

    private void createImageWithAverageColor(String filepath, double x)
    {
        var image = createImage(filepath, 1, 1).setPosition(x, 0);
        createRectangle(1.0, 1.0).setPosition(x, -1.2)
                .setColor(image.getColor());
        createRectangle(1.0, 0.5).setPosition(x, -1.9)
                .setColor(image.getComplementaryColor());
    }

    public static void main(String[] args)
    {
        Game.start(new ImageAverageColorDemo());
    }
}
```

`ALT + d` aktiviert den Debug-Modus: Die Bilder werden von Umrissen in den Komplementärfarben umrahmt.

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/color-complementary/Images_shapes.png)

`Alt + a` blendet die Figurenfüllungen aus. Es sind nur noch die Umrisse zu sehen.

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/color-complementary/Shapes-only.png)


## Java

### Java-Entwicklungsumgebung: IDE - Integrated Development Environment (integrierte Entwicklungsumgebung)

Eine integrierte Entwicklungsumgebung (IDE, von englisch integrated development environment) ist
eine Sammlung von Computerprogrammen, mit denen die Aufgaben der Softwareentwicklung
möglichst ohne Medienbrüche bearbeitet werden können.

<small>Quelle: [wikipedia.org](https://de.wikipedia.org/wiki/Integrierte_Entwicklungsumgebung)</small>

#### Bekannte IDEs

- [BlueJ](https://www.bluej.org/): Reduzierte IDE für pädagogische Zwecke
- [Visual Studio Code](https://code.visualstudio.com): von Microsoft entwickelt, für alle Sprachen einsetzbar, wegen vieler Erweiterungen, läuft auf Google Chrome
- [Eclipse](https://www.eclipse.org/downloads)
- [IntelliJ IDEA](https://www.jetbrains.com/de-de/idea): auf Java spezialisiert

Wir setzen die [Community Edition von IntelliJ](https://www.jetbrains.com/de-de/idea/download/other.html) ein.

### Game-Engine

Als Game-Engine kommt die [Engine Alpha](https://engine-alpha.org) zum Einsatz.
Der [Quell-Code](https://github.com/engine-alpha/engine-alpha) ist
auf Github gehostet. Die Engine Alpha wurde und wird von
[Michael Andonie](https://github.com/andonie)
und [Niklas Keller](https://github.com/kelunik) entwickelt.

Wir setzen jedoch einen Fork der Engine Alpha ein,
genannt [Engine Omega](https://github.com/Josef-Friedrich/engine-omega).
Im Gegensatz zur originalen Engine ist die
[Engine Omega](https://central.sonatype.com/artifact/de.pirckheimer_gymnasium.engine_pi/engine-omega)
über das wichtigste Repository für Java-Projekte das sogenannte
[Maven Central Repository](https://central.sonatype.com) abrufbar.

In der Projekt-Datei `pom.xml` ist die Engine Omega als
Abhängigkeit (`dependency`) hinterlegt.

```xml
<project>
  <dependencies>
    <dependency>
      <groupId>de.pirckheimer_gymnasium.engine_pi</groupId>
      <artifactId>engine-omega</artifactId>
      <version>0.2.0</version>
    </dependency>
  </dependencies>
</project>
```

### Java-Paketnamen

Um Pakete mit gleichem Namen zu vermeiden, haben sich in der Java-Welt folgende
Konvention für Paketnamen herausgebildet:

- Paketnamen bestehen nur aus Kleinbuchstaben und Unterstrichen `_` (um sie von Klassen zu unterscheiden).
- Paketnamen sind durch Punkte getrennt.
- Der Anfang des Paketnamens wird durch die Organisation bestimmt, die sie erstellt.

Um den Paketnamen auf der Grundlage einer Organisation zu bestimmen, wird die URL der Organisation umgedreht.
Beispielsweise wird aus der URL

    https://pirckheimer-gymnasium.de/tetris

der Paketname:

    de.pirckheimer_gymnasium.tetris

<small>Quelle: [baeldung.com](https://www.baeldung.com/java-packages#1-naming-conventions)</small>

### Importe von Java-Klassen aus Paketen

Java verfügt über unzählige vorgefertigte Klassen und Schnittstellen. Thematisch zusammengehörende Klassen und
Schnittstellen werden zu einem Paket (_package_) zusammengefasst. Die so entstehende Java-Bibliothek ist riesig und
enthält tausende verschiedener Klassen mit unterschiedlichsten Methoden. Um sich einer dieser Klassen bedienen
zu können, muss man sie in das gewünschte Projekt importieren. In Java funktioniert das mit dem Schlüsselwort
`import`.

**Syntax**

`import <paketname>.<klassenname>;` Importiert nur die gewünschte Klasse des angesprochenen Paketes.<br>
`import <paketname>.*;` Importiert sämtliche Klassen des angesprochenen Paketes.

**Beispiel**

`import java.util.Random;` Importiert die Klasse Random des Paketes java.util.<br>
`import java.util.*;` Importiert das vollständige Paket java.util.

<small>Quelle: Klett, Informatik 2, 2021, Seite 275</small>

### `super`-Schlüsselwort

Das Java-Schlüsselwort `super` hat drei explizite Verwendungsbereiche.

1. Zugriff auf die Attribute der Elternklasse, wenn die Kindklasse ebenfalls Attribute mit demselben Namen hat.
2. Aufruf des Konstruktoren der Elternklasse in der Kindklasse.
3. Aufruf der Methoden der Elternklasse in der Kindklasse, wenn die Kindklasse Methoden überschrieben hat.

<small>Quelle: [codegym.cc](https://codegym.cc/de/groups/posts/super-schlsselwort-in-java)</small>

### Überladen von Methoden

Überladen bedeutet, dass derselbe Methodenname mehrfach in einer Klasse verwendet werden kann.
Damit das Überladen möglich ist, muss wenigstens eine der folgenden Vorraussetzungen erfüllt sein:

1. Der Datentyp mindestens eines Übergabeparameters ist anders als in den übrigen
   gleichnamen Methoden.
2. Die Anzahl der Übergabeparameter ist unterschiedlich.

<small>Quelle: [Java-Tutorial.org ](https://www.java-tutorial.org/ueberladen_von_methoden.html)</small>

### Lambda-Ausdrücken

Mit Lambda-Ausdrücken kann man sich viel Schreibarbeit sparen. Klassen, die eine
sogenannten Funktionale Schnittstelle (Functional Interface) implementieren,
d. h. ein Interface mit genau einer abstrakten Methoden, können auch als
Lambda-Ausdruck formuliert werden.

Klasse, die das Interface/Schnittstelle `Runnable` implementiert.

```java
class MyRunnable implements Runnable
{
    public void run()
    {
        startTitleScene();
    }
}

delay(3, new MyRunnable());
```

Als anonyme Klasse

```java
delay(3, new Runnable()
{
    public void run()
    {
        startTitleScene();
    }
});
```

Als Lambda-Ausdruck (Name stammt vom [Lambda-Kalkül](https://de.wikipedia.org/wiki/Lambda-Kalk%C3%BCl) ab)

```java
delay(3, () -> startTitleScene());
```

### Entwurfsmuster [Schablonenmethode](https://de.wikipedia.org/wiki/Schablonenmethode)

Beim Schablonenmethoden-Entwurfsmuster wird in einer abstrakten Klasse das
Skelett eines Algorithmus definiert. Die konkrete Ausformung der einzelnen
Schritte wird an Unterklassen delegiert. Dadurch besteht die Möglichkeit,
einzelne Schritte des Algorithmus zu verändern oder zu überschreiben, ohne dass
die zu Grunde liegende Struktur des Algorithmus modifiziert werden muss. Die
Schablonenmethode (engl. template method) ruft abstrakte Methoden auf, die erst
in den Unterklassen definiert werden. Diese Methoden werden auch als
Einschubmethoden bezeichnet.

Quelle: [Wikipedia](https://de.wikipedia.org/wiki/Schablonenmethode)

### foreach-Schleife

for-each ist eine Art for-Schleife, die du verwendest, wenn du alle Elemente
eines Arrays oder einer Collection verarbeiten musst. Allerdings wird der
Ausdruck for-each in dieser Schleife eigentlich nicht verwendet. Die Syntax
lautet wie folgt:

```java
for (type itVar : array)
{
    // Operations
}
```

Wobei type der Typ der Iterator-Variable ist (der dem Datentyp der Elemente im
Array entspricht!), itVar ihr Name und array ein Array (andere Datenstrukturen
sind auch erlaubt, z. B. eine Collection, wie ArrayList), d. h. das Objekt, auf
dem die Schleife ausgeführt wird. Wie du siehst, wird bei diesem Konstrukt kein
Zähler verwendet: Die Iterator-Variable iteriert einfach über die Elemente des
Arrays oder der Collection. Wenn eine solche Schleife ausgeführt wird, wird der
Iterator-Variable nacheinander der Wert jedes Elements des Arrays oder der
Collection zugewiesen, woraufhin der angegebene Anweisungsblock (oder die
Anweisung) ausgeführt wird.

Quelle: [codegym.cc](https://codegym.cc/de/groups/posts/1011-die-for-each-schleife-in-java)

## Deutsche Übersetzungen von englischen Klassennamen

| englisch       | deutsch                                                |
| -------------- | ------------------------------------------------------ |
| Actor          | Figur                                                  |
| Rigid Body     | Starrer Körper                                         |
| BodyType       | Verhalten einer Figur in der physikalischen Simulation |
| Bounds         | Schranken, Abgrenzung                                  |
| DistanceJoint  | Stabverbindung                                         |
| Fixture        | Halterung, Kollisionsform                              |
| Frame          | Einzelbild                                             |
| Handler        | Steuerungsklasse                                       |
| Joint          | Verbindung                                             |
| Listener       | Beobachter                                             |
| Offset         | Verzug                                                 |
| PrismaticJoint | Federverbindung                                        |
| RevoluteJoint  | Gelenkverbindung                                       |
| RopeJoint      | Seilverbindung                                         |
| Shape          | Umriss                                                 |
| TurboFire      | Dauerfeuer                                             |
| WeldJoint      | Schweißnaht                                            |
