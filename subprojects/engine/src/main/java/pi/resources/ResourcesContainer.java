/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/resources/ResourcesContainer.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
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
package pi.resources;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.function.Supplier;

import pi.annotations.Getter;

/**
 * Eine abstrakte Implementierung für Unterklassen, die einen bestimmten Typ von
 * Ressourcen (z. b. Bilder, Klänge) bereitstellen wollen. Diese Klasse bietet
 * Methoden zum Verwalten der Ressourcen an.
 *
 * <p>
 * Die Ressourcen werden von dieser Klasse im Speicher gehalten. Es handelt sich
 * also um einen Cache.
 * </p>
 *
 * @param <T> Der Datentyp der Ressource, die in dieser Instanz enthalten ist.
 *
 * @see ResourcesContainerListener
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public abstract class ResourcesContainer<T> implements Container<T>
{
    // use a work-stealing pool to maximize resource load speed while minimizing
    // the number of resources
    // in use
    private static final ExecutorService ASYNC_POOL = Executors
        .newWorkStealingPool();

    private final Map<String, T> resources = new ConcurrentHashMap<>();

    private final Map<String, String> aliases = new ConcurrentHashMap<>();

    private final List<ResourcesContainerListener<T>> listeners = new CopyOnWriteArrayList<>();

    private final List<ResourcesContainerClearedListener> clearedListeners = new CopyOnWriteArrayList<>();

    private ResourceManipulator<T> manipulator;

    /**
     * Add a new container listener to this instance in order to observe
     * resource life cycles. The listener will get notified whenever a resource
     * was added to or removed from this container.
     *
     * @param listener The container listener instance that will receive call
     *     backs from this container.
     *
     * @see #removeContainerListener(ResourcesContainerListener)
     */
    public ResourcesContainerListener<T> addContainerListener(
            ResourcesContainerListener<T> listener)
    {
        listeners.add(listener);
        return listener;
    }

    /**
     * Remove the specified listener from this container.
     *
     * @param listener The listener instance that was previously added to this
     *     container.
     *
     * @see #addContainerListener(ResourcesContainerListener)
     */
    public void removeContainerListener(ResourcesContainerListener<T> listener)
    {
        listeners.remove(listener);
    }

    /**
     * Add a new container listener to this instance that observes whenever this
     * instance is cleared.
     *
     * @param listener The container listener instance.
     *
     * @see #removeClearedListener(ResourcesContainerClearedListener)
     */
    public void addClearedListener(ResourcesContainerClearedListener listener)
    {
        clearedListeners.add(listener);
    }

    /**
     * Remove the specified listener from this container.
     *
     * @param listener The listener instance that was previously added to this
     *     container.
     *
     * @see #addClearedListener(ResourcesContainerClearedListener)
     */
    public void removeClearedListener(
            ResourcesContainerClearedListener listener)
    {
        clearedListeners.remove(listener);
    }

    /**
     * Fügt einen Ressourcen-Manipulator zu diesem Ressourcenspeicher hinzu.
     *
     * @author Josef Friedrich
     */
    public void addManipulator(ResourceManipulator<T> manipulator)
    {
        this.manipulator = manipulator;
    }

    /**
     * Entfernt den Ressourcen-Manipulator aus diesem Ressourcenspeicher.
     *
     * @author Josef Friedrich
     */
    public void removeManipulator()
    {
        manipulator = null;
    }

    /**
     * Fügt die angegebene Ressource zu diesem Speicher hinzu.<br>
     * Das hinzugefügte Element kann später aus dem Speicher abgerufen werden,
     * indem {@code get(resourceName)} aufgerufen wird.
     * <p>
     * Verwenden Sie diese Methode, um eine Ressource während der Laufzeit über
     * diesen Speicher zugänglich zu machen.
     * </p>
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     * @param resource Die Ressourceninstanz.
     *
     * @see #get(Predicate)
     * @see #get(String)
     * @see #get(String, boolean)
     * @see #remove(String)
     * @see #tryGet(String)
     */
    public T add(String name, T resource)
    {
        if (manipulator != null)
        {
            T r = manipulator.beforeAdd(name, resource);
            if (r != null)
            {
                resource = r;
            }
        }
        resources.put(name, resource);
        for (ResourcesContainerListener<T> listener : listeners)
        {
            listener.added(name, resource);
        }
        return resource;
    }

    public T add(URL name, T resource)
    {
        return add(name.toString(), resource);
    }

    /**
     * Leert den Ressourcenspeicher, indem alle zuvor geladenen Ressourcen
     * entfernt werden.
     */
    public void clear()
    {
        resources.clear();
        for (ResourcesContainerListener<T> listener : listeners)
        {
            listener.cleared();
        }
    }

    /**
     * Checks if this instance contains a resource with the specified name.
     * <p>
     * Note that the name is <b>not case-sensitive</b>.
     * </p>
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     *
     * @return True if this container contains a resource with the specified
     *     name; otherwise false.
     *
     * @see ResourcesContainer#contains(Object)
     */
    public boolean contains(String name)
    {
        return resources.containsKey(identifier(name));
    }

    public boolean contains(URL name)
    {
        return contains(name.toString());
    }

    /**
     * Checks if the specified resource is contained by this instance.
     *
     * @param resource The resource.
     *
     * @return True if this instance contains the specified resource instance;
     *     otherwise false.
     */
    public boolean contains(T resource)
    {
        return resources.containsValue(resource);
    }

    /**
     * Gets the amount of resources that this container holds.
     *
     * @return The amount of resources in this container.
     */
    public int count()
    {
        return resources.size();
    }

    /**
     * Gets all resources that match the specified condition.
     *
     * @param pred The condition that a resource must fulfill in order to be
     *     returned.
     *
     * @return All resources that match the specified condition.
     */
    public Collection<T> get(Predicate<T> pred)
    {
        if (pred == null)
        {
            return new ArrayList<>();
        }
        return resources.values().stream().filter(pred).toList();
    }

    /**
     * Ruft die <b>Ressource</b> mit dem angegebenen <b>Name</b> oder
     * <b>Dateipfad</b> auf.
     *
     * <p>
     * Dies ist die gängigste (und bevorzugte) Methode, um Ressourcen aus einem
     * Speicher abzurufen.
     * </p>
     *
     * <p>
     * Wenn die Ressource nicht zuvor geladen wurde, versucht diese Methode, sie
     * sofort zu laden, andernfalls wird sie aus dem Cache abgerufen.
     * </p>
     *
     * @param name Der Name oder Dateipfad, unter dem die Ressource verwaltet
     *     wird.
     *
     * @return Die Ressource mit dem angegebenen Namen oder {@code null}, wenn
     *     sie nicht gefunden wird.
     */
    public T get(String name)
    {
        return get(identifier(name), false);
    }

    public T get(URL name)
    {
        return get(name, false);
    }

    /**
     * Gets the resource with the specified name.<br>
     * <p>
     * If no such resource is currently present on the container, it will be
     * loaded with the specified {@code loadCallback} and added to this
     * container.
     * </p>
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     * @param loadCallback The callback that is used to load the resource
     *     on-demand if it's not present on this container.
     *
     * @return T The resource with the specified name.
     */
    public T get(String name, Supplier<? extends T> loadCallback)
    {
        Optional<T> opt = tryGet(name);
        if (opt.isPresent())
        {
            return opt.get();
        }
        T resource = loadCallback.get();
        if (resource != null)
        {
            return add(name, resource);
        }
        throw new ResourceLoadException(
                "Die Ressource " + name + " konnte nicht geladen werden.");
    }

    public T get(URL name, Supplier<? extends T> loadCallback)
    {
        return get(name.toString(), loadCallback);
    }

    /**
     * Gets the resource with the specified name.
     * <p>
     * If not previously loaded, this method attempts to load the resource on
     * the fly otherwise it will be retrieved from the cache.
     * </p>
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     * @param forceLoad If set to true, cached resource (if existing) will be
     *     discarded and the resource will be freshly loaded.
     *
     * @return The game resource or null if not found.
     */
    public T get(String name, boolean forceLoad)
    {
        if (name == null)
        {
            throw new IllegalArgumentException(
                    "Der Name der Ressource darf nicht null sein.");
        }
        T resource = resources.get(name);
        if (forceLoad || resource == null)
        {
            resource = loadResource(name);
            if (resource == null)
            {
                throw new ResourceLoadException("Die Ressource " + name
                        + " konnte nicht geladen werden.");
            }
            return add(name, resource);
        }
        return resource;
    }

    public T get(URL name, boolean forceLoad)
    {
        return get(name.toString(), forceLoad);
    }

    /**
     * <b>Gibt</b> <b>mehrere</b> Ressources als Feld/Array zurück.
     *
     * @param names Die Namen bzw. die Dateipfade der gewünschten Ressourcen.
     *
     * @return Die gewünschten Ressourcen als Feld/Array.
     */
    public List<T> getMultiple(String... names)
    {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < names.length; i++)
        {
            result.add(get(names[i]));
        }
        return result;
    }

    /**
     * Eventually gets the resource with the specified location. The resource is
     * loaded asynchronously and can be retrieved from the returned
     * {@code Future} object returned by this method once loaded.
     *
     * @param location The location of the resource
     *
     * @return A {@code Future} object that can be used to retrieve the resource
     *     once it is finished loading
     */
    public Future<T> getAsync(URL location)
    {
        return ASYNC_POOL.submit(() -> get(location));
    }

    /**
     * Eventually gets the resource with the specified location. The resource is
     * loaded asynchronously and can be retrieved from the returned
     * {@code Future} object returned by this method once loaded.
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     *
     * @return A {@code Future} object that can be used to retrieve the resource
     *     once it is finished loading
     */
    public Future<T> getAsync(String name)
    {
        return getAsync(ResourceLoader.location(identifier(name)));
    }

    /**
     * Gets all loaded resources from this container.
     *
     * @return All loaded resources.
     */
    public Collection<T> getAll()
    {
        return resources.values();
    }

    /**
     * Removes the resource with the specified name from this container.
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     *
     * @return The removed resource.
     */
    public T remove(String name)
    {
        T removedResource = resources.remove(name);
        if (removedResource != null)
        {
            for (ResourcesContainerListener<? super T> listener : listeners)
            {
                listener.removed(name, removedResource);
            }
        }
        return removedResource;
    }

    public T remove(URL name)
    {
        return remove(name.toString());
    }

    /**
     * Tries to get a resource with the specified name from this container.
     * <p>
     * This method should be used, if it's not clear whether the resource is
     * present on this container.<br>
     * It is basically a combination of {@code get(String)} and
     * {@code contains(String)} and allows to check whether a resource is
     * present while also fetching it from the container.
     * </p>
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     *
     * @return An Optional instance that holds the resource instance, if present
     *     on this container.
     *
     * @see Optional
     * @see #contains(String)
     * @see #get(String)
     */
    public Optional<T> tryGet(String name)
    {
        if (contains(name))
        {
            return Optional.of(get(name));
        }
        return Optional.empty();
    }

    public Optional<T> tryGet(URL name)
    {
        return tryGet(name.getPath());
    }

    protected abstract T load(URL name) throws Exception;

    /**
     * Ruft einen Alias für den angegebenen resourceName ab. Die Bereitstellung
     * eines Alias hängt von der jeweiligen ResourceContainer-Implementierung
     * ab.
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     * @param resource The resource.
     *
     * @return An alias for the specified resource.
     */
    @Getter
    protected String alias(String name, T resource)
    {
        return null;
        // throw new UnsupportedOperationException(
        // "Die Methode ist nicht implmementiert für den Ressourcentyp "
        // + resource);
    }

    protected Map<String, T> getResources()
    {
        return resources;
    }

    protected T loadResource(String identifier)
    {
        T newResource;
        try
        {
            newResource = load(ResourceLoader.location(identifier));
        }
        catch (Exception e)
        {
            throw new ResourceLoadException(
                    "Die Ressource konnte nicht geladen werden: " + identifier);
        }
        String alias = alias(identifier, newResource);
        if (alias != null)
        {
            aliases.put(alias, identifier);
        }
        return newResource;
    }

    @Getter
    private String identifier(String name)
    {
        return aliases.getOrDefault(name, name);
    }
}
