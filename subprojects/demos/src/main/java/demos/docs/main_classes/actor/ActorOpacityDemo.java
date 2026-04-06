/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2024 Josef Friedrich and contributors.
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
package demos.docs.main_classes.actor;

import pi.Controller;
import pi.Scene;
import pi.Text;
import pi.actor.Actor;
import pi.actor.Image;
import pi.util.TextUtil;

/**
 * Demonstriert den Setter {@link pi.actor.Actor#opacity(double) opacity} der
 * {@link pi.actor.Actor Figuren}.
 *
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class ActorOpacityDemo extends Scene
{
    int xCursor = -17;

    public ActorOpacityDemo()
    {
        info().title("opacity (Durchsichtigkeit)")
            .description(
                "Die linke Figur ist undurchsichtig, die rechte komplett transparent. Vom links nach rechts nimmt die Durchsichtigkeit um 0.2 pro Bild zu.");
        for (double opacity = 1; opacity >= 0; opacity -= 0.2)
        {
            createImage(opacity);
        }
    }

    private void createImage(double opacity)
    {
        Actor image = new Image(
                "openpixelproject/various/opp_promo_cavegirl.png")
                    .opacity(opacity)
                    .anchor(xCursor, -2);
        add(image);

        add(new Text(TextUtil.roundNumber(image.opacity())).anchor(xCursor + 2,
            -4));
        xCursor += 6;
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.start(new ActorOpacityDemo(), 1200, 400);
    }

}
