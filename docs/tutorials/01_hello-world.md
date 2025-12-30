# Hello World[^engine-alpha-wiki:hello-world]

[^engine-alpha-wiki:hello-world]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Hello_World

## Schritt 1: Grundlegender Aufbau

Das grundlegendste *Hello World* sieht so aus:

{{ image('docs/tutorials/hello-world/v1_basic.png') }}
/// caption
Das (noch wenig spannende) Ergebnis des Codes
///

{{ demo('tutorials/hello_world/HelloWorldVersion1', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L27-L42') }}

```java
public class HelloWorldVersion1 extends Scene
{
    public HelloWorldVersion1()
    {
        Text helloWorld = new Text("Hello, World!", 2);
        helloWorld.setColor("white");
        helloWorld.setCenter(0, 1);
        add(helloWorld);
        Game.debug();
    }

    public static void main(String[] args)
    {
        Game.start(new HelloWorldVersion1(), 400, 300);
    }
}
```

### Szene

Die `HelloWorldVersion1`-Klasse leitet sich aus der Klasse `Scene` der Engine
ab. Szenen in der Engine sind eigenständige Spielbereiche. Jede Szene hat ihre
eigenen grafischen (und sonstige) Objekte; Szenes werden unabhängig voneinander
berechnet. Ein Spiel besteht aus einer oder mehreren Szenen und wir erstellen
eine Szene, in der „Hello World“ dargestellt werden soll:

```java
public class HelloWorldVersion1 extends Scene
```

### Text

Wir wollen den Text *„Hello, World!“* darstellen. Die Klasse `Text` ist dafür
zuständig. Ein Text mit Inhalt *„Hello, World!“* und Höhe 2 wird erstellt:

```java
Text helloWorld = new Text("Hello, World!", 2);
```

Der Text wird an Position (0|1) zentriert:

```java
helloWorld.setCenter(0, 1);
```

Der Text wird an der Szene angemeldet:

```java
add(helloWorld);
```

Der letzte Schritt ist nötig, damit das Objekt auch sichtbar wird. In jeder
Szene werden nur die Objekte gerendert, die auch an der Szene angemeldet
sind.

### Debug-Modus

{{ image('docs/tutorials/hello-world/v1_debug.png') }}
/// caption
Der Debug-Modus zeigt das Koordinatensystem und weitere hilfreiche Informationen.
///

Um Überblick zu behalten und die Grafikebene zu verstehen, ist der Debug-Modus
der Engine hilfreich. Diese Zeile aktiviert den Debug Modus:

```java
Game.debug();
```

Die Klasse `Game` enthält neben Debug-Modus weitere Features, die die
Spielumgebung global betreffen.

Die Klasse `Game` kontrolliert auch den Spielstart. Dazu muss lediglich die
(zuerst) darzustellende Szene angegeben werden, sowie die Fenstermaße (in diesem
Fall 400 px Breite und 300 px Höhe):

```java
Game.start(400, 300, new HelloWorldVersion1());
```

## Schritt 2: Geometrie und Farbe

Beim nächste Codebeispiel handelt es sich um eine Erweiterung der Version 1 um
geometrischen Figuren und Farbe.

{{ image('docs/tutorials/hello-world/v2_geometry.png') }}
/// caption
Jetzt mit mehr Farbe und geometrischen Figuren
///

{{ demo('tutorials/hello_world/HelloWorldVersion2', 'a010897d03ba56fa142466a40f00a7e6f12a71d7', 'L23-L55') }}

```java
import pi.Game;
import pi.Scene;
import pi.actor.Circle;
import pi.actor.Rectangle;
import pi.actor.Text;

public class HelloWorldVersion2 extends Scene
{
    public HelloWorldVersion2()
    {
        Text helloworld = new Text("Hello, World!", 2);
        helloworld.setCenter(0, 1);
        add(helloworld);
        helloworld.setColor("black");
        // Ein grünes Rechteck als Hintergrund
        Rectangle background = new Rectangle(12, 3);
        background.setColor("green");
        background.setCenter(0, 1);
        background.setLayerPosition(-1);
        // Ein blauer Kreis
        Circle circle = new Circle(8);
        circle.setColor("blue");
        circle.setCenter(0, 1);
        circle.setLayerPosition(-2);
        add(background, circle);
        getCamera().setMeter(20);
    }

    public static void main(String[] args)
    {
        Game.start(400, 300, new HelloWorldVersion2());
    }
}
```

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
background.setColor("green");
circle.setColor("blue");
```

### Ebenen-Position

{{ image('docs/tutorials/hello-world/v2_nolayer.png') }}
/// caption
So würde das Bild aussehen, wenn die Ebenen-Position nicht explizit gesetzt
werden würde.
///


Wir wollen explizit, dass der Text vor allen anderen Objekten dargestellt wird.
Außerdem soll der Kreis noch hinter dem Rechteck sein. Um das sicherzustellen,
kann die Ebenen-Position explizit angegeben werden: Je höher die
Ebenen-Position, desto weiter im Vordergrund ist das Objekt.

```java
background.setLayerPosition(-1);
circle.setLayerPosition(-2);
```
