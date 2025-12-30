# Kollisionen-Erkennung[^engine-alpha-wiki:collision]

[^engine-alpha-wiki:collision]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Collision

## Spielkonzept und grundlegender Aufbau

Ein Frosch soll fröhlich durch das Spiel springen und sich vom Boden abstoßen,
wenn immer er die Chance dazu hat.

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/src/test/resources/froggy/Frog.png)
/// caption
Dieser Frosch soll durch das Spiel springen:
///

In der Scene `FroggyJump` kann der Spieler ein
Objekt der Klasse `Frog` steuern. Zusätzlich geben Objekte der Klasse `Platform`
halt.

Damit ergibt sich das Codegerüst für das Spiel:

{{ demo('tutorials/collision/FroggyJump') }}

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

{{ image('docs/FrogTutorial1.gif') }}
/// caption
Der Frosch kann sich bewegen, knallt aber unangenehmerweise noch gegen die Decke
///

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

## Durch Platformen Springen: Kollisionen kontrollieren

Das Interface {{ class('event.CollisionListener') }} wurde bereits in
seiner grundlegenden Form im Nutzereingabe-Tutorial benutzt.

{{ class('event.CollisionListener') }} kann mehr als nur melden, wenn zwei
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

{{ demo('tutorials/collision/FroggyJump', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L172-L197') }}

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
