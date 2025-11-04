package de.pirckheimer_gymnasium.blockly_robot.robot.gui.map;

import java.awt.Color;
import java.awt.Graphics2D;

import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;

public class Grid extends Actor
{
    int numX;

    int numY;

    /**
     * In Pixel pro Meter. Ist beispielsweise die Einheit „Pixel pro Meter“ auf
     * 60 Pixel und dieses Attribut auf 2 gesetzt, dann werden die vom Gitter
     * eingeschlossenen Rechtecke 120 auf 120 Pixel groß.
     */
    double size = 1;

    Color color = Color.GREEN;

    Color background;

    public Grid(int numX, int numY, double size)
    {
        super(() -> FixtureBuilder.rectangle(numX * size, numY * size));
        this.numX = numX;
        this.numY = numY;
        this.size = size;
    }

    public Grid(int numX, int numY)
    {
        this(numX, numY, 1);
    }

    public Actor setColor(Color color)
    {
        this.color = color;
        return this;
    }

    public void setBackground(Color color)
    {
        background = color;
    }

    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        int gridSize = (int) Math.round(pixelPerMeter * size);
        if (background != null)
        {
            g.setColor(background);
            g.fillRect(0, -Math.round(gridSize * numY),
                    Math.round(gridSize * numX), Math.round(gridSize * numY));
        }
        g.setColor(color);
        // Zeichnen der vertikalen Linien von links nach rechts.
        for (int gridX = 0; gridX <= numX; gridX++)
        {
            g.fillRect(Math.round(gridX * gridSize),
                    -Math.round(numY * gridSize), 1,
                    Math.round(numY * gridSize));
        }
        // Zeichnen der horizontalen Linien von unten nach oben.
        for (int gridY = 0; gridY <= numY; gridY++)
        {
            g.fillRect(0, -Math.round(gridY * gridSize),
                    Math.round(numX * gridSize), 1);
        }
    }
}
