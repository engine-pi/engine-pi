package de.pirckheimer_gymnasium.engine_pi.graphics.boxes;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/modules/demos/src/main/java/de/pirckheimer_gymnasium/demos/classes/graphics/boxes/TextTableBoxDemo.java

public class TextTableBox extends GenericGridBox<TextBlockAlignBox>
{
    public TextTableBox(String... contents)
    {
        super();
        for (String content : contents)
        {
            addChild(new TextBlockAlignBox(content));
        }
        buildGrid();
    }
}
