/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/dude/ManaPickup.java
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
package rocks.friedrich.engine_omega.examples.dude;

import rocks.friedrich.engine_omega.actor.Animation;
import rocks.friedrich.engine_omega.event.CollisionEvent;
import rocks.friedrich.engine_omega.event.CollisionListener;

/**
 * Danke an <a href="https://sorceressgamelab.itch.io/">SorceressGameLab</a> für die Assets!
 */
public class ManaPickup extends Animation implements CollisionListener<PlayerCharacter> {
    private static final float SIZE = .4f;
    private static final float PICKUP_DELAY = 5;

    private boolean active = true;

    public ManaPickup() {
        super(Animation.createFromSpritesheet(.1f, "dude/gem_blue.png", 6, 1, SIZE, SIZE));
    }

    @Override
    public void onCollision(CollisionEvent<PlayerCharacter> collisionEvent) {
        if (!active) {
            return;
        }

        // Ich wurde aufgesammelt!
        collisionEvent.getColliding().gotItem(Item.ManaPickup);
        this.setActive(false);
        active = false;

        delay(3, () -> setActive(true));
        animateOpacity(2, 1f);
    }

    private void setActive(boolean b) {
        active = b;
        setOpacity(b ? 1 : 0);
    }
}
