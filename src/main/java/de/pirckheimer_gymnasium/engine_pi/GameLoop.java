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

import static de.pirckheimer_gymnasium.engine_pi.Resources.getColor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.graphics.RenderTarget;

public final class GameLoop
{
    private static final int DEBUG_INFO_HEIGHT = 20;

    private static final int DEBUG_INFO_LEFT = 10;

    private static final int DEBUG_INFO_TEXT_OFFSET = 16;

    private static final Color DEBUG_GRID_COLOR = new Color(255, 255, 255, 100);

    private static final int GRID_SIZE_IN_PIXELS = 150;

    private static final int GRID_SIZE_METER_LIMIT = 100000;

    private static final int DEBUG_TEXT_SIZE = 12;

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
                double deltaSeconds = Math.min(2 * DESIRED_FRAME_DURATION,
                        frameDuration);
                scene.step(deltaSeconds, threadPoolExecutor::submit);
                // Beobachter der Bildaktualisierung.
                frameUpdateListeners.invoke(
                        listener -> listener.onFrameUpdate(deltaSeconds));
                scene.getCamera().onFrameUpdate();
                scene.invokeFrameUpdateListeners(deltaSeconds);
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
            // noinspection UnnecessaryReturnStatement
            return; // if interrupted again, don't wait
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
     * @param g      Das Zeichenobjekt.
     * @param width  Die Breite in Pixel.
     * @param height Die Höhe in Pixel.
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
            renderGrid(g, scene, width, height);
            renderInfo(g, new DebugInfo(frameDuration, currentScene.get()
                    .getWorldHandler().getWorld().getBodyCount()));
        }
        g.dispose();
    }

    /**
     * Zeichnet Debug-Informationen auf dem Bildschirm.
     *
     * @param g      Das Graphics-Objekt zum Zeichnen.
     * @param Scene  Die Szene, in die Debug-Information gezeichen werden
     *               sollen.
     * @param width  Die Breite in Pixel.
     * @param height Die Höhe in Pixel.
     */
    @Internal
    private void renderGrid(Graphics2D g, Scene scene, int width, int height)
    {
        AffineTransform pre = g.getTransform();
        Camera camera = scene.getCamera();
        Vector position = camera.getPosition();
        double rotation = -camera.getRotation();
        g.setClip(0, 0, width, height);
        g.translate(width / 2, height / 2);
        double pixelPerMeter = camera.getMeter();
        g.rotate(Math.toRadians(rotation), 0, 0);
        g.translate(-position.getX() * pixelPerMeter,
                position.getY() * pixelPerMeter);
        int gridSizeInMeters = (int) Math
                .round(GRID_SIZE_IN_PIXELS / pixelPerMeter);
        double gridSizeInPixels = gridSizeInMeters * pixelPerMeter;
        double gridSizeFactor = gridSizeInPixels / gridSizeInMeters;
        if (gridSizeInMeters > 0 && gridSizeInMeters < GRID_SIZE_METER_LIMIT)
        {
            int windowSizeInPixels = Math.max(width, height);
            int startX = (int) (position.getX()
                    - windowSizeInPixels / 2 / pixelPerMeter);
            int startY = (int) ((-1 * position.getY())
                    - windowSizeInPixels / 2 / pixelPerMeter);
            startX -= (startX % gridSizeInMeters) + gridSizeInMeters;
            startY -= (startY % gridSizeInMeters) + gridSizeInMeters;
            startX -= gridSizeInMeters;
            int stopX = (int) (startX + windowSizeInPixels / pixelPerMeter
                    + gridSizeInMeters * 2);
            int stopY = (int) (startY + windowSizeInPixels / pixelPerMeter
                    + gridSizeInMeters * 2);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, DEBUG_TEXT_SIZE));
            g.setColor(DEBUG_GRID_COLOR);
            for (int x = startX; x <= stopX; x += gridSizeInMeters)
            {
                g.fillRect((int) (x * gridSizeFactor) - 1,
                        (int) ((startY - 1) * gridSizeFactor), 2,
                        (int) (windowSizeInPixels + 3 * gridSizeInPixels));
            }
            for (int y = startY; y <= stopY; y += gridSizeInMeters)
            {
                g.fillRect((int) ((startX - 1) * gridSizeFactor),
                        (int) (y * gridSizeFactor - 1),
                        (int) (windowSizeInPixels + 3 * gridSizeInPixels), 2);
            }
            for (int x = startX; x <= stopX; x += gridSizeInMeters)
            {
                for (int y = startY; y <= stopY; y += gridSizeInMeters)
                {
                    g.drawString(x + " / " + -y, (int) (x * gridSizeFactor + 5),
                            (int) (y * gridSizeFactor - 5));
                }
            }
        }
        g.setTransform(pre);
    }

    /**
     * Zeichnet zusätzliche Debug-Informationen auf dem Bildschirm.
     *
     * @param g Das Graphics-Objekt zum Zeichnen.
     */
    @Internal
    private void renderInfo(Graphics2D g, DebugInfo debugInfo)
    {
        // Einzelbilder pro Sekunden
        double frameDuration = debugInfo.frameDuration();
        drawTextBox(g, "FPS: "
                + (frameDuration == 0 ? "∞" : Math.round(1 / frameDuration)),
                10, getColor("blue"));
        // Anzahl an Figuren
        drawTextBox(g, "Actors: " + debugInfo.bodyCount(), 50,
                getColor("green"));
        // Schwerkraft
        Scene scene = currentScene.get();
        Vector gravity = scene.getGravity();
        Color gravityColor = Resources.colorSchema.getIndigo();
        if (!gravity.isNull())
        {
            drawTextBox(g, String.format("G(x,y): %.2f,%.2f", gravity.getX(),
                    gravity.getY()), 90, gravityColor);
            drawGravityVector(g, 40, 145, gravity, gravityColor);
        }
    }

    /**
     * Zeichnet eine Textbox, die sich automatische an die Länge des Texts
     * anpasst. Die Schriftfarbe ist standardmäßig Weiß.
     *
     * @param g
     * @param text
     * @param y
     * @param background
     * @param border
     */
    private void drawTextBox(Graphics2D g, String text, int y, Color background)
    {
        Font font = new Font("Monospaced", Font.PLAIN, DEBUG_TEXT_SIZE);
        FontMetrics fm = g.getFontMetrics(font);
        Rectangle2D bounds;
        bounds = fm.getStringBounds(text, g);
        // Hintergrund
        g.setColor(background);
        g.fillRect(DEBUG_INFO_LEFT, y,
                (int) bounds.getWidth() + DEBUG_INFO_HEIGHT,
                (int) bounds.getHeight() + DEBUG_INFO_TEXT_OFFSET);
        // Rahmen
        g.setColor(background.darker());
        g.drawRect(DEBUG_INFO_LEFT, y,
                (int) bounds.getWidth() + DEBUG_INFO_HEIGHT - 1,
                (int) bounds.getHeight() + DEBUG_INFO_TEXT_OFFSET - 1);
        // Text
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(text, DEBUG_INFO_LEFT + 10,
                y + 8 + fm.getHeight() - fm.getDescent());
    }

    private void drawGravityVector(Graphics2D g, int x, int y, Vector gravity,
            Color color)
    {
        drawArrowLine(g, x, y, x + (int) gravity.getX() * 2,
                y + (int) gravity.getY() * -2, 5, 5, color);
    }

    /**
     * Draw an arrow line between two points.
     *
     * https://stackoverflow.com/a/27461352
     *
     * @param g           the graphics component.
     * @param x1          x-position of first point in Pixel.
     * @param y1          y-position of first point in Pixel.
     * @param x2          x-position of second point in Pixel.
     * @param y2          y-position of second point in Pixel.
     * @param arrowWidth  the width of the arrow in Pixel.
     * @param arrowHeight the height of the arrow in Pixel.
     */
    private void drawArrowLine(Graphics2D g, int x1, int y1, int x2, int y2,
            int arrowWidth, int arrowHeight, Color color)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - arrowWidth, xn = xm, ym = arrowHeight,
                yn = -arrowHeight, x;
        double sin = dy / D, cos = dx / D;
        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        int[] xPoints = { x2, (int) xm, (int) xn };
        int[] yPoints = { y2, (int) ym, (int) yn };
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
