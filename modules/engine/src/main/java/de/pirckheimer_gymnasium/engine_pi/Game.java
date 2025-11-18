/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/Game.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2019 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.debug.DebugConfiguration;
import de.pirckheimer_gymnasium.engine_pi.debug.MainAnimation;
import de.pirckheimer_gymnasium.engine_pi.event.DefaultControl;
import de.pirckheimer_gymnasium.engine_pi.event.DefaultListener;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListenerRegistration;
import de.pirckheimer_gymnasium.engine_pi.event.MouseButton;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener;
import de.pirckheimer_gymnasium.engine_pi.event.MouseScrollEvent;
import de.pirckheimer_gymnasium.engine_pi.event.MouseScrollListener;
import de.pirckheimer_gymnasium.engine_pi.event.SceneLaunchListener;
import de.pirckheimer_gymnasium.engine_pi.graphics.RenderPanel;
import de.pirckheimer_gymnasium.engine_pi.util.FileUtil;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;
import de.pirckheimer_gymnasium.jbox2d.common.Settings;

/**
 * Diese Klasse gibt Zugriff auf das aktuelle <b>Spiel</b>.
 *
 * @author Michael Andonie
 * @author Niklas Keller
 */
@SuppressWarnings("StaticVariableOfConcreteClass")
public final class Game
{
    static
    {
        System.setProperty("sun.java2d.opengl", "true"); // ok
        System.setProperty("sun.java2d.d3d", "true"); // ok
        System.setProperty("sun.java2d.noddraw", "false"); // set false if
                                                           // possible, linux
        System.setProperty("sun.java2d.pmoffscreen", "true"); // set true if
                                                              // possible, linux
        System.setProperty("sun.java2d.ddoffscreen", "true"); // ok, windows
        System.setProperty("sun.java2d.ddscale", "true"); // ok, hardware
                                                          // accelerated image
                                                          // scaling on windows
        /**
         * Damit die Umrisse zum Beispiel bei der Klasse Star richtig gezeichnet
         * wird.
         */
        Settings.maxPolygonVertices = 20;
    }

    /**
     * Breite des Fensters in Pixel.
     */
    private static int width;

    /**
     * Höhe des Fensters in Pixel.
     */
    private static int height;

    private static int pixelMultiplication = 1;

    /**
     * Eigentliches Fenster des Spiels.
     */
    private static final Frame frame = new Frame("Engine Pi");

    /**
     * Die Zeichenfläche, in der alle Figuren eingezeichnet werden.
     */
    private static RenderPanel renderPanel;

    /**
     * Aktuelle Szene des Spiels.
     */
    private static Scene scene = new Scene();

    private static GameLoop loop;

    /**
     * Speichert den Zustand der einzelnen Tasten der Tastatur. Ist ein Wert
     * <code>true</code>, so ist die entsprechende Taste gedrückt, sonst ist der
     * Wert <code>false</code>.
     */
    private static final Collection<Integer> pressedKeys = ConcurrentHashMap
            .newKeySet();

    private static DefaultListener defaultControl = new DefaultControl();

    /**
     * Letzte Mausposition.
     */
    private static java.awt.Point mousePosition;

    private static final EventListeners<KeyStrokeListener> keyStrokeListeners = new EventListeners<>();

    private static final EventListeners<MouseClickListener> mouseClickListeners = new EventListeners<>();

    private static final EventListeners<MouseScrollListener> mouseScrollListeners = new EventListeners<>();

    private static final EventListeners<SceneLaunchListener> sceneLaunchListeners = new EventListeners<>();

    /**
     * Setzt den Titel des Spielfensters.
     *
     * @param title Titel des Spielfensters.
     */
    @API
    public static void setTitle(String title)
    {
        frame.setTitle(title);
    }

    /**
     * Setzt den Wert der Pixelvervielfältigung.
     *
     * @param pixelMultiplication Der Wert der Pixelvervielfältigung.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.util.ImageUtil#multiplyPixel(BufferedImage,
     *     int)
     *
     * @since 0.25.0
     */
    public static void setPixelMultiplication(int pixelMultiplication)
    {
        Game.pixelMultiplication = pixelMultiplication;
    }

