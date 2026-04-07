/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pi.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import pi.Scene;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;

// Go to file:///data/school/repos/inf/java/engine-pi/docs/manual/main-classes/actor/group.md

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/group/GroupDemo.java

/**
 * Eine <b>Gruppe</b> bestehend aus mehreren {@link Actor}-Objekten.
 *
 * <p>
 * Über eine Gruppe lassen sich mehrere Figure gemeinsam verwalten, z.B.
 * gleichzeitig bewegen oder einer Szene hinzufügen. Die Gruppe implementiert
 * {@link Iterable}, sodass sie direkt in einer {@code for}-Schleife verwendet
 * werden kann.
 * </p>
 *
 * @param <T> der Typ der enthaltenen {@link Actor}-Objekte
 *
 * @author Josef Friedrich
 */
public class Group<T extends Actor> implements Iterable<T>
{

    /**
     * Erstellt eine neue Gruppe mit den angegebenen {@link Actor}-Objekten.
     *
     * @param actors Die Figuren, aus denen die Gruppe besteht.
     */
    @SafeVarargs
    public Group(T... actors)
    {
        this.actors = new ArrayList<>(Arrays.asList(actors));
    }

    /* actors */

    /**
     * Eine Liste, die alle {@link Actor Figuren} enthält.
     */
    private final List<T> actors;

    /**
     * Gibt die Liste zurück, die alle {@link Actor Figuren} enthält.
     *
     * @return Die Liste, die alle {@link Actor Figuren} enthält.
     *
     * @since 0.45.0
     */
    @API
    @Getter
    public List<T> actors()
    {
        return actors;
    }

    /**
     * Gibt einen Iterator über alle {@link Actor Figuren} dieser Gruppe zurück.
     *
     * <p>
     * Die zurückgegebene Liste ist schreibgeschützt; Änderungen an der Gruppe
     * müssen über die Methoden der Gruppe selbst vorgenommen werden.
     * </p>
     *
     * @return Ein Iterator über alle Figuren der Gruppe.
     *
     * @since 0.45.0
     */
    @Override
    public Iterator<T> iterator()
    {
        return Collections.unmodifiableList(actors).iterator();
    }

    /**
     * <b>Fügt</b> eine {@link Actor Figur} zur Gruppe hinzu.
     *
     * @param actor Die Figur, die hinzugefügt werden soll.
     *
     * @return diese Gruppe (für Method-Chaining)
     */
    @API
    @ChainableMethod
    public Group<T> add(T actor)
    {
        Objects.requireNonNull(actor,
            "Der Eingabeparameter 'actor' darf nicht null sein.");
        actors.add(actor);
        return this;
    }

    /**
     * <b>Entfernt</b> eine {@link Actor Figur} aus der Gruppe.
     *
     * @param actor Die Figur, die entfernt werden soll.
     *
     * @return diese Gruppe (für Method-Chaining)
     */
    @API
    @ChainableMethod
    public Group<T> remove(T actor)
    {
        Objects.requireNonNull(actor,
            "Der Eingabeparameter 'actor' darf nicht null sein.");
        actors.remove(actor);
        return this;
    }

    /**
     * Gibt die <b>Anzahl</b> der enthaltenen {@link Actor Figuren} zurück.
     *
     * @return Die Anzahl der Figuren in dieser Gruppe.
     */
    @API
    public int size()
    {
        return actors.size();
    }

    /**
     * Prüft, ob diese Gruppe <b>keine</b> {@link Actor Figuren} enthält.
     *
     * @return {@code true}, wenn die Gruppe leer ist, sonst {@code false}.
     */
    @API
    public boolean isEmpty()
    {
        return actors.isEmpty();
    }

    /**
     * <b>Fügt</b> alle {@link Actor Figuren} dieser Gruppe der angegebenen
     * <b>{@link Scene} hinzu</b>.
     *
     * @param scene Die Szene, der die Figuren hinzugefügt werden sollen.
     *
     * @return diese Gruppe (für Method-Chaining)
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public Group<T> addToScene(Scene scene)
    {
        for (T actor : actors)
        {
            scene.add(actor);
        }
        return this;
    }

    /**
     * <b>Führt</b> eine <b>Aktion</b> für <b>alle</b> {@link Actor Figuren}
     * aus.
     *
     * Da die Klasse {@link Group} die Schnittstelle {@link Iterable}
     * implementiert, gibt es bereits eine {@code forEach}-Methode. Diese
     * Methode ist jedoch {@link ChainableMethod verkettbar}.
     *
     * @param action Die Aktion, die für jede passende Figur ausgeführt wird.
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public Group<T> forEachActor(Consumer<? super T> action)
    {
        Objects.requireNonNull(action,
            "Der Eingabeparameter 'action' darf nicht null sein.");
        for (T actor : actors)
        {
            action.accept(actor);
        }
        return this;
    }

    /**
     * <b>Führt</b> eine <b>Aktion</b> für alle {@link Actor Figuren} aus, die
     * Instanzen der angegebenen <b>Unterklasse</b> sind.
     *
     * @param <S> Die gesuchte Unterklasse.
     * @param clazz Die Unterklasse, nach der gefiltert werden soll.
     * @param action Die Aktion, die für jede passende Figur ausgeführt wird.
     *
     * @since 0.45.0
     */
    @API
    @ChainableMethod
    public <S extends T> Group<T> forEach(Class<S> clazz,
            Consumer<? super S> action)
    {
        Objects.requireNonNull(clazz,
            "Der Eingabeparameter 'clazz' darf nicht null sein.");
        Objects.requireNonNull(action,
            "Der Eingabeparameter 'action' darf nicht null sein.");
        for (T actor : actors)
        {
            if (clazz.isInstance(actor))
            {
                action.accept(clazz.cast(actor));
            }
        }
        return this;
    }
}
