package pi.resources.color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ColorSchemeContainerTest
{
    ColorSchemeContainer container;

    @BeforeEach
    void setup()
    {
        container = new ColorSchemeContainer();
    }

    @Test
    void testInitializationWithPredefinedSchemes()
    {
        for (PredefinedColorScheme predefinedScheme : PredefinedColorScheme
                .values())
        {
            assertEquals(predefinedScheme.getScheme(),
                    container.get(predefinedScheme.getScheme().name()));
        }
    }

    @Test
    void testAddAndGetColorScheme()
    {
        ColorScheme customScheme = new ColorScheme("CustomScheme");
        container.add(customScheme);
        assertEquals(customScheme, container.get("CustomScheme"));
    }

    @Test
    void testGetNonExistentScheme()
    {
        assertThrows(RuntimeException.class, () -> {
            container.get("NonExistentScheme");
        });
    }

    @Test
    void testGetPredefinedScheme()
    {
        assertEquals("Java", container.get("Java").name());
    }

    @Test
    void testOverwriteExistingScheme()
    {
        ColorScheme newScheme = new ColorScheme("Java");
        container.add(newScheme);
        assertEquals(newScheme, container.get("Java"));
    }
}
