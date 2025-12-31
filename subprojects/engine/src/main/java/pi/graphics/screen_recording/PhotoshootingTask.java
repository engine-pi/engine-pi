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

import java.text.SimpleDateFormat;
import java.util.Date;

import pi.util.FileUtil;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
abstract class PhotoshootingTask
{
    protected Date started = new Date();

    protected String baseDir;

    PhotoshootingTask()
    {
        started = new Date();
    }

    /**
     * Formatiert die Startzeit des Fotoshooting-Tasks zu einer Zeichenkette,
     * wie zum Beispiel {@code "2025-12-31_09-40-08_192"}.
     *
     * @return Ein formatierter Zeitstempel im Format
     *     {@code "yyyy-MM-dd_HH-mm-ss_S"} basierend auf dem Startzeitpunkt der
     *     Aufgabe.
     */
    protected String getFormattedTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss_S");
        return sdf.format(started);
    }

    abstract String getBaseDir();

    String baseDir()
    {
        if (baseDir == null)
        {
            baseDir = getBaseDir();
        }
        FileUtil.createDir(baseDir);
        return baseDir;
    }

    abstract String getFilename();

    abstract boolean hasToTakeScreenshot();

    void writeImage(ScreenshotImage image)
    {
        image.write(baseDir() + "/" + getFilename());
    }
}
