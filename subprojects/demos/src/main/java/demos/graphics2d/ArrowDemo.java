package demos.graphics2d;

import java.awt.Graphics2D;

import pi.Vector;
import pi.util.Graphics2DUtil;

/**
 * Demonstiert wie man eine Pfeilspitze einzeichnet als
 * Gleichschenkliges<a href=
 * "https://de.wikipedia.org/wiki/Gleichschenkliges_Dreieck">Gleichschenkliges
 * Dreieck</a> unter Verwendung der Vektor-Klasse
 *
 * Die beiden gleich langen Seiten heißen Schenkel, die dritte Seite heißt
 * Basis. Der der Basis gegenüberliegende Eckpunkt heißt Spitze. Die an der
 * Basis anliegenden Winkel heißen Basiswinkel.
 *
 * Schenkel = sides
 *
 * Basis = base
 *
 * gamma γ = 36 ∘ {\displaystyle \gamma =36^{\circ }} oder den Winkel γ = 108 ∘
 * {\displaystyle \gamma =108^{\circ }}
 */
public class ArrowDemo extends Graphics2DComponent
{

    @Override
    public void render(Graphics2D g)
    {
        Vector from = new Vector(10, 30);
        Vector to = new Vector(300, 600);
        Graphics2DUtil.drawLine(g, from, to);
        Graphics2DUtil.drawArrow(g, from, to, 45, 100);
    }

    public static void main(String[] args)
    {
        new ArrowDemo().show();
    }
}
