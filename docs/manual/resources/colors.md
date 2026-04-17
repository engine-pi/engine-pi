# `colors` (Farben)

## Farbnamen

{{ static_import_admonition('colors') }}

Folgende Zeichenketten repräsentieren die entsprechende Farbe in der Engine:[^engine-alpha-wiki:farben]

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/ColorContainer.java -->

Die zwölf Farben des Farbkreises von Itten zusammen mit ihren Aliassen bzw.
Synonymen:

- `yellow`: `Gelb`, `Hellgelb`
- `yellow orange`: `orange yellow`, `gold`, `Gelb-Orange`, `Orange-Gelb`, `Golden`, `Dunkelgelb`
- `orange`: `Orange`
- `red orange`: `orange red`, `brick red`, `brick`, `Rot-Orange`, `Orange-Rot`, `Ziegelrot`, `Hellrot`
- `red`: `Rot`
- `red purple`: `purple red`, `magenta`, `pink`, `Rot-Violett`, `Violett-Rot`, `Rosa`
- `purple`: `Violet`, `Violett`, `Lila`
- `blue purple`: `purple blue`, `indigo`, `Violett Blau`, `Blau Violett`
- `blue`: `Blau`
- `blue green`: `green blue`, `cyan`, `Blau-Grün`, `Grün-Blau`, `Türkis`
- `green`: `Grün`
- `yellow green`: `green yellow`, `lime`, `lime green`, `Gelb-Grün`, `Grün-Gelb`, `Limetten Grün`, `Limette`, `Hellgrün`

Diese Farben sind ebenfalls im Speicher für Farben enthalten (gehören aber nicht
zum Farbkreis von Itten):

- `brown`: `Braun`
- `white`: `Weiß`
- `gray`: `grey`, `Grau`
- `black`: `Schwarz`

## Farbschema

{{ static_import_admonition('colorScheme') }}


<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/ColorScheme.java -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/PredefinedColorScheme.java -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/ColorSchemeContainer.java -->

{{ video('docs/resources/colors/color-scheme/ColorScheme-Cycle.mp4') }}

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/color/ColorWheelIttenDemo.java -->

{{ code('docs/resources/color/ColorWheelIttenDemo', 52) }}

<div class="grid cards" markdown>

- {{ image('docs/resources/colors/color-scheme/Gnome.png', 'Gnome') }}
- {{ image('docs/resources/colors/color-scheme/Java.png', 'Java') }}
- {{ image('docs/resources/colors/color-scheme/Android.png', 'Android') }}
- {{ image('docs/resources/colors/color-scheme/iOS.png', 'iOS') }}
- {{ image('docs/resources/colors/color-scheme/Tailwind-CSS.png', 'Tailwind CSS') }}

</div>

## Farben über die Java-Klasse `Color`

Zur Darstellung von Farben als Zeichenketten gibt es eine Alternative, mit der
sich jede Farbe exakt maßschneidern lässt. Ein Farbton lässt sich über die
Anteile der drei Farben _Rot_, _Grün_ und _Blau_ beschreiben. Diese Anteile können
zwischen `0%` und `100%` liegen.

Man kann das als digitales Farbenmischen betrachten: Dabei werden verschiedene
Anteile der Grundfarben gemischt, um den gewünschten Farbton zu erhalten. Diese
Anteile werden jedoch nicht in Prozent, sondern als Zahlen zwischen `0` (≙ `0%`
Anteil) und `255` (≙ `100%` Anteil) angegeben. Eine Farbe lässt sich somit durch
drei Zahlen beschreiben, die die Anteile von _Rot_, _Grün_ und _Blau_ angeben. Dies
wird mit R/G/B abgekürzt.[^engine-alpha-wiki:farben]

{{ javadoc('java.desktop:java.awt.Color') }} ist eine vordefinierte Java-Klasse.
Sie beschreibt eine Farbe und lässt sich sehr leicht instanzieren. Nachfolgend
der Konstruktor der Klasse `Color`:

```java
new Color(int red, int green, int blue)
```

