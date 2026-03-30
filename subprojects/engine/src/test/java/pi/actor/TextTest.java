package pi.actor;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import pi.Controller;

public class TextTest
{
    Text text;

    @BeforeEach
    void setUp()
    {
        Controller.instantMode(false);
        text = new Text("Hello World");
    }

    @Test
    void textCreation()
    {
        assertNotNull(text);
    }

    @Test
    void toStringWithContent()
    {
        String result = text.toString();
        assertTrue(result.contains("Text"));
        assertTrue(result.contains("Hello World"));
    }

    @Test
    void nullContent()
    {
        Text text = new Text(null);
        assertNotNull(text);
    }

    @Test
    void toStringWithDifferentContent()
    {
        Text text = new Text(42);
        String result = text.toString();
        assertNotNull(result);
        assertTrue(result.contains("42"));
    }
}
