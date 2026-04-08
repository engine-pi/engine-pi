# Logging

<!-- https://litiengine.com/docs/configuration/

The LITIENGINE uses the java.util.logging framework to log information
and errors. It is possible to configure the output of the logging by
providing a logging.properties file in the Game’s execution directory.
You can read more about
this HERE. -->

Die Engine Pi nutzt das
[java.util.logging](https://docs.oracle.com/en/java/javase/17/docs/api/java.logging/java/util/logging/package-summary.html)-Framework,
um Informationen und Fehler zu protokollieren. Die Ausgabe der Protokollierung
lässt sich konfigurieren, indem man eine Datei namens
[logging.properties](https://logging.properties) im Ausführungsverzeichnis des
Spiels ablegt. Diese Datei definiert, wohin Logs geschrieben werden (`Handlers`)
und wie detailliert diese sein sollen
(`Levels`).[^gemini][^loggly][^esb-dev][^javabeginners][^oracle-logging-overview][^oracle-logging-config][^stackoverflow][^dash0][^youtube][^papertrail][^sematext]

## logging.properties-Datei

```ini
# Globale Handler definieren (Konsole und Datei)
handlers = java.util.logging.ConsoleHandler, java.util.logging.FileHandler

# Standard-Log-Level für die gesamte Anwendung
.level = INFO

# Konfiguration für die Konsolenausgabe
java.util.logging.ConsoleHandler.level = ALL
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

# Konfiguration für die Dateiausgabe
java.util.logging.FileHandler.pattern = %h/java_app%u.log
java.util.logging.FileHandler.limit = 50000
java.util.logging.FileHandler.count = 1
java.util.logging.FileHandler.formatter = java.util.logging.XMLFormatter
```

Das Logging kann global über die Datei
`#!bash $JAVA_HOME/conf/logging.properties` konfiguriert werden.[^last9]
Auf Linux ist beispielsweise diese `logging.properties`-Datei im Verzeichnis
`/usr/lib/jvm/java-17-openjdk-amd64/conf/logging.properties` zu finden, wenn das
OpenJDK 17 installiert ist. Diese globale `logging.properties`-Datei ist
folgendermaßen aufgebaut:

```ini
############################################################
#  	Default Logging Configuration File
#
# You can use a different file by specifying a filename
# with the java.util.logging.config.file system property.
# For example, java -Djava.util.logging.config.file=myfile
############################################################

############################################################
#  	Global properties
############################################################

# "handlers" specifies a comma-separated list of log Handler
# classes.  These handlers will be installed during VM startup.
# Note that these classes must be on the system classpath.
# By default we only configure a ConsoleHandler, which will only
# show messages at the INFO and above levels.
handlers= java.util.logging.ConsoleHandler

# To also add the FileHandler, use the following line instead.
#handlers= java.util.logging.FileHandler, java.util.logging.ConsoleHandler

# Default global logging level.
# This specifies which kinds of events are logged across
# all loggers.  For any given facility this global level
# can be overridden by a facility-specific level
# Note that the ConsoleHandler also has a separate level
# setting to limit messages printed to the console.
.level= INFO

############################################################
# Handler specific properties.
# Describes specific configuration info for Handlers.
############################################################

# default file output is in user's home directory.
java.util.logging.FileHandler.pattern = %h/java%u.log
java.util.logging.FileHandler.limit = 50000
java.util.logging.FileHandler.count = 1
# Default number of locks FileHandler can obtain synchronously.
# This specifies maximum number of attempts to obtain lock file by FileHandler
# implemented by incrementing the unique field %u as per FileHandler API documentation.
java.util.logging.FileHandler.maxLocks = 100
java.util.logging.FileHandler.formatter = java.util.logging.XMLFormatter

# Limit the messages that are printed on the console to INFO and above.
java.util.logging.ConsoleHandler.level = INFO
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

# Example to customize the SimpleFormatter output format
# to print one-line log message like this:
#     <level>: <log message> [<date/time>]
#
# java.util.logging.SimpleFormatter.format=%4$s: %5$s [%1$tc]%n

############################################################
# Facility-specific properties.
# Provides extra control for each logger.
############################################################

# For example, set the com.xyz.foo logger to only log SEVERE
# messages:
# com.xyz.foo.level = SEVERE
```

## Wichtige Konfigurationsoptionen

* `Handlers`: Bestimmen das Ziel der Logs (z. B. `ConsoleHandler` für den Bildschirm, `FileHandler` für Dateien).
* `Levels`: Steuern die Granularität. Häufige Stufen sind `SEVERE`, `WARNING`, `INFO`, `CONFIG`, `FINE`, `FINER`, `FINEST`.
* `Formatter`: Legen das Layout fest (z. B. `SimpleFormatter` für Text oder `XMLFormatter`).
* `FileHandler` patterns: `%h` steht für das Home-Verzeichnis des Nutzers, `%t` für den temporären Ordner.

## Logger im Code verwenden

Die Klasse
[Logger](https://docs.oracle.com/en/java/javase/17/docs/api/java.logging/java/util/logging/Logger.html)
ist der zentrale Einstiegspunkt fuer Logging in Java.

```java
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingExample {
    private static final Logger LOG =
	    Logger.getLogger(LoggingExample.class.getName());

    public void run() {
	LOG.info("Anwendung gestartet");
	LOG.warning("Konfiguration ist unvollstaendig");
	LOG.log(Level.SEVERE, "Unerwarteter Fehler");
    }
}
```

## Wichtige Logging-Methoden von Logger

### Logger erzeugen

Mit `#!java getLogger(String name)` wird ein benannter Logger geholt.
In der Praxis wird oft der vollqualifizierte Klassenname verwendet.

```java
private static final Logger LOG =
	Logger.getLogger(MyClass.class.getName());
```

### Direkte Level-Methoden

Diese Methoden sind kurz und lesbar und entsprechen festen Log-Leveln:

* `severe(String msg)`
* `warning(String msg)`
* `info(String msg)`
* `config(String msg)`
* `fine(String msg)`
* `finer(String msg)`
* `finest(String msg)`

```java
LOG.severe("Datenbank nicht erreichbar");
LOG.warning("Antwortzeit erhöht");
LOG.info("Spielstand geladen");
LOG.fine("Physikschritt abgeschlossen");
```

### Flexible Methode log(...)

Mit `#!java log(...)` kann das `Level` dynamisch gesetzt werden.

```java
LOG.log(Level.INFO, "Szene geladen: {0}", sceneName);
LOG.log(Level.SEVERE, "Fehler beim Laden", exception);
```

Typische Varianten:

* `log(Level level, String msg)`
* `log(Level level, String msg, Object param1)`
* `log(Level level, String msg, Object[] params)`
* `log(Level level, String msg, Throwable thrown)`

## Best Practices

* Logger als `private static final` pro Klasse definieren.
* Exceptions immer mit Throwable-Parameter loggen, nicht nur als Text.
* Für produktive Systeme `FINE`, `FINER`, `FINEST` nur gezielt aktivieren.
* Keine sensitiven Daten (Passwoerter, Tokens, personenbezogene Daten)
  in Logs schreiben.

## Logging in Engine Pi

{{ code("demos.classes.class_controller.LoggingDemo", from_import=true) }}

[^gemini]: https://share.google/aimode/z6DIbXRAaOnEVUWVI
[^loggly]: https://www.loggly.com/ultimate-guide/java-logging-basics/
[^esb-dev]: https://esb-dev.github.io/mat/logging.pdf
[^javabeginners]: https://javabeginners.de/Allgemeines/Logging/Logging_mit_Properties-Datei.php
[^oracle-logging-overview]: https://docs.oracle.com/en/java/javase/17/core/java-logging-overview.html#GUID-B83B652C-17EA-48D9-93D2-563AE1FF8EDA
[^oracle-logging-config]: https://docs.oracle.com/cd/E57471_01/bigData.100/data_processing_bdd/src/rdp_logging_config.html
[^stackoverflow]: https://stackoverflow.com/questions/960099/how-to-set-up-java-logging-using-a-properties-file-java-util-logging
[^dash0]: https://www.dash0.com/faq/java-util-logging-jul-the-complete-guide-to-built-in-java-logging
[^youtube]: https://www.youtube.com/watch?v=9UCwNuiBDps&t=433
[^papertrail]: https://www.papertrail.com/solution/tips/logging-in-java-best-practices-and-tips
[^sematext]:(https://sematext.com/blog/java-logging)
[^last9]: https://last9.io/blog/java-util-logging-configuration
