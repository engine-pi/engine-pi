/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/graphics/AnimationFrame.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.animation;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Beschreibt ein Einzelbild einer
 * {@link de.pirckheimer_gymnasium.engine_pi.actor.Animation}.
 *
 * @author Niklas Keller
 *
 * @hidden
 */
@Internal
public final class AnimationFrame
{
    /**
     * Ein Bild, das sich bereits im Speicher befindet.
     */
    private final BufferedImage image;

    /**
     * Die Dauer in Sekunden, die dieses Einzelbild aktiv bleibt.
     */
    private double duration;

    /**
     * Erstellt ein Einzelbild.
     *
     * @param image Ein Bild, das sich bereits im Speicher befindet.
     * @param duration Die Dauer in Sekunden, die dieses Einzelbild aktiv
     *     bleibt.
     *
     * @hidden
     */
    @Internal
    public AnimationFrame(BufferedImage image, double duration)
    {
        this.image = image;
        this.duration = duration;
    }

    /**
     * @hidden
     */
    @Internal
    public void setDuration(double duration)
    {
        this.duration = duration;
    }

    /**
     * @hidden
     */
    @Internal
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * @hidden
     */
    @Internal
    public double getDuration()
    {
        return duration;
    }

    /**
     * Rendert das Einzelbild (an der entsprechenden Position des Graphics
     * Objekts)
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @hidden
     */
    @Internal
    public void render(Graphics2D g, double width, double height,
            boolean flipHorizontal, boolean flipVertical)
    {
        AffineTransform pre = g.getTransform();
        g.scale(width / this.image.getWidth(), height / this.image.getHeight());
        g.drawImage(image, flipHorizontal ? image.getWidth() : 0,
                -image.getHeight() + (flipVertical ? image.getHeight() : 0),
                (flipHorizontal ? -1 : 1) * image.getWidth(),
                (flipVertical ? -1 : 1) * image.getHeight(), null);
        g.setTransform(pre);
    }
}
