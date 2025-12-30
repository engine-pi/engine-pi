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

import pi.util.FileUtil;

/**
 * Erzeugt mehrere Bildschirmfotos für eine Video (screen recording).
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class VideoTask extends PhotoshootingTask
{
    private int frameCounter = 0;

    int nFrames;

    public VideoTask()
    {
        this(2);
    }

    public VideoTask(int nFrames)
    {
        super();
        this.nFrames = nFrames;
    }

    protected String getBaseDir()
    {
        return FileUtil.getTmpDir() + "/engine-pi-screen-recording_"
                + getFormattedTime();
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
}
