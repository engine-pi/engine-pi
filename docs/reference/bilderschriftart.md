# Bilderschriftart

{{ image('docs/actor/ImageFontTextMultilineDemo.png') }}

Quellcode: [demos/actor/ImageFontTextMultilineDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/actor/ImageFontTextMultilineDemo.java)

```java
public class ImageFontTextMultilineDemo extends Scene
{
    public ImageFontTextMultilineDemo()
    {
        ImageFont font = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
        ImageFontText textField = new ImageFontText(font,
                "Das ist ein laengerer Text, der in mehrere Zeilen unterteilt ist. "
                        + "Zeilenumbrueche\nkoennen auch\nerzwungen werden.",
                20, TextAlignment.LEFT);
        add(textField);
        setBackgroundColor("white");
        setFocus(textField);
    }
}
```

{{ image('docs/actor/ImageFontTextAlignmentDemo.png') }}

Quellcode: [demos/actor/ImageFontTextAlignmentDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/actor/ImageFontTextAlignmentDemo.java)

```java
import pi.Game;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

public class ImageFontTextAlignmentDemo extends Scene
{
    ImageFont font = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    public ImageFontTextAlignmentDemo()
    {
        getCamera().setMeter(32);
        setBackgroundColor("white");
        createTextLine(3, "Dieser Text ist linksbuendig ausgerichtet.", LEFT);
        createTextLine(-2, "Dieser Text ist zentriert ausgerichtet.", CENTER);
        createTextLine(-7, "Dieser Text ist rechtsbuendig ausgerichtet.",
                RIGHT);
    }

    private void createTextLine(int y, String content, TextAlignment alignment)
    {
        ImageFontText line = new ImageFontText(font, content, 18, alignment);
        line.setPosition(-9, y);
        add(line);
    }
}
```

{{ image('docs/actor/ImageFontTextColorDemo.png') }}

Quellcode: [demos/actor/ImageFontTextColorDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/actor/ImageFontTextColorDemo.java)

```java
public class ImageFontTextColorDemo extends Scene
{
    ImageFont font = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    public ImageFontTextColorDemo()
    {
        setBackgroundColor("#eeeeee");
        int y = 9;
        for (Map.Entry<String, Color> entry : Resources.COLORS.getAll()
                .entrySet())
        {
            setImageFontText(entry.getKey(), -5, y);
            y--;
        }
    }

    public void setImageFontText(String color, int x, int y)
    {
        ImageFontText textField = new ImageFontText(font, color, color);
        textField.setPosition(x, y);
        add(textField);
    }
}
```

{{ image('docs/actor/ImageFontTextTetris.png') }}

Quellcode: [tetris: scenes/CopyrightScene.java](https://github.com/engine-pi/tetris/blob/main/src/main/java/de/pirckheimer_gymnasium/tetris/scenes/CopyrightScene.java)

```java
public class CopyrightScene extends BaseScene implements KeyStrokeListener
{
    public CopyrightScene()
    {
        super(null);
        setBackgroundColor(Tetris.COLOR_SCHEME_GREEN.getWhite());
        String origText = "\"TM and ©1987 ELORG,\n" + //
                "Tetris licensed to\n" + //
                "Bullet-Proof\n" + //
                "software and\n" + //
                "sub-licensed to\n" + //
                "Nintendo.\n" + //
                "\n" + //
                "©1989 Bullet-Proof\n" + //
                "software\n" + //
                "©1989 Nintendo\n" + //
                "\n" + //
                "All rights reserved.\n" + //
                "\n" + //
                "original concept\n" + //
                "design and programm\n" + //
                // Im Original: by Alexey Pazhitnov."
                // ." kann mit ImageFont nicht als ein Zeichen dargestellt werden.
                "by Alexey Pazhitnov\"\n" + "\n";
        ImageFontText text = new ImageFontText(Font.getFont(), origText, 21,
                TextAlignment.CENTER);
        text.setPosition(-2, 0);
        add(text);
        delay(4, this::startTitleScene);
    }
}
```
