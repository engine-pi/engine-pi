package de.pirckheimer_gymnasium.pacman.debug;

import static de.pirckheimer_gymnasium.pacman.actors.GhostState.DOWN;
import static de.pirckheimer_gymnasium.pacman.actors.GhostState.LEFT;
import static de.pirckheimer_gymnasium.pacman.actors.GhostState.RIGHT;
import static de.pirckheimer_gymnasium.pacman.actors.GhostState.STAND;
import static de.pirckheimer_gymnasium.pacman.actors.GhostState.UP;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.pacman.Main;
import de.pirckheimer_gymnasium.pacman.actors.Blinky;
import de.pirckheimer_gymnasium.pacman.actors.Clyde;
import de.pirckheimer_gymnasium.pacman.actors.Ghost;
import de.pirckheimer_gymnasium.pacman.actors.Inky;
import de.pirckheimer_gymnasium.pacman.actors.Pinky;

public class GhostsDebugScene extends Scene implements KeyStrokeListener
{
    Ghost blinky;

    Ghost clyde;

    Ghost inky;

    Ghost pinky;

    Ghost current;

    public GhostsDebugScene()
    {
        getCamera().setMeter(16);
        blinky = addGhost(Blinky.class, 2, 2);
        clyde = addGhost(Clyde.class, -2, 2);
        inky = addGhost(Inky.class, -2, -2);
        pinky = addGhost(Pinky.class, 2, -2);
        current = clyde;
    }

    private Ghost addGhost(Class<? extends Ghost> clazz, int x, int y)
    {
        Ghost ghost = Ghost.createGhost(clazz);
        assert ghost != null;
        ghost.setPosition(x, y);
        add(ghost);
        return ghost;
    }

    private void setCurrent(Ghost ghost)
    {
        current.setState(STAND);
        current = ghost;
        current.setState(UP);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_SPACE -> current.setState(STAND);
        case KeyEvent.VK_DOWN -> current.setState(DOWN);
        case KeyEvent.VK_UP -> current.setState(UP);
        case KeyEvent.VK_LEFT -> current.setState(LEFT);
        case KeyEvent.VK_RIGHT -> current.setState(RIGHT);
        case KeyEvent.VK_1 -> setCurrent(blinky);
        case KeyEvent.VK_2 -> setCurrent(clyde);
        case KeyEvent.VK_3 -> setCurrent(inky);
        case KeyEvent.VK_4 -> setCurrent(pinky);
        }
    }

    public static void main(String[] args)
    {
        Main.start(new GhostsDebugScene(), 4);
    }
}
