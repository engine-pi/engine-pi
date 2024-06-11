package de.pirckheimer_gymnasium.engine_pi.demos.event;

import java.awt.event.KeyEvent;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;

class Scene1 extends Scene
{
    public Scene1()
    {
        createText("Scene 1");
    }
}

class Scene2 extends Scene
{
    public Scene2()
    {
        createText("Scene 2");
    }
}

public class SceneLaunchDemo
{
    public static Scene scene1 = new Scene1();

    public static Scene scene2 = new Scene2();

    public static void main(String[] args)
    {
        Game.addSceneLaunchListener((scene, previous) -> {
            System.out.println("launch " + scene);
        });
        Game.addKeyStrokeListener((event) -> {
            switch (event.getKeyCode())
            {
            case KeyEvent.VK_1 ->
            {
                Game.transitionToScene(scene1);
            }
            case KeyEvent.VK_2 ->
            {
                Game.transitionToScene(scene2);
            }
            }
        });
        Game.start(scene1);
    }
}
