# `Text` (einzeiliger Text)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/Text.java -->

## Klassenkarte

{{ drawio('Text') }}
/// caption
Das Klassenkarte der Figur `Text` mit dem Konstruktor und allen Settern.
///

## Setter

Folgendes Codebeispiel verwendet alle Setter der Figur {{ class('pi.actor.Text') }}:

<!-- /home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextAllSettersDemo.java -->

{{ code('demos.docs.main_classes.actor.text.TextAllSettersDemo', 41, 46) }}

<!-- ```java
        Text text = new Text("Alter Inhalt").content("Alle Attribute")
            .width(15.5)
            .height(3.2)
            .font("Arial")
            .style(1)
            .color("green");
``` -->

## Schriftstil

Der Stil der Schriftart (_fett_, _kursiv_ oder _fett und kursiv_) kann entweder
als Ganzzahl oder als Aufzählungstyp (enum) angegeben werden. Die Methode {{
class('pi.actor.Text', 'style(int)') }} akzeptiert Ganzzahlen als
Eingabeparameter und die überladene Methode {{ class('pi.actor.Text',
'style(pi.resources.font.FontStyle)') }} den Aufzählungstyp {{
class('pi.resources.font.FontStyle') }}. Folgende Aufzählung stellt dar, welche
Ganzzahl welchem Aufzählungstyp entspricht:

- `0`: {{ method('pi.resources.font.FontStyle', 'PLAIN') }} (normaler Text)
- `1`: {{ method('pi.resources.font.FontStyle', 'BOLD') }} (fetter Text)
- `2`: {{ method('pi.resources.font.FontStyle', 'ITALIC') }} (kursiver Text)
- `3`: {{ method('pi.resources.font.FontStyle', 'BOLD_ITALIC') }} (fett und kursiv kombiniert)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/assets/docs/main-classes/actor/text/TextStyleDemo.mp4 -->

{{ video('docs/main-classes/actor/text/TextStyleDemo.mp4') }}

<!-- /home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextStyleDemo.java -->

{{ code('demos.docs.main_classes.actor.text.TextStyleDemo', 80, 90) }}

<!-- ```java
        // Mithilfe des Aufzählungstyps FontStyle
        plain.style(FontStyle.PLAIN);
        bold.style(FontStyle.BOLD);
        italic.style(FontStyle.ITALIC);
        boldItalic.style(FontStyle.BOLD_ITALIC);

        // Oder als Ganzzahl
        plain.style(0);
        bold.style(1);
        italic.style(2);
        boldItalic.style(3);
``` -->

## Text in der Physik-Simulation

{{ video('docs/main-classes/actor/text/TextPhysicsDemo.mp4') }}

Wie alle anderen Figuren kann auch die {{ class('pi.actor.Text') }}-Figur in
einer Physik-Simulation verwendet werden. Das folgende Beispiel lässt den
Beispieltext auf einer Ebene abprallen. Damit der Text aus dem Spielfenster
fliegt, wird seine Fallrichtung mit einem nach links gerichteten Impuls
(`#!java applyImpulse(new Vector(-100, 0))`) beeinflusst.

<!-- ```java
    public TextPhysicsDemo()
    {
        backgroundColor("blue");
        gravityOfEarth();

        add(new Text("Text").font(new Font(Font.SERIF, 1, 10))
            .height(6)
            .density(1)
            .restitution(0.95)
            .center(9, 7)
            .rotateBy(60)
            .makeDynamic()
            .applyImpulse(new Vector(-100, 0)));

        add(new Rectangle(15, 1).center(0, -7).makeStatic());
    }
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/text/TextPhysicsDemo.java -->

{{ code('demos.docs.main_classes.actor.text.TextPhysicsDemo', 42, 57) }}
