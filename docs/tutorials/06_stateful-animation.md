# Stateful Animation[^engine-alpha-wiki:stateful-animation]

[^engine-alpha-wiki:stateful-animation]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: [engine-alpha.org/wiki/v4.x/Stateful_Animation](https://engine-alpha.org/wiki/v4.x/Stateful_Animation)

Dies ist ein Tutorial zur Klasse [StatefulAnimation](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/StatefulAnimation.html). In diesem Tutorial:

* Konzipierst du eine komplexe Spielfigur mit Zustandsübergängen.
* Implementierst du funktionale Bewegungsmechanik für einen Platformer.
* Setzt eine komplexe Spielfigur bestehend aus mehreren Animationen in einer Spielumgebung zusammen.

## Stateful Animations

Die
[StatefulAnimation](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/StatefulAnimation.html)
ist eine elegante Möglichkeit, komplexe Spielfiguren mit wenig Aufwand
umzusetzen.

Nehmen wir dieses Beispiel:[^oop]

[^oop]: Die Spielfigur stammt aus dem [Open Pixel
Project](http://www.openpixelproject.com), aus dem Ordner
[./sprites/humans/traveler/](https://www.openpixelproject.com/wp-content/uploads/opp-assets.zip)
der Zip-Datei
[opp-assets.zip](https://www.openpixelproject.com/wp-content/uploads/opp-assets.zip).

| Zustand | Animiertes GIF                                                                                    |
| ------- | ------------------------------------------------------------------------------------------------- |
| Idle    | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/idle.gif)         |
| Jumping | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_1up.gif)     |
| Midair  | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_2midair.gif) |
| Falling | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_3down.gif)   |
| Landing | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_4land.gif)   |
| Walking | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/walk.gif)         |
| Running | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/run.gif)          |

## Zustandsübergangsdiagramm für die Figur

Bevor die Umsetzung beginnt, ist es sinnvoll, die Zustände und deren Übergänge zu modellieren. Hier ist ein mögliches Zustandsübergangsdiagramm für die Figur.

{{ image('docs/stateful-animation/TransitionDiagram.png') }}
/// caption
Zustandsübergangsdiagramm für die Figur
///

### Die Zustände als Enumeration

Zustände einer Figur werden in der Engine
stets als [enum](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html) implementiert.

Diese enum definiert die Spielerzustände und speichert gleichzeitig die
Dateipfade der zugehörigen GIF-Dateien.

{{ demo('tutorials/stateful_animation/PlayerState') }}

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

### Die Klasse für den Player Character

Mit den definierten Zuständen in `PlayerState` kann nun die Implementierung der
eigentlichen Spielfigur beginnen:

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter') }}

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
[setStateTransition(...)](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/StatefulAnimation.html)
umgesetzt werden.

Schließlich wird in `setupPhysics()` die Figur über die Engine-Physik noch
dynamisch gesetzt und bereit gemacht, sich als Platformer-Figur der Schwerkraft
auszusetzen. Der hohe Reibungswert `setFriction(30)` sorgt dafür, dass die Figur
später schnell auf dem Boden abbremsen kann, sobald sie sich nicht mehr bewegt.
Ein Verhalten, dass bei den meisten Platformern erwünscht ist.

### Testbed

Damit die Figur getestet werden kann, schreiben wir ein schnelles Testbett für
sie. In einer `Scene` bekommt sie einen Boden zum Laufen:

{{ image('docs/stateful-animation/StatefulAnimation_First_Testbed.gif') }}
/// caption
Der Zwischenstand: Noch passiert nicht viel.
///

{{ demo('tutorials/stateful_animation/StatefulAnimationDemo') }}


