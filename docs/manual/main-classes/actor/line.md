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
Das Klassendiagramm der Figur `Line` zusammen mit der inneren Klasse `Line.End` und dem Aufzählungstyp `Line.ArrowType`.
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

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/line/LinePhysicsDemo.java -->

{{ code('demos.docs.main_classes.actor.line.LinePhysicsDemo', 34, 45) }}

## `dashPattern` (Strichmuster)

{{ image('docs/main-classes/actor/line/LineDashPatternDemo.png') }}

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/line/LineDashPatternDemo.java -->

{{ code('demos.docs.main_classes.actor.line.LineDashPatternDemo', 41, 80) }}

Mit der Setter-Methode {{ method('pi.actor.Line', 'dashPattern(double...)') }} kann das Muster der gestrichelten Linie festgelegt werden.

`#!java dashPattern(double... dashPattern)` – beschreibt die **Längen der Abschnitte** im Wechsel:

1. Argument: gezeichneter Abschnitt
2. Argument: Lücke
3. Argument: gezeichneter Abschnitt
4. Argument: Lücke
5. usw.

Beispiel: `#!java .dashPattern(1)`

1 Meter zeichnen, 1 Meter Lücke, usw.

Beispiel: `#!java .dashPattern(1, 2)`

1 Meter zeichnen, 2 Meter Lücke, 1 Meter zeichnen, 2 Meter Lücke usw.

Beispiel: `#!java .dashPattern(1, 2, 3)`

1 Meter zeichnen, 2 Meter Lücke, 3 Meter zeichnen, 1 Meter Lücke, 2 Meter zeichnen, 3 Meter Lücke, 1 Meter zeichnen usw.

Alle `dash`-Werte müssen **größer als 0** sein. Das Muster wird entlang des gesamten Pfads fortlaufend angewendet.
