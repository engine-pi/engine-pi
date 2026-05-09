/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/test/java/de/gurkenlabs/litiengine/configuration/ConfigurationTests.java
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
package pi.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Eine Sammlung von statischen Hilfsmethoden, um mit <b>Reflection</b> zu
 * arbeiten.
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public final class ReflectionUtil
{
    private static final Logger log = Logger
        .getLogger(ReflectionUtil.class.getName());

    /**
     * Dieser private Konstruktor dient dazu, den öffentlichen Konstruktor zu
     * verbergen. Dadurch ist es nicht möglich, Instanzen dieser Klasse zu
     * erstellen.
     *
     * @throws UnsupportedOperationException Falls eine Instanz der Klasse
     *     erzeugt wird.
     */
    private ReflectionUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Liefert ein Feld des angegebenen Typs anhand seines Namens.
     *
     * @param cls Der Typ, in dem nach dem Feld gesucht wird.
     * @param fieldName Der Name des gesuchten Feldes.
     *
     * @return Das gefundene Feld oder {@code null}, falls kein passendes Feld
     *     existiert.
     */
    public static <T> Field getField(Class<T> cls, final String fieldName)
    {
        return getField(cls, fieldName, true);
    }

    /**
     * Liefert ein Feld des angegebenen Typs anhand seines Namens und kann dabei
     * optional auch Oberklassen durchsuchen.
     *
     * @param cls Der Typ, in dem nach dem Feld gesucht wird.
     * @param fieldName Der Name des gesuchten Feldes.
     * @param recursive Gibt an, ob auch Oberklassen durchsucht werden sollen.
     *
     * @return Das gefundene Feld oder {@code null}, falls kein passendes Feld
     *     gefunden wurde.
     */
    public static <T> Field getField(Class<T> cls, final String fieldName,
            boolean recursive)
    {
        for (final Field field : cls.getDeclaredFields())
        {
            if (field.getName().equalsIgnoreCase(fieldName))
            {
                return field;
            }
        }

        if (recursive && cls.getSuperclass() != null
                && !cls.getSuperclass().equals(Object.class))
        {
            Field f = getField(cls.getSuperclass(), fieldName, true);
            if (f != null)
            {
                return f;
            }
        }

        log.log(Level.WARNING,
            "Could not find field [{0}] on class [{1}] or its parents.",
            new Object[]
            { fieldName, cls });
        return null;
    }

    /**
     * Liest den Wert eines statischen Feldes aus.
     *
     * @param cls Der Typ, der das statische Feld enthält.
     * @param fieldName Der Name des statischen Feldes.
     *
     * @return Der Wert des Feldes oder {@code null}, falls das Feld nicht
     *     gelesen werden kann.
     */
    @SuppressWarnings("unchecked")
    public static <V> V getStaticValue(Class<?> cls, String fieldName)
    {
        Field keyField = ReflectionUtil.getField(cls, fieldName);
        if (keyField == null)
        {
            return null;
        }

        try
        {
            return (V) keyField.get(null);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Ermittelt rekursiv alle Felder des angegebenen Typs und berücksichtigt
     * dabei auch Oberklassen.
     *
     * @param fields Die Liste, in die alle gefundenen Felder eingetragen
     *     werden.
     * @param type Der Typ, dessen Felder ermittelt werden sollen.
     *
     * @return Alle Felder des angegebenen Typs einschließlich der Felder der
     *     Oberklassen.
     */
    public static List<Field> getAllFields(List<Field> fields, Class<?> type)
    {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null
                && !type.getSuperclass().equals(Object.class))
        {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    /**
     * Ermittelt rekursiv eine Methode anhand ihres Namens und ihrer
     * Parametertypen und berücksichtigt dabei auch Oberklassen.
     *
     * @param name Der Name der Methode.
     * @param type Der Typ, auf dem nach der Methode gesucht wird.
     * @param parameterTypes Die in der Methodensignatur definierten
     *     Parametertypen.
     *
     * @return Die gefundene Methode oder {@code null}, falls keine passende
     *     Methode existiert.
     */
    public static Method getMethod(String name, Class<?> type,
            Class<?>... parameterTypes)
    {
        Method method = null;
        try
        {
            method = type.getDeclaredMethod(name, parameterTypes);
        }
        catch (NoSuchMethodException e)
        {
            if (type.getSuperclass() != null
                    && !type.getSuperclass().equals(Object.class))
            {
                return getMethod(name, type.getSuperclass(), parameterTypes);
            }
        }

        return method;
    }

    /**
     * Setzt den Wert eines Feldes über einen Setter oder direkt per
     * Feldzugriff.
     *
     * @param cls Der Typ, der das zu setzende Feld enthält.
     * @param instance Die Instanz, auf der der Wert gesetzt werden soll.
     * @param fieldName Der Name des Feldes.
     * @param value Der neue Wert.
     *
     * @return {@code true}, wenn der Wert erfolgreich gesetzt wurde,
     *     andernfalls {@code false}.
     */
    public static <T, C> boolean setValue(Class<C> cls, Object instance,
            final String fieldName, final T value)
    {
        try
        {
            final Method method = getSetter(cls, fieldName);
            if (method != null)
            {
                if (!method.canAccess(instance))
                {
                    method.setAccessible(true);
                }
                // Setzt den neuen Wert ueber den Setter.
                method.invoke(instance, value);
                return true;
            }
            else
            {
                // Falls kein Setter vorhanden ist, wird das Feld direkt
                // gesetzt.
                for (final Field field : cls.getDeclaredFields())
                {
                    if (field.getName().equals(fieldName)
                            && (field.getType() == value.getClass()
                                    || isWrapperType(field.getType(),
                                        value.getClass())
                                    || isWrapperType(value.getClass(),
                                        field.getType())))
                    {
                        if (!field.canAccess(instance))
                        {
                            field.setAccessible(true);
                        }

                        field.set(instance, value);
                        return true;
                    }
                }
            }
        }
        catch (final SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e)
        {
            log.log(Level.SEVERE,
                String.format("%s (%s-%s)", e.getMessage(), fieldName, value),
                e);
        }

        return false;
    }

    /**
     * Setzt ein Enum-Feld anhand seines String-Werts.
     *
     * @param cls Der Typ, der das Feld enthält.
     * @param instance Die Instanz, auf der der Wert gesetzt werden soll.
     * @param field Das Enum-Feld.
     * @param propertyName Der Name der Eigenschaft.
     * @param value Die textuelle Repräsentation der Enum-Konstante.
     *
     * @return {@code true}, wenn eine passende Enum-Konstante gefunden und
     *     gesetzt wurde, andernfalls {@code false}.
     */
    public static <T> boolean setEnumPropertyValue(Class<T> cls,
            Object instance, final Field field, String propertyName,
            String value)
    {
        final Object[] enumArray = field.getType().getEnumConstants();

        for (final Object enumConst : enumArray)
        {
            if (enumConst != null
                    && enumConst.toString().equalsIgnoreCase(value))
            {
                return ReflectionUtil.setValue(cls,
                    instance,
                    propertyName,
                    field.getType().cast(enumConst));
            }
        }

        return false;
    }

    /**
     * Liefert den Setter zu einem Feldnamen.
     *
     * @param cls Der Typ, dessen Setter durchsucht werden.
     * @param fieldName Der Name des Feldes.
     *
     * @return Die passende Setter-Methode oder {@code null}, falls kein Setter
     *     gefunden wurde.
     */
    public static <T> Method getSetter(Class<T> cls, final String fieldName)
    {
        for (final Method method : getSetters(cls))
        {
            if (method.getName().equalsIgnoreCase("set" + fieldName))
            {
                return method;
            }
        }

        return null;
    }

    /**
     * Liefert alle oeffentlichen Setter-Methoden eines Typs.
     *
     * @param cls Der Typ, dessen Setter ermittelt werden.
     *
     * @return Eine unveraenderliche Sammlung aller gefundenen Setter.
     */
    public static <T> Collection<Method> getSetters(Class<T> cls)
    {
        Collection<Method> methods = new ArrayList<>();

        for (final Method method : cls.getMethods())
        {
            // Ein Setter beginnt mit "set" und besitzt genau einen
            // Parameter.
            if (method.getName().toLowerCase().startsWith("set")
                    && method.getParameters().length == 1)
            {
                methods.add(method);
            }
        }

        return Collections.unmodifiableCollection(methods);
    }

    /**
     * Prueft, ob ein Typ der Wrapper-Typ eines primitiven Typs ist.
     *
     * @param primitive Der primitive Typ.
     * @param potentialWrapper Der moegliche Wrapper-Typ.
     *
     * @return {@code true}, wenn {@code potentialWrapper} der passende
     *     Wrapper-Typ zu {@code primitive} ist, andernfalls {@code false}.
     */
    public static <T, C> boolean isWrapperType(Class<T> primitive,
            Class<C> potentialWrapper)
    {
        if (!primitive.isPrimitive() || potentialWrapper.isPrimitive())
        {
            return false;
        }

        if (primitive == boolean.class)
        {
            return potentialWrapper == Boolean.class;
        }

        if (primitive == char.class)
        {
            return potentialWrapper == Character.class;
        }

        if (primitive == byte.class)
        {
            return potentialWrapper == Byte.class;
        }

        if (primitive == short.class)
        {
            return potentialWrapper == Short.class;
        }

        if (primitive == int.class)
        {
            return potentialWrapper == Integer.class;
        }

        if (primitive == long.class)
        {
            return potentialWrapper == Long.class;
        }

        if (primitive == float.class)
        {
            return potentialWrapper == Float.class;
        }

        if (primitive == double.class)
        {
            return potentialWrapper == Double.class;
        }

        if (primitive == void.class)
        {
            return potentialWrapper == Void.class;
        }

        return false;
    }

    /**
     * Setzt ein Feld anhand einer String-Repräsentation und konvertiert den
     * Wert passend zum Feldtyp.
     *
     * @param cls Der Typ, der das Feld enthält.
     * @param instance Die Instanz, auf der der Wert gesetzt werden soll.
     * @param fieldName Der Name des Feldes.
     * @param value Der zu konvertierende String-Wert.
     *
     * @return {@code true}, wenn das Feld erfolgreich gesetzt wurde,
     *     andernfalls {@code false}.
     */
    public static <T> boolean setFieldValue(final Class<T> cls,
            final Object instance, final String fieldName, final String value)
    {
        // Falls ein Setter vorhanden ist, wird er verwendet. Andernfalls wird
        // direkt versucht, das Feld zu setzen.
        final Field field = getField(cls, fieldName);
        if (field == null)
        {
            return false;
        }

        // final-Felder duerfen nicht geaendert werden.
        if (Modifier.isFinal(field.getModifiers()))
        {
            return false;
        }

        try
        {
            if (field.getType().equals(boolean.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Boolean.parseBoolean(value));
            }
            else if (field.getType().equals(int.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Integer.parseInt(value));
            }
            else if (field.getType().equals(float.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Float.parseFloat(value));
            }
            else if (field.getType().equals(double.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Double.parseDouble(value));
            }
            else if (field.getType().equals(short.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Short.parseShort(value));
            }
            else if (field.getType().equals(byte.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Byte.parseByte(value));
            }
            else if (field.getType().equals(long.class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    Long.parseLong(value));
            }
            else if (field.getType().equals(String.class))
            {
                return setValue(cls, instance, fieldName, value);
            }
            else if (field.getType().equals(String[].class))
            {
                return setValue(cls, instance, fieldName, value.split(","));
            }
            else if (field.getType().equals(int[].class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    ArrayUtil.splitInt(value, ","));
            }
            else if (field.getType().equals(double[].class))
            {
                return setValue(cls,
                    instance,
                    fieldName,
                    ArrayUtil.splitDouble(value, ","));
            }
            else if (field.getType().isEnum())
            {
                return setEnumPropertyValue(cls,
                    instance,
                    field,
                    fieldName,
                    value);
            }
            // else if (field.getType().equals(Material.class)) {
            // return setValue(cls, instance, fieldName, Material.get(value));
            // }
            // TODO: implement support for Attribute and RangeAttribute fields
        }
        catch (final NumberFormatException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }

        return false;
    }

    /**
     * Liefert alle Methoden eines Typs, die mit einer bestimmten Annotation
     * versehen sind.
     *
     * @param type Der zu durchsuchende Typ.
     * @param annotation Die gesuchte Annotation.
     *
     * @return Alle annotierten Methoden des Typs und seiner Oberklassen.
     */
    public static List<Method> getMethodsAnnotatedWith(final Class<?> type,
            final Class<? extends Annotation> annotation)
    {
        final List<Method> methods = new ArrayList<>();
        Class<?> clazz = type;
        while (clazz != Object.class)
        {
            // Die Hierarchie wird durchlaufen, damit auch Methoden aus
            // Oberklassen beruecksichtigt werden.
            final List<Method> allMethods = new ArrayList<>(
                    Arrays.asList(clazz.getDeclaredMethods()));
            for (final Method method : allMethods)
            {
                if (method.isAnnotationPresent(annotation))
                {
                    methods.add(method);
                }
            }
            // Wechselt zur Oberklasse, um weitere Methoden zu pruefen.
            clazz = clazz.getSuperclass();
        }
        return methods;
    }

    /**
     * Liefert die Ereignismethoden des angegebenen Typs.
     *
     * <p>
     * Hierzu werden alle Methoden gesucht, die einen Parameter vom Typ
     * {@code EventListener} besitzen und den Namenskonventionen der LITIENGINE
     * für Event-Registrierungen entsprechen, also mit einem der Präfixe "add"
     * oder "on" beginnen.
     *
     * @param type Der Typ, dessen Ereignismethoden untersucht werden.
     *
     * @return Alle Methoden des angegebenen Typs, die als Ereignisse gewertet
     *     werden.
     *
     * @see EventListener
     */
    public static Collection<Method> getEvents(final Class<?> type)
    {
        final String eventAddPrefix = "add";
        final String eventOnPrefix = "on";

        final List<Method> events = new ArrayList<>();
        Class<?> clazz = type;
        while (clazz != Object.class)
        {
            // Die Hierarchie wird durchlaufen, damit auch Ereignismethoden aus
            // Oberklassen beruecksichtigt werden.
            final List<Method> allMethods = new ArrayList<>(
                    Arrays.asList(clazz.getDeclaredMethods()));
            for (final Method method : allMethods)
            {
                for (Class<?> paramtype : method.getParameterTypes())
                {
                    if (EventListener.class.isAssignableFrom(paramtype)
                            && (method.getName().startsWith(eventAddPrefix)
                                    || method.getName()
                                        .startsWith(eventOnPrefix)))
                    {
                        events.add(method);
                    }
                }
            }

            // Wechselt zur Oberklasse, um weitere Methoden zu pruefen.
            clazz = clazz.getSuperclass();
        }
        return events;
    }

    /**
     * Liefert den Standardwert eines Typs.
     *
     * @param clazz Der Typ, für den der Standardwert ermittelt werden soll.
     *
     * @return Der Java-Standardwert des Typs, etwa {@code 0}, {@code false}
     *     oder {@code null}.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getDefaultValue(Class<T> clazz)
    {
        return (T) Array.get(Array.newInstance(clazz, 1), 0);
    }

    /**
     * Ermittelt den ersten generischen Typparameter eines Interfaces, das von
     * einem Typ oder einer seiner Oberklassen beziehungsweise Oberinterfaces
     * implementiert wird.
     *
     * @param clazz Der zu untersuchende Typ.
     * @param interfaze Das Interface, dessen generischer Typparameter gesucht
     *     wird.
     *
     * @return Der gefundene generische Typ oder {@code null}, falls kein
     *     passender Typparameter ermittelt werden kann.
     *
     * @since 0.49.0
     */
    @SuppressWarnings("squid:S3776")
    public static Type getGenericOfInterface(Class<?> clazz, Class<?> interfaze)
    {
        if (clazz == null || interfaze == null)
        {
            return null;
        }

        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces)
        {
            if (genericInterface instanceof ParameterizedType parameterizedType)
            {
                Type rawType = parameterizedType.getRawType();
                if (rawType instanceof Class<?> rawClass
                        && interfaze.isAssignableFrom(rawClass))
                {
                    Type[] genericTypes = parameterizedType
                        .getActualTypeArguments();
                    if (genericTypes.length > 0)
                    {
                        return genericTypes[0];
                    }
                }

                if (rawType instanceof Class<?> rawClass)
                {
                    Type genericType = getGenericOfInterface(rawClass,
                        interfaze);
                    if (genericType != null)
                    {
                        return genericType;
                    }
                }
            }
            else if (genericInterface instanceof Class<?> interfaceClass)
            {
                Type genericType = getGenericOfInterface(interfaceClass,
                    interfaze);
                if (genericType != null)
                {
                    return genericType;
                }
            }
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !Object.class.equals(superclass))
        {
            return getGenericOfInterface(superclass, interfaze);
        }

        return null;
    }
}
