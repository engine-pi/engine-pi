package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

public class GridBox extends ChildsBox
{
    int numberOfColumns = 2;

    public GridBox(Box... childs)
    {
        super(childs);
    }

    public int numberOfRows()
    {
        return (int) Math.ceil((double) numberOfChilds() / numberOfColumns);
    }
}
