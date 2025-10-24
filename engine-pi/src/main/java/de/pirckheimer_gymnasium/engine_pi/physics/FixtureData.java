/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/internal/physics/FixtureData.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.physics;

import de.pirckheimer_gymnasium.jbox2d.collision.shapes.Shape;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Filter;
import de.pirckheimer_gymnasium.jbox2d.dynamics.Fixture;
import de.pirckheimer_gymnasium.jbox2d.dynamics.FixtureDef;

import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;

/**
 * Wrapper-Klasse für
 * {@link de.pirckheimer_gymnasium.jbox2d.dynamics.FixtureDef} zur Übersetzung
 * zwischen Engine-Features und JBox2D-Features.
 *
 * @author Michael Andonie
 *
 * @see de.pirckheimer_gymnasium.engine_pi.physics.PhysicsData
 */
public class FixtureData
{
    private double density = PhysicsData.DEFAULT_DENSITY;

    private boolean isDensitySet = false;

    private double friction = PhysicsData.DEFAULT_FRICTION;

    private boolean isFrictionSet = false;

    private double restitution = PhysicsData.DEFAULT_RESTITUTION;

    private boolean isRestitutionSet = false;

    private Shape shape;

    public double getDensity()
    {
        return density;
    }

    public void setDensity(double density)
    {
        isDensitySet = true;
        this.density = density;
    }

    public double getFriction()
    {
        return friction;
    }

    public void setFriction(double friction)
    {
        isFrictionSet = true;
        this.friction = friction;
    }

    public double getRestitution()
    {
        return restitution;
    }

    public void setRestitution(double restitution)
    {
        isRestitutionSet = true;
        this.restitution = restitution;
    }

    public Shape getShape()
    {
        return shape;
    }

    public void setShape(Shape shape)
    {
        this.shape = shape;
    }

    public boolean isSensor()
    {
        return isSensor;
    }

    public void setSensor(boolean sensor)
    {
        isSensor = sensor;
    }

    public Filter getFilter()
    {
        return filter;
    }

    public void setFilter(Filter filter)
    {
        throw new UnsupportedOperationException(
                "Custom Filter sind noch nicht implementiert. Ist für future release geplant.");
    }

    private boolean isSensor = false;

    private boolean isSensorSet = false;

    /**
     * Erstellt eine neue Fixture-Data.
     *
     * @param shape Die Shape, die diese Fixture hat.
     */
    public FixtureData(Shape shape)
    {
        this.shape = shape;
    }

    /**
     * Collision-Filter. Ist default by Standard. Kann beliebig gesetzt werden;
     * in einem zukünftigen Release. Siehe:
     * https://www.iforce2d.net/b2dtut/collision-filtering
     * <p>
     * TODO: Implement filter Functionality
     * </p>
     */
    private Filter filter = new Filter();

    /**
     * Generiert eine JBox2D Fixture-Definition, die den aktuellen Settings
     * dieser Fixture-Data entspricht.
     *
     * @hidden
     */
    @Internal
    FixtureDef createFixtureDef(PhysicsData parent)
    {
        FixtureDef def = new FixtureDef();
        def.density = (float) (isDensitySet ? density
                : parent.getGlobalDensity());
        def.friction = (float) (isFrictionSet ? friction
                : parent.getGlobalFriction());
        def.restitution = (float) (isRestitutionSet ? restitution
                : parent.getGlobalRestitution());
        def.isSensor = isSensorSet ? isSensor : parent.getType().isSensor();
        def.filter = filter;
        def.shape = shape;
        return def;
    }

    /**
     * Generiert eine Fixture Data aus einer JBox2D-Fixture
     *
     * @param fixture Die JBox2D-Fixture, die als Engine Fixture Data generiert
     *     werden solll
     *
     * @return Eine Engine-Fixture-Data, die die JBox2D-Fixture-Def vollständig
     *     beschreibt.
     *
     * @hidden
     */
    @Internal
    public static FixtureData fromFixture(Fixture fixture)
    {
        FixtureData data = new FixtureData(fixture.shape);
        data.setRestitution(fixture.restitution);
        data.setDensity(fixture.density);
        data.setFriction(fixture.density);
        data.setSensor(fixture.isSensor);
        data.filter = fixture.filter; // TODO: Adapt Filter Line once Filter
                                      // is implemented
        return data;
    }
}
