/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package demos.edu_projects.concurrency.philosophers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Verwaltet alle 26 Philosophen, die dargestellt werden können.
 */
public class PhilosophersCollection
{
    /**
     * Verwaltet alle Philosophen.
     */
    private List<Philosopher> all;

    public PhilosophersCollection()
    {
        all = new ArrayList<>(26);

        // Go to
        // file:///data/school/repos/inf/java/engine-pi/assets/demos/resources/philosophers/README.md
        create("Aquin", "#2a3234", 1225, 1274);
        create("Aristoteles", "#0fa0e3", -384, -322);
        create("Augustinus", "#ffb61c", 354, 430);
        create("Averroes", "#dac66e", 1126, 1198);
        create("Bacon", "#377296", 1561, 1626);
        create("Buddha", "#ff7a18", -563, -483);
        create("Descartes", "#1f2628", 1596, 1650);
        create("Hegel", "#7f5437", 1770, 1831);
        create("Hume", "#f7333f", 1711, 1776);
        create("Jesus", "#fa323f", -4, 30);
        create("Kant", "#767733", 1724, 1804);
        create("Kierkegaard", "#471008", 1813, 1855);
        create("Konfuzius", "#8b101d", -551, -479);
        create("Laozi", "#114ea6", -600, -550);
        create("Machiavelli", "#f7323d", 1469, 1527);
        create("Marx", "#1b2223", 1818, 1883);
        create("Montesquieu", "#87111b", 1689, 1755);
        create("Nietzsche", "#293133", 1844, 1900);
        create("Platon", "#bda8d3", -427, -348);
        create("Rousseau", "#3c7eaa", 1712, 1778);
        create("Schopenhauer", "#323835", 1788, 1860);
        create("Seneca", "#dde1de", 1, 65);
        create("Sokrates", "#f3a05b", -469, -399);
        create("Thales", "#0fa2e3", -623, -548);
        create("Unamuno", "#232a2c", 1864, 1936);
        create("Voltaire", "#4b2c62", 1694, 1778);
    }

    private void create(String name, String color, int birth, int death)
    {
        all.add(new Philosopher(name, color, birth, death));
    }

    /**
     * <b>Wählt</b> zufällig eine bestimme Anzahl an Philosophen aus, sortiert
     * sie nach dem <b>Geburtsjahr</b> und gibt sie als <b>Liste</b> zurück.
     *
     * @param count Die <b>Anzahl</b> an Philosophen.
     */
    public List<Philosopher> select(int count)
    {
        if (count < 0 || count > all.size())
        {
            throw new IllegalArgumentException(
                    "Anzahl der essenden Philosophen ist ungültig: " + count);
        }

        List<Philosopher> shuffled = new ArrayList<>(all);
        Collections.shuffle(shuffled);

        List<Philosopher> selected = new ArrayList<>(
                shuffled.subList(0, count));

        // Wir sortieren aufsteigenend nach dem Geburtsjahr.
        selected.sort(Comparator.comparingInt(Philosopher::birth));

        return selected;
    }

}
