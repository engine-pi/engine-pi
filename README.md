[![Maven Central](https://img.shields.io/maven-central/v/de.pirckheimer-gymnasium/engine-pi.svg?style=flat)](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)
[![javadoc](https://javadoc.io/badge2/de.pirckheimer-gymnasium/engine-pi/javadoc.svg)](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi)

# Über diese Game Engine

![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/logo/logo.svg)

Die [Engine Pi](https://github.com/engine-pi/engine-pi) ist eine
einsteigerfreundliche Java-Game-Engine für den Informatik-Unterricht an
allgemeinbildenden Schulen mit [deutscher
Dokumentation](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi).

Die Engine Pi ist eine Fortführung und Erweiterung (Fork) der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) von [Michael
Andonie](https://github.com/andonie) und [Niklas
Keller](https://github.com/kelunik) und zwar ein Fork der [Core Engine
v4.x](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha). Die
Engine Alpha wird leider offensichtlich nicht mehr weiterentwickelt. Die
[Engine-Alpha-Edu](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha-edu)
Version mit deutschen Java Bezeichnern wurde nicht geforkt.

Da die [Engine-Alpha](https://github.com/engine-alpha/engine-alpha) momentan keine
Audio-Wiedergabe unterstützt, wurde der Audio-Code der
[LITIENGINE](https://github.com/gurkenlabs/litiengine) in die Engine Pi übernommen.
Die LITIENGINE ist eine Java-2D-Game-Engine der bayerischen Entwickler
[Steffen Wilke](https://github.com/steffen-wilke) und
[Matthias Wilke](https://github.com/nightm4re94). Neben der Sound-Engine kommen
viele Klassen zur Resourcen-Verwaltung, einige Hilfsklassen sowie das
Tweening-Paket aus der LITIENGINE in der Engine Pi zum Einsatz.

Diese README-Datei verwendet Dokumentationen, Tutorials und Bilder aus dem
[Engine Alpha Wiki](https://engine-alpha.org), die unter der
[Creative Commons „Namensnennung, Weitergabe unter gleichen Bedingungen“](https://creativecommons.org/licenses/by-sa/3.0/)
Lizenz stehen.

## Verwendung

Im Gegensatz zur Engine Alpha ist die
[Engine Pi](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)
über das wichtigste Repository für Java-Projekte das sogenannte
[Maven Central Repository](https://central.sonatype.com) abrufbar.

### BlueJ-Projekt

Auf [Github Releases](https://github.com/engine-pi/engine-pi/releases) gehen und die aktuelle Version `engine-pi-<version>.jar` herunterladen (z. B. [engine-pi-0.41.0.jar](https://github.com/engine-pi/engine-pi/releases/download/v0.41.0/engine-pi-0.41.0.jar)),
einen `+libs` Ordner erstellen und die JAR-Datei hineinkopieren.

### Maven-Projekt

https://github.com/engine-pi/maven-boilerplate

In der Projekt-Datei `pom.xml` ist die Engine Pi als
Abhängigkeit (`dependency`) hinterlegt.

```xml
<project>
  <dependencies>
    <dependency>
      <groupId>de.pirckheimer-gymnasium</groupId>
      <artifactId>engine-pi</artifactId>
      <version>0.41.0</version>
    </dependency>
  </dependencies>
</project>
```

## Koordinatensystem

Das Koordinatensystem ist mittig zentriert. Die x-Achse zeigt wie im
Mathematikunterricht nach rechts und die y-Achse nach oben. 1 Längeneinheit
entspricht 1 Meter. Die verwendete Physik-Engine rechnet intern mit Einheiten
aus der realen Welt, deshalb bietet sich Meter als Maßheit für das
Koordinatensystem an.[^engine-alpha-wiki:zeichnen-grafikfenster]

[^engine-alpha-wiki:zeichnen-grafikfenster]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster#Zeichnen_im_Grafikfenster

## Figur (Actor)

Eine Figur (engl. Actor) ist ein grafisches Objekt, das sich bewegt bzw.
das bewegt werden kann. In der Engine Pi gibt es eine Vielzahl
verschiedener Figurenarten (z. B.
[Image](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/Image.html),
[Text](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/Text),
[Rectangle](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/Rectangle),
[Circle](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/Circle)). Alle
diese Spezialisierungen sind abgeleitet von der Oberklasse
[Actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/Actor.html)
Die API-Dokumentation des Pakets
[pi.actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/package-summary.html)
listet alle verfügbaren Actor-Klassen auf.

Nachdem eine Figur erzeugt und zur Szene hinzugefügt wurde, befindet sie
sich an der Koordinate (0|0), d. h. die linke untere Ecke der Figur -
ihr Ankerpunkt - liegt an dem Punkt im Koordinatensystem, das 0 sowohl
für den x- als auch den y-Wert der Koordinate hat.

## Tutorial: Hello World[^engine-alpha-wiki:hello-world]

[^engine-alpha-wiki:hello-world]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Hello_World

<!-- ### Schritt 1: Grundlegender Aufbau -->

Das grundlegendste Hello World sieht so aus:
Das (noch wenig spannende) Ergebnis des Codes

Quellcode: [src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion1.java#L23-L41](https://github.com/engine-pi/engine-pi/blob/123719a158c4d268875630251b67fefe448a5b66/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion1.java#L23-L41)

```java
import pi.Game;
import pi.Scene;
import pi.actor.Text;

public class HelloWorldVersion1 extends Scene
{
    public HelloWorldVersion1()
    {
        Text helloWorld = new Text("Hello, World!", 2);
        helloWorld.setCenter(0, 1);
        add(helloWorld);
        Game.debug();
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
Game.debug();
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

Quellcode: [demos/helloworld/HelloWorldVersion2.java#L23-L55](https://github.com/engine-pi/engine-pi/blob/d46b39b8f2ea0cc1bcdaa63cbeefec6fe42d6de9/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/helloworld/HelloWorldVersion2.java#L23-L55)

```java
import pi.Game;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Rectangle;
import pi.actor.Text;

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

## Nutzereingaben[^engine-alpha-wiki:user-input]

[^engine-alpha-wiki:user-input]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input

### Tastatureingaben erstellen

![Der Counter im Gange](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/KeyStrokeCounter.png)

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
[KeyStrokeListener](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/KeyStrokeListener.html).
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
e)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/KeyStrokeListener.html#onKeyDown(java.awt.event.KeyEvent)>)
-Methode bei jedem Tastendruck aufgerufen.

Soll reagiert werden, wenn eine Taste losgelassen wird, kann die
[onKeyUp(KeyEvent
e)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/KeyStrokeListener.html#onKeyDown(java.awt.event.KeyEvent)>)-Methode
implementiert werden.

Alle Informationen über den Tastendruck sind im Objekt `keyEvent` der Klasse
[java.awt.event.KeyEvent](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html)
gespeichert. Die Engine nutzt hier dieselbe Schnittstelle wie Java.

Im [folgendem
Beispiel](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/keyboard/KeyEventDemo.java)
wird mit Hilfe der vier Cursor-Tasten ein kleines Rechteck bewegt:

![Das rote Rechteck bewegt sich mit WASD](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/KeyEventDemo.png)

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

### Mauseingaben erstellen[^engine-alpha-wiki:user-input-mouse-click-listener]

[^engine-alpha-wiki:user-input-mouse-click-listener]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input#MouseClickListener

![Auf Mausklick reagieren: Kreise malen](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/PaintingCirclesDemo.gif)

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
für die Arbeit mit der Engine.[^engine-alpha-wiki:vector]

[^engine-alpha-wiki:vector]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/User_Input#Vector

![Ein besseres Kreismalen: Auswählbare Größe und Farbe über ein kleines Menü](https://raw.githubusercontent.com/engine-pi/assets/main/docs/input/PaintingCirclesAdvancedDemo.gif)

Quellcode: [demos/input/mouse/PaintingCirclesAdvancedDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/input/mouse/PaintingCirclesAdvancedDemo.java)

### Voreingestellte Tastenkürzel und Steuerungsmöglichkeiten

Die Engine registriert im Auslieferungszustand einige wenige [grundlegenden
Maus- und
Tastatur-Steuermöglichkeiten](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/event/DefaultControl.html).

Diese sind hoffentlich beim Entwickeln hilfreich. Mit den statischen Methoden
[Game.removeDefaultControl()](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/Game.html#removeDefaultControl()>)
können diese Kürzel entfernt oder mit
[Game.setDefaultControl(DefaultControl)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/Game.html#setDefaultControl(pi.event.DefaultListener)>)
neue Kürzel gesetzt werden.

- `ESCAPE` zum Schließen des Fensters.
- `ALT + a` zum An- und Abschalten der Figuren-Zeichenroutine (Es
  werden nur die Umrisse gezeichnet, nicht die Füllung).
- `ALT + d` zum An- und Abschalten des Debug-Modus.
- `ALT + p` zum Ein- und Ausblenden der Figuren-Positionen (sehr
  ressourcenintensiv).
- `ALT + s` zum Speichern eines Bildschirmfotos (unter `~/engine-pi`).
- `ALT + PLUS` Hineinzoomen.
- `ALT + MINUS` Herauszoomen.
- `ALT + SHIFT + PLUS` schnelles Hineinzoomen.
- `ALT + SHIFT + MINUS` schnelles Herauszoomen.
- `ALT + Pfeiltasten` zum Bewegen der Kamera.
- `ALT + Mausrad` zum Einstellen des Zoomfaktors.

## Game Loop[^engine-alpha-wiki:game-loop]

[^engine-alpha-wiki:game-loop]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Game_Loop

<!-- ### How-To Engine Code: Der Game Loop -->

Das Snake-Spiel ist ein erstes interaktives Spiel. Es nutzt den Game Loop der
Engine. Dieser funktioniert folgendermaßen:

![Der Engine Pi Game Loop](https://raw.githubusercontent.com/engine-pi/assets/main/docs/GameLoop.png)

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

![Das Snake-Spiel: Der Kreis jagt die willkürlich auftauchenden Texte](https://raw.githubusercontent.com/engine-pi/assets/main/docs/Snake_Minimal.gif)

Quellcode: [demos/game_loop/SnakeMinimal.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/game_loop/SnakeMinimal.java)

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

Quellcode: [demos/game_loop/SnakeMinimal.java#L86-L89](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/game_loop/SnakeMinimal.java#L86-L89)

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

Quellcode: [demos/game_loop/SnakeMinimal.java#L92-L113](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/game_loop/SnakeMinimal.java#L92-L113)

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

Quellcode: [demos/game_loop/SnakeMinimal.java#L124-L130](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/game_loop/SnakeMinimal.java#L124-L130)

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

Quellcode: [demos/game_loop/SnakeMinimal.java#L64-L72](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/game_loop/SnakeMinimal.java#L64-L72)

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

![Eine Snake, die mit jedem Pickup wächst](https://raw.githubusercontent.com/engine-pi/assets/main/docs/Snake_Advanced.gif)

Quellcode: [demos/game_loop/SnakeAdvanced.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/game_loop/SnakeAdvanced.java)

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

## Szenen[^engine-alpha-wiki:scenes]

[^engine-alpha-wiki:scenes]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Scenes

Ein Spiel hat oftmals mehrere verschiedene „Teile“, zwischen denen der Spieler
während des Spielens wechselt. Zum Beispiel gibt es neben der Hauptdarstellung,
Pausenmenüs, Inventare, Hauptmenüs, etc. Es wäre unnötig komplex, für den
Wechsel zwischen diesen Szenen stets alle grafischen Objekte zu zerstören und
wieder neu aufzubauen. Stattdessen werden alle grafischen Objekte in einer
`Scene` hinzugefügt. Dies passiert - wie in den vorigen Tutorials - über die
Methode `add(...)`.

Über die Klasse `Game` kann schnell zwischen Szenen gewechselt werden. Dazu gibt
es die Methode `Game.transitionToScene(Scene)`.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/scenes/Scene_Demonstration.png)

<!-- #### Ein Pausenmenü -->

Das folgende Beispiel enthält zwei Szenen: Eine einfache Animation und ein
Pausenmenü. Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück

Quellcode: [demos/scenes/MainScene.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java)

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

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/scenes/Tutorial_Pause_Menu.gif)

<!-- ### Die zwei Szenen -->

Die Hauptszene ist `MainScene`. Hier könnte ein Game Loop für ein
Spiel stattfinden. Dieses Tutorial zeigt stattdessen eine kleine Animation.

Die zweite Szene heißt `PauseMenu`. In ihr gibt es eine Textbotschaft und einen
kleinen Knopf, um das Menü wieder zu verlassen.

Quellcode: [demos/scenes/MainScene.java#L36-L38](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java#L36-L38)

```java
public class MainScene extends Scene
{
    private Scene pauseMenu;
    //...
}
```

Quellcode: [demos/scenes/MainScene.java#L70-L72](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java#L70-L72)

```java
private class PauseMenu extends Scene
{
    private Scene mainScene;
    //...
}
```

Die Haupt-Szene wird per Knopfdruck pausiert. Wird der P-Knopf gedrückt, wird
die Transition ausgeführt:

Quellcode: [demos/scenes/MainScene.java#L65-L68](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java#L65-L68)

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

Quellcode: [demos/scenes/MainScene.java#L102-L108](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java#L102-L108)

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
rotiert ein oranges Rechteck wiederholend um den Punkt `(0|0)`. Eine volle
Rotation im Uhrzeigersinn dauert `8` Sekunden.

Quellcode: [demos/scenes/MainScene.java#L43-L49][https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java#L43-L49]

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

Quellcode: [demos/scenes/MainScene.java#L111-L121](https://github.com/engine-pi/engine-pi/blob/c1a0517e9940601ef0fb8fb06195c3c9444d1e12/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java#L111-L121)

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

### Schwerkraft

Quellcode: [demos/physics/single_aspects/GravityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/GravityDemo.java)

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

Quellcode: [demos/physics/single_aspects/ElasticityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/ElasticityDemo.java)

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

Quellcode: [demos/physics/single_aspects/DensityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/DensityDemo.java)

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

## Farben

Die Klassen mit Farbbezug:

- [resources.ColorContainer](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/resources/ColorContainer.html)
- [resources.ColorScheme](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/resources/ColorScheme.html)
- [resources.ColorSchemeSelection](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/resources/ColorSchemeSelection.html)
- [resources.NamedColor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/resources/NamedColor.html)
- [Resources.COLORS](https://javadoc.io/static/de.pirckheimer-gymnasium/engine-pi/0.31.0/de/pirckheimer_gymnasium/engine_pi/Resources.html#COLORS)

In der ersten Reihe sind mehrere Bilder zu sehen, in der
Reihe unterhalb Rechtecke mit der Durchschnittsfarbe der Bilder, in der letzten
Reihe die Komplementärfarben der entsprechenden Bilder.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/color-complementary/Images_derived_complementary-color.png)

Quellcode: [demos/actor/ImageAverageColorDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/actor/ImageAverageColorDemo.java)

```java
import pi.Game;
import pi.Scene;

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

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/color-complementary/Images_shapes.png)

`Alt + a` blendet die Figurenfüllungen aus. Es sind nur noch die Umrisse zu sehen.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/color-complementary/Shapes-only.png)

## Entwicklung

### Aufbau des Projekts

Die Engine Pi nutzt das Build Tool [maven](https://maven.apache.org/). Das
Projekt ist als sogenanntes [Multiple
Modules](https://maven.apache.org/guides/mini/guide-multiple-modules.html)-Projekt
strukturiert.
Die eigentliche Engine befindet sich im Ordner `./modules/engine`.

| Pfad                          | artefactId              |
| ----------------------------- | ----------------------- |
| ./                            | engine-pi-meta          |
| ./modules/engine              | engine-pi               |
| ./modules/demos               | engine-pi-demos         |
| ./modules/cli                 | engine-pi-cli           |
| ./modules/games/blockly-robot | engine-pi-blockly-robot |
| ./modules/games/pacman        | engine-pi-pacman        |
| ./modules/games/tetris        | engine-pi-tetris        |
| ./modules/build-tools         | engine-pi-build-tools   |

### Eine neue Version veröffentlichen

Es wird Semantic Versioning verwendet.

Die Versionsnummer der Engine setzen.

```
mvn versions:set -f modules/engine/pom.xml
```

Die Versionsnummer des Meta-Projekts setzen.

```
mvn versions:set
```

Die Datei `CHANGELOG.md` bezieht sich auf die Engine also auf `./modules/engine`.

### javadoc

Im Module `build-tools` befindet sich eine CSS-Datei, die verwendet wird, um das
Aussehen der Dokumentation mit den Engine-Pi-Farbe (Gnome-Farben) und der
Engine-Pi-Standard-Schrift (Cantarell) anzupassen.

#### Eigene CSS-Klassen

`.development-note`

#### Abfolge der Tags

Wir folgenden der Empfehlung, die im Artikel [How to Write Doc Comments for the
Javadoc
Tool](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#orderoftags)
von [Oracle](https://www.oracle.com) dargestellt wird.

1. `@author` (classes and interfaces only, required)
1. `@version` (classes and interfaces only, required. See footnote 1)
1. `@param` (methods and constructors only)
1. `@return` (methods only)
1. `@throws` (a synonym added in Javadoc 1.2 `@exceptions`)
1. `@see`
1. `@since`
1. `@deprecated` (see How and When To Deprecate APIs)

### Code Beispiele

Einzeilig

```java
/**
 * {@code System.out.println("Lorem ipsum");}
 */
```

Mehrzeilig:

```java
/**
 * <pre>
 * {@code
 * Set<String> s;
 * System.out.println(s);
 * }
 * </pre>
 */
```

### File Link zu den Demos:

```java
// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java
```

### Deutsche Übersetzungen von englischen Klassennamen

| englisch                         | deutsch                                                |
| -------------------------------- | ------------------------------------------------------ |
| AABB (axis-aligned bounding box) | achsenparallelen Begrenzungsrahmen                     |
| Actor                            | Figur                                                  |
| BodyType                         | Verhalten einer Figur in der physikalischen Simulation |
| Bounds                           | Schranken, Abgrenzung                                  |
| DistanceJoint                    | Stabverbindung                                         |
| Fixture                          | Halterung, Kollisionsform                              |
| Frame                            | Einzelbild                                             |
| Handler                          | Steuerungsklasse                                       |
| Joint                            | Verbindung                                             |
| Layer                            | Ebene                                                  |
| Listener                         | Beobachter                                             |
| Loop                             | Schleife                                               |
| Offset                           | Verzug                                                 |
| PrismaticJoint                   | Federverbindung                                        |
| Rectangle                        | Rechteck                                               |
| RevoluteJoint                    | Gelenkverbindung                                       |
| Rigid Body                       | Starrer Körper                                         |
| RopeJoint                        | Seilverbindung                                         |
| Scene                            | Szene                                                  |
| Shape                            | Umriss                                                 |
| Square                           | Quadrat                                                |
| TurboFire                        | Dauerfeuer                                             |
| WeldJoint                        | Schweißnaht                                            |

### Kommentar mit Lizenzhinweis

```java
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
```

### Anordnung der Konstruktoren

Oben stehen die Konstruktoren mit wenigen Parametern unten die mit vielen.
