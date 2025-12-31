## repeat (Wiederholung)

Quellcode: [demos/event/RepeatDemo.java](https://github.com/engine-pi/engine-pi/blob/main/engine-pi-demos/src/main/java/de/pirckheimer_gymnasium/engine_pi_demos/event/RepeatDemo.java)

```java
public class RepeatDemo extends Scene
{
    public RepeatDemo()
    {
        setBackgroundColor("white");
        add(new CounterText());
    }

    private class CounterText extends Text
    {
        PeriodicTaskExecutor task;

        public CounterText()
        {
            super("0", 2);
            setCenter(0, 0);
            start();
            addKeyStrokeListener((e) -> {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                {
                    if (task == null)
                    {
                        start();
                    }
                    else
                    {
                        stop();
                    }
                }
            });
        }

        public void start()
        {
            task = repeat(1, (counter) -> {
                counter++;
                setContent(counter);
            });
        }

        public void stop()
        {
            task.unregister();
            task = null;
        }
    }

    public static void main(String[] args)
    {
        Game.start(new RepeatDemo());
    }
}
```
