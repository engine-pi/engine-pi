# `density` (Dichte)

Quellcode: [demos/physics/single_aspects/DensityDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/physics/single_aspects/DensityDemo.java)

```java
public class DensityDemo extends Scene implements KeyStrokeListener
{
    private final Rectangle ground;

    private final Circle[] circles;

    private final Text[] densityLables;

    public DensityDemo()
    {
        circles = new Circle[3];
        densityLables = new Text[3];
        int density = 10;
        int x = -5;
        for (int i = 0; i < 3; i++)
        {
            circles[i] = createCircle(x, density);
            densityLables[i] = createDensityLables(x, density);
            x += 5;
            density += 10;
        }
        setGravity(0, -9.81);
        ground = new Rectangle(20, 1);
        ground.setPosition(-10, -5);
        ground.makeStatic();
        add(ground);
    }

    private Circle createCircle(double x, double density)
    {
        Circle circle = new Circle(1);
        circle.setPosition(x, 5);
        circle.setDensity(density);
        circle.makeDynamic();
        add(circle);
        return circle;
    }

    private Text createDensityLables(int x, int density)
    {
        Text text = new Text(density + "", 1);
        text.setPosition(x, -7);
        text.makeStatic();
        add(text);
        return text;
    }

    @Override
    public void onKeyDown(KeyEvent e)
    {
        for (Circle circle : circles)
        {
            circle.applyImpulse(0, 100);
        }
    }
}
```
