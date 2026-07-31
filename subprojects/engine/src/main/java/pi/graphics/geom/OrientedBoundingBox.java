package pi.graphics.geom;

/**
 * @param x Die {@code x}-Koordinate der <i>linken unteren Ecke</i> des
 *     Rechtecks.
 * @param y Die {@code y}-Koordinate der <i>linken unteren Ecke</i> des
 *     Rechtecks.
 * @param width Die <b>Breite</b> des Rechtecks.
 * @param height Die <b>Höhe</b> des Rechtecks.
 * @param rotation Der <b>Winkel</b>, um den das Rechteck <b>rotiert</b> ist.
 *     Positive Werte drehen gegen den Uhrzeigersinn.
 *
 * @since 0.53.0
 */
public record OrientedBoundingBox(double x, double y, double width,
        double height, double rotation)
{

}
