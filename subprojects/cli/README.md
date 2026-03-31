# Command Line Tool

Command line tool for the Engine Pi development
(Ein Programm auf der Kommandozeile für die Entwicklung von Engine Pi)

## Unified CLI

Both tools are available from one program with subcommands:

- `checklinks`
- `java2umltext`

Run from repository root:

```bash
mvn -pl subprojects/cli -DskipTests compile
mvn -q -pl subprojects/cli -DincludeScope=runtime dependency:build-classpath -Dmdep.outputFile=/tmp/engine-pi-cli.cp
java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client --help
```

Examples:

```bash
java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client checklinks .
java -cp "subprojects/cli/target/classes:$(cat /tmp/engine-pi-cli.cp)" cli.Client java2umltext plantuml subprojects/engine/src/main/java
```
