package de.pirckheimer_gymnasium.engine_pi.util;

import static de.pirckheimer_gymnasium.engine_pi.Resources.COLORS;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

/**
 * Eine Sammlung von statischen Hilfsmethoden um auf dem Graphics2D-Objekt zu
 * zeichnen.
 */
public class Graphics2DUtil
{
    private static final int DEBUG_INFO_HEIGHT = 20;

    private static final int DEBUG_INFO_LEFT = 10;

    private static final int DEBUG_INFO_TEXT_OFFSET = 16;

    /**
     * Die Standard-Schriftgröße ist 12.
     */
    private static final int FONT_SIZE = 12;

    /**
     * Die Standard-Schriftfarbe ist weiß.
     */
    private static final String FONT_COLOR = "white";

    private Graphics2DUtil()
    {
        throw new UnsupportedOperationException();
    }

    public static Font getFont(int size)
    {
        return new Font("Monospaced", Font.PLAIN, size);
    }

    public static Font getFont()
    {
        return getFont(FONT_SIZE);
    }

    /**
     * Setzt die Schriftart durch Angabe der <b>Schriftart</b>.
     *
     * @param g    Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param font Die Schriftart.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *         wurde.
     */
    public static Font setFont(Graphics2D g, Font font)
    {
        g.setColor(COLORS.getSafe(FONT_COLOR));
        g.setFont(font);
        return font;
    }

    /**
     * Setzt die Schriftart durch Angabe der <b>Schriftgröße</b> und
     * <b>Schriftfarbe</b>.
     *
     * @param g     Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *              soll.
     * @param size  Die Größe der Schrift in pt (Points).
     * @param color Ein Farbname wie er im
     *              {@link de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer
     *              ColorContainer} hinterlegt ist.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *         wurde.
     */
    public static Font setFont(Graphics2D g, int size, String color)
    {
        Font font = getFont(size);
        g.setColor(COLORS.get(color));
        g.setFont(font);
        return font;
    }

    /**
     * Setzt die Schriftart auf {@link #FONT_COLOR} durch Angabe der
     * <b>Schriftgröße</b>.
     *
     * @param g    Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param size Die Größe der Schrift in pt (Points).
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *         wurde.
     */
    public static Font setFont(Graphics2D g, int size)
    {
        return setFont(g, size, FONT_COLOR);
    }

    /**
     * Setzt die Schriftart auf {@link #FONT_SIZE} durch Angabe der
     * <b>Schriftfarbe</b>.
     *
     * @param g     Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *              soll.
     * @param color Ein Farbname wie er im
     *              {@link de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer
     *              ColorContainer} hinterlegt ist.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *         wurde.
     */
    public static Font setFont(Graphics2D g, String color)
    {
        return setFont(g, FONT_SIZE, color);
    }

    /**
     * Setzt die Schriftart auf {@link #FONT_SIZE} und {@link #FONT_COLOR}.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *         wurde.
     */
    public static Font setFont(Graphics2D g)
    {
        return setFont(g, FONT_SIZE, FONT_COLOR);
    }

    /**
     * Zeichnet einen <b>Text</b> mit der Schriftgröße {@link #FONT_SIZE} und
     * der Schriftfarbe {@link #FONT_COLOR} an eine bestimmte <b>Position</b>.
     *
     * @param g    Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param text Der Text, der eingezeichnet werden soll.
     * @param x    Die x-Koordinate in Pixel der Position, an die der Text
     *             gesetzt werden soll.
     * @param y    Die y-Koordinate in Pixel der Position an die der Text
     *             gesetzt werden soll.
     */
    public static void drawText(Graphics2D g, String text, int x, int y)
    {
        setFont(g);
        g.drawString(text, x, y);
    }

    /**
     * Zeichnet einen <b>Text</b> mit der Schriftfarbe {@link #FONT_COLOR} durch
     * Angabe der <b>Schriftgröße</b> und einer <b>Position</b>.
     *
     * @param g    Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param text Der Text, der eingezeichnet werden soll.
     * @param size Die Größe der Schrift in pt (Points).
     * @param x    Die x-Koordinate in Pixel der Position, an die der Text
     *             gesetzt werden soll.
     * @param y    Die y-Koordinate in Pixel der Position an die der Text
     *             gesetzt werden soll.
     */
    public static void drawText(Graphics2D g, String text, int size, int x,
            int y)
    {
        setFont(g, size);
        g.drawString(text, x, y);
    }

    /**
     * Zeichnet eine <b>Textbox</b>, die sich automatische an die Länge des
     * Texts anpasst. Die Schriftfarbe ist {@link #FONT_COLOR}.
     *
     * @param g          Das {@link Graphics2D}-Objekt, in das gezeichnet werden
     *                   soll.
     * @param text       Der Text, der in die Textbox gesetzt werden soll.
     * @param y          Die y-Koordinate in Pixel.
     * @param background Ein Farbname wie er im
     *                   {@link de.pirckheimer_gymnasium.engine_pi.resources.ColorContainer
     *                   ColorContainer} hinterlegt ist.
     */
    public static void drawTextBox(Graphics2D g, String text, int y,
            Color background)
    {
        Font font = getFont();
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
        setFont(g, font);
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

    /**
     * Die <b>Kantenglättung</b> (Antialiasing) ein- oder ausschalten.
     *
     * @param on Ist der Wert wahr, so wird die Kantenglättung eingeschaltet,
     *           sonst wird sie ausgeschaltet.
     */
    public static void setAntiAliasing(Graphics2D g, boolean on)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                on ? RenderingHints.VALUE_ANTIALIAS_ON
                        : RenderingHints.VALUE_ANTIALIAS_OFF);
    }

    public static void main(String[] args)
    {
        Game.setDebug(true);
        Game.start(new Scene()
        {
            {
                // Damit wir den Pfeil sehen
                setGravityOfEarth();
                addCircle(7).setCenter(0, 0).makeStatic();
            }
        });
    }
}
