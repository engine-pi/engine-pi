package de.pirckheimer_gymnasium.engine_pi.resources;

/**
 * Repräsentiert die verfügbaren <b>Schriftstile</b> bzw. Schriftschnitte.
 *
 * <p>
 * Dieser Aufzählungstype dient dazu, die Schriftstile eindeutig zu benennen und
 * den zugehörigen numerischen Wert über {@link #getStyle()} bereitzustellen.
 * </p>
 */
public enum FontStyle
{
    PLAIN(0), BOLD(1), ITALIC(2), BOLD_ITALIC(3);

    private final int style;

    FontStyle(int style)
    {
        this.style = style;
    }

    public int getStyle()
    {
        return style;
    }
}
