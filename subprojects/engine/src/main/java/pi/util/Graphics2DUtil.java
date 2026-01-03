package pi.util;

import static pi.Resources.colors;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import pi.Circle;
import pi.Game;
import pi.Scene;
import pi.Vector;

/**
 * Eine Sammlung von statischen Hilfsmethoden um auf dem Graphics2D-Objekt zu
 * zeichnen.
 *
 * @author Josef Friedrich
 */
public class Graphics2DUtil
{
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
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param font Die Schriftart.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *     wurde.
     */
    public static Font setFont(Graphics2D g, Font font)
    {
        g.setColor(colors.getSafe(FONT_COLOR));
        g.setFont(font);
        return font;
    }

    /**
     * Setzt die Schriftart durch Angabe der <b>Schriftgröße</b> und
     * <b>Schriftfarbe</b>.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param size Die Größe der Schrift in pt (Points).
     * @param color Ein Farbname wie er im
     *     {@link pi.resources.color.ColorContainer ColorContainer} hinterlegt
     *     ist.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *     wurde.
     */
    public static Font setFont(Graphics2D g, int size, String color)
    {
        Font font = getFont(size);
        g.setColor(colors.get(color));
        g.setFont(font);
        return font;
    }

    /**
     * Setzt die Schriftart auf {@link #FONT_COLOR} durch Angabe der
     * <b>Schriftgröße</b>.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param size Die Größe der Schrift in pt (Points).
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *     wurde.
     */
    public static Font setFont(Graphics2D g, int size)
    {
        return setFont(g, size, FONT_COLOR);
    }

    /**
     * Setzt die Schriftart auf {@link #FONT_SIZE} durch Angabe der
     * <b>Schriftfarbe</b>.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param color Ein Farbname wie er im
     *     {@link pi.resources.color.ColorContainer ColorContainer} hinterlegt
     *     ist.
     *
     * @return Die Schriftart, die in das {@link Graphics2D}-Objekt gesetzt
     *     wurde.
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
     *     wurde.
     */
    public static Font setFont(Graphics2D g)
    {
        return setFont(g, FONT_SIZE, FONT_COLOR);
    }

    /**
     * Zeichnet einen <b>Text</b> mit der Schriftgröße {@link #FONT_SIZE} und
     * der Schriftfarbe {@link #FONT_COLOR} an eine bestimmte <b>Position</b>.
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param text Der Text, der eingezeichnet werden soll.
     * @param x Die x-Koordinate in Pixel der Position, an die der Text gesetzt
     *     werden soll.
     * @param y Die y-Koordinate in Pixel der Position an die der Text gesetzt
     *     werden soll.
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
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param text Der Text, der eingezeichnet werden soll.
     * @param size Die Größe der Schrift in pt (Points).
     * @param x Die x-Koordinate in Pixel der Position, an die der Text gesetzt
     *     werden soll.
     * @param y Die y-Koordinate in Pixel der Position an die der Text gesetzt
     *     werden soll.
     */
    public static void drawText(Graphics2D g, String text, int size, int x,
            int y)
    {
        setFont(g, size);
        g.drawString(text, x, y);
    }

    /**
     * Draw an arrow line between two points.
     *
     * https://stackoverflow.com/a/27461352
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param x1 x-position of first point in Pixel.
     * @param y1 y-position of first point in Pixel.
     * @param x2 x-position of second point in Pixel.
     * @param y2 y-position of second point in Pixel.
     * @param arrowWidth the width of the arrow in Pixel.
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

    public static void drawLine(Graphics2D g, Vector from, Vector to)
    {
        drawLine(g, from, to, 1);
    }

    public static void drawLine(Graphics2D g, Vector from, Vector to,
            double pixelPerMeter)
    {
        g.drawLine(from.x(pixelPerMeter), from.y(pixelPerMeter),
                to.x(pixelPerMeter), to.y(pixelPerMeter));
    }

    // Go to
    // file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/graphics2d/ArrowDemo.java

    /**
     * Zeichnet an ein Ende der Linie ein Dreieck als Pfeilspitze.
     *
     * <p>
     * Die Pfeilspitze wird als <a href=
     * "https://de.wikipedia.org/wiki/Gleichschenkliges_Dreieck">Gleichschenkliges
     * Dreieck</a> unter Verwendung der {@link Vector}-Klasse eingezeichnet.
     * </p>
     *
     * <p>
     * Die beiden gleich langen Seiten heißen Schenkel (legs), die dritte Seite
     * heißt Basis (base). Der der Basis gegenüberliegende Winkel heißt γ =
     * gamma (vertex angle). Die an der Basis anliegenden Winkel heißen
     * Basiswinkel.
     * </p>
     *
     * @param g Das {@link Graphics2D}-Objekt, in das gezeichnet werden soll.
     * @param from Der Ursprung der Line in Pixel.
     * @param to An diesen Punkt wird die Pfeilspitze platziert (in Pixel)
     * @param legsLength Die Länge der Schenkel (legs) des gleichseitigen
     *     Dreiecks in Pixel.
     * @param vertexAngle Der Winkel der Pfeilspitze in Grad (γ = gamma wird der
     *     Winkel genannt, der an der Spitze des gleichschenkligen Dreiecks
     *     liegt)
     */
    public static void drawArrow(Graphics2D g, Vector from, Vector to,
            int legsLength, double vertexAngle)
    {
        // C ist die Spitze des gleichschenkligen Dreiecks.
        Vector C = to;

        // Winkel der Line zur x-Achse
        double direction = from.subtract(to).angle();

        vertexAngle = (360 - vertexAngle) / 2;
        double directionA = direction - vertexAngle + 180;
        double directionB = direction + vertexAngle + 180;

        Vector A = to.add(Vector.ofAngle(directionA).multiply(legsLength));
        Vector B = to.add(Vector.ofAngle(directionB).multiply(legsLength));
        drawLine(g, C, A);
        drawLine(g, C, B);
    }

    /**
     * Die <b>Kantenglättung</b> (Antialiasing) ein- oder ausschalten.
     *
     * @param on Ist der Wert wahr, so wird die Kantenglättung eingeschaltet,
     *     sonst wird sie ausgeschaltet.
     */
    public static void setAntiAliasing(Graphics2D g, boolean on)
    {
        // Kantenglättung für Text einschalten.
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                on ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                        : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

        // Kantenglättung für geometrische Formen
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                on ? RenderingHints.VALUE_ANTIALIAS_ON
                        : RenderingHints.VALUE_ANTIALIAS_OFF);

        // Algorithmusauswahl eher auf Geschwindigkeit oder Qualität
        // ausgerichtet
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);
    }

    public static void main(String[] args)
    {
        Game.instantMode(false);
        Game.debug();
        Game.start(new Scene()
        {
            {
                // Damit wir den Pfeil sehen
                gravityOfEarth();
                add(new Circle(7).center(0, 0).makeStatic());
            }
        });
    }
}
