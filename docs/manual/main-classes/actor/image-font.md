# `ImageFont` (Bilderschriftart)

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/engine/src/main/java/pi/actor/ImageFont.java -->

{{ import_admonition('pi.actor.ImageFont') }}

## `multiline` (mehrzeilig)

{{ image('docs/actor/ImageFontTextMultilineDemo.png') }}

<!-- ```java
public class MultilineDemo extends Scene
{
    public MultilineDemo()
    {
        ImageFont font = new ImageFont("image-font/tetris",
                ImageFontCaseSensitivity.TO_UPPER);
        ImageFontText textField = new ImageFontText(font,
                "Das ist ein laengerer Text, der in mehrere Zeilen unterteilt ist. "
                        + "Zeilenumbrueche\nkoennen auch\nerzwungen werden.",
                20, TextAlignment.LEFT);
        add(textField);
        backgroundColor("white");
        focus(textField);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new MultilineDemo());
    }
}
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_font/MultilineDemo.java -->

{{ code('demos.classes.actor.image_font.MultilineDemo', start_line=43) }}

## `TextAlignment` (Textausrichtung)

{{ image('docs/actor/ImageFontTextAlignmentDemo.png') }}

<!-- ```java
import static pi.util.TextAlignment.CENTER;
import static pi.util.TextAlignment.LEFT;
import static pi.util.TextAlignment.RIGHT;

import pi.Controller;
import pi.Scene;
import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;
import pi.actor.ImageFontText;
import pi.util.TextAlignment;

public class AlignmentDemo extends Scene
{
    ImageFont font = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    public AlignmentDemo()
    {
        camera().meter(32);
        backgroundColor("blue");
        createText(3, "Dieser Text ist linksbuendig ausgerichtet.", LEFT);
        createText(-2, "Dieser Text ist zentriert ausgerichtet.", CENTER);
        createText(-7,
            "Dieser Text ist rechtsbuendig ausgerichtet.",
            RIGHT);
    }

    private void createText(int y, String content, TextAlignment alignment)
    {
        ImageFontText line = new ImageFontText(font, content, 18, alignment);
        line.anchor(-9, y);
        add(line);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new AlignmentDemo());
    }
}
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_font/AlignmentDemo.java -->

{{ code('demos.classes.actor.image_font.AlignmentDemo', start_line=47) }}

## `color` (Farbe)


{{ image('docs/actor/ImageFontTextColorDemo.png') }}

<!-- ```java
public class ColorDemo extends Scene
{
    ImageFont font = new ImageFont("image-font/tetris",
            ImageFontCaseSensitivity.TO_UPPER);

    public ColorDemo()
    {
        backgroundColor("#eeeeee");
        int y = 9;
        for (Map.Entry<String, Color> entry : colors.getAll().entrySet())
        {
            setImageFontText(entry.getKey(), -5, y);
            y--;
        }
    }

    public void setImageFontText(String color, int x, int y)
    {
        ImageFontText textField = new ImageFontText(font, color, color);
        textField.anchor(x, y);
        add(textField);
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ColorDemo(), 600, 800);
    }
}
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_font/ColorDemo.java -->

{{ code('demos.classes.actor.image_font.ColorDemo', start_line=44) }}

## `real-world example` (Beispiel aus der Praxis)

{{ image('docs/actor/ImageFontTextTetris.png') }}

<!-- ```java
public class CopyrightScene extends BaseScene implements KeyStrokeListener
{
    public CopyrightScene()
    {
        super(null);
        backgroundColor(COLOR_SCHEME_GREEN.getWhite());
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
                // ." kann mit ImageFont nicht als ein Zeichen dargestellt
                // werden.
                // U+E000..U+F8FF BMP (0) Private Use Area
                "by Alexey Pazhitnov\uE000\n" + "\n";
        ImageFontText text = new ImageFontText(Font.getFont(), origText, 21,
                TextAlignment.CENTER);
        text.anchor(-2, 0);
        add(text);
        delay(4, this::startTitleScene);
    }

    public void startTitleScene()
    {
        Tetris.start(new TitleScene());
    }

    /**
     * Wenn eine beliebige Taste gedrückt wird, wird zum nächsten Bildschirm,
     * der Titelszene, gesprungen.
     */
    public void onKeyDown(KeyEvent keyEvent)
    {
        startTitleScene();
    }

    public static void main(String[] args)
    {
        Tetris.start(new CopyrightScene());
    }
}
``` -->

<!-- Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/image_font/ColorDemo.java -->

{{ code('tetris.scenes.CopyrightScene', start_line=35) }}
