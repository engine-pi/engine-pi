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
            addFromSpritesheet(state, state.getImagePath(), 32, 32);
        }
    }
}

public class StatefulAnimationDemo extends Scene implements KeyStrokeListener
{
    Character character;

    public StatefulAnimationDemo()
    {
        character = new Character();
        character.setState(State.JUMP);
        add(character);
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        switch (event.getKeyCode())
        {
        case KeyEvent.VK_LEFT -> System.out.println();
        }
    }

    public static void main(String[] args)
    {
        Game.start(new StatefulAnimationDemo());
    }
}
