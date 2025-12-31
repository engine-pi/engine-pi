/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.graphics.screen_recording;

import java.util.Timer;
import java.util.TimerTask;

import pi.Game;

/**
 * Der Fotograph steuert, ob einzelne Bildschirmfotos gemacht werden sollen.
 *
 * <p>
 * {@link java.awt.Canvas} beinhaltet keine Pixel-Daten, die man abgreifen
 * könnte und eine Bilddatei erstellen könnte. Ein Einzelbild von dem eine
 * Bildschirmfoto gemacht werden soll, muss also zweimal gezeichnet werden. Die
 * Klasse wird in {@link pi.graphics.RenderPanel} als Attribut eingefügt.
 * </p>
 *
 * <p>
 * Diese drei Methoden sind die Methode
 * {@link pi.graphics.RenderPanel#render(pi.graphics.RenderSource)} eingebettet:
 * </p>
 *
 * <ul>
 * <li>{@link #hasToTakeScreenshot()}</li>
 * <li>{@link #createImage(int, int)}</li>
 * <li>{@link #writeImage(ScreenshotImage)}</li>
 * </ul>
 *
 * @repolink https://github.com/gurkenlabs/litiengine/blob/17d67e93a48c261414fd4bfe9cfd252a6c7181ae/litiengine/src/main/java/de/gurkenlabs/litiengine/graphics/RenderComponent.java#L238-L249
 *
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 *
 * @see pi.graphics.RenderPanel
 */
public final class Photographer
{
    private static Photographer photographer;

    String baseDir;

    private ImageTask imageTask;

    private VideoTask videoTask;

    private Photographer()
    {

    }

    /**
     * @since 0.42.0
     */
    public static Photographer getPhotographer()
    {
        if (photographer == null)
        {
            photographer = new Photographer();
        }
        return photographer;
    }

    /**
     * Erstellt ein <b>Bildschirmfoto</b> (Screenshot) des aktuellen
     * Spielfensters.
     *
     * <p>
     * Das Bild wird als PNG-Datei in das Bilder-Verzeichnis abgespeichert,
     * beispielsweise
     * {@code "~/Pictures/Engine-Pi_2025-12-31_09-40-08_192.png"}.
     * </p>
     *
     * @since 0.42.0
     */
    public void takeScreenshot()
    {
        if (imageTask == null)
        {
            imageTask = new ImageTask();
        }
    }

    /**
     * @param duration Die <b>Dauer</b> der Videoaufnahme in Sekunden.
     *
     * @since 0.42.0
     */
    public void startScreenRecording(double duration)
    {
        startScreenRecording();
        scheduleStopScreenRecording(duration);
    }

    /**
     * @since 0.42.0
     */
    public void startScreenRecording()
    {
        if (videoTask == null)
        {
            videoTask = new VideoTask();
        }
    }

    /**
     * @since 0.42.0
     */
    public VideoTask stopScreenRecording()
    {
        if (videoTask != null)
        {
            videoTask.onStopRecording();
        }
        var old = videoTask;
        videoTask = null;
        return old;
    }

    /**
     * @param duration Die <b>Dauer</b> der Videoaufnahme in Sekunden.
     *
     * @since 0.42.0
     */
    public void scheduleStopScreenRecording(double duration)
    {
        Timer timer = new Timer();

        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                stopScreenRecording();
            }
        }, (long) (1000 * duration));
    }

    /**
     * @since 0.42.0
     */
    public void toggleScreenRecording()
    {
        if (videoTask == null)
        {
            startScreenRecording();
        }
        else
        {
            stopScreenRecording();
        }
    }

    /**
     * @param duration Die <b>Dauer</b> der Videoaufnahme in Sekunden.
     *
     * @since 0.42.0
     */
    public void toggleScreenRecording(double duration)
    {
        if (videoTask == null)
        {
            startScreenRecording(duration);
        }
        else
        {
            stopScreenRecording();
        }
    }

    /**
     * Soll bei diesem Einzelbild ein Bildschirmfoto gemacht werden?
     *
     * @since 0.42.0
     */
    public boolean hasToTakeScreenshot()
    {
        return (imageTask != null && imageTask.hasToTakeScreenshot())
                || (videoTask != null && videoTask.hasToTakeScreenshot());
    }

    /**
     * @since 0.42.0
     */
    public ScreenshotImage createImage(int width, int height)
    {
        return new ScreenshotImage(width, height);
    }

    /**
     * @since 0.42.0
     */
    public void writeImage(ScreenshotImage image)
    {
        if (imageTask != null)
        {
            imageTask.writeImage(image);
            imageTask = null;
        }

        if (videoTask != null)
        {
            videoTask.writeImage(image);
        }
    }

    public static void main(String[] args)
    {
        Game.start();
    }

}
