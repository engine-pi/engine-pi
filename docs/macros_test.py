from pathlib import Path
from typing import Optional

import pytest
from macros import JavaFile, Snippet


# Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StarDemo.java


def java_file(
    path: str = "demos.classes.actor.StarDemo",
    content: Optional[str] = None,
    lines: Optional[list[str]] = None,
) -> JavaFile:
    java_file = JavaFile(path)

    if content is not None and lines is not None:
        raise Exception("specify content OR lines not both")
    if content is not None:
        java_file.lines = content.splitlines()
    elif lines is not None:
        java_file.lines = lines

    return java_file


class TestSnippet:
    def test_is_start_marker(self) -> None:
        assert Snippet.is_start_marker("//-->")
        assert Snippet.is_start_marker("  // -->")
        assert Snippet.is_start_marker("// -->after")

        assert not Snippet.is_start_marker("//->")
        assert not Snippet.is_start_marker("/-->")
        assert not Snippet.is_start_marker("abc//-->")

    def test_is_end_marker(self) -> None:
        assert Snippet.is_end_marker("//<--")
        assert Snippet.is_end_marker("  // <--")
        assert Snippet.is_end_marker("// <--after")

        assert not Snippet.is_end_marker("/<--")
        assert not Snippet.is_end_marker("//<-")

    def test_is_line_comment(self):
        assert Snippet.is_line_comment("//")
        assert Snippet.is_line_comment("  //")
        assert Snippet.is_line_comment("//after")

        assert not Snippet.is_line_comment("")
        assert not Snippet.is_line_comment("abc")
        assert not Snippet.is_line_comment("/**/")
        assert not Snippet.is_line_comment("abc//")


