package demos.classes.class_game.attribute_dialog;

import java.util.function.Supplier;

import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.actor.Text;
import pi.event.MouseButton;
import pi.event.MouseClickListener;

/**
 * Demonstriert die Klasse {@link pi.graphics.DialogLauncher}.
 */
public class AllDialogsDemo extends Scene
{
    Text result;

    public AllDialogsDemo()
    {
        result = new Text("Ergebnis");
        result.setPosition(0, 6);
        result.setColor("red");
        add(result);

        // showMessage

        int x = -12;

        int y1 = 3;
        int y2 = 2;

        int y3 = -2;
        int y4 = -3;

        // showMessage

        new DialogOpener("showMessage(message)", () -> {
            Game.dialog.showMessage("Message");
            return null;
        }, x, y1);

        new DialogOpener("showMessage(message, title)", () -> {
            Game.dialog.showMessage("Message", "Title");
            return null;
        }, x, y2);

        // requestStringInput

        new DialogOpener("requestStringInput(message)",
                () -> Game.dialog.requestStringInput("Message"), x, y3);

        new DialogOpener("requestStringInput(message, title)",
                () -> Game.dialog.requestStringInput("Message", "Title"), x,
                y4);

        // requestYesNo

        x = 2;

        new DialogOpener("requestYesNo(message)",
                () -> Game.dialog.requestYesNo("Message"), x, y1);

        new DialogOpener("requestYesNo(message, title)",
                () -> Game.dialog.requestYesNo("Message", "Title"), x, y2);

        // requestOkCancel

        new DialogOpener("requestOkCancel(message)",
                () -> Game.dialog.requestOkCancel("Message"), x, y3);

        new DialogOpener("requestYesNo(message, title)",
                () -> Game.dialog.requestOkCancel("Message", "Title"), x, y4);
    }

    class DialogOpener extends Text implements MouseClickListener
    {
        Supplier<Object> supplier;

        public DialogOpener(String content, Supplier<Object> supplier, double x,
                double y)
        {
            super(content);
            this.supplier = supplier;
            add(this);
            setPosition(x, y);
        }

        @Override
        public void onMouseDown(Vector position, MouseButton button)
        {
            if (contains(position))
            {
                result.setContent(supplier.get());
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(new AllDialogsDemo());
    }
}
