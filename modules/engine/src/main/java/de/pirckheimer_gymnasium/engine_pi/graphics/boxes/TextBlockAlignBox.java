package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

public class TextBlockAlignBox extends CombinedAlignBox
{
    public AlignBox align;

    public TextBlockBox text;

    public TextBlockAlignBox()
    {
        this("");
    }

    public TextBlockAlignBox(String content)
    {
        text = new TextBlockBox(content);
        align = new AlignBox(text);
        addChild(align);
    }

    @Override
    public int innerWidth()
    {
        return text.width;
    }

    @Override
    public int innerHeight()
    {
        return text.height;
    }

    @Override
    public CombinedAlignBox width(int width)
    {
        align.width(width);
        return this;
    }

    @Override
    public CombinedAlignBox height(int height)
    {
        align.height(height);
        return this;
    }

    @Override
    public CombinedAlignBox hAlign(HAlign hAlign)
    {
        align.hAlign(hAlign);
        return this;
    }

    @Override
    public CombinedAlignBox vAlign(VAlign vAlign)
    {
        align.vAlign(vAlign);
        return this;
    }

}