```java
public class StatefulAnimationDemo extends Scene
{
    public StatefulAnimationDemo()
    {
        StatefulPlayerCharacter character = new StatefulPlayerCharacter();
        setupGround();
        add(character);
        setFocus(character);
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

## Implementieren der Zustände & Übergänge

### Springen

{{ image('docs/stateful-animation/TransitionDiagram_jumpstates.png') }}
/// caption
Wir fokussieren uns nun auf die Übergänge zum Springen.
///

Auf Tastendruck (Leertaste) soll die Spielfigur
springen, wenn sie auf festem Boden steht. Die Spielfigur implementiert nun
zusätzlich den `KeyStrokeListener` und führt auf Leertastendruck die Sprungroutine aus:

{{ image('docs/stateful-animation/StatefulAnimation_First_Jump.gif') }}
/// caption
Die Figur kann springen, aber nicht landen.
///

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L92-L104](https://github.com/engine-pi/engine-pi/blob/f99a9f20e7d08584472978d54105162e3466672b/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/stateful_animation/StatefulPlayerCharacter.java#L92-L104)

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L92-L104') }}

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

### Fallen und Landen

{{ image('docs/stateful-animation/TransitionDiagram_vy_states.png') }}
/// caption
Die nächsten Übergänge, die wir umsetzen, sind für das Fallen und Landen.
///

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

{{ image('docs/stateful-animation/StatefulAnimation_Full_Jump2.gif') }}
/// caption
Die Figur hat jetzt einen vollen Sprungzyklus
///

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L107-L133') }}

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

### Player Movement

Die letzten zu implementierenden Zustände sind die Bewegung des Spielers. Durch
die Physik-Engine gibt es viele Möglichkeiten, Bewegung im Spiel zu simulieren.
Ein physikalisch korrekte Implementierung ist die kontinuierliche Anwendung
einer Bewegungskraft:

{{ image('docs/stateful-animation/StatefulAnimation_Player_Movement.png') }}
/// caption
Player Movement
///

Die (je nach Tastendruck gerichtete) Kraft beschleunigt die Spielfigur, bis die
Reibung die wirkende Kraft ausgleicht. In der Methode `setupPhysics()` wurden
bereits folgende Reibung für die Figur aktiviert:

- Luftreibung (gesetzt mit `setLinearDamping(.3)`)
- Kontaktreibung, z. B mit Platformen (gesetzt mit `setFriction(30)`)

Die Maximalgeschwindigkeit sowie die konstant wirkende Kraft setzen wir als
Konstanten in der Klasse der Figur, um diese Werte schnell ändern zu können:

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L41-L43') }}

```java
private static final Float MAX_SPEED = 20;
private static final float FORCE = 16000;
```

Um die Kraft und die Geschwindigkeit frameweise zu implementieren, wird die
Methode `onFrameUpdate(double pastTime)` erweitert:

{{ image('docs/stateful-animation/StatefulAnimation_Movement_Base.gif') }}
/// caption
Die Figur kann sich bewegen, jedoch resultiert dies noch nicht in
Zustandsänderung.
///

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L133-L145') }}

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

### Die Übergänge IDLE - WALKING - RUNNING

{{ image('docs/stateful-animation/TransitionDiagram_vx_states.png') }}
/// caption
Die letzten zu implementierenden Zustandsübergänge hängen von der
Spielerbewegung ab.
///

Die Figur kann jetzt voll gesteuert werden. Die Zustände `WALKING` und `RUNNING`
können nun eingebracht werden. Ist die Figur in einem der drei „bodenständigen“
Zustände (`IDLE`, `WALKING`, `RUNNING`), so hängt der Übergang zwischen diesen
Zuständen nur vom Betrag ihrer Geschindigkeit ab:

- Bewegt sich die Figur „langsam“, so ist sie `WALKING`.
- Bewegt sich die Figur „schnell“, so ist sie `RUNNING`.
- Bewegt sich die Figur „gar nicht“, so ist sie `IDLE`.

Um die Begriffe „langsam“ und „schnell“ greifbar zu machen, ist einen Grenzwert
nötig. Dazu definieren wir Konstanten in der Figur:

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L37-L39') }}

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

{{ image('docs/stateful-animation/StatefulAnimation_Movement_Full.gif') }}
/// caption
Die Figur ist mit ihren Zuständen und Übergängen
vollständig implementiert.
///

{{ demo('tutorials/stateful_animation/StatefulPlayerCharacter', '90cfff6e267a902bc3783c2ce7d223558a7c1289', 'L107-L172') }}

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

## Anregung zum Experimentieren

- __Different Settings, Different Game:__ Platformer werden fundamental anders,
  wenn du an den Stellschrauben drehst: Ändere die Werte für Beschleunigung,
  Entschleunigung, und Geschwindigkeit und überlege dir interessante
  Herausforderungen. Ein Platformer mit langer Be-/Ent-Schleunigung eignet sich
  weniger für viele präzise Sprünge, verlangt allerdings viel Überlegung und
  Vorbereitung von Seiten des Spielers. Spiele mit den Werten und ändere das
  Testbett und finde heraus, was dir Spaß macht.
- __Still too simple:__ Die Geschwindigkeit wird derzeit "blind" interpoliert:
  Sollte unsere Figur gegen eine Wand knallen, so wird die Geschwindigkeit im
  folgenden Frame gleich wieder auf den gewünschten Laufwert gesetzt. Durch
  smartes Reagieren auf Kollisionstests lässt sich die Figur in ihrer Bewegung
  weiter verbessern.
- __Create Something!__ Die Grundlage für einen Platformer ist geschaffen.
  Bewegung ist da. Allerdings sonst noch nicht viel. Baue ein, worauf du Lust
  hast, zum Beispiel:
    - __Ein Level:__ Stelle Platformen zusammen, baue Schluchten,
        Kletterparcours nach oben, was immer dein Jump n' Run Herz begehrt!
    - __Kamera-Einbindung:__ Die Kamera kann sich dem Charakter anpassen, sodass
        ein Level auch über die Sichtweite des Spielfensters hinaus ragen darf.
    - __Pick-Ups:__ Bei Berührung erhält der Charakter einen Bonus (z.B.
        zeitweise höhere Geschwindigkeit/Sprungkraft)
    - __Gegner:__ Andere Akteure, die der Charakter besser nicht berühren
        sollte; sie ziehen ihm Hit Points ab (oder beenden das Spiel direkt).
        Vielleicht kann sich der Charakter mit einem Mario-Sprung auf den Kopf
        der Gegner zur Wehr setzen?
    - __Ein Ziel:__ Quo Vadis? Was ist das Ziel des Levels? Von Flagge am
        rechten Levelrand über Bossgegner und Collectibles ist alles möglich.
    - etc, etc, etc.
