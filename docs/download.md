# Download

[engine-pi-{{ version }}-jar-with-dependencies.jar](https://github.com/engine-pi/engine-pi/releases/download/v{{ version }}/engine-pi-{{ version }}-jar-with-dependencies.jar){ .md-button .md-button--primary }

## BlueJ-Projekt

### Vorlage verwenden

Über die [Github-Release-Seite](https://github.com/engine-pi/engine-pi/releases)
des Projekt kann die aktuelle Version der Engine als BlueJ-Vorlage
heruntergeladen werden:

[engine-pi-{{ version }}-bluej-template.zip](https://github.com/engine-pi/engine-pi/releases/download/v{{ version }}/engine-pi-{{ version }}-bluej-template.zip){ .md-button .md-button--primary }

### BlueJ-Projekt erstellen

Auf [Github Releases](https://github.com/engine-pi/engine-pi/releases) gehen und die aktuelle Version `engine-pi-<version>-jar-with-dependencies.jar` herunterladen (z. B. [engine-pi-{{ version }}-jar-with-dependencies.jar](https://github.com/engine-pi/engine-pi/releases/download/v{{ version }}/engine-pi-{{ version }}-jar-with-dependencies.jar)),
einen `+libs` Ordner erstellen und die JAR-Datei hineinkopieren.

{{ image('docs/download/Github-Release.png') }}
/// caption
Github Release
///

## Maven Central Repository

Im Gegensatz zur Engine Alpha ist die
[Engine Pi](https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/engine-pi)
über das wichtigste Repository für Java-Projekte das sogenannte
[Maven Central Repository](https://central.sonatype.com) abrufbar.

```xml
<project>
  <dependencies>
    <dependency>
      <groupId>de.pirckheimer-gymnasium</groupId>
      <artifactId>engine-pi</artifactId>
      <version>{{ version }}</version>
    </dependency>
  </dependencies>
</project>
```


{{ image('docs/download/Maven-Central.png') }}
/// caption
Maven Central Repository
///

## Maven-Projekt

https://github.com/engine-pi/maven-boilerplate

In der Projekt-Datei `pom.xml` ist die Engine Pi als
Abhängigkeit (`dependency`) hinterlegt.
