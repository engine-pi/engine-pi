jbox2d wurde auf Maven Central schon lange nicht mehr aktualisiert:
https://central.sonatype.com/artifact/org.jbox2d/jbox2d

Im Git Repository gab es jedoch viele Commits seit der letzten Veröffentlichung
auf Maven Central: https://github.com/jbox2d/jbox2d

Das Einbinden über ein lokales Repository wie bei der Engine Alpha umgesetzt,
hat Probleme bereitet bei Builds mit Gradle.

Geändert:

src/main/java/org/jbox2d/common/Settings.java

```java
    public static int maxPolygonVertices = 20;
```
