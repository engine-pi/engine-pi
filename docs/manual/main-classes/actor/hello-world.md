# Tutorial „Hello World“[^engine-alpha-wiki:hello-world]

[^engine-alpha-wiki:hello-world]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Hello_World

## Schritt 1: Grundlegender Aufbau

Das grundlegendste _Hello World_ sieht so aus:

{{ image('docs/tutorials/hello-world/v1_basic.png') }}
/// caption
Das (noch wenig spannende) Ergebnis des Codes
///

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/hello_world/HelloWorldVersion1.java -->

{{ code('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 25) }}

<!-- ```java
import pi.Controller;
import pi.Scene;
import pi.Text;

public class HelloWorldVersion1 extends Scene
{
    public HelloWorldVersion1()
    {
        Text helloWorld = new Text("Hello, World!", 2);
        helloWorld.color("white");
        helloWorld.center(0, 1);
        add(helloWorld);
        Controller.debug();
    }

    public static void main(String[] args)
    {
        Controller.start(new HelloWorldVersion1(), 400, 300);
    }
}
``` -->

### Szene

Die {{ class('demos.docs.main_classes.actor.hello_world.HelloWorldVersion1') }}-Klasse leitet sich aus der Klasse {{ class('pi.Scene') }} der Engine
ab. Szenen in der Engine sind eigenständige Spielbereiche. Jede Szene hat ihre
eigenen grafischen (und sonstige) Objekte; Szenes werden unabhängig voneinander
berechnet. Ein Spiel besteht aus einer oder mehreren Szenen und wir erstellen
eine Szene, in der „Hello World“ dargestellt werden soll:

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 29) }}

<!--```java
public class HelloWorldVersion1 extends Scene
```-->

### Text

Wir wollen den Text _„Hello, World!“_ darstellen. Die Klasse {{ class('pi.Text')
}} ist dafür zuständig. Ein Text mit Inhalt _„Hello, World!“_ und Höhe 2 wird
erstellt:

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 33) }}

<!-- ```java
Text helloWorld = new Text("Hello, World!", 2);
``` -->

Der Text wird an Position `(0|1)` zentriert:

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 35) }}

<!-- ```java
        helloWorld.center(0, 1);
``` -->

Der Text wird an der Szene angemeldet:

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 36) }}

<!-- ```java
add(helloWorld);
``` -->

Der letzte Schritt ist nötig, damit das Objekt auch sichtbar wird. In jeder
Szene werden nur die Objekte gerendert, die auch an der Szene angemeldet
sind.

### Debug-Modus

{{ image('docs/tutorials/hello-world/v1_debug.png', 'Der Debug-Modus zeigt das Koordinatensystem und weitere hilfreiche Informationen.') }}

Um Überblick zu behalten und die Grafikebene zu verstehen, ist der Debug-Modus
der Engine hilfreich. Diese Zeile aktiviert den Debug Modus:


{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 17) }}

<!-- ```java
        Controller.debug();
``` -->

Die Klasse {{ class('pi.Controller') }} enthält neben Debug-Modus weitere Features, die die
Spielumgebung global betreffen.

Da wir die Figuren „per Hand“, d.h. mithilfe der Methode {{ method('pi.Scene',
'add(pi.actor.Actor...)', 'add()') }} zur Szene hinzugefügt haben, muss der Instant-Modus
deaktiviert werden.

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 42) }}

<!-- ```java
        Controller.instantMode(false);
``` -->

Die Klasse {{ class('pi.Controller') }} kontrolliert auch den Spielstart. Dazu muss lediglich die
(zuerst) darzustellende Szene angegeben werden, sowie die Fenstermaße (in diesem
Fall `400` Pixel Breite und `300` Pixel Höhe):

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 43) }}


<!-- ```java
        Controller.start(new HelloWorldVersion1(), 400, 300);
``` -->

## Schritt 2: Geometrie und Farbe

Beim nächste Codebeispiel handelt es sich um eine Erweiterung der Version 1 um
geometrischen Figuren und Farbe.

{{ image('docs/tutorials/hello-world/v2_geometry.png', 'Jetzt mit mehr Farbe und geometrischen Figuren') }}

{{ code('docs/main_classes/actor/hello_world/HelloWorldVersion2.java', 25) }}

<!-- ```java
import pi.Controller;
import pi.Scene;
import pi.Circle;
import pi.Rectangle;
import pi.Text;

public class HelloWorldVersion2 extends Scene
{
    public HelloWorldVersion2()
    {
        Text helloworld = new Text("Hello, World!", 2);
        helloworld.center(0, 1);
        add(helloworld);
        helloworld.color("black");
        // Ein grünes Rechteck als Hintergrund
        Rectangle background = new Rectangle(12, 3);
        background.color("green");
        background.center(0, 1);
        background.layerPosition(-1);
        // Ein blauer Kreis
        Circle circle = new Circle(8);
        circle.color("blue");
        circle.center(0, 1);
        circle.layerPosition(-2);
        add(background, circle);
        camera().meter(20);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new HelloWorldVersion2(), 400, 300);
    }
}
``` -->

### Geometrische Figuren

Die Engine unterstützt diverse geometrische Figuren. Dazu gehören Rechtecke und
Kreise. Der Code erstellt ein Rechteck mit Breite 12 und Höhe 3 sowie einen
Kreis mit Durchmesser 8.

```java
Rectangle background = new Rectangle(12, 3);
Circle circle = new Circle(8);
```

### Farbe

Einige Objekte in der Engine können beliebig gefärbt werden. Text und
geometrische Figuren gehören dazu. Mit `setColor(Color)` kann die Farbe als
AWT-Color-Objekt übergeben werden oder einfacher als
[Zeichenkette](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/resources/ColorContainer.html):

```java
background.color("green");
circle.color("blue");
```

### Ebenen-Position

{{ image('docs/tutorials/hello-world/v2_nolayer.png', 'So würde das Bild aussehen, wenn die Ebenen-Position nicht explizit gesetzt werden würde.') }}

Wir wollen explizit, dass der Text vor allen anderen Objekten dargestellt wird.
Außerdem soll der Kreis noch hinter dem Rechteck sein. Um das sicherzustellen,
kann die Ebenen-Position explizit angegeben werden: Je höher die
Ebenen-Position, desto weiter im Vordergrund ist das Objekt.

```java
background.layerPosition(-1);
circle.layerPosition(-2);
```
