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

        new DialogOpener("Ja/Nein (mit Titel)",
                () -> Game.dialog.requestYesNo("Message", "Title"), 0, 5);

        new DialogOpener("Ja/Nein (ohne Titel)",
                () -> Game.dialog.requestYesNo("Message"), 0, 4);
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
