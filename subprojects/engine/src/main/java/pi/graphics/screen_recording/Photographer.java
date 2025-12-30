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

import pi.Game;
import pi.debug.DebugConfiguration;
import pi.util.FileUtil;

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
public class Photographer
{
    String baseDir;

    private int frameCounter = 0;

    private String baseDir()
    {
        if (baseDir == null)
        {
            baseDir = FileUtil.getHome() + "/engine-pi";
            FileUtil.createDir(baseDir);
        }
        return baseDir;
    }

    /**
     * Soll bei diesem Einzelbild ein Bildschirmfoto gemacht werden?
     *
     * @see DebugConfiguration#screenRecording
     * @see DebugConfiguration#screenRecordingNFrames
     */
    public boolean hasToTakeScreenshot()
    {
        // Soll ein einzelnes Bild gemacht werden
        if (DebugConfiguration.takeSingleScreenshot)
        {
            DebugConfiguration.takeSingleScreenshot = false;
            return true;
        }

        // Sollen mehrere Bilder hintereinander gemachten werden?
        frameCounter++;
        if (DebugConfiguration.screenRecording
                && frameCounter >= DebugConfiguration.screenRecordingNFrames)
        {
            frameCounter = 0;
            return true;
        }
        return false;
    }

    /**
     * Speichert ein Bildschirmfoto des aktuellen Spielfensters in den Ordner
     * {@code ~/engine-pi}.
     */
    public ScreenshotImage createImage(int width, int height)
    {
        return new ScreenshotImage(width, height);
    }

    public void writeImage(ScreenshotImage image)
    {
        image.write(baseDir() + "/screenshot_" + System.nanoTime() + ".png");
    }

    public static void main(String[] args)
    {
        Game.start();
    }

}
