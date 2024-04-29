package rocks.friedrich.engine_omega.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.event.KeyListener;

public class KeyListenerExample extends Scene implements KeyListener {
    Rectangle rectangle;

    public KeyListenerExample() {
        rectangle = new Rectangle(2, 2);
        rectangle.setColor(Color.BLUE);
        add(rectangle);
    }

    @Override
    public void onKeyDown(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                rectangle.moveBy(-1, 0);
                break;

            case KeyEvent.VK_RIGHT:
                rectangle.moveBy(1, 0);
                break;
        }
    }

    public static void main(String[] args) {
        Game.start(600, 400, new KeyListenerExample());
    }
}
