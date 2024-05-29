/*
 * Source: https://github.com/engine-alpha/tutorials/blob/master/src/eatutorials/statefulanimation/PlayerState.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
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
package rocks.friedrich.engine_omega.tutorials.statefulanimation;

public enum PlayerState
{
    IDLE("spr_m_traveler_idle_anim.gif"),
    WALKING("spr_m_traveler_walk_anim.gif"),
    RUNNING("spr_m_traveler_run_anim.gif"),
    JUMPING("spr_m_traveler_jump_1up_anim.gif"),
    MIDAIR("spr_m_traveler_jump_2midair_anim.gif"),
    FALLING("spr_m_traveler_jump_3down_anim.gif"),
    LANDING("spr_m_traveler_jump_4land_anim.gif");

    private String gifFileName;

    PlayerState(String gifFileName)
    {
        this.gifFileName = gifFileName;
    }

    public String getGifFileLocation()
    {
        return "eatutorials/statefulanimation/assets/" + this.gifFileName;
    }
}
