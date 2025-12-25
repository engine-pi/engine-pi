# Figur (Actor)

Eine Figur (engl. Actor) ist ein grafisches Objekt, das sich bewegt bzw.
das bewegt werden kann. In der Engine Pi gibt es eine Vielzahl
verschiedener Figurenarten (z. B.
[Image](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Image.html),
[Text](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Text),
[Rectangle](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Rectangle),
[Circle](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Circle)). Alle
diese Spezialisierungen sind abgeleitet von der Oberklasse
[Actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/Actor.html)
Die API-Dokumentation des Pakets
[pi.actor](https://javadoc.io/doc/de.pirckheimer-gymnasium/engine-pi/latest/pi/actor/package-summary.html)
listet alle verfügbaren Actor-Klassen auf.

Nachdem eine Figur erzeugt und zur Szene hinzugefügt wurde, befindet sie
sich an der Koordinate (0|0), d. h. die linke untere Ecke der Figur -
ihr Ankerpunkt - liegt an dem Punkt im Koordinatensystem, das 0 sowohl
für den x- als auch den y-Wert der Koordinate hat.