    /**
     * Gibt den Wert der Pixelvervielfältigung zurück.
     *
     * @return Der Wert der Pixelvervielfältigung.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.util.ImageUtil#multiplyPixel(BufferedImage,
     *     int)
     * @see Camera#getMeter()
     * @see de.pirckheimer_gymnasium.engine_pi.actor.Animation#createFromSpritesheet
     *
     * @since 0.25.0
     */
    public static int getPixelMultiplication()
    {
        return pixelMultiplication;
    }

    /**
     * Gibt wahr zurück, wenn die Pixelvervielfältigung aktiviert ist.
     *
     * @return Wahr, wenn die Pixelvervielfältigung aktiviert ist.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.util.ImageUtil#multiplyPixel(BufferedImage,
     *     int)
     *
     * @since 0.25.0
     */
    public static boolean isPixelMultiplication()
    {
        return pixelMultiplication > 1;
    }

    /**
     * Startet das Spiel in einem Fenster mit der angegebenen <b>Breite</b>,
     * <b>Höhe</b> und <b>Pixelvervielfältigung</b>.
     *
     * @param scene Die <b>Szene</b>, mit der das Spiel gestartet wird.
     * @param width Die <b>Breite</b> des Zeichenbereichs in Pixel.
     * @param height Die <b>Höhe</b> des Zeichenbereichs in Pixel.
     * @param pixelMultiplication Wie oft ein <b>Pixel vervielfältigt</b> werden
     *     soll.
     *
     * @return Die Szene, mit der das Spiel gestartet wurde.
     *
     * @see #setPixelMultiplication(int)
     *
     * @since 0.26.0 parameter pixelMultiplication
     */
    @API
    public static Scene start(Scene scene, int width, int height,
            int pixelMultiplication)
    {
        if (renderPanel != null)
        {
            throw new IllegalStateException(
                    "Game.start wurde bereits ausgeführt und kann nur einmal ausgeführt werden");
        }
        setPixelMultiplication(pixelMultiplication);
        width *= pixelMultiplication;
        height *= pixelMultiplication;
        Game.width = width;
        Game.height = height;
        Game.scene = scene;
        renderPanel = new RenderPanel(width, height);
        frame.setResizable(false);
        frame.add(renderPanel);
        // pack() already allows to create the buffer strategy for rendering
        // (but not on Windows?)
        frame.pack();
        if (DebugConfiguration.windowPosition != Direction.NONE)
        {
            Game.setWindowPosition(DebugConfiguration.windowPosition);
        }
        else
        {
            // Zentriert das Fenster auf dem Bildschirm -
            // https://stackoverflow.com/a/144893/2373138
            frame.setLocationRelativeTo(null);
        }
        frame.setVisible(true);
        renderPanel.allocateBuffers();
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Game.exit();
            }
        });
        KeyListener keyListener = new KeyListener();
        frame.addKeyListener(keyListener);
        renderPanel.addKeyListener(keyListener);
        renderPanel.setFocusable(true);
        MouseAdapter mouseListener = new MouseListener();
        renderPanel.addMouseMotionListener(mouseListener);
        renderPanel.addMouseListener(mouseListener);
        renderPanel.addMouseWheelListener(Game::enqueueMouseScrollEvent);
        frame.setIconImage(Resources.images.get("logo/logo.png"));
        mousePosition = new java.awt.Point(width / 2, height / 2);
        Thread mainThread = new Thread(Game::run,
                "de.pirckheimer_gymnasium.engine_pi.main");
        mainThread.start();
        mainThread.setPriority(Thread.MAX_PRIORITY);
        if (defaultControl != null)
        {
            setDefaultControl(defaultControl);
        }
        return scene;
    }

    /**
     * Startet das Spiel in einem Fenster mit der angegebenen <b>Breite</b>,
     * <b>Höhe</b>
     *
     * @param width Die Breite des Zeichenbereichs in Pixel.
     * @param height Die Höhe des Zeichenbereichs in Pixel.
     * @param scene Die Szene, mit der das Spiel gestartet wird.
     *
     * @return Die Szene, mit der das Spiel gestartet wurde.
     *
     * @see #getPixelMultiplication()
     *
     * @deprecated use {@link #start(Scene, int, int)}
     */
    public static Scene start(int width, int height, Scene scene)
    {
        return start(scene, width, height, getPixelMultiplication());
    }

    /**
     * Startet das Spiel in einem Fenster mit der angegebenen <b>Breite</b> und
     * <b>Höhe</b>.
     *
     * @param scene Die Szene, mit der das Spiel gestartet wird.
     *
     * @return Die Szene, mit der das Spiel gestartet wurde.
     */
    @API
    public static Scene start(Scene scene, int width, int height)
    {
        return start(width, height, scene);
    }

    /**
     * Startet das Spiel in einem Fenster mit den Abmessungen {@code 800x600}
     * Pixel. Es wird automatische eine Szene erzeugt und diese zur weiteren
     * Verwendung an eine <b>Lambda-Funktion</b> übergeben.
     *
     * <pre>{@code
     * Game.start((scene) -> {
     *     Line line = new Line(1, 1, 4, 5);
     *     line.setColor("grün");
     *     scene.add(line);
     * });
     * }</pre>
     *
     * @param sceneConsumer Eine Lamda-Funktion, die als Eingabe-Parameter die
     *     erzeugte Szene erhält.
     *
     * @return Die Szene, mit der das Spiel gestartet wurde.
     */
    public static Scene start(Consumer<Scene> sceneConsumer)
    {
        Scene scene = new Scene();
        sceneConsumer.accept(scene);
        return start(scene);
    }

    /**
     * Startet das Spiel in einem Fenster mit den Abmessungen {@code 800x600}
     * Pixel.
     *
     * @param scene Die Szene, mit der das Spiel gestartet wird.
     *
     * @return Die Szene, mit der das Spiel gestartet wurde.
     */
    @API
    public static Scene start(Scene scene)
    {
        return start(800, 600, scene);
    }

    /**
     * Startet das Spiel in einem Fenster mit den Abmessungen {@code 800x600}
     * Pixel und der Begrüßungsanimation.
     *
     * @return Die erzeugte Szene, mit der das Spiel gestartet wurde.
     *
     * @see MainAnimation
     */
    @API
    public static Scene start()
    {
        return start(new MainAnimation());
    }

    /**
     * Wenn das Spiel noch nicht läuft, wird das Spiel gestartet, ansonsten wird
     * zur gegeben Szene gewechselt.
     *
     * @since 0.40.0
     */
    public static Scene startSafe(Scene scene)
    {
        if (!isRunning())
        {
            Game.start(scene);
        }
        else
        {
            transitionToScene(scene);
        }
        return scene;
    }

    /**
     * Wechselt die aktuelle Szene.
     *
     * @param scene Die Szene, zu der gewechselt werden soll. Wird
     *     <code>null</code> übergeben, wird eine neue Szene erstellt.
     */
    @API
    public static void transitionToScene(Scene scene)
    {
        Scene previous = getActiveScene();
        if (scene == previous)
        {
            return;
        }
        final Scene next;
        next = Objects.requireNonNullElseGet(scene, Scene::new);
        loop.enqueue(() -> {
            sceneLaunchListeners.invoke(
                    (listener) -> listener.onSceneLaunch(next, previous));
            Game.scene = next;
        });
    }

    private static void run()
    {
        loop = new GameLoop(renderPanel, Game::getActiveScene, Game::isDebug);
        sceneLaunchListeners.invoke((listener) -> listener
                .onSceneLaunch(Game.getActiveScene(), null));
        loop.run();
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }

    /**
     * Gibt die momentan registrierten, grundlegenden Maus- und
     * Tastatur-Steuermöglichkeiten zurück.
     *
     * @return Die registrierten, grundlegenden Maus- und
     *     Tastatur-Steuermöglichkeiten.
     */
    public static DefaultListener getDefaultControl()
    {
        return defaultControl;
    }

    /**
     * Registriert grundlegende Maus- und Tastatur-Steuermöglichkeiten.
     *
     * @param control Die grundlegenden Maus- und Tastatur-Steuermöglichkeiten.
     */
    public static void setDefaultControl(DefaultListener control)
    {
        defaultControl = control;
        if (control != null)
        {
            addFrameUpdateListener(defaultControl);
            addKeyStrokeListener(defaultControl);
            addMouseClickListener(defaultControl);
            addMouseScrollListener(defaultControl);
        }
    }

    /**
     * Meldet die grundlegenden Maus- und Tastatur-Steuermöglichkeiten ab.
     *
     * @see DefaultControl Die grundlegenden Maus- und
     *     Tastatur-Steuermöglichkeiten.
     */
    public static void removeDefaultControl()
    {
        if (defaultControl != null)
        {
            removeFrameUpdateListener(defaultControl);
            removeKeyStrokeListener(defaultControl);
            removeMouseClickListener(defaultControl);
            removeMouseScrollListener(defaultControl);
            defaultControl = null;
        }
    }

    /**
     * Diese Methode wird immer dann ausgeführt, wenn das Mausrad bewegt wurde
     * und ein {@code MouseWheelEvent} registriert wurde.
     *
     * @param event das Event.
     */
    private static void enqueueMouseScrollEvent(MouseWheelEvent event)
    {
        MouseScrollEvent mouseScrollEvent = new MouseScrollEvent(
                event.getPreciseWheelRotation());
        loop.enqueue(() -> {
            mouseScrollListeners.invoke(
                    (listener) -> listener.onMouseScrollMove(mouseScrollEvent));
            scene.invokeMouseScrollListeners(mouseScrollEvent);
        });
    }

    /**
     * Registriert einen statischen, d. h. globalen Beobachter, der auf
     * Bildaktualisierungen reagiert.
     *
     * @param listener Der Beobachter, der auf Bildaktualisierungen reagiert.
     *
     * @return Der Rückgabewert ist mit dem Eingabeparameter identisch. Dieser
     *     Wert kann dann mit dem Datentyp {@link FrameUpdateListener} einer
     *     lokalen Variablen bzw. einem Attribut zugewiesen werden und später
     *     zum Abmelden verwendet werden.
     *
     * @author Josef Friedrich
     */
    public static FrameUpdateListener addFrameUpdateListener(
            FrameUpdateListener listener)
    {
        if (loop != null)
        {
            loop.getFrameUpdateListener().add(listener);
        }
        else
        {
            addSceneLaunchListener((next, previous) -> {
                if (previous == null)
                {
                    loop.getFrameUpdateListener().add(listener);
                }
            });
        }
        return listener;
    }

    /**
     * Meldet einen statischen, d. h. globalen Beobachter ab, der auf
     * Bildaktualisierungen reagiert.
     *
     * @author Josef Friedrich
     *
     * @param listener Der Beobachter, der auf Bildaktualisierungen reagiert.
     */
    public static void removeFrameUpdateListener(FrameUpdateListener listener)
    {
        loop.getFrameUpdateListener().remove(listener);
    }

    /**
     * Fügt einen statisch {@link KeyStrokeListener} hinzu, d. h. dieser
     * KeyStrokeListener gilt global über das ganze Spiel und ist unabhängig von
     * der aktuellen Szene.
     *
     * <p>
     * Der {@link KeyStrokeListener} kann auf mehrere Arten implementiert
     * werden:
     * </p>
     *
     * <ol>
     * <li>Als normale Klasse:
     *
     * <pre>{@code
     * class MyKeyStrokelistener implements KeyStrokeListener
     * {
     *     @Override
     *     public void onKeyDown(KeyEvent e)
     *     {
     *         // Code here
     *     }
     * }
     * obj.addKeyStrokeListener(new MyKeyStrokelistener());
     * }</pre>
     *
     * </li>
     *
     * <li>Als anonyme Klasse:
     *
     * <pre>{@code
     * obj.addKeyStrokeListener(new KeyStrokeListener()
     * {
     *     @Override
     *     public void onKeyDown(KeyEvent e)
     *     {
     *         // Code here
     *     }
     * });
     * }</pre>
     *
     * </li>
     *
     * <li>Oder als Lambda-Ausdruck:
     *
     * <pre>{@code
     * obj.addKeyStrokeListener(e -> {
     *     // Code here
     * });
     * }</pre>
     *
     * </li>
     * </ol>
     *
     * @author Josef Friedrich
     *
     * @param listener Ein Objekt der Klasse {@link KeyStrokeListener}.
     *
     * @see KeyStrokeListenerRegistration#addKeyStrokeListener(KeyStrokeListener)
     */
    public static void addKeyStrokeListener(KeyStrokeListener listener)
    {
        keyStrokeListeners.add(listener);
    }

    /**
     * Entfernt einen statischen {@link KeyStrokeListener} vom Objekt, d. h.
     * einen KeyStrokeListener, der global für das ganze Spiel gilt.
     *
     * @author Josef Friedrich
     *
     * @param listener Ein Objekt der Klasse {@link KeyStrokeListener}.
     *
     * @see KeyStrokeListenerRegistration#removeKeyStrokeListener(KeyStrokeListener)
     */
    public static void removeKeyStrokeListener(KeyStrokeListener listener)
    {
        keyStrokeListeners.remove(listener);
    }

    public static void addMouseClickListener(MouseClickListener listener)
    {
        mouseClickListeners.add(listener);
    }

    public static void removeMouseClickListener(MouseClickListener listener)
    {
        mouseClickListeners.remove(listener);
    }

    public static void addMouseScrollListener(MouseScrollListener listener)
    {
        mouseScrollListeners.add(listener);
    }

    public static void removeMouseScrollListener(MouseScrollListener listener)
    {
        mouseScrollListeners.remove(listener);
    }

    public static void addSceneLaunchListener(SceneLaunchListener listener)
    {
        sceneLaunchListeners.add(listener);
    }

    public static void removeSceneLaunchListener(SceneLaunchListener listener)
    {
        sceneLaunchListeners.remove(listener);
    }

    /**
     * Gibt die gerade aktive Szene an.
     *
     * @return Die gerade aktive Szene. Wurde das Spiel noch nicht gestartet,
     *     ist die Rückgabe <code>null</code>.
     *
     * @see de.pirckheimer_gymnasium.engine_pi.Scene
     */
    @API
    public static Scene getActiveScene()
    {
        return scene;
    }

    /**
     * Gibt an, ob eine bestimmte Taste derzeit heruntergedrückt ist.
     *
     * @param keyCode Die zu testende Taste als Key-Code (also z.B.
     *     <code>KeyEvent.VK_D</code>).
     *
     * @return <code>true</code>, wenn die zu testende Taste gerade
     *     heruntergedrückt ist, sonst <code>false</code>.
     *
     * @see KeyEvent#getKeyCode()
     * @see java.awt.event.KeyEvent
     */
    @API
    public static boolean isKeyPressed(int keyCode)
    {
        return pressedKeys.contains(keyCode);
    }

    /**
     * Gibt an, ob die Engine gerade läuft. Die Engine läuft, sobald es ein
     * sichtbares Fenster gibt. Dieses läuft, sobald {@link #start(Scene)}
     * ausgeführt wurde.
     *
     * @return <code>true</code>, wenn das Spiel läuft, sonst
     *     <code>false</code>.
     *
     * @see #start(int, int, Scene)
     */
    @API
    public static boolean isRunning()
    {
        return frame.isVisible();
    }

    /**
     * Setzt die <b>Größe</b> des Engine-<b>Fensters</b>.
     *
     * @param width Die neue <b>Breite</b> des Engine-Fensters in Pixel.
     * @param height Die neue <b>Höhe</b> des Engine-Fensters in Pixel.
     *
     * @see #getWindowSize()
     * @see #setWindowPosition(int, int)
     */
    @API
    public static void setWindowSize(int width, int height)
    {
        if (width <= 0 || height <= 0)
        {
            throw new RuntimeException(
                    "Die Fenstergröße kann nicht kleiner/gleich 0 sein. "
                            + "Eingabe war: " + width + " - " + height + ".");
        }
        if (renderPanel == null)
        {
            throw new RuntimeException(
                    "Fenster-Resizing ist erst möglich, nachdem Game.start ausgeführt wurde.");
        }
        int diffX = (width - Game.width) / 2;
        int diffY = (height - Game.height) / 2;
        Game.width = width;
        Game.height = height;
        renderPanel.setSize(width, height);
        renderPanel.setPreferredSize(new Dimension(width, height));
        frame.pack();
        frame.setLocation(frame.getLocation().x - diffX,
                frame.getLocation().y - diffY);
    }

    /**
     * Gibt die Fenstergröße <b>in Pixel</b> aus.
     *
     * @return Ein Vektor-Objekt, dessen Höhe und Breite mit der Fensterhöhe und
     *     -breite übereinstimmt.
     *
     * @see #setWindowPosition(int, int)
     * @see #setWindowSize(int, int)
     */
    @API
    public static Vector getWindowSize()
    {
        return new Vector(width, height);
    }

    /**
     * Setzt das Spielfenster an eine neue <b>Position</b>.
     *
     * <p>
     * Standardmäßig ist das Fenster mittig positioniert. Die Parameter x und y
     * beziehen sich auf die linke obere Ecke der neuen Fenster-Position.
     * </p>
     *
     * @param x Die x-Koordinate der linken oberen Ecke des Fensters in Pixel.
     * @param y Die y-Koordinate der linken oberen Ecke des Fensters in Pixel.
     *
     * @see #getWindowSize()
     * @see #setWindowSize(int, int)
     */
    @API
    public static void setWindowPosition(int x, int y)
    {
        frame.setLocation(x, y);
    }

    /**
     * Setzt das Spielfenster an eine neue Position.
     *
     * @param direction Wo das Spielfeld auf dem Bildschirm angezeigt werden
     *     sollen.
     *     <ul>
     *     <li>{@link Direction#UP}: oben mittig</li>
     *     <li>{@link Direction#UP_RIGHT}: oben rechts</li>
     *     <li>{@link Direction#RIGHT}: rechts mittig</li>
     *     <li>{@link Direction#DOWN_RIGHT}: unten rechts</li>
     *     <li>{@link Direction#DOWN}: unten mittig</li>
     *     <li>{@link Direction#DOWN_LEFT}: unten links</li>
     *     <li>{@link Direction#LEFT}: links</li>
     *     <li>{@link Direction#UP_LEFT}: oben links</li>
     *     <li>{@link Direction#NONE}: mittig</li>
     *     </ul>
     *
     * @see DebugConfiguration#windowPosition
     */
    public static void setWindowPosition(Direction direction)
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        Vector vector = Game.getWindowSize();
        int windowWidth = (int) vector.getX();
        int windowHeight = (int) vector.getY();
        int diffWidth = screenWidth - windowWidth;
        int diffHeight = screenHeight - windowHeight;
        switch (direction)
        {
        case UP -> Game.setWindowPosition(diffWidth / 2, 0);
        case UP_RIGHT -> Game.setWindowPosition(diffWidth, 0);
        case RIGHT -> Game.setWindowPosition(diffWidth, diffHeight / 2);
        case DOWN_RIGHT -> Game.setWindowPosition(diffWidth, diffHeight);
        case DOWN -> Game.setWindowPosition(diffWidth / 2, diffHeight);
        case DOWN_LEFT -> Game.setWindowPosition(0, diffHeight);
        case LEFT -> Game.setWindowPosition(0, diffHeight / 2);
        case UP_LEFT -> Game.setWindowPosition(0, 0);
        case NONE ->
            // zentrieren
            Game.setWindowPosition(diffWidth / 2, diffHeight / 2);
        }
    }

    /**
     * Beendet das Spiel.
     *
     * <p>
     * Das Fenster wird geschlossen, alle belegten Ressourcen werden freigegeben
     * und die virtuelle Maschine wird beendet.
     * </p>
     */
    @API
    public static void exit()
    {
        System.exit(0);
    }

    /**
     * Gibt eine Nachricht in einem modalen Dialogfenster aus. Der Dialog ist
     * über {@link javax.swing.JOptionPane} implementiert.
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     */
    @API
    public static void showMessage(String message, String title)
    {
        JOptionPane.showMessageDialog(frame, message, title,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Öffnet ein modales Dialogfenster, in dem der Nutzer zur Eingabe von Text
     * in einer Zeile aufgerufen wird. Der Dialog ist über
     * {@link javax.swing.JOptionPane} implementiert.
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers. Ist <code>null</code>, wenn der Nutzer
     *     den Dialog abgebrochen hat.
     */
    @API
    public static String requestStringInput(String message, String title)
    {
        return JOptionPane.showInputDialog(frame, message, title,
                JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Öffnet ein modales Dialogfenster mit Ja/Nein-Buttons. Der Dialog ist über
     * {@link javax.swing.JOptionPane} implementiert.
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers:
     *     <ul>
     *     <li>Ja → <code>true</code></li>
     *     <li>Nein → <code>false</code></li>
     *     <li>Abbruch (= Dialog manuell schließen) → <code>false</code></li>
     *     </ul>
     */
    @API
    public static boolean requestYesNo(String message, String title)
    {
        return JOptionPane.showConfirmDialog(frame, message, title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION;
    }

    /**
     * Öffnet ein modales Dialogfenster mit OK/Abbrechen-Buttons. Der Dialog ist
     * über {@link javax.swing.JOptionPane} implementiert.
     *
     * @param message Der Inhalt der Botschaft im Dialogfenster.
     * @param title Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers:
     *     <ul>
     *     <li>OK → <code>true</code></li>
     *     <li>Abbrechen → <code>false</code></li>
     *     <li>Abbruch (= Dialog manuell schließen) → <code>false</code></li>
     *     </ul>
     */
    @API
    public static boolean requestOkCancel(String message, String title)
    {
        return JOptionPane.showConfirmDialog(frame, message, title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
    }

    /**
     * Finde die Position des Mausklicks auf der Zeichenebene. Die Position wird
     * relativ zum Ursprung des {@link RenderPanel}-Canvas angegeben. Die
     * Mausklick-Position muss mit dem Zoom-Wert verrechnet werden.
     *
     * @hidden
     */
    @Internal
    public static Vector convertMousePosition(Scene scene,
            java.awt.Point mousePosition)
    {
        Camera camera = scene.getCamera();
        double zoom = camera.getMeter();
        double rotation = camera.getRotation();
        Vector position = camera.getCenter();
        return new Vector(
                position.getX() + ((Math.cos(Math.toRadians(rotation))
                        * (mousePosition.x - width / 2.0)
                        + Math.sin(Math.toRadians(rotation))
                                * (mousePosition.y - height / 2.0)))
                        / zoom,
                position.getY() + ((Math.sin(Math.toRadians(rotation))
                        * (mousePosition.x - width / 2.0)
                        - Math.cos(Math.toRadians(rotation))
                                * (mousePosition.y - height / 2.0)))
                        / zoom);
    }

    /**
     * @hidden
     */
    @Internal
    public static java.awt.Point getMousePositionInFrame()
    {
        return mousePosition;
    }

    /**
     * Gibt die Zeichenfläche, in der alle Figuren eingezeichnet werden, zurück.
     *
     * @return Die Zeichenfläche, in der alle Figuren eingezeichnet werden.
     *
     * @since 0.38.0
     */
    public static RenderPanel getRenderPanel()
    {
        return renderPanel;
    }

    /**
     * Gibt die Position der Maus in der aktuellen Szene als Vektor in Meter
     * relativ zum Koordinatensystem zurück.
     *
     * <p>
     * Der Positions-Vektor ist in der Einheit Meter angegeben und bezieht sich
     * auf einen Punkt des zu Grunde liegenden Koordinatensystems.
     * </p>
     *
     * @return Die Position der Maus in der aktuellen Szene als Vektor in Meter.
     *
     * @see Scene#getMousePosition()
     */
    @API
    public static Vector getMousePosition()
    {
        return scene.getMousePosition();
    }

    /**
     * Setzt, ob die Engine im Debug-Modus ausgeführt werden soll.
     *
     * @param value ist dieser Wert <code>true</code>, wird die Engine ab sofort
     *     im Debug-Modus ausgeführt. Hierdurch werden mehr Informationen beim
     *     Ausführen der Engine angegeben, zum Beispiel ein grafisches Raster
     *     und mehr Logging-Informationen. Dies ist hilfreich für das Debugging
     *     des eigenen Spiels.
     *
     * @see #isDebug()
     */
    @API
    public static void setDebug(boolean value)
    {
        DebugConfiguration.enableDebugMode = value;
    }

    /**
     * Gibt an, ob die Engine gerade im Debug-Modus ausgeführt wird.
     *
     * @return ist dieser Wert <code>true</code>, wird die Engine gerade im
     *     Debug-Modus ausgeführt. Sonst ist der Wert <code>false</code>.
     *
     * @see #setDebug(boolean)
     */
    @API
    public static boolean isDebug()
    {
        return DebugConfiguration.enableDebugMode;
    }

    /**
     * Schaltet je nach Zustand den Debug-Modus an oder aus. Ist der Debug-Modus
     * an, wird er ausgeschaltet, ist er aus so wird er angeschaltet.
     */
    @API
    public static void toggleDebug()
    {
        Game.setDebug(!Game.isDebug());
    }

    /**
     * Aktiviert den Entwicklungsmodus.
     *
     * @since 0.27.0
     */
    @API
    public static void debug()
    {
        DebugConfiguration.enableDebugMode = true;
    }

    /**
     * Setzt, ob die Figuren gezeichnet werden sollen.
     *
     * @see #getRenderActors()
     */
    @API
    public static void setRenderActors(boolean value)
    {
        DebugConfiguration.renderActors = value;
    }

    /**
     * Gibt an, ob die Figuren gezeichnet werden sollen.
     *
     * @see #setRenderActors(boolean)
     */
    @API
    public static boolean getRenderActors()
    {
        return DebugConfiguration.renderActors;
    }

    /**
     * @see #setRenderActors(boolean)
     * @see #getRenderActors()
     */
    public static void toggleRenderActors()
    {
        Game.setRenderActors(!Game.getRenderActors());
    }

    /**
     * Gibt an, ob die laufende Instanz der Engine gerade verbose Output gibt.
     *
     * @return ist dieser Wert <code>true</code>, werden extrem ausführliche
     *     Logging-Informationen gespeichert. Sonst ist der Wert
     *     <code>false</code>.
     *
     * @see #setVerbose(boolean)
     */
    @API
    public static boolean isVerbose()
    {
        return DebugConfiguration.verbose;
    }

    /**
     * Setzt, ob die aktuell laufende Instanz der Engine verbose Output geben
     * soll.
     *
     * @param value ist dieser Wert <code>true</code>, so wird ein äußerst
     *     ausführliches Log über die Funktionalität der Engine geführt. Dies
     *     ist hauptsächlich für das Debugging an der Engine selbst notwendig.
     *
     * @see #isVerbose()
     * @see #setDebug(boolean)
     */
    @API
    public static void setVerbose(boolean value)
    {
        DebugConfiguration.verbose = value;
    }

    /**
     * Speichert ein Bildschirmfoto des aktuellen Spielfensters in den Ordner
     * {@code ~/engine-pi}.
     */
    @API
    public static void takeScreenshot()
    {
        BufferedImage screenshot = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) screenshot.getGraphics();
        loop.render(source -> source.render(g, width, height));
        String dir = FileUtil.getHome() + "/engine-pi";
        FileUtil.createDir(dir);
        ImageUtil.write(screenshot,
                dir + "/screenshot_" + System.nanoTime() + ".png");
    }

    @SuppressWarnings("AssignmentToStaticFieldFromInstanceMethod")
    private static class MouseListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent event)
        {
            enqueueMouseEvent(event, true);
        }

        @Override
        public void mouseReleased(MouseEvent event)
        {
            enqueueMouseEvent(event, false);
        }

        @Override
        public void mouseEntered(MouseEvent event)
        {
            mousePosition = event.getPoint();
        }

        @Override
        public void mouseMoved(MouseEvent event)
        {
            mousePosition = event.getPoint();
        }

        @Override
        public void mouseDragged(MouseEvent event)
        {
            mousePosition = event.getPoint();
        }

        private void enqueueMouseEvent(MouseEvent event, boolean down)
        {
            Vector position = convertMousePosition(scene, event.getPoint());
            MouseButton button;
            switch (event.getButton())
            {
            case MouseEvent.BUTTON1:
                button = MouseButton.LEFT;
                break;

            case MouseEvent.BUTTON3:
                button = MouseButton.RIGHT;
                break;

            default:
                // Ignore event
                return;
            }
            loop.enqueue(() -> {
                if (down)
                {
                    mouseClickListeners.invoke(
                            listener -> listener.onMouseDown(position, button));
                    scene.invokeMouseDownListeners(position, button);
                }
                else
                {
                    mouseClickListeners.invoke(
                            listener -> listener.onMouseDown(position, button));
                    scene.invokeMouseUpListeners(position, button);
                }
            });
        }
    }

    private static class KeyListener extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent event)
        {
            enqueueKeyEvent(event, true);
        }

        @Override
        public void keyReleased(KeyEvent event)
        {
            enqueueKeyEvent(event, false);
        }

        private void enqueueKeyEvent(KeyEvent event, boolean down)
        {
            boolean pressed = pressedKeys.contains(event.getKeyCode());
            if (down)
            {
                if (pressed)
                {
                    return; // Ignore duplicate presses, because they're system
                            // dependent
                }
                pressedKeys.add(event.getKeyCode());
            }
            else
            {
                pressedKeys.remove(event.getKeyCode());
            }
            loop.enqueue(() -> {
                if (down)
                {
                    keyStrokeListeners.invoke(
                            keyListener -> keyListener.onKeyDown(event));
                    scene.invokeKeyDownListeners(event);
                }
                else
                {
                    keyStrokeListeners
                            .invoke(keyListener -> keyListener.onKeyUp(event));
                    scene.invokeKeyUpListeners(event);
                }
            });
        }
    }

    public static void main(String[] args)
    {
        Game.start();
    }
}
