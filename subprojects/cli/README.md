# Command Line Tool

Command line tool for the Engine Pi development
(Ein Programm auf der Kommandozeile für die Entwicklung von Engine Pi)

## Check file links

Checks all `.java` and Markdown files for `file://` links and verifies that
their target files exist.

Run from repository root:

```bash
mvn -pl subprojects/cli -DskipTests compile
java -cp subprojects/cli/target/classes:$(mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputAbsoluteArtifactFilename=true -Dmdep.path) cli.checklinks.FileLinkChecker .
```

Exit code is `0` when all links are valid and `1` when broken links are found.
