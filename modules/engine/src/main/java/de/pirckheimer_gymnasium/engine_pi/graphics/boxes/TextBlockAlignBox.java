package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

public class TextBlockAlignBox extends CombinedAlignBox
{
    public ContainerBox container;

    public TextBlockBox text;

    public TextBlockAlignBox()
    {
        this("");
    }

    public TextBlockAlignBox(String content)
    {
        text = new TextBlockBox(content);
        container = new ContainerBox(text);
        addChild(container);
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
        container.width(width);
        return this;
    }

    @Override
    public CombinedAlignBox height(int height)
    {
        container.height(height);
        return this;
    }

    @Override
    public CombinedAlignBox hAlign(HAlign hAlign)
    {
        container.hAlign(hAlign);
        return this;
    }

    @Override
    public CombinedAlignBox vAlign(VAlign vAlign)
    {
        container.vAlign(vAlign);
        return this;
    }

}
