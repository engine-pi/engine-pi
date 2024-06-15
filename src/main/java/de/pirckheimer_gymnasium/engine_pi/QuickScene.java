package de.pirckheimer_gymnasium.engine_pi;

import de.pirckheimer_gymnasium.engine_pi.actor.ActorCreator;

public interface QuickScene extends ActorCreator
{
    default Scene getScene()
    {
        return new Scene();
    }

    void setup();
}