class TestJavaFile:
    class TestConstructor:
        def test_filepath(self) -> None:
            file = JavaFile("classes/actor/StarDemo.java")
            assert file.path == Path(
                "/data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StarDemo.java"
            )

        def test_filepath_without_extension(self) -> None:
            file = JavaFile("classes/actor/StarDemo")
            assert file.path == Path(
                "/data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StarDemo.java"
            )

        def test_classpath(self) -> None:
            file = JavaFile("demos.classes.actor.StarDemo")
            assert file.path == Path(
                "/data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StarDemo.java"
            )

        def test_lines_are_loaded(self) -> None:
            file = java_file()
            assert len(file.lines) == 46

        def test_first_line(self) -> None:
            file = java_file()
            assert file.lines[0] == "/*"

        def test_last_line(self) -> None:
            file = java_file()
            assert file.lines[-1] == "}"

    class TestExceptions:
        def test_key_error(self) -> None:
            with pytest.raises(KeyError):
                JavaFile("xxxxxxx")

        def test_class_not_found(self) -> None:
            with pytest.raises(FileNotFoundError):
                JavaFile("demos/xxxxxxx")

    class TestGetFirstImportStatement:
        def test_returns_line_number_of_first_import(self) -> None:
            # StarDemo.java: first import is on line 21
            file = java_file()
            assert file.get_first_import_statement() == 21

        def test_returns_line_count_plus_one_when_no_imports(self) -> None:
            file = java_file()
            # Replace lines with content that has no import statements
            file.lines = ["package foo;", "public class Foo {}"]
            result = file.get_first_import_statement()
            assert result == 3  # counter ends at len(lines) + 1

    class TestGetCodeSample:
        def test_default_returns_all_lines(self) -> None:
            file = java_file()
            sample = file.define_snippet()
            assert isinstance(sample, Snippet)
            assert sample.start_line_no == 1
            assert sample.end_line_no == 46

        def test_start_line_only(self) -> None:
            file = java_file()
            sample = file.define_snippet(start_line_no=21)
            assert sample.start_line_no == 21
            assert sample.end_line_no == 46

        def test_end_line_only(self) -> None:
            file = java_file()
            sample = file.define_snippet(end_line_no=22)
            assert sample.start_line_no == 1
            assert sample.end_line_no == 22

        def test_start_and_end_line(self) -> None:
            file = java_file()
            sample = file.define_snippet(start_line_no=21, end_line_no=22)
            assert sample.start_line_no == 21
            assert sample.end_line_no == 22
            assert sample.lines == ["import pi.Controller;", "import pi.actor.Star;"]

        def test_line_parameter(self) -> None:
            file = java_file()
            sample = file.define_snippet(line_no=21)
            assert sample.start_line_no == 21
            assert sample.end_line_no == 21
            assert sample.lines == ["import pi.Controller;"]

        def test_from_import(self) -> None:
            file = java_file()
            sample = file.define_snippet(from_import=True)
            assert sample.start_line_no == 21
            assert sample.end_line_no == 46

        def test_from_import_exclusive_with_start_line(self) -> None:
            file = java_file()
            with pytest.raises(Exception, match="exclusive"):
                file.define_snippet(from_import=True, start_line_no=5)

        def test_from_import_exclusive_with_end_line(self) -> None:
            file = java_file()
            with pytest.raises(Exception, match="exclusive"):
                file.define_snippet(from_import=True, end_line_no=30)

        def test_from_import_exclusive_with_line(self) -> None:
            file = java_file()
            with pytest.raises(Exception, match="exclusive"):
                file.define_snippet(from_import=True, line_no=21)

        def test_empty_start_line_raises(self) -> None:
            # Line 20 of StarDemo.java is empty
            file = java_file()
            with pytest.raises(Exception, match="Start line"):
                file.define_snippet(start_line_no=20)

        def test_empty_end_line_raises(self) -> None:
            # Line 20 of StarDemo.java is empty
            file = java_file()
            with pytest.raises(Exception, match="End line"):
                file.define_snippet(end_line_no=20)

    class TestGetCodeSnippets:
        def test_no_markers_returns_empty_list(self) -> None:
            file = java_file(lines=["line one", "line two", "line three"])
            assert file.get_all_snippets() == []

        def test_single_closed_snippet(self) -> None:
            file = java_file(
                lines=[
                    "line one",
                    "// --> This is a prolog text",
                    "// continuation",
                    "snippet line 1",
                    "snippet line 2",
                    "// end <--",
                    "line six",
                ]
            )
            snippets = file.get_all_snippets()
            assert len(snippets) == 1
            snippet = snippets[0]
            assert isinstance(snippet, Snippet)
            assert snippet.prolog == ["This is a prolog text", "continuation"]
            assert snippet.epilog == ["end"]

            assert snippet.start_line_no == 4
            assert snippet.lines == ["snippet line 1", "snippet line 2"]

        def test_open_ended_snippet(self) -> None:
            # No closing marker → snippet runs to end of file
            file = java_file(
                lines=[
                    "line one",
                    "// -->",
                    "snippet line 1",
                    "snippet line 2",
                ]
            )
            snippets = file.get_all_snippets()
            assert len(snippets) == 1
            assert snippets[0].start_line_no == 3
            assert snippets[0].lines == ["snippet line 1", "snippet line 2"]

        def test_multiple_closed_snippets(self) -> None:
            file = java_file(
                lines=[
                    "// -->",  # index 0 → start_line = 1
                    "first snippet",  # index 1
                    "// a <--",  # index 2 → end_line = 1, lines[1:1] = []
                    "middle",
                    "// -->",  # index 4 → start_line = 5
                    "second snippet",  # index 5
                    "// b <--",  # index 6 → end_line = 5, lines[5:5] = []
                ]
            )
            snippets = file.get_all_snippets()
            assert len(snippets) == 2

    class TestUrl:
        def test_returns_github_url(self) -> None:
            file = java_file()
            url = file.url()
            assert url.startswith("https://github.com/engine-pi/engine-pi/blob/main/")
            assert "StarDemo.java" in url
