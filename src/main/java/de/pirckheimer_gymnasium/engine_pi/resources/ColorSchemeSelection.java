package de.pirckheimer_gymnasium.engine_pi.resources;

public enum ColorSchemeSelection
{
    GNOME(ColorScheme.getGnomeScheme()), JAVA(ColorScheme.getJavaScheme()), ANDROID(ColorScheme.getAndroidScheme());

    private ColorScheme scheme;

    private ColorSchemeSelection(ColorScheme scheme)
    {
        this.scheme = scheme;
    }

    public ColorScheme getScheme()
    {
        return scheme;
    }
}
