package de.pirckheimer_gymnasium.engine_pi.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Josef Friedrich
 *
 * @since 0.25.0
 */
public class ToStringFormatter
{
    private HashMap<String, Object> map = new LinkedHashMap<>();

    private String className;

    public ToStringFormatter(String className)
    {
        this.className = className;
    }

    public void add(String key, Object value)
    {
        map.put(key, value);
    }

    public void add(String key, Object value, String unit)
    {
        map.put(key, String.format("%s%s", value, unit));
    }

    public void add(String key)
    {
        map.put(key, "true");
    }

    public String format()
    {
        ArrayList<String> entries = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet())
        {
            entries.add(entry.getKey() + "=" + entry.getValue());
        }
        return String.format("%s [%s]", className, String.join(", ", entries));
    }
}
