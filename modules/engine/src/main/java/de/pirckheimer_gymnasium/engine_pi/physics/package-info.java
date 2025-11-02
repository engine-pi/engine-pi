/**
 * Klassen, die innerhalb der Engine als Middleware zwischen der externen
 * <b>Physics-Engine</b>
 * <a href="https://github.com/jbox2d/jbox2d">JBox2D-Projekt</a> von
 * <a href="https://github.com/dmurph">Daniel Murhpy</a> und der Engine-API
 * fungieren.
 *
 * <p>
 * Die Längeneinheit der JBox2D ist auch in Meter, jedoch zeigen positive
 * y-Werte nach unten und nicht nach oben wie in der Mathematik oder in der
 * Engine Pi.
 * </p>
 *
 * <p>
 * JBox2D ist eine Portierung des ursprünglichen C++-Codes der
 * <a href="https://box2d.org">Box2D</a> von Erin Catto.
 * <a href="https://github.com/jbox2d/jbox2d">JBox2D</a> wird nicht mehr aktiv
 * weiterentwickelt. Besonders alt ist die Version im <a href=
 * "https://central.sonatype.com/artifact/org.jbox2d/jbox2d-library">Maven
 * Central Repository</a> (2013-04-10).
 * </p>
 *
 * <p>
 * Mittlerweise ist die box2d Engine in Version 3 erschienen. Sie wurde nach C
 * portiert. jbox2d basiert jedoch auf die Version 2 der box2d.
 * </p>
 *
 * <p>
 * In der Engine Alpha wurde jbox2d über ein lokales JAR-Repository eingebunden.
 * Wir verwenden einen eigenen
 * <a href="https://github.com/engine-pi/jbox2d">Fork</a>, der über das <a href=
 * "https://central.sonatype.com/artifact/de.pirckheimer-gymnasium/jbox2d">Maven
 * Central Repository</a> abrufbar ist.
 * </p>
 *
 * @author Josef Friedrich
 * @author Michael Andonie
 *
 * @since 15.02.15
 */
package de.pirckheimer_gymnasium.engine_pi.physics;
