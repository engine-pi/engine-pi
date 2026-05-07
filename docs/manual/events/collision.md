# `CollisionListener` (Kollisionen-Erkennung)[^engine-alpha-wiki:collision]

[^engine-alpha-wiki:collision]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Collision

Zu diesem Tutorial gibt es ein
[Repository](https://github.com/engine-pi/froggy-jump) in the
[Engine-Pi-Github-Organsiation](https://github.com/engine-pi/froggy-jump).

Die einzelnen Entwicklungsstadien sind in folgenden Git-Branches hinterlegt:

1. [blank](https://github.com/engine-pi/froggy-jump/tree/blank):
   Die Projektstruktur, die leere Hauptklasse `FroggyJump` ist angelegt, sowie
   die zwei benötigten Grafikdateien sind dem Repository hinzugefügt.
1. [basic](https://github.com/engine-pi/froggy-jump/tree/basic):
   10 vertikal übereinander positionierte Plattformen, der Frosch kann horizonal
   bewegt werden, der Frosch springt automatisch von den Plattformen ab.
1. [jump-through](https://github.com/engine-pi/froggy-jump/tree/jump-through):
   Frosch kann durch die Plattformen hindurchspringen.
1. [platforms-deluxe](https://github.com/engine-pi/froggy-jump/tree/platforms-deluxe)
   Zufällig angeordnete Plattformen, die nach oben hin immer mehr Abstand zu
   einander aufweisen.
1. [spike-balls](https://github.com/engine-pi/froggy-jump/tree/spike-balls):
   Eisenkugeln mit Stacheln fallen auf den Frosch, falls er den Kugeln zu nahe kommt.
1. [death-scene](https://github.com/engine-pi/froggy-jump/tree/death-scene) bzw. [final](https://github.com/engine-pi/froggy-jump/tree/final):
   Wird der Frosch von einer Stachelkugel getroffen, erscheint eine Szene,
   die den Tod des Frosches verkündet.

## Spielkonzept und grundlegender Aufbau

Ein Frosch soll fröhlich durch das Spiel springen und sich vom Boden abstoßen,
wenn immer er die Chance dazu hat.

<!-- Go to file:///data/school/repos/inf/java/engine-pi/assets/docs/events/collision -->

{{ image('docs/events/collision/Frog.png', 'Dieser Frosch soll durch das Spiel springen') }}

In der Szene `FroggyJump` kann der Spieler eine Figur der Klasse `Frog` steuern.
Zusätzlich geben Figuren der Klasse `Platform` Halt.

Damit ergibt sich das Codegerüst für das Spiel:

```java
import pi.Camera;
import pi.Controller;
import pi.Scene;
import pi.graphics.geom.Vector;

public class FroggyJump extends Scene
{
    private Frog frog;

    private static final double PLATFORM_HEIGHT = 0.5;

    public FroggyJump()
    {
        frog = new Frog();
        add(frog);
        gravityOfEarth();
        Camera camera = camera();
        camera.focus(frog);
        camera.offset(new Vector(0, 4));
        makePlatforms(10);
    }

    private void makePlatforms(int count)
    {
        for (int i = 0; i < count; i++)
        {
            Platform platform = new Platform(5, PLATFORM_HEIGHT);
            platform.anchor(0, (double) i * 4);
            add(platform);
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new FroggyJump(), 400, 600);
    }
}
```

```java
import java.awt.event.KeyEvent;

import pi.Controller;
import pi.actor.Image;
import pi.event.FrameListener;

class Frog extends Image implements FrameListener
{
    private boolean jumpEnabled = true;

    private static final double MAX_SPEED = 4;

    public Frog()
    {
        super("images/Frog.png");
        pixelPerMeter(25);
        makeDynamic();
        rotationLocked(true);
    }

    public void jumpEnabled(boolean jumpEnabled)
    {
        this.jumpEnabled = jumpEnabled;
    }

    @Override
    public void onFrame(double pastTime)
    {
        // Die Blickrichtung des Frosches steuern
        flippedHorizontally(velocityX() < 0);

        // Die horizontale Bewegung steuern
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            if (velocityX() > 0)
            {
                velocityX(0);
            }
            applyForce(-600, 0);
        }
        else if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            if (velocityX() < 0)
            {
                velocityX(0);
            }
            applyForce(600, 0);
        }

        // Die horizontale Geschwindigkeit begrenzen
        if (Math.abs(velocityX()) > MAX_SPEED)
        {
            velocityX(MAX_SPEED * Math.signum(velocityX()));
        }

        // Wenn möglich den Frosch springen lassen
        if (isGrounded() && velocityY() <= 0 && jumpEnabled)
        {
            velocityY(0);
            applyImpulse(0, 180);
        }
    }
}
```

```java
import pi.actor.Rectangle;

class Platform extends Rectangle
{
    public Platform(double width, double height)
    {
        super(width, height);
        makeStatic();
        color("brown");
    }
}
```

{{ image('docs/events/collision/FrogTutorial1.gif', 'Der Frosch kann sich bewegen, knallt aber unangenehmerweise noch gegen die Decke') }}

Ein paar Erklärungen zum Codegerüst für `FroggyJump`:

## Physikalische Eigenschaften

Wie im Physics-Tutorial beschrieben, werden die physikalischen Eigenschaften der
Spielobjekte und ihrer Umgebung bestimmt:

- Platformen sind **statische** Objekte: Sie ignorieren Schwerkraft und können
  nicht durch andere Objekte verschoben werden (egal mit wie viel Kraft der
  Frosch auf sie fällt).
- Der Frosch ist ein **dynamisches** Objekt: Er lässt sich von der Schwerkraft
  beeinflussen und wird von den statischen Platformen aufgehalten.
- In der Scene `FroggyJump` existiert eine Schwerkraft von 10 m/s^2. Sie wird
  mit `setGravity(Vector)` gesetzt.

## Bewegung des Frosches

Die Bewegung des Frosches wird in jedem Frame kontrolliert. Wie im Game Loop
Tutorial beschrieben, wird hierzu das Interface `Listener` genutzt.

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
- Teil C: Springe, wenn möglich: Mit der Methode `isGrounded()` bietet die
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

## Durch Platformen Springen: Kollisionen kontrollieren

Das Interface {{ javadoc('pi.event.CollisionListener') }} wurde bereits in
seiner grundlegenden Form im Nutzereingabe-Tutorial benutzt.

{{ javadoc('pi.event.CollisionListener') }} kann mehr als nur melden, wenn zwei
Actor-Objekte sich überschneiden. Um das `FroggyJump`-Spiel zu implementieren,
nutzen wir weitere Features.

Unser Frosch soll fähig sein, von unten „durch die Platform hindurch“ zu
springen. Von oben fallend soll er natürlich auf der Platform stehen bleiben.

Um diesen Effekt zu erzeugen, müssen Kollisionen zwischen Frosch und Platform
unterschiedlich behandelt werden:

1. Kollidiert der Frosch von unten, so soll die Kollision ignoriert werden.
   Er prallt so nicht von der Decke ab und kann weiter nach oben Springen.
2. Kollidiert der Frosch von oben, so soll die Kollision normal aufgelöst werden,
   sodass er nicht durch den Boden fällt.

Hierzu stellt das `CollisionEvent`-Objekt in der `onCollision`-Methode Funktionen bereit.

{{ code('docs/events/collision/froggy_jump/Platform.java', 29) }}

<!-- ```java
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
        double frogY = collisionEvent.colliding().position().y();
        if (frogY < y())
        {
            collisionEvent.ignoreCollision();
            collisionEvent.colliding().setJumpEnabled(false);
        }
    }

    @Override
    public void onCollisionEnd(CollisionEvent<Frog> collisionEvent)
    {
        collisionEvent.colliding().setJumpEnabled(true);
    }
}
``` -->

## Kompletter Code

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/events/collision/froggy_jump/FroggyJump.java -->


<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/events/collision/froggy_jump/package-info.java -->


{{ code('docs/events/collision/froggy_jump/FroggyJump.java', 25) }}

{{ code('docs/events/collision/froggy_jump/DeathScene.java', 25) }}

{{ code('docs/events/collision/froggy_jump/Frog.java', 25) }}

{{ code('docs/events/collision/froggy_jump/Platform.java', 25) }}

{{ code('docs/events/collision/froggy_jump/SpikeBall.java', 25) }}

{{ code('docs/events/collision/froggy_jump/SpikeSensor.java', 25) }}
