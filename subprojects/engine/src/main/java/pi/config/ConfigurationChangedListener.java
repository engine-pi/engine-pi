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
