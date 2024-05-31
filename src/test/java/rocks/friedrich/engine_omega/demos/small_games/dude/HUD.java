/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/HUD.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package rocks.friedrich.engine_omega.demos.small_games.dude;

import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.Layer;
import rocks.friedrich.engine_omega.actor.Actor;

/**
 * Das HUD gibt einige Spieldaten über dem Rest der Szenen-Objekte wieder
 */
public class HUD extends Layer implements FrameUpdateListener
{
    private final HUDDisplay display;

    private final GameData gameData;

    public HUD(GameData gameData)
    {
        this.setParallaxZoom(0);
        this.setParallaxPosition(0, 0);
        this.setParallaxRotation(0);
        this.gameData = gameData;
        this.display = new HUDDisplay(-DudeDemo.GAME_WIDTH_PX / 2 + 20,
                DudeDemo.GAME_HEIGHT_PX / 2 - 130);
        add(display.getActors().toArray(new Actor[0]));
    }

    @Override
    public void onFrameUpdate(double deltaSeconds)
    {
        display.setLineDisplay(0, 1);
        display.setLineDisplay(1,
                (float) gameData.getMana() / GameData.MAX_MANA);
        display.setLineDisplay(2,
                Math.min(1, gameData.getPlayerVelocity() / 80));
        display.setDisplayNumber(gameData.getMoney());
    }
}
