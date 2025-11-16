package de.pirckheimer_gymnasium.engine_pi.dsa.turtle;

import java.util.function.Supplier;

public abstract class TurtleAlgorithmSeries extends TurtleAlgorithm
{

    protected Supplier<Boolean> onRepeat;

    public TurtleAlgorithmSeries onRepeat(Supplier<Boolean> afterRepeat)
    {
        this.onRepeat = afterRepeat;
        return this;
    }

}
