# Getter und Setter ohne `get` and `set` Präfix

Die Engine Pi verwendet [Getter und
Setter](https://de.wikipedia.org/wiki/Zugriffsfunktion) ohne die üblichen
`get*()` und `set*()` Präfixe.

## Gründe

- Die neue Java [Record](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Record.html)-Klasse erstellt automatisch Getter ohne `get`-Präfix
- Weniger Schreibarbeit
- Getter und Setter werden in der Autovervollständig (BlueJ `Strg + Leertaste`) untereinander angezeigt
- Da unter Umständen viele Methoden mit `get*()` und `set*()` beginnen, ist es schwieriger die gewünschte Methode zu finden.
- Auch in der Java Community sind Getters und Setters Anlass zur Diskussion[^gitconnected] [^reddit]

Um zu verdeutlichen welche Methode Attributwerte gibt und welche Methode
Attribute setzt, sind alle Getter- und Setter-Methoden in den javadocs mit den
Annotationen {{ class('pi.annotations.Getter') }} und {{
class('pi.annotations.Setter') }} markiert.

Statt

```java
Circle particle = new Circle(size);
particle.setBodyType(PARTICLE);
particle.setLayerPosition(2);
particle.setColor("red");
particle.setCenter(1, 1);
particle.setGravityScale(1);
particle.setLinearDamping(range(18, 22));
particle.setLayerPosition(-1);
```

kann dann geschrieben werden:

```java
Circle particle = new Circle(size);
particle.bodyType(PARTICLE);
particle.layerPosition(2);
particle.color("red");
particle.center(1, 1);
particle.gravityScale(1);
particle.linearDamping(range(18, 22));
particle.layerPosition(-1);
```

Oder in Zusammenhang mit verketteten Methoden:

```java
Circle particle = new Circle(size);
particle.bodyType(PARTICLE)
    .layerPosition(2)
    .color("red")
    .center(1, 1)
    .gravityScale(1)
    .linearDamping(range(18, 22))
    .layerPosition(-1);
```
### Anordnung der Getter und Setter Quellcode

1. Attribute
2. Getter
3. Setter

```java
    /* color */

    protected Color color = colors.getSafe("gray");

    /**
     * Gibt die <b>Farbe</b> des Textes zurück.
     *
     * @return Die <b>Farbe</b> des Textes.
     *
     * @since 0.42.0
     */
    @Getter
    public Color color()
    {
        return color;
    }

    /**
     * Setzt die <b>Farbe</b> des Textes.
     *
     * @param color Die <b>Farbe</b> des Textes.
     *
     * @return Eine Referenz auf die eigene Instanz der Box, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften der Box durch
     *     aneinander gekettete Setter festgelegt werden können, z. B.
     *     {@code box.x(..).y(..)}.
     */
    @Setter
    public TextBox color(Color color)
    {
        this.color = color;
        return this;
    }
```

[^gitconnected]:
    https://levelup.gitconnected.com/are-getters-and-setters-an-anti-pattern-c8cb5625ca8c

    # Are Getters And Setters an Anti-Pattern?

    “Getters/Setters. Evil. Period.” — Yegor Bugayenko

[^reddit]:
    https://www.reddit.com/r/java/comments/176io2n/are_record_style_getters_and_setters_idiomatic

    # Are record style getters and setters idiomatic for wider use now?

    Its' been a while now since the introduction of records and the `fieldName()`
    getter syntax instead of `getFieldName()`. I've since seen a few projects start to
    slide to this style with POJOs or even service classes that expose some fields.

    I'm wondering if this is actually becoming the new idiom or not (with setters
    like `fieldName(newValue)` as well). I like it because it's less text to process
    when reading code so it's slightly faster for me, but I was also thinking about
    the possible ambiguity between setters, and if you wanted to have getters that
    have a selection parameter, but I think this would just be a case of having a
    more descriptive name or maybe switch to stating get just for those e.g.
    `getFieldNameForSelection(selectionParam)`.

    I'm wondering what other arguments there are for, or against, this being the new
    idiom across the board, espcially since a lot of serialisation and
    deserialisation processes are now supporting this too, or do most java devs just
    hate this idea?
