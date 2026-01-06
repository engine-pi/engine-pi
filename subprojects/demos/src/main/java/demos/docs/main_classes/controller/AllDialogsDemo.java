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
package demos.docs.main_classes.controller;

import java.util.function.Supplier;

import pi.Controller;
import pi.Scene;
import pi.Vector;
import pi.Text;
import pi.event.FrameUpdateListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;

/**
 * Demonstriert die Klasse {@link pi.graphics.DialogLauncher}.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class AllDialogsDemo extends Scene
{
    Text result;

    public AllDialogsDemo()
    {
        result = new Text("Ergebnis");
        result.position(0, 6);
        result.color("red");
        add(result);

        int x = -12;

        int y1 = 3;
        int y2 = 2;

        int y3 = -2;
        int y4 = -3;

        // showMessage

        new DialogOpener("showMessage(message)", () -> {
            Controller.dialog.showMessage("Message");
            return null;
        }, x, y1);

        new DialogOpener("showMessage(message, title)", () -> {
            Controller.dialog.showMessage("Message", "Title");
            return null;
        }, x, y2);

        // requestStringInput

        new DialogOpener("requestStringInput(message)",
                () -> Controller.dialog.requestStringInput("Message"), x, y3);

        new DialogOpener("requestStringInput(message, title)",
                () -> Controller.dialog.requestStringInput("Message", "Title"),
                x, y4);

        // requestYesNo

        x = 2;

        new DialogOpener("requestYesNo(message)",
                () -> Controller.dialog.requestYesNo("Message"), x, y1);

        new DialogOpener("requestYesNo(message, title)",
                () -> Controller.dialog.requestYesNo("Message", "Title"), x,
                y2);

        // requestOkCancel

        new DialogOpener("requestOkCancel(message)",
                () -> Controller.dialog.requestOkCancel("Message"), x, y3);

        new DialogOpener("requestYesNo(message, title)",
                () -> Controller.dialog.requestOkCancel("Message", "Title"), x,
                y4);
    }

    class DialogOpener extends Text
            implements MouseClickListener, FrameUpdateListener
    {
        Supplier<Object> supplier;

        public DialogOpener(String content, Supplier<Object> supplier, double x,
                double y)
        {
            super(content);
            this.supplier = supplier;
            add(this);
            position(x, y);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton button)
        {
            if (contains(position))
            {
                result.content(supplier.get());
            }
        }

        @Override
        public void onFrameUpdate(double pastTime)
        {
            if (contains(Controller.mousePosition()))
            {
                color("blue");
            }
            else
            {
                color("white");
            }
        }
    }

    public static void main(String[] args)
    {
        Controller.start(new AllDialogsDemo());
    }
}
