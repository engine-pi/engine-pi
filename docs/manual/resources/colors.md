# `colors` (Farben)

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

Quellcode: [demos/actor/ImageAverageColorDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/actor/ImageAverageColorDemo.java)

```java
import pi.Game;
import pi.Scene;

public class ImageAverageColorDemo extends Scene
{
    public ImageAverageColorDemo()
    {
        getCamera().setMeter(90);
        double x = -4;
        for (String filepath : new String[] { "car/background-color-grass.png",
                "car/wheel-back.png", "car/truck-240px.png",
                "dude/background/snow.png", "dude/box/obj_box001.png",
                "dude/moon.png" })
        {
            createImageWithAverageColor(filepath, x);
            x = x + 1.2;
        }
    }

    private void createImageWithAverageColor(String filepath, double x)
    {
        var image = createImage(filepath, 1, 1).setPosition(x, 0);
        createRectangle(1.0, 1.0).setPosition(x, -1.2)
                .setColor(image.getColor());
        createRectangle(1.0, 0.5).setPosition(x, -1.9)
                .setColor(image.getComplementaryColor());
    }

    public static void main(String[] args)
    {
        Game.start(new ImageAverageColorDemo());
    }
}
```

`ALT + d` aktiviert den Debug-Modus: Die Bilder werden von Umrissen in den Komplementärfarben umrahmt.

{{ image('docs/resources/colors/actor/Images_shapes.png') }}

`Alt + a` blendet die Figurenfüllungen aus. Es sind nur noch die Umrisse zu sehen.

{{ image('docs/resources/colors/actor/Shapes-only.png') }}
