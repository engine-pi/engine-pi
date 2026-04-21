"""
https://mkdocs-macros-plugin.readthedocs.io/en/latest/macros/
"""

from pathlib import Path
import re
from typing import Any, Optional, Union

JAVADOC_URL_PREFIX = "https://engine-pi.github.io/javadocs"
# JAVADOC_URL_PREFIX = "https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest"
RAW_GITHUB_URL = "https://raw.githubusercontent.com/engine-pi"
RAW_ASSETS_URL = f"{RAW_GITHUB_URL}/assets/refs/heads/main"
ORACLE_URL_PREFIX = "https://docs.oracle.com/en/java/javase/17/docs/api"


BASE = (Path(__file__).parent / "..").resolve()


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
# | ./                                | engine-pi-project       |
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
    "java": "",
}


def _check_classpath(class_path: str) -> None:
    relpath = _classpath_2_subproject(class_path)
    if not Path(relpath).exists():
        raise Exception(
            f"The class path “{class_path}” has no corresponding Java file in “{relpath}”!"
        )


def _classpath_2_subproject(class_path: str, check: bool = True) -> str:
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
    if check and not (BASE / subproject_path).exists():
        raise Exception(
            f"The class path “{class_path_orig}” has no corresponding Java file in “{subproject_path}”!"
        )
    return subproject_path


def _classpath_2_link(spec: str, link_title: str | None = None) -> str:
    """
    - ``pi.actor.Actor#center(double,double)``: ``https://engine-pi.github.io/javadocs/pi/actor/Actor.html#center(double,double)``
    - ``java.lang.String#indexOf(java.lang.String,int)``: ``https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html#indexOf(java.lang.String,int)``
    - ``java.lang.Runnable#run()``: ``https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Runnable.html#run()``
    - ``java.desktop:java.awt.Graphics2D``: ``https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/awt/Graphics2D.html``

    :param spec: The specification of the class path

    :return: A HTTP-URL
    """

    class_path: str = ""
    """for example ``pi.actor.Actor``
    """

    member = ""
    """for example ``#center(double,double)``
    """

    module = ""
    if ":" in spec:
        module = spec.split(":")[0]
        spec = spec.split(":")[1]

    if "#" in spec:
        class_path = spec.split("#")[0]
        member = "#" + spec.split("#")[1]
    else:
        class_path = spec

    is_java = False
    if class_path.startswith("java"):
        is_java = True

    class_relpath = _classpath_2_relpath(class_path, check=not is_java)

    class_name = _get_class_name(class_path)

    is_package = False

    if class_name == class_name.lower():
        is_package = True
        class_relpath = class_relpath + "/package-summary"

    if link_title is None:
        if is_package:
            link_title = class_path
        else:
            link_title = class_name + member

    url_prefix: str
    if is_java:
        url_prefix = ORACLE_URL_PREFIX
    else:
        url_prefix = JAVADOC_URL_PREFIX

    if is_java and module == "":
        module = "java.base/"

    if not module.endswith("/"):
        module += "/"

    return f":fontawesome-brands-java:[{link_title}]({url_prefix}/{module}{class_relpath}.html{member})"


def _classpath_2_relpath(class_path: str, check: bool = True) -> str:
    """:param class_path: for example ``java.awt.Color``

    :return: ``java/awt/Color``
    """
    if check:
        _classpath_2_subproject(class_path, True)
    return class_path.replace(".", "/")


def _classpath_2_package(class_path: str, check: bool = True) -> str:
    """:param class_path: for example ``java.awt.Color``

    :return: ``java.awt``
    """
    class_path = _normalize_package_path(class_path)
    class_path = class_path.replace(".java", "")

    if check:
        _check_classpath(class_path)

    parts = class_path.split(".")
    if len(parts) <= 1:
        return ""
    return ".".join(parts[:-1])


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


