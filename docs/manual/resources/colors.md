# `colors` (Farben)

## Farbnamen

Folgende Zeichenketten repräsentieren die entsprechende Farbe in der Engine:[^engine-alpha-wiki:farben]

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/ColorContainer.java -->

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

## Farben über RGB

Zur Darstellung von Farben als Zeichenketten gibt es eine Alternative, mit der
sich jede Farbe exakt maßschneidern lässt. Ein Farbton lässt sich über die
Anteile der drei Farben Rot, Grün und Blau beschreiben. Diese Anteile können
zwischen 0 und 100 % liegen.

Man kann das als digitales Farbenmischen betrachten: Man mischt verschiedene
Anteile der Grundfarben zusammen, um den gewünschten Farbton zu erhalten. Diese
Anteile werden jedoch nicht in Prozent, sondern als Zahlen zwischen 0 (≙ 0 %
Anteil) und 255 (≙ 100 % Anteil) angegeben. Eine Farbe lässt sich also durch
drei Zahlen beschreiben, die die Anteile von Rot, Grün und Blau angeben. Dies
wird mit R/G/B abgekürzt.[^engine-alpha-wiki:farben]

{{ java_class('java.awt.Color', module='java.desktop') }} ist eine vordefinierte Java-Klasse. Sie beschreibt eine Farbe und lässt sich sehr leicht instanzieren. Nachfolgend der Konstruktor der Klasse `Color`:

```java
Color(int r, int g, int b)
```

Die drei Zahlenwerte müssen Zahlen zwischen 0 und 255 liegen und entsprechen den Anteilen der entsprechenden Grundfarbe, die zum "Mischen" verwendet werden soll.

So erhält man zum Beispiel:

<div class="grid cards" markdown>

-   Schwarz

    ---

    ```java
    new Color(0, 0, 0);
    ```

-   Weiß

    ---

    ```java
    new Color(255, 255, 255);
    ```

-   Orange

    ---

    ```java
    new Color(255, 200, 0);
    ```

-   Cyan/Türkis

    ---

    ```java
    new Color(0, 255, 255);
    ```
</div>

Natürlich sind auch alle anderen Zahlenkombinationen mit Werten zwischen 0 und 255 möglich.[^engine-alpha-wiki:farben]

## Methoden zum Färben

Alle färbbaren Klassen (z. B. Text und alle Geometrie-Klassen) verfügen zum Festlegen der Farbe über nachfolgende zwei Methoden:[^engine-alpha-wiki:farben]

- {{ method('pi.actor.Actor', 'color(java.lang.String)', 'color(String color)') }}
- {{ method('pi.actor.Actor', 'color(java.awt.Color)', 'color(Color color)') }}

## Alpha-Werte

Ein weiterer Vorteil der Klasse Farbe ist die Möglichkeit, Farben zu einem gewissen Anteil durchsichtig machen zu können. Die "Nichtdurchsichtigkeit" (oder auch Opacity) einer Farbe wird als Alpha-Helligkeit bezeichnet. Auch diese lässt sich mit einem Wert zwischen 0 und 255 beschreiben. Bei einem Wert von 255 ist die Farbe gänzlich sichtbar und bei einem Wert von 0 absolut durchsichtig, d. h. unsichtbar. Alle Werte dazwischen sorgen für eine unterschiedlich stark durchsichtige, geister- oder glasartige Farbe.[^engine-alpha-wiki:farben]



Hierfür gibt es dann den vollen Konstruktor der Klasse Farbe:

public Farbe(int r, int g, int b, int alpha)


Ein sehr einfaches Beispiel, es wird ein halbdurchsichtiges Rechteck erstellt:

/* In der spielsteuernden Klasse (wegen der “wurzel“) */

// Erstelle in der linken oberen Bildecke ein Quadrat mit der Seitenlänge 200
Rechteck recht = new Rechteck(0, 0 200, 200);

// Erstelle der Fuellfarbe
Farbe fuellFarbe = new Farbe(178, 255, 255, 200);

// Dem Quadrat die Füllfarbe zuweisen
recht.farbeSetzen(fuellFarbe);

// Das Rechteck an der Wurzel anmelden, um es sichtbar zu machen
wurzel.add(recht);

## Die Klassen mit Farbbezug:

- {{ class('pi.resources.color.ColorContainer') }}
- {{ class('pi.resources.color.ColorScheme') }}
- {{ class('pi.resources.color.PredefinedColorScheme') }}
- {{ class('pi.resources.color.NamedColor') }}
- [Resources.colors](https://javadoc.io/static/de.pirckheimer-gymnasium/engine-pi/0.31.0/de/pirckheimer_gymnasium/engine_pi/Resources.html#colors)

## Farbschema

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/ColorScheme.java -->

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/PredefinedColorScheme.java -->

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/engine/src/main/java/pi/resources/color/ColorSchemeContainer.java -->

{{ video('docs/resources/colors/color-scheme/ColorScheme-Cycle.mp4') }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/color/ColorWheelIttenDemo.java -->

{{ code('docs/resources/color/ColorWheelIttenDemo', 52) }}


<div class="grid cards" markdown>

- {{ image('docs/resources/colors/color-scheme/Gnome.png', 'Gnome') }}
- {{ image('docs/resources/colors/color-scheme/Java.png', 'Java') }}
- {{ image('docs/resources/colors/color-scheme/Android.png', 'Android') }}
- {{ image('docs/resources/colors/color-scheme/iOS.png', 'iOS') }}
- {{ image('docs/resources/colors/color-scheme/Tailwind-CSS.png', 'Tailwind CSS') }}

</div>

## Farben in der Klasse {{ class('pi.actor.Actor', 'Actor') }}

In der ersten Reihe sind mehrere Bilder zu sehen, in der
Reihe unterhalb Rechtecke mit der Durchschnittsfarbe der Bilder, in der letzten
Reihe die Komplementärfarben der entsprechenden Bilder.

{{ image('docs/resources/colors/actor/Images_derived_complementary-color.png') }}

<!-- Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/resources/color/ImageAverageColorDemo.java -->

{{ code('docs/resources/color/ImageAverageColorDemo', 32) }}

`ALT + d` aktiviert den Debug-Modus: Die Bilder werden von Umrissen in den Komplementärfarben umrahmt.

{{ image('docs/resources/colors/actor/Images_shapes.png') }}

`Alt + a` blendet die Figurenfüllungen aus. Es sind nur noch die Umrisse zu sehen.

{{ image('docs/resources/colors/actor/Shapes-only.png') }}

[^engine-alpha-wiki:farben]:
    Der Abschnitt stammt aus dem
    Engine-Alpha-Wiki: https://engine-alpha.org/wiki/Tutorials/Farben
