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
