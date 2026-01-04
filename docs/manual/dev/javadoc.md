# `javadoc` (API-Dokumentation)

Im Subprojekt `build-tools` befindet sich eine  {{
repo_link('subprojects/build-tools/src/main/resources/javadoc-stylesheet.css',
'CSS-Datei') }}, die verwendet wird, um das Aussehen der Dokumentation mit den
Engine-Pi-Farbe (Gnome-Farben) und der Engine-Pi-Standard-Schrift (Cantarell)
anzupassen.

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

## Deutsche Übersetzungen von englischen Klassennamen

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

## Kommentar mit Lizenzhinweis

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
