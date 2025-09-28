package de.pirckheimer_gymnasium.engine_pi.little_engine;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Die Klasse stellt ein Fenster mit einer Malfläche zur Verfügung, auf der
 * Objekte der Klassen Rechteck, Kreis und Dreieck sowie Turtle dargestellt
 * werden können. Die Zeichenfläche wird beim ersten Anlegen eines
 * Zeichenobjekts automatisch nach dem Muster Singleton angelegt.
 *
 * <p>
 * Der ursprüngliche Name der Klasse war {@code Zeichenfenster}.
 * </p>
 *
 * @author Albert Wiedemann
 *
 * @version 1.0
 */
public class DrawingWindow
{
    /**
     * Interface für die Aktionsausführung.
     */
    interface AktionsEmpfaenger
    {
        /**
         * Methode wird vom Taktgeber aufgerufen.
         */
        void Ausführen();

        void Taste(char taste);

        void SonderTaste(int taste);

        void Geklickt(int x, int y, int anzahl);
    }

    /**
     * Aufzählung der erzeugbaren Objektarten.
     */
    static enum SymbolArt
    {
        kreis, dreieck, rechteck, turtle, figur, text;
    };

    /**
     * Einziges Objekt der Zeichenfläche.
     */
    private static DrawingWindow zeichenfläche = null;

    /**
     * Fenster für die Zeichenfläche.
     */
    private JFrame fenster;

    /**
     * Die eigentliche Darstellungskomponente.
     */
    private JComponent malfläche;

    /**
     * Stop-Knopf für den Taktgeber.
     */
    private JButton stop;

    /**
     * Start-Knopf für den Taktgeber.
     */
    private JButton start;

    /**
     * Einsteller für die Taktrate
     */
    private JSlider slider;

    /**
     * Feld aller zu zeichnenden Objekte.
     */
    private ArrayList<GrafikSymbol> alleSymbole;

    /**
     * Feld aller zu zeichnenden Objekte.
     */
    private ArrayList<AktionsEmpfaenger> aktionsEmpfänger;

    /**
     * Timerobjekt für die zentrale Zeitverwaltung
     */
    private javax.swing.Timer timer;

    /**
     * Legt das Fenster und die Malfläche an
     */
    private DrawingWindow()
    {
        alleSymbole = new ArrayList<GrafikSymbol>();
        aktionsEmpfänger = new ArrayList<AktionsEmpfaenger>();
        fenster = new JFrame("Zeichenfenster");
        fenster.setLocation(50, 50);
        fenster.setSize(800, 600);
        fenster.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); // Close-Button
                                                                               // kann
                                                                               // nicht
                                                                               // versteckt
                                                                               // oder
                                                                               // abgestellt
                                                                               // werden.

