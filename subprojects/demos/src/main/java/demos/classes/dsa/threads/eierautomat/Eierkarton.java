package demos.classes.dsa.threads.eierautomat;

import pi.actor.Image;

/**
 * Eierkarton
 *
 * @author Johannes Neumeyer
 * @author Josef Friedrich
 *
 * @version 1.0
 */
class Eierkarton extends Image
{
    /**
     * Legt das Aussehen des Kartons fest
     */
    Eierkarton()
    {
        super("eierautomat/eierkarton.png");
        size(3, 1.4);
    }
}
