package pi.actor;

import pi.graphics.boxes_ng.EllipseBox;

public class Ellipse extends BoxActorNg<EllipseBox>
{

    public Ellipse(double width, double height)
    {
        super(new EllipseBox(width, height));
    }

}
