import pi.Controller;
import pi.Scene;
import pi.actor.Text;

public class HelloWorld extends Scene
{
    public HelloWorld()
    {
        add(new Text("Hello, World!").height(2).color("white").center(0, 1));
    }

    public static void main(String[] args)
    {
        Controller.instantMode(false);
        Controller.debug();
        Controller.start(new HelloWorld(), 400, 300);
    }
}
