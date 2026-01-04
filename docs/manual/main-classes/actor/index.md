# `Actor` (Figur)

Eine Figur (engl. Actor) ist ein grafisches Objekt, das sich bewegt bzw.
das bewegt werden kann. In der Engine Pi gibt es eine Vielzahl
verschiedener Figurenarten (z. B.
{{ class('pi.actor.Image') }},
{{ class('pi.Text') }},
{{ class('pi.Rectangle') }},
{{ class('pi.Circle') }}). Alle
diese Spezialisierungen sind abgeleitet von der Oberklasse
{{ class('pi.actor.Actor') }}.
Die API-Dokumentation des Pakets {{ package('pi.actor') }}
listet alle verfügbaren Actor-Klassen auf.

Nachdem eine Figur erzeugt und zur Szene hinzugefügt wurde, befindet sie
sich an der Koordinate `(0|0)`, d.h. die linke untere Ecke der Figur -
ihr Ankerpunkt - liegt an dem Punkt im Koordinatensystem, das `0` sowohl
für den `x`- als auch den `y`-Wert der Koordinate hat.

![](https://raw.githubusercontent.com/engine-pi/engine-pi/refs/heads/main/docs/drawio/Class-diagram_Actor.drawio)
/// caption
Vererbungshierarchie `Actor`
///
