package de.pirckheimer_gymnasium.engine_pi.debug;

import static de.pirckheimer_gymnasium.engine_pi.Resources.colors;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.pirckheimer_gymnasium.engine_pi.util.ColorUtil;

class DrawUtil
{
    private static final int DEBUG_INFO_HEIGHT = 20;

    private static final int DEBUG_INFO_LEFT = 10;

    private static final int DEBUG_INFO_TEXT_OFFSET = 16;

    private static final int DEBUG_TEXT_SIZE = 12;

    /**
     * Zeichnet eine Textbox, die sich automatische an die Länge des Texts
     * anpasst. Die Schriftfarbe ist standardmäßig Weiß.
     *
     * @param g          Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *                   soll.
     * @param text
     * @param y
     * @param background
     * @param border
     */
    public static void drawTextBox(Graphics2D g, String text, int y,
            Color background)
    {
        Font font = new Font("Monospaced", Font.PLAIN, DEBUG_TEXT_SIZE);
        FontMetrics fm = g.getFontMetrics(font);
        Rectangle2D bounds;
        bounds = fm.getStringBounds(text, g);
        // Hintergrund
        g.setColor(ColorUtil.changeAlpha(background, 150));
        g.fillRect(DEBUG_INFO_LEFT, y,
                (int) bounds.getWidth() + DEBUG_INFO_HEIGHT,
                (int) bounds.getHeight() + DEBUG_INFO_TEXT_OFFSET);
        // Rahmen
        g.setColor(ColorUtil.changeAlpha(background.darker().darker(), 150));
        g.drawRect(DEBUG_INFO_LEFT, y,
                (int) bounds.getWidth() + DEBUG_INFO_HEIGHT - 1,
                (int) bounds.getHeight() + DEBUG_INFO_TEXT_OFFSET - 1);
        // Text
        g.setColor(colors.get("white"));
        g.setFont(font);
        g.drawString(text, DEBUG_INFO_LEFT + 10,
                y + 8 + fm.getHeight() - fm.getDescent());
    }

    /**
     * Draw an arrow line between two points.
     *
     * https://stackoverflow.com/a/27461352
     *
     * @param g           Das {@link Graphics2D}-Objekt, in das gezeichnet
     *                    werden soll.
     * @param x1          x-position of first point in Pixel.
     * @param y1          y-position of first point in Pixel.
     * @param x2          x-position of second point in Pixel.
     * @param y2          y-position of second point in Pixel.
     * @param arrowWidth  the width of the arrow in Pixel.
     * @param arrowHeight the height of the arrow in Pixel.
     */
    public static void drawArrowLine(Graphics2D g, int x1, int y1, int x2,
            int y2, int arrowWidth, int arrowHeight, Color color)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - arrowWidth, xn = xm, ym = arrowHeight,
                yn = -arrowHeight, x;
        double sin = dy / D, cos = dx / D;
        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        int[] xPoints = { x2, (int) xm, (int) xn };
        int[] yPoints = { y2, (int) ym, (int) yn };
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
