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
package pi.debug;

import static pi.Resources.colors;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import pi.Circle;
import pi.Controller;
import pi.Random;
import pi.Rectangle;
import pi.Scene;
import pi.Text;
import pi.Vector;
import pi.actor.Actor;
import pi.actor.Logo;
import pi.actor.Polygon;
import pi.annotations.Internal;
import pi.event.FrameUpdateListener;
import pi.resources.color.ColorUtil;

/**
 * Zeigt eine <b>Animation</b>, wenn die <b>main</b>-Methode ausgeführt wird.
 *
 * <p>
 * In der Mitte steht das Engine-Pi-Logo. Von oben fallen kleine geometrische
 * Figuren (3 Dreiecken, 3 Rechtecken und 3 Kreisen) herab, die am Logo oder auf
 * der Linie, auf der das Logo steht, abprallen. Fällt eine Figur in die Tiefe,
 * wird sie wieder von oben herabgeworfen. Die Animation kommt zum Stillstand,
 * wenn alle 9 Figuren in eine Ruheposition gelangen.
 * </p>
 *
 * @author Niklas Keller
 * @author Josef Friedrich
 *
 * @hidden
 */
@Internal
public class MainAnimation extends Scene implements FrameUpdateListener
{
    /**
     * Eine etwas dunklere Farbe, damit sich die kleinen Figuren vom Logo
     * abgrenzen.
     */
    private final Color rectangleItemColor = darkenColor("red");

    /**
     * Eine etwas dunklere Farbe, damit sich die kleinen Figuren vom Logo
     * abgrenzen.
     */
    private final Color circleItemColor = darkenColor("blue");

    /**
     * Eine etwas dunklere Farbe, damit sich die kleinen Figuren vom Logo
     * abgrenzen.
     */
    private final Color triangleItemColor = darkenColor("yellow");

    /**
     * Eine Liste mit 3 Dreiecken, 3 Rechtecken und 3 Kreisen.
     */
    private final List<Actor> items = new ArrayList<>();

    private final Logo logo;

    private double yMovementCircleI = 0.01;

    /**
     * Erstellt die <b>Hauptanimation</b>.
     *
     * <p>
     * BuildInformationen werden dabei angezeigt.
     * </p>
     */
    public MainAnimation()
    {
        this(true);
    }

    /**
     * Erstellt die <b>Hauptanimation</b>.
     *
     * @param showBuildInformations Gibt an, ob Build-Informationen angezeigt
     *     werden sollen.
     *
     * @since 0.42.0
     */
    public MainAnimation(boolean showBuildInformations)
    {
        // https://gitlab.gnome.org/GNOME/gsettings-desktop-schemas/-/blob/master/schemas/org.gnome.desktop.interface.gschema.xml.in#L165
        // Font: https://cantarell.gnome.org/
        logo = new Logo(this, new Vector(-4, -5), 2.3);
        Text enginePiText = new Text("E   n   g   i   n   e         P   i", 2,
                "fonts/Cantarell-Bold.ttf", 0);
        enginePiText.makeStatic();
        enginePiText.color("white");
        enginePiText.center(0, -7);
        add(enginePiText);
        gravityOfEarth();
        createGround();
        for (int i = 0; i < 3; i++)
        {
            createRectangleItem();
            createCircleItem();
            createTriangleItem();
        }

        if (showBuildInformations)
        {
            addBuildInfos();
        }
    }

    private void addBuildInfos()
    {
        Text text = new Text("Build " + Version.getGitCommitIdAbbrev() + "   "
                + formatBuildTime(), .5, "fonts/Cantarell-Regular.ttf");
        text.position(-10, 8.2);
        text.color("gray");
        text.makeStatic();
        text.density(0.1);
        add(text);
    }

    private Color darkenColor(String color)
    {
        return ColorUtil.interpolate(colors.get(color), colors.get("black"),
                0.5);
    }

    private void createGround()
    {
        Rectangle ground = new Rectangle(20, 0.2);
        ground.color("white");
        ground.center(0, -6);
        ground.restitution(.95);
        ground.friction(.2);
        ground.makeStatic();
        add(ground);
    }

    private void createRectangleItem()
    {
        Rectangle rectangle = new Rectangle(1, 2);
        rectangle.color(rectangleItemColor);
        rectangle.position(-5, 10);
        makeItemDynamic(rectangle);
        rectangle.rotation(30);
        dropDownItem(rectangle);
    }

    private void createCircleItem()
    {
        Circle circle = new Circle();
        circle.color(circleItemColor);
        circle.position(5, 10);
        makeItemDynamic(circle);
        circle.applyImpulse(new Vector(Random.range(-100, 100), 0));
        dropDownItem(circle);
    }

    private void createTriangleItem()
    {
        Polygon triangle = new Polygon(new Vector(0, 0), new Vector(1, 0),
                new Vector(.5, -1));
        triangle.color(triangleItemColor);
        makeItemDynamic(triangle);
        triangle.rotation(-20);
        dropDownItem(triangle);
    }

    /**
     * Mache eine geometrische Figur dynamisch und stelle weitere physikalische
     * Eigenschaften ein.
     *
     * @param item Eine kleine geometrische Figur (Rechteck, Kreis, Dreieck).
     */
    private void makeItemDynamic(Actor item)
    {
        item.restitution(0.9);
        item.friction(1);
        item.makeDynamic();
    }

    /**
     * Wenn eine dynamische geometrische Figur tiefer als {@code y = -10} fällt,
     * wird sie wieder von oben heruntergeworfen.
     */
    @Override
    public void onFrameUpdate(double pastTime)
    {
        for (Actor item : items)
        {
            if (item.center().y() < -10)
            {
                dropDownItem(item);
            }
        }
        // Den Kreis des i’s auf und ab bewegen, damit sich keine
        // kleinen Figuren zwischen dem P und dem i ansammeln. Somit endet die
        // Animation praktisch nie.
        if (logo.circleI.y() >= 4)
        {
            // abwärts
            yMovementCircleI = -0.01;
        }
        else if (logo.circleI.y() <= 2)
        {
            // aufwärts
            yMovementCircleI = 0.01;
        }
        logo.circleI.moveBy(0, yMovementCircleI);
    }

    private String formatBuildTime()
    {
        Date date = new Date(Version.getBuildTime());
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
    }

    /**
     * Lasse eine geometrische Figur herabfallen.
     *
     * @param item Eine kleine geometrische Figur (Rechteck, Kreis, Dreieck).
     */
    private void dropDownItem(Actor item)
    {
        if (!item.isMounted())
        {
            delay(Random.range(5), () -> {
                items.add(item);
                add(item);
            });
        }
        item.resetMovement();
        item.center(Random.range(-7, 7), Random.range(5, 8));
    }

    public static void main(String[] args) throws IOException
    {
        Controller.instantMode(false);
        Controller.start(new MainAnimation(false));
        Controller.recordScreen(20);
        Controller.title("Engine Pi " + Version.getVersion());
    }
}
