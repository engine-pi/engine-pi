/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/PrismaticJoint.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2020 Michael Andonie and contributors.
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
package pi.physics.joints;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.annotations.Setter;

/**
 * Eine <b>Federverbindung</b>.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 *
 * @see Joint
 * @see DistanceJoint
 * @see PrismaticJoint
 * @see RevoluteJoint
 * @see RopeJoint
 * @see WeldJoint
 */
public final class PrismaticJoint extends
        Joint<de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint>
{
    private double lowerLimit;

    private double upperLimit;

    private boolean motorEnabled;

    private boolean limitEnabled;

    /**
     * Geschwindigkeit in [m / s].
     */
    private double motorSpeed;

    private double maximumMotorForce;

    @API
    @Setter
    public void maximumMotorForce(double maximumMotorForce)
    {
        this.maximumMotorForce = maximumMotorForce;
        this.motorEnabled = true;
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            joint.setMaxMotorForce((float) maximumMotorForce);
            joint.enableMotor(true);
        }
    }

    @API
    @Getter
    public double maximumMotorForce()
    {
        return maximumMotorForce;
    }

    @API
    @Getter
    public double lowerLimit()
    {
        return lowerLimit;
    }

    @API
    @Setter
    public void lowerLimit(double lowerLimit)
    {
        this.lowerLimit = lowerLimit;
        this.limitEnabled = true;
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            joint.setLimits((float) lowerLimit, (float) upperLimit);
            joint.enableLimit(true);
        }
    }

    @API
    @Getter
    public double upperLimit()
    {
        return upperLimit;
    }

    @API
    @Setter
    public void upperLimit(double upperLimit)
    {
        this.upperLimit = upperLimit;
        this.limitEnabled = true;
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            joint.setLimits((float) lowerLimit, (float) upperLimit);
            joint.enableLimit(true);
        }
    }

    @API
    @Getter
    public double motorSpeed()
    {
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            return joint.getMotorSpeed();
        }
        return motorSpeed;
    }

    @API
    @Setter
    public void motorSpeed(double motorSpeed)
    {
        this.motorSpeed = motorSpeed;
        this.motorEnabled = true;
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            joint.setMotorSpeed((float) motorSpeed);
            joint.enableMotor(true);
        }
    }

    @API
    public boolean isMotorEnabled()
    {
        return motorEnabled;
    }

    @API
    @Setter
    public void motorEnabled(boolean motorEnabled)
    {
        this.motorEnabled = motorEnabled;
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            joint.enableMotor(motorEnabled);
        }
    }

    @API
    public boolean isLimitEnabled()
    {
        return limitEnabled;
    }

    @API
    @Setter
    public void limitEnabled(boolean limitEnabled)
    {
        this.limitEnabled = limitEnabled;
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint != null)
        {
            joint.enableLimit(limitEnabled);
        }
    }

    @API
    @Setter
    public void limits(double lower, double upper)
    {
        lowerLimit(lower);
        upperLimit(upper);
    }

    @API
    @Getter
    public double translation()
    {
        de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint = joint();
        if (joint == null)
        {
            return 0;
        }
        return joint.getJointTranslation();
    }

    @Override
    protected void updateCustomProperties(
            de.pirckheimer_gymnasium.jbox2d.dynamics.joints.PrismaticJoint joint)
    {
        joint.setMotorSpeed((float) motorSpeed);
        joint.setMaxMotorForce((float) maximumMotorForce);
        joint.setLimits((float) lowerLimit, (float) upperLimit);
        joint.enableMotor(motorEnabled);
        joint.enableLimit(limitEnabled);
    }
}
