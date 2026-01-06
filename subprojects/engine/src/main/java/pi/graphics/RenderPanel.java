/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/graphics/RenderPanel.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2017 Michael Andonie and contributors.
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
package pi.graphics;

import java.awt.Canvas;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import static pi.Controller.colors;

import pi.graphics.screen_recording.Photographer;

/**
 * Ein Render-Panel, das als Zeichenfläche für die Engine dient.
 *
 * <p>
 * Diese Klasse erweitert {@link Canvas} und implementiert {@link RenderTarget},
 * um eine effiziente Darstellung von Grafiken mithilfe von Buffer-Strategien zu
 * ermöglichen. Das Panel verwendet Double-Buffering, um Flackern beim Rendern
 * zu vermeiden.
 * </p>
 *
 * <p>
 * Das Panel muss nach dem Sichtbarwerden des Fensters initialisiert werden,
 * indem {@link #allocateBuffers()} aufgerufen wird, um die Buffer-Strategie zu
 * erstellen.
 * </p>
 *
 * @see RenderTarget
 * @see Canvas
 * @see BufferStrategy
 *
 * @author Michael Andonie
 * @author Niklas Keller
 */
public final class RenderPanel extends Canvas implements RenderTarget
{
    private Photographer photographer;

    /**
     * Konstruktor für Objekte der Klasse {@link RenderPanel}
     *
     * @param width Die Größe des Einflussbereichs des Panels in Richtung
     *     {@code x} in Pixel.
     * @param height Die Größe des Einflussbereichs des Panels in Richtung
     *     {@code y} in Pixel.
     */
    public RenderPanel(int width, int height)
    {
        setSize(width, height);
        setPreferredSize(getSize());
        setBackground(colors.getSafe("black"));
        photographer = Photographer.get();
    }

    /**
     * Muss aufgerufen werden, nachdem das Fenster sichtbar ist, um die
     * {@link BufferStrategy} zu erzeugen.
     */
    public void allocateBuffers()
    {
        createBufferStrategy(2);
    }

    @Override
    public void render(RenderSource source)
    {
        BufferStrategy bufferStrategy = getBufferStrategy();
        do
        {
            do
            {
                source.render((Graphics2D) bufferStrategy.getDrawGraphics(),
                        getWidth(), getHeight());

                // Kann von jedem Einzelbild ein Bildschirmfoto machen.
                if (photographer.hasToTakeScreenshot())
                {
                    var image = photographer.createImage(getWidth(),
                            getHeight());
                    source.render(image.g(), getWidth(), getHeight());
                    photographer.writeImage(image);
                }
            }
            while (bufferStrategy.contentsRestored()
                    && !Thread.currentThread().isInterrupted());
            if (!bufferStrategy.contentsLost())
            {
                bufferStrategy.show();
                Toolkit.getDefaultToolkit().sync();
            }
        }
        while (bufferStrategy.contentsLost()
                && !Thread.currentThread().isInterrupted());
    }
}
