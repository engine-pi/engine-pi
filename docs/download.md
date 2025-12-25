# Download

[engine-pi-0.41.0-jar-with-dependencies.jar](https://github.com/engine-pi/engine-pi/releases/download/v0.41.0/engine-pi-0.41.0-jar-with-dependencies.jar){ .md-button .md-button--primary }

## BlueJ-Projekt

### Vorlage verwenden

Über die [Github-Release-Seite](https://github.com/engine-pi/engine-pi/releases)
des Projekt kann die aktuelle Version der Engine als BlueJ-Vorlage
heruntergeladen werden:

[engine-pi-0.41.0-bluej-template.zip](https://github.com/engine-pi/engine-pi/releases/download/v0.41.0/engine-pi-0.41.0-bluej-template.zip){ .md-button .md-button--primary }


### BlueJ-Projekt erstellen

Auf [Github Releases](https://github.com/engine-pi/engine-pi/releases) gehen und die aktuelle Version `engine-pi-<version>-jar-with-dependencies.jar` herunterladen (z. B. [engine-pi-0.41.0-jar-with-dependencies.jar](https://github.com/engine-pi/engine-pi/releases/download/v0.41.0/engine-pi-0.41.0-jar-with-dependencies.jar)),
einen `+libs` Ordner erstellen und die JAR-Datei hineinkopieren.

![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/download/Github-Release.png)
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
      <version>0.41.0</version>
    </dependency>
  </dependencies>
</project>
```


![](https://raw.githubusercontent.com/engine-pi/assets/main/docs/download/Maven-Central.png)
/// caption
Maven Central Repository
///

## Maven-Projekt

https://github.com/engine-pi/maven-boilerplate

In der Projekt-Datei `pom.xml` ist die Engine Pi als
Abhängigkeit (`dependency`) hinterlegt.
