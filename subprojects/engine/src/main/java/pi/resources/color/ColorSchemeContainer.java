package pi.resources.color;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 0.42.0
 */
public class ColorSchemeContainer
{
    private final Map<String, ColorScheme> schemes = new HashMap<>();

    public ColorSchemeContainer()
    {
        for (PredefinedColorScheme predefinedScheme : PredefinedColorScheme
                .values())
        {
            add(predefinedScheme.getScheme());
        }
    }

    public void add(ColorScheme scheme)
    {
        schemes.put(scheme.name(), scheme);
    }

    public ColorScheme get(String name)
    {
        if (!schemes.containsKey(name))
        {
            throw new RuntimeException("Unbekanntes Farbschema : " + name);
        }
        return schemes.get(name);
    }
}
