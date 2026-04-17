## Importe von Java-Klassen aus Paketen

Java verfügt über unzählige vorgefertigte Klassen und Schnittstellen. Thematisch
zusammengehörende Klassen und Schnittstellen werden zu einem Paket (_package_)
zusammengefasst. Die so entstehende Java-Bibliothek ist riesig und enthält
tausende verschiedener Klassen mit unterschiedlichsten Methoden. Um sich einer
dieser Klassen bedienen zu können, muss man sie in das gewünschte Projekt
importieren. In Java funktioniert das mit dem Schlüsselwort `#!java import`.[^klett]

**Syntax**

`#!java import <paketname>.<klassenname>;` Importiert nur die gewünschte Klasse des angesprochenen Paketes.<br>
`#!java import <paketname>.*;` Importiert sämtliche Klassen des angesprochenen Paketes.

**Beispiel**

`#!java import java.util.Random;` Importiert die Klasse Random des Paketes java.util.<br>
`#!java import java.util.*;` Importiert das vollständige Paket java.util.

[^klett]: Klett, Informatik 2, 2021, Seite 275
