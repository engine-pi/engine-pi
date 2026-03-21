# `versions` (Versionsverwaltung)

Die Engine Pi folgt dem [Semantic-Versioning](https://semver.org/)-Schema.

## Eine neue Version veröffentlichen

### Manuell

Am besten nach der letzten Versionsnummer suchen (z. B.
`<version>0.42.0</version>`) und mit der neuen ersetzen.

Die Versionsnummer auch in der Datei {{ repo_link('mkdocs.yml') }} ändern.


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
