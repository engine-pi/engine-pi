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

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import pi.annotations.Getter;
import pi.util.Graphics2DUtil;
import pi.util.ImageUtil;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class ScreenshotImage
{
    private BufferedImage image;

    private Graphics2D g;

    public ScreenshotImage(int width, int height)
    {
        image = new BufferedImage(config.graphics.windowWidth(),
                config.graphics.windowHeight(), BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        Graphics2DUtil.antiAliasing(g, true);
    }

    @Getter
    public Graphics2D g()
    {
        return g;
    }

    public void write(String filePath)
    {
        ImageUtil.write(image, filePath);
    }
}
