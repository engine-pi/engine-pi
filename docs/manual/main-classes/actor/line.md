# `Line` (Linie)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Line.java -->

{{ class('pi.actor.Line') }}

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