Die drei Zahlenwerte müssen Zahlen zwischen `0` und `255` liegen und entsprechen
den Anteilen der entsprechenden Grundfarbe, die zum „Mischen“ verwendet werden
soll.

So erhält man zum Beispiel:

<div class="grid cards" markdown>

- Schwarz

  ```java
  new Color(0, 0, 0);
  ```

- Weiß


  ```java
  new Color(255, 255, 255);
  ```

- Orange


  ```java
  new Color(255, 200, 0);
  ```

- Cyan/Türkis

  ```java
  new Color(0, 255, 255);
  ```
</div>

Natürlich sind auch alle anderen Zahlenkombinationen mit Werten zwischen `0` und `255` möglich.[^engine-alpha-wiki:farben]

### Alpha-Werte

Ein weiterer Vorteil der Klasse {{ javadoc('java.desktop:java.awt.Color') }} ist
die Möglichkeit, Farben zu einem gewissen Anteil durchsichtig zu machen. Dieser
Grad der Durchsichtigkeit wird als Alpha-Helligkeit oder `Opacity` bezeichnet.
Auch diese lässt sich mit einem Wert zwischen `0` und `255` beschreiben. Bei
einem Wert von `255` ist die Farbe vollständig sichtbar, bei einem Wert von `0`
vollständig durchsichtig, also unsichtbar.[^engine-alpha-wiki:farben]

Nachfolgend der Konstruktor der Klasse {{ javadoc('java.desktop:java.awt.Color')
}}, mit dem der Alphawert angegeben werden kann:

```java
new Color(int red, int green, int blue, int alpha)
```

<!-- Ein sehr einfaches Beispiel, es wird ein halbdurchsichtiges Rechteck erstellt:

/_ In der spielsteuernden Klasse (wegen der “wurzel“) _/

// Erstelle in der linken oberen Bildecke ein Quadrat mit der Seitenlänge 200
Rechteck recht = new Rechteck(0, 0 200, 200);

// Erstelle der Fuellfarbe
Farbe fuellFarbe = new Farbe(178, 255, 255, 200);

// Dem Quadrat die Füllfarbe zuweisen
recht.farbeSetzen(fuellFarbe);

// Das Rechteck an der Wurzel anmelden, um es sichtbar zu machen
wurzel.add(recht); -->

## Methoden und Klassen mit Farbbezug

### Klassen mit Farbbezug

- {{ javadoc('pi.resources.color.ColorContainer') }}
- {{ javadoc('pi.resources.color.ColorScheme') }}
- {{ javadoc('pi.resources.color.PredefinedColorScheme') }}
- {{ javadoc('pi.resources.color.NamedColor') }}
- [Resources.colors](https://javadoc.io/static/de.pirckheimer-gymnasium/engine-pi/0.31.0/pi/Resources.html#colors)

### Farben in der Klasse {{ javadoc('pi.actor.Actor', 'Actor') }}

Alle Figren (z.B. Text und alle Geometrie-Klassen) verfügen zum Festlegen der
Farbe über nachfolgende zwei Methoden:[^engine-alpha-wiki:farben]

- {{ javadoc('pi.actor.Actor#color(java.lang.String)', 'color(String color)') }}
- {{ javadoc('pi.actor.Actor#color(java.awt.Color)', 'color(Color color)') }}

In der ersten Reihe sind mehrere Bilder zu sehen, in der
Reihe unterhalb Rechtecke mit der Durchschnittsfarbe der Bilder, in der letzten
Reihe die Komplementärfarben der entsprechenden Bilder.

{{ image('docs/resources/colors/actor/Images_derived_complementary-color.png') }}

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/color/ImageAverageColorDemo.java -->

{{ code('docs/resources/color/ImageAverageColorDemo', 32) }}

`ALT + d` aktiviert den Debug-Modus: Die Bilder werden von Umrissen in den Komplementärfarben umrahmt.

{{ image('docs/resources/colors/actor/Images_shapes.png') }}

`Alt + a` blendet die Figurenfüllungen aus. Es sind nur noch die Umrisse zu sehen.

{{ image('docs/resources/colors/actor/Shapes-only.png') }}

[^engine-alpha-wiki:farben]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/Tutorials/Farben
