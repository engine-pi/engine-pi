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
package pi.debug;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

import pi.Configuration;
import pi.Game;
import pi.util.FileUtil;
import pi.util.Graphics2DUtil;
import pi.util.ImageUtil;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class ScreenRecorder
{
    String baseDir;

    private int frameCounter = 0;

    public ScreenRecorder()
    {
        baseDir = FileUtil.getHome() + "/engine-pi";
        FileUtil.createDir(baseDir);
    }

    /**
     * Speichert ein Bildschirmfoto des aktuellen Spielfensters in den Ordner
     * {@code ~/engine-pi}.
     */
    public void take(Consumer<Graphics2D> consumer)
    {
        BufferedImage screenshot = new BufferedImage(
                Configuration.windowWidthPx, Configuration.windowHeightPx,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) screenshot.getGraphics();
        Graphics2DUtil.setAntiAliasing(g, true);
        consumer.accept(g);
        ImageUtil.write(screenshot,
                baseDir + "/screenshot_" + System.nanoTime() + ".png");
    }

    /**
     * Soll bei diesem Einzelbild ein Bildschirmfoto für die Bildschirmaufnahme
     * (screen recording) gemacht werden?
     *
     * @see DebugConfiguration#screenRecording
     * @see DebugConfiguration#screenRecordingNFrames
     */
    public boolean isFrameForRecording()
    {
        frameCounter++;
        if (DebugConfiguration.screenRecording
                && frameCounter >= DebugConfiguration.screenRecordingNFrames)
        {
            frameCounter = 0;
            return true;
        }
        return false;
    }

    public static void main(String[] args)
    {
        Game.start();
    }
}
