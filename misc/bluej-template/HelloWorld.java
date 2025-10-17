import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.actor.Text;

public class HelloWorld extends Scene
{
    public HelloWorld()
    {
        Text helloWorld = new Text("Hello, World!", 2);
        helloWorld.setColor("white");
        helloWorld.setCenter(0, 1);
        add(helloWorld);
        Game.debug();
    }

    public static void main(String[] args)
    {
        Game.start(new HelloWorld(), 400, 300);
    }
}
