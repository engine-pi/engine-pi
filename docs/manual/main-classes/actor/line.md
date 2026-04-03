# `Line` (Linie)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Line.java -->

Die Figur {{ class('pi.actor.Line') }} ermöglicht es eine Linie zwischen zwei
Endpunkten zu zeichnen.

Die Klasse {{ class('pi.actor.Line') }} ist im
Paket {{ package('pi.actor') }} enthalten und kann über die Anweisung
`#!java import pi.actor.Line;`
importiert werden.

## Klassenkarte

{{ drawio('Line') }}
/// caption
Das Klassendiagramm der Figur `Line` zusammen mit der inneren Klasse `Line.LineEnd` und dem Aufzählungstyp `Line.ArrowType`.
///

## Eine Linie in der Physik-Simulation

{{ video('docs/main-classes/actor/line/LinePhysicsDemo.mp4') }}

Wie alle anderen Figuren kann auch die {{ class('pi.actor.Line') }}-Figur in
einer Physik-Simulation verwendet werden. Das folgende Beispiel lässt die Linie
bzw. den Pfeil in einem Bogen nach oben schießen. Damit sich der Pfeil umdreht
und mit der Pfeilspitze auf dem Boden landet, wird seine Fallrichtung mit einem
Drehimpuls (`#!java applyRotationImpulse(-7)`) beeinflusst.

<!-- ```java
    public LinePhysicsDemo()
    {
        backgroundColor("blue");
        ground.color("green");
        Line line = new Line(-9, -8, -9, -4);
        line.makeDynamic()
            .applyImpulse(25, 120)
            .applyRotationImpulse(-7)
            .color("brown");
        line.end2.arrow(true);
        add(line);
    }
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextPhysicsDemo.java -->

{{ code('demos.docs.main_classes.actor.line.LinePhysicsDemo', 34, 45) }}


## Strichmuster in Java


Mit `BasicStroke` kann eine Linie nicht nur in ihrer Breite, sondern auch als **gestricheltes Muster** gezeichnet werden.
Das Strichmuster wird über zwei Werte definiert:

- `float[] dash` – beschreibt die **Längen der Abschnitte** im Wechsel:
- 1. Wert: gezeichneter Abschnitt
- 2. Wert: Lücke
- 3. Wert: gezeichneter Abschnitt
- 4. Wert: Lücke
- usw.
- `float dash_phase` – gibt an, **an welcher Position im Muster** die Linie beginnt (Versatz).


### Funktionsweise

Beispiel: `dash = {10f, 5f}`

- 10 Pixel zeichnen
- 5 Pixel auslassen
- Muster wiederholen

Beispiel: `dash = {12f, 4f, 2f, 4f}`

- 12 Pixel zeichnen
- 4 Pixel Lücke
- 2 Pixel zeichnen
- 4 Pixel Lücke
- dann Wiederholung ab Anfang

---

### Wichtige Hinweise

- Alle `dash`-Werte müssen **größer als 0** sein.
- `dash == null` bedeutet **durchgezogene Linie** (kein Strichmuster).
- Das Muster wird entlang des gesamten Pfads fortlaufend angewendet.
- `dash_phase` verschiebt nur den Startpunkt im Muster, nicht das Muster selbst.
