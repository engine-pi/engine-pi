# `mkdocs` (Dokumentationsseite)

Die Dokumentation der [Engine Pi](https://github.com/engine-pi/engine-pi) wurde
mit [MkDocs](https://www.mkdocs.org) und dem Theme
[Material](https://squidfunk.github.io/mkdocs-material) realisiert. Sie
verwendet Tutorials und Bilder aus dem [Engine Alpha
Wiki](https://engine-alpha.org), die unter der [Creative Commons „Namensnennung,
Weitergabe unter gleichen
Bedingungen“](https://creativecommons.org/licenses/by-sa/3.0/) Lizenz stehen.

## Makros:

{{ repo_link('docs/macros.py') }}

### `demo(relpath, hash?, lines?)`

{{ demo('docs/dev/design/SimpleGeometricActorsDemo') }}

---

### `package(package_path, link_title?)`

---

### `class(class_path, link_title?)`

---

### `image(relpath, caption?)`

---

### `video(relpath, caption?)`

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
