# `monorepo` (Aufbau des Repository)

Das [Engine Pi Repository](https://github.com/engine-pi/engine-pi) ist als
[Monorepo](https://en.wikipedia.org/wiki/Monorepo) strukturiert. In diesem
großen Repository sind nicht nur die einzelnen Java-Subprojects zu finden,
sondern auch die Dokumentation ({{ repo_link('docs') }}) und die Mediendateien
([assets](https://github.com/engine-pi/assets)).

!!! info

    In Versionskontrollsystemen ist ein Monorepo („mono“ bedeutet „einzeln“
    und „repo“ ist die Abkürzung für „Repository“) eine Strategie zur
    Softwareentwicklung, bei der der Code für mehrere Projekte im selben
    Repository gespeichert wird.[^wikipedia:monorepo]

[^wikipedia:monorepo]: https://en.wikipedia.org/wiki/Monorepo

## `java` (Java-Code)

Das Projekt nutzt das Build Tool [maven](https://maven.apache.org/) und ist
ist als sogenanntes [Multiple Modules bzw.
Subprojects](https://maven.apache.org/guides/mini/guide-multiple-modules.html)-Projekt
strukturiert. Die eigentliche Engine befindet sich im Ordner
{{ repo_link('subprojects/engine') }}.

| Pfad                                               | artefactId              |
| -------------------------------------------------- | ----------------------- |
| ./                                                 | engine-pi-meta          |
| {{ repo_link('subprojects/engine') }}              | engine-pi               |
| {{ repo_link('subprojects/demos') }}               | engine-pi-demos         |
| {{ repo_link('subprojects/cli') }}                 | engine-pi-cli           |
| {{ repo_link('subprojects/games/blockly-robot') }} | engine-pi-blockly-robot |
| {{ repo_link('subprojects/games/pacman') }}        | engine-pi-pacman        |
| {{ repo_link('subprojects/games/tetris') }}        | engine-pi-tetris        |
| {{ repo_link('subprojects/build-tools') }}         | engine-pi-build-tools   |
