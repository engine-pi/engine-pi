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

import static pi.Controller.config;

import java.util.Timer;
import java.util.TimerTask;

import pi.Controller;
import pi.util.FileUtil;

/**
 * Erzeugt mehrere Bildschirmfotos für eine Video (screen recording).
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
class VideoTask extends PhotoshootingTask
{
    private int frameCounter = 0;

    /**
     * Wie oft ein Bildschirmfoto abgespeichert werden. Der Wert 2 macht
     * beispielsweise jedes zweite Einzelbild ein Bildschirmfoto.
     *
     *
     */
    int nFrames;

    private String oldTitle;

    VideoTask()
    {
        this(config.graphics.screenRecordingNFrames());
    }

    VideoTask(int nFrames)
    {
        super();
        this.nFrames = nFrames;
    }

    protected String getBaseDir()
    {
        return FileUtil.getTmpDir() + "/engine-pi-screen-recording_"
                + getFormattedTime();
    }

    String getFilename()
    {
        return "screenshot_" + System.nanoTime() + ".png";
    }

    @Override
    boolean hasToTakeScreenshot()
    {
        frameCounter++;
        if (frameCounter >= nFrames)
        {
            frameCounter = 0;
            return true;
        }
        return false;
    }

    @Override
    void writeImage(ScreenshotImage image)
    {
        super.writeImage(image);
        var window = Controller.window();
        if (window != null)
        {
            if (oldTitle == null)
            {
                oldTitle = window.getTitle();
            }
            window.setTitle("Bildschirmaufnahme: " + imageCount
                    + " Einzelbilder aufgenommen");
        }
    }

    void onStopRecording()
    {
        // Wir warten eine gewissen Zeit, bis das letzte Bild augenommen wurde.
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                // Den alten Titel wiederherstellen
                var window = Controller.window();
                if (window != null && oldTitle != null)
                {
                    window.setTitle(oldTitle);
                }
                // Die Bilder zu Videos konvertieren
                new ImagesToVideoConverter(baseDir(),
                        FileUtil.getVideosDir() + "/Engine-Pi_"
                                + getFormattedTime(),
                        (int) Math.round(
                                (double) config.graphics.framerate() / nFrames))
                        .generate();
                // Den temporären Ordner mit allen Bilder löschen.
                FileUtil.deleteDir(baseDir());
            }

        }, 100 /* 100 Millisekunden */);
    }
}
