package tetris.text;

import static tetris.Tetris.COLOR_SCHEME_GREEN;

import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;

public class Font
{
    private static ImageText.Font font;

    public static ImageText.Font getFont()
    {
        if (font == null)
        {
            font = new ImageText.Font("images/image-font")
                .caseSensitivity(CaseSensitivity.TO_UPPER)
                .color(COLOR_SCHEME_GREEN.getBlack())
                .addMapping('\uE000', "e000_quotation-mark-and-dot");
        }
        return font;
    }
}
