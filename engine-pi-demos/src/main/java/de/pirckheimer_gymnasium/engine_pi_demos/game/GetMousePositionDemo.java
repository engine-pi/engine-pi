package de.pirckheimer_gymnasium.engine_pi_demos.game;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.util.TextUtil;

/**
 * Demonstriert die Methode {@link Game#getMousePosition()}.
 */
public class GetMousePositionDemo extends Scene implements FrameUpdateListener
{
    private final Text x;

    private final Text y;

    public GetMousePositionDemo()
    {
        addText("x:", -3, 0);
        x = addText("", -2, 0);
        addText("y:", 1, 0);
        y = addText("", 2, 0);
        setBackgroundColor("white");
    }

    @Override
    public void onFrameUpdate(double pastTime)
    {
        Vector position = Game.getMousePosition();
        x.setContent(TextUtil.roundNumber(position.getX()));
        y.setContent(TextUtil.roundNumber(position.getY()));
    }

    public static void main(String[] args)
    {
        Game.start(new GetMousePositionDemo());
    }
}
