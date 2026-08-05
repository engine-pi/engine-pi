# `images` (Bilder)

{{ static_import_admonition('images') }}

## Bilder laden

Über {{ javadoc('pi.Controller#images', 'Controller.images') }} bzw. den
statischen Import `#!java import static pi.Controller.images;` werden Bilder als
{{ javadoc('java.desktop:java.awt.image.BufferedImage') }} geladen.

```java
import static pi.Controller.images;
import java.awt.image.BufferedImage;

BufferedImage moon = images.get("moon.png");
```

Der übergebene Name ist ein Ressourcenpfad relativ zum Ressourcenordner.

## Pixel vervielfältigen

Pixelgrafiken lassen sich beim Laden mit Hilfe der Methode {{
javadoc('pi.resources.ImageContainer#get(java.lang.String,int)',
'get(String, int)') }} mit einem Vergrößerungsfaktor abrufen.
Dabei wird jeder Pixel als Block vervielfacht.

```java
import static pi.Controller.images;
import java.awt.image.BufferedImage;

BufferedImage normal = images.get("box.png");
BufferedImage x2 = images.get("box.png", 2);
BufferedImage x3 = images.get("box.png", 3);
```

Zusätzlich kann global in der Konfiguration
{{ javadoc('pi.config.GraphicsConfig#pixelMultiplication()') }} gesetzt werden.

## Farben in Bildern ersetzen

Die Methode {{
javadoc('pi.resources.ImageContainer#get(java.lang.String,java.awt.Color[],java.awt.Color[])',
'get(String, Color[], Color[])') }} unterstützt das Ersetzen von
Farben direkt beim Abruf.

```java
import static pi.Controller.images;
import java.awt.Color;
import java.awt.image.BufferedImage;

Color[] from = { Color.BLUE };
Color[] to = { Color.RED };

BufferedImage recolored = images.get("box.png", from, to);
BufferedImage recoloredScaled = images.get(
    "box.png",
    2,
    from,
    to
);
```

## Bild als Figur verwenden

Die vom {{ javadoc('pi.resources.ImageContainer', 'ImageContainer') }} geladenen
Bilder als {{ javadoc('java.desktop:java.awt.image.BufferedImage') }} können in
der Figur bzw. dem Actor {{ javadoc('pi.actor.Image') }} entweder über den
Konstruktor
{{ javadoc('pi.actor.Image#<init>(java.awt.image.BufferedImage)', 'Image#Image(BufferedImage)') }}
oder die Setter-Methode
{{ javadoc('pi.actor.Image#image(java.awt.image.BufferedImage)', 'Image#image(BufferedImage)') }}
verwendet werden.

```java
import static pi.Controller.images;
import pi.Controller;
import pi.Scene;
import pi.actor.Image;
import java.awt.image.BufferedImage;

public class MyScene extends Scene
{
    public MyScene()
    {
        BufferedImage dude = images.get("dude.png")
        Image actor = new Image(dude)
            .pixelPerMeter(30)
            .center(0, 0);
        add(actor);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new MyScene());
    }
}
```

## Caching und Performance

Der Bildcontainer speichert geladene Bilder intern. Mehrfaches
`#!java images.get("...")` mit demselben Namen liefert typischerweise dieselbe
Instanz aus dem Cache. Dadurch werden Dateizugriffe reduziert und Ladezeiten
verbessert.

Bei Bedarf kann der Container geleert werden:

```java
import static pi.Controller.images;

images.clear();
```
