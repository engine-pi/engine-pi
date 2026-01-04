# impluse (Impuls)[^engine-alpha-wiki:physics]

[^engine-alpha-wiki:physics]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Physics

<!-- ## Tutorial: Dominosteine umwerfen -->

Um die Grundlagen der Engine Pi Physics zu testen, bauen wir eine einfache
Kettenreaktion: Ein Ball wird gegen eine Reihe von Dominos geworfen.

Bevor wir die Physik einschalten, bauen wir das Spielfeld mit allen Objekten auf:

```java
public class ImpulseDemo extends Scene
        implements FrameUpdateListener, MouseClickListener
{
    private Rectangle ground;

    private Rectangle wall;

    private Circle ball;

    private Rectangle angle;

    public ImpulseDemo()
    {
        setupBasicObjects();
        makeDominoes();
    }

    private void setupBasicObjects()
    {
        // Boden auf dem die Dominosteine stehen
        ground = new Rectangle(200, 2);
        ground.center(0, -5);
        ground.color("white");
        add(ground);
        // Der Ball, der die Dominosteine umwerfen soll.
        ball = new Circle(0.5);
        ball.color("red");
        ball.position(-10, -2);
        add(ball);
        // Eine senkrechte Wand links der Simulation
        wall = new Rectangle(1, 40);
        wall.position(-14, -4);
    }

    private void makeDominoes()
    {
        for (int i = 0; i < 20; i++)
        {
            Rectangle domino = new Rectangle(0.4, 3);
            domino.position(i * 3 * 0.4, -4);
            domino.makeDynamic();
            domino.color("blue");
            add(domino);
        }
    }
}
```

Dieser Code baut ein einfaches Spielfeld auf: Ein roter Ball, ein paar
Dominosteine, und ein weißer Boden mit Wand.

{{ image('docs/Dominos_1-statisch.png') }}
/// caption
Das Spielbrett ist aufgebaut, allerdings passiert noch nichts interessantes. Zeit für Physik!
///

Wir erwarten verschiedenes Verhalten von den physikalischen Objekten. Dies
drückt sich in verschiedenen {{ class('pi.actor.BodyType') }}s  aus:

- Der Ball und die Dominos sollen sich verhalten wie normale physische Objekte:
  Der Ball prallt an den Dominos ab und die Steine fallen um. Diese `Actors` haben
  einen dynamischen Körper.
- Aber der Boden und die Wand sollen nicht wie die Dominos umfallen. Egal mit
  wie viel Kraft ich den Ball gegen die Wand werfe, sie wird niemals nachgeben.
  Diese `Actors` haben einen statischen Körper.

Mit der Methode `#!java Actor.setBodyType(BodyType)` wird das grundlegende Verhalten
eines `Actors` bestimmt. Zusätzlich wird mit `#!java Scene.setGracity(Vector)` eine
Schwerkraft gesetzt, die auf den Ball und die Dominos wirkt.
Jetzt wirkt Schwerkraft auf die dynamischen Objekte und der statische Boden
hält den Fall

In einer `#!java setupPhysics()`-Methode werden die Body Types für die Actors gesetzt und
die Schwerkraft (standardmäßige `9,81 m/s^2`, gerade nach unten) aktiviert:

{{ code('docs/physics/ImpulseDemo.java', 77, 83) }}

Zusätzlich werden die Dominos in `makeDominoes()` mit `domino.makeDynamic();`
eingerichtet.

{{ image('docs/Dominos_2-dynamisch.gif') }}
/// caption
Jetzt wirkt Schwerkraft auf die dynamischen Objekte und der statische Boden hält den Fall
///

Dynamische und statische Körper sind die essentiellsten Body Types in der
Engine, allerdings nicht die einzigen. Du findest einen Umriss aller Body Types
in der Dokumentation von {{ class('pi.actor.BodyType') }} und eine vergleichende Übersicht in der
dedizierten Wikiseite Den Ball Werfen Mit einem Methodenaufruf fliegt der Ball

Zeit, die Dominos umzuschmeißen! Die Methode
`applyImpulse(Vector)` erlaubt, den Ball physikalisch korrekt zu
'werfen'.

Mit der Zeile `ball.applyImpulse(new Vector(15, 12));` kannst der erste
Ballwurf getestet werden.

{{ image('docs/Dominos_3-Wurf.gif') }}
/// caption
Mit einem Methodenaufruf fliegt der Ball
///

Um hieraus eine Spielmechanik zu bauen, soll der Spieler Richtung und Stärke des
Wurfes mit der Maus kontrollieren können: Per Mausklick wird der Ball in
Richtung des Mauscursors katapultiert. Das Angle-Objekt hilft dem Spieler

Hierzu wird ein weiteres Rechteck angle eingeführt, das die Richtung des
Impulses markiert:

{{ code('docs/physics/ImpulseDemo.java', 70, 75) }}

{{ image('docs/Dominos_4-Wurfwinkel-Visualisierung.gif') }}
/// caption
Visualisierung des Wurfwinkels
///

Wir wollen, dass das Rechteck stets Ball und Maus verbindet. Die einfachste
Methode hierzu ist, in jedem Frame das Rechteck erneut an die Maus anzupassen.
Dafür implementiert die Dominoes-Klasse das Interface `FrameUpdateListener` und
berechnet frameweise anhand der aktuellen Mausposition die korrekte Länge und
den korrekten Winkel, um die visuelle Hilfe richtig zu positionieren:

{{ code('docs/physics/ImpulseDemo.java', 97, 107) }}

Zuletzt muss der Ballwurf bei Mausklick umgesetzt werden. Hierzu wird noch das
Interface `MouseClickListener` implementiert:

{{ code('docs/physics/ImpulseDemo.java', 109, 114) }}

<!-- - Von Dominos zu Kartenhaus: Mehrere Schichten von Dominos, mit quer gelegten
  Steinen als Fundament zwischen den Schichten, sorgen für mehr Spaß bei der
  Zerstörung.
- Reset Button: Ein Knopfdruck setzt den Ball auf seine Ursprüngliche Position
  (und Geschwindigkeit) zurück; dabei werden all Dominos wieder neu aufgesetz. -->

## Der komplette Code

{{ code('docs/physics/ImpulseDemo.java') }}
