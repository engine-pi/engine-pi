# `TextBlock` (mehrzeiliger Text)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/TextBlock.java -->

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
