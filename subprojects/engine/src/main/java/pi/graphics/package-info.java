/**
 * Klassen, um eine <b>Bildfläche</b> ({@link java.awt.Canvas Canvas}) mit der
 * {@link java.awt.Graphics2D Graphics2D}-API zu bemalen.
 *
 * <p>
 * Die {@link pi.actor.Actor#render(java.awt.Graphics2D, double)}-Methode der
 * {@link pi.actor.Actor Actor}-Klasse bietet beispielsweise Zugriff auf das
 * {@link java.awt.Graphics2D Graphics2D}-Objekt:
 * </p>
 *
 * <pre>{@code
 * public class MyActor extends Actor
 *
 *     public void render(Graphics2D g, double pixelPerMeter)
 *     {
 *         // g <- Graphics2D
 *     }
 * }</pre>
 *
 * <h2>Bilder zeichnen</h2>
 *
 * <p>
 * Wir nutzen den {@link pi.resources.ImageContainer Bildspeicher} um ein Bild
 * zu laden:
 * </p>
 *
 * <pre>{@code
 * import static pi.Controller.images;
 *
 * BufferedImage image = images.get("froggy/Frog.png");
 * }</pre>
 *
 * <p>
 * Um ein Bild an eine Koordinate (x, y) zu setzen, kann die Methode
 * {@link java.awt.Graphics2D#drawImage(java.awt.Image, int, int, java.awt.image.ImageObserver)
 * g.drawImage(image, x, y, null)} verwendet werden. Die Koordinate bezieht sich
 * auf das linke obere Eck des Bilds. Folgender Code-Ausschnitt setzt das Bild
 * an die Position {@code (50|50)}, d. h. das Bild ist {@code 50px} vom linken
 * und {@code 50px} vom oberen Rand der Zeichenfläche plaziert:
 * </p>
 *
 * <pre>{@code
 * g.drawImage(image, 50, 50, null);
 * }</pre>
 *
 * <p>
 * Wir skalieren ein Bild mit der Methode
 * {@link java.awt.Graphics2D#drawImage(java.awt.Image, int, int, int, int, java.awt.image.ImageObserver)
 * g.drawImage(image, x, y, width, height, null);}. Durch folgenden Code wird
 * das Bild auf {@code 100x100} skaliert:
 * </p>
 *
 * <pre>{@code
 * g.drawImage(image, 50, 50, 100, 100, null);
 * }</pre>
 *
 * <p>
 * Wir drehen das Bild 45 Grad im Uhrzeigersinn:
 * </p>
 *
 * <pre>{@code
 * AffineTransform transform = new AffineTransform();
 * transform.translate(100, 100);
 * transform.rotate(Math.toRadians(45));
 * g.drawImage(image, transform, null);
 * }</pre>
 *
 * @see <a href="https://www.medien.ifi.lmu.de/lehre/ss07/mt/mtB2b.pdf">B2.
 *     2D-Computergrafik mit Java Medieninformatik - SS2007 - Prof. Butz</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/2d/TOC.html">The Java™
 *     Tutorials</a>
 * @see <a href="https://www.jfree.org/jfreechart/index.html">JFreeChart</a>:
 *     Diagramme zeichnen mit Graphics2D
 */
package pi.graphics;
