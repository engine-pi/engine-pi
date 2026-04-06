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

import pi.Scene;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/actor/GroupDemo.java

/**
 * Eine <b>Gruppe</b> bestehend aus mehreren {@link Actor}-Objekten.
 *
 * <p>
 * Über eine Gruppe lassen sich mehrere Akteure gemeinsam verwalten, z. B.
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
    private final List<T> actors;

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
     * <b>Fügt</b> alle {@link Actor Figuren} dieser Gruppe der angegebenen
     * <b>{@link Scene} hinzu</b>.
     *
     * @param scene Die Szene, der die Figuren hinzugefügt werden sollen.
     *
     * @return diese Gruppe (für Method-Chaining)
     *
     * @since 0.45.0
     */
    public Group<T> addToScene(Scene scene)
    {
        for (T actor : actors)
        {
            scene.add(actor);
        }
        return this;
    }
}
