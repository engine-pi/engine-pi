/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/EngineAlpha.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2014 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.pirckheimer_gymnasium.engine_pi;

import java.awt.Color;
import java.io.IOException;
import java.net.JarURLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import de.pirckheimer_gymnasium.engine_pi.actor.Actor;
import de.pirckheimer_gymnasium.engine_pi.actor.Circle;
import de.pirckheimer_gymnasium.engine_pi.actor.Logo;
import de.pirckheimer_gymnasium.engine_pi.actor.Polygon;
import de.pirckheimer_gymnasium.engine_pi.actor.Rectangle;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.resources.ResourceLoader;

/**
 * Zeigt eine Animation, wenn die main-Methode ausgeführt wird.
 *
 * <p>
 * Diese Klasse definiert Versions-Konstanten und sorgt für eine About-Box beim
 * Ausführen der .jar-Datei.
 *
 * @author Niklas Keller
 */
@Internal
public final class MainAnimation
{
    /**
     * Der Versionscode des aktuellen Release.<br>
     * Rechnung:<br>
     * <code>
     * 10000 * major + 100 * minor + 1 * bugfix
     * </code>
     */
    public static final int VERSION_CODE = 40000;

    /**
     * Format: v(major).(minor).(bugfix) Beispiel: v3.1.2
     */
    public static final String VERSION_STRING = "v4.0.0-dev";

    /**
     * Gibt an, ob dieser Release in .jar - Form vorliegt. Ist das der Fall, ist
     * dieser Wert <code>true</code>, sonst ist er <code>false</code>.
     */
    public static final boolean IS_JAR;

    /**
     * Zeitpunkt, an dem diese Jar-Datei erzeugt wurde, falls als Jar-Datei
     * ausgeführt, sonst die aktuelle Zeit in Sekunden seit dem 01.01.1970 (Unix
     * Timestamp)
     */
    public static final long BUILD_TIME;
    /*
     * Statischer Konstruktor. Ermittelt <code>IS_JAR</code> und
     * <code>BUILD_TIME</code>.
     */
    static
    {
        IS_JAR = isJar();
        BUILD_TIME = IS_JAR ? getBuildTime() / 1000
                : System.currentTimeMillis() / 1000;
    }

    /**
     * Gibt an, ob das Programm gerade aus einer Jar heraus gestartet wurde.
     *
     * @return <code>true</code>, falls ja, sonst <code>false</code>.
     */
    @API
    public static boolean isJar()
    {
        String className = MainAnimation.class.getName().replace('.', '/');
        String classJar = MainAnimation.class
                .getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }

