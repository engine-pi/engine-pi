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

import static pi.Controller.colors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Function;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import pi.Scene;
import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Internal;
import pi.annotations.Setter;
import pi.debug.ToStringFormatter;
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

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/graphics/SceneInfoOverlayDemo.java

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
     * Die Referenz auf die übergeordnete Szene wird benötigt, um Zugriff auf
     * die
     * {@link pi.event.FrameUpdateListenerRegistration#delay(double, Runnable)
     * delay}-Methode zu haben.
     */
    private @NonNull final Scene scene;

    private @NonNull final CellBox cell;

    public @NonNull final InsetBox margin;

    public @NonNull final BackgroundBox background;

    private VerticalBox<Box> vertical;

    /**
     * Die Aufgabe, die mit Verzögerung die Infobox ausblendet.
     */
    private SingleTask hideTask;

    public SceneInfoOverlay(Scene scene)
    {
        this.scene = scene;
        background = new BackgroundBox().color(colors.get("white", 200));
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
        hideTask = scene.delay(duration, this::disable);
    }

    /* permanent */

    /**
     * Die Infobox wird nicht nach einer gewissen Zeit ausgeblendet, sondern ist
     * <b>permanent</b> zu sehen.
     */
    private boolean permanent = false;

    /**
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    @ChainableMethod
    public SceneInfoOverlay permanent()
    {
        this.permanent = true;
        cancelHideTask();
        return this;
    }

    /**
     * Die Anzeigedauer der Infobox in Sekunden.
     */
    private double duration = 10;

    /**
     * Setzt die Anzeigedauer der Infobox in Sekunden.
     *
     * @param duration Die Anzeigedauer der Infobox in Sekunden.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @API
    @Setter
    @ChainableMethod
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
     * Diese Methode muss jedes Mal ausgeführt werden, wenn ein Text gesetzt
     * wird.
     * </p>
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     */

    @ChainableMethod
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

    private boolean isStringEmpty(Object content)
    {
        return content == null || String.valueOf(content).isEmpty();
    }

    private @Nullable TextBox setBox(Object content,
            Function<Object, TextBox> function)
    {
        if (isStringEmpty(content))
        {
            return null;
        }
        return function.apply(content);
    }

    /* title */

    /**
     * Der <b>Titel</b> der Szene.
     *
     * <p>
     * Der Titel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private @Nullable TextBox title = null;

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
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    @ChainableMethod
    public SceneInfoOverlay title(Object title)
    {
        this.title = setBox(title,
            content -> new TextLineBox(content).fontStyle(FontStyle.BOLD)
                .fontSize(18)).color(textColor);
        return assemble();
    }

    /* subtitle */

    /**
     * Der <b>Untertitel</b> der Szene.
     *
     * <p>
     * Der Untertitel wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private @Nullable TextBox subtitle = null;

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
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    @ChainableMethod
    public SceneInfoOverlay subtitle(Object subtitle)
    {
        this.subtitle = setBox(subtitle, TextLineBox::new).color(textColor);
        return assemble();
    }

    /* description */

    /**
     * Ein längerer, mehrzeiliger <b>Beschreibungstext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private @Nullable TextBox description = null;

    /**
     * Setzt den längeren, mehrzeiligen <b>Beschreibungstext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param description Ein längerer, mehrzeiliger <b>Beschreibungstext</b>
     *     zur Szene. Jedes Argument wird in eine neue Zeile gesetzt.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    @ChainableMethod
    public SceneInfoOverlay description(Object... description)
    {
        if (isContentArrayNull(description))
        {
            this.description = null;
        }
        else
        {
            this.description = new TextBlockBox(description).fontSize(12)
                .color(textColor);
        }
        return assemble();
    }

    private boolean isContentArrayNull(Object... content)
    {
        return content.length == 0
                || (content.length == 1 && content[0] == null);
    }

    /**
     * Ein längerer, mehrzeiliger <b>Hilfetext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @since 0.42.0
     */
    private @Nullable TextBox help = null;

    /**
     * Setzt den <b>Hilfetext</b> zur Szene.
     *
     * <p>
     * Der Beschreibungstext wird als Einblendung über die Szene gelegt.
     * </p>
     *
     * @param help Eine beliebige Anzahl an <b>Hilfetexten</b> zur Szene. Jedes
     *     Argument wird in eine neue Zeile gesetzt.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    @ChainableMethod
    public SceneInfoOverlay help(Object... help)
    {
        if (isContentArrayNull(help))
        {
            this.help = null;
        }
        else
        {
            this.help = new TextBlockBox(help).fontSize(12)
                .fontStyle(FontStyle.ITALIC)
                .color(textColor);
        }
        return assemble();
    }

    /* textColor */

    private Color textColor = colors.get("black");

    /**
     * Setzt die <b>Textfarbe</b>.
     *
     * @param textColor die <b>Textfarbe</b>.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @Setter
    @API
    @ChainableMethod
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
     * Setzt die <b>horizontale</b> Ausrichtung der Infobox.
     *
     * @param hAlign Die <b>horizontale</b> Ausrichtung der Infobox.
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     */
    @Setter
    @API
    @ChainableMethod
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
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     */
    @Setter
    @API
    @ChainableMethod
    public SceneInfoOverlay vAlign(VAlign vAlign)
    {
        cell.vAlign(vAlign);
        return this;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
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
     * <b>Deaktiviert</b> die Infobox der Szene.
     *
     * <p>
     * Die Infobox wird nicht angezeigt.
     * </p>
     *
     * @return Eine Referenz auf die eigene Instanz, damit nach dem
     *     Erbauer/Builder-Entwurfsmuster die Eigenschaften durch aneinander
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     */
    @API
    @ChainableMethod
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
     *     gekettete Setter festgelegt werden können, z.B.
     *     {@code info().title(..).subtitle(..)}.
     *
     * @since 0.42.0
     */
    @API
    @ChainableMethod
    public SceneInfoOverlay toggle()
    {
        cell.toggle();
        return this;
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        ToStringFormatter formatter = new ToStringFormatter(this);

        if (title != null)
        {
            formatter.append("title", title.content());
        }
        if (subtitle != null)
        {
            formatter.append("subtitle", subtitle.content());
        }
        if (description != null)
        {
            formatter.append("description", description.content());
        }
        if (help != null)
        {
            formatter.append("help", help.content());
        }

        formatter.append("permanent", permanent);
        formatter.append("duration", duration, "s");
        formatter.append("textColor", textColor);
        return formatter.format();
    }
}
