# `versions` (Versionsverwaltung)

Die Engine Pi folgt dem [Semantic-Versioning](https://semver.org/)-Schema. Die
einzelnen Subprojekte des Monorepositories haben unterschiedliche Versionen. Die
in Git mit Tags markierten Versionen beziehen sich dabei auf die Engine selbst
und nicht auf die anderen Subprojekte. Das
[central-publishing-maven-plugin](https://central.sonatype.org/publish/publish-portal-maven/)
ist so konfiguriert, dass nur Subprojekte mit geänderter Versionsnummer
veröffentlicht werden.

```xml
<plugin>
    <groupId>org.sonatype.central</groupId>
    <artifactId>central-publishing-maven-plugin</artifactId>
    <version>0.9.0</version>
    <extensions>true</extensions>
    <configuration>
        <autoPublish>true</autoPublish>
        <!-- The plugin can ignore, or more specifically not add, components that have
        already been published in the past to the bundle that will be uploaded and
        published.
        https://central.sonatype.org/publish/publish-portal-maven/#ignorepublishedcomponents -->
        <ignorePublishedComponents>true</ignorePublishedComponents>
    </configuration>
</plugin>
```

## Eine neue Version veröffentlichen

### Manuell

Am besten nach der letzten Versionsnummer suchen (z. B.
`<version>0.42.0</version>`) und unter Verwendung der Suchen und Ersetzten
Funktion eines Text-Editors mit der neuen ersetzen.

Die Versionsnummer auch in der Datei {{ repo_link('docs/mkdocs.yml') }} ändern.

### Mit Hilfe des Maven Plugins Versions

Die Versionsnummer der Engine setzen.

```
mvn versions:set -f modules/engine/pom.xml
```

Die Versionsnummer des Meta-Projekts setzen.

```
mvn versions:set
```

Die Datei {{ repo_link('CHANGELOG.md') }} bezieht sich auf die Engine also auf {{ repo_link('subprojects/engine') }}.
