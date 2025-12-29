/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.graphics;

import static pi.Resources.colors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;

import pi.Scene;
import pi.annotations.API;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.event.SingleTask;
import pi.graphics.boxes.BackgroundBox;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.CellBox;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.InsetBox;
import pi.graphics.boxes.TextBlockBox;
import pi.graphics.boxes.TextBox;
import pi.graphics.boxes.TextLineBox;
import pi.graphics.boxes.VAlign;
import pi.graphics.boxes.VerticalBox;
import pi.resources.font.FontStyle;

// Go to file:///home/jf/repos/school/monorepo/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/SceneInfoOverlayDemo.java

/**
 * Eine <b>Infobox</b>, die <b>über</b> eine <b>Szene</b> gelegt werden kann.
 *
 * <p>
 * Sie wird standardmäßig nach einer gewissen Zeit wieder ausgeblendet.
 * </p>
 *
 * <p>
 * Die Infobox kann vier Zeichenketten enthalten:
 * </p>
 *
 * <ul>
 * <li>{@link #title}: Der <b>Titel</b> der Szene.</li>
 * <li>{@link #subtitle}: Der <b>Untertitel</b> der Szene.</li>
 * <li>{@link #description}: Ein längerer, mehrzeiliger <b>Beschreibungstext</b>
 * zur Szene.</li>
 * <li>{@link #help}: Ein längerer, mehrzeiliger <b>Hilfetext</b> zur
 * Szene.</li>
 * </ul>
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 *
 * @see Scene#info()
 */
public class SceneInfoOverlay
{
    /**
     * Der <b>Titel</b> der Szene.
     *
     * <p>
     * Der Titel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox title = null;

    /**
     * Der <b>Untertitel</b> der Szene.
     *
     * <p>
     * Der Untertitel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox subtitle = null;

    /**
     * Ein längerer, mehrzeiliger <b>Beschreibungstext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox description = null;

    /**
     * Ein längerer, mehrzeiliger <b>Hilfetext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private TextBox help = null;

    private Color textColor = colors.get("black");

    /**
     * Die Infobox wird nicht nach einer gewissen Zeit ausgeblendet, sondern ist
     * <b>permanent</b> zu sehen.
     */
    private boolean permanent = false;

    /**
     * Die Anzeigedauer der Infobox in Sekunden.
     */
    private double duration = 5;

    private CellBox cell;

    public final InsetBox margin;

    public final BackgroundBox background;

    private VerticalBox<Box> vertical;

    /**
     * Die Referenz auf die übergeordnete Szene wird benötigt, um Zugriff auf
     * die
     * {@link pi.event.FrameUpdateListenerRegistration#delay(double, Runnable)
     * delay}-Methode zu haben.
     */
    private final Scene scene;

    /**
     * Die Aufgabe, die mit Verzögerung die Infobox ausblendet.
     */
    private SingleTask hideTask;

    public SceneInfoOverlay(Scene scene)
    {
        this.scene = scene;
        background = new BackgroundBox().color(colors.get("white", 150));
        margin = new InsetBox(background).allSides(10);
        cell = new CellBox(margin).hAlign(HAlign.RIGHT).vAlign(VAlign.TOP);
        startHideTask();
    }

    private void cancelHideTask()
    {
        hideTask.cancel();
    }

    private void startHideTask()
    {
        if (permanent)
        {
            return;
        }
        hideTask = scene.delay(duration, () -> disable());
    }

