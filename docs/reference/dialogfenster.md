# Dialogfenster

https://engine-alpha.org/wiki/Tutorials/Dialoge

<!-- Ziel dieses Tutorials

Nachdem du dieses Tutorial durchgearbeitet hast, kannst du die verschiedenen Dialog-Fenster einsetzen, die dir die Engine Alpha für die Interaktion mit dem Spieler zur Verfügung stellt. Beispielsweise kannst du es dem Spieler ermöglichen seinen Namen einzugeben. -->

tutorial nachrichtSchicken.png

tutorial eingabeFordern.png

tutorial frage.png
Überblick der Dialoge

Die Engine Alpha stellt dir in der Klasse Game vier Methoden für die Kommunikation mit dem Spieler zur Verfügung:
Methode 	Erklärung

 public void nachrichtSchicken(String nachricht)

	erzeugt ein modales (im Vordergrund stehendes) Fenster mit einer Nachricht. Der Spieler muss diese Nachricht durch einen Linksklick auf "Ok" bestätigen.

 public String eingabeFordern(String nachricht)

	erzeugt ein modales (im Vordergrund stehendes) Fenster mit einem erklärenden Text und einem Eingabe-Feld in das der Spieler hinein schreiben kann.

 public boolean frage(String frage)

	erzeugt ein modales (im Vordergrund stehendes) Fenster mit einer Frage, die mit JA oder NEIN per Linksklick beantwortet werden muss.


Achtung!

Alle Dialog-Fenster drängen sich kompromisslos in den Vordergrund und die
entsprechende Methode endet erst dann, wenn das Fenster wieder geschlossen wird.
<!-- Deshalb solltest du vor dem Aufruf dieser Methoden das Spiel unbedingt anhalten! -->


```java
public class AllDialogsDemo extends Scene
{
    Text result;

    public AllDialogsDemo()
    {
        result = new Text("Ergebnis");
        result.setPosition(0, 6);
        result.setColor("red");
        add(result);

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

        @Override
        public void onFrameUpdate(double pastTime)
        {
            if (contains(Game.getMousePosition()))
            {
                setColor("blue");
            }
            else
            {
                setColor("white");
            }
        }
    }

    public static void main(String[] args)
    {
        Game.start(new AllDialogsDemo());
    }
}
```

{{ demo('classes/class_game/attribute_dialog/AllDialogsDemo') }}
