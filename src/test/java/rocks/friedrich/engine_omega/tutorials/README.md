Engine Alpha Tutorials
==================

Dieses Repository enthält die Quellen zu allen einfachen Tutorials für die Engine.
Die Dateien hierin sind im Regelfall funktionierende Beispiele.


So sind die Tutorials zu benutzen
--------------------

Die Tutorials werden schrittweise erläutert auf der 
[Engine-Alpha-Tutorialseite](https://engine-alpha.org/wiki/v4.x/Tutorials). 
Wer den Umgang mit der Engine lernen möchte, sollte dort starten.

Die Tutorials sind unkommentiert. Sie wurden konzipiert, um auch ohne Kommentare nachvollziehbar zu
sein und sollen stattdessen eher zum eigenen, konzentrierten Nachvollziehen anregen.


Repository-Struktur 
--------------------

Im Verzeichnis `src/eatutorials` sind alle Beispiele in einem eigenen Unterverzeichnis angelegt. 
Der Code in jedem Verzeichnis ist unabhängig vom Rest der Files ausführbar.


Ausführbarkeit
------
Das Repository enthält keine IDE-Projektstrukturen oder Dependencies. 
Daher ist der Code in diesem Repository **nicht ohne Weiteres ausführbar**.
Wenn das Repo an sich ausgeführt werden soll, so **muss die Engine-Bibliothek in das Projekt eingebettet werden** 
(sowie ggf. das Java JDK).

Stattdessen können die einzelnen Beispielverzeichnisse (bzw. Beispiel-Files) auch in ein Projekt eingefügt werden,
das bereits die Engine eingebettet hat.