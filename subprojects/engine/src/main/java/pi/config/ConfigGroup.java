/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/configuration/ConfigurationGroup.java
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
package pi.config;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import pi.annotations.Getter;
import pi.util.ReflectionUtil;

/**
 * Diese Klasse enthält grundlegende Funktionalität für alle
 * Einstellungsgruppen.
 *
 * <p>
 * Sie liest die {@link ConfigGroupInfo}-Annotation aus und ermittelt den
 * Präfix, der beim Lesen und Schreiben der Einstellungen in eine
 * Properties-Datei verwendet wird.
 * </p>
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 *
 * @since 0.42.0
 */
@ConfigGroupInfo
public abstract class ConfigGroup
{
    private final Collection<ConfigurationChangedListener> listeners = ConcurrentHashMap
        .newKeySet();

    private final String prefix;

    /**
     * Erstellt eine neue Instanz der Klasse {@link ConfigGroup}.
     */
    protected ConfigGroup()
    {
        final ConfigGroupInfo info = this.getClass()
            .getAnnotation(ConfigGroupInfo.class);
        this.prefix = info.prefix();
    }

    /**
     * Fügt den angegebenen Listener hinzu, der Ereignisse über geänderte
     * Konfigurationseigenschaften empfängt.
     *
     * <p>
     * Das Ereignis wird für jede Eigenschaft unterstützt, die die Methode
     * {@link #set(String, Object)} zum Setzen des Feldwerts verwendet.
     * </p>
     *
     * <p>
     * Das Ereignis liefert den Feldnamen des aufgerufenen Setters (z.B. "debug"
     * für den Aufruf von "setDebug").
     * </p>
     *
     * @param listener Der hinzuzufügende Listener.
     *
     * @see ConfigGroup#set(String, Object)
     */
    public void onChanged(ConfigurationChangedListener listener)
    {
        listeners.add(listener);
    }

    /**
     * Entfernt den angegebenen Listener.
     *
     * @param listener Der zu entfernende Listener.
     */
    public void removeListener(ConfigurationChangedListener listener)
    {
        listeners.remove(listener);
    }

    /**
     * Gibt den Präfix der Konfigurationsgruppe zurück.
     *
     * @return Der Präfix der Konfigurationsgruppe, oder ein leerer String,
     *     falls der Präfix {@code null} ist.
     */
    @Getter
    public String prefix()
    {
        return prefix != null ? prefix : "";
    }

    /**
     * Initialisiert eine Eigenschaft anhand ihres Schlüssels und Werts.
     *
     * @param key Der Schlüssel der Eigenschaft.
     * @param value Der Wert der Eigenschaft.
     */
    protected void initializeByProperty(final String key, final String value)
    {
        final String propertyName = key.substring(prefix().length());
        ReflectionUtil.setFieldValue(getClass(), this, propertyName, value);
    }

    /**
     * Speichert die Eigenschaften. Standardmäßig werden folgende Typen
     * unterstützt: boolean, int, double, float, String und Enum-Werte. Für
     * andere Objekte sollte diese Methode sowie
     * {@link #initializeByProperty(String, String)} überschrieben und ein
     * eigener Ansatz implementiert werden.
     *
     * @param properties Die zu befüllenden Properties.
     */
    @SuppressWarnings({ "java:S3011", "java:S135" })
    protected void storeProperties(final Properties properties)
    {
        try
        {
            for (Field field : getClass().getDeclaredFields())
            {
                if (Modifier.isFinal(field.getModifiers())
                        || Modifier.isStatic(field.getModifiers()))
                {
                    continue; // Skip final or static fields
                }

                field.setAccessible(true); // Ensure field is accessible
                String propertyKey = prefix() + field.getName();
                Object value = field.get(this);

                if (value == null)
                {
                    properties.setProperty(propertyKey, "");
                    continue;
                }

                if (field.getType().isPrimitive() || value instanceof String
                        || value instanceof Path)
                {
                    properties.setProperty(propertyKey, value.toString());
                }
                else if (value instanceof String[] stringArray)
                {
                    properties.setProperty(propertyKey,
                        String.join(",", stringArray).replace("null", ""));
                }
                else if (field.getType().isEnum())
                {
                    properties.setProperty(propertyKey, value.toString());
                }
            }
        }
        catch (IllegalAccessException e)
        {
            throw new ConfigException(e);
        }
    }

    /**
     * Setzt eine Konfigurationseigenschaft und löst dabei das
     * {@code configurationChanged}-Ereignis aus.
     *
     * @param <T> Der Typ des zu setzenden Werts.
     * @param fieldName Der Name des zu setzenden Felds.
     * @param value Der zu setzende Wert.
     */
    @SuppressWarnings("java:S3011")
    protected <T> void set(String fieldName, T value)
    {
        Field field = ReflectionUtil.getField(this.getClass(), fieldName, true);
        if (field != null)
        {
            try
            {
                if (!field.canAccess(this))
                {
                    field.setAccessible(true);
                }

                final Object currentValue = field.get(this);

                final PropertyChangeEvent event = new PropertyChangeEvent(this,
                        fieldName, currentValue, value);
                field.set(this, value);

                for (ConfigurationChangedListener listener : listeners)
                {
                    listener.configurationChanged(event);
                }
            }
            catch (IllegalArgumentException | IllegalAccessException e)
            {
                throw new ConfigException(e);
            }
        }
    }
}
