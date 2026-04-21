from pathlib import Path

import pytest
from macros import JavaFile, CodeSnippet


@pytest.fixture
def java_file() -> JavaFile:
    return JavaFile("demos.classes.actor.StarDemo")


class TestJavaFile:
    class TestConstructor:
        def test_filepath(self) -> None:
            file = JavaFile("classes/actor/StarDemo.java")
            assert file.path == Path(
                "/data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StarDemo.java"
            )

        def test_classpath(self) -> None:
            file = JavaFile("demos.classes.actor.StarDemo")
            assert file.path == Path(
                "/data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/StarDemo.java"
            )

    class TestExceptions:
        def test_key_error(self) -> None:
            with pytest.raises(KeyError):
                JavaFile("xxxxxxx")

        def test_class_not_found(self) -> None:
            with pytest.raises(FileNotFoundError):
                JavaFile("demos/xxxxxxx")


def test_code_snippet() -> None:
    file = JavaFile("demos.classes.resources.sound.SoundsContainerContainsDemo")
    snippets = file.get_code_snippets()
    assert len(snippets) == 1

    snippet = snippets[0]

    assert snippet.start_line == 34
    assert (
        snippet.lines[0] == '        String filePath = "sounds/game-level-music.mp3";'
    )
    assert (
        snippet.lines[-1] == '        }'
    )
    assert len(snippet.lines) == 14



class TestCodeSample:
    class TestInit:
        def test_start_line(self, java_file: JavaFile) -> None:
            sample = CodeSnippet(java_file=java_file, start_line=5)
            assert sample.start_line == 5

        def test_end_line(self, java_file: JavaFile) -> None:
            sample = CodeSnippet(java_file=java_file, start_line=1, end_line=10)
            assert sample.end_line == 10

        def test_default_end_line(self, java_file: JavaFile) -> None:
            sample = CodeSnippet(java_file=java_file, start_line=1)
            assert sample.end_line == 0

        def test_java_file_reference(self, java_file: JavaFile) -> None:
            sample = CodeSnippet(java_file=java_file, start_line=1)
            assert sample.java_file is java_file

        def test_lines_with_range(self, java_file: JavaFile) -> None:
            sample = CodeSnippet(java_file=java_file, start_line=1, end_line=3)
            assert len(sample.lines) == 3

        def test_lines_start_line_only(self, java_file: JavaFile) -> None:
            total_lines = len(java_file.lines)
            sample = CodeSnippet(java_file=java_file, start_line=2, end_line=0)
            assert len(sample.lines) == total_lines - 1

        def test_fenced_code_block(self, java_file: JavaFile) -> None:
            sample = CodeSnippet(java_file=java_file, start_line=5, end_line=7)
            block = sample.fenced_code_block()
            assert 'linenums="5"' in block
            assert block.startswith("``` java")
