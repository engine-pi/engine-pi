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
package pi.loop;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import pi.Configuration;
import pi.Game;
import pi.Scene;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.debug.CoordinateSystemDrawer;
import pi.debug.DebugInfoBoxDrawer;
import pi.event.EventListeners;
import pi.event.FrameUpdateListener;
import pi.graphics.RenderTarget;
import pi.util.Graphics2DUtil;

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
    /**
     * Die <b>angestrebte Anzeigedauer</b> eines Einzelbilds in Sekunden.
     */
    private static double DESIRED_FRAME_DURATION = 1.0
            / Configuration.get().graphics().framerate();

    /**
     * Die <b>tatsächliche Anzeigedauer</b> eines <b>Einzelbilds</b> in
     * Sekunden.
     */
    private double frameDuration;

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

    private DebugInfoBoxDrawer infoBoxDrawer;

    /**
     * Die <b>Anzahl</b> an <b>Einzelbilder</b>, die seit dem Start des Spiels
     * berechnet wurden.
     */
    private long frameCounter;

    public GameLoop(RenderTarget render, Supplier<Scene> currentScene,
            Supplier<Boolean> isDebug)
    {
        this.render = render;
        this.currentScene = currentScene;
        this.isDebug = isDebug;
        infoBoxDrawer = new DebugInfoBoxDrawer();
    }

    /**
     * Gibt die <b>aktuelle Szene</b> aus.
     *
     * @return Die aktuelle Szene.
     *
     * @since 0.42.0
     */
    @Getter
    public Scene getCurrentScene()
    {
        return currentScene.get();
    }

    /**
     * Gibt die <b>Anzahl</b> an <b>Einzelbilder</b> aus, die seit dem Start des
     * Spiels berechnet wurden.
     *
     * @return Die <b>Anzahl</b> an <b>Einzelbilder</b>, die seit dem Start des
     *     Spiels berechnet wurden.
     *
     * @since 0.42.0
     */
    @Getter
    public long getFrameCounter()
    {
        return frameCounter;
    }

    /**
     * Gibt <b>tatsächliche Anzeigedauer</b> eines <b>Einzelbilds</b> in
     * Sekunden aus.
     *
     * @return Die <b>tatsächliche Anzeigedauer</b> eines <b>Einzelbilds</b> in
     *     Sekunden.
     *
     * @since 0.42.0
     */
    @Getter
    public double getFrameDuration()
    {
        return frameDuration;
    }

    /**
     * Fügt eine {@link Runnable Aufgabe} in die Warteschlange ein, um ihn
     * später auszuführen.
     *
     * @param task Die auszuführende {@link Runnable Aufgabe}.
     */
    public void enqueue(Runnable task)
    {
        dispatchableQueue.add(task);
    }

    /**
     * Führt die Haupt-Ereignisschleife aus, die kontinuierlich bis zur
     * Unterbrechung des Threads läuft.
     *
     * Die Methode orchestriert den gesamten Spielzyklus:
     * <ul>
     * <li>Berechnet die verstrichene Zeit seit dem letzten Frame (maximal 2x
     * die gewünschte Frame-Dauer)</li>
     * <li>Aktualisiert die aktuelle Szene mit der verstrichenen Zeit</li>
     * <li>Ruft die {@link FrameUpdateListener} der aktuellen Szene auf.</li>
     * <li>Aktualisiert die Kamera der aktuellen Szene</li>
     * <li>Verarbeitet alle ausstehenden Aufgaben aus der Dispatch-Queue</li>
     * <li>Rendert den aktuellen Frame</li>
     * <li>Synchronisiert die Frame-Rate durch Sleep-Mechanismus, um die
     * gewünschte Frame-Dauer einzuhalten</li>
     * <li>Berechnet die tatsächliche Frame-Dauer für den nächsten Zyklus</li>
     * </ul>
     *
     * Die Loop wird beendet, wenn der aktuelle Thread unterbrochen wird
     * (InterruptedException). Nach Beendigung der Loop wird der Thread-Pool
     * korrekt heruntergefahren und wartet maximal 3 Sekunden auf die
     * Terminierung ausstehender Aufgaben.
     *
     * @throws RuntimeException wenn ein unerwarteter Fehler während der
     *     Ausführung der Loop auftritt
     */
    public void run()
    {
        frameDuration = DESIRED_FRAME_DURATION;
        long frameStart = System.nanoTime();
        long frameEnd;
        while (!Thread.currentThread().isInterrupted())
        {
            Scene scene = getCurrentScene();
            try
            {
                frameCounter++;
                double pastTime = Math.min(2 * DESIRED_FRAME_DURATION,
                        frameDuration);
                scene.step(pastTime, threadPoolExecutor::submit);
                // Beobachter der Bildaktualisierung.
                frameUpdateListeners
                        .invoke(listener -> listener.onFrameUpdate(pastTime));
                // Aktualisiert die Kamera der aktuellen Szene
                scene.getCamera().onFrameUpdate();
                // Ruft die {@link FrameUpdateListener} der aktuellen Szene auf.
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
        Scene scene = getCurrentScene();
        // have to be the same @ Game.screenshot!
        Graphics2DUtil.setAntiAliasing(g, true);
        // Absoluter Hintergrund
        g.setColor(scene.getBackgroundColor());
        g.fillRect(0, 0, width, height);
        g.setClip(0, 0, width, height);
        AffineTransform oldTransform = g.getTransform();
        scene.render(g, width, height);
        g.setTransform(oldTransform);
        if (isDebug.get())
        {
            new CoordinateSystemDrawer(g, scene, width, height).draw();
            infoBoxDrawer.draw(g, this);
        }
        g.dispose();
    }

    public static void main(String[] args)
    {
        Game.start();
    }
}
