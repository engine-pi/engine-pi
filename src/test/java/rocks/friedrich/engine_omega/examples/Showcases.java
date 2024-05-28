/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha-examples/src/main/java/ea/example/showcase/Showcases.java
 *
 * Engine Alpha ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2024 Michael Andonie and contributors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package rocks.friedrich.engine_omega.examples;

import java.awt.Color;
import java.awt.Font;
import java.util.function.Supplier;

import rocks.friedrich.engine_omega.Game;
import rocks.friedrich.engine_omega.Scene;
import rocks.friedrich.engine_omega.Vector;
import rocks.friedrich.engine_omega.actor.Actor;
import rocks.friedrich.engine_omega.actor.Rectangle;
import rocks.friedrich.engine_omega.actor.Text;
import rocks.friedrich.engine_omega.event.FrameUpdateListener;
import rocks.friedrich.engine_omega.event.MouseButton;
import rocks.friedrich.engine_omega.event.MouseClickListener;

/**
 * Diese Klasse beschreibt die Kontroll-Scene, in der man Demos auswählen und
 * starten kann.
 */
public class Showcases extends Scene
{
    public static final int WIDTH = 1240, HEIGHT = 812;

    /**
     * State für die interne Textbox
     */
    private enum TextboxState
    {
        NORMAL, PRESSED, HOVER;

        public Color toColor()
        {
            switch (this)
            {
            case NORMAL:
                return BOX_NORMAL;

            case HOVER:
                return BOX_HOVER;

            case PRESSED:
                return BOX_PRESSED;

            default:
                return null;
            }
        }
    }

    private static final Color BOX_NORMAL = new Color(111, 119, 130);

    private static final Color BOX_HOVER = new Color(167, 173, 200);

    private static final Color BOX_PRESSED = new Color(251, 248, 255);

    private static final int BOX_WIDTH = 300;

    private static final int BOX_HEIGHT = 48;

    /**
     * Textbox-Element. Besteht aus einem Text mit Hintergrund-Box. Beim
     * Mausklick auf die Box wird die zugewiesene Scene gestartet.
     */
    private class TextBox implements MouseClickListener, FrameUpdateListener
    {
        // Box für den Text
        private final Rectangle box;

        // Der sichtbare Text
        private final Text text;

        // Runnable, das die Scene.
        private final Supplier<Scene> sceneCreator;

        // Der aktuelle State der TextBox
        private TextboxState state = TextboxState.NORMAL;

        public TextBox(String content, Supplier<Scene> sceneCreator, float x,
                float y)
        {
            box = new Rectangle(BOX_WIDTH, BOX_HEIGHT);
            box.setBorderRadius(1f);
            box.setPosition(x, y);
            text = new Text(content, 24);
            text.setStyle(Font.BOLD);
            text.setColor(Color.BLACK);
            box.setLayerPosition(0);
            text.setLayerPosition(1);
            text.setPosition(x, y);
            text.moveBy(20, 10);
            this.sceneCreator = sceneCreator;
            updateUI();
        }

        /**
         * Stellt sicher, dass das UI optisch korrekt dargestellt wird. Wird bei
         * jedem State-Wechsel aufgerufen und ändert Farben im UI.
         */
        private void updateUI()
        {
            box.setColor(state.toColor());
        }

        @Override
        public void onMouseDown(Vector point, MouseButton mouseButton)
        {
            if (box.contains(point))
            {
                // CLICKED ME
                state = TextboxState.PRESSED;
                updateUI();
            }
        }

        @Override
        public void onMouseUp(Vector point, MouseButton mouseButton)
        {
            if (box.contains(point))
            {
                // CLICKED ME
                if (state == TextboxState.PRESSED)
                {
                    // I was pressed before ==> CHANGE SCENE
                    Scene scene = sceneCreator.get();
                    Game.transitionToScene(scene);
                }
            }
        }

        public Actor[] getActors()
        {
            return new Actor[] { box, text };
        }

        @Override
        public void onFrameUpdate(double deltaSeconds)
        {
            Vector mousePosition = Showcases.this.getMousePosition();
            if (box.contains(mousePosition))
            {
                if (state != TextboxState.PRESSED)
                {
                    state = TextboxState.HOVER;
                }
            }
            else
            {
                state = TextboxState.NORMAL;
            }
            updateUI();
        }
    }

    /**
     * Count der aktuellen Buttons in der Demo.
     */
    private int buttonCount = 0;

    public Showcases()
    {
        Text title = new Text("Engine Alpha: 4.0 Feature Showcase", 48);
        title.setColor(Color.WHITE);
        Text subtitle = new Text(
                "Knopfdruck startet Demo. Escape-Taste bringt dich ins Menü zurück",
                24);
        subtitle.setColor(Color.GRAY);
        title.setPosition(-500, 100);
        subtitle.setPosition(-500, 50);
        add(title, subtitle);
    }

    /**
     * Fügt eine Scene der Showcase hinzu.
     *
     * @param sceneSupplier Die hinzuzufügende Scene. Gewrappt in einen Creator,
     *                      damit keine Ressourcen aufgebraucht werden, bis die
     *                      Scene tatsächlich angefragt wird.
     * @param title         Der Titel für die Textbox
     */
    public void addScene(Supplier<Scene> sceneSupplier, String title)
    {
        buttonCount++;
        boolean left = buttonCount % 2 == 1;
        int row = (buttonCount - 1) / 2;
        TextBox button = new TextBox(title, sceneSupplier, left ? -500 : -150,
                -60 + -1 * row * (BOX_HEIGHT + 5));
        add(button.getActors());
        addMouseClickListener(button);
        addFrameUpdateListener(button);
    }

    /**
     * Main-Methode. Startet die Demo.
     */
    public static void main(String[] args)
    {
        Showcases mainscene = new Showcases();
        mainscene.getCamera().setZoom(1);
        // mainscene.addScene(() -> new BallThrow(mainscene),
        // "Einfacher Ballwurf");
        // mainscene.addScene(() -> new PhysicsSandbox(mainscene, WIDTH,
        // HEIGHT),
        // "Kräfte-Sandbox");
        // mainscene.addScene(() -> new JointDemo(mainscene),
        // "Joints in der Engine");
        // mainscene.addScene(() -> new MarbleDemo(mainscene), "Murmel-Demo");
        // mainscene.addScene(() -> new DudeDemo(mainscene), "DudeDemo");
        // mainscene.addScene(() -> new Billard(mainscene), "Billard");
        // mainscene.addScene(() -> new Particles(mainscene), "Particles");
        // mainscene.addScene(() -> new CarDemo(mainscene), "Car Demo");
        Game.setDebug(true);
        Game.setExitOnEsc(false);
        Game.setTitle("Engine Alpha: Feature Showcase");
        Game.start(WIDTH, HEIGHT, mainscene);
    }
}
