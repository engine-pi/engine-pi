# `colors` (Farben)

## Farbnamen

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

Die Klassen mit Farbbezug:

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

{{ image('docs/resources/colors/color-scheme/Gnome.png') }}
{{ image('docs/resources/colors/color-scheme/Java.png') }}
{{ image('docs/resources/colors/color-scheme/Android.png') }}
{{ image('docs/resources/colors/color-scheme/iOS.png') }}
{{ image('docs/resources/colors/color-scheme/Tailwind-CSS.png') }}

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
