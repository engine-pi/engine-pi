# `mkdocs` (Dokumentationsseite)

Die Dokumentation der [Engine Pi](https://github.com/engine-pi/engine-pi) ist
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

### Java spezifisch

#### `package(package_path, link_title?)`

```jinja
{% raw %}
{{ package('pi', 'Haupt-Paket') }}
{% endraw %}
```

{{ package('pi', 'Haupt-Paket') }}

---

#### `class(class_path, link_title?)`

```jinja
{% raw %}
{{ class('pi.Game', 'Spiel') }}
{% endraw %}
```

{{ class('pi.Game', 'Spiel') }}

#### `method(class_path, method, link_title?)`

```jinja
{% raw %}
{{ method('pi.actor.Actor', 'color(java.awt.Color)', 'color()') }}
{% endraw %}
```

{{ method('pi.actor.Actor', 'color(java.awt.Color)', 'color()') }}

---

#### `attribute(class_path, attribute, link_title?)`

```jinja
{% raw %}
{{ attribute('pi.Controller', 'colors', 'statisches Attribute colors') }}
{% endraw %}
```

{{ attribute('pi.Controller', 'colors', 'statisches Attribute colors') }}

---

#### `demo(relpath, hash?, lines?)`

```jinja
{% raw %}
{{ demo('docs/dev/design/SimpleGeometricActorsDemo') }}
{% endraw %}
```

{{ demo('docs/dev/design/SimpleGeometricActorsDemo') }}

```jinja
{% raw %}
{{ demo('docs/dev/design/SimpleGeometricActorsDemo', 'd9e92a3759dce13923528f62a1afa8328be5126e', 'L38-L42') }}
{% endraw %}
```

{{ demo('docs/dev/design/SimpleGeometricActorsDemo', 'd9e92a3759dce13923528f62a1afa8328be5126e', 'L38-L42') }}

---

#### `code(path, start_line?, end_line?, line?, link?, from_import?)`

```jinja
{% raw %}
{{ code('docs/main_classes/actor/hello_world/HelloWorldVersion2.java', 25) }}
{% endraw %}
```

##### Ohne Argumente

Ohne Argumente wird die komplette Java-Datei eingebunden.

```jinja
{% raw %}
{{ code('docs/HelloWorldVersion.java') }}
{% endraw %}
```

{{ code('docs/main_classes/actor/hello_world/HelloWorldVersion2.java') }}

##### from_import=true

Ist das Argument `from_import` auf wahr gesetzt, so wird der Quelltext ab dem ersten import-Statement angezeigt.

```jinja
{% raw %}
{{ code('docs/HelloWorldVersion.java', from_import=true) }}
{% endraw %}
```

{{ code('docs/main_classes/actor/hello_world/HelloWorldVersion2.java', from_import=true) }}

---

##### link=false

Das Argument `link` steuert, ob eine Link zum Github-Repository angehängt werden
soll.

```jinja
{% raw %}
{{ code('docs/HelloWorldVersion.java', link=false) }}
{% endraw %}
```

{{ code('docs/main_classes/actor/hello_world/HelloWorldVersion2.java', line=57, link=false) }}

---

#### `line(relpath, line)`

```jinja
{% raw %}
{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 42) }}
{% endraw %}
```

{{ line('docs/main_classes/actor/hello_world/HelloWorldVersion1.java', 44) }}

### assets (Mediendaten)

#### `image(relpath, caption?)`

```jinja
{% raw %}
{{ image('logo/logo.svg', 'Das Logo der Engine Pi') }}
{% endraw %}
```

{{ image('logo/logo.svg', 'Das Logo der Engine Pi') }}

---

#### `video(relpath, caption?)`

```jinja
{% raw %}
{{ video('docs/MainAnimation.mp4', 'Die Hauptanimation der Engine Pi. <br>Sie erscheint, wenn <code>Game.start()</code> ohne Szene ausgeführt wird.') }}
{% endraw %}
```

{{ video('docs/MainAnimation.mp4', 'Die Hauptanimation der Engine Pi. <br>Sie erscheint, wenn <code>Game.start()</code> ohne Szene ausgeführt wird.') }}

---

### Projekt spezifisch

#### `contribute()`

```jinja
{% raw %}
{{ contribute() }}
{% endraw %}
```

{{ contribute() }}

---

### `repo_link(relpath, link_title)`

```jinja
{% raw %}
{{ repo_link('docs/macros.py') }}
{% endraw %}
```

{{ repo_link('docs/macros.py') }}

### `drawio(basename)`

```jinja
{% raw %}
{{ drawio('main-classes') }}
{% endraw %}
```

{{ drawio('main-classes') }}

---

## Vom Macros-Plugin bereit gestellt.

{{ macros_info() }}
