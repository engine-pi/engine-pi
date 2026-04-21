from pathlib import Path

import pytest
from macros import JavaFile


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


def test_all() -> None:
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
