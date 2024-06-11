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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;

import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.event.*;
import de.pirckheimer_gymnasium.engine_pi.graphics.RenderPanel;
import de.pirckheimer_gymnasium.engine_pi.sound.Jukebox;
import de.pirckheimer_gymnasium.engine_pi.util.ImageUtil;

/**
 * Diese Klasse gibt Zugriff auf das aktuelle Spiel.
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
    }

    /**
     * Eine statische Instanz der {@link Jukebox}.
     */
    private static final Jukebox jukebox = new Jukebox();

    /**
     * Wird debug auf <code>true</code> gesetzt, so werden ausführliche
     * Informationen zu Tickern im Logger ausgegeben.
     */
    private static boolean debug;

    /**
     * Wird <code>verbose</code> auf <code>true</code> gesetzt, so werden
     * äußerst ausführliche Log-Ausgaben gemacht. Dies betrifft unter anderem
     * Informationen über das Verhalten der frameweise arbeitenden Threads.
     * Hierfür wurde diese Variable eingeführt.
     */
    private static boolean verbose;

    /**
     * Breite des Fensters.
     */
    private static int width;

    /**
     * Höhe des Fensters.
     */
    private static int height;

    /**
     * Eigentliches Fenster des Spiels.
     */
    private static final Frame frame = new Frame("Engine Pi");

    private static RenderPanel renderPanel;

    /**
     * Aktuelle Szene des Spiels.
     */
    private static Scene scene = new Scene();

    private static GameLoop loop;

    private static Thread mainThread;

    /**
     * Speichert den Zustand der einzelnen Tasten der Tastatur. Ist ein Wert
     * <code>true</code>, so ist die entsprechende Taste gedrückt, sonst ist der
     * Wert <code>false</code>.
     */
    private static final Collection<Integer> pressedKeys = ConcurrentHashMap
            .newKeySet();

    private static DefaultControl defaultControl = new DefaultControl();

    /**
     * Letzte Mausposition.
     */
    private static java.awt.Point mousePosition;

    private static final EventListeners<KeyStrokeListener> keyStrokeListeners = new EventListeners<>();

    private static final EventListeners<MouseWheelListener> mouseWheelListeners = new EventListeners<>();

    private static final EventListeners<MouseClickListener> mouseClickListeners = new EventListeners<>();

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
     * Startet das Spiel in einem Fenster mit der angegebenen Breite und Höhe.
     *
     * @param width  Die Breite des Zeichenbereichs in Pixel.
     * @param height Die Höhe des Zeichenbereichs in Pixel.
     * @param scene  Die Szene, mit der das Spiel gestartet wird.
     */
    @API
    public static void start(int width, int height, Scene scene)
    {
        if (renderPanel != null)
        {
            throw new IllegalStateException(
                    "Game.start wurde bereits ausgeführt und kann nur einmal ausgeführt werden");
        }
        Game.width = width;
        Game.height = height;
        Game.scene = scene;
        renderPanel = new RenderPanel(width, height);
        frame.setResizable(false);
        frame.add(renderPanel);
        // pack() already allows to create the buffer strategy for rendering
        // (but not on Windows?)
        frame.pack();
        // Zentriert das Fenster auf dem Bildschirm -
        // https://stackoverflow.com/a/144893/2373138
        frame.setLocationRelativeTo(null);
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
        java.awt.event.KeyListener keyListener = new KeyListener();
        frame.addKeyListener(keyListener);
        renderPanel.addKeyListener(keyListener);
        renderPanel.setFocusable(true);
        MouseAdapter mouseListener = new MouseListener();
        renderPanel.addMouseMotionListener(mouseListener);
        renderPanel.addMouseListener(mouseListener);
        renderPanel.addMouseWheelListener(Game::enqueueMouseWheelEvent);
        try
        {
            frame.setIconImage(Resources.images.get("assets/favicon.png"));
        }
        catch (Exception e)
        {
            // FIXME: Doesn't work in JAR in BlueJ
            // Logger.warning("IO", "Standard-Icon konnte nicht geladen
            // werden.");
        }
        mousePosition = new java.awt.Point(width / 2, height / 2);
        mainThread = new Thread(Game::run,
                "de.pirckheimer_gymnasium.engine_pi.main");
        mainThread.start();
        mainThread.setPriority(Thread.MAX_PRIORITY);
        if (defaultControl != null)
        {
            addKeyStrokeListener(defaultControl);
            addFrameUpdateListener(defaultControl);
        }
    }

    /**
     * Startet das Spiel in einem Fenster mit den Abmessungen 800x600 Pixel.
     *
     * @param scene Die Szene, mit der das Spiel gestartet wird.
     */
    @API
    public static void start(Scene scene)
    {
        start(800, 600, scene);
    }

    /**
     * Wechselt die aktuelle Szene.
     *
     * @param scene Die Szene, zu der gewechselt werden soll. Wird
     *              <code>null</code> übergeben, wird eine neue Szene erstellt.
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
        if (scene == null)
        {
            next = new Scene();
        }
        else
        {
            next = scene;
        }
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
     * Finde die Position des Mausklicks auf der Zeichenebene. Die Position wird
     * relativ zum Ursprung des {@link RenderPanel}-Canvas angegeben. Die
     * Mausklick-Position muss mit dem Zoom-Wert verrechnet werden.
     */
    @Internal
    public static Vector convertMousePosition(Scene scene,
            java.awt.Point mousePosition)
    {
        Camera camera = scene.getCamera();
        double zoom = camera.getMeter();
        double rotation = camera.getRotation();
        Vector position = camera.getPosition();
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
     * Diese Methode wird immer dann ausgeführt, wenn das Mausrad bewegt wurde
     * und ein {@code java.awt.event.MouseWheelEvent} registriert wurde.
     *
     * @param event das Event.
     */
    private static void enqueueMouseWheelEvent(
            java.awt.event.MouseWheelEvent event)
    {
        MouseWheelEvent mouseWheelEvent = new MouseWheelEvent(
                event.getPreciseWheelRotation());
        loop.enqueue(() -> {
            mouseWheelListeners.invoke((listener) -> {
                listener.onMouseWheelMove(mouseWheelEvent);
            });
            scene.invokeMouseWheelMoveListeners(mouseWheelEvent);
        });
    }

    /**
     * Registriert einen statischen, d. h. globalen Beobachter, der auf
     * Bildaktualisierungen reagiert.
     *
     * @param listener Der Beobachter, der auf Bildaktualisierungen reagiert.
     * @return Derselbe Beobachter als als Eingabeparameter angegeben. Kann
     *         nützlich sein, wenn der Beobachter als Lambda-Ausdruck angegeben
     *         wird. Dieser Ausdruck kann dann mit dem Datentyp
     *         {@link FrameUpdateListener} einer lokalen Variablen bzw. einem
     *         Attribut zugewiesen werden.
     *
     * @author Josef Friedrich
     */
    public static FrameUpdateListener addFrameUpdateListener(
            FrameUpdateListener listener)
    {
        addSceneLaunchListener((next, previous) -> {
            if (previous == null)
            {
                loop.getFrameUpdateListener().add(listener);
            }
        });
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
     * KeyListener gilt global über das ganze Spiel und ist unabhängig von der
     * aktuellen Szene.
     *
     * <p>
     * Der {@link KeyListener} kann auf mehrere Arten implementiert werden:
     * </p>
     *
     * <ol>
     * <li>Als normale Klasse:
     *
     * <pre>{@code
     * class MyKeylistener implements KeyListener
     * {
     *     @Override
     *     public void onKeyDown(KeyEvent e)
     *     {
     *         // Code here
     *     }
     * }
     * obj.addKeyListener(new MyKeylistener());
     * }</pre>
     *
     * </li>
     *
     * <li>Als anonyme Klasse:
     *
     * <pre>{@code
     * obj.addKeyListener(new KeyListener()
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
     * obj.addKeyListener(e -> {
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
     * einen KeyListener, der global für das ganze Spiel gilt.
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

    public static void addMouseWheelListener(MouseWheelListener listener)
    {
        mouseWheelListeners.add(listener);
    }

    public static void removeMouseWheelListener(MouseWheelListener listener)
    {
        mouseWheelListeners.remove(listener);
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
     *         ist die Rückgabe <code>null</code>.
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
     *                <code>KeyEvent.VK_D</code>).
     *
     * @return <code>true</code>, wenn die zu testende Taste gerade
     *         heruntergedrückt ist, sonst <code>false</code>.
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
     * Gibt an, ob gerade die Engine läuft. Die Engine läuft, sobald es ein
     * sichtbares Fenster gibt. Dieses läuft, sobald
     * {@link #start(int, int, Scene)} ausgeführt wurde.
     *
     * @return <code>true</code>, wenn das Spiel läuft, sonst
     *         <code>false</code>.
     *
     * @see #start(int, int, Scene)
     */
    @API
    public static boolean isRunning()
    {
        return frame.isVisible();
    }

    /**
     * Setzt die Größe des Engine-Fensters.
     *
     * @param width  Die neue Breite des Engine-Fensters in Pixel.
     * @param height Die neue Höhe des Engine-Fensters in Pixel.
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
     *         -breite übereinstimmt.
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
     * Setzt das Spielfenster an eine neue Position.
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
     * @param title   Der Titel des Dialogfensters.
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
     * @param title   Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers. Ist <code>null</code>, wenn der Nutzer
     *         den Dialog abgebrochen hat.
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
     * @param title   Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers:
     *         <ul>
     *         <li>Ja → <code>true</code></li>
     *         <li>Nein → <code>false</code></li>
     *         <li>Abbruch (= Dialog manuell schließen) →
     *         <code>false</code></li>
     *         </ul>
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
     * @param title   Der Titel des Dialogfensters.
     *
     * @return Die Eingabe des Nutzers:
     *         <ul>
     *         <li>OK → <code>true</code></li>
     *         <li>Abbrechen → <code>false</code></li>
     *         <li>Abbruch (= Dialog manuell schließen) →
     *         <code>false</code></li>
     *         </ul>
     */
    @API
    public static boolean requestOkCancel(String message, String title)
    {
        return JOptionPane.showConfirmDialog(frame, message, title,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION;
    }

    @Internal
    public static java.awt.Point getMousePositionInFrame()
    {
        return mousePosition;
    }

    /**
     * Gibt die Position der Maus in der aktuell angezeigten Scene aus.
     *
     * @return Die Position der Maus in der aktuellen Scene (unter Einbezug von
     *         Kamerazoom und -verschiebung).
     */
    @API
    public static Vector getMousePositionInCurrentScene()
    {
        return scene.getMousePosition();
    }

    /**
     * Setzt, ob die Engine im Debug-Modus ausgeführt werden soll.
     *
     * @param value ist dieser Wert <code>true</code>, wird die Engine ab sofort
     *              im Debug-Modus ausgeführt. Hierdurch werden mehr
     *              Informationen beim Ausführen der Engine angegeben, zum
     *              Beispiel ein grafisches Raster und mehr
     *              Logging-Informationen. Dies ist hilfreich für das Debugging
     *              des eigenen Spiels.
     *
     * @see #isDebug()
     */
    @API
    public static void setDebug(boolean value)
    {
        debug = value;
    }

    /**
     * Gibt an, ob die Engine gerade im Debug-Modus ausgeführt wird.
     *
     * @return ist dieser Wert <code>true</code>, wird die Engine gerade im
     *         Debug-Modus ausgeführt. Sonst ist der Wert <code>false</code>.
     *
     * @see #setDebug(boolean)
     */
    @API
    public static boolean isDebug()
    {
        return debug;
    }

    /**
     * Schaltet je nach Zustand den Debug-Modus an oder aus. Ist der Debug-Modus
     * an, wird er ausgeschaltet, ist er aus so wird er angeschaltet.
     */
    public static void toggleDebug()
    {
        Game.setDebug(!Game.isDebug());
    }

    /**
     * Gibt an, ob die laufende Instanz der Engine gerade verbose Output gibt.
     *
     * @return ist dieser Wert <code>true</code>, werden extrem ausführliche
     *         Logging-Informationen gespeichert. Sonst ist der Wert
     *         <code>false</code>.
     *
     * @see #setVerbose(boolean)
     */
    @API
    public static boolean isVerbose()
    {
        return verbose;
    }

    /**
     * Setzt, ob die aktuell laufende Instanz der Engine verbose Output geben
     * soll.
     *
     * @param value ist dieser Wert <code>true</code>, so wird ein äußerst
     *              ausführliches Log über die Funktionalität der Engine
     *              geführt. Dies ist hauptsächlich für das Debugging an der
     *              Engine selbst notwendig.
     *
     * @see #isVerbose()
     * @see #setDebug(boolean)
     */
    @API
    public static void setVerbose(boolean value)
    {
        verbose = value;
    }

    /**
     * Gibt die statische, d. h. globale Instanz der {@link Jukebox} zurück, die
     * Geräusche und Musik abspielen kann.
     *
     * @author Josef Friedrich
     *
     * @return Eine Instanz der {@link Jukebox}.
     */
    public static Jukebox getJukebox()
    {
        return jukebox;
    }

    /**
     * Rendert einen Screenshot des aktuellen Spielfensters und speichert das
     * resultierende Bild in einer Datei.
     *
     * @param filename Der Name der Datei, in der der Screenshot gespeichert
     *                 werden soll.
     */
    @API
    public static void takeScreenshot(String filename)
    {
        BufferedImage screenshot = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = (Graphics2D) screenshot.getGraphics();
        loop.render(source -> source.render(g2d, width, height));
        ImageUtil.write(screenshot, filename);
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
}
