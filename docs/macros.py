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


def _normalize_java_path(path: str) -> str:
    """Ensures that the path ends with .java"""
    if not path.endswith(".java"):
        return path + ".java"
    return path


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


def _fenced_code_block(code: str, language: str = "java", start_line: int = 0) -> str:
    linesnums = ""
    if start_line > 0:
        linesnums = f' linenums="{start_line}"'
    return "``` " + language + linesnums + "\n" + code + "\n```"


def _demo_github_url(
    relpath: str,
    blob: str = "main",
    lines: str | None = None,
    start_line: int = 0,
    end_line: int = 0,
) -> str:
    """
    :param blob: The branch name or the commit id
    """
    relpath = _normalize_java_path(relpath)

    if lines is not None and (start_line > 0 or end_line > 0):
        raise Exception("Use lines OR start_line and end_line")

    if lines is None:
        lines = ""
    if start_line > 0:
        lines = f"L{start_line}"
    if end_line > 0:
        lines = f"{lines}-L{end_line}"

    if not lines.startswith("#") and lines != "":
        lines = "#" + lines

    return f"https://github.com/engine-pi/engine-pi/blob/{blob}/subprojects/demos/src/main/java/demos/{relpath}{lines}"


class JavaFile:
    relpath: str
    path: Path

    lines: list[str]

    def __init__(self, relpath: str) -> None:
        self.relpath = _normalize_java_path(relpath)

        self.path = (
            Path("subprojects", "demos", "src", "main", "java", "demos") / self.relpath
        )
        self.lines = self.path.read_text().splitlines()

    def get_code(self, start_line: int = 0, end_line: int = 0) -> str:
        lines = self.lines
        if end_line > 0:
            lines = lines[:end_line]
        if start_line > 0:
            lines = lines[start_line - 1 :]
        return "\n".join(lines)

    def url(self) -> str:
        return _demo_github_url(str(self.relpath))


def define_env(env: Any) -> None:
    def class_name(class_path: str, link_title: str | None = None) -> str:
        """
        {{ class('actor.Actor') }} {{ class('pi.actor.Actor') }}
        """
        class_path = _normalize_package_path(class_path)
        if link_title is None:
            link_title = class_path.split(".")[-1]
        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(class_path)}.html)"

    env.macro(class_name, "class")

    @env.macro
    def package(package_path: str, link_title: str | None = None) -> str:  # pyright: ignore[reportUnusedFunction]
        if link_title is None:
            link_title = package_path

        return f"[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(package_path)}/package-summary.html)"

    @env.macro
    def demo(
        relpath: str,
        blob: str = "main",
        lines: str | None = None,
        start_line: int = 0,
        end_line: int = 0,
    ) -> str:  # pyright: ignore[reportUnusedFunction]
        """
        :param blob: The branch name or the commit id
        """
        relpath = _normalize_java_path(relpath)
        url = _demo_github_url(
            relpath, blob, lines=lines, start_line=start_line, end_line=end_line
        )
        return f"<small>Zum Java-Code: [demos/{relpath}]({url})</small>"

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
    def code(relpath: str, start_line: int = 0, end_line: int = 0) -> str:  # pyright: ignore[reportUnusedFunction]
        """
        :param start_line: 1 is the first line (including)
        :param end_line: including


        https://github.com/mkdocs/mkdocs/issues/692

        https://pypi.org/project/mkdocs-snippets/
        """

        java_file = JavaFile(relpath)

        start_line_for_code_block = 1
        if start_line > 0:
            start_line_for_code_block = start_line

        return (
            _fenced_code_block(
                java_file.get_code(start_line, end_line),
                language="java",
                start_line=start_line_for_code_block,
            )
            + "\n"
            + demo(relpath, start_line=start_line, end_line=end_line)
        )
