package pi.resources;

import pi.resources.color.ColorContainer;

/**
 * Schnittstelle für die Ressourcenspeicher.
 *
 * <p>
 * Neben der abstrakten Klasse {@link ResourcesContainer}, die Datei gebundene
 * Ressourcen verwaltet, wird diese Schnittstelle noch von der Klasse
 * {@link ColorContainer} implementiert.
 * </p>
 *
 * @param <T> Die Ressource, z.B. BufferedImage, Sound, Color
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 */
public interface Container<T>
{
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
     */
    T add(String name, T resource);

    /**
     * Leert den Ressourcenspeicher, indem alle zuvor geladenen Ressourcen
     * entfernt werden.
     *
     * @see ResourcesContainer#clear()
     */
    void clear();

    /**
     * Ruft die Ressource mit dem angegebenen Namen ab.<br>
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
     * @param name Der Name, unter dem die Ressource verwaltet wird.
     *
     * @return Die Ressource mit dem angegebenen Namen oder null, wenn sie nicht
     *     gefunden wird.
     */
    T get(String name);

    int count();
}
