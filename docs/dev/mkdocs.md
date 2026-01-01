# `mkdocs` (Dokumentationsseite)

Die Dokumentation der [Engine Pi](https://github.com/engine-pi/engine-pi) wurde
mit [MkDocs](https://www.mkdocs.org) und dem Theme
[Material](https://squidfunk.github.io/mkdocs-material) realisiert. Sie
verwendet Tutorials und Bilder aus dem [Engine Alpha
Wiki](https://engine-alpha.org), die unter der [Creative Commons „Namensnennung,
Weitergabe unter gleichen
Bedingungen“](https://creativecommons.org/licenses/by-sa/3.0/) Lizenz stehen.

## Makros:

Das MkDocs-Plugin
[Mkdocs-Macros](https://github.com/fralau/mkdocs-macros-plugin) ermöglicht den
Einsatz von Makros. Die Makros sind in der Datei {{ repo_link('docs/macros.py')
}} definiert. Folgende Makros können verwendet werden:

### `demo(relpath, hash?, lines?)`

{% raw %}
{{ demo('docs/dev/design/SimpleGeometricActorsDemo') }}
{% endraw %}

{{ demo('docs/dev/design/SimpleGeometricActorsDemo') }}

{% raw %}
{{ demo('docs/dev/design/SimpleGeometricActorsDemo', 'd9e92a3759dce13923528f62a1afa8328be5126e', 'L38-L42') }}
{% endraw %}

{{ demo('docs/dev/design/SimpleGeometricActorsDemo', 'd9e92a3759dce13923528f62a1afa8328be5126e', 'L38-L42') }}

---

### `package(package_path, link_title?)`

{% raw %}
{{ package('pi', 'Haupt-Paket') }}
{% endraw %}

{{ package('pi', 'Haupt-Paket') }}

---

### `class(class_path, link_title?)`

{% raw %}
{{ class('pi.Game', 'Spiel') }}
{% endraw %}

{{ class('pi.Game', 'Spiel') }}

---

### `image(relpath, caption?)`

{% raw %}
{{ image('logo/logo.svg', 'Das Logo der Engine Pi') }}
{% endraw %}

{{ image('logo/logo.svg', 'Das Logo der Engine Pi') }}

---

### `video(relpath, caption?)`

{% raw %}
{{ video('docs/MainAnimation.mp4', 'Die Hauptanimation der Engine Pi. <br>Sie erscheint, wenn <code>Game.start()</code> ohne Szene ausgeführt wird.') }}
{% endraw %}

{{ video('docs/MainAnimation.mp4', 'Die Hauptanimation der Engine Pi. <br>Sie erscheint, wenn <code>Game.start()</code> ohne Szene ausgeführt wird.') }}

---

### `contribute()`

{% raw %}
{{ contribute() }}
{% endraw %}

{{ contribute() }}

---

### `repo_link(relpath, link_title)`

{% raw %}
{{ repo_link('docs/macros.py') }}
{% endraw %}

{{ repo_link('docs/macros.py') }}
