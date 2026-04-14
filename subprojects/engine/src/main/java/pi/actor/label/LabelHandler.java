/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package pi.actor.label;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Internal;
import pi.annotations.Setter;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/ActorLabelDemo.java

/**
 * Verwaltet eine {@link Label Hauptbeschriftung} und mehrere weitere
 * {@link Label Beschriftungen}.
 *
 * <p>
 * Diese Klasse liefert das öffentliche Attribut {@link pi.actor.Actor#label}.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.46.0
 */
public class LabelHandler
{
    /* labels */

    private final List<Label> labels = new CopyOnWriteArrayList<>();

    /**
     * Gibt alle <b>Beschriftung</b> einschließlich der Hauptbeschriftung
     * zurück.
     *
     * @return Alle <b>Beschriftung</b> einschließlich der Hauptbeschriftung.
     *
     * @since 0.46.0
     */
    @API
    @Getter
    public List<Label> labels()
    {
        return labels;
    }

    /**
     * @since 0.46.0
     */
    public boolean hasLabels()
    {
        return labels.isEmpty();
    }

    public LabelHandler add(@NonNull Label label)
    {
        if (labels.contains(label))
        {
            throw new IllegalArgumentException(
                    "Die Beschriftung wurde bereits der Beschriftungsliste hinzugefügt");
        }
        labels.add(label);
        return this;
    }

    /**
     * <b>Entfernt</b> alle Beschriftungen einschließlich der Hauptbeschriftung.
     *
     * @since 0.46.0
     */
    @ChainableMethod
    public LabelHandler remove()
    {
        mainLabel = null;
        labels.clear();
        return this;
    }

    /* labels */

    private @Nullable Label mainLabel;

    /**
     * Gibt die <b>Hauptbeschriftung</b> zurück.
     *
     * @since 0.46.0
     *
     * @hidden
     */
    @Internal
    @Getter
    public @Nullable Label mainLabel()
    {
        return mainLabel;
    }

    /**
     * Setzt die <b>Hauptbeschriftung</b>.
     *
     * @param mainLabel Die <b>Hauptbeschriftung</b>.
     *
     * @since 0.46.0
     *
     * @hidden
     */
    @Internal
    @Setter
    @ChainableMethod
    public LabelHandler mainLabel(@Nullable Label mainLabel)
    {
        if (mainLabel != null)
        {
            labels.remove(mainLabel);
        }
        this.mainLabel = mainLabel;
        labels.add(mainLabel);
        return this;
    }

    @API
    @Setter
    @ChainableMethod
    public LabelHandler mainLabel(Object... content)
    {
        return mainLabel(new TextLabel(content));
    }
}
