# `Actor` (Figur)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/package-info.java -->

Eine Figur (engl. Actor) ist ein grafisches Objekt, das sich bewegt bzw.
das bewegt werden kann. In der Engine Pi gibt es eine Vielzahl
verschiedener Figurenarten (z.B.
{{ javadoc('pi.Image') }},
{{ javadoc('pi.Text') }},
{{ javadoc('pi.Rectangle') }},
{{ javadoc('pi.Circle') }}). Alle
diese Spezialisierungen sind abgeleitet von der Oberklasse
{{ javadoc('pi.actor.Actor') }}.
Die API-Dokumentation des Pakets {{ package('pi.actor') }}
listet alle verfügbaren Actor-Klassen auf.

{{ import_admonition('pi.actor.Actor') }}

Nachdem eine Figur erzeugt und zur Szene hinzugefügt wurde, befindet sie
sich an der Koordinate `(0|0)`, d.h. die linke untere Ecke der Figur -
ihr Ankerpunkt - liegt an dem Punkt im Koordinatensystem, das `0` sowohl
für den `x`- als auch den `y`-Wert der Koordinate hat.

{{ drawio('Actor') }}
/// caption
Vererbungshierarchie `Actor`
///

## `opacity` (Durchsichtigkeit)

Über den Setter
{{ javadoc('pi.actor.Actor#opacity(double)') }}
dann die
Durchsichtigkeit bzw. Deckkraft der Figur eingestellt werden. Der
Eingabeparameter akzeptiert Kommazahlen (`double`) im Bereich von `0` bis `1`.
Bei `1` ist die Figur undurchsichtig, bei `0` komplett transparent. Der
gleichlautende Getter
{{ javadoc('pi.actor.Actor#opacity()') }}
liefert den
aktuellen `opacity`-Wert. Neu erzeugte Figuren haben den `opacity`-Wert von `1`.
Sie sind also undurchsichtig.


<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/ActorOpacityDemo.java -->

{{ image('docs/main-classes/actor/ActorOpacityDemo.png') }}
/// caption
Die linke Figur ist undurchsichtig, die rechte komplett transparent. Vom links nach rechts nimmt die Durchsichtigkeit um 0.2 pro Bild zu.
///

{{ code('demos.docs.main_classes.actor.ActorOpacityDemo', 40, 62) }}
