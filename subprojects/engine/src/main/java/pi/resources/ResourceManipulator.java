package pi.resources;

/**
 * Eine Schnittstelle für Klassen die Resourcen manipulieren bzw. verändern.
 *
 * @author Josef Friedrich
 */
public interface ResourceManipulator<T>
{
    /**
     * Wird ausgeführt, bevor eine Resource zum Resourcenspeicher hinzugefügt
     * wird.
     *
     * @param resourceName Der Name der Resource als Dateipfad.
     * @param resource Die Resource
     *
     * @return Die veränderte Resource oder {@code null} falls die Resource
     *     nicht verändert werden soll.
     */
    T beforeAdd(String resourceName, T resource);
}
