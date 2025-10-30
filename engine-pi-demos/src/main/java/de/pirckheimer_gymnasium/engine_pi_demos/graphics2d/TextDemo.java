package de.pirckheimer_gymnasium.engine_pi_demos.graphics2d;

import java.awt.Graphics2D;

class TextDemo extends Component
{

    @Override
    public void render(Graphics2D g)
    {
        g.drawString("This is gona be awesome", 70, 60);
    }

    public static void main(String[] args)
    {
        new TextDemo().show();
    }
}
