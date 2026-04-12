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

import pi.annotations.API;
import pi.annotations.ChainableMethod;
import pi.annotations.Getter;
import pi.annotations.Setter;
import pi.graphics.boxes.Box;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/docs/main_classes/actor/ActorLabelDemo.java

/**
 * Eine <b>Beschriftung</b> für eine Figur.
 *
 * @author Josef Friedrich
 *
 * @since 0.46.0
 */
public abstract class Label
{
    @API
    @Getter
    public abstract Box box();

    /* vAlign */

    protected VAlign vAlign = VAlign.BOTTOM;

    /**
     * @since 0.46.0
     */
    @API
    @Getter
    public VAlign vAlign()
    {
        return vAlign;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Beschriftung, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der Beschriftung
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code label.content(..).font(..)}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public Label vAlign(VAlign vAlign)
    {
        this.vAlign = vAlign;
        return this;
    }

    /* hAlign */

    protected HAlign hAlign = HAlign.CENTER;

    /**
     * @since 0.46.0
     */
    @API
    @Getter
    public HAlign hAlign()
    {
        return hAlign;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Beschriftung, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der Beschriftung
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code label.content(..).font(..)}.
     *
     * @since 0.45.0
     */
    @API
    @Setter
    @ChainableMethod
    public Label hAlign(HAlign hAlign)
    {
        this.hAlign = hAlign;
        return this;
    }

    /* offset */

    /**
     * Der Abstand vom achsenparallelen Begrenzungsrahmen (AABB) zur
     * Beschriftung. Der Offset hat keine Auswirkung wenn {@link VAlign#MIDDLE}
     * und {@link HAlign#CENTER} eingestellt ist.
     *
     * @since 0.46.0
     */
    private double offset = 0.2;

    /**
     * @since 0.46.0
     */
    @API
    @Getter
    public double offset()
    {
        return offset;
    }

    /**
     * @return Eine Referenz auf die eigene Instanz der Beschriftung, damit nach
     *     dem Erbauer/Builder-Entwurfsmuster die Eigenschaften der Beschriftung
     *     durch aneinander gekettete Setter festgelegt werden können, z.B.
     *     {@code label.content(..).font(..)}.
     *
     * @since 0.46.0
     */
    @API
    @Setter
    @ChainableMethod
    public Label offset(double offset)
    {
        this.offset = offset;
        return this;
    }
}
