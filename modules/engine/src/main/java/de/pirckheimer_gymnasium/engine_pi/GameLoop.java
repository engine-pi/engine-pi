/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/GameLogic.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.debug.CoordinateSystemDrawer;
import de.pirckheimer_gymnasium.engine_pi.debug.InfoBoxDrawer;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.graphics.RenderTarget;

/**
 * Die <b>Ereignisschleife</b> der Engine.
 *
 * <p>
 * In ihr wird die Verarbeitung der Eingaben des Benutzers angestoßen, die
 * Berechnung neuer Weltzustände gestartet und das Erzeugen neuer Ausgaben
 * angewiesen.
 * </p>
 *
 * @author Michael Andonie
 * @author Niklas Keller
 */
public final class GameLoop
{
    private static final double DESIRED_FRAME_DURATION = 0.016;

    private static final int NANOSECONDS_PER_SECOND = 1000000000;

    private final ExecutorService threadPoolExecutor = Executors
            .newCachedThreadPool();

    private final RenderTarget render;

    private final Supplier<Scene> currentScene;

    private final Supplier<Boolean> isDebug;

    /**
     * Queue aller Dispatchables, die im nächsten Frame ausgeführt werden.
     */
    private final Queue<Runnable> dispatchableQueue = new ConcurrentLinkedQueue<>();

    /**
     * Für globale Beobachter, die auf Bildaktualisierung reagieren.
     */
    private final EventListeners<FrameUpdateListener> frameUpdateListeners = new EventListeners<>();

    private double frameDuration;

    public GameLoop(RenderTarget render, Supplier<Scene> currentScene,
            Supplier<Boolean> isDebug)
    {
        this.render = render;
        this.currentScene = currentScene;
        this.isDebug = isDebug;
    }

    public void enqueue(Runnable runnable)
    {
        dispatchableQueue.add(runnable);
    }

    public void run()
    {
        this.frameDuration = DESIRED_FRAME_DURATION;
        long frameStart = System.nanoTime();
        long frameEnd;
        while (!Thread.currentThread().isInterrupted())
        {
            Scene scene = this.currentScene.get();
            try
            {
                double pastTime = Math.min(2 * DESIRED_FRAME_DURATION,
                        frameDuration);
                scene.step(pastTime, threadPoolExecutor::submit);
                // Beobachter der Bildaktualisierung.
                frameUpdateListeners
                        .invoke(listener -> listener.onFrameUpdate(pastTime));
                scene.getCamera().onFrameUpdate();
                scene.invokeFrameUpdateListeners(pastTime);
                Runnable runnable = dispatchableQueue.poll();
                while (runnable != null)
                {
                    runnable.run();
                    runnable = dispatchableQueue.poll();
                }
                render();
                frameEnd = System.nanoTime();
                double duration = (double) (frameEnd - frameStart)
                        / NANOSECONDS_PER_SECOND;
                if (duration < DESIRED_FRAME_DURATION)
                {
                    try
                    {
                        // noinspection BusyWait
                        Thread.sleep((int) (1000
                                * (DESIRED_FRAME_DURATION - duration)));
                    }
                    catch (InterruptedException e)
                    {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                frameEnd = System.nanoTime();
                frameDuration = ((double) (frameEnd - frameStart)
                        / NANOSECONDS_PER_SECOND);
                frameStart = frameEnd;
            }
            catch (InterruptedException e)
            {
                break;
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
        threadPoolExecutor.shutdown();
        try
        {
            threadPoolExecutor.awaitTermination(3, TimeUnit.SECONDS);
        }
        catch (InterruptedException e)
        {
            return;
        }
    }

    public EventListeners<FrameUpdateListener> getFrameUpdateListener()
    {
        return frameUpdateListeners;
    }

    public void render(RenderTarget renderTarget)
    {
        renderTarget.render(this::render);
    }

    private void render()
    {
        render.render(this::render);
    }

    /**
     * Führt die gesamte Zeichenroutine aus.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param width Die Breite in Pixel.
     * @param height Die Höhe in Pixel.
     *
     * @hidden
     */
    @Internal
    private void render(Graphics2D g, int width, int height)
    {
        Scene scene = currentScene.get();
        // have to be the same @ Game.screenshot!
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
        // Absoluter Hintergrund
        g.setColor(scene.getBackgroundColor());
        g.fillRect(0, 0, width, height);
        g.setClip(0, 0, width, height);
        AffineTransform transform = g.getTransform();
        scene.render(g, width, height);
        g.setTransform(transform);
        if (isDebug.get())
        {
            new CoordinateSystemDrawer(g, scene, width, height).draw();
            InfoBoxDrawer.draw(g, scene, frameDuration,
                    scene.getWorldHandler().getWorld().getBodyCount());
        }
        g.dispose();
    }

    public static void main(String[] args)
    {
        Game.debug();
        Game.start();
    }
}
