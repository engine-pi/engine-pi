package pi.dsa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * <b>Quellenangaben</b> für Graphen, Bäume, Listen etc.
 *
 * <p>
 * So können Methoden, die z.B. einen Graphen aus einem Schulbuch nachzeichnen,
 * annotiert werden und dadurch mit einer Quellenangabe versehen werden.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.37.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Source
{

    /**
     * Der Titel der Quelle, z.B. <em>Informatik Oberstufe 1</em>
     */
    String title() default "";

    /**
     * Der Untertitel der Quelle, z.B. <em>Datenstrukturen und
     * Softwareentwicklung</em>.
     */
    String subtitle() default "";

    /**
     * Der Verlag
     */
    String publisher() default "";

    /**
     * Das Jahr, in dem die Quelle veröffentlich wurde, z.B. <em>2009</em>.
     */
    int releaseYear() default 0;

    /**
     * Die Seite auf der die Quelle zu finden ist.
     */
    int page() default 0;

    /**
     * Der Dateiname, falls sich z.B. um eine Bilddatei auf der CD handelt.
     */
    String filename() default "";

}
