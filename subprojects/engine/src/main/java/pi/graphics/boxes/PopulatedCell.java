package pi.graphics.boxes;

/**
 * Eine Zellbox, die einen Inhalt hat.
 *
 * <p>
 * Als Argument für die {@link java.util.function.Consumer}-Funktionen der
 * {@link ChildsBox}-Klassen gedacht.
 * </p>
 */
public class PopulatedCell<T extends Box>
{
    /**
     * Die übergeordnete Zelle
     */
    public CellBox cell;

    /**
     * Die Kind-Box der Zelle
     */
    public T box;

    @SuppressWarnings("unchecked")
    public PopulatedCell(CellBox cell)
    {
        this.cell = cell;
        this.box = (T) cell.child;
    }
}
