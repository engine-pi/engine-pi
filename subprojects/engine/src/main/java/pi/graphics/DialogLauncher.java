/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.graphics;

import java.awt.Component;

import javax.swing.JOptionPane;

import pi.annotations.API;
import pi.annotations.Internal;

/**
 * Öffnet verschiedene <b>modale Dialogfenster</b>.
 */
public class DialogLauncher
{
    private Component parent;

    /**
     * @hidden
     */
    @Internal
    public DialogLauncher(Component parent)
    {
        this.parent = parent;
    }

    /**
     * Gibt eine <b>Nachricht</b> in einem modalen Dialogfenster aus.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     */
    @API
    public void showMessage(String message)
    {
        showMessage(message, null);
    }

    /**
     * Gibt eine <b>Nachricht</b> in einem modalen Dialogfenster aus.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     */
    @API
    public void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(parent, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Öffnet ein modales Dialogfenster, in dem der Nutzer zur <b>Eingabe von
     * Text</b> in einer Zeile aufgerufen wird.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     *
     * @return Die Eingabe des Nutzers. Ist <code>null</code>, wenn der Nutzer
     *     den Dialog abgebrochen hat.
     */
    @API
    public String requestStringInput(String message)
    {
        return requestStringInput(message, null);
    }

    /**
     * Öffnet ein modales Dialogfenster, in dem der Nutzer zur <b>Eingabe von
     * Text</b> in einer Zeile aufgerufen wird.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers. Ist <code>null</code>, wenn der Nutzer
     *     den Dialog abgebrochen hat.
     */
    @API
    public String requestStringInput(String message, String title)
    {
        return JOptionPane.showInputDialog(parent, message, title,
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Öffnet ein modales Dialogfenster mit <b>Ja/Nein-Schaltflächen</b>.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     *
     * @return Die Eingabe des Nutzers:
     *     <ul>
     *     <li>Ja → <code>true</code></li>
     *     <li>Nein → <code>false</code></li>
     *     <li>Abbruch (= Dialog manuell schließen) → <code>false</code></li>
     *     </ul>
     */
    @API
    public boolean requestYesNo(String message)
    {
        return requestYesNo(message, null);
    }

    /**
     * Öffnet ein modales Dialogfenster mit <b>Ja/Nein-Schaltflächen</b>.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers:
     *     <ul>
     *     <li>Ja → <code>true</code></li>
     *     <li>Nein → <code>false</code></li>
     *     <li>Abbruch (= Dialog manuell schließen) → <code>false</code></li>
     *     </ul>
     */
    @API
    public boolean requestYesNo(String message, String title)
    {
        return JOptionPane.showConfirmDialog(parent, message, title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /**
     * Öffnet ein modales Dialogfenster mit <b>OK/Abbrechen-Schaltflächen</b>.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     *
     * @return Die Eingabe des Nutzers:
     *     <ul>
     *     <li>OK → <code>true</code></li>
     *     <li>Abbrechen → <code>false</code></li>
     *     <li>Abbruch (= Dialog manuell schließen) → <code>false</code></li>
     *     </ul>
     */
    @API
    public boolean requestOkCancel(String message)
    {
        return requestOkCancel(message, null);
    }

    /**
     * Öffnet ein modales Dialogfenster mit <b>OK/Abbrechen-Schaltflächen</b>.
     *
     * <p class="development-note">
     * Der Dialog ist über {@link javax.swing.JOptionPane} implementiert.
     * </p>
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers:
     *     <ul>
     *     <li>OK → <code>true</code></li>
     *     <li>Abbrechen → <code>false</code></li>
     *     <li>Abbruch (= Dialog manuell schließen) → <code>false</code></li>
     *     </ul>
     */
    @API
    public boolean requestOkCancel(String message, String title)
    {
        return JOptionPane.showConfirmDialog(parent, message, title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
    }
}
