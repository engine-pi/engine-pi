[![Maven Central](https://img.shields.io/maven-central/v/de.pirckheimer-gymnasium/engine-pi.svg?style=flat)](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)
[![javadoc](https://javadoc.io/badge2/de.pirckheimer-gymnasium/engine-pi/javadoc.svg)](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi)

# Über diese Game Engine

![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/logo/logo.svg)

Die [Engine Pi](https://github.com/engine-pi/engine-pi) ist eine
einsteigerfreundliche Java-Game-Engine für den Informatik-Unterricht an
allgemeinbildenden Schulen mit [deutscher
Dokumentation](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi).

Die Engine Pi ist eine Fortführung und Erweiterung (Fork) der
[Engine-Alpha](https://github.com/engine-alpha/engine-alpha) von [Michael
Andonie](https://github.com/andonie) und [Niklas
Keller](https://github.com/kelunik) und zwar ein Fork der [Core Engine
v4.x](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha). Die
Engine Alpha wird leider offensichtlich nicht mehr weiterentwickelt. Die
[Engine-Alpha-Edu](https://github.com/engine-alpha/engine-alpha/tree/4.x/engine-alpha-edu)
Version mit deutschen Java Bezeichnern wurde nicht geforkt.

Da die [Engine-Alpha](https://github.com/engine-alpha/engine-alpha) momentan keine
Audio-Wiedergabe unterstützt, wurde der Audio-Code der
[LITIENGINE](https://github.com/gurkenlabs/litiengine) in die Engine Pi übernommen.
Die LITIENGINE ist eine Java-2D-Game-Engine der bayerischen Entwickler
[Steffen Wilke](https://github.com/steffen-wilke) und
[Matthias Wilke](https://github.com/nightm4re94). Neben der Sound-Engine kommen
viele Klassen zur Resourcen-Verwaltung, einige Hilfsklassen sowie das
Tweening-Paket aus der LITIENGINE in der Engine Pi zum Einsatz.

Diese README-Datei verwendet Dokumentationen, Tutorials und Bilder aus dem
[Engine Alpha Wiki](https://engine-alpha.org), die unter der
[Creative Commons „Namensnennung, Weitergabe unter gleichen Bedingungen“](https://creativecommons.org/licenses/by-sa/3.0/)
Lizenz stehen.

## Verwendung

Im Gegensatz zur Engine Alpha ist die
[Engine Pi](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)
über das wichtigste Repository für Java-Projekte das sogenannte
[Maven Central Repository](https://central.sonatype.com) abrufbar.

### BlueJ-Projekt

Auf [Github Releases](https://github.com/engine-pi/engine-pi/releases) gehen und die aktuelle Version `engine-pi-<version>.jar` herunterladen (z. B. [engine-pi-0.41.0.jar](https://github.com/engine-pi/engine-pi/releases/download/v0.41.0/engine-pi-0.41.0.jar)),
einen `+libs` Ordner erstellen und die JAR-Datei hineinkopieren.

### Maven-Projekt

https://github.com/engine-pi/maven-boilerplate

In der Projekt-Datei `pom.xml` ist die Engine Pi als
Abhängigkeit (`dependency`) hinterlegt.

```xml
<project>
  <dependencies>
    <dependency>
      <groupId>de.pirckheimer-gymnasium</groupId>
      <artifactId>engine-pi</artifactId>
      <version>0.41.0</version>
    </dependency>
  </dependencies>
</project>
```

## Koordinatensystem

Das Koordinatensystem ist mittig zentriert. Die x-Achse zeigt wie im
Mathematikunterricht nach rechts und die y-Achse nach oben. 1 Längeneinheit
entspricht 1 Meter. Die verwendete Physik-Engine rechnet intern mit Einheiten
aus der realen Welt, deshalb bietet sich Meter als Maßheit für das
Koordinatensystem an.[^engine-alpha-wiki:zeichnen-grafikfenster]

[^engine-alpha-wiki:zeichnen-grafikfenster]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/v4.x/Das_Grafikfenster#Zeichnen_im_Grafikfenster

## Figur (Actor)

Eine Figur (engl. Actor) ist ein grafisches Objekt, das sich bewegt bzw.
das bewegt werden kann. In der Engine Pi gibt es eine Vielzahl
verschiedener Figurenarten (z. B.
[Image](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Image.html),
[Text](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Text),
[Rectangle](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Rectangle),
[Circle](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Circle)). Alle
diese Spezialisierungen sind abgeleitet von der Oberklasse
[Actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Actor.html)
Die API-Dokumentation des Pakets
[pi.actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/package-summary.html)
listet alle verfügbaren Actor-Klassen auf.

Nachdem eine Figur erzeugt und zur Szene hinzugefügt wurde, befindet sie
sich an der Koordinate (0|0), d. h. die linke untere Ecke der Figur -
ihr Ankerpunkt - liegt an dem Punkt im Koordinatensystem, das 0 sowohl
für den x- als auch den y-Wert der Koordinate hat.


### Voreingestellte Tastenkürzel und Steuerungsmöglichkeiten

Die Engine registriert im Auslieferungszustand einige wenige [grundlegenden
Maus- und
Tastatur-Steuermöglichkeiten](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/event/DefaultControl.html).

Diese sind hoffentlich beim Entwickeln hilfreich. Mit den statischen Methoden
[Game.removeDefaultControl()](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/Game.html#removeDefaultControl()>)
können diese Kürzel entfernt oder mit
[Game.setDefaultControl(DefaultControl)](<https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/Game.html#setDefaultControl(pi.event.DefaultListener)>)
neue Kürzel gesetzt werden.

- `ESCAPE` zum Schließen des Fensters.
- `ALT + a` zum An- und Abschalten der Figuren-Zeichenroutine (Es
  werden nur die Umrisse gezeichnet, nicht die Füllung).
- `ALT + d` zum An- und Abschalten des Debug-Modus.
- `ALT + p` zum Ein- und Ausblenden der Figuren-Positionen (sehr
  ressourcenintensiv).
- `ALT + s` zum Speichern eines Bildschirmfotos (unter `~/engine-pi`).
- `ALT + PLUS` Hineinzoomen.
- `ALT + MINUS` Herauszoomen.
- `ALT + SHIFT + PLUS` schnelles Hineinzoomen.
- `ALT + SHIFT + MINUS` schnelles Herauszoomen.
- `ALT + Pfeiltasten` zum Bewegen der Kamera.
- `ALT + Mausrad` zum Einstellen des Zoomfaktors.


### Schwerkraft

Quellcode: [demos/physics/single_aspects/GravityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/GravityDemo.java)

```java
public class GravityDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle rectangle;

    public GravityDemo()
    {
        setGravity(0, -9.81);
        createBorder(-5, 4, false);
        createBorder(-5, -5, false);
        createBorder(-5, -5, true);
        createBorder(4, -5, true);
        rectangle = new Rectangle(1, 1);
        rectangle.makeDynamic();
        add(rectangle);
    }

    private Rectangle createBorder(double x, double y, boolean vertical)
    {
        Rectangle rectangle = !vertical ? new Rectangle(10, 1)
                : new Rectangle(1, 10);
        rectangle.setPosition(x, y);
        rectangle.makeStatic();
        add(rectangle);
        return rectangle;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
        case KeyEvent.VK_UP -> setGravity(0, 9.81);
        case KeyEvent.VK_DOWN -> setGravity(0, -9.81);
        case KeyEvent.VK_RIGHT -> setGravity(9.81, 0);
        case KeyEvent.VK_LEFT -> setGravity(-9.81, 0);
        }
    }
}
```

### Elastizität

Wir setzen die Elastizität auf 0, damit beim ersten Kreis mit der
Stoßzahl 0 demonstriert werden kann, dass dieser nicht abprallt.

Quellcode: [demos/physics/single_aspects/ElasticityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/ElasticityDemo.java)

```java
public class ElasticityDemo extends Scene
{
    private final Rectangle ground;

    public ElasticityDemo()
    {
        getCamera().setZoom(20);
        // Ein Reckteck als Boden, auf dem die Kreise abprallen.
        ground = new Rectangle(24, 1);
        ground.setPosition(-12, -16);
        ground.setElasticity(0);
        ground.makeStatic();
        setGravity(0, -9.81);
        add(ground);
        double elasticity = 0;
        for (double x = -11.5; x < 12; x += 2)
        {
            createCircle(x, elasticity);
            elasticity += 0.1;
        }
    }

    private void createCircle(double x, double elasticity)
    {
        Circle circle = new Circle(1);
        add(circle);
        circle.setElasticity(elasticity);
        circle.setPosition(x, 5);
        circle.makeDynamic();
        // Eine Beschriftung mit der Stoßzahl unterhalb des Kollisionsrechtecks
        DecimalFormat df = new DecimalFormat("0.00");
        Text label = new Text(df.format(elasticity), 0.8);
        label.setPosition(x, -17);
        label.makeStatic();
        add(label);
    }

}
```

### Dichte

Quellcode: [demos/physics/single_aspects/DensityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/DensityDemo.java)

```java
public class DensityDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle ground;

    private final Circle[] circles;

    private final Text[] densityLables;

    public DensityDemo()
    {
        circles = new Circle[3];
        densityLables = new Text[3];
        int density = 10;
        int x = -5;
        for (int i = 0; i < 3; i++)
        {
            circles[i] = createCircle(x, density);
            densityLables[i] = createDensityLables(x, density);
            x += 5;
            density += 10;
        }
        setGravity(0, -9.81);
        ground = new Rectangle(20, 1);
        ground.setPosition(-10, -5);
        ground.makeStatic();
        add(ground);
    }

    private Circle createCircle(double x, double density)
    {
        Circle circle = new Circle(1);
        circle.setPosition(x, 5);
        circle.setDensity(density);
        circle.makeDynamic();
        add(circle);
        return circle;
    }

    private Text createDensityLables(int x, int density)
    {
        Text text = new Text(density + "", 1);
        text.setPosition(x, -7);
        text.makeStatic();
        add(text);
        return text;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        for (Circle circle : circles)
        {
            circle.applyImpulse(0, 100);
        }
    }
}
```

## Farben

Die Klassen mit Farbbezug:

- [resources.ColorContainer](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/resources/ColorContainer.html)
- [resources.ColorScheme](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/resources/ColorScheme.html)
- [resources.ColorSchemeSelection](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/resources/ColorSchemeSelection.html)
- [resources.NamedColor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/resources/NamedColor.html)
- [Resources.COLORS](https://javadoc.io/static/de.pirckheimer-gymnasium/engine-pi/0.31.0/de/pirckheimer_gymnasium/engine_pi/Resources.html#COLORS)

In der ersten Reihe sind mehrere Bilder zu sehen, in der
Reihe unterhalb Rechtecke mit der Durchschnittsfarbe der Bilder, in der letzten
Reihe die Komplementärfarben der entsprechenden Bilder.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/color-complementary/Images_derived_complementary-color.png)

Quellcode: [demos/actor/ImageAverageColorDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/actor/ImageAverageColorDemo.java)

```java
import pi.Game;
import pi.Scene;

public class ImageAverageColorDemo extends Scene
{
    public ImageAverageColorDemo()
    {
        getCamera().setMeter(90);
        double x = -4;
        for (String filepath : new String[] { "car/background-color-grass.png",
                "car/wheel-back.png", "car/truck-240px.png",
                "dude/background/snow.png", "dude/box/obj_box001.png",
                "dude/moon.png" })
        {
            createImageWithAverageColor(filepath, x);
            x = x + 1.2;
        }
    }

    private void createImageWithAverageColor(String filepath, double x)
    {
        var image = createImage(filepath, 1, 1).setPosition(x, 0);
        createRectangle(1.0, 1.0).setPosition(x, -1.2)
                .setColor(image.getColor());
        createRectangle(1.0, 0.5).setPosition(x, -1.9)
                .setColor(image.getComplementaryColor());
    }

    public static void main(String[] args)
    {
        Game.start(new ImageAverageColorDemo());
    }
}
```

`ALT + d` aktiviert den Debug-Modus: Die Bilder werden von Umrissen in den Komplementärfarben umrahmt.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/color-complementary/Images_shapes.png)

`Alt + a` blendet die Figurenfüllungen aus. Es sind nur noch die Umrisse zu sehen.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/color-complementary/Shapes-only.png)

## Entwicklung

### Aufbau des Projekts

Die Engine Pi nutzt das Build Tool [maven](https://maven.apache.org/). Das
Projekt ist als sogenanntes [Multiple
Modules](https://maven.apache.org/guides/mini/guide-multiple-modules.html)-Projekt
strukturiert.
Die eigentliche Engine befindet sich im Ordner `./modules/engine`.

| Pfad                          | artefactId              |
| ----------------------------- | ----------------------- |
| ./                            | engine-pi-meta          |
| ./modules/engine              | engine-pi               |
| ./modules/demos               | engine-pi-demos         |
| ./modules/cli                 | engine-pi-cli           |
| ./modules/games/blockly-robot | engine-pi-blockly-robot |
| ./modules/games/pacman        | engine-pi-pacman        |
| ./modules/games/tetris        | engine-pi-tetris        |
| ./modules/build-tools         | engine-pi-build-tools   |

### Eine neue Version veröffentlichen

Es wird Semantic Versioning verwendet.

Die Versionsnummer der Engine setzen.

```
mvn versions:set -f modules/engine/pom.xml
```

Die Versionsnummer des Meta-Projekts setzen.

```
mvn versions:set
```

Die Datei `CHANGELOG.md` bezieht sich auf die Engine also auf `./modules/engine`.

### javadoc

Im Module `build-tools` befindet sich eine CSS-Datei, die verwendet wird, um das
Aussehen der Dokumentation mit den Engine-Pi-Farbe (Gnome-Farben) und der
Engine-Pi-Standard-Schrift (Cantarell) anzupassen.

#### Eigene CSS-Klassen

`.development-note`

#### Abfolge der Tags

Wir folgenden der Empfehlung, die im Artikel [How to Write Doc Comments for the
Javadoc
Tool](https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html#orderoftags)
von [Oracle](https://www.oracle.com) dargestellt wird.

1. `@author` (classes and interfaces only, required)
1. `@version` (classes and interfaces only, required. See footnote 1)
1. `@param` (methods and constructors only)
1. `@return` (methods only)
1. `@throws` (a synonym added in Javadoc 1.2 `@exceptions`)
1. `@see`
1. `@since`
1. `@deprecated` (see How and When To Deprecate APIs)

### Code Beispiele

Einzeilig

```java
/**
 * {@code System.out.println("Lorem ipsum");}
 */
```

Mehrzeilig:

```java
/**
 * <pre>
 * {@code
 * Set<String> s;
 * System.out.println(s);
 * }
 * </pre>
 */
```

### File Link zu den Demos:

```java
// Go to file: file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java
```

### Deutsche Übersetzungen von englischen Klassennamen

| englisch                         | deutsch                                                |
| -------------------------------- | ------------------------------------------------------ |
| AABB (axis-aligned bounding box) | achsenparallelen Begrenzungsrahmen                     |
| Actor                            | Figur                                                  |
| BodyType                         | Verhalten einer Figur in der physikalischen Simulation |
| Bounds                           | Schranken, Abgrenzung                                  |
| DistanceJoint                    | Stabverbindung                                         |
| Fixture                          | Halterung, Kollisionsform                              |
| Frame                            | Einzelbild                                             |
| Handler                          | Steuerungsklasse                                       |
| Joint                            | Verbindung                                             |
| Layer                            | Ebene                                                  |
| Listener                         | Beobachter                                             |
| Loop                             | Schleife                                               |
| Offset                           | Verzug                                                 |
| PrismaticJoint                   | Federverbindung                                        |
| Rectangle                        | Rechteck                                               |
| RevoluteJoint                    | Gelenkverbindung                                       |
| Rigid Body                       | Starrer Körper                                         |
| RopeJoint                        | Seilverbindung                                         |
| Scene                            | Szene                                                  |
| Shape                            | Umriss                                                 |
| Square                           | Quadrat                                                |
| TurboFire                        | Dauerfeuer                                             |
| WeldJoint                        | Schweißnaht                                            |

### Kommentar mit Lizenzhinweis

```java
/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
```

### Anordnung der Konstruktoren

Oben stehen die Konstruktoren mit wenigen Parametern unten die mit vielen.
