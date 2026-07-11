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
package pi.config;

import java.beans.PropertyChangeEvent;
import java.util.EventListener;

/**
 * Empfängt Ereignisse, wenn sich eine Konfigurationseigenschaft geändert hat.
 *
 * @see ConfigGroup#onChanged(ConfigurationChangedListener)
 */
@FunctionalInterface
public interface ConfigurationChangedListener extends EventListener
{
    /**
     * Wird aufgerufen, wenn eine Konfigurationseigenschaft über die Methode
     * {@link ConfigGroup#set(String, Object)} geändert wurde.
     *
     * @param event Das Ereignis mit den Informationen zur Eigenschaftsänderung.
     */
    void configurationChanged(PropertyChangeEvent event);
}
