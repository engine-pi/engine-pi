"""
https://mkdocs-macros-plugin.readthedocs.io/en/latest/macros/
"""

from pathlib import Path
from typing import Any

JAVADOC_URL_PREFIX = "https://engine-pi.github.io/javadocs"
# JAVADOC_URL_PREFIX = "https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest"


def _normalize_package_path(package_path: str) -> str:
    return package_path.replace("/", ".")


def _normalize_java_path(path: str) -> str:
    """Ensures that the path ends with .java"""
    if not path.endswith(".java"):
        return path + ".java"
    return path


def _to_url(package_path: str) -> str:
    """Convert a package path to a URL with slashes

    :param package_path: for example ``pi.actor.Actor``

    """
    return package_path.replace(".", "/")


# | Pfad                              | artefactId              |
# | --------------------------------- | ----------------------- |
# | ./                                | engine-pi-meta          |
# | ./subprojects/engine              | engine-pi               |
# | ./subprojects/demos               | engine-pi-demos         |
# | ./subprojects/cli                 | engine-pi-cli           |
# | ./subprojects/games/blockly-robot | engine-pi-blockly-robot |
# | ./subprojects/games/pacman        | engine-pi-pacman        |
# | ./subprojects/games/tetris        | engine-pi-tetris        |
# | ./subprojects/build-tools         | engine-pi-build-tools   |

subprojects = {
    "pi": "engine",
    "demos": "demos",
    "blockly_robot": "games/blockly-robot",
    "tetris": "games/tetris",
    "pacman": "games/pacman",
    "cli": "cli",
}


def _convert_class_path_to_relpath(class_path: str) -> str:
    """:param package_path: for example ``pi.actor.Actor``

    :return: ``subprojects/engine/src/main/java/pi/actor/Actor.java``
    """
    subproject = class_path.split(".")[0]
    class_path = class_path.replace(".", "/")
    return f"subprojects/{subprojects[subproject]}/src/main/java/{class_path}.java"


def _check_class_path(class_path: str) -> None:
    relpath = _convert_class_path_to_relpath(class_path)
    if not Path(relpath).exists():
        raise Exception(
            f"The class path “{class_path}” has no corresponding Java file in “{relpath}”!"
        )


def _check_asset(relpath: str) -> None:
    if not (Path("assets") / relpath).exists():
        raise Exception(f"The asset “{relpath}” doesn’t exist!")


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


def _get_class_name(class_path: str) -> str:
    """
    :param class_path: For example ``pi.actor.Actor``

    :return: For example ``Actor``
    """

    return class_path.split(".")[-1]


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
            if lines[len(lines) - 1] == "":
                raise Exception(
                    f"End line no {end_line} of code {lines} is an empty string!"
                )
        if start_line > 0:
            lines = lines[start_line - 1 :]
            if lines[0] == "":
                raise Exception(
                    f"Start line no {start_line} of code {lines} is an empty string!"
                )
        return "\n".join(lines)

    def url(self) -> str:
        return _demo_github_url(str(self.relpath))


def define_env(env: Any) -> None:
    def macro_class(class_path: str, link_title: str | None = None) -> str:
        """
        :param class_path: For example ``pi.actor.Actor``

        ``{{ class('pi.actor.Actor') }}``
        """
        class_path = _normalize_package_path(class_path)
        _check_class_path(class_path)
        if link_title is None:
            link_title = _get_class_name(class_path)
        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(class_path)}.html)"

    env.macro(macro_class, "class")

    def macro_package(package_path: str, link_title: str | None = None) -> str:
        if link_title is None:
            link_title = package_path

        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(package_path)}/package-summary.html)"

    env.macro(macro_package, "package")

    def macro_method(
        class_path: str, method: str, link_title: str | None = None
    ) -> str:
        """
        :param class_path: For example ``pi.actor.Actor``
        :param method: For example ``color(java.awt.Color)``
        :return: ``https://engine-pi.github.io/javadocs/pi/actor/Actor.html#color(java.awt.Color)``
        """

        if link_title is None:
            link_title = method

        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(class_path)}.html#{method})"

    env.macro(macro_method, "method")

    def macro_methods(class_path: str, methods: list[str]) -> str:
        """
        :param class_path: For example ``pi.actor.Actor``
        :param methods: For example ``['color(java.awt.Color)', 'color(String)']``
        """
        output: str = f"__Methoden in der Klasse {macro_class(class_path)}:__\n\n"
        for m in methods:
            output += "- " + macro_method(class_path, m) + "\n"
        return output

    env.macro(macro_methods, "methods")

    def macro_attribute(
        class_path: str, attribute: str, link_title: str | None = None
    ) -> str:  # pyright: ignore[reportUnusedFunction]
        """
        :param class_path: For example ``pi.Resources``
        :param attribute: For example ``color``
        :return: ``https://engine-pi.github.io/javadocs/pi/Resources.html#colors``
        """

        if link_title is None:
            link_title = _get_class_name(class_path)

        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_to_url(class_path)}.html#{attribute})"

    env.macro(macro_attribute, "attribute")

    def macro_demo(
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

    env.macro(macro_demo, "demo")

    def macro_image(relpath: str, caption: str | None = None) -> str:
        _check_asset(relpath)
        return _caption(
            f'<img src="https://raw.githubusercontent.com/engine-pi/assets/refs/heads/main/{relpath}">',
            caption,
        )

    env.macro(macro_image, "image")

    @env.macro
    def macro_video(relpath: str, caption: str | None = None) -> str:
        _check_asset(relpath)
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

    env.macro(macro_video, "video")

    def macro_contribute() -> str:
        return """!!! warning

    Diese Hilfeseite hat leider noch keinen Inhalt. Hilf mit und fülle diese Seite mit Inhalt.
"""

    env.macro(macro_contribute, "contribute")

    def macro_repo_link(relpath: str, link_title: str | None = None) -> str:
        if link_title is None:
            link_title = relpath
        return f"[{link_title}](https://github.com/engine-pi/engine-pi/blob/main/{relpath})"

    env.macro(macro_repo_link, "repo_link")

    def macro_code(relpath: str, start_line: int = 0, end_line: int = 0) -> str:
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
            + macro_demo(relpath, start_line=start_line, end_line=end_line)
        )

    env.macro(macro_code, "code")
