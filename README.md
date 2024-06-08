<!-- https://github.com/softwaremill/maven-badges -->

[![Maven Central](https://img.shields.io/maven-central/v/rocks.friedrich.engine_omega/engine-omega.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/rocks.friedrich.engine_omega/engine-omega)
[![javadoc](https://javadoc.io/badge2/rocks.friedrich.engine_omega/engine-omega/javadoc.svg)](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega)

# Über diese Game Engine

![](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/src/main/resources/assets/logo.png)

Diese Game Engine ist ein Fork der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) von
[Michael Andonie](https://github.com/andonie)
und [Niklas Keller](https://github.com/kelunik) und zwar ein Fork der Core Engine v4.x.
Die Engine-Alpha-Edu Version mit deutschen Java Bezeichnern wurde nicht geforkt.

Da die [Engine-Alpha](https://github.com/engine-alpha/engine-alpha) keine
Audio-Wiedergabe unterstützt, wurde Code der Sound-Engine der
[LITIENGINE](https://github.com/gurkenlabs/litiengine) in die Engine-Alpha übernommen.
Die LITIENGINE ist eine Java-2D-Game-Engine der bayerischen Entwickler
[Steffen Wilke](https://github.com/steffen-wilke)
[Matthias Wilke](https://github.com/nightm4re94). Neben der Sound-Engine kommen
viele Klassen zur Resourcen-Verwaltung, einige Hilfsklassen sowie das
Tweening-Paket aus der LITIENGINE in der Engine Omega zum Einsatz.

Diese README-Datei verwendet Dokumentationen, Tutorials und Bilder aus dem
[Engine Alpha Wiki](https://engine-alpha.org), die unter der
[Creative Commons „Namensnennung, Weitergabe unter gleichen Bedingungen“](https://creativecommons.org/licenses/by-sa/3.0/)
Lizenz stehen.

## Koordinatensystem

Das Koordinatensystem ist mittig zentriert. Die x-Achse zeigt wie im
Mathematikunterricht nach rechts und die y-Achse nach oben.
1 Längeneinheit entspricht 1 Meter. Die verwendete Physik-Engine rechnet
intern mit Einheiten aus der realen Welt, deshalb bietet sich Meter als Maßheit für das Koordinatensystem an.
[^zeichnen-grafikfenster]

[^zeichnen-grafikfenster]: https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster#Zeichnen_im_Grafikfenster

## Hello World

https://engine-alpha.org/wiki/v4.x/Hello_World

### Schritt 1: Grundlegender Aufbau

Das grundlegendste Hello World sieht so aus:
Das (noch wenig spannende) Ergebnis des Codes

```java
import ea.Scene;
import ea.Game;

import ea.actor.Text;

public class HelloWorld_v1
        extends Scene{

    public HelloWorld_v1() {
        Text helloworld = new Text("Hello World", 2);
        helloworld.setCenter(0,1);
        this.add(helloworld);
        //Game.setDebug(true);
    }

    public static void main(String[] args) {
        Scene helloWorld = new HelloWorld_v1();
        Game.start(400, 300, helloWorld);
    }
}
```

#### Scene

Die Hello World-Klasse leitet sich aus der classe ea.Scene der Engine ab. Szenen in der Engine sind eigenständige Spielbereiche. Jede Scene hat ihre eigenen grafischen (und sonstige) Objekte; Scenes werden unabhängig voneinander berechnet. Mehr dazu erfährst du im Szenen-Tutorial. Für den Moment ist relevant: Ein Spiel besteht aus einer oder mehreren Szenen und wir erstellen eine Szene, in der "Hello World" dargestellt werden soll:

```java
public class HelloWorld_v1
        extends Scene
```

#### Text

Wir wollen den Text "Hello World" darstellen. Die Klasse ea.actor.Text ist dafür zuständig.

Ein Text mit Inhalt "Hello World" und Höhe 2 wird erstellt:

```java
Text helloworld = new Text("Hello World", 2);
```

Der Text wird an Position (0|1) zentriert:

```java
helloworld.setCenter(0,1);
```

Der Text wird an der Szene angemeldet:

```java
this.add(helloworld);
```

Der letzte Schritt ist nötig, damit das Objekt auch sichtbar wird. In jeder Szene werden nur die Objekte auch gerendert, die auch an der Szene angemeldet sind.

#### Debug Mode

Der Debug-Modus zeigt das Koordinatensystem und weitere hilfreiche Infos.

Um Überblick zu behalten und die Grafikebene zu verstehen, ist der Debug-Modus der Engine hilfreich. Die auskommentierte Zeile aktiviert den Debug Modus:

```java
Game.setDebug(true);
```

Die Klasse ea.Game enthält neben Debug-Modus weitere Features, die die Spielumgebung global betreffen. Du erfährst mehr dazu im Tutorial zur Spielsteuerung.
Das Spiel starten

Die Klasse ea.Game kontrolliert auch den Spielstart. Dazu muss lediglich die (zuerst) darzustellende Szene angegeben werden, sowie die Fenstermaße (in diesem Fall 400 px Breite und 300 px Höhe):

```java
Scene helloWorld = new HelloWorld_v1();
Game.start(400, 300, helloWorld);
```

### Schritt 2: Geometrie und Farbe

Im nächsten Schritt hübschen wir die Szene ein wenig auf. Dazu arbeiten wir mit geometrischen Figuren und Farbe.
Jetzt mit mehr Farbe und geometrischen Figuren

```java
import ea.Scene;
import ea.Game;

import ea.actor.Circle;
import ea.actor.Rectangle;
import ea.actor.Text;

import java.awt.Color;

public class HelloWorld_v2
        extends Scene{

    public HelloWorld_v2() {
        Text helloworld = new Text("Hello World", 2);
        helloworld.setCenter(0,1);
        this.add(helloworld);
        //Game.setDebug(true);

        helloworld.setColor(Color.BLACK);

        Rectangle background = new Rectangle(10, 3);
        background.setColor(Color.PINK);
        background.setCenter(0, 1);
        background.setLayerPosition(-1);

        Circle circle = new Circle(5);
        circle.setColor(Color.GRAY);
        circle.setCenter(0, 1);
        circle.setLayerPosition(-2);

        this.add(background, circle);
    }

    public static void main(String[] args) {
        Scene helloWorld = new HelloWorld_v2();
        Game.start(400, 300, helloWorld);
    }
}
```

#### Geometrische Figuren

Die Engine unterstützt diverse geometrische Figuren. Dazu gehören Rechtecke und Kreise. Der Code erstellt ein Rechteck mit Breite 10 und Höhe 3 sowie einen Kreis mit Durchmesser 5.

```java
Rectangle background = new Rectangle(10, 3);
Circle circle = new Circle(5);
```

#### Farbe

Einige Objekte in der Engine können beliebig gefärbt werden. Text und geometrische Figuren gehören dazu. Mit setColor kann die Farbe als AWT-Color Objekt übergeben werden:

```java
background.setColor(Color.PINK);
circle.setColor(Color.GRAY);
```

#### Layer Position

So würde das Bild aussehen, wenn die Layer-Position nicht explizit gesetzt werden würde.

Wir wollen explizit, dass der Text vor allen anderen Objekten dargestellt wird. Außerdem soll der Kreis noch hinter dem Rechteck sein. Um das sicherzustellen, kann die Layer-Position explizit angegeben werden: Je höher die Layer-Position, desto weiter im Vordergrund ist das Objekt.

```java
background.setLayerPosition(-1);
circle.setLayerPosition(-2);
```

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

Soll reagiert werden, wenn eine Taste losgelassen wird, kann die
[onKeyUp(KeyEvent
e)](<https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/KeyListener.html#onKeyDown(java.awt.event.KeyEvent)>)-Methode
implementiert werden.

Alle Informationen über den Tastendruck sind im Objekt `keyEvent` der Klasse
[java.awt.event.KeyEvent](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html)
gespeichert. Die Engine nutzt hier dieselbe Schnittstelle wie Java.

Im [folgendem
Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/keyboard/KeyEventDemo.java)
wird mit Hilfe der vier Cursor-Tasten ein kleines Rechteck bewegt:

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

Java ordnet jeder Taste eine Ganzzahl, einen sogenannten `KeyCode`, zu. Mit der
Methode
[KeyEvent#getKeyCode()](<https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/event/KeyEvent.html#getKeyCode()>)
kann dieser Code abgefragt werden. Außerdem stellt die Klasse `KeyEvent` eine
Vielzahl von statischen Attributen bzw. Klassenattributen bereit, dessen Name
`VK_` vorangestellt ist. `VK` steht dabei für [`Virtual
Key`](https://stackoverflow.com/a/70191567). Diese Klassenattribute können in
einer `switch`-Kontrollstruktur zur Fallunterscheidung verwendet werden.

Das [nächste
Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/keyboard/KeyEventDisplayDemo.java)
zeigt den entsprechenden Namen des `VK`-Klassenattributs an, nachdem eine Taste
gedrückt wurde. Wird zum Beispiel die Leertaste gedrückt, erscheint der Text
`VK_SPACE`.

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

https://engine-alpha.org/wiki/v4.x/User_Input#MouseClickListener

### Mauseingaben

Auf Mausklick reagieren: Kreise malen

Das [folgende
Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/mouse/PaintingCirclesDemo.java)
malt bei jedem Knopfdruck einen Kreis.[^mausklick-kreise-malen]

[^mausklick-kreise-malen]: https://engine-alpha.org/wiki/v4.x/User_Input#Auf_Mausklick_reagieren:_Kreise_malen

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

#### Schnittstelle `MouseClickListener`

Das Interface
[MouseClickListener](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/MouseClickListener.html)
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

#### `Vector`

Statt zwei `double`-Parametern für die X/Y-Koordinaten des Klicks, nutzt die
Engine hier die interne Klasse
[Vector](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/Vector.html).
Die Klasse `Vector` wird in der Engine durchgehend verwendet und ist essentiell
für die Arbeit mit der Engine.

https://engine-alpha.org/wiki/v4.x/User_Input#Vector

Ein besseres Kreismalen: Auswählbare Größe und Farbe über ein kleines Menü:

[PaintingCirclesAdvancedDemo.java](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/mouse/PaintingCirclesAdvancedDemo.java)

## Game Loop

https://engine-alpha.org/wiki/v4.x/Game_Loop

### How-To Engine Code: Der Game Loop

Das Snake-Spiel ist ein erstes interaktives Spiel. Es nutzt den Game Loop der
Engine. Dieser funktioniert folgendermaßen:

![Der Engine Alpha Game Loop](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/misc/images/GameLoop.png)

Ein Film besteht aus 24 bis 60 Bildern pro Sekunde, die schnell hintereinander
abgespielt werden, um die Illusion von Bewegung zu erzeugen. Ähnlich werden bei
(den meisten) Computerspielen 30 bis 60 Bilder pro Sekunde in Echtzeit
gerendert, um die selbe Illusion von Bewegung zu erzeugen. Nach jedem Bild
berechnet die Engine intern die nächsten Schritte und gibt die relevanten
Ereignisse (Tastendruck, Kollision, Frame-Update) an die entsprechenden Listener
weiter.

Alle Spiel-Logik ist also in den Listener-Interfaces geschrieben. Guter
Engine-Code ist verpackt in Interfaces nach Spiel-Logik.

### Snake ohne Körper

Das folgende Program implementiert ein einfaches Snake-Spiel mit einem
Steuerbaren Kreis und dem Ziel, Goodies zu sammeln.

![Das Snake-Spiel: Der Kreis jagt die willkürlich auftauchenden Texte](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/misc/images/Snake_Minimal.gif)

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
            implements FrameUpdateListener, KeyListener
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

### Snake: Frame-Weise Bewegung

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

### Bewegungsgeschwindigkeit festlegen

Was die tatsächliche Bewegungsgeschwindigkeit der Snake ist, hängt davon ab,
welche Taste der Nutzer zuletzt runtergedrückt hat und ist in der Snake über
`KeyListener` gelöst wie im vorigen Tutorial:

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

### Auf Kollisionen reagieren: Goodies

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

### Anregung zum Experimentieren

![Eine Snake, die mit jedem Pickup wächst](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/misc/images/Snake_Advanced.gif)

- Deadly Pickups: Es gibt noch keine Gefahr für die Schlange. Ein giftiges
  Pick-Up tötet die Schlange und beendet das Spiel (oder zieht der Schlange
  einen von mehreren Hit Points ab).
- Smoother Movement: Die aktuelle Implementierung für die Bewegung der Schlange
  ist sehr steif und die Schlange kann nicht stehen bleiben. Vielleicht möchtest
  du dem Spieler mehr Kontrolle über die Schlange geben: Statt des
  KeyListener-Interfaces, kann die Schlange in ihrer
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

### Scenes in der Engine

Die bisherigen Beispiele waren simplistisch. Ein echtes Spiel hat mehrere
verschiedene "Teile", zwischen denen der Spieler während des Spielens wechselt.
Zum Beispiel gibt es neben der Hauptdarstellung: Pausenmenüs, Level-Selections,
Inventare, Hauptmenüs, etc. Es wäre unnötig komplex, für den Wechsel zwischen
diesen Szenen stets alle grafischen Objekte zu zerstören und wieder neu
aufzubauen. Stattdessen werden alle grafischen Objekte in einer ea.Scene
hinzugefügt. Dies passiert - wie in den vorigen Tutorials - über die Methode
add(...).

Über die Klasse Game kann schnell zwischen Szenen gewechselt werden. Dazu gibt
es die Methode Game.transitionToScene(Scene).

Szenen in der Engine: Beispiel mit Pausenmenü

#### Ein Pausenmenü

Das folgende Beispiel enthält zwei Szenen: Eine einfache Animation und ein
Pausenmenü. Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück

```java
import ea.*;
import ea.actor.Rectangle;
import ea.actor.Text;
import ea.animation.CircleAnimation;
import ea.event.KeyListener;
import ea.event.MouseButton;
import ea.event.MouseClickListener;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class MainScene
extends Scene
implements KeyListener {

    private PauseMenu pauseMenu;

    public MainScene() {
        pauseMenu = new PauseMenu(this);

        Rectangle toAnimate = new Rectangle(5, 2);
        toAnimate.setCenter(0, -5);
        toAnimate.setColor(Color.ORANGE);

        CircleAnimation animation = new CircleAnimation(toAnimate, new Vector(0,0), 8, true, true);
        addFrameUpdateListener(animation);

        add(toAnimate);
        addKeyListener(this);

        Text info = new Text("Pause mit P", 0.5f);
        info.setCenter(-7, -5);
        add(info);
    }


    public static void main(String[] args) {
        Game.start(600, 400, new MainScene());
        //Game.setDebug(true);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_P) {
            gotoPause();
        }
    }

    private void gotoPause() {
        Game.transitionToScene(pauseMenu);
    }

    private class PauseMenu
    extends Scene {

        private Scene mainScene;

        public PauseMenu(Scene mainScene) {
            this.mainScene = mainScene;

            MenuItem back = new MenuItem(new Vector(0,-5), "Zurück");
            add(back, back.label);

            Text headline = new Text("Mach mal Pause.", 2.5f);
            headline.setCenter(0, 3);
            add(headline);

        }

        private class MenuItem
        extends Rectangle
        implements MouseClickListener, FrameUpdateListener{

            private Text label;

            public MenuItem(Vector center, String labelText) {
                super(10, 1.5f);

                label = new Text(labelText, 1);
                label.setLayerPosition(1);
                label.setColor(Color.BLACK);
                label.setCenter(center);

                setLayerPosition(0);
                setColor(Color.cyan);
                setCenter(center);
            }

            @Override
            public void onMouseDown(Vector clickLoc, MouseButton mouseButton) {
                if(contains(clickLoc)) {
                    Game.transitionToScene(mainScene);
                }
            }

            @Override
            public void onFrameUpdate(float v) {
                if(contains(Game.getMousePositionInCurrentScene())) {
                    this.setColor(Color.MAGENTA);
                } else {
                    this.setColor(Color.CYAN);
                }
            }
        }
    }
}
```

### Die zwei Szenen

Die Hauptszene ist MainScene. Hierdrin könnte ein normaler Game Loop für ein
Spiel stattfinden. Für dieses Tutorial ist in der Hauptszene stattdessen nur
eine stumpfe Animation.

Die zweite Szene ist PauseMenu. In ihr gibt es eine Textbotschaft und einen
kleinen Button, um das Menü wieder zu verlassen.

```java
public class MainScene
extends Scene {
    private Scene pauseMenu;
    //...
}

private class PauseMenu
extends Scene {
    private Scene mainScene;
    //...
}
```

Die Haupt-Szene wird per Knopfdruck pausiert. Wird der P-Knopf gedrückt, wird
die Transition ausgeführt:

```java
private void gotoPause() {
    Game.transitionToScene(pauseMenu);
}
```

Das Pausenmenü wird statt mit Tastatur per Mausklick geschlossen. Im internen
Steuerelement MenuItem wird dafür die entsprechende Methode aufgerufen, wann
immer ein Mausklick auf dem Element landet - dies wird durch die Methode
contains(Vector) geprüft:

```java
@Override
public void onMouseDown(Vector clickLoc, MouseButton mouseButton) {
    if(contains(clickLoc)) {
        Game.transitionToScene(mainScene);
    }
}
```

### Kosmetische Kleinigkeiten

Damit es zumindest irgendetwas zu sehen gibt in den zwei kahlen Szenen, hat die
Hauptszene eine Interpolierte Rotationsanimation. Diese rotiert ein oranges
Rechteck wiederholend um den Punkt (0|0). Eine volle Rotation im Uhrzeigersinn
dauert 8 Sekunden.

```java
Rectangle toAnimate = new Rectangle(5, 2);
toAnimate.setCenter(0, -5);
toAnimate.setColor(Color.ORANGE);

CircleAnimation animation = new CircleAnimation(toAnimate, new Vector(0,0), 8, true, true);
addFrameUpdateListener(animation);

add(toAnimate);
```

Das Pausenmenü hat einen Hover-Effekt. Hierzu wird einfach jeden Frame
überprüft, ob die Maus derzeit innerhalb des Steuerelementes liegt und abhängig
davon die Rechtecksfarbe ändert. Hierzu wird die Methode
ea.Game.getMousePositionInCurrentScene() genutzt:

```java
@Override
public void onFrameUpdate(float v) {
    if(contains(Game.getMousePositionInCurrentScene())) {
        this.setColor(Color.MAGENTA);
    } else {
        this.setColor(Color.CYAN);
    }
}
```

### Anmerkungen und Beobachtungen

- Die Kreisrotation in der Hauptszene geht nicht weiter, solange das Pausenmenü
  die aktive Szene ist. Dies liegt daran, dass die Animation als
  FrameUpdateListener in der Hauptszene angemeldet wurde
  (addFrameUpdateListener(animation)). Alle Listener einer Szene können nur dann
  aufgerufen werden, wenn die Szene aktiv ist.
- Deshalb lässt sich das Pausenmenü nicht durch drücken von P beenden. Der
  KeyListener, der bei Druck von P zum Pausenmenü wechselt, ist in der
  Hauptszene angemeldet.

## Physics

### Schwerkraft

https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/physics/single_aspects/GravityDemo.java

```java
public class GravityDemo extends Scene implements KeyListener
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
        rectangle.setBodyType(BodyType.DYNAMIC);
        add(rectangle);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 1)
                : new Rectangle(1, 10);
        rectangle.setPosition(x, y);
        rectangle.setBodyType(BodyType.STATIC);
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

[Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/physics/single_aspects/ElasticityDemo.java)

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

https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/physics/single_aspects/DensityDemo.java

```java
public class DensityDemo extends Scene implements KeyListener
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

https://engine-alpha.org/wiki/v4.x/Physics

Physik in der Engine

Seit Version 4.0 nutzt Engine Alpha eine Java-Version von Box2D. Diese mächtige
und effiziente Physics-Engine ist in der Engine leicht zu bedienen und
ermöglicht es, mit wenig Aufwand mechanische Phänomene in Deine Spiele zu bringen:
von Platforming und Billiard bis zu Hängebrücken und Autos.

Die Physics Engine basiert auf den Prinzipien der Klassischen Mechanik. Ein
Grundverständnis hierüber ist nötig: Begriffe wie Masse, Dichte, Impuls und
Kraft sollten dir zumindest grob geläufig sein, um diese auf deine Spielobjekte
anzuwenden.

### Beispiel 1: Dominos

Um die Grundlagen der Engine Alpha Physics zu testen, bauen wir eine einfache
Kettenreaktion: Ein Ball wird gegen eine Reihe von Dominos geworfen.

### Setup ohne Physics

Bevor wir die Physik einschalten, bauen wir das Spielfeld mit allen Objekten auf:

https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/physics/DominoesDemo.java

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

### Die Body Types

Wir erwarten verschiedenes Verhalten von den physikalischen Objekten. Dies
drückt sich in verschiedenen Body Types aus:

- Der Ball und die Dominos sollen sich verhalten wie normale physische Objekte:
  Der Ball prallt an den Dominos ab und die Steine fallen um. Diese Actors haben
  einen dynamischen Körper.
- Aber der Boden und die Wand sollen nicht wie die Dominos umfallen. Egal mit
  wie viel Kraft ich den Ball gegen die Wand werfe, sie wird niemals nachgeben.
  Diese Actors haben einen statischen Körper.

Mit der Methode `Actor.setBodyType(BodyType)` wird das grundlegende Verhalten
eines Actors bestimmt. Zusätzlich wird mit Scene.setGracity(Vector) eine
Schwerkraft gesetzt, die auf den Ball und die Dominos wirkt.
Jetzt wirkt Schwerkraft auf die dynamischen Objekte und der statische Boden
hält den Fall

In einer `setupPhysics()`-Methode werden die Body Types für die Actors gesetzt und
die Schwerkraft (standardmäßige 9,81 m/s^2, gerade nach unten) aktiviert:

```java
    private void setupPhysics()
    {
        ground.makeStatic();
        wall.makeDynamic();
        ball.makeDynamic();
        setGravityOfEarth();
    }
```

Zusätzlich werden die Dominos in `makeDominoes()` mit `domino.makeDynamic();` eingerichtet.

Dynamische und statische Körper sind die essentiellsten Body Types in der
Engine, allerdings nicht die einzigen. Du findest einen Umriss aller Body Types
in der Dokumentation von BodyType und eine vergleichende Übersicht in der
dedizierten Wikiseite Den Ball Werfen Mit einem Methodenaufruf fliegt der Ball

Zeit, die Dominos umzuschmeißen! Die Methode
[https://docs.engine-alpha.org/4.x/ea/actor/Actor.html#applyImpulse-ea.Vector-
Actor.applyImpulse(Vector) erlaubt dir, den Ball physikalisch korrekt zu
'werfen'.

Mit der Zeile ball.applyImpulse(new Vector(15, 12)); kannst du den ersten
Ballwurf testen.

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

Wir wollen, dass das Rechteck stets Ball und Maus verbindet. Die einfachste
Methode hierzu ist, in jedem Frame das Rechteck erneut an die Maus anzupassen.
Dafür implementiert die Dominoes-Klasse das Interface FrameUpdateListener und
berechnet frameweise anhand der aktuellen Mausposition die korrekte Länge und
den korrekten Winkel, um die visuelle Hilfe richtig zu positionieren:

```java
    @Override
    public void onFrameUpdate(double deltaSeconds)
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
Interface MouseClickListener implementiert:

```java
    @Override
    public void onMouseDown(Vector position, MouseButton button)
    {
        Vector impulse = ball.getCenter().getDistance(position).multiply(5);
        ball.applyImpulse(impulse);
    }
```

### Anregung zum Experimentieren

- Von Dominos zu Kartenhaus: Mehrere Schichten von Dominos, mit quer gelegten
  Steinen als Fundament zwischen den Schichten, sorgen für mehr Spaß bei der
  Zerstörung.

- Reset Button: Ein Knopfdruck setzt den Ball auf seine Ursprüngliche Position
  (und Geschwindigkeit) zurück; dabei werden all Dominos wieder neu aufgesetz.

## Zeitsteuerung

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

## Kollisionen-Erkennung

https://engine-alpha.org/wiki/v4.x/Collision

### Spielkonzept und grundlegender Aufbau

Ein Frosch soll fröhlich durch das Spiel springen, wann immer er die Chance hat,
sich vom Boden abzustoßen. In der Scene `FroggyJump` kann der Spieler ein
Objekt der Klasse `Frog` steuern. Zusätzlich geben Objekte der Klasse
`Platform` halt.

[Quellcode des fertigen Spiels](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/collision/FroggyJump.java)

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
    public void onFrameUpdate(double deltaSeconds)
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
        setBodyType(BodyType.STATIC);
    }
}
```

Der Frosch kann sich bewegen, knallt aber unangenehmerweise noch gegen die Decke

![](https://raw.githubusercontent.com/Josef-Friedrich/engine-omega/fork/misc/images/FrogTutorial1.gif)

Ein paar Erklärungen zum Codegerüst für `FroggyJump`:

### Physikalische Eigenschaften

Wie im Physics-Tutorial beschrieben, werden die physikalischen Eigenschaften der
Spielobjekte und ihrer Umgebung bestimmt:

- Platformen sind **statische** Objekte: Sie ignorieren Schwerkraft und können
  nicht durch andere Objekte verschoben werden (egal mit wie viel Kraft der
  Frosch auf sie fällt).
- Der Frosch ist ein **dynamisches** Objekt: Er lässt sich von der Schwerkraft
  beeinflussen und wird von den statischen Platformen aufgehalten.
- In der Scene `FroggyJump` existiert eine Schwerkraft von 10 m/s^2. Sie wird
  mit `setGravity(Vector)` gesetzt.

### Bewegung des Frosches

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

### Durch Platformen Springen: Kollisionen kontrollieren

Das Interface
[CollisionListener](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/CollisionEvent.html) wurde bereits in seiner grundlegenden Form im Nutzereingabe-Tutorial benutzt.

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

## Deutsche Übersetzungen von englischen Klassennamen

| englisch       | deutsch                   |
| -------------- | ------------------------- |
| Bounds         | Schranken, Abgrenzung     |
| DistanceJoint  | Stabverbindung            |
| Fixture        | Halterung, Kollisionsform |
| Handler        | Steuerungsklasse          |
| Joint          | Verbindung                |
| Listener       | Beobachter                |
| Offset         | Verzug                    |
| PrismaticJoint | Federverbindung           |
| RevoluteJoint  | Gelenkverbindung          |
| RobeJoint      | Seilverbindung            |
| WeldJoint      | Schweißnaht               |
| Frame          | Einzelbild                |