    /**
     * Gibt den Namen der Jar-Datei zurück, die gerade ausgeführt wird.
     *
     * @return Dateiname der Jar-Datei oder <code>null</code>, falls das
     *         Programm nicht über eine Jar-Datei ausgeführt wird.
     */
    @API
    public static String getJarName()
    {
        String className = MainAnimation.class.getName().replace('.', '/');
        String classJar = MainAnimation.class
                .getResource("/" + className + ".class").toString();
        if (classJar.startsWith("jar:"))
        {
            String[] values = classJar.split("/");
            for (String value : values)
            {
                if (value.contains("!"))
                {
                    try
                    {
                        return java.net.URLDecoder.decode(
                                value.substring(0, value.length() - 1),
                                StandardCharsets.UTF_8);
                    }
                    catch (Exception e)
                    {
                        return null;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gibt an, wann die Jar-Datei erzeugt wurde.
     *
     * @return Erzeugungsdatum der Jar-Datei in Sekunden seit dem 01.01.1970
     *         (Unix Timestamp) oder den aktuellen Timestamp, falls nicht von
     *         einer Jar-Datei ausgeführt.
     */
    @API
    public static long getBuildTime()
    {
        try
        {
            String uri = MainAnimation.class.getName().replace('.', '/')
                    + ".class";
            JarURLConnection j = (JarURLConnection) ClassLoader
                    .getSystemResource(uri).openConnection();
            long time = j.getJarFile().getEntry("META-INF/MANIFEST.MF")
                    .getTime();
            return time > 0 ? time : System.currentTimeMillis() / 1000;
        }
        catch (Exception e)
        {
            return System.currentTimeMillis() / 1000;
        }
    }

    private static Properties getProjectProperties() throws IOException
    {
        // https://stackoverflow.com/questions/26551439/getting-maven-project-version-and-artifact-id-from-pom-while-running-in-eclipse/26573884#26573884
        final Properties properties = new Properties();
        properties.load(ResourceLoader.loadAsStream("project.properties"));
        return properties;
    }

    private static Properties getGitProperties() throws IOException
    {
        final Properties properties = new Properties();
        properties.load(ResourceLoader.loadAsStream("git.properties"));
        return properties;
    }

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) throws IOException
    {
        final Properties propertiesGit = getGitProperties();
        Game.start(800, 600, new Scene()
        {
            private final List<Actor> items = new ArrayList<>();
            {
                // https://gitlab.gnome.org/GNOME/gsettings-desktop-schemas/-/blob/master/schemas/org.gnome.desktop.interface.gschema.xml.in#L165
                // Font: https://cantarell.gnome.org/
                new Logo(this, new Vector(-3, -6), 2);
                Text enginePiText = new Text(
                        "E   n   g   i   n   e         P   i", 2,
                        "fonts/Cantarell-Bold.ttf", 0);
                enginePiText.makeStatic();
                enginePiText.setColor("white");
                enginePiText.setCenter(0, -7);
                add(enginePiText);
                setGravityOfEarth();
                Rectangle ground = new Rectangle(20, .2);
                ground.setColor("white");
                ground.setCenter(0, -6);
                ground.setElasticity(.95);
                ground.setFriction(.2);
                ground.makeStatic();
                add(ground);
                for (int i = 0; i < 3; i++)
                {
                    Rectangle a = new Rectangle(1, 1);
                    a.setPosition(-5, 10);
                    a.setElasticity(.95);
                    a.setFriction(1);
                    a.makeDynamic();
                    a.setRotation(30);
                    spawnItem(a);
                    Circle b = new Circle(1);
                    b.setPosition(5, 10);
                    b.setElasticity(.95);
                    b.setFriction(1);
                    b.makeDynamic();
                    b.applyImpulse(new Vector(Random.range(-100, 100), 0));
                    spawnItem(b);
                    Polygon c = new Polygon(new Vector(0, 0), new Vector(1, 0),
                            new Vector(.5, -1));
                    c.setColor("yellow");
                    c.setElasticity(.9);
                    c.setFriction(1);
                    c.makeDynamic();
                    c.setRotation(-20);
                    spawnItem(c);
                }
                Date date = new Date(BUILD_TIME * 1000);
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "dd.MM.yyyy HH:mm:ss z");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                Text text = new Text(
                        "Build #" + propertiesGit.getProperty("git.commit.id.abbrev") + "   " + sdf.format(date), .5,
                        "fonts/Cantarell-Regular.ttf");
                text.setPosition(-10, 9);
                text.setColor(Color.WHITE);
                text.makeStatic();
                add(text);
                addFrameUpdateListener(time -> {
                    for (Actor item : items)
                    {
                        if (item.getCenter().getY() < -10)
                        {
                            spawnItem(item);
                        }
                    }
                });
            }

            private void spawnItem(Actor item)
            {
                if (!item.isMounted())
                {
                    delay(Random.range(5), () -> {
                        items.add(item);
                        add(item);
                    });
                }
                item.resetMovement();
                item.setCenter(Random.range(-7, 7), Random.range(5, 8));
            }
        });
        Game.setTitle("Engine Pi " + VERSION_STRING);
    }
}
