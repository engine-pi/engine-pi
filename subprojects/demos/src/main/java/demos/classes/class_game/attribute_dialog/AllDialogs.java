package demos.classes.class_game.attribute_dialog;

import pi.Configuration;
import pi.Game;
import pi.actor.Text;

public class AllDialogs
{
    public static void main(String[] args)
    {
        Configuration.instantMode = true;

        Text result = new Text("Ergebnis");
        result.setPosition(0, 6);

        Text yesNoTitle = new Text("Ja/Nein");
        yesNoTitle.setPosition(0, 5);
        yesNoTitle.addMouseClickListener((v, e) -> {
            result.setContent(Game.dialog.requestYesNo("Message", "Title"));
        });

        Text yesNo = new Text("Ja/Nein");
        yesNo.setPosition(0, 4);
        yesNo.addMouseClickListener((v, e) -> {
            result.setContent(Game.dialog.requestYesNo("Message"));
        });
    }

}
