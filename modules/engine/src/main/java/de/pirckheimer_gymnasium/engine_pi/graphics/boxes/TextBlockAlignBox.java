package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

public class TextBlockAlignBox extends CombinedChildBox
{
    public ContainerBox container;

    public TextBlockBox text;

    public TextBlockAlignBox()
    {
        this("");
    }

    public TextBlockAlignBox(Object content)
    {
        text = new TextBlockBox(content);
        container = new ContainerBox(text);
        addChild(container);
        supportsDefinedDimension = true;
    }

    public int innerWidth()
    {
        return text.width;
    }

    public int innerHeight()
    {
        return text.height;
    }

    public TextBlockAlignBox width(int width)
    {
        container.width(width);
        return this;
    }

    public TextBlockAlignBox height(int height)
    {
        container.height(height);
        return this;
    }

    public TextBlockAlignBox hAlign(HAlign hAlign)
    {
        container.hAlign(hAlign);
        return this;
    }

    public TextBlockAlignBox vAlign(VAlign vAlign)
    {
        container.vAlign(vAlign);
        return this;
    }
}