class CodeSnippet:
    """
    Represents a slice of lines extracted from a :class:`JavaFile`.
    """

    java_file: "JavaFile"
    """
    Source Java file used to retrieve lines.
    """

    start_line: int = 0
    """First 1-based line index to include."""

    end_line: int = 0
    """Last 1-based line index to include. If ``0``, includes
        all remaining lines from ``start_line``."""

    prefix_comment: Optional[str]

    suffix_comment: Optional[str]

    def __init__(
        self,
        java_file: "JavaFile",
        start_line: int = 1,
        end_line: int = 0,
    ) -> None:
        """
        Initialize a code sample selection.

        :param java_file: The Java source holder.
        :param start_line: Starting 1-based line number.
        :param end_line: Ending 1-based line number (inclusive). Use ``0``
            to include all lines to the end of the file.
        """
        self.java_file = java_file
        self.start_line = start_line
        self.end_line = end_line

    @property
    def lines(self) -> list[str]:
        """
        Return the selected lines from the source file.

        :return: A list of lines covered by this sample.
        """
        if not self.end_line:
            return self.java_file.lines[self.start_line - 1 :]
        return self.java_file.lines[self.start_line - 1 : self.end_line]

    def fenced_code_block(self) -> str:
        """
        Build a fenced Markdown code block for the selected Java lines.

        :return: A fenced code block with Java syntax hint and line numbering.
        """
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
        :param path: A class path (for example
            ``demos.docs.resources.config.CustomConfigGroupDemo``) or a file
            path relative to ``subprojects/demos/src/main/java/demos``.
        """
        if "/" not in path:
            self.path = BASE / _classpath_2_subproject(path)
        else:
            self.path = (
                BASE
                / Path("subprojects", "demos", "src", "main", "java", "demos")
                / _normalize_java_path(path)
            )
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
    ) -> CodeSnippet:
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
        return CodeSnippet(java_file=self, start_line=start_line, end_line=end_line)

    def get_code_snippets(self) -> list[CodeSnippet]:
        snippets: list[CodeSnippet] = []

        i = 0
        start_line = -1
        end_line = -1
        for line in self.lines:
            if "// -->" in line:
                start_line = i + 1

            if re.search(r"// .*<--", line) is not None:
                end_line = i

            if start_line > -1 and end_line > 0:
                snippets.append(
                    CodeSnippet(
                        java_file=self, start_line=start_line + 1, end_line=end_line
                    )
                )
                start_line = -1
                end_line = -1

            i += 1

        if start_line > -1 and end_line == -1:
            snippets.append(CodeSnippet(java_file=self, start_line=start_line))

        return snippets

    def url(self) -> str:
        return _github_code_url(str(self.path))


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
    return f'<small class="demo-link">Zum Java-Code: <a href="{url}" title="Die Quelldatei des Code-Beispiels auf Github aufrufen." target="_blank">demos/{relpath}</a></small>'


def macro_code(
    path: str,
    start_line: int = 0,
    end_line: int = 0,
    line: int = 0,
    link: bool = True,
    from_import: bool = False,
    snippet: Union[bool, int] = False,
) -> str:
    """
    Extract a substring of code lines based on specified line numbers.

    :param path: A class path (for example
        ``demos.docs.resources.config.CustomConfigGroupDemo``) or a file
        path relative to ``subprojects/demos/src/main/java/demos``.
    :param start_line: The starting line number (1-indexed). If 0, starts from
        the beginning. Defaults to 0.
    :param end_line: The ending line number (1-indexed, inclusive). If 0, goes
        to the end. Defaults to 0.
    :param line: A specific line number to extract (1-indexed). If greater than 0,
                    overrides start_line and end_line to extract only that line.
                    Defaults to 0.
    :param link: If true, it adds a link to the GitHub repository where the
        Java class is hosted.
    :param snippet:

    :return: A string containing the extracted code lines joined by newline characters.

    :raises Exception: If the end line is an empty string.
    :raises Exception: If the start line is an empty string.

    .. note::
        If the `line` parameter is specified (> 0), it takes precedence over
        `start_line` and `end_line` parameters.
    https://github.com/mkdocs/mkdocs/issues/692

    https://pypi.org/project/mkdocs-snippets/
    """

    if snippet and (start_line or line or from_import):
        raise Exception("Don’t use snippet with start_line, line or from_import")

    java_file = JavaFile(path)

    output: str = ""

    if snippet:
        snippets = java_file.get_code_snippets()

        for s in snippets:
            output += s.fenced_code_block()

    else:
        output += java_file.get_code_sample(
            start_line=start_line, end_line=end_line, line=line, from_import=from_import
        ).fenced_code_block()

    if link:
        output += "\n" + macro_demo(
            str(java_file.path), start_line=start_line, end_line=end_line
        )

    return output


def define_env(env: Any) -> None:

    def macro_javadoc(
        spec: str,
        link_title: str | None = None,
    ) -> str:
        return _classpath_2_link(spec=spec, link_title=link_title)

    env.macro(macro_javadoc, "javadoc")

    def macro_methods(class_path: str, methods: list[str]) -> str:
        """
        :param class_path: For example ``pi.actor.Actor``
        :param methods: For example ``['color(java.awt.Color)', 'color(String)']``
        """
        output: str = f"__Methoden in der Klasse {macro_javadoc(class_path)}:__\n\n"
        for m in methods:
            output += "- " + macro_javadoc(class_path + "#" + m) + "\n"
        return output

    env.macro(macro_methods, "methods")

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

    def macro_import_statement(classpath: str) -> str:
        """:param class_path: for example ``pi.actor.Group``"""

        return f"""!!! import

    Die Klasse
    {_classpath_2_link(classpath)} ist im
    Paket
    {_classpath_2_link(_classpath_2_package(classpath))} enthalten und kann über die Anweisung

    ```java
    import {classpath};
    ```

    importiert werden.
"""

    env.macro(macro_import_statement, "import_admonition")

    def macro_static_import(attribute: str, classpath: str = "pi.Controller") -> str:
        """:param attribute: The name of the static attribute, for example ``images``."""
        """:param classpath: The class path of the class containing the static attribute, for example ``pi.Controller``."""

        return f"""!!! import

    Das statische Attribut {_classpath_2_link(classpath + "#" + attribute, attribute)} der
    Klasse {_classpath_2_link(classpath)} kann über einen
    statischen Import eingebunden werden:

    ```java
    import static pi.Controller.{attribute};
    ```
"""

    env.macro(macro_static_import, "static_import_admonition")

    def macro_repo_link(relpath: str, link_title: str | None = None) -> str:
        _check_repo_path(relpath)
        if link_title is None:
            link_title = relpath
        return f"[{link_title}](https://github.com/engine-pi/engine-pi/blob/main/{relpath})"

    env.macro(macro_repo_link, "repo_link")

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
