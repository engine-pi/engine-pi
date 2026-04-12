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

import static pi.Controller.colors;
import static pi.Controller.config;

import java.awt.event.KeyEvent;

import pi.Controller;
import pi.Random;
import pi.Scene;
import pi.actor.Actor;
import pi.actor.ActorCreator;
import pi.actor.Rectangle;
import pi.actor.label.TextLabel;
import pi.event.KeyStrokeListener;
import pi.graphics.boxes.HAlign;
import pi.graphics.boxes.VAlign;

/**
 * @author Josef Friedrich
 *
 * @since 0.45.0
 */
public class ActorLabelDemo extends Scene implements KeyStrokeListener
{
    Actor actor;

    String longContent = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

    String origContent = "";

    TextLabel label;

    public ActorLabelDemo()
    {
        info("label (Beschriftung)");
        ActorCreator.createCage(this);

        label = new TextLabel("Das ist eine Beschriftung", "(label)");

        actor = new Rectangle(4, 2).makeDynamic()
            .applyForce(16000, 31000)
            .restitution(1)
            .rotateBy(42)
            .label(label);

        add(actor);
    }

    private void toggleContent()
    {
        if (label.content().equals(longContent))
        {
            label.content(origContent);
        }
        else
        {
            origContent = label.content();
            label.content(longContent);
        }
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent)
    {
        switch (keyEvent.getKeyCode())
        {
        case KeyEvent.VK_1 -> label.vAlign(VAlign.TOP);
        case KeyEvent.VK_2 -> label.vAlign(VAlign.MIDDLE);
        case KeyEvent.VK_3 -> label.vAlign(VAlign.BOTTOM);
        case KeyEvent.VK_4 -> label.hAlign(HAlign.LEFT);
        case KeyEvent.VK_5 -> label.hAlign(HAlign.CENTER);
        case KeyEvent.VK_6 -> label.hAlign(HAlign.RIGHT);
        // T = text
        case KeyEvent.VK_T -> toggleContent();
        // C = color
        case KeyEvent.VK_C -> label.color(colors.random());
        // O = offset
        case KeyEvent.VK_O -> label.offset(Random.range(-0.2, 5));
        // M = multiline
        case KeyEvent.VK_M ->
            actor.label("Eine", "neue", "mehrzeilige", "Beschriftung");
        }
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        config.debug.enabled(true).renderAABBs(true);
        Controller.start(new ActorLabelDemo());
    }
}
