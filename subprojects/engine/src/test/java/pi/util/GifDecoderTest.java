/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.image.BufferedImage;

import org.junit.jupiter.api.Test;

/**
 * @author Josef Friedrich
 *
 * @since 0.46.0
 */
class GifDecoderTest
{
    @Test
    void testDecodeImageData()
    {
        GifDecoder d = new GifDecoder();
        d.read(
            "demos/openpixelproject/sprites/humans/traveler/spr_m_traveler_run_anim.gif");

        assertEquals(6, d.getFrameCount());
        for (int i = 0; i < d.getFrameCount(); i++)
        {
            BufferedImage frame = d.getFrame(i);

            assertEquals(64, frame.getWidth());
            assertEquals(64, frame.getHeight());
            assertEquals(80, d.getDelay(i));
        }
    }
}
