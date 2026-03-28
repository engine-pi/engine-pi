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
package demos.classes.dsa.threads.eierautomat;

import java.util.ArrayList;

import pi.Rectangle;
import pi.Scene;

/**
 * Eierautomat auf dem Lande
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Eierautomat
{
    /**
     * Feld, das die Eierkartons im Automaten verwaltet
     */
    protected ArrayList<Eierkarton> eierkartons;

    Scene scene;

    /**
     * Besetzt die Attribute vor.
     */
    Eierautomat(Scene scene)
    {
        Rectangle automat = new Rectangle();
        automat.size(4, 15);
        automat.color("grau");
        automat.center(0, 0);
        eierkartons = new ArrayList<Eierkarton>();
        this.scene = scene;
        scene.add(automat);
    }

    protected void fügeEierkartonHinzu(int fach)
    {
        Eierkarton eierkarton = new Eierkarton();
        eierkarton.center(0, 6.5 - 1.40 * fach);
        scene.add(eierkarton);
        eierkartons.add(eierkarton);
    }

    /**
     * Ein Eierkarton wird aus dem Feld entfernt und seine Darstellung aus dem
     * Zeichenfenster
     */
    protected Eierkarton entferneEierkarton()
    {
        Eierkarton gekaufterKarton = eierkartons.remove(0);
        scene.remove(gekaufterKarton);
        return gekaufterKarton;
    }

    /**
     * Befüllen des Eierautomaten mit neuen Eierkartons
     */
    synchronized boolean befülle()
    {
        if (eierkartons.size() == 0)
        {
            // Der leere Automat wird mit zehn neuen Kartons befüllt.
            for (int i = 0; i < 10; i++)
            {
                fügeEierkartonHinzu(i);
            }
            return true;
        }
        return false;
    }

    /**
     * Holen eines Eierkartons aus dem Automaten
     *
     * @return Eierkarton oder null bei Fehlversuch
     */
    synchronized Eierkarton holeEier()
    {
        if (eierkartons.size() > 0)
        {
            return entferneEierkarton();
        }
        return null;
    }
}
