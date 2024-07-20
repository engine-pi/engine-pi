package de.pirckheimer_gymnasium.engine_pi_demos.actor;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.StatefulAnimation;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;

import java.awt.event.KeyEvent;

enum State
{
    DOUBLE_JUMP("Double Jump"), FALL("Fall"), HIT("Hit"), IDLE("Idle"),
    JUMP("Jump"), RUN("Run"), WALL_JUMP("Wall Jump");

    private final String fileName;

    State(String fileName)
    {
        this.fileName = fileName;
    }

    public String getImagePath()
    {
        return "Pixel-Adventure-1/Main Characters/Virtual Guy/" + fileName
                + " (32x32).png";
    }
}

class Character extends StatefulAnimation<State>
{
    public Character()
    {
        super(1, 1, 0.1);
        for (State state : State.values())
        {
            addFromSpritesheet(state, 32, 32, state.getImagePath());
        }
    }
}

/**
 * Demonstriert eine <b>animierte</b> Figur, die <b>mehrere Zustände</b> haben
 * kann ({@link StatefulAnimation}).
 *
 * @author Josef Friedrich
 */
public class StatefulAnimationDemo extends Scene implements KeyStrokeListener
{
    Character character;

    public StatefulAnimationDemo()
    {
        character = new Character();
        setFocus(character);
        getCamera().setMeter(32);
        character.setState(State.IDLE);
        add(character);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_LEFT -> character.setState(State.JUMP);
        case KeyEvent.VK_RIGHT -> character.setState(State.HIT);
        case KeyEvent.VK_UP -> character.setState(State.RUN);
        case KeyEvent.VK_DOWN -> character.setState(State.DOUBLE_JUMP);
        }
    }

    public static void main(String[] args)
    {
        Game.setPixelMultiplication(8);
        Game.start(new StatefulAnimationDemo(), 50, 50);
    }
}
