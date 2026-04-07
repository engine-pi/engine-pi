# `TextBlock` (mehrzeiliger Text)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/TextBlock.java -->

{{ import_admonition('pi.actor.TextBlock') }}

## Klassendiagramm

{{ drawio('TextBlock') }}
/// caption
Die Klasse `TextBlock` und ihr Vererbungshierachie.
///

## `lineSpacing` (Zeilenabstand)

Durch die Methode
{{ javadoc('pi.actor.TextBlock#lineSpacing(double)') }} kann
der Zeilenabstand des Textblocks einstellt werden.
<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/TextBlock.java -->
<!-- Go to /data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/graphics/boxes/TextBlockBox.java -->
Der Zeilenabstand ist ein Faktor, der den Abstand zwischen den Zeilen relativ
zum Standardabstand bestimmt. Ein Wert von `1` bedeutet den normalen
Zeilenabstand, Werte größer als `1` erhöhen den Abstand, während Werte
zwischen `0` und `1` den Abstand verringern. Zum Beispiel würde ein
Wert von `1.5` den Zeilenabstand um `50%` erhöhen, während ein Wert
von `0.75` den Zeilenabstand um `25%` verringern würde.

{{ image('docs/main-classes/actor/text-block/TextBlockLineSpacingDemo.png') }}

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text_block/TextBlockLineSpacingDemo.java -->

{{ code('demos.docs.main_classes.actor.text_block.TextBlockLineSpacingDemo', 45, 53) }}

## Der Textblock in der Physik-Simulation

{{ video('docs/main-classes/actor/text-block/TextBlockPhysicsDemo.mp4') }}

Wie alle anderen Figuren kann auch die {{ class('pi.actor.TextBlock') }}-Figur in
einer Physik-Simulation verwendet werden. Das folgende Beispiel lässt den
Beispieltext auf einer Ebene abprallen. Damit der Text aus dem Spielfenster
fliegt, wird seine Fallrichtung mit einem nach rechts gerichteten Impuls
(`#!java applyImpulse(new Vector(500, 0))`) beeinflusst.

<!-- ```java
    public TextBlockPhysicsDemo()
    {
        backgroundColor("yellow");
        gravityOfEarth();

        add(new TextBlock("Zeile 1\nZeile 2\nZeile 3").height(10)
            .density(1)
            .restitution(0.95)
            .center(-9, 7)
            .color("blue")
            .rotateBy(60)
            .makeDynamic()
            .applyImpulse(new Vector(500, 0)));

        add(new Rectangle(15, 1).center(0, -7).makeStatic());
    }
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text_block/TextBlockPhysicsDemo.java -->

{{ code('demos.docs.main_classes.actor.text_block.TextBlockPhysicsDemo', 40, 55) }}
