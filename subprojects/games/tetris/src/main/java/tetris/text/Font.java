package tetris.text;

import static tetris.Tetris.COLOR_SCHEME_GREEN;

import pi.actor.ImageFont;
import pi.actor.ImageFontCaseSensitivity;

public class Font
{
    private static ImageFont font;

    public static ImageFont getFont()
    {
        if (font == null)
        {
            font = new ImageFont("images/image-font")
                    .caseSensitivity(ImageFontCaseSensitivity.TO_UPPER)
                    .color(COLOR_SCHEME_GREEN.getBlack())
                    .addMapping('\uE000', "e000_quotation-mark-and-dot");
        }
        return font;
    }
}
