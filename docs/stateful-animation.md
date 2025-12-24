## Stateful Animation[^engine-alpha-wiki:stateful-animation]

[^engine-alpha-wiki:stateful-animation]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Stateful_Animation

Die
[StatefulAnimation](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/de/pirckheimer_gymnasium/engine_pi/actor/StatefulAnimation.html)
ist eine elegante Möglichkeit, komplexe Spielfiguren mit wenig Aufwand
umzusetzen.

Nehmen wir dieses Beispiel:

| Zustand | Animiertes GIF                                                                                    |
| ------- | ------------------------------------------------------------------------------------------------- |
| Idle    | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/idle.gif)         |
| Jumping | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_1up.gif)     |
| Midair  | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_2midair.gif) |
| Falling | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_3down.gif)   |
| Landing | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/jump_4land.gif)   |
| Walking | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/walk.gif)         |
| Running | ![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/traveler/run.gif)          |

### Zustandsübergangsdiagramm für die Figur

Ein mögliches Zustandsübergangsdiagramm für die Figur:

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/TransitionDiagram.png)

### Die Zustände als Enumeration

Zustände einer Figur werden in der Engine
stets als [enum](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html) implementiert.

Diese enum definiert die Spielerzustände und speichert gleichzeitig die
Dateipfade der zugehörigen GIF-Dateien.

Quellcode: [demos/stateful_animation/PlayerState.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/stateful_animation/PlayerState.java)

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

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/stateful_animation/StatefulPlayerCharacter.java)

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

### Testbed

Damit die Figur getestet werden kann, schreiben wir ein schnelles Testbett für
sie. In einer `Scene` bekommt sie einen Boden zum Laufen:

![Der Zwischenstand: Noch passiert nicht viel.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_First_Testbed.gif)

Quellcode: [demos/stateful_animation/StatefulAnimationDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/stateful_animation/StatefulAnimationDemo.java)

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

![Wir fokussieren uns nun auf die Übergänge zum Springen.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/TransitionDiagram_jumpstates.png)

Auf Tastendruck (Leertaste) soll die Spielfigur
springen, wenn sie auf festem Boden steht. Die Spielfigur implementiert nun
zusätzlich den `KeyStrokeListener` und führt auf Leertastendruck die Sprungroutine aus:

![Die Figur kann springen, aber nicht landen.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_First_Jump.gif)

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L92-L104](https://github.com/engine-pi/engine-pi/blob/f99a9f20e7d08584472978d54105162e3466672b/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/stateful_animation/StatefulPlayerCharacter.java#L92-L104)

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

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L108-L133](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L108-L133)

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

![](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_Player_Movement.png)

Die (je nach Tastendruck gerichtete) Kraft beschleunigt die Spielfigur, bis die
Reibung die wirkende Kraft ausgleicht. In der Methode `setupPhysics()` wurden
bereits folgende Reibung für die Figur aktiviert:

- Luftreibung (gesetzt mit `setLinearDamping(.3)`)
- Kontaktreibung, z. B mit Platformen (gesetzt mit `setFriction(30)`)

Die Maximalgeschwindigkeit sowie die konstant wirkende Kraft setzen wir als
Konstanten in der Klasse der Figur, um diese Werte schnell ändern zu können:

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L41-L43](https://github.com/engine-pi/engine-pi/blob/f99a9f20e7d08584472978d54105162e3466672b/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/stateful_animation/StatefulPlayerCharacter.java#L41-L43)

```java
private static final Float MAX_SPEED = 20;
private static final float FORCE = 16000;
```

Um die Kraft und die Geschwindigkeit frameweise zu implementieren, wird die
Methode `onFrameUpdate(double pastTime)` erweitert:

![Die Figur kann sich bewegen, jedoch resultiert dies noch nicht in
Zustandsänderung.](https://raw.githubusercontent.com/engine-pi/engine-pi/main/misc/images/stateful-animation/StatefulAnimation_Movement_Base.gif)

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L134-L146](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L134-L146)

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

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L37-L39](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L37-L39)

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

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L107-L172](https://github.com/engine-pi/engine-pi/blob/c196e1adb23228b21633277c0bffe11ae08f1e61/src/test/java/de/pirckheimer_gymnasium/engine_pi/demos/stateful_animation/StatefulPlayerCharacter.java#L107-L172)

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
