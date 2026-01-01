package pi;

public class Tmp extends Scene
{
    public Tmp()
    {
        new Circle();
    }

    public static void main(String[] args)
    {
        Controller.start(new Tmp());
    }
}
