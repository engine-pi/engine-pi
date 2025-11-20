package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

interface FixedSizeSetter
{

    Box getBox();

    default Box size(int width, int height)
    {
        Box box = getBox();
        if (box.fixedSize == null)
        {
            box.fixedSize = new FixedSize();
        }
        box.fixedSize.width = width;
        box.fixedSize.height = height;
        return getBox();
    }
}
