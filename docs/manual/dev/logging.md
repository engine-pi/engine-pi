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
lässt sich konfigurieren, indem man eine Datei namens [logging.properties](https://logging.properties/) im
Ausführungsverzeichnis des Spiels ablegt. Weitere Informationen hierzu finden
Sie auf
[docs.oracle.com](https://docs.oracle.com/en/java/javase/25/core/java-logging-overview.html#GUID-84971801-F327-4F96-8F35-DA4D6737F857).

## Beispiel einer logging.properties-Datei

```ini
handlers=java.util.logging.FileHandler, java.util.logging.ConsoleHandler

java.util.logging.ConsoleHandler.level=WARNING
java.util.logging.ConsoleHandler.formatter=java.util.logging.SimpleFormatter

java.util.logging.FileHandler.level=WARNING
java.util.logging.FileHandler.pattern=game.log
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
java.util.logging.FileHandler.append=true
java.util.logging.FileHandler.limit = 50000
java.util.logging.FileHandler.count = 1
```


In Java wird das standardmäßige Logging-Framework JUL (java.util.logging) über eine logging.properties-Datei gesteuert. Dieses Tutorial zeigt Ihnen, wie Sie diese Datei erstellen, konfigurieren und in Ihre Anwendung einbinden. [1, 2]
1. Die logging.properties erstellen
Erstellen Sie eine Textdatei mit dem Namen logging.properties. Diese Datei definiert, wohin Logs geschrieben werden (Handlers) und wie detailliert diese sein sollen (Levels). [1, 3, 4, 5, 6]
Beispielkonfiguration:

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

2. Wichtige Konfigurationsoptionen

* Handlers: Bestimmen das Ziel der Logs (z. B. ConsoleHandler für den Bildschirm, FileHandler für Dateien).
* Levels: Steuern die Granularität. Häufige Stufen sind SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST.
* Formatter: Legen das Layout fest (z. B. SimpleFormatter für Text oder XMLFormatter).
* FileHandler Patterns: %h steht für das Home-Verzeichnis des Nutzers, %t für den temporären Ordner. [7, 8, 9, 10, 11, 12]

3. Die Datei in die Anwendung laden
Es gibt zwei gängige Wege, um Java mitzuteilen, dass es Ihre Datei verwenden soll:
Option A: Über den Start-Parameter (Empfohlen)
Geben Sie den Pfad zur Datei direkt beim Start der Java-Virtual-Machine (JVM) an:

java -Djava.util.logging.config.file=pfad/zu/logging.properties -jar MeineApp.jar

Dies ist die sauberste Methode, da kein Code geändert werden muss. [1]
Option B: Programmgesteuert im Code
Sie können die Konfiguration auch manuell innerhalb Ihrer main-Methode laden:

import java.util.logging.LogManager;import java.io.FileInputStream;
public class Main {
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(
                new FileInputStream("logging.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

Dieser Ansatz ist nützlich, wenn die Datei relativ zum Installationsverzeichnis der App liegen soll. [7, 13]
4. Logging im Code verwenden
Nachdem die Konfiguration geladen wurde, nutzen Sie den Logger wie gewohnt:

import java.util.logging.Logger;
public class MyClass {
    private static final Logger logger = Logger.getLogger(MyClass.class.getName());

    public void doSomething() {
        logger.info("Aktion wurde gestartet.");
        logger.fine("Detail-Log für Debugging.");
    }
}

Möchten Sie erfahren, wie Sie für bestimmte Pakete (z. B. nur für Datenbank-Abfragen) unterschiedliche Log-Level in der Datei festlegen?

https://share.google/aimode/z6DIbXRAaOnEVUWVI

[1] [https://www.loggly.com](https://www.loggly.com/ultimate-guide/java-logging-basics/)
[2] [https://esb-dev.github.io](https://esb-dev.github.io/mat/logging.pdf)
[3] [https://www.loggly.com](https://www.loggly.com/ultimate-guide/java-logging-basics/#:~:text=Die%20Java%20Logging%20API%20besteht%20aus%20drei,genannt%29%20schreiben%20die%20Log%2DEreignisse%20in%20ein%20Zielverzeichnis.)
[4] [https://javabeginners.de](https://javabeginners.de/Allgemeines/Logging/Logging_mit_Properties-Datei.php#:~:text=Javabeginners%20%2D%20Logging%20mit%20Properties%2DDatei.)
[5] [https://www.youtube.com](https://www.youtube.com/watch?v=9UCwNuiBDps&t=433)
[6] [https://esb-dev.github.io](https://esb-dev.github.io/mat/logging.pdf)
[7] [https://stackoverflow.com](https://stackoverflow.com/questions/960099/how-to-set-up-java-logging-using-a-properties-file-java-util-logging)
[8] [https://www.dash0.com](https://translate.google.com/translate?u=https://www.dash0.com/faq/java-util-logging-jul-the-complete-guide-to-built-in-java-logging&hl=de&sl=en&tl=de&client=sge#:~:text=Kernkomponenten%20der%20Java%2DUtil%2DProtokollierung%20Logger:%20Die%20zentrale%20Klasse%2C,Protokolleintr%C3%A4ge%20in%20formatierte%20Zeichenketten%20f%C3%BCr%20die%20Ausgabe.)
[9] [https://sematext.com](https://sematext.com/blog/java-logging/)
[10] [https://www.papertrail.com](https://translate.google.com/translate?u=https://www.papertrail.com/solution/tips/logging-in-java-best-practices-and-tips/&hl=de&sl=en&tl=de&client=sge#:~:text=Das%20in%20Java%20integrierte%20Logging%2DFramework%20%28java.util.logging%29%20bietet,Dies%20sind%20die%20vom%20Java%2DFramework%20definierten%20Standard%2DProtokollierungsstufen.)
[11] [https://last9.io](https://last9.io/blog/java-util-logging-configuration/)
[12] [https://docs.oracle.com](https://docs.oracle.com/cd/E57471_01/bigData.100/data_processing_bdd/src/rdp_logging_config.html)
[13] [https://javabeginners.de](https://javabeginners.de/Allgemeines/Logging/Logging_mit_Properties-Datei.php)