    /**
     *
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    public SceneInfoOverlay permanent()
    {
        this.permanent = true;
        cancelHideTask();
        return this;
    }

    /**
     * Setzt die Anzeigedauer der Infobox in Sekunden.
     *
     * @param duration Die Anzeigedauer der Infobox in Sekunden.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    public SceneInfoOverlay duration(double duration)
    {
        this.duration = duration;
        this.permanent = false;
        cancelHideTask();
        startHideTask();
        return this;
    }

    /**
     * Baut den Boxenbaum neu auf.
     *
     * <p>
     * Diese Methode muss jedes mal ausgeführt werden, wenn ein Text gesetzt
     * wird.
     * </p>
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     */
    private SceneInfoOverlay assemble()
    {
        vertical = new VerticalBox<>();
        vertical.padding(5);

        if (title != null)
        {
            vertical.addChild(title);
        }

        if (subtitle != null)
        {
            vertical.addChild(subtitle);
        }

        if (description != null)
        {
            vertical.addChild(description);
        }

        if (help != null)
        {
            vertical.addChild(help);
        }
        background.addChild(vertical);
        return this;
    }

    private boolean isStringEmpty(String content)
    {
        return content == null || content.equals("");
    }

    private TextBox setBox(String content, Function<String, TextBox> function)
    {
        if (isStringEmpty(content))
        {
            return null;
        }
        return function.apply(content);
    }

    /**
     * Setzt den <b>Titel</b> der Szene.
     *
     * <p>
     * Der Titel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param title Der <b>Titel</b> der Szene.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay title(String title)
    {
        this.title = setBox(title, content -> new TextLineBox(content)
                .fontStyle(FontStyle.BOLD).fontSize(18)).color(textColor);
        return assemble();
    }

    /**
     * Setzt den <b>Untertitel</b> der Szene.
     *
     * <p>
     * Der Untertitel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param subtitle Der <b>Untertitel</b> der Szene.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay subtitle(String subtitle)
    {
        this.subtitle = setBox(subtitle, content -> new TextLineBox(content))
                .color(textColor);;
        return assemble();
    }

    /**
     * Setzt den längerer, mehrzeiliger <b>Beschreibungstext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param description Ein längerer, mehrzeiliger <b>Beschreibungstext</b>
     *     zur Szene.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay description(String description)
    {
        this.description = setBox(description,
                content -> new TextBlockBox(description).fontSize(12))
                .color(textColor);;
        return assemble();
    }

    /**
     * Setzt den <b>Hilfetext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param help Ein <b>Hilfetext</b> zur Szene.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay help(String help)
    {
        this.help = setBox(help, content -> new TextBlockBox(help).fontSize(12))
                .color(textColor);
        return assemble();
    }

    /**
     * Setzt die <b>Textfarbe</b>.
     *
     * @param textColor die <b>Textfarbe</b>.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    public SceneInfoOverlay textColor(Color textColor)
    {
        this.textColor = textColor;
        if (title != null)
        {
            title.color(textColor);
        }

        if (subtitle != null)
        {
            subtitle.color(textColor);
        }

        if (description != null)
        {
            description.color(textColor);
        }

        if (help != null)
        {
            help.color(textColor);
        }

        return this;
    }

    /**
     * Setzt die <b>horizonale</b> Ausrichtung der Infobox.
     *
     * @param hAlign Die <b>horizonale</b> Ausrichtung der Infobox.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     */
    @Setter
    @API
    public SceneInfoOverlay hAlign(HAlign hAlign)
    {
        cell.hAlign(hAlign);
        return this;
    }

    /**
     * Setzt die <b>vertikale</b> Ausrichtung der Infobox.
     *
     * @param vAlign Die <b>vertikale</b> Ausrichtung der Infobox.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     */
    @Setter
    @API
    public SceneInfoOverlay vAlign(VAlign vAlign)
    {
        cell.vAlign(vAlign);
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @hidden
     */
    @Internal
    public SceneInfoOverlay render(Graphics2D g, int width, int height)
    {
        cell.width(width).height(height).remeasure().render(g);
        return this;
    }

    /**
     * Deaktiviert die Infobox.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     */
    @API
    public SceneInfoOverlay disable()
    {
        cell.disable();
        return this;
    }

    /**
     * Schaltet zwischen dem Status <b>deaktiviert</b> und <b>aktiviert</b>
     * <b>hin</b>- und <b>her</b>.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z. B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @API
    public SceneInfoOverlay toggle()
    {
        cell.toggle();
        return this;
    }
}
