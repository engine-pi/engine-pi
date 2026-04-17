# `design pattern` (Entwurfsmuster)

## Entwurfsmuster `template methodc` (Schablonenmethode)

Beim Schablonenmethoden-Entwurfsmuster wird in einer abstrakten Klasse das
Skelett eines Algorithmus definiert. Die konkrete Ausformung der einzelnen
Schritte wird an Unterklassen delegiert. Dadurch besteht die Möglichkeit,
einzelne Schritte des Algorithmus zu verändern oder zu überschreiben, ohne dass
die zu Grunde liegende Struktur des Algorithmus modifiziert werden muss. Die
Schablonenmethode (engl. template method) ruft abstrakte Methoden auf, die erst
in den Unterklassen definiert werden. Diese Methoden werden auch als
Einschubmethoden bezeichnet.[^wikipedia]

[^wikipedia]: https://de.wikipedia.org/wiki/Schablonenmethode
