/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/configuration/ConfigurationGroupTests.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2025 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.configuration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pi.configuration.ConfigurationGroup.ConfigurationChangedListener;
import pi.util.ReflectionUtil;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;

public class ConfigurationGroupTest
{
    @Test
    void testPropertyChangedGraphicsConfig()
    {
        this.testConfigurationChanged(new GraphicsConfiguration());
    }

    @Test
    void testPropertyChangedSoundConfig()
    {
        this.testConfigurationChanged(new SoundConfiguration());
    }

    @Test
    void testPropertyChangedDebugConfig()
    {
        this.testConfigurationChanged(new DebugConfiguration());
    }

    @Test
    void testPropertyChangedEventValues()
    {
        GraphicsConfiguration config = new GraphicsConfiguration();
        TestConfigurationChangedListener listener = new TestConfigurationChangedListener();

        config.onChanged(listener);

        int oldWidth = config.windowWidth();
        config.windowWidth(99);

        assertEquals("windowWidth", listener.name);
        assertEquals(99, listener.newVal);
        assertEquals(oldWidth, listener.oldVal);
        assertEquals(config, listener.source);

        int oldHeight = config.windowHeight();
        config.windowHeight(101);

        assertEquals("windowHeight", listener.name);
        assertEquals(101, listener.newVal);
        assertEquals(oldHeight, listener.oldVal);
        assertEquals(config, listener.source);
    }

    @Test
    void testReflectionBasedSetters()
    {
        GraphicsConfiguration config = new GraphicsConfiguration();
        config.windowWidth(99);
        assertEquals(99, config.windowWidth());
    }

    private <T extends ConfigurationGroup> void testConfigurationChanged(
            T instance)
    {
        TestConfigurationChangedListener listener = new TestConfigurationChangedListener();

        instance.onChanged(listener);

        for (Method method : ReflectionUtil.getSetters(instance.getClass()))
        {
            Object value = ReflectionUtil
                    .getDefaultValue(method.getParameters()[0].getType());
            assertDoesNotThrow(() -> method.invoke(instance, value));
            assertNotNull(method.getName());
            assertNotNull(listener.name, method.getName());
            assertTrue(
                    method.getName().toLowerCase()
                            .contains(listener.name.toLowerCase()),
                    method.getName() + " == " + listener.name);
        }
    }

    static class TestConfigurationChangedListener
            implements ConfigurationChangedListener
    {
        Object newVal;

        Object oldVal;

        Object source;

        String name;

        @Override
        public void configurationChanged(PropertyChangeEvent event)
        {
            newVal = event.getNewValue();
            name = event.getPropertyName();
            source = event.getSource();
            oldVal = event.getOldValue();
        }
    }
}
