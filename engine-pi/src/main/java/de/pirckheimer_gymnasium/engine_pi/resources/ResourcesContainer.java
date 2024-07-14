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
package de.pirckheimer_gymnasium.engine_pi.resources;

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
     *                 backs from this container.
     * @see #removeContainerListener(ResourcesContainerListener)
     */
    public ResourcesContainerListener<T> addContainerListener(
            ResourcesContainerListener<T> listener)
    {
        this.listeners.add(listener);
        return listener;
    }

    /**
     * Remove the specified listener from this container.
     *
     * @param listener The listener instance that was previously added to this
     *                 container.
     * @see #addContainerListener(ResourcesContainerListener)
     */
    public void removeContainerListener(ResourcesContainerListener<T> listener)
    {
        this.listeners.remove(listener);
    }

    /**
     * Add a new container listener to this instance that observes whenever this
     * instance is cleared.
     *
     * @param listener The container listener instance.
     * @see #removeClearedListener(ResourcesContainerClearedListener)
     */
    public void addClearedListener(ResourcesContainerClearedListener listener)
    {
        this.clearedListeners.add(listener);
    }

    /**
     * Remove the specified listener from this container.
     *
     * @param listener The listener instance that was previously added to this
     *                 container.
     * @see #addClearedListener(ResourcesContainerClearedListener)
     */
    public void removeClearedListener(
            ResourcesContainerClearedListener listener)
    {
        this.clearedListeners.remove(listener);
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
     * @param name     Der Name, unter dem die Ressource verwaltet wird.
     * @param resource Die Ressourceninstanz.
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
        this.resources.put(name, resource);
        for (ResourcesContainerListener<T> listener : this.listeners)
        {
            listener.added(name, resource);
        }
        return resource;
    }

    public T add(URL name, T resource)
    {
        return this.add(name.toString(), resource);
    }

    /**
     * Leert den Ressourcenspeicher, indem alle zuvor geladenen Ressourcen
     * entfernt werden.
     */
    public void clear()
    {
        this.resources.clear();
        for (ResourcesContainerListener<T> listener : this.listeners)
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
     * @return True if this container contains a resource with the specified
     *         name; otherwise false.
     * @see ResourcesContainer#contains(Object)
     */
    public boolean contains(String name)
    {
        return this.resources.containsKey(this.getIdentifier(name));
    }

    public boolean contains(URL name)
    {
        return this.contains(name.toString());
    }

    /**
     * Checks if the specified resource is contained by this instance.
     *
     * @param resource The resource.
     * @return True if this instance contains the specified resource instance;
     *         otherwise false.
     */
    public boolean contains(T resource)
    {
        return this.resources.containsValue(resource);
    }

    /**
     * Gets the amount of resources that this container holds.
     *
     * @return The amount of resources in this container.
     */
    public int count()
    {
        return this.resources.size();
    }

    /**
     * Gets all resources that match the specified condition.
     *
     * @param pred The condition that a resource must fulfill in order to be
     *             returned.
     * @return All resources that match the specified condition.
     */
    public Collection<T> get(Predicate<T> pred)
    {
        if (pred == null)
        {
            return new ArrayList<>();
        }
        return this.resources.values().stream().filter(pred).toList();
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
     *             wird.
     *
     * @return Die Ressource mit dem angegebenen Namen oder {@code null}, wenn
     *         sie nicht gefunden wird.
     */
    public T get(String name)
    {
        return this.get(this.getIdentifier(name), false);
    }

    public T get(URL name)
    {
        return this.get(name, false);
    }

    /**
     * Gets the resource with the specified name.<br>
     * <p>
     * If no such resource is currently present on the container, it will be
     * loaded with the specified {@code loadCallback} and added to this
     * container.
     * </p>
     *
     * @param name         Der Name, unter dem die Ressource verwaltet wird.
     * @param loadCallback The callback that is used to load the resource
     *                     on-demand if it's not present on this container.
     * @return T The resource with the specified name.
     */
    public T get(String name, Supplier<? extends T> loadCallback)
    {
        Optional<T> opt = this.tryGet(name);
        if (opt.isPresent())
        {
            return opt.get();
        }
        T resource = loadCallback.get();
        if (resource != null)
        {
            return this.add(name, resource);
        }
        return null;
    }

    public T get(URL name, Supplier<? extends T> loadCallback)
    {
        return this.get(name.toString(), loadCallback);
    }

    /**
     * Gets the resource with the specified name.
     * <p>
     * If not previously loaded, this method attempts to load the resource on
     * the fly otherwise it will be retrieved from the cache.
     * </p>
     *
     * @param name      Der Name, unter dem die Ressource verwaltet wird.
     * @param forceLoad If set to true, cached resource (if existing) will be
     *                  discarded and the resource will be freshly loaded.
     * @return The game resource or null if not found.
     */
    public T get(String name, boolean forceLoad)
    {
        if (name == null)
        {
            return null;
        }
        T resource = this.resources.get(name);
        if (forceLoad || resource == null)
        {
            resource = this.loadResource(name);
            if (resource == null)
            {
                return null;
            }
            return this.add(name, resource);
        }
        return resource;
    }

    public T get(URL name, boolean forceLoad)
    {
        return this.get(name.toString(), forceLoad);
    }

    @SuppressWarnings("unchecked")
    public T[] getMultiple(String[] names)
    {
        Object[] resources = new Object[names.length];
        for (int i = 0; i < names.length; i++)
        {
            resources[i] = get(names[i]);
        }
        return (T[]) resources;
    }

    /**
     * Eventually gets the resource with the specified location. The resource is
     * loaded asynchronously and can be retrieved from the returned
     * {@code Future} object returned by this method once loaded.
     *
     * @param location The location of the resource
     * @return A {@code Future} object that can be used to retrieve the resource
     *         once it is finished loading
     */
    public Future<T> getAsync(URL location)
    {
        return ASYNC_POOL.submit(() -> this.get(location));
    }

    /**
     * Eventually gets the resource with the specified location. The resource is
     * loaded asynchronously and can be retrieved from the returned
     * {@code Future} object returned by this method once loaded.
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     * @return A {@code Future} object that can be used to retrieve the resource
     *         once it is finished loading
     */
    public Future<T> getAsync(String name)
    {
        return this
                .getAsync(ResourceLoader.getLocation(this.getIdentifier(name)));
    }

    /**
     * Gets all loaded resources from this container.
     *
     * @return All loaded resources.
     */
    public Collection<T> getAll()
    {
        return this.resources.values();
    }

    /**
     * Removes the resource with the specified name from this container.
     *
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     * @return The removed resource.
     */
    public T remove(String name)
    {
        T removedResource = this.resources.remove(name);
        if (removedResource != null)
        {
            for (ResourcesContainerListener<? super T> listener : this.listeners)
            {
                listener.removed(name, removedResource);
            }
        }
        return removedResource;
    }

    public T remove(URL name)
    {
        return this.remove(name.toString());
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
     * @return An Optional instance that holds the resource instance, if present
     *         on this container.
     * @see Optional
     * @see #contains(String)
     * @see #get(String)
     */
    public Optional<T> tryGet(String name)
    {
        if (this.contains(name))
        {
            return Optional.of(this.get(name));
        }
        return Optional.empty();
    }

    public Optional<T> tryGet(URL name)
    {
        return this.tryGet(name.getPath());
    }

    protected abstract T load(URL name) throws Exception;

    /**
     * Gets an alias for the specified resourceName. Note that the process of
     * providing an alias is up to the ResourceContainer implementation.
     *
     * @param name     Der Name, unter dem die Ressource verwaltet wird.
     * @param resource The resource.
     * @return An alias for the specified resource.
     */
    protected String getAlias(String name, T resource)
    {
        return null;
    }

    protected Map<String, T> getResources()
    {
        return this.resources;
    }

    protected T loadResource(String identifier)
    {
        T newResource;
        try
        {
            newResource = this.load(ResourceLoader.getLocation(identifier));
        }
        catch (Exception e)
        {
            throw new ResourceLoadException(e);
        }
        String alias = this.getAlias(identifier, newResource);
        if (alias != null)
        {
            this.aliases.put(alias, identifier);
        }
        return newResource;
    }

    private String getIdentifier(String name)
    {
        return this.aliases.getOrDefault(name, name);
    }
}
