# `versions` (Versionsverwaltung)

Die Engine Pi folgt dem [Semantic-Versioning](https://semver.org/)-Schema.

## Eine neue Version veröffentlichen

Die Versionsnummer der Engine setzen.

```
mvn versions:set -f modules/engine/pom.xml
```

Die Versionsnummer des Meta-Projekts setzen.

```
mvn versions:set
```

Die Datei {{ repo_link('CHANGELOG.md') }} bezieht sich auf die Engine also auf {{ repo_link('subprojects/engine') }}.
