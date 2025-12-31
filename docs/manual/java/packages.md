# `packages` (Pakete)

Um Pakete mit gleichem Namen zu vermeiden, haben sich in der Java-Welt folgende
Konvention für Paketnamen herausgebildet:

- Paketnamen bestehen nur aus Kleinbuchstaben und Unterstrichen `_` (um sie von Klassen zu unterscheiden).
- Paketnamen sind durch Punkte getrennt.
- Der Anfang des Paketnamens wird durch die Organisation bestimmt, die sie erstellt.

Um den Paketnamen auf der Grundlage einer Organisation zu bestimmen, wird die
URL der Organisation umgedreht. Beispielsweise wird aus der URL

    https://pirckheimer-gymnasium.de/tetris

der Paketname:

    de.pirckheimer_gymnasium.tetris

<small>Quelle: [baeldung.com](https://www.baeldung.com/java-packages#1-naming-conventions)</small>

## Importe von Java-Klassen aus Paketen

Java verfügt über unzählige vorgefertigte Klassen und Schnittstellen. Thematisch zusammengehörende Klassen und
Schnittstellen werden zu einem Paket (_package_) zusammengefasst. Die so entstehende Java-Bibliothek ist riesig und
enthält tausende verschiedener Klassen mit unterschiedlichsten Methoden. Um sich einer dieser Klassen bedienen
zu können, muss man sie in das gewünschte Projekt importieren. In Java funktioniert das mit dem Schlüsselwort
`import`.

**Syntax**

`import <paketname>.<klassenname>;` Importiert nur die gewünschte Klasse des angesprochenen Paketes.<br>
`import <paketname>.*;` Importiert sämtliche Klassen des angesprochenen Paketes.

**Beispiel**

`import java.util.Random;` Importiert die Klasse Random des Paketes java.util.<br>
`import java.util.*;` Importiert das vollständige Paket java.util.

<small>Quelle: Klett, Informatik 2, 2021, Seite 275</small>
