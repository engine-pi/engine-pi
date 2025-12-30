# Physics[^engine-alpha-wiki:physics]

[^engine-alpha-wiki:physics]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Physics

Die Engine Pi nutzt eine [Java-Version](http://jbox2d.org/) von [Box2D](https://box2d.org/). Diese mächtige
und effiziente Physics-Engine ist in der Engine Pi leicht zu bedienen und
ermöglicht es, mit wenig Aufwand mechanische Phänomene in ein Spiel zu bringen.

Die Physics Engine basiert auf den Prinzipien der [klassischen
Mechanik](https://de.wikipedia.org/wiki/Klassische_Mechanik).

## Tutorial: Dominosteine umwerfen

Um die Grundlagen der Engine Pi Physics zu testen, bauen wir eine einfache
Kettenreaktion: Ein Ball wird gegen eine Reihe von Dominos geworfen.

Bevor wir die Physik einschalten, bauen wir das Spielfeld mit allen Objekten auf:

{{ demo('tutorials/physics/DominoesDemo') }}

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

{{ image('docs/Dominos_1-statisch.png') }}
/// caption
Das Spielbrett ist aufgebaut, allerdings passiert noch nichts interessantes. Zeit für Physik!
///

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

{{ demo('tutorials/physics/DominoesDemo', '3c5fad40d3031ce6de9719deb41bf52f1a774022', 'L77-L83') }}

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

{{ image('docs/Dominos_2-dynamisch.gif') }}
/// caption
Jetzt wirkt Schwerkraft auf die dynamischen Objekte und der statische Boden hält den Fall
///

Dynamische und statische Körper sind die essentiellsten Body Types in der
Engine, allerdings nicht die einzigen. Du findest einen Umriss aller Body Types
in der Dokumentation von `BodyType` und eine vergleichende Übersicht in der
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

{{ demo('tutorials/physics/DominoesDemo', '3c5fad40d3031ce6de9719deb41bf52f1a774022', 'L70-L75') }}


```java
private void setupAngle()
{
    angle = new Rectangle(1, 0.1);
    angle.setColor(Color.GREEN);
    add(angle);
}
```

{{ image('docs/Dominos_4-Wurfwinkel-Visualisierung.gif') }}
/// caption
Visualisierung des Wurfwinkels
///

Wir wollen, dass das Rechteck stets Ball und Maus verbindet. Die einfachste
Methode hierzu ist, in jedem Frame das Rechteck erneut an die Maus anzupassen.
Dafür implementiert die Dominoes-Klasse das Interface `FrameUpdateListener` und
berechnet frameweise anhand der aktuellen Mausposition die korrekte Länge und
den korrekten Winkel, um die visuelle Hilfe richtig zu positionieren:

{{ demo('tutorials/physics/DominoesDemo', '3c5fad40d3031ce6de9719deb41bf52f1a774022', 'L98-L107') }}

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

{{ demo('tutorials/physics/DominoesDemo', '3c5fad40d3031ce6de9719deb41bf52f1a774022', 'L110-L114') }}

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
