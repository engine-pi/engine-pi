package rocks.friedrich.engine_omega.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;

public class StaticKeyListenerExample extends Scene {
    Rectangle rectangle;

    public StaticKeyListenerExample() {
        rectangle = new Rectangle(2, 2);
        rectangle.setColor(Color.BLUE);
        add(rectangle);
    }

    public void moveLeft() {
        rectangle.moveBy(-1, 0);
    }

    public void moveRight() {
        rectangle.moveBy(1, 0);
    }

    public static void main(String[] args) {
        StaticKeyListenerExample scene = new StaticKeyListenerExample();
        Game.start(600, 400, scene);

        Game.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        scene.moveLeft();
                        break;

                    case KeyEvent.VK_RIGHT:
                        scene.moveRight();
                        break;
                }

            }
        });
    }
}
