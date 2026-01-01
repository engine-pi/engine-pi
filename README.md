[![Maven Central](https://img.shields.io/maven-central/v/de.pirckheimer-gymnasium/engine-pi.svg?style=flat)](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)

Dokumentation: https://engine-pi.github.io/engine-pi

Javadocs: https://engine-pi.github.io/javadocs/pi/package-summary.html

[![](https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/docs/MainAnimation.webp)](https://engine-pi.github.io/engine-pi)

## Entwicklung

### Aufbau des Projekts

Die Engine Pi nutzt das Build Tool [maven](https://maven.apache.org/). Das
Projekt ist als sogenanntes [Multiple Modules bzw.
Subprojects](https://maven.apache.org/guides/mini/guide-multiple-modules.html)-Projekt
strukturiert. Die eigentliche Engine befindet sich im Ordner
`./subprojects/engine`.

| Pfad                              | artefactId              |
| --------------------------------- | ----------------------- |
| ./                                | engine-pi-meta          |
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

### File Link zu den Demos:

```java
// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/dsa/turtle/TurtleDemo.java
```

### Anordnung der Konstruktoren

Oben stehen die Konstruktoren mit wenigen Parametern unten die mit vielen.
