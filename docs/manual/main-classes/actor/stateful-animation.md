# `StatefulAnimation` (Zustandsbehaftete Animation)[^engine-alpha-wiki:stateful-animation]

[^engine-alpha-wiki:stateful-animation]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: [engine-alpha.org/wiki/v4.x/Stateful_Animation](https://engine-alpha.org/wiki/v4.x/Stateful_Animation)

{{ import_admonition('pi.actor.StatefulAnimation') }}

<!-- Dies ist ein Tutorial zur Klasse {{ javadoc('pi.actor.StatefulAnimation') }}. In diesem Tutorial:

- Konzipierst du eine komplexe Spielfigur mit Zustandsübergängen.
- Implementierst du funktionale Bewegungsmechanik für einen Platformer.
- Setzt eine komplexe Spielfigur bestehend aus mehreren Animationen in einer Spielumgebung zusammen.

## Stateful Animations -->

Mit der Figur {{ javadoc('pi.actor.StatefulAnimation') }} lassen sich komplexe Spielfiguren mit wenig Aufwand umsetzen.

Nehmen wir dieses Beispiel:[^oop]

[^oop]:
    Die Spielfigur stammt aus dem [Open Pixel
    Project](http://www.openpixelproject.com), aus dem Ordner
    [./sprites/humans/traveler/](https://www.openpixelproject.com/wp-content/uploads/opp-assets.zip)
    der Zip-Datei
    [opp-assets.zip](https://www.openpixelproject.com/wp-content/uploads/opp-assets.zip).

| Zustand | Animiertes GIF                                                                                               |
| ------- | ------------------------------------------------------------------------------------------------------------ |
| Idle    | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_idle_anim.gif') }}         |
| Jumping | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_jump_1up_anim.gif') }}     |
| Midair  | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_jump_2midair_anim.gif') }} |
| Falling | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_jump_3down_anim.gif') }}   |
| Landing | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_jump_4land_anim.gif') }}   |
| Walking | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_walk_anim.gif') }}         |
| Running | {{ image('demos/resources/openpixelproject/sprites/humans/traveler/spr_m_traveler_run_anim.gif') }}          |

## Zustandsübergangsdiagramm für die Figur

Bevor mit der Umsetzung begonnen wird, ist es sinnvoll, die Zustände und deren
Übergänge zu modellieren. Hier ist ein mögliches Zustandsübergangsdiagramm für
die Figur.

{{ image('docs/stateful-animation/TransitionDiagram.png') }}
/// caption
Zustandsübergangsdiagramm für die Figur
///

### Die Zustände als Aufzählungstyp (Enumeration)

Die Zustände einer Figur werden in der Engine stets als [Aufzählungstyp
(enum)](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
implementiert. Dieser Aufzählungstyp definiert die Spielerzustände und speichert
gleichzeitig die Dateipfade der zugehörigen GIF-Dateien.

<!-- ```java
public enum PlayerState
{
    IDLE("idle"),
    WALKING("walk"),
    RUNNING("run"),
    JUMPING("jump_1up"),
    MIDAIR("jump_2midair"),
    FALLING("jump_3down"),
    LANDING("jump_4land");

    private final String filename;

    PlayerState(String filename)
    {
        this.filename = filename;
    }

    @Getter
    public String gifFileLocation()
    {
        return "openpixelproject/sprites/humans/traveler/spr_m_traveler_"
                + filename + "_anim.gif";
    }
}
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/PlayerState.java -->

{{ code('demos.docs.main_classes.actor.stateful_animation.PlayerState.java', start_line=27) }}

Ist beispielsweise das GIF des Zustandes
`#!java JUMPING` gefragt, so ist es jederzeit mit `#!java JUMPING.gifFileLocation()`
erreichbar. Dies macht den Code deutlich wartbarer.

### Die Klasse der Spielfigur

Mit den definierten Zuständen in `#!java PlayerState` kann nun die Implementierung der
eigentlichen Spielfigur beginnen:

<!-- ```java
public class StatefulPlayerCharacter extends StatefulAnimation<PlayerState>
        implements KeyStrokeListener, FrameUpdateListener
{

    public StatefulPlayerCharacter(Text text)
    {
        // Alle Bilder haben die Abmessung 64x64px und deshalb die gleiche
        // Breite und Höhe. Wir verwenden drei Meter.
        super(3, 3);
        this.text = text;
        setupPlayerStates();
        setupAutomaticTransitions();
        setupPhysics();
    }

    private void setupPlayerStates()
    {
        for (PlayerState state : PlayerState.values())
        {
            Animation animationOfState = Animation
                .createFromAnimatedGif(state.gifFileLocation(), 3, 3);
            addState(state, animationOfState);
        }
    }

    private void setupAutomaticTransitions()
    {
        stateTransition(PlayerState.MIDAIR, PlayerState.FALLING);
        stateTransition(PlayerState.LANDING, PlayerState.IDLE);
    }

    private void setupPhysics()
    {
        makeDynamic();
        rotationLocked(true);
        restitution(0);
        friction(30);
        linearDamping(0.3);
    }
}
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

{{ code('docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java', start_line=35, end_line=85) }}

In `#!java setupPlayerStates()` werden alle in `#!java PlayerState` definierten
Zustände der Spielfigur eingepflegt, inklusive des Einladens der animierten
GIFs.

Zwei der Zustände bestehen nur aus einen Animationszyklus. Danach sollen sie in
einen anderen Zustand übergehen: `#!java MIDAIR` geht über zu `#!java FALLING`
und `#!java LANDING` geht über zu `#!java IDLE`. Diese Übergänge können direkt
über die Methode
{{ javadoc('pi.actor.StatefulAnimation#stateTransition(State,State)', 'stateTransition()') }} umgesetzt werden.

Schließlich wird in `#!java setupPhysics()` die Figur über die Engine-Physik
noch dynamisch gesetzt und bereit gemacht, sich als Platformer-Figur der
Schwerkraft auszusetzen. Der hohe Reibungswert `#!java friction(30)` sorgt
dafür, dass die Figur später schnell auf dem Boden abbremsen kann, sobald sie
sich nicht mehr bewegt. Ein Verhalten, dass bei den meisten Platformern
erwünscht ist.

### Einbetten in eine Szene

Damit die Figur getestet werden kann, schreiben wir ein schnelles Testbett für
sie. In einer {{ javadoc('pi.Scene') }} bekommt sie einen Boden zum Laufen:

{{ image('docs/stateful-animation/StatefulAnimation_First_Testbed.gif') }}
/// caption
Der Zwischenstand: Noch passiert nicht viel.
///

<!--```java
public class StatefulAnimationDemo extends Scene
{
    public StatefulAnimationDemo()
    {
        Text text = new Text("State");
        text.anchor(-10, 5);
        text.makeStatic();
        add(text);
        StatefulPlayerCharacter character = new StatefulPlayerCharacter(text);
        setupGround();
        add(character);
        focus(character);
        gravityOfEarth();
    }

    private void setupGround()
    {
        Rectangle ground = makePlatform(200, 0.2);
        ground.center(0, -5);
        ground.color(new Color(255, 195, 150));
        makePlatform(12, 0.3).center(16, -1);
        makePlatform(7, 0.3).center(25, 2);
        makePlatform(20, 0.3).center(35, 6);
        makeBall(5).center(15, 3);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new StatefulAnimationDemo(), 1200, 820);
    }
}
```-->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulAnimationDemo.java -->

{{ code('docs/main_classes/actor/stateful_animation/StatefulAnimationDemo.java', start_line=36) }}

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
zusätzlich den `#!java KeyStrokeListener` und führt auf Leertastendruck die Sprungroutine aus:

{{ image('docs/stateful-animation/StatefulAnimation_First_Jump.gif') }}
/// caption
Die Figur kann springen, aber nicht landen.
///

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

Quellcode: [demos/stateful_animation/StatefulPlayerCharacter.java#L92-L104](https://github.com/engine-pi/engine-pi/blob/f99a9f20e7d08584472978d54105162e3466672b/engine-pi-demos/src/main/java/pi_demos/stateful_animation/StatefulPlayerCharacter.java#L92-L104)

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
in den `#!java IDLE`-Zustand kommt. Dafür ist die Geschwindigkeit der Figur in
`Y`-Richtung wichtig. Im Zustandsübergangsdiagramm haben wir dafür `#!java v_y < 0` als
Fallen definiert und `#!java v_y = 0` als Stehen. Das ist im Modell in Ordnung,
allerdings ist die Physik mit Fließkomma-Zahlen nicht ideal für „harte“
Schwellwerte. Stattdessen definieren wir einen Grenzwert, innerhalb dessen wir
auf `0` runden. (`#!java private static final float THRESHOLD = 0.01;`).

Unsere Spielfigur soll in jedem Einzelbild ihre eigene `Y`-Geschwidingkeit
überprüfen. Dazu implementiert sie nun zusätzlich `#!java FrameUpdateListener` und
prüft in jedem Frame entsprechend unseres Zustandsübergangsdiagrammes:

{{ image('docs/stateful-animation/StatefulAnimation_Full_Jump2.gif') }}
/// caption
Die Figur hat jetzt einen vollen Sprungzyklus
///

<!-- ```java
    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector velocity = velocity();
        PlayerState state = state();
        text.content(state);
        if (velocity.y() < -THRESHOLD)
        {
            switch (state)
            {
            case JUMPING:
                state(PlayerState.MIDAIR);
                break;

            case IDLE:
            case WALKING:
            case RUNNING:
                state(PlayerState.FALLING);
                break;

            default:
                break;
            }
        }
        else if (velocity.y() < THRESHOLD && state == PlayerState.FALLING)
        {
            state(PlayerState.LANDING);
        }
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

{{ code('demos.docs.main_classes.actor.stateful_animation.StatefulPlayerCharacter.java', start_line=110, end_line=137) }}

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
Reibung die wirkende Kraft ausgleicht. In der Methode `#!java setupPhysics()` wurden
bereits folgende Reibung für die Figur aktiviert:

- Luftreibung (gesetzt mit `#!java setLinearDamping(.3)`)
- Kontaktreibung, z. B. mit Plattformen (gesetzt mit `#!java setFriction(30)`)

Die Maximalgeschwindigkeit sowie die konstant wirkende Kraft setzen wir als
Konstanten in der Klasse der Figur, um diese Werte schnell ändern zu können:

<!-- ```java
    private static final double MAX_SPEED = 20;

    private static final double FORCE = 16000;
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

{{ code('demos.docs.main_classes.actor.stateful_animation.StatefulPlayerCharacter.java', start_line=44, end_line=46) }}

Um die Kraft und die Geschwindigkeit frameweise zu implementieren, wird die
Methode `#!java onFrameUpdate(double pastTime)` erweitert:

{{ image('docs/stateful-animation/StatefulAnimation_Movement_Base.gif') }}
/// caption
Die Figur kann sich bewegen, jedoch resultiert dies noch nicht in
Zustandsänderung.
///

In der Methode `#!java onFrameUpdate()`:

<!-- ```java
        if (Math.abs(velocity.x()) > MAX_SPEED)
        {
            velocity(new Vector(Math.signum(velocity.x()) * MAX_SPEED,
                    velocity.y()));
        }
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            applyForce(new Vector(-FORCE, 0));
        }
        else if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            applyForce(new Vector(FORCE, 0));
        }
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

{{ code('demos.docs.main_classes.actor.stateful_animation.StatefulPlayerCharacter.java', start_line=138, end_line=150) }}

### Die Übergänge IDLE - WALKING - RUNNING

{{ image('docs/stateful-animation/TransitionDiagram_vx_states.png') }}
/// caption
Die letzten zu implementierenden Zustandsübergänge hängen von der
Spielerbewegung ab.
///

Die Figur kann jetzt voll gesteuert werden. Die Zustände `#!java WALKING` und `#!java RUNNING`
können nun eingebracht werden. Ist die Figur in einem der drei „bodenständigen“
Zustände (`#!java IDLE`, `#!java WALKING`, `#!java RUNNING`), so hängt der Übergang zwischen diesen
Zuständen nur vom Betrag ihrer Geschindigkeit ab:

- Bewegt sich die Figur „langsam“, so ist sie `#!java WALKING`.
- Bewegt sich die Figur „schnell“, so ist sie `#!java RUNNING`.
- Bewegt sich die Figur „gar nicht“, so ist sie `#!java IDLE`.

Um die Begriffe *„langsam“* und *„schnell“* greifbar zu machen, ist einen Grenzwert
nötig. Dazu definieren wir Konstanten in der Figur:

<!-- ```java
private static final double RUNNING_THRESHOLD = 10;

private static final double WALKING_THRESHOLD = 1;
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

{{ code('demos.docs.main_classes.actor.stateful_animation.StatefulPlayerCharacter.java', start_line=40, end_line=42) }}

Sobald sich die Figur mindestens 1 Meter pro Sekunde bewegt, zählt sie als `#!java WALKING`,
sobald sie sich mindestens 10 Meter pro Sekunde bewegt (die Hälfte der maximalen
Geschwindigkeit), so zählt sie als `#!java RUNNING`.

Auf diese Grenzwerte wird jeden Frame in der `#!java onFrameUpdate(...)` der Spielfigur
geprüft, genauso wie zuvor die Y-Geschwindigkeit implementiert wurde. Damit ist
die neue `#!java onFrameUpdate(...)`:

{{ image('docs/stateful-animation/StatefulAnimation_Movement_Full.gif') }}
/// caption
Die Figur ist mit ihren Zuständen und Übergängen
vollständig implementiert.
///

<!-- ```java
    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector velocity = velocity();
        PlayerState state = state();
        text.content(state);
        if (velocity.y() < -THRESHOLD)
        {
            switch (state)
            {
            case JUMPING:
                state(PlayerState.MIDAIR);
                break;

            case IDLE:
            case WALKING:
            case RUNNING:
                state(PlayerState.FALLING);
                break;

            default:
                break;
            }
        }
        else if (velocity.y() < THRESHOLD && state == PlayerState.FALLING)
        {
            state(PlayerState.LANDING);
        }
        if (Math.abs(velocity.x()) > MAX_SPEED)
        {
            velocity(new Vector(Math.signum(velocity.x()) * MAX_SPEED,
                    velocity.y()));
        }
        if (Controller.isKeyPressed(KeyEvent.VK_A))
        {
            applyForce(new Vector(-FORCE, 0));
        }
        else if (Controller.isKeyPressed(KeyEvent.VK_D))
        {
            applyForce(new Vector(FORCE, 0));
        }
        if (state == PlayerState.IDLE || state == PlayerState.WALKING
                || state == PlayerState.RUNNING)
        {
            double velXTotal = Math.abs(velocity.x());
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
        if (velocity.x() > 0)
        {
            flipHorizontal(false);
        }
        else if (velocity.x() < 0)
        {
            flipHorizontal(true);
        }
    }
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/stateful_animation/StatefulPlayerCharacter.java -->

{{ code('demos.docs.main_classes.actor.stateful_animation.StatefulPlayerCharacter.java', start_line=110, end_line=176) }}

Die letzte Überprüfung der X-Geschwindigkeit dient dazu, die Bewegungsrichtung
festzustellen. Mit dieser Info kann zum richtigen Zeitpunkt über
`#!java setFlipHorizontal(boolean flip)` die Blickrichtung der Figur angepasst werden.

<!-- ## Anregung zum Experimentieren

- **Different Settings, Different Game:** Platformer werden fundamental anders,
  wenn du an den Stellschrauben drehst: Ändere die Werte für Beschleunigung,
  Entschleunigung, und Geschwindigkeit und überlege dir interessante
  Herausforderungen. Ein Platformer mit langer Be-/Ent-Schleunigung eignet sich
  weniger für viele präzise Sprünge, verlangt allerdings viel Überlegung und
  Vorbereitung von Seiten des Spielers. Spiele mit den Werten und ändere das
  Testbett und finde heraus, was dir Spaß macht.
- **Still too simple:** Die Geschwindigkeit wird derzeit "blind" interpoliert:
  Sollte unsere Figur gegen eine Wand knallen, so wird die Geschwindigkeit im
  folgenden Frame gleich wieder auf den gewünschten Laufwert gesetzt. Durch
  smartes Reagieren auf Kollisionstests lässt sich die Figur in ihrer Bewegung
  weiter verbessern.
- **Create Something!** Die Grundlage für einen Platformer ist geschaffen.
  Bewegung ist da. Allerdings sonst noch nicht viel. Baue ein, worauf du Lust
  hast, zum Beispiel:
  - **Ein Level:** Stelle Platformen zusammen, baue Schluchten,
    Kletterparcours nach oben, was immer dein Jump n' Run Herz begehrt!
  - **Kamera-Einbindung:** Die Kamera kann sich dem Charakter anpassen, sodass
    ein Level auch über die Sichtweite des Spielfensters hinaus ragen darf.
  - **Pick-Ups:** Bei Berührung erhält der Charakter einen Bonus (z.B.
    zeitweise höhere Geschwindigkeit/Sprungkraft)
  - **Gegner:** Andere Akteure, die der Charakter besser nicht berühren
    sollte; sie ziehen ihm Hit Points ab (oder beenden das Spiel direkt).
    Vielleicht kann sich der Charakter mit einem Mario-Sprung auf den Kopf
    der Gegner zur Wehr setzen?
  - **Ein Ziel:** Quo Vadis? Was ist das Ziel des Levels? Von Flagge am
    rechten Levelrand über Bossgegner und Collectibles ist alles möglich.
  - etc, etc, etc. -->
