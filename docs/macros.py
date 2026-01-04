"""
https://mkdocs-macros-plugin.readthedocs.io/en/latest/macros/
"""

from pathlib import Path
from typing import Any


JAVADOC_URL_PREFIX = "https://engine-pi.github.io/javadocs"
# JAVADOC_URL_PREFIX = "https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest"


def _normalize_package_path(package_path: str) -> str:
    if not package_path.startswith("pi."):
        return "pi." + package_path
    return package_path


def _to_url(package_path: str) -> str:
    """Convert a package path to a URL with slashes"""
    return package_path.replace(".", "/")


def _caption(content: str, caption: str | None = None) -> str:
    if caption is None:
        return content
    return f"""<figure>
    <p>{content}</p>
    <figcaption>
        <p>{caption}</p>
    </figcaption>
</figure>
"""


def define_env(env: Any) -> None:
    def class_name(class_path: str, link_title: str | None = None) -> str:
        """
        {{ class('actor.Actor') }} {{ class('pi.actor.Actor') }}
        """
        class_path = _normalize_package_path(class_path)
        if link_title is None:
            link_title = class_path.split(".")[-1]
        return f"[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(class_path)}.html)"

    env.macro(class_name, "class")

    @env.macro
    def package(package_path: str, link_title: str | None = None) -> str:  # pyright: ignore[reportUnusedFunction]
        if link_title is None:
            link_title = package_path

        return f"[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(package_path)}/package-summary.html)"

    @env.macro
    def demo(relpath: str, hash: str = "main", lines: str | None = None) -> str:  # pyright: ignore[reportUnusedFunction]
        if lines is None:
            lines = ""
        if not lines.startswith("#") and lines != "":
            lines = "#" + lines
        return f"<small>Zum Java-Code: [demos/{relpath}.java](https://github.com/engine-pi/engine-pi/blob/{hash}/subprojects/demos/src/main/java/demos/{relpath}.java{lines})</small>"

    @env.macro
    def image(relpath: str, caption: str | None = None) -> str:  # pyright: ignore[reportUnusedFunction]
        return _caption(
            f'<img src="https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/{relpath}">',
            caption,
        )

    @env.macro
    def video(relpath: str, caption: str | None = None) -> str:  # pyright: ignore[reportUnusedFunction]
        return _caption(
            f"""<video autoplay loop>
    <source src="https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/{relpath}" type="video/mp4" />
    Download the
    <a href="https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/{relpath}">MP4</a>
    video.
</video>
""",
            caption,
        )

    @env.macro
    def contribute() -> str:  # pyright: ignore[reportUnusedFunction]
        return """!!! warning

    Diese Hilfeseite hat leider noch keinen Inhalt. Hilf mit und fülle diese Seite mit Inhalt.
"""

    @env.macro
    def repo_link(relpath: str, link_title: str | None = None) -> str:  # pyright: ignore[reportUnusedFunction]
        if link_title is None:
            link_title = relpath
        return f"[{link_title}](https://github.com/engine-pi/engine-pi/blob/main/{relpath})"

    @env.macro
    def snippet(relpath: str) -> str:  # pyright: ignore[reportUnusedFunction]
        """
        https://github.com/mkdocs/mkdocs/issues/692

        https://pypi.org/project/mkdocs-snippets/
        """

        path = Path("subprojects", "demos", "src", "main", "java", "demos") / relpath

        return "```java\n" + path.read_text() + "\n```"
