"""
https://mkdocs-macros-plugin.readthedocs.io/en/latest/macros/
"""

from pathlib import Path
from typing import Any

JAVADOC_URL_PREFIX = "https://engine-pi.github.io/javadocs"
# JAVADOC_URL_PREFIX = "https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest"
RAW_GITHUB_URL = "https://raw.githubusercontent.com/engine-pi"
RAW_ASSETS_URL = f"{RAW_GITHUB_URL}/assets/refs/heads/main"
ORACLE_URL_PREFIX = "https://docs.oracle.com/en/java/javase/17/docs/api"


def _normalize_package_path(package_path: str) -> str:
    """
    Normalize a package path by converting forward slashes to dots.

    :param package_path: A package path using forward slashes as separators
                           (e.g., "com/example/module").

    :return: The normalized package name using dots as separators
             (e.g., "com.example.module").
    """
    return package_path.replace("/", ".")


def _normalize_java_path(path: str) -> str:
    """Ensures that the path ends with .java"""
    if not path.endswith(".java"):
        return path + ".java"
    return path


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


def _check_class_path(class_path: str) -> None:
    relpath = _convert_class_path_to_subproject_path(class_path)
    if not Path(relpath).exists():
        raise Exception(
            f"The class path “{class_path}” has no corresponding Java file in “{relpath}”!"
        )


def _convert_class_path_to_subproject_path(class_path: str, check: bool = True) -> str:
    """:param class_path: for example ``pi.actor.Actor``

    :return: ``subprojects/engine/src/main/java/pi/actor/Actor.java``
    """
    class_path_orig = class_path
    class_path = class_path.replace("/", ".")
    class_path = class_path.replace(".java", "")
    subproject = class_path.split(".")[0]
    class_name = class_path.split(".")[-1]

    class_path = class_path.replace(".", "/")

    if class_name[0].isupper():
        class_path += ".java"
    subproject_path = (
        f"subprojects/{subprojects[subproject]}/src/main/java/{class_path}"
    )
    if check and not Path(subproject_path).exists():
        raise Exception(
            f"The class path “{class_path_orig}” has no corresponding Java file in “{subproject_path}”!"
        )
    return subproject_path


def _convert_class_path_to_relpath(class_path: str, check: bool = True) -> str:
    """:param class_path: for example ``java.awt.Color``

    :return: ``java/awt/Color``
    """
    if check:
        _convert_class_path_to_subproject_path(class_path, True)
    return class_path.replace(".", "/")


def _check_asset(relpath: str) -> None:
    if not (Path("assets") / relpath).exists():
        raise Exception(f"The asset “{relpath}” doesn’t exist!")


def _check_repo_path(relpath: str) -> None:
    if not (Path(relpath)).exists():
        raise Exception(f"The file “{relpath}” doesn’t exist!")


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


