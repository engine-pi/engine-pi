package tetris.text;

import pi.actor.ImageText;
import pi.actor.ImageText.CaseSensitivity;

public class FontProvider
{
    private static ImageText.Font font;

    public static ImageText.Font getFont()
    {
        if (font == null)
        {
            font = new ImageText.Font("images/image-font")
                .addMapping('\uE000', "e000_quotation-mark-and-dot")
                .supportsCase(CaseSensitivity.UPPER);
        }
        return font;
    }
}
