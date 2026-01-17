package demos.docs.main_classes.actor.text;

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.event.KeyEvent;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/main-classes/actor/text.md

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.event.KeyStrokeListener;
import pi.resources.font.FontContainer;
import pi.resources.font.FontStyle;

public class TextStyleDemo extends Scene implements KeyStrokeListener
{
    private String[] systemFonts;

    private int currentSystemFont = 0;

    private Text fontName;

    private Text fontNameClear;

    private Text plain;

    private Text bold;

    private Text italic;

    private Text boldItalic;

    public TextStyleDemo()
    {
        info().title("Schriftstil-Demo")
            .description(
                "Die Demo zeigt zurerst den Schriftnamen, dann die vier Schriftstile „normal”, „fett”, „kursiv” und „fett und kursiv”.")
            .help(
                "Mit einer beliebigen Taste kann durch alle Systemschriftarten in alphabetischer Reihenfolge gegangen werden.");
        systemFonts = FontContainer.systemFonts();

        fontName = createText("Times New Roman");
        fontName.style(FontStyle.BOLD).y(6);

        fontNameClear = createText("(Times New Roman)");
        fontNameClear.height(1).font("Arial").y(5);

        plain = createText("Normal");
        plain.style(FontStyle.PLAIN);
        plain.y(0);

        bold = createText("Fett");
        bold.style(FontStyle.BOLD);
        bold.y(-3);

        italic = createText("Kursiv");
        italic.style(FontStyle.ITALIC);
        italic.y(-6);

        boldItalic = createText("Fett und kursiv");
        boldItalic.style(FontStyle.BOLD_ITALIC);
        boldItalic.y(-9);

        backgroundColor("#666666");
    }

    private Text createText(String content)
    {
        Text text = new Text(content);
        text.height(3).font("Times New Roman").x(-11);
        add(text);
        return text;
    }

    private void setFont(String fontName)
    {
        Color random = colors.random();

        this.fontName.content(fontName).font(fontName).color(random);
        fontNameClear.content("(" + fontName + ")");
        plain.font(fontName).color(random);
        bold.font(fontName).color(random);
        italic.font(fontName).color(random);
        boldItalic.font(fontName).color(random);

    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        setFont(systemFonts[currentSystemFont]);
        if (currentSystemFont == systemFonts.length - 1)
        {
            currentSystemFont = 0;
        }
        else
        {
            currentSystemFont++;
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new TextStyleDemo());
        Controller.recordScreen(15);
    }
}