def _github_code_url(
    relpath: str,
    blob: str = "main",
    lines: str | None = None,
    start_line: int = 0,
    end_line: int = 0,
) -> str:
    """
    Generate a GitHub URL pointing to a specific file and optional line range.

    :param relpath: Relative path to the file in the repository
    :param blob: The branch name or the commit id (default: "main")
    :param lines: Line range specification (e.g., "L10-L20"), mutually exclusive with start_line/end_line
    :param start_line: Starting line number for the range (use with end_line, not with lines)
    :param end_line: Ending line number for the range (use with start_line, not with lines)
    :return: A complete GitHub URL to the file with optional line anchor
    :raises Exception: If both lines and start_line/end_line parameters are provided simultaneously
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

    return f"https://github.com/engine-pi/engine-pi/blob/{blob}/{relpath}{lines}"


def _get_class_name(class_path: str) -> str:
    """
    Extracts the class name from a fully qualified class path.

    :param class_path: A fully qualified class path string (e.g., ``pi.actor.Actor``)

    :return: The simple class name extracted from the path (e.g., ``Actor``)
    """
    return class_path.split(".")[-1]


class CodeSample:
    java_file: "JavaFile"

    start_line: int = 0

    end_line: int = 0

    def __init__(
        self,
        java_file: "JavaFile",
        start_line: int = 1,
        end_line: int = 0,
    ) -> None:
        self.java_file = java_file
        self.start_line = start_line
        self.end_line = end_line

    @property
    def lines(self) -> list[str]:
        return self.java_file.lines[self.start_line - 1 : self.end_line]

    def fenced_code_block(self) -> str:
        return _fenced_code_block(
            "\n".join(self.lines),
            language="java",
            start_line=self.start_line,
        )


class JavaFile:
    path: Path

    lines: list[str]

    def __init__(self, path: str) -> None:
        """
        :param path: A class path or a file path relative to subprojects/demos/src/main/java/demos
        """
        if "/" not in path:
            self.path = Path(_convert_class_path_to_subproject_path(path))
        else:
            self.path = Path(
                "subprojects", "demos", "src", "main", "java", "demos"
            ) / _normalize_java_path(path)
        self.lines = self.path.read_text().splitlines()

    def get_first_import_statement(self) -> int:
        counter = 1
        for line in self.lines:
            if line.startswith("import "):
                return counter
            counter += 1
        return counter

    def get_code_sample(
        self,
        start_line: int = 0,
        end_line: int = 0,
        line: int = 0,
        from_import: bool = False,
    ) -> CodeSample:
        """
        Extract a substring of code lines based on specified line numbers.

        :param start_line: The starting line number (1-indexed). If 0, starts from the beginning.
                           Defaults to 0.
        :param end_line: The ending line number (1-indexed, inclusive). If 0, goes to the end.
                         Defaults to 0.
        :param line: A specific line number to extract (1-indexed). If greater than 0,
                     overrides start_line and end_line to extract only that line.
                     Defaults to 0.
        :return: A string containing the extracted code lines joined by newline characters.

        :raises Exception: If the end line is an empty string.
        :raises Exception: If the start line is an empty string.

        .. note::
           If the `line` parameter is specified (> 0), it takes precedence over
           `start_line` and `end_line` parameters.
        """

        if from_import and (start_line > 0 or end_line > 0 or line > 0):
            raise Exception("from_import is an exclusive option")

        if from_import:
            start_line = self.get_first_import_statement()

        if line > 0:
            start_line = line
            end_line = line
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

        if start_line == 0:
            start_line = 1
        if end_line == 0:
            end_line = len(self.lines)
        return CodeSample(java_file=self, start_line=start_line, end_line=end_line)

    def url(self) -> str:
        return _github_code_url(str(self.path))


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
        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_convert_class_path_to_relpath(class_path)}.html)"

    env.macro(macro_class, "class")

    def macro_java_class(
        class_path: str,
        link_title: str | None = None,
        module: str = "java.base",
    ) -> str:
        if link_title is None:
            link_title = _get_class_name(class_path)
        return f"[{link_title}]({ORACLE_URL_PREFIX}/{module}/{_convert_class_path_to_relpath(class_path, False)}.html)"

    env.macro(macro_java_class, "java_class")

    def macro_package(package_path: str, link_title: str | None = None) -> str:
        if link_title is None:
            link_title = package_path

        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_convert_class_path_to_relpath(package_path)}/package-summary.html)"

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

        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_convert_class_path_to_relpath(class_path)}.html#{method})"

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

        return f":fontawesome-brands-java:[{link_title}]({JAVADOC_URL_PREFIX}/{_convert_class_path_to_relpath(class_path)}.html#{attribute})"

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
        url = _github_code_url(
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

    def macro_video(relpath: str, caption: str | None = None) -> str:
        _check_asset(relpath)
        return _caption(
            f"""<video autoplay loop>
    <source src="{RAW_ASSETS_URL}/{relpath}" type="video/mp4" />
    Download the
    <a href="{RAW_ASSETS_URL}/{relpath}">MP4</a>
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
        _check_repo_path(relpath)
        if link_title is None:
            link_title = relpath
        return f"[{link_title}](https://github.com/engine-pi/engine-pi/blob/main/{relpath})"

    env.macro(macro_repo_link, "repo_link")

    def macro_code(
        path: str,
        start_line: int = 0,
        end_line: int = 0,
        line: int = 0,
        link: bool = True,
        from_import: bool = False,
    ) -> str:
        """
        Extract a substring of code lines based on specified line numbers.

        :param start_line: The starting line number (1-indexed). If 0, starts from the beginning.
                           Defaults to 0.
        :param end_line: The ending line number (1-indexed, inclusive). If 0, goes to the end.
                         Defaults to 0.
        :param line: A specific line number to extract (1-indexed). If greater than 0,
                     overrides start_line and end_line to extract only that line.
                     Defaults to 0.
        :return: A string containing the extracted code lines joined by newline characters.

        :raises Exception: If the end line is an empty string.
        :raises Exception: If the start line is an empty string.

        .. note::
           If the `line` parameter is specified (> 0), it takes precedence over
           `start_line` and `end_line` parameters.
        https://github.com/mkdocs/mkdocs/issues/692

        https://pypi.org/project/mkdocs-snippets/
        """

        java_file = JavaFile(path)

        output = java_file.get_code_sample(
            start_line=start_line, end_line=end_line, line=line, from_import=from_import
        ).fenced_code_block()

        if link:
            output += "\n" + macro_demo(
                str(java_file.path), start_line=start_line, end_line=end_line
            )

        return output

    env.macro(macro_code, "code")

    def macro_line(relpath: str, line: int) -> str:
        return macro_code(path=relpath, line=line, link=False)

    env.macro(macro_line, "line")

    def macro_drawio(basename: str) -> str:
        """
        :param basename: The filename without extension
        """
        return f"![]({RAW_GITHUB_URL}/engine-pi/refs/heads/main/docs/drawio/{basename}.drawio)"

    env.macro(macro_drawio, "drawio")
