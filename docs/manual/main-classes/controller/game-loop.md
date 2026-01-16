# Game Loop[^engine-alpha-wiki:game-loop]

[^engine-alpha-wiki:game-loop]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Game_Loop

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/assets/docs/main-classes/controller/game-loop/ -->

<!-- ## How-To Engine Code: Der Game Loop -->

Das Snake-Spiel ist ein erstes interaktives Spiel. Es nutzt den Game Loop der
Engine. Dieser funktioniert folgendermaßen:

{{ image('docs/main-classes/controller/game-loop/GameLoop.png', 'Der Engine Pi Game Loop') }}

Ein Film besteht aus 24 bis 60 Bildern pro Sekunde, die schnell nacheinander
abgespielt werden, um die Illusion von Bewegung zu erzeugen. Ähnlich werden bei
(den meisten) Computerspielen 30 bis 60 Bilder pro Sekunde in Echtzeit
gerendert, um gleiche Illusion von Bewegung zu erzeugen. Nach jedem Bild
berechnet die Engine die nächsten Schritte intern und gibt die relevanten
Ereignisse (Tastendruck, Kollision, Frame-Update) an die entsprechenden Listener
weiter.

Alle Spiel-Logik ist also in den Listener-Interfaces geschrieben. Guter
Engine-Code ist verpackt in Interfaces nach Spiel-Logik.

## Snake ohne Körper

Das folgende Program implementiert ein einfaches Snake-Spiel mit einem
Steuerbaren Kreis und dem Ziel, Goodies zu sammeln.

{{ image('docs/main-classes/controller/game-loop/Snake_Minimal.gif', 'Das Snake-Spiel: Der Kreis jagt die willkürlich auftauchenden Texte') }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/game_loop/SnakeMinimal.java -->

{{ code('docs/main_classes/controller/game_loop/SnakeMinimal', 38) }}

## Snake: Frame-Weise Bewegung

Die Snake ist der spielbare Charakter. Sie soll sich jeden Frame in eine der
vier Himmelsrichtungen bewegen.

Die Bewegung der Snake soll möglichst flüssig sein. Daher wird die Bewegung in
jedem einzelnen Frame ausgeführt, um maximal sauber auszusehen. Dazu
implementiert die Snake das Engine-Interface FrameUpdateListener, um in jedem
Frame seine Bewegungslogik auszuführen.

Hierzu kennt die Snake ihre aktuelle Geschwindigkeit als gerichteten Vektor (in
Meter/Sekunde). Ein Frame ist deutlich kürzer als eine Sekunde. Mathematik zur
Hilfe! `v = s/t` und damit `s = v\*t`. Jeden Frame erhält die Snake die
tatsächlich vergangene Zeit `t` seit dem letzten Frame-Update und verrechnet
diese mit ihrer aktuellen Geschwindigkeit `v`:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/game_loop/SnakeMinimal.java -->

{{ code('docs/main_classes/controller/game_loop/SnakeMinimal', 86, 90) }}

## Bewegungsgeschwindigkeit festlegen

Was die tatsächliche Bewegungsgeschwindigkeit der Snake ist, hängt davon ab,
welche Taste der Nutzer zuletzt runtergedrückt hat und ist in der Snake über
`KeyStrokeListener` gelöst wie im vorigen Tutorial:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/game_loop/SnakeMinimal.java -->

{{ code('docs/main_classes/controller/game_loop/SnakeMinimal', 93, 114) }}

## Auf Kollisionen reagieren: Goodies

Die Schlange bewegt sich. Als nächstes braucht sie ein Ziel, auf das sie sich
zubewegt. Hierzu schreiben wir eine Klasse für Goodies.

Ein Goodie wartet nur darauf, gegessen zu werden. Damit nicht jeden Frame "von
Hand" überprüft werden muss, ob die Schlange das Goodie berührt, lässt sich das
ebenfalls über ein Listener-Interface lösen: `CollisionListener`. Das
Interface ist mit Java Generics umgesetzt, daher die spitzen Klammern. Einige
Vorteile hiervon kannst du in der Dokumentation durchstöbern.

Wenn das Goodie mit der Schlange kollidiert, so soll der Punktestand geändert,
das Goodie entfernt, und ein neues Goodie platziert werden.

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/game_loop/SnakeMinimal.java -->

{{ code('docs/main_classes/controller/game_loop/SnakeMinimal', 125, 130) }}

In der `placeRandomGoodie()`-Methode wird ein neues Goodie erstellt und mit
`Random` an einer zufälligen Stelle auf dem Spielfeld platziert. Weil das
Goodie nur auf Kollision mit der Schlange reagieren soll (und nicht z.B. auf
Kollision mit dem "Score"-Text), wird es abschließend als Collision-Listener
spezifisch mit der Schlange angemeldet:

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/game_loop/SnakeMinimal.java -->

{{ code('docs/main_classes/controller/game_loop/SnakeMinimal', 65, 73) }}

## Anregung zum Experimentieren

{{ image('docs/main-classes/controller/game-loop/Snake_Advanced.gif', 'Eine Snake, die mit jedem Pickup wächst') }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/controller/game_loop/SnakeAdvanced.java -->

{{ code('docs/main_classes/controller/game_loop/SnakeAdvanced', 37) }}

<!-- - Deadly Pickups: Es gibt noch keine Gefahr für die Schlange. Ein giftiges
  Pick-Up tötet die Schlange und beendet das Spiel (oder zieht der Schlange
  einen von mehreren Hit Points ab).
- Smoother Movement: Die aktuelle Implementierung für die Bewegung der Schlange
  ist sehr steif und die Schlange kann nicht stehen bleiben. Vielleicht möchtest
  du dem Spieler mehr Kontrolle über die Schlange geben: Statt des
  KeyStrokeListener-Interfaces, kann die Schlange in ihrer
  onFrameUpdate(float)-Methode abfragen, ob gerade der W/A/S/D-Key
  heruntergedrückt ist und sich entsprechend dessen weiter bewegen. Tipp: Die
  Methode ea.Game.isKeyPressed(int keycode) ist hierfür hilfreich.
- Escalating Difficulty: Je mehr Pick-Ups gesammelt wurden (und damit desto
  höher der Score), desto schneller bewegt sich die Schlange. Actual Snake: Wenn
  du Lust auf eine Herausforderung hast, kannst du aus dem Spiel ein echtes
  Snake machen: Beim aufnehmen eines Pick-Ups wird die Schlange um ein Glied
  länger. Die Glieder bewegen sich versetzt mit der Schlange weiter. Wenn die
  Schlange sich selbst berührt, ist das Spiel beendet. -->
