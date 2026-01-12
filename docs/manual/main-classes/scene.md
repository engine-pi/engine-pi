# `Scene` (Szene)[^engine-alpha-wiki:scenes]

[^engine-alpha-wiki:scenes]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Scenes

{{ drawio('Scene') }}

Ein Spiel hat oftmals mehrere verschiedene „Teile“, zwischen denen der Spieler
während des Spielens wechselt. Zum Beispiel gibt es neben der Hauptdarstellung,
Pausenmenüs, Inventare, Hauptmenüs, etc. Es wäre unnötig komplex, für den
Wechsel zwischen diesen Szenen stets alle grafischen Objekte zu zerstören und
wieder neu aufzubauen. Stattdessen werden alle grafischen Objekte in einer
`Scene` hinzugefügt. Dies passiert - wie in den vorigen Tutorials - über die
Methode `add(...)`.

Über die Klasse `Game` kann schnell zwischen Szenen gewechselt werden. Dazu gibt
es die Methode `Game.transitionToScene(Scene)`.

{{ image('docs/scenes/Scene_Demonstration.png', 'Szenen in der Engine: Beispiel mit Pausenmenü') }}

## Ein Pausenmenü

Das folgende Beispiel enthält zwei Szenen: Eine einfache Animation und ein
Pausenmenü. Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück

{{ image('docs/scenes/Tutorial_Pause_Menu.gif', 'Ein Wechsel zwischen Hauptszene zu Pausenmenü und wieder zurück') }}

## Die zwei Szenen

Die Hauptszene ist `MainScene`. Hier könnte ein Game Loop für ein
Spiel stattfinden. Dieses Tutorial zeigt stattdessen eine kleine Animation.

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/scene/tutorial/MainScene.java -->

{{ code('docs/main_classes/scene/tutorial/MainScene.java', 25) }}

Die zweite Szene heißt `PauseMenu`. In ihr gibt es eine Textbotschaft und einen
kleinen Knopf, um das Menü wieder zu verlassen.

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/scene/tutorial/PauseMenu.java -->

{{ code('docs/main_classes/scene/tutorial/PauseMenu.java', 25) }}

Die Haupt-Szene wird per Knopfdruck pausiert. Wird der P-Knopf gedrückt, wird
die Transition ausgeführt:

{{ code('docs/main_classes/scene/tutorial/MainScene.java', 64, 67) }}

<!-- ```java
    private void gotoPause()
    {
        Controller.transitionToScene(pauseMenu);
    }
``` -->

Das Pausenmenü wird statt mit Tastatur per Mausklick geschlossen. Im internen
Steuerelement `MenuItem` wird dafür die entsprechende Methode aufgerufen, wann
immer ein Mausklick auf dem Element landet - dies wird durch die Methode
`contains(Vector)` geprüft:

{{ code('docs/main_classes/scene/tutorial/MenuItem.java', 54, 61) }}

<!-- ```java
    @Override
    public void onMouseDown(Vector clickLoc, MouseButton mouseButton)
    {
        if (contains(clickLoc))
        {
            Controller.transitionToScene(mainScene);
        }
    }
``` -->

## Kosmetische Kleinigkeiten

In der Hauptszene findet eine interpolierte Rotationsanimation statt. Diese
rotiert ein oranges Rechteck wiederholend um den Punkt `(0|0)`. Eine volle
Rotation im Uhrzeigersinn dauert `8` Sekunden.

{{ code('docs/main_classes/scene/tutorial/MainScene.java', 42, 48) }}

<!-- ```java
        Rectangle toAnimate = new Rectangle(5, 2);
        toAnimate.center(0, -5);
        toAnimate.color("orange");
        CircleAnimation animation = new CircleAnimation(toAnimate,
                new Vector(0, 0), 8, true, true);
        addFrameUpdateListener(animation);
        add(toAnimate);
``` -->

Das Pausenmenü hat einen Hover-Effekt. Hierzu wird in jeden Einzelbild
überprüft, ob die Maus derzeit innerhalb des Steuerelementes liegt und abhängig
davon die Rechtecksfarbe ändert. Hierzu wird die Methode
`Game.getMousePositionInCurrentScene()` genutzt:

{{ code('docs/main_classes/scene/tutorial/MenuItem.java', 63, 74) }}

<!-- ```java
    @Override
    public void onFrameUpdate(double pastTime)
    {
        if (contains(Controller.mousePosition()))
        {
            color("blue");
        }
        else
        {
            color("blueGreen");
        }
    }
``` -->

Kompletter Code MenuItem

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/scene/tutorial/MenuItem.java -->

{{ code('docs/main_classes/scene/tutorial/MenuItem.java', 25) }}

## Anmerkungen und Beobachtungen

Die Kreisrotation in der Hauptszene geht nicht weiter, solange das Pausenmenü
die aktive Szene ist. Dies liegt daran, dass die Animation als
`FrameUpdateListener` in der Hauptszene angemeldet wurde
(`addFrameUpdateListener(animation)`). Alle Beobachter einer Szene können nur
dann aufgerufen werden, wenn die Szene aktiv ist.
Deshalb lässt sich das Pausenmenü nicht durch drücken von P beenden. Der
`KeyStrokeListener`, der bei Druck von P zum Pausenmenü wechselt, ist in der
Hauptszene angemeldet.
