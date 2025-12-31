# `Scene` (Szene)[^engine-alpha-wiki:scenes]

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

{{ image('docs/scenes/Scene_Demonstration.png') }}
/// caption
Szenen in der Engine: Beispiel mit Pausenmenü
///

## Ein Pausenmenü

Das folgende Beispiel enthält zwei Szenen: Eine einfache Animation und ein
Pausenmenü. Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück

Quellcode: [demos/scenes/MainScene.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/scenes/MainScene.java)

{{ demo('tutorials/scenes/MainScene') }}

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

{{ image('docs/scenes/Tutorial_Pause_Menu.gif') }}
/// caption
Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück
///

## Die zwei Szenen

Die Hauptszene ist `MainScene`. Hier könnte ein Game Loop für ein
Spiel stattfinden. Dieses Tutorial zeigt stattdessen eine kleine Animation.

Die zweite Szene heißt `PauseMenu`. In ihr gibt es eine Textbotschaft und einen
kleinen Knopf, um das Menü wieder zu verlassen.

{{ demo('tutorials/scenes/MainScene', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L36-L38') }}

```java
public class MainScene extends Scene
{
    private Scene pauseMenu;
    //...
}
```

{{ demo('tutorials/scenes/MainScene', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L70-L72') }}

```java
private class PauseMenu extends Scene
{
    private Scene mainScene;
    //...
}
```

Die Haupt-Szene wird per Knopfdruck pausiert. Wird der P-Knopf gedrückt, wird
die Transition ausgeführt:

{{ demo('tutorials/scenes/MainScene', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L65-L68') }}

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

{{ demo('tutorials/scenes/MainScene', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L102-L108') }}

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

## Kosmetische Kleinigkeiten

In der Hauptszene findet eine interpolierte Rotationsanimation statt. Diese
rotiert ein oranges Rechteck wiederholend um den Punkt `(0|0)`. Eine volle
Rotation im Uhrzeigersinn dauert `8` Sekunden.

{{ demo('tutorials/scenes/MainScene', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L43-L49') }}

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

{{ demo('tutorials/scenes/MainScene', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L111-L121') }}

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

## Anmerkungen und Beobachtungen

Die Kreisrotation in der Hauptszene geht nicht weiter, solange das Pausenmenü
die aktive Szene ist. Dies liegt daran, dass die Animation als
`FrameUpdateListener` in der Hauptszene angemeldet wurde
(`addFrameUpdateListener(animation)`). Alle Beobachter einer Szene können nur
dann aufgerufen werden, wenn die Szene aktiv ist.
Deshalb lässt sich das Pausenmenü nicht durch drücken von P beenden. Der
`KeyStrokeListener`, der bei Druck von P zum Pausenmenü wechselt, ist in der
Hauptszene angemeldet.
