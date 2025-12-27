[![Maven Central](https://img.shields.io/maven-central/v/de.pirckheimer-gymnasium/engine-pi.svg?style=flat)](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)

Dokumentation: https://engine-pi.github.io/engine-pi

Javadocs: https://engine-pi.github.io/javadocs/pi/package-summary.html

# Über diese Game Engine

[![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/logo/logo.svg)](https://engine-pi.github.io/engine-pi)

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

## Entwicklung

### Aufbau des Projekts

Die Engine Pi nutzt das Build Tool [maven](https://maven.apache.org/). Das
Projekt ist als sogenanntes [Multiple
Modules](https://maven.apache.org/guides/mini/guide-multiple-modules.html)-Projekt
strukturiert.
Die eigentliche Engine befindet sich im Ordner `./subprojects/engine`.

| Pfad                          | artefactId              |
| ----------------------------- | ----------------------- |
| ./                            | engine-pi-meta          |
| ./subprojects/engine              | engine-pi               |
| ./subprojects/demos               | engine-pi-demos         |
| ./subprojects/cli                 | engine-pi-cli           |
| ./subprojects/games/blockly-robot | engine-pi-blockly-robot |
| ./subprojects/games/pacman        | engine-pi-pacman        |
| ./subprojects/games/tetris        | engine-pi-tetris        |
| ./subprojects/build-tools         | engine-pi-build-tools   |

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
