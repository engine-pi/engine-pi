# `javadoc` (API-Dokumentation)

Im Subprojekt `build-tools` befindet sich eine {{
repo_link('subprojects/build-tools/src/main/resources/javadoc-stylesheet.css',
'CSS-Datei') }}, die verwendet wird, um das Aussehen der Dokumentation mit den
Engine-Pi-Farbe (Gnome-Farben) und der Engine-Pi-Standard-Schrift (Cantarell)
anzupassen.

<!-- /data/school/repos/inf/java/engine-pi/subprojects/build-tools/README.md -->

Mit folgender Konfiguration in der Datei pom.xml kann mit Hilfe von Maven
javadoc angewiesen werden, diese CSS-Datei zu verwenden:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-javadoc-plugin</artifactId>
            <version>3.12.0</version>
            <configuration>
                <!-- https://maven.apache.org/plugins/maven-javadoc-plugin/examples/stylesheet-configuration.html -->
                <stylesheetfile>javadoc-stylesheet.css</stylesheetfile>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>de.pirckheimer-gymnasium</groupId>
                    <artifactId>engine-pi-build-tools</artifactId>
                    <version>0.2.0</version>
                </dependency>
            </dependencies>
            <executions>
                <execution>
                    <id>attach-javadocs</id>
                    <goals>
                        <goal>jar</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

## Eigene CSS-Klassen

`.development-note`

## Abfolge der Tags

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

## Code Beispiele

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

## Privater Konstruktor einer statischen Klasse

```java
    /**
     * Dieser private Konstruktor dient dazu, den öffentlichen Konstruktor zu
     * verbergen. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls eine Instanz der Klasse
     *     erzeugt wird.
     */
    private Random()
    {
        throw new UnsupportedOperationException();
    }
```

## Deutsche Übersetzungen von englischen Klassennamen

| englisch                         | deutsch                                                |
| -------------------------------- | ------------------------------------------------------ |
| AABB (axis-aligned bounding box) | achsenparalleler Begrenzungsrahmen                     |
| Actor                            | Figur                                                  |
| BodyType                         | Verhalten einer Figur in der physikalischen Simulation |
| Bounds                           | Schranken, Abgrenzung                                  |
| Controller                       | Steuerung                                              |
| DistanceJoint                    | Stabverbindung                                         |
| Fixture                          | Halterung, Kollisionsform                              |
| Frame                            | Einzelbild                                             |
| Handler                          | Steuerungsklasse                                       |
| Joint                            | Verbindung                                             |
| Layer                            | Ebene                                                  |
| LineCap                          | Linienabschluss                                        |
| Listener                         | Beobachter                                             |
| Loop                             | Schleife                                               |
| Offset                           | Verzug                                                 |
| Opacity                          | Durchsichtigkeit (Deckkraft)                           |
| PrismaticJoint                   | Federverbindung                                        |
| Rectangle                        | Rechteck                                               |
| RevoluteJoint                    | Gelenkverbindung                                       |
| Rigid Body                       | Starrer Körper                                         |
| RopeJoint                        | Seilverbindung                                         |
| Scene                            | Szene                                                  |
| ScreenRecording                  | Bildschirmaufnahme                                     |
| Screenshot                       | Bildschirmfoto                                         |
| Shape                            | Umriss                                                 |
| Square                           | Quadrat                                                |
| TurboFire                        | Dauerfeuer                                             |
| WeldJoint                        | Schweißnaht                                            |

## Kommentar mit Lizenzhinweis

```java
/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
