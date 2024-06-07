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

https://engine-alpha.org/wiki/v4.x/User_Input#MouseClickListener

### Mauseingaben

Auf Mausklick reagieren: Kreise malen

Das [folgende Beispiel](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/mouse/PaintingCirclesDemo.java) malt bei jedem Knopfdruck einen Kreis.[^mausklick-kreise-malen]

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

Das Interface [MouseClickListener](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/MouseClickListener.html) ermöglicht das Reagieren auf Mausklicks des Nutzers. Ebenso ermöglicht es das Reagieren auf Loslassen der Maus.

Bei einem Mausklick (egal ob linke, rechte, oder sonstige Maustaste) wird ein Kreis an der Position des Klicks erstellt:

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
[Vector](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/Vector.html). Die Klasse `Vector` wird in der Engine durchgehend verwendet und ist essentiell für die Arbeit mit der Engine.

https://engine-alpha.org/wiki/v4.x/User_Input#Vector

Ein besseres Kreismalen: Auswählbare Größe und Farbe über ein kleines Menü:

[PaintingCirclesAdvancedDemo.java](https://github.com/Josef-Friedrich/engine-omega/blob/fork/src/test/java/rocks/friedrich/engine_omega/demos/input/mouse/PaintingCirclesAdvancedDemo.java)

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

Die Physics Engine basiert auf den Prinzipien der Klassischen Mechanik. Ein Grundverständnis hierüber ist nötig: Begriffe wie Masse, Dichte, Impuls und Kraft sollten dir zumindest grob geläufig sein, um diese auf deine Spielobjekte anzuwenden.

### Beispiel 1: Dominos

Um die Grundlagen der Engine Alpha Physics zu testen, bauen wir eine einfache Kettenreaktion: Ein Ball wird gegen eine Reihe von Dominos geworfen.

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

Dynamische und statische Körper sind die essentiellsten Body Types in der Engine, allerdings nicht die einzigen. Du findest einen Umriss aller Body Types in der Dokumentation von BodyType und eine vergleichende Übersicht in der dedizierten Wikiseite
Den Ball Werfen
Mit einem Methodenaufruf fliegt der Ball

Zeit, die Dominos umzuschmeißen! Die Methode [https://docs.engine-alpha.org/4.x/ea/actor/Actor.html#applyImpulse-ea.Vector- Actor.applyImpulse(Vector) erlaubt dir, den Ball physikalisch korrekt zu 'werfen'.

Mit der Zeile ball.applyImpulse(new Vector(15, 12)); kannst du den ersten Ballwurf testen.

Um hieraus eine Spielmechanik zu bauen, soll der Spieler Richtung und Stärke des Wurfes mit der Maus kontrollieren können: Per Mausklick wird der Ball in Richtung des Mauscursors katapultiert.
Das Angle-Objekt hilft dem Spieler

Hierzu wird ein weiteres Rechteck angle eingeführt, das die Richtung des Impulses markiert:

```java
    private void setupAngle()
    {
        angle = new Rectangle(1, 0.1);
        angle.setColor(Color.GREEN);
        add(angle);
    }
```

Wir wollen, dass das Rechteck stets Ball und Maus verbindet. Die einfachste Methode hierzu ist, in jedem Frame das Rechteck erneut an die Maus anzupassen. Dafür implementiert die Dominoes-Klasse das Interface FrameUpdateListener und berechnet frameweise anhand der aktuellen Mausposition die korrekte Länge und den korrekten Winkel, um die visuelle Hilfe richtig zu positionieren:

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

    Von Dominos zu Kartenhaus: Mehrere Schichten von Dominos, mit quer gelegten Steinen als Fundament zwischen den Schichten, sorgen für mehr Spaß bei der Zerstörung.

    Reset Button: Ein Knopfdruck setzt den Ball auf seine Ursprüngliche Position (und Geschwindigkeit) zurück; dabei werden all Dominos wieder neu aufgesetz.

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

Physikalische Eigenschaften

Wie im Physics-Tutorial beschrieben, werden die physikalischen Eigenschaften der Spielobjekte und ihrer Umgebung bestimmt:

    Platformen sind statische Objekte: Sie ignorieren Schwerkraft und können nicht durch andere Objekte verschoben werden (egal mit wie viel Kraft der Frosch auf sie fällt).
    Der Frosch ist ein dynamisches Objekt: Er lässt sich von der Schwerkraft beeinflussen und wird von den statischen Platformen aufgehalten.
    In der Scene FroggyJump existiert eine Schwerkraft von 10 m/s^2. Sie wird mit setGravity(Vector) gesetzt.

Bewegung des Frosches

Die Bewegung des Frosches wird in jedem Frame kontrolliert. Wie im Game Loop Tutorial beschrieben, wird hierzu das Interface FrameUpdateListener genutzt.

In jedem Frame wird die Bewegung des Frosches in dreierlei hinsicht kontrolliert:

    Teil A: Blickrichtung des Frosches: Das Bild des Frosches wird gespiegelt, falls er sich nach links bewegt.
    Teil B: Horizontale Bewegung des Frosches: Jeden Frame, in dem der Spieler den Frosch (per Tastendruck) nach links oder rechts steuern möchte, wird eine Bewegungskraft auf den Frosch angewendet. Wird der Frosch in die Gegenrichtung seiner aktuellen Bewegung gesteuert, wird seine horizontale Geschwindigkeit zuvor auf 0 gesetzt, um ein langsames Abbremsen zu verhindern. Das ermöglicht schnelle Reaktion auf Nutzereingabe und ein besseres Spielgefühl. Zusätzlich wird seine Geschwindigkeit auf die Konstante MAX_SPEED begrenzt.
    Teil C: Springe, wenn möglich: Mit der Funktion isGrounded() bietet die Engine einen einfachen Test, um sicherzustellen, dass der Frosch Boden unter den Füßen hat. Wenn dies gegeben ist, wird ein Sprungimpuls auf den Frosch angewandt. Zuvor wird die vertikale Komponente seiner Geschwindigkeit auf 0 festgesetzt - das garantiert, dass der Frosch jedes mal die selbe Sprunghöhe erreicht.

Die Kamera folgt dem Frosch

Der Frosch soll stets sichtbar bleiben. Hierzu werden zwei Funktionen der Engine-Kamera genutzt:

1. Der Frosch wird mit `Camera.setFocus(Actor)` in den Mittelpunkt der Kamera
   gesetzt. Sie folgt dem Frosch.
2. Gleichzeitig soll der Frosch nicht exakt im Mittelpunkt des Bildschirms sein:
   Weil das Spielziel ist, sich nach oben zu bewegen, braucht der Spieler mehr
   Blick nach oben als nach unten. Mit `Camera.setOffset(Vector)` wird die
   Kamera nach oben verschoben.

### Durch Platformen Springen: Kollisionen kontrollieren

Das Interface [CollisionListener](https://javadoc.io/doc/rocks.friedrich.engine_omega/engine-omega/latest/rocks/friedrich/engine_omega/event/CollisionEvent.html) wurde bereits in seiner grundlegenden Form im Nutzereingabe-Tutorial benutzt.

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
class Platform extends Rectangle implements CollisionListener<Frog> {

    public Platform(float width, float height) {
        super(width, height);
        setBodyType(BodyType.STATIC);
        this.addCollisionListener(Frog.class, this);
    }

    @Override
    public void onCollision(CollisionEvent<Frog> collisionEvent) {
        float frogY = collisionEvent.getColliding().getPosition().getY();
        if(frogY<this.getY()) {
            collisionEvent.ignoreCollision();
        }
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
