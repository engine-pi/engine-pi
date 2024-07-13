package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.image.BufferedImage;

/**
 *
 * @param <State>
 *
 * @since 0.26.0
 */
public class StatefulImagesAnimation<State> extends StatefulAnimation<State>   {
    public StatefulImagesAnimation(double width, double height, double frameDuration) {
        super(width, height, frameDuration);
    }

    /**
     * @param state
     * @param frameDuration
     * @param images
     */
    public void addAnimation(State state, double frameDuration,
                             BufferedImage... images)
    {
        addState(state, Animation.createFromImages(frameDuration, width, height,
                images));
    }

    /**
     * @param state
     * @param images
     */
    public void addAnimation(State state, BufferedImage... images)
    {
        addAnimation(state, frameDuration, images);
    }

    /**
     * @param state
     * @param filePaths
     */
    public void addAnimation(State state, double frameDuration, String... filePaths)
    {
        addState(state, Animation.createFromImages(frameDuration, width, height,
                filePaths));
    }

    /**
     * @param state
     * @param filePaths
     */
    public void addAnimation(State state, String... filePaths)
    {
        addAnimation(state, frameDuration, filePaths);
    }
}

