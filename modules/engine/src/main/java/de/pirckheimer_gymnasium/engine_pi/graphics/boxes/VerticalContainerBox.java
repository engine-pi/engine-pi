package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

public class VerticalContainerBox extends MultipleChildBoxContainer
{
    public VerticalContainerBox(Box... childs)
    {
        super(childs);
    }

    @Override
    public int width()
    {
        int maxWidth = 0;
        for (Box box : childs)
        {
            if (box.width() > maxWidth)
            {
                maxWidth = box.width();
            }
        }
        return maxWidth;
    }

    @Override
    public int height()
    {
        int height = 0;
        for (Box box : childs)
        {
            height += box.height();
        }
        return height;
    }

    @Override
    public void calculateAnchors()
    {
        int yCursor = y;
        for (Box box : childs)
        {
            box.x = x;
            box.y = yCursor;
            yCursor += box.height();
        }
    }

}