        malfläche = new JComponent()
        {
            public void paint(Graphics g)
            {
                g.setColor(new Color(230, 230, 230));
                g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
                synchronized (malfläche)
                {
                    for (GrafikSymbol s : alleSymbole)
                    {
                        if (s.sichtbar)
                        {
                            s.Zeichnen(g);
                        }
                    }
                }
            }
        };
        malfläche.setOpaque(true);
        malfläche.addMouseListener(new MouseAdapter()
        {
            /**
             * Gibt den Ort eines Mouseclicks an die eigentliche Aktionsmethode
             * weiter.
             *
             * @param e das zugrestellte Ereignis
             */
            public void mousePressed(MouseEvent e)
            {
                malfläche.requestFocus();
                ArrayList<AktionsEmpfaenger> empfänger = new ArrayList<AktionsEmpfaenger>(
                        aktionsEmpfänger);
                for (AktionsEmpfaenger em : empfänger)
                {
                    em.Geklickt(e.getX(), e.getY(), e.getClickCount());
                }
            }
        });
        malfläche.addKeyListener(new KeyAdapter()
        {
            /**
             * Gibt die Taste an die eigentliche Aktionsmethode weiter.
             *
             * @param e das zugestellte Ereignis
             */
            public void keyPressed(KeyEvent e)
            {
                ArrayList<AktionsEmpfaenger> empfänger = new ArrayList<AktionsEmpfaenger>(
                        aktionsEmpfänger);
                if ((int) e.getKeyChar() == KeyEvent.CHAR_UNDEFINED)
                {
                    switch (e.getKeyCode())
                    {
                    case KeyEvent.VK_ENTER:
                        for (AktionsEmpfaenger em : empfänger)
                        {
                            em.Taste((char) KeyEvent.VK_ENTER);
                        }
                        break;

                    default:
                        for (AktionsEmpfaenger em : empfänger)
                        {
                            em.SonderTaste(e.getKeyCode());
                        }
                    }
                }
                else
                {
                    for (AktionsEmpfaenger em : empfänger)
                    {
                        em.Taste(e.getKeyChar());
                    }
                }
            }
        });
        malfläche.addComponentListener(new ComponentAdapter()
        {
            /**
             * Setzt die Hintegrundbilder aller Turtle auf die neue Größe.
             */
            public void componentResized(ComponentEvent e)
            {
                synchronized (malfläche)
                {
                    for (GrafikSymbol s : alleSymbole)
                    {
                        if (s instanceof TurtleIntern)
                        {
                            ((TurtleIntern) s).NeueGrößeSetzen();
                        }
                    }
                }
            }
        });
        fenster.add(malfläche, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setMinimumSize(new Dimension(200, 60));
        panel.setSize(200, 60);
        panel.setVisible(true);
        panel.setLayout(new GridLayout(1, 2));
        JPanel panel2 = new JPanel();
        panel2.setMinimumSize(new Dimension(100, 60));
        panel2.setSize(100, 60);
        panel2.setVisible(true);
        panel2.setLayout(new GridLayout(1, 1));
        stop = new JButton();
        start = new JButton();
        start.setLocation(10, 10);
        start.setSize(80, 30);
        start.setText("Start");
        start.setVisible(true);
        start.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                TaktgeberStartenIntern();
                malfläche.requestFocus();
            }
        });
        panel2.add(start);
        stop.setLocation(100, 10);
        stop.setSize(80, 30);
        stop.setText("Stop");
        stop.setVisible(true);
        stop.setEnabled(false);
        stop.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt)
            {
                TaktgeberStoppenIntern();
                malfläche.requestFocus();
            }
        });
        panel2.add(stop);
        panel.add(panel2);
        slider = new JSlider(0, 1000, 100);
        slider.setLocation(190, 10);
        slider.setSize(160, 40);
        slider.setMinimumSize(new Dimension(160, 40));
        slider.setPreferredSize(new Dimension(160, 40));
        slider.setMajorTickSpacing(100);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setValue(1000);
        slider.addChangeListener(new ChangeListener()
        {
            public void stateChanged(ChangeEvent e)
            {
                timer.setDelay(slider.getValue());
                malfläche.requestFocus();
            }
        });
        panel.add(slider);

        fenster.add(panel, BorderLayout.SOUTH);
        fenster.setVisible(true);
        malfläche.requestFocus();

        timer = new javax.swing.Timer(1000, new ActionListener()
        {
            /**
             * Vom Timer aufgerufen. Erzeugt den nächsten Taktimpuls
             *
             * @param evt der Timerevent
             */
            public void actionPerformed(ActionEvent evt)
            {
                ArrayList<AktionsEmpfaenger> empfänger = new ArrayList<AktionsEmpfaenger>(
                        aktionsEmpfänger);
                for (AktionsEmpfaenger e : empfänger)
                {
                    e.Ausführen();
                }
            }
        });
    }

    /**
     * Meldet die aktuelle Breite der Malfläche.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code MalflächenBreiteGeben}.
     * </p>
     *
     * @return Breite der Malfläche
     */
    public static int MalflächenBreiteGeben()
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        return zeichenfläche.malfläche.getWidth();
    }

    /**
     * Meldet die aktuelle Höhe der Malfläche.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code MalflächenHöheGeben}.
     * </p>
     *
     * @return Höhe der Malfläche
     */
    public static int MalflächenHöheGeben()
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        return zeichenfläche.malfläche.getHeight();
    }

    /**
     * Trägt einen neuen Aktionsempfänger ein.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code AktionsEmpfängerEintragen}.
     * </p>
     *
     * @param neu der neue Aktionsempfänger
     */
    public static void AktionsEmpfängerEintragen(AktionsEmpfaenger neu)
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        zeichenfläche.aktionsEmpfänger.add(neu);
    }

    /**
     * Löscht einen Aktionsempfänger aus der Liste.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code AktionsEmpfängerEntfernen}.
     * </p>
     *
     * @param alt der zu löschende Aktionsempfänger
     */
    public static void AktionsEmpfängerEntfernen(AktionsEmpfaenger alt)
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        zeichenfläche.aktionsEmpfänger.remove(alt);
    }

    /**
     * Erzeugt ein neues darzustelledes Symbol. Die möglichen Symbole sind im
     * Aufzählungstyp SymbolArt beschrieben.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code SymbolErzeugen}.
     * </p>
     *
     * @param art Art des zu erzeugenden Symbols.
     *
     * @return Referenz auf das Delegate-Objekt.
     */
    public static GrafikSymbol SymbolErzeugen(SymbolArt art)
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        return zeichenfläche.SymbolAnlegen(art);
    }

    /**
     * Startet den Taktgeber.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TaktgeberStarten}.
     * </p>
     */
    public static void TaktgeberStarten()
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        zeichenfläche.TaktgeberStartenIntern();
    }

    /**
     * Stoppt den Taktgeber.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TaktgeberStoppen}.
     * </p>
     */
    public static void TaktgeberStoppen()
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        zeichenfläche.TaktgeberStoppenIntern();
    }

    /**
     * Ablaufgeschwindigkeit des Zeitgebers einstellen.
     *
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TaktdauerSetzen}.
     * </p>
     *
     * @param dauer: Angabe in Millisekunden
     */
    public static void TaktdauerSetzen(int dauer)
    {
        if (zeichenfläche == null)
        {
            zeichenfläche = new DrawingWindow();
        }
        zeichenfläche.slider
                .setValue(dauer < 0 ? 0 : (dauer > 1000 ? 1000 : dauer));
    }

    /**
     * Erzeugt das neue Symbol tatsächlich.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war {@code SymbolAnlegen}.
     * </p>
     *
     * @param art Art des zu erzeugenden Symbols.
     *
     * @return Referenz auf das Delegate-Objekt.
     */
    private GrafikSymbol SymbolAnlegen(SymbolArt art)
    {
        GrafikSymbol neu = null;
        switch (art)
        {
        case rechteck:
            neu = new RechteckIntern();
            break;

        case kreis:
            neu = new EllipseIntern();
            break;

        case dreieck:
            neu = new DreieckIntern();
            break;

        case turtle:
            neu = new TurtleIntern();
            break;

        case figur:
            neu = new FigurIntern();
            break;

        case text:
            neu = new TextIntern();
            break;
        }
        synchronized (zeichenfläche.malfläche)
        {
            zeichenfläche.alleSymbole.add(neu);
        }
        malfläche.repaint();
        return neu;
    }

    /**
     * Startet den Taktgeber.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TaktgeberStartenIntern}.
     * </p>
     */
    private void TaktgeberStartenIntern()
    {
        start.setEnabled(false);
        stop.setEnabled(true);
        timer.start();
    }

    /**
     * Stoppt den Taktgeber.
     *
     * <p>
     * Die ursprünglich deutsche Name dieser Methode war
     * {@code TaktgeberStoppenIntern}.
     * </p>
     */
    private void TaktgeberStoppenIntern()
    {
        start.setEnabled(true);
        stop.setEnabled(false);
        timer.stop();
    }

    /**
     * Oberklasse für alle verfügbaren Grafiksymbole. Alle Grafiksymbole werden
     * über ihr umgebendes Rechteck beschrieben.
     */
    abstract class GrafikSymbol
    {
        /**
         * x-Position der linken oberen Ecke.
         */
        protected int x;

        /**
         * y-Position der linken oberen Ecke.
         */
        protected int y;

        /**
         * Breite des umgebenden Rechtecks.
         */
        protected int b;

        /**
         * Höhe des umgebenden Rechtecks.
         */
        protected int h;

        /**
         * Farbe des Symbols.
         */
        protected Color c;

        /**
         * Sichtbarkeit des Symbols.
         */
        protected boolean sichtbar;

        /**
         * Drehwinkel (mathematisch positiver Drehsinn) des Symbols.
         */
        protected int winkel;

        /**
         * Die Form des Grafiksymbols.
         */
        protected Area form;

        /**
         * Farbe Hellgelb.
         */
        protected final Color hellgelb = new Color(255, 255, 128);

        /**
         * Farbe Hellgrün.
         */
        protected final Color hellgrün = new Color(128, 255, 128);

        /**
         * Farbe Orange.
         */
        protected final Color orange = new Color(255, 128, 0);

        /**
         * Farbe Braun.
         */
        protected final Color braun = new Color(128, 64, 0);

        /**
         * Der Konstruktor erzeugt ein rotes Symbol in der linken oberen Ecke
         * des Fensters.
         */
        GrafikSymbol()
        {
            x = 10;
            y = 10;
            b = 100;
            h = 100;
            c = Color.RED;
            sichtbar = true;
            winkel = 0;
            FormErzeugen();
        }

        /**
         * Normiert den Winkel auf Werte im Bereich [0; 360[
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code WinkelNormieren}.
         * </p>
         *
         * @param winkel der Eingabewinkel
         *
         * @return der normierte Winkel
         */
        int WinkelNormieren(int winkel)
        {
            while (winkel < 0)
            {
                winkel += 360;
            }
            return winkel % 360;
        }

        /**
         * Setzt die Position (der linken oberen Ecke) des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code PositionSetzen}.
         * </p>
         *
         * @param x x-Position der linken oberen Ecke
         * @param y y-Position der linken oberen Ecke
         */
        void PositionSetzen(int x, int y)
        {
            this.x = x;
            this.y = y;
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Setzt die Größe des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code GrößeSetzen}.
         * </p>
         *
         * @param breite (neue) Breite des Objekts
         * @param höhe (neue) Höhe des Objekts
         */
        void GrößeSetzen(int breite, int höhe)
        {
            b = breite;
            h = höhe;
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Bestimmt die RGB-Farbe für den gegeben String.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FarbeCodieren}.
         * </p>
         *
         * @param farbe die Farbe als String
         *
         * @return die Farbe als RGB-Farbe
         */
        Color FarbeCodieren(String farbe)
        {
            farbe = farbe.toLowerCase();
            switch (farbe)
            {
            case "weiß":
            case "weiss":
                return Color.WHITE;

            case "rot":
                return Color.RED;

            case "grün":
            case "gruen":
                return Color.GREEN;

            case "blau":
                return Color.BLUE;

            case "gelb":
                return Color.YELLOW;

            case "magenta":
                return Color.MAGENTA;

            case "cyan":
                return Color.CYAN;

            case "hellgelb":
                return hellgelb;

            case "hellgrün":
            case "hellgruen":
                return hellgrün;

            case "orange":
                return orange;

            case "braun":
                return braun;

            case "grau":
                return Color.GRAY;

            case "schwarz":
                return Color.BLACK;

            default:
                return Color.BLACK;
            }
        }

        /**
         * Setzt die Farbe des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FarbeSetzen}.
         * </p>
         *
         * @param farbe (neue) Farbe des Objekts
         */
        void FarbeSetzen(String farbe)
        {
            FarbeSetzen(FarbeCodieren(farbe));
        }

        /**
         * Setzt die Farbe des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FarbeSetzen}.
         * </p>
         *
         * @param c (neue) Farbe des Objekts
         */
        void FarbeSetzen(Color c)
        {
            this.c = c;
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Setzt die Sichtbarkeit des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code SichtbarkeitSetzen}.
         * </p>
         *
         * @param sichtbar (neue) Sichtbarkeit des Objekts
         */
        void SichtbarkeitSetzen(boolean sichtbar)
        {
            this.sichtbar = sichtbar;
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Setzt den Drehwinkel des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code WinkelSetzen}.
         * </p>
         *
         * @param winkel der (neue) Drehwinkel des Objekts
         */
        void WinkelSetzen(int winkel)
        {
            this.winkel = WinkelNormieren(winkel);
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Entfernt das Objekt aus dem Zeichenfenster.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Entfernen}.
         * </p>
         */
        void remove()
        {
            synchronized (zeichenfläche.malfläche)
            {
                zeichenfläche.alleSymbole.remove(this);
                zeichenfläche.malfläche.repaint();
            }
        }

        /**
         * Bringt das Objekt eine Ebene nach vorn.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code NachVornBringen}.
         * </p>
         */
        void raiseLayer()
        {
            synchronized (zeichenfläche.malfläche)
            {
                int index = zeichenfläche.alleSymbole.indexOf(this);
                if (index < zeichenfläche.alleSymbole.size() - 1)
                {
                    zeichenfläche.alleSymbole.set(index,
                            zeichenfläche.alleSymbole.get(index + 1));
                    zeichenfläche.alleSymbole.set(index + 1, this);
                    zeichenfläche.malfläche.repaint();
                }
            }
        }

        /**
         * Bringt das Objekt in die vorderste Ebene.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code GanzNachVornBringen}.
         * </p>
         */
        void bringToFront()
        {
            synchronized (zeichenfläche.malfläche)
            {
                int index = zeichenfläche.alleSymbole.indexOf(this);
                if (index < zeichenfläche.alleSymbole.size() - 1)
                {
                    zeichenfläche.alleSymbole.remove(index);
                    zeichenfläche.alleSymbole.add(this);
                    zeichenfläche.malfläche.repaint();
                }
            }
        }

        /**
         * Bringt das Objekt eine Ebene nach hinten.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code NachHintenBringen}.
         * </p>
         */
        void lowerLayer()
        {
            synchronized (zeichenfläche.malfläche)
            {
                int index = zeichenfläche.alleSymbole.indexOf(this);
                if (index > 0)
                {
                    zeichenfläche.alleSymbole.set(index,
                            zeichenfläche.alleSymbole.get(index - 1));
                    zeichenfläche.alleSymbole.set(index - 1, this);
                    zeichenfläche.malfläche.repaint();
                }
            }
        }

        /**
         * Bringt das Objekt in die hinterste Ebene.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code GanzNachHintenBringen}.
         * </p>
         */
        void bringToBack()
        {
            synchronized (zeichenfläche.malfläche)
            {
                int index = zeichenfläche.alleSymbole.indexOf(this);
                if (index > 0)
                {
                    zeichenfläche.alleSymbole.remove(index);
                    zeichenfläche.alleSymbole.add(0, this);
                    zeichenfläche.malfläche.repaint();
                }
            }
        }

        /**
         * Testet, ob der angegebene Punkt innerhalb der Figur ist.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code IstInnerhalb}.
         * </p>
         *
         * @param x x-Koordinate des zu testenden Punktes
         * @param y y-Koordinate des zu testenden Punktes
         *
         * @return wahr, wenn der Punkt innerhalb der Figur ist
         */
        boolean IstInnerhalb(int x, int y)
        {
            return form.contains(x, y);
        }

        /**
         * Testet, ob die beiden Figuren überlappen.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Schneidet}.
         * </p>
         *
         * @param wen die andere Form
         *
         * @return wahr, wenn die beiden Formen überlappen.
         */
        boolean Schneidet(Area wen)
        {
            Area area = new Area(form);
            area.intersect(wen);
            return !area.isEmpty();
        }

        /**
         * Zeichnet das Objekt
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Zeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt zum Zeichnen
         */
        void Zeichnen(Graphics g)
        {
            g.setColor(c);
            ((Graphics2D) g).fill(form);
        }

        /**
         * Berechnet den Drehwinkel gemäß den Konventionen des
         * Graphik-Frameworks. Für Java: Winkel in Radians, positive Drehrichtng
         * im Uhrzeiger.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code DrehwinkelGeben}.
         * </p>
         *
         * @param winkel: Der Winkel in Grad, mathematischer Drehsinn
         *
         * @return Winkel für Graphik-Framework
         */
        double DrehwinkelGeben(int winkel)
        {
            return -Math.PI * (winkel > 180 ? winkel - 360 : winkel) / 180.0;
        }

        /**
         * Erstellt die Form des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        abstract void FormErzeugen();
    }

    /**
     * Objekte dieser Klasse verwalten ein Rechteck.
     */
    private class RechteckIntern extends GrafikSymbol
    {
        /**
         * Erstellt die Form des Rechtecks.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        @Override
        void FormErzeugen()
        {
            AffineTransform a = new AffineTransform();
            a.rotate(DrehwinkelGeben(winkel), this.x + b / 2, this.y + h / 2);
            form = new Area(new Path2D.Double(
                    new Rectangle2D.Double(this.x, this.y, b, h), a));
        }
    }

    /**
     * Objekte dieser Klasse verwalten eine Ellipse.
     */
    private class EllipseIntern extends GrafikSymbol
    {
        /**
         * Erstellt die Form der Ellipse.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        @Override
        void FormErzeugen()
        {
            AffineTransform a = new AffineTransform();
            a.rotate(DrehwinkelGeben(winkel), this.x + b / 2, this.y + h / 2);
            form = new Area(new Path2D.Double(
                    new Ellipse2D.Double(this.x, this.y, b, h), a));
        }
    }

    /**
     * Objekte dieser Klasse verwalten ein Dreieck.
     */
    private class DreieckIntern extends GrafikSymbol
    {
        /**
         * Erstellt die Form des Dreiecks.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        @Override
        void FormErzeugen()
        {
            Polygon rand = new Polygon(
                    new int[]
                    { x + b / 2, x + b, x, x + b / 2 },
                    new int[]
                    { y, y + h, y + h, y }, 4);
            AffineTransform a = new AffineTransform();
            a.rotate(DrehwinkelGeben(winkel), this.x + b / 2, this.y + h / 2);
            form = new Area(new Path2D.Double(rand, a));
        }
    }

    /**
     * Objekte dieser Klasse verwalten einen Text.
     */
    class TextIntern extends GrafikSymbol
    {
        /**
         * Der aktuelle Text.
         */
        private String text;

        /**
         * Die aktuelle Textgröße.
         */
        float size;

        /**
         * Belegt text und size mit Defaultwerten.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code TextIntern}.
         * </p>
         */
        TextIntern()
        {
            super();
            text = "Text";
            size = 12;
            c = Color.black;
        }

        /**
         * Erstellt die Form des Textes. Dummy, legt ein leeres Area an.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        @Override
        void FormErzeugen()
        {
            form = new Area();
        }

        /**
         * Testet, ob der angegebene Punkt innerhalb der Figur ist.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code IstInnerhalb}.
         * </p>
         *
         * @param x x-Koordinate des zu testenden Punktes
         * @param y y-Koordinate des zu testenden Punktes
         *
         * @return falsch
         */
        @Override
        boolean IstInnerhalb(int x, int y)
        {
            return false;
        }

        /**
         * Setzt den aktuellen Text.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code TextSetzen}.
         * </p>
         *
         * @param t der neue Text
         */
        void TextSetzen(String t)
        {
            text = t;
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Setzt die Größe des Textes.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code TextGrößeSetzen}.
         * </p>
         */
        void TextGrößeSetzen(int größe)
        {
            size = größe;
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Vergrößert den Text.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code TextVergrößern}.
         * </p>
         */
        void TextVergrößern()
        {
            if (size <= 10)
            {
                size += 1;
            }
            else if (size <= 40)
            {
                size += 2;
            }
            else
            {
                size += 4;
            }
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Verkleinert den Text.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code TextVerkleinern}.
         * </p>
         */
        void TextVerkleinern()
        {
            if (size <= 10)
            {
                size -= 1;
            }
            else if (size <= 40)
            {
                size -= 2;
            }
            else
            {
                size -= 4;
            }
            if (size < 1)
            {
                size = 1;
            }
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Zeichnet das Objekt als Dreieck in der gegebenen Farbe.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Zeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt zum Zeichnen
         */
        @Override
        void Zeichnen(Graphics g)
        {
            g.setColor(c);
            Font f = g.getFont();
            Font f2 = f.deriveFont(size);
            g.setFont(f2);

            if (winkel == 0)
            {
                g.drawString(text, x, y);
            }
            else
            {
                Graphics2D g2 = (Graphics2D) g;
                AffineTransform alt = g2.getTransform();
                // g2.rotate(DrehwinkelGeben (winkel), x + b / 2, y + h / 2);
                // g2.rotate(DrehwinkelGeben (winkel), x + text.length() * size
                // / 4, y + size / 2);
                Rectangle2D bounds = f2.getStringBounds(text,
                        g2.getFontRenderContext());
                g2.rotate(DrehwinkelGeben(winkel), x + bounds.getWidth() / 2,
                        y - bounds.getHeight() / 2);
                g.drawString(text, x, y);
                g2.setTransform(alt);
            }
            g.setFont(f);
        }
    }

    /**
     * Oberklasse für alle Elemente einer Figur (Figur, Turtle).
     */
    private abstract class FigurenElement
    {
        double xe;

        double ye;

        double breite;

        double höhe;

        Color c;

        /**
         * Zeichnet das Figurenelement.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        abstract void ElementZeichnen(Graphics2D g, double größe, int x, int y);

        /**
         * Fügt den Pfadteil deiser Komponente zum umgebenden Pfad hinzu.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZuForm}.
         * </p>
         *
         * @param p der Gesamtpfad
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        abstract void ElementZuForm(Path2D.Double p, double größe, int x,
                int y);
    }

    /**
     * Ein rechteckiges Figurenelement.
     */
    private class FigurenElementRechteck extends FigurenElement
    {
        /**
         * Der Konstruktor speichert die Rahmendaten.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FigurenElementRechteck}.
         * </p>
         *
         * @param x x-Koordinate der linken oberen Ecke des Rechtecks relativ
         *     zum Aufhängepunkt.
         * @param y y-Koordinate der linken oberen Ecke des Rechtecks relativ
         *     zum Aufhängepunkt.
         * @param breite Breite des Rechtecks
         * @param höhe Höhe des Rechtecks
         * @param c Farbe des Rechtecks
         */
        FigurenElementRechteck(double x, double y, double breite, double höhe,
                Color c)
        {
            this.xe = x;
            this.ye = y;
            this.breite = breite;
            this.höhe = höhe;
            this.c = c;
        }

        /**
         * Zeichnet das Figurenelement.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        @Override
        void ElementZeichnen(Graphics2D g, double größe, int x, int y)
        {
            g.setColor(c);
            g.fill(new Rectangle2D.Double(x + größe * xe / 100.0,
                    y + größe * ye / 100.0, größe * breite / 100.0,
                    größe * höhe / 100.0));
        }

        /**
         * Fügt den Pfadteil dieser Komponente zum umgebenden Pfad hinzu.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZuForm}.
         * </p>
         *
         * @param p der Gesamtpfad
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        @Override
        void ElementZuForm(Path2D.Double p, double größe, int x, int y)
        {
            p.append(new Rectangle2D.Double(x + größe * xe / 100.0,
                    y + größe * ye / 100.0, größe * breite / 100.0,
                    größe * höhe / 100.0), false);
        }
    }

    /**
     * Ein elliptisches Figurenelement.
     */
    private class FigurenElementEllipse extends FigurenElement
    {
        /**
         * Der Konstruktor speichert die Rahmendaten.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FigurenElementEllipse}.
         * </p>
         *
         * @param x x-Koordinate der linken oberen Ecke des umgebenden Rechtecks
         *     relativ zum Aufhängepunkt.
         * @param y y-Koordinate der linken oberen Ecke des umgebenden Rechtecks
         *     relativ zum Aufhängepunkt.
         * @param breite Breite der Ellipse
         * @param höhe Höhe der Ellipse
         * @param c Farbe der Ellipse
         */
        FigurenElementEllipse(double x, double y, double breite, double höhe,
                Color c)
        {
            this.xe = x;
            this.ye = y;
            this.breite = breite;
            this.höhe = höhe;
            this.c = c;
        }

        /**
         * Zeichnet das Figurenelement.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        @Override
        void ElementZeichnen(Graphics2D g, double größe, int x, int y)
        {
            g.setColor(c);
            g.fill(new Ellipse2D.Double(x + größe * xe / 100.0,
                    y + größe * ye / 100.0, größe * breite / 100.0,
                    größe * höhe / 100.0));
        }

        /**
         * Fügt den Pfadteil dieser Komponente zum umgebenden Pfad hinzu.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZuForm}.
         * </p>
         *
         * @param p der Gesamtpfad
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        @Override
        void ElementZuForm(Path2D.Double p, double größe, int x, int y)
        {
            p.append(new Ellipse2D.Double(x + größe * xe / 100.0,
                    y + größe * ye / 100.0, größe * breite / 100.0,
                    größe * höhe / 100.0), false);
        }
    }

    /**
     * Ein Figurenelement begrenzt durch das angegebene Polygon.
     */
    private class FigurenElementPolygon extends FigurenElement
    {
        /**
         * Das Polygonobjekt
         */
        private Polygon poly;

        /**
         * Der Konstruktor speichert die Rahmendaten.
         *
         * @param x x-Koordinaten der Stützpunkte des Polygons relativ zum
         *     Aufhängepunkt.
         * @param y y-Koordinaten der Stützpunkte des Polygons relativ zum
         *     Aufhängepunkt.
         * @param c Farbe der Polygonfläche
         */
        FigurenElementPolygon(int[] x, int[] y, Color c)
        {
            int anz = x.length <= y.length ? x.length : y.length;
            poly = new Polygon(x, y, anz);
            Rectangle2D bounds = poly.getBounds2D();
            xe = bounds.getX();
            ye = bounds.getY();
            breite = bounds.getWidth();
            höhe = bounds.getHeight();
            this.c = c;
        }

        /**
         * Zeichnet das Figurenelement.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        @Override
        void ElementZeichnen(Graphics2D g, double größe, int x, int y)
        {
            g.setColor(c);
            AffineTransform at = new AffineTransform(größe / 100.0, 0, 0,
                    größe / 100.0, x, y);
            g.fill(new Path2D.Double(poly, at));
        }

        /**
         * Fügt den Pfadteil dieser Komponente zum umgebenden Pfad hinzu.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ElementZuForm}.
         * </p>
         *
         * @param p der Gesamtpfad
         * @param größe die aktuelle Größe der Figur
         * @param x die x-Koordinate des Aufhängepunkts der Figur
         * @param y die y-Koordinate des Aufhängepunkts der Figur
         */
        @Override
        void ElementZuForm(Path2D.Double p, double größe, int x, int y)
        {
            AffineTransform at = new AffineTransform(größe / 100.0, 0, 0,
                    größe / 100.0, x, y);
            Path2D.Double p2 = new Path2D.Double(poly, at);
            p2.closePath();
            p2.setWindingRule(Path2D.WIND_EVEN_ODD);
            p.append(p2, false);
        }
    }

    /**
     * Das Objekt dieser Klasse zeichnet den Weg der Turtle.
     */
    class TurtleIntern extends GrafikSymbol
    {
        private class LinienElement
        {
            /**
             * x-Koordinate des Startpunktes.
             */
            private double xStart;

            /**
             * y-Koordinate des Startpunktes.
             */
            private double yStart;

            /**
             * x-Koordinate des Endpunktes.
             */
            private double xEnde;

            /**
             * y-Koordinate des Endpunktes.
             */
            private double yEnde;

            /**
             * Farbe des LinienElements.
             */
            private Color c;

            LinienElement(double xStart, double yStart, double xEnde,
                    double yEnde, Color c)
            {
                this.xStart = xStart;
                this.yStart = yStart;
                this.xEnde = xEnde;
                this.yEnde = yEnde;
                this.c = c;
            }

            void Zeichnen(Graphics2D g)
            {
                g.setColor(c);
                g.draw(new Line2D.Double(xStart, yStart, xEnde, yEnde));
            }
        }

        /**
         * Verwaltet das Hintergrundfenster für die Turtlezeichnung.
         */
        private class HintergrundBild
        {
            /**
             * Das aktuelle Hintergrundbild.
             */
            private BufferedImage bild;

            /**
             * Das zugehörige Zeichenobjekt.
             */
            private Graphics2D g;

            /**
             * Der Konstruktor legt das Bild in der Größe der Zeichenfläche an.
             */
            HintergrundBild()
            {
                bild = new BufferedImage(DrawingWindow.MalflächenBreiteGeben(),
                        DrawingWindow.MalflächenBreiteGeben(),
                        BufferedImage.TYPE_INT_ARGB);
                g = bild.createGraphics();
                g.setColor(new Color(0, 0, 0, 0));
                g.fillRect(0, 0, bild.getWidth(), bild.getHeight());
            }

            /**
             * Zeichent die angegebe Linie in das Bild.
             *
             * <p>
             * Die ursprünglich deutsche Name dieser Methode war
             * {@code LinieZeichnen}.
             * </p>
             *
             * @param linie das zu zeichnende Linienelement.
             */
            void LinieZeichnen(LinienElement linie)
            {
                linie.Zeichnen(g);
            }

            /**
             * Zeichnet das Bild in das angegebene Zeichenobjekt.
             *
             * <p>
             * Die ursprünglich deutsche Name dieser Methode war
             * {@code BildZeichnen}.
             * </p>
             *
             * @param wohin Zeichenobjekt
             */
            void BildZeichnen(Graphics2D wohin)
            {
                wohin.drawImage(bild, null, 0, 0);
            }
        }

        /**
         * Genaue x-Koordinate der Turtle.
         */
        double xD;

        /**
         * Genaue y-Koordinate der Turtle.
         */
        double yD;

        /**
         * Startkoordinate der Turtle.
         */
        private int homeX;

        /**
         * Startkoordinate der Turtle.
         */
        private int homeY;

        /**
         * Startwinkel der Turtle.
         */
        private int homeWinkel;

        /**
         * Stiftposition.
         */
        boolean stiftUnten;

        /**
         * Die Sichtbarkeit des Turtle-Symbols.
         */
        private boolean symbolSichtbar;

        /**
         * Linienelemente.
         */
        private ArrayList<LinienElement> linien;

        /**
         * Standardfigur für Turtle.
         */
        private LinkedList<FigurenElement> standardFigur;

        /**
         * Das Hintergrundbild für die Linien.
         */
        private HintergrundBild hintergrund;

        /**
         * Legt die Turtle mit Startpunkt (100|200) in Richtung 0˚ an.
         */
        TurtleIntern()
        {
            super();
            x = 100;
            y = 200;
            xD = x;
            yD = y;
            h = 40;
            b = 40;
            homeX = x;
            homeY = y;
            homeWinkel = winkel;
            c = Color.black;
            stiftUnten = true;
            symbolSichtbar = true;
            linien = new ArrayList<LinienElement>();
            hintergrund = new HintergrundBild();
            standardFigur = new LinkedList<FigurenElement>();
            StandardfigurErzeugen();
            FormErzeugen();
        }

        /**
         * Baut die Standardfigur aus den Elementen auf.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code StandardfigurErzeugen}.
         * </p>
         */
        private void StandardfigurErzeugen()
        {
            // Kopf
            standardFigur.add(
                    new FigurenElementEllipse(50, -12.5, 25, 25, Color.GREEN));
            // Beine
            standardFigur.add(new FigurenElementEllipse(22.5, -32.5, 12.5, 17.5,
                    Color.GREEN));
            standardFigur.add(new FigurenElementEllipse(40.0, -32.5, 12.5, 17.5,
                    Color.GREEN));
            standardFigur.add(new FigurenElementEllipse(22.5, 15.0, 12.5, 17.5,
                    Color.GREEN));
            standardFigur.add(new FigurenElementEllipse(40.0, 15.0, 12.5, 17.5,
                    Color.GREEN));
            // Augen
            standardFigur
                    .add(new FigurenElementRechteck(67.5, -10.0, 5.0, 7.5, c));
            standardFigur
                    .add(new FigurenElementRechteck(67.5, 2.5, 5.0, 7.5, c));
            // Schwanz
            standardFigur.add(new FigurenElementEllipse(0, -3.75, 25, 7.5, c));
            // Rumpf
            standardFigur.add(
                    new FigurenElementEllipse(7.5, -23.75, 57.5, 47.5, braun));
        }

        /**
         * Passt das Hintergrundbild an eine neue Größe der Zeichenfläche an.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code NeueGrößeSetzen}.
         * </p>
         */
        void NeueGrößeSetzen()
        {
            hintergrund = new HintergrundBild();
            for (LinienElement l : linien)
            {
                hintergrund.LinieZeichnen(l);
            }
        }

        /**
         * Erstellt die Form der Turtle.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        @Override
        void FormErzeugen()
        {
            Area area = new Area();
            AffineTransform a = new AffineTransform();
            a.rotate(DrehwinkelGeben(winkel), this.x, this.y);
            double größe = h > b ? b : h;
            if (standardFigur != null)
            {
                synchronized (standardFigur)
                {
                    for (FigurenElement e : standardFigur)
                    {
                        Path2D.Double p = new Path2D.Double();
                        e.ElementZuForm(p, größe, x, y);
                        area.add(new Area(new Path2D.Double(p, a)));
                    }
                }

            }
            form = area;
        }

        /**
         * Setzt die Position (der linken oberen Ecke) des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code PositionSetzen}.
         * </p>
         *
         * @param x x-Position der linken oberen Ecke
         * @param y y-Position der linken oberen Ecke
         */
        @Override
        void PositionSetzen(int x, int y)
        {
            super.PositionSetzen(x, y);
            xD = x;
            yD = y;
        }

        /**
         * Setzt die Turtle wieder an ihre Ausgangsposition.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ZumStartpunktGehen}.
         * </p>
         */
        void moveToStartPoint()
        {
            x = homeX;
            y = homeY;
            xD = x;
            yD = y;
            winkel = homeWinkel;
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Bewegt die Turtle nach vorne.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Gehen}.
         * </p>
         *
         * @param länge Anzahl der Längeneinheiten
         */
        void move(double länge)
        {
            double neuX = xD + Math.cos(DrehwinkelGeben(winkel)) * länge;
            double neuY = yD + Math.sin(DrehwinkelGeben(winkel)) * länge;
            if (stiftUnten)
            {
                synchronized (this)
                {
                    LinienElement l = new LinienElement(xD, yD, neuX, neuY, c);
                    linien.add(l);
                    hintergrund.LinieZeichnen(l);
                }
            }
            xD = neuX;
            yD = neuY;
            x = (int) Math.round(xD);
            y = (int) Math.round(yD);
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Dreht die Turtle
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Drehen}.
         * </p>
         *
         * @param grad Drehwinkel im Gradmass
         */
        void rotate(int grad)
        {
            winkel = WinkelNormieren(winkel + grad);
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Versetzt Zeichenfläche und Turtle in den Ausgangszustand
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Löschen}.
         * </p>
         */
        void reset()
        {
            linien.clear();
            hintergrund = new HintergrundBild();
            moveToStartPoint();
        }

        /**
         * Turtle wechselt in den Modus "nicht zeichnen"
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code StiftHeben}.
         * </p>
         */
        void liftPen()
        {
            stiftUnten = false;
        }

        /**
         * Turtle wechselt in den Modus "zeichnen"
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code StiftSenken}.
         * </p>
         */
        void lowerPen()
        {
            stiftUnten = true;
        }

        /**
         * Schaltet die Sichtbarkeit des Turtlesymbols ein oder aus. Erlaubte
         * Parameterwerte: true, false
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code SichtbarkeitFürSymbolSetzen}.
         * </p>
         *
         * @param sichtbar (neue) Sichtbarkeit des Turtlesymbols
         */
        void setSymbolVisibility(boolean sichtbar)
        {
            symbolSichtbar = sichtbar;
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Testet, ob die Turtle eine (sichtbare) Figur berührt.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
         * </p>
         *
         * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur
         *     sind
         */
        boolean isTouching()
        {
            for (GrafikSymbol g : zeichenfläche.alleSymbole)
            {
                if ((g != this) && g.IstInnerhalb(x, y) && g.sichtbar
                        && (!(g instanceof TurtleIntern)
                                || ((TurtleIntern) g).symbolSichtbar))
                {
                    return true;
                }
            }
            return false;
        }

        /**
         * Testet, ob die Turtle eine (sichtbare) Figur in der angegebenen Farbe
         * berührt. Bei Überlappungen
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
         * </p>
         *
         * @param farbe die Farbe, die die berührte Figur haben muss.
         *
         * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur
         *     in der angegebenen Farbe sind
         */
        boolean isTouching(String farbe)
        {
            Color c2 = FarbeCodieren(farbe);
            boolean ok = false;
            for (GrafikSymbol g : zeichenfläche.alleSymbole)
            {
                if ((g != this) && g.IstInnerhalb(x, y) && g.sichtbar)
                {
                    if (g instanceof TurtleIntern)
                    {
                        TurtleIntern t = (TurtleIntern) g;
                        if (t.symbolSichtbar)
                        {
                            for (FigurenElement e : t.standardFigur)
                            {
                                Path2D.Double p = new Path2D.Double();
                                double größe = t.h > t.b ? t.b : t.h;
                                e.ElementZuForm(p, größe, t.x, t.y);
                                AffineTransform a = new AffineTransform();
                                a.rotate(DrehwinkelGeben(t.winkel), t.x, t.y);
                                p = new Path2D.Double(p, a);
                                if (p.contains(x, y))
                                {
                                    ok = c2.equals(e.c);
                                }
                            }
                        }
                    }
                    else if (g instanceof FigurIntern)
                    {
                        FigurIntern t = (FigurIntern) g;
                        LinkedList<FigurenElement> figur = ((t.eigeneFigur == null)
                                || (t.eigeneFigur.size() == 0))
                                        ? t.standardFigur
                                        : t.eigeneFigur;
                        for (FigurenElement e : figur)
                        {
                            Path2D.Double p = new Path2D.Double();
                            double größe = t.h > t.b ? t.b : t.h;
                            e.ElementZuForm(p, größe, t.x, t.y);
                            AffineTransform a = new AffineTransform();
                            a.rotate(DrehwinkelGeben(t.winkel), t.x, t.y);
                            p = new Path2D.Double(p, a);
                            if (p.contains(x, y))
                            {
                                ok = c2.equals(e.c);
                            }
                        }
                    }
                    else
                    {
                        ok = ok || c2.equals(g.c);
                    }
                }
            }
            return ok;
        }

        /**
         * Testet, ob die Turtle die (sichtbare, ) angegebene Figur berührt.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
         * </p>
         *
         * @param object das Objekt, das getestet werden soll.
         *
         * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur
         *     in der angegebenen Farbe sind
         */
        boolean isTouching(Object object)
        {
            GrafikSymbol s = null;
            if (object instanceof Rectangle)
            {
                s = ((Rectangle) object).symbol;
            }
            else if (object instanceof Triangle)
            {
                s = ((Triangle) object).symbol;
            }
            else if (object instanceof Circle)
            {
                s = ((Circle) object).symbol;
            }
            else if (object instanceof Turtle)
            {
                s = ((Turtle) object).symbol;
            }
            else if (object instanceof Character)
            {
                s = ((Character) object).symbol;
            }
            return (s != null) && (s != this) && s.IstInnerhalb(x, y)
                    && s.sichtbar && (!(s instanceof TurtleIntern)
                            || ((TurtleIntern) s).symbolSichtbar);
        }

        /**
         * Zeichnet das Objekt als Dreieck in der gegebenen Farbe.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Zeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt zum Zeichnen
         */
        @Override
        void Zeichnen(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            synchronized (this)
            {
                hintergrund.BildZeichnen(g2);
            }

            if (symbolSichtbar)
            {
                g.setColor(Color.black);
                double größe = h > b ? b : h;
                AffineTransform alt = g2.getTransform();
                g2.rotate(DrehwinkelGeben(winkel), x, y);
                if (standardFigur != null)
                {
                    synchronized (standardFigur)
                    {
                        for (FigurenElement e : standardFigur)
                        {
                            e.ElementZeichnen(g2, größe, x, y);
                        }
                    }
                }
                g2.setTransform(alt);
            }
        }
    }

    /**
     * Das Objekt dieser Klasse ist ein in der Gestalt definierbarer Akteur.
     */
    class FigurIntern extends GrafikSymbol
    {

        /**
         * Genaue x-Koordinate der Figur.
         */
        double xD;

        /**
         * Genaue y-Koordinate der Figur.
         */
        double yD;

        /**
         * Startkoordinate der Figur.
         */
        private int homeX;

        /**
         * Startkoordinate der Figur.
         */
        private int homeY;

        /**
         * Startwinkel der Figur.
         */
        private int homeWinkel;

        /**
         * Eigene Figur für Figur.
         */
        private LinkedList<FigurenElement> eigeneFigur;

        /**
         * Standardfigur für Figur.
         */
        private LinkedList<FigurenElement> standardFigur;

        /**
         * Legt die Figur mit Startpunkt (100|200) in Richtung 0˚ an.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FigurIntern}.
         * </p>
         */
        FigurIntern()
        {
            super();
            x = 100;
            y = 200;
            xD = x;
            yD = y;
            h = 40;
            b = 40;
            homeX = x;
            homeY = y;
            homeWinkel = winkel;
            c = Color.black;
            eigeneFigur = new LinkedList<FigurenElement>();
            standardFigur = new LinkedList<FigurenElement>();
            StandardfigurErzeugen();
            FormErzeugen();
        }

        /**
         * Baut die Standardfigur aus den Elementen auf.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code StandardfigurErzeugen}.
         * </p>
         */
        private void StandardfigurErzeugen()
        {
            int[] x = new int[] { -50, 50, -50 };
            int[] y = new int[] { -50, 0, 50 };
            standardFigur.add(new FigurenElementPolygon(x, y, Color.yellow));
            standardFigur.add(
                    new FigurenElementEllipse(-10, -10, 20, 20, Color.blue));
        }

        /**
         * Erstellt die Form der Figur.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FormErzeugen}.
         * </p>
         */
        @Override
        void FormErzeugen()
        {
            Area area = new Area();
            AffineTransform a = new AffineTransform();
            a.rotate(DrehwinkelGeben(winkel), this.x, this.y);
            double größe = h > b ? b : h;
            if (standardFigur != null)
            {
                LinkedList<FigurenElement> figur = ((eigeneFigur == null)
                        || (eigeneFigur.size() == 0)) ? standardFigur
                                : eigeneFigur;
                synchronized (figur)
                {
                    for (FigurenElement e : figur)
                    {
                        Path2D.Double p = new Path2D.Double();
                        e.ElementZuForm(p, größe, x, y);
                        area.add(new Area(new Path2D.Double(p, a)));
                    }
                }

            }
            form = area;
        }

        /**
         * Setzt die Position (der Mitte) des Objekts.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code PositionSetzen}.
         * </p>
         *
         * @param x x-Position der Mitte
         * @param y y-Position der Mitte
         */
        @Override
        void PositionSetzen(int x, int y)
        {
            super.PositionSetzen(x, y);
            xD = x;
            yD = y;
        }

        /**
         * Setzt die Figur wieder an ihre Ausgangsposition.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code ZumStartpunktGehen}.
         * </p>
         */
        void ZumStartpunktGehen()
        {
            x = homeX;
            y = homeY;
            xD = x;
            yD = y;
            winkel = homeWinkel;
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Bewegt die Figur nach vorne.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Gehen}.
         * </p>
         *
         * @param länge Anzahl der Längeneinheiten
         */
        void Gehen(double länge)
        {
            double neuX = xD + Math.cos(DrehwinkelGeben(winkel)) * länge;
            double neuY = yD + Math.sin(DrehwinkelGeben(winkel)) * länge;
            xD = neuX;
            yD = neuY;
            x = (int) Math.round(xD);
            y = (int) Math.round(yD);
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Dreht die Figur
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Drehen}.
         * </p>
         *
         * @param grad Drehwinkel im Gradmass
         */
        void Drehen(int grad)
        {
            winkel = WinkelNormieren(winkel + grad);
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Testet, ob die Figur eine (sichtbare) Grafik-Figur berührt.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
         * </p>
         *
         * @return true, wenn die Figurkoordinaten innerhalb einer Grafikfigur
         *     sind
         */
        boolean Berührt()
        {
            for (GrafikSymbol g : zeichenfläche.alleSymbole)
            {
                if ((g != this) && g.Schneidet(form) && g.sichtbar
                        && (!(g instanceof TurtleIntern)
                                || ((TurtleIntern) g).symbolSichtbar))
                {
                    return true;
                }
            }
            return false;
        }

        /**
         * Testet, ob die Figur eine (sichtbare) Grafik-Figur in der angegebenen
         * Farbe berührt. Bei Überlappungen
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
         * </p>
         *
         * @param farbe die Farbe, die die berührte Figur haben muss.
         *
         * @return true, wenn die Figurkoordinaten innerhalb einer Grafikfigur
         *     in der angegebenen Farbe sind
         */
        boolean Berührt(String farbe)
        {
            Color c2 = FarbeCodieren(farbe);
            boolean ok = false;
            for (GrafikSymbol g : zeichenfläche.alleSymbole)
            {
                if ((g != this) && g.Schneidet(form) && g.sichtbar)
                {
                    if (g instanceof TurtleIntern)
                    {
                        TurtleIntern t = (TurtleIntern) g;
                        if (t.symbolSichtbar)
                        {
                            Area[] areas = new Area[t.standardFigur.size()];
                            Color[] colors = new Color[t.standardFigur.size()];
                            AffineTransform a = new AffineTransform();
                            a.rotate(DrehwinkelGeben(t.winkel), t.x, t.y);
                            int pos = 0;
                            for (FigurenElement e : t.standardFigur)
                            {
                                Path2D.Double p = new Path2D.Double();
                                double größe = t.h > t.b ? t.b : t.h;
                                e.ElementZuForm(p, größe, t.x, t.y);
                                p = new Path2D.Double(p, a);
                                areas[pos] = new Area(p);
                                colors[pos] = e.c;
                                for (int i = pos - 1; i >= 0; i--)
                                {
                                    areas[i].subtract(areas[pos]);
                                }
                                pos += 1;
                            }
                            for (int i = 0; i < areas.length; i++)
                            {
                                if (Schneidet(areas[i])
                                        && (c2.equals(colors[i])))
                                {
                                    ok = true;
                                }
                            }
                        }
                    }
                    else if (g instanceof FigurIntern)
                    {
                        FigurIntern t = (FigurIntern) g;
                        LinkedList<FigurenElement> figur = ((t.eigeneFigur == null)
                                || (t.eigeneFigur.size() == 0))
                                        ? t.standardFigur
                                        : t.eigeneFigur;
                        Area[] areas = new Area[figur.size()];
                        Color[] colors = new Color[figur.size()];
                        AffineTransform a = new AffineTransform();
                        a.rotate(DrehwinkelGeben(t.winkel), t.x, t.y);
                        int pos = 0;
                        for (FigurenElement e : figur)
                        {
                            Path2D.Double p = new Path2D.Double();
                            double größe = t.h > t.b ? t.b : t.h;
                            e.ElementZuForm(p, größe, t.x, t.y);
                            p = new Path2D.Double(p, a);
                            areas[pos] = new Area(p);
                            colors[pos] = e.c;
                            for (int i = pos - 1; i >= 0; i--)
                            {
                                areas[i].subtract(areas[pos]);
                            }
                            pos += 1;
                        }
                        for (int i = 0; i < areas.length; i++)
                        {
                            if (Schneidet(areas[i]) && (c2.equals(colors[i])))
                            {
                                ok = true;
                            }
                        }
                    }
                    else
                    {
                        ok = ok || c2.equals(g.c);
                    }
                }
            }
            return ok;
        }

        /**
         * Testet, ob die Figur die (sichtbare, ) angegebene Grafik-Figur
         * berührt.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Berührt}.
         * </p>
         *
         * @param object das Objekt, das getestet werden soll.
         *
         * @return true, wenn die Turtlekoordinaten innerhalb einer Grafikfigur
         *     in der angegebenen Farbe sind
         */
        boolean Berührt(Object object)
        {
            GrafikSymbol s = null;
            if (object instanceof Rectangle)
            {
                s = ((Rectangle) object).symbol;
            }
            else if (object instanceof Triangle)
            {
                s = ((Triangle) object).symbol;
            }
            else if (object instanceof Circle)
            {
                s = ((Circle) object).symbol;
            }
            else if (object instanceof Turtle)
            {
                s = ((Turtle) object).symbol;
            }
            else if (object instanceof Character)
            {
                s = ((Character) object).symbol;
            }
            return (s != null) && (s != this) && s.Schneidet(form) && s.sichtbar
                    && (!(s instanceof TurtleIntern)
                            || ((TurtleIntern) s).symbolSichtbar);
        }

        /**
         * Erzeugt ein neues, rechteckiges Element einer eigenen Darstellung der
         * Figur. Alle Werte beziehen sich auf eine Figur der Größe 100 und den
         * Koordinaten (0|0) in der Mitte des Quadrats
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FigurteilFestlegenRechteck}.
         * </p>
         *
         * @param x x-Wert der linken oberen Ecke des Rechtecks
         * @param y y-Wert der linken oberen Ecke des Rechtecks
         * @param breite Breite des Rechtecks
         * @param höhe Höhe des Rechtecks
         * @param farbe (Füll)Farbe des Rechtecks
         */
        void FigurteilFestlegenRechteck(int x, int y, int breite, int höhe,
                String farbe)
        {
            synchronized (eigeneFigur)
            {
                eigeneFigur.add(new FigurenElementRechteck(x, y, breite, höhe,
                        FarbeCodieren(farbe)));
            }
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Erzeugt ein neues, elliptisches Element einer eigenen Darstellung der
         * Figur. Alle Werte beziehen sich auf eine Figur der Größe 100 und den
         * Koordinaten (0|0) in der Mitte des Quadrats
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FigurteilFestlegenEllipse}.
         * </p>
         *
         * @param x x-Wert der linken oberen Ecke des umgebenden Rechtecks der
         *     Ellipse
         * @param y y-Wert der linken oberen Ecke des umgebenden Rechtecks der
         *     Ellipse
         * @param breite Breite des umgebenden Rechtecks der Ellipse
         * @param höhe Höhe des umgebenden Rechtecks der Ellipse
         * @param farbe (Füll)Farbe der Ellipse
         */
        void FigurteilFestlegenEllipse(int x, int y, int breite, int höhe,
                String farbe)
        {
            synchronized (eigeneFigur)
            {
                eigeneFigur.add(new FigurenElementEllipse(x, y, breite, höhe,
                        FarbeCodieren(farbe)));
            }
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Erzeugt ein neues, dreieckiges Element einer eigenen Darstellung der
         * Figur. Alle Werte beziehen sich auf eine Figur der Größe 100 und den
         * Koordinaten (0|0) in der Mitte des Quadrats
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code FigurteilFestlegenDreieck}.
         * </p>
         *
         * @param x1 x-Wert des ersten Punkts des Dreiecks
         * @param y1 y-Wert des ersten Punkts des Dreiecks
         * @param x2 x-Wert des zweiten Punkts des Dreiecks
         * @param y2 y-Wert des zweiten Punkts des Dreiecks
         * @param x3 x-Wert des dritten Punkts des Dreiecks
         * @param y3 y-Wert des dritten Punkts des Dreiecks
         * @param farbe (Füll)Farbe der Ellipse
         */
        void FigurteilFestlegenDreieck(int x1, int y1, int x2, int y2, int x3,
                int y3, String farbe)
        {
            synchronized (eigeneFigur)
            {
                int[] x = new int[] { x1, x2, x3 };
                int[] y = new int[] { y1, y2, y3 };
                eigeneFigur.add(
                        new FigurenElementPolygon(x, y, FarbeCodieren(farbe)));
            }
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Löscht die Vereinbarung für die eigene Darstellung Figur. Die Figur
         * wird wieder durch die Originalfigur dargestellt.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war
         * {@code EigeneFigurLöschen}.
         * </p>
         */
        void EigeneFigurLöschen()
        {
            eigeneFigur.clear();
            FormErzeugen();
            zeichenfläche.malfläche.repaint();
        }

        /**
         * Zeichnet das Objekt als Dreieck in der gegebenen Farbe.
         *
         * <p>
         * Die ursprünglich deutsche Name dieser Methode war {@code Zeichnen}.
         * </p>
         *
         * @param g das Grafikobjekt zum Zeichnen
         */
        @Override
        void Zeichnen(Graphics g)
        {
            Graphics2D g2 = (Graphics2D) g;
            // Outline
            g.setColor(Color.black);
            Stroke stAlt = g2.getStroke();
            g2.setStroke(new BasicStroke(3.0f));
            g2.draw(form);
            g2.setStroke(stAlt);
            // Füllung
            double größe = h > b ? b : h;
            AffineTransform alt = g2.getTransform();
            g2.rotate(DrehwinkelGeben(winkel), x, y);
            if (standardFigur != null)
            {
                LinkedList<FigurenElement> figur = ((eigeneFigur == null)
                        || (eigeneFigur.size() == 0)) ? standardFigur
                                : eigeneFigur;
                synchronized (figur)
                {
                    for (FigurenElement e : figur)
                    {
                        e.ElementZeichnen(g2, größe, x, y);
                    }
                }
            }
            g2.setTransform(alt);
        }
    }
}
