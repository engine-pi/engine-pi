package de.pirckheimer_gymnasium.engine_pi_demos.algorithms.graph;

/**
 * Klasse für einen ungerichteten, gewichteten Graphen. Als Datenstruktur wird
 * eine Adjazenzmatrix verwendet
 *
 * <p>
 * Heißt im Schulbuch: <code>GraphMatrix</code>.
 * </p>
 */
public class GraphMatrix
{
    /**
     * Die aktuelle Knotenanzahl.
     *
     * <p>
     * Heißt im Schulbuch: <code>nodeCount</code>.
     * </p>
     */
    private int nodeCount;

    /**
     * Das Feld der Knoten des Graphen.
     *
     * <p>
     * Heißt im Schulbuch: <code>knoten</code>.
     * </p>
     */
    private Node[] nodes;

    /**
     * Das 2-dimensionale Feld der Adjazenzmatrix.
     *
     * <p>
     * Heißt im Schulbuch: <code>matrix</code>.
     * </p>
     */
    private int[][] matrix;

    /**
     * Konstruktor für Objekte der Klasse GRAPH_MATRIX Die maximale Anzahl der
     * Knoten wird dabei festgelegt
     *
     * <p>
     * Heißt im Schulbuch: <code>GraphMatrix(int maximaleKnoten)</code>.
     * </p>
     *
     * @param maxNodes Anzahl der maximal möglichen Knoten
     */
    public GraphMatrix(int maxNodes)
    {
        nodeCount = 0;
        nodes = new Node[maxNodes];
        matrix = new int[maxNodes][maxNodes];
    }

    /**
     * Gibt die interne Nummer des Knoten zurück.
     *
     * <p>
     * Wenn ein Knoten mit diesem Bezeichner nicht bekannt ist wird -1
     * zurückgegeben. Heißt im Schulbuch:
     * <code>KnotenNummer(String bezeichner)</code>.
     * </p>
     *
     * @param label Bezeichner des Knoten der gesucht wird
     *
     * @return Indexnummer des Knotens im Knotenarray; 0&lt;= x &lt;= anzahl-1
     *     bzw. -1
     */
    private int getNodeIndex(String label)
    {
        for (int i = 0; i < nodeCount; i++)
        {
            if (nodes[i].getLabel().equals(label))
            {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gibt die Bezeichnung eines Knotens mit der internen Knotennummer.
     *
     * <p>
     * Heißt im Schulbuch: <code>KnotenBezeichnerGeben(int knotenNummer)</code>.
     * </p>
     *
     * @param index Indexnummer des Knotens im Knotenarray; 0&lt;= x &lt;=
     *     anzahl-1
     *
     * @return Bezeichner des Knoten
     */
    public String getNodeLabel(int index)
    {
        if (index < nodeCount && index >= 0)
        {
            return nodes[index].getLabel();
        }
        return "";
    }

    /**
     * Gibt die Anzahl der Knoten des Graphen
     *
     * <p>
     * Heißt im Schulbuch: <code>KnotenAnzahlgeben()</code>.
     * </p>
     *
     * @return Anzahl der Knoten
     */
    public int getNodesCount()
    {
        return nodeCount;
    }

    /**
     * Gibt die Gewichtung einer Kante zurück.
     *
     * <p>
     * Die Kante ist durch einen Anfangsknoten und einen Endknoten festgelegt.
     * Heißt im Schulbuch:
     * <code>KanteGewichtGeben(String von, String nach)</code>.
     * </p>
     *
     * @param from Bezeichner des Anfangsknotens
     * @param to Bezeichner des Endknotens
     *
     * @return Gewichtung der Kante
     */
    public int getEdgeWeight(String from, String to)
    {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex != -1 && toIndex != -1)
        {
            return matrix[fromIndex][toIndex];
        }
        return -1;
    }

    /**
     * <b>Einfügt</b> einen neuen Knoten in den Graphen <b>ein</b>.
     *
     * <p>
     * Wenn die maximale Anzahl an Knoten erreicht wird, dann erfolgt kein
     * Einfügen.
     * </p>
     *
     * @param label Der <b>Bezeichner</b> des neuen Knotens, der dem Graphen
     *     hinzugefügt wird.
     */
    public void addNode(String label)
    {
        addNode(label, 0, 0);
    }

    /**
     * <b>Einfügt</b> einen neuen Knoten in den Graphen <b>ein</b>.
     *
     * <p>
     * Wenn die maximale Anzahl an Knoten erreicht wird, dann erfolgt kein
     * Einfügen.
     * </p>
     *
     * @param label Der <b>Bezeichner</b> des neuen Knotens, der dem Graphen
     *     hinzugefügt wird.
     */
    public void addNode(String label, double x, double y)
    {
        if (nodeCount < nodes.length && getNodeIndex(label) == -1)
        {
            nodes[nodeCount] = new Node(label, x, y);
            matrix[nodeCount][nodeCount] = 0;
            for (int i = 0; i < nodeCount; i++)
            {
                // Symmetrie, da ungerichteter Graph
                matrix[nodeCount][i] = -1;
                matrix[i][nodeCount] = -1;
            }
            nodeCount++;
        }
    }

    /**
     * Einfügen einer Kante in den Graphen Eine Kante ist durch einen
     * Anfangsknoten und einen Endknoten festgelegt und hat eine Gewichtung
     *
     * @param from Der Bezeichner des Anfangsknotens.
     * @param to Der Bezeichner des Endknotens.
     * @param weight Die Gewichtung der Kante.
     */
    public void addEdge(String from, String to, int weight)
    {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex != -1 && toIndex != -1 && fromIndex != toIndex)
        {
            matrix[fromIndex][toIndex] = weight;
            matrix[toIndex][fromIndex] = weight;
        }
    }

    public void addDirectedEdge(String from, String to, int weight)
    {
        int fromIndex = getNodeIndex(from);
        int toIndex = getNodeIndex(to);
        if (fromIndex != -1 && toIndex != -1 && fromIndex != toIndex)
        {
            matrix[fromIndex][toIndex] = weight;
        }
    }

    /**
     * Gibt die Adjazenzmatrix des Graphen in der Konsole aus Nach Zeilen und
     * Spalten formatiert Als Spaltenbreite wurde hier 4 Zeichen gewählt.
     */
    public void print()
    {
        int width = 4;
        String whiteSpace = " ".repeat(4);
        // Kopfzeile
        System.out.print(whiteSpace);
        for (int i = 0; i < nodeCount; i++)
        {
            System.out.print(nodes[i].getFormattedLabel(width));
        }
        System.out.println();
        for (int i = 0; i < nodeCount; i++)
        {
            System.out.print(nodes[i].getFormattedLabel(width));
            for (int j = 0; j < nodeCount; j++)
            {
                if (matrix[i][j] != -1)
                {
                    System.out.print(
                            (matrix[i][j] + whiteSpace).substring(0, width));
                }
                else
                {
                    System.out.print(whiteSpace);
                }
            }
            System.out.println();
        }
    }

    public static GraphMatrix getHausDesNikolaus()
    {
        GraphMatrix g = new GraphMatrix(5);
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addEdge("A", "B", 1);
        g.addEdge("A", "D", 1);
        g.addEdge("A", "E", 1);
        g.addEdge("B", "C", 1);
        g.addEdge("B", "D", 1);
        g.addEdge("B", "E", 1);
        g.addEdge("C", "D", 1);
        g.addEdge("D", "E", 1);
        return g;
    }

    /**
     * <p>
     * Führt sämtliche Arbeiten zur Implementierung des Graphen entsprechend der
     * Vorgabe in der Teilaufgabe Autobahn aus Instanzieren des Graphenobjekts;
     * Einfügen der Knoten und Einfügen der Kanten; am Ende Ausgabe der
     * Adjazenzmatrix zur Kontrolle
     * </p>
     *
     * Heißt im Schulbuch: <code>Arbeit.AusfuehrenAutobahn()</code>.
     */
    public static GraphMatrix getHighway()
    {
        // Erzeugen eines Graphenobjekts g für 14 Knoten
        GraphMatrix g = new GraphMatrix(14);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("F");
        g.addNode("FD");
        g.addNode("HO");
        g.addNode("KA");
        g.addNode("LI");
        g.addNode("M");
        g.addNode("N");
        g.addNode("PA");
        g.addNode("R");
        g.addNode("RO");
        g.addNode("S");
        g.addNode("UL");
        g.addNode("WÜ");
        // Anlegen der Kanten
        g.addEdge("KA", "F", 127);
        g.addEdge("F", "WÜ", 131);
        g.addEdge("WÜ", "N", 104);
        g.addEdge("N", "R", 80);
        g.addEdge("R", "PA", 72);
        g.addEdge("HO", "WÜ", 192);
        g.addEdge("HO", "N", 116);
        g.addEdge("HO", "R", 166);
        g.addEdge("FD", "WÜ", 98);
        g.addEdge("M", "A", 64);
        g.addEdge("M", "N", 163);
        g.addEdge("M", "R", 117);
        g.addEdge("M", "RO", 60);
        g.addEdge("UL", "A", 59);
        g.addEdge("UL", "WÜ", 165);
        g.addEdge("UL", "LI", 126);
        g.addEdge("UL", "S", 103);
        g.addEdge("S", "KA", 53);
        g.addEdge("S", "WÜ", 155);
        return g;
    }

    /**
     * <p>
     * Führt sämtliche Arbeiten zur Implementierung des Graphen entsprechend der
     * Vorgabe in der Teilaufgabe Flugroute aus Instanzieren des Graphenobjekts;
     * Einfügen der Knoten und Einfügen der Kanten; am Ende Ausgabe der
     * Adjazenzmatrix zur Kontrolle
     * </p>
     */
    public static GraphMatrix getFlightRoute()
    {
        // Erzeugen eines Graphenobjekts g für 21 Knoten
        GraphMatrix g = new GraphMatrix(21);
        // Anlegen der Knoten
        g.addNode("AMS");
        g.addNode("ARN");
        g.addNode("BKK");
        g.addNode("CAI");
        g.addNode("CDG");
        g.addNode("DEL");
        g.addNode("DME");
        g.addNode("FRA");
        g.addNode("GRU");
        g.addNode("JFK");
        g.addNode("JNB");
        g.addNode("LHR");
        g.addNode("MAD");
        g.addNode("MEX");
        g.addNode("MUC");
        g.addNode("NRT");
        g.addNode("ORD");
        g.addNode("PEK");
        g.addNode("SFO");
        g.addNode("SIN");
        g.addNode("TXL");
        // Anlegen der Kanten mit der Gewichtung Flugzeit in Minuten
        g.addEdge("FRA", "AMS", 70);
        g.addEdge("FRA", "ARN", 125);
        g.addEdge("FRA", "BKK", 620);
        g.addEdge("FRA", "CAI", 235);
        g.addEdge("FRA", "CDG", 70);
        g.addEdge("FRA", "DEL", 440);
        g.addEdge("FRA", "DME", 195);
        g.addEdge("FRA", "GRU", 710);
        g.addEdge("FRA", "JFK", 525);
        g.addEdge("FRA", "JNB", 645);
        g.addEdge("FRA", "LHR", 100);
        g.addEdge("FRA", "MAD", 150);
        g.addEdge("FRA", "MEX", 705);
        g.addEdge("FRA", "MUC", 55);
        g.addEdge("FRA", "NRT", 660);
        g.addEdge("FRA", "ORD", 565);
        g.addEdge("FRA", "PEK", 550);
        g.addEdge("FRA", "SFO", 684);
        g.addEdge("FRA", "SIN", 720);
        g.addEdge("FRA", "TXL", 65);
        g.addEdge("MUC", "AMS", 95);
        g.addEdge("MUC", "ARN", 130);
        g.addEdge("MUC", "BKK", 620);
        g.addEdge("MUC", "CAI", 230);
        g.addEdge("MUC", "CDG", 100);
        g.addEdge("MUC", "DEL", 445);
        g.addEdge("MUC", "DME", 190);
        g.addEdge("MUC", "GRU", 760);
        g.addEdge("MUC", "JFK", 550);
        g.addEdge("MUC", "JNB", 645);
        g.addEdge("MUC", "LHR", 125);
        g.addEdge("MUC", "MAD", 155);
        g.addEdge("MUC", "NRT", 705);
        g.addEdge("MUC", "ORD", 600);
        g.addEdge("MUC", "PEK", 590);
        g.addEdge("MUC", "SFO", 730);
        g.addEdge("MUC", "SIN", 725);
        g.addEdge("MUC", "TXL", 70);
        g.addEdge("TXL", "ARN", 90);
        g.addEdge("TXL", "CDG", 105);
        g.addEdge("TXL", "DME", 165);
        return g;
    }

    /**
     * <p>
     * Führt sämtliche Arbeiten zur Implementierung des Graphen entsprechend der
     * Vorgabe in der Teilaufgabe ICE-Verbindungen aus Instanzieren des
     * Graphenobjekts; Einfügen der Knoten und Einfügen der Kanten; am Ende
     * Ausgabe der Adjazenzmatrix zur Kontrolle
     * </p>
     */
    public static GraphMatrix getICE()
    {
        // Erzeugen eines Graphenobjekts g für 16 Knoten
        GraphMatrix g = new GraphMatrix(16);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("B");
        g.addNode("D");
        g.addNode("DD");
        g.addNode("F");
        g.addNode("F2");
        g.addNode("FD");
        g.addNode("HH");
        g.addNode("H");
        g.addNode("K");
        g.addNode("L");
        g.addNode("M");
        g.addNode("MA");
        g.addNode("N");
        g.addNode("S");
        g.addNode("WÜ");
        // Anlegen der Kanten mit der Gewichtung Fahrzeit in Minuten
        g.addEdge("F2", "F", 10);
        g.addEdge("FD", "F", 52);
        g.addEdge("HH", "B", 143);
        g.addEdge("H", "B", 98);
        g.addEdge("H", "D", 100);
        g.addEdge("H", "FD", 88);
        g.addEdge("H", "HH", 74);
        g.addEdge("K", "D", 77);
        g.addEdge("K", "F2", 67);
        g.addEdge("L", "B", 67);
        g.addEdge("L", "DD", 73);
        g.addEdge("L", "FD", 151);
        g.addEdge("M", "A", 40);
        g.addEdge("MA", "F", 30);
        g.addEdge("MA", "F2", 30);
        g.addEdge("N", "A", 62);
        g.addEdge("N", "L", 189);
        g.addEdge("N", "M", 71);
        g.addEdge("S", "A", 98);
        g.addEdge("S", "MA", 37);
        g.addEdge("WÜ", "F", 70);
        g.addEdge("WÜ", "FD", 34);
        g.addEdge("WÜ", "N", 58);
        return g;
    }

    /**
     * Graph Seite 110, Gewichte nach k10_a3_1.png
     */
    public static GraphMatrix getBavarianHighwayGraph()
    {
        // Erzeugen eines Graphenobjekts g für 11 Knoten
        GraphMatrix g = new GraphMatrix(11);
        // Anlegen der Knoten
        g.addNode("A"); // Augsburg
        g.addNode("FD"); // Fulda
        g.addNode("HO"); // Hof
        g.addNode("LI"); // Lindau
        g.addNode("M"); // München
        g.addNode("N"); // Nürnberg
        g.addNode("R"); // Regensburg
        g.addNode("RO"); // Rosenheim
        g.addNode("S"); // Stuttgart
        g.addNode("UL"); // Ulm
        g.addNode("WÜ"); // Würzburg
        // Anlegen der Kanten
        // A
        g.addEdge("A", "M", 64);
        g.addEdge("A", "UL", 59);
        // FD
        g.addEdge("FD", "WÜ", 148);
        // HO
        g.addEdge("HO", "N", 116);
        g.addEdge("HO", "R", 167);
        g.addEdge("HO", "WÜ", 192);
        // LI
        g.addEdge("LI", "UL", 111);
        // M
        g.addEdge("M", "N", 143);
        g.addEdge("M", "R", 96);
        g.addEdge("M", "RO", 96);
        // N
        g.addEdge("N", "R", 80);
        g.addEdge("N", "WÜ", 104);
        // S
        g.addEdge("S", "UL", 103);
        // UL
        g.addEdge("UL", "WÜ", 165);
        return g;
    }

    /**
     * <p>
     * Führt sämtliche Arbeiten zur Implementierung des Graphen entsprechend der
     * Vorgabe in der Teilaufgabe Autobahnkartenausschnitt aus: Instanzieren des
     * Graphenobjekts; Einfügen der Knoten und Einfügen der Kanten; am Ende
     * Ausgabe der Adjazenzmatrix zur Kontrolle
     * </p>
     */
    public static GraphMatrix getHighwaySelection()
    {
        // Erzeugen eines Graphenobjekts g für 14 Knoten
        GraphMatrix g = new GraphMatrix(14);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("F");
        g.addNode("FD");
        g.addNode("HO");
        g.addNode("KA");
        g.addNode("LI");
        g.addNode("M");
        g.addNode("N");
        g.addNode("PA");
        g.addNode("R");
        g.addNode("RO");
        g.addNode("S");
        g.addNode("UL");
        g.addNode("WÜ");
        // Anlegen der Kanten mit der Gewichtung AutobahnKilometer
        g.addEdge("KA", "F", 127);
        g.addEdge("F", "WÜ", 131);
        g.addEdge("WÜ", "N", 104);
        g.addEdge("N", "R", 80);
        g.addEdge("R", "PA", 72);
        g.addEdge("HO", "WÜ", 192);
        g.addEdge("HO", "N", 116);
        g.addEdge("HO", "R", 166);
        g.addEdge("FD", "WÜ", 98);
        g.addEdge("M", "A", 64);
        g.addEdge("M", "N", 163);
        g.addEdge("M", "R", 117);
        g.addEdge("M", "RO", 60);
        g.addEdge("UL", "A", 59);
        g.addEdge("UL", "WÜ", 165);
        g.addEdge("UL", "LI", 126);
        g.addEdge("UL", "S", 103);
        g.addEdge("S", "KA", 53);
        // g.addEdge("S", "WÜ", 155);
        return g;
    }

    /**
     * <p>
     * Führt sämtliche Arbeiten zur Implementierung des Graphen entsprechend der
     * Vorgabe in der Teilaufgabe S- und U-Bahn-Verbindungen aus: Instanzieren
     * des Graphenobjekts; Einfügen der Knoten und Einfügen der Kanten; am Ende
     * Ausgabe der Adjazenzmatrix zur Kontrolle
     * </p>
     */
    public static GraphMatrix getSubway()
    {
        // Erzeugen eines Graphenobjekts g für 41 Knoten
        GraphMatrix g = new GraphMatrix(41);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("Altomünster");
        g.addNode("Arabellapark");
        g.addNode("Dachau");
        g.addNode("Donnersbergerbrücke");
        g.addNode("Ebersberg");
        g.addNode("Erding");
        g.addNode("Feldmoching");
        g.addNode("Flughafen");
        g.addNode("Freising");
        g.addNode("Fürstenried");
        g.addNode("Garching");
        g.addNode("Geltendorf");
        g.addNode("Giesing");
        g.addNode("Großhadern");
        g.addNode("Harras");
        g.addNode("Hauptbahnhof");
        g.addNode("Heimeranplatz");
        g.addNode("Herrsching");
        g.addNode("Holzkirchen");
        g.addNode("Innsbrucker Ring");
        g.addNode("Karlsplatz");
        g.addNode("Kreuzstraße");
        g.addNode("Laim");
        g.addNode("Laimer Platz");
        g.addNode("Mammendorf");
        g.addNode("Mangfallplatz");
        g.addNode("Marienplatz");
        g.addNode("Messe");
        g.addNode("Neufahrn");
        g.addNode("Neuperlach");
        g.addNode("Odeonsplatz");
        g.addNode("OEZ");
        g.addNode("Ostbahnhof");
        g.addNode("Pasing");
        g.addNode("Petershausen");
        g.addNode("Scheidplatz");
        g.addNode("Sendlinger Tor");
        g.addNode("Trudering");
        g.addNode("Tutzing");
        g.addNode("Wolfratshausen");
        // Anlegen der Kanten mit der Gewichtung "ist verbunden"
        g.addEdge("Dachau", "Altomünster", 1);
        g.addEdge("Harras", "Großhadern", 1);
        g.addEdge("Hauptbahnhof", "Donnersbergerbrücke", 1);
        g.addEdge("Heimeranplatz", "Donnersbergerbrücke", 1);
        g.addEdge("Heimeranplatz", "Harras", 1);
        g.addEdge("Heimeranplatz", "Hauptbahnhof", 1);
        g.addEdge("Holzkirchen", "Giesing", 1);
        g.addEdge("Innsbrucker Ring", "Giesing", 1);
        g.addEdge("Karlsplatz", "Hauptbahnhof", 1);
        g.addEdge("Laim", "Dachau", 1);
        g.addEdge("Laim", "Donnersbergerbrücke", 1);
        g.addEdge("Laim", "Feldmoching", 1);
        g.addEdge("Laimer Platz", "Heimeranplatz", 1);
        g.addEdge("Marienplatz", "Karlsplatz", 1);
        g.addEdge("Neufahrn", "Feldmoching", 1);
        g.addEdge("Neufahrn", "Flughafen", 1);
        g.addEdge("Neufahrn", "Freising", 1);
        g.addEdge("Neuperlach", "Giesing", 1);
        g.addEdge("Neuperlach", "Innsbrucker Ring", 1);
        g.addEdge("Neuperlach", "Kreuzstraße", 1);
        g.addEdge("Odeonsplatz", "Arabellapark", 1);
        g.addEdge("Odeonsplatz", "Garching", 1);
        g.addEdge("Odeonsplatz", "Karlsplatz", 1);
        g.addEdge("Odeonsplatz", "Marienplatz", 1);
        g.addEdge("OEZ", "Hauptbahnhof", 1);
        g.addEdge("Ostbahnhof", "Erding", 1);
        g.addEdge("Ostbahnhof", "Flughafen", 1);
        g.addEdge("Ostbahnhof", "Giesing", 1);
        g.addEdge("Ostbahnhof", "Innsbrucker Ring", 1);
        g.addEdge("Ostbahnhof", "Marienplatz", 1);
        g.addEdge("Ostbahnhof", "Odeonsplatz", 1);
        g.addEdge("Pasing", "Geltendorf", 1);
        g.addEdge("Pasing", "Herrsching", 1);
        g.addEdge("Pasing", "Laim", 1);
        g.addEdge("Pasing", "Mammendorf", 1);
        g.addEdge("Petershausen", "Dachau", 1);
        g.addEdge("Scheidplatz", "Feldmoching", 1);
        g.addEdge("Scheidplatz", "Hauptbahnhof", 1);
        g.addEdge("Scheidplatz", "OEZ", 1);
        g.addEdge("Sendlinger Tor", "Fürstenried", 1);
        g.addEdge("Sendlinger Tor", "Giesing", 1);
        g.addEdge("Sendlinger Tor", "Harras", 1);
        g.addEdge("Sendlinger Tor", "Hauptbahnhof", 1);
        g.addEdge("Sendlinger Tor", "Marienplatz", 1);
        g.addEdge("Trudering", "Ebersberg", 1);
        g.addEdge("Trudering", "Innsbrucker Ring", 1);
        g.addEdge("Trudering", "Messe", 1);
        g.addEdge("Trudering", "Ostbahnhof", 1);
        g.addEdge("Tutzing", "Pasing", 1);
        g.addEdge("Wolfratshausen", "Harras", 1);
        return g;
    }

    /**
     * Buchner Seite 45
     */
    public static GraphMatrix getFahrzeitenZweispurig()
    {
        GraphMatrix g = new GraphMatrix(6);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addNode("F");
        // Anlegen der Kanten
        g.addEdge("A", "B", 7);
        g.addEdge("A", "C", 9);
        g.addEdge("A", "F", 14);
        g.addEdge("B", "C", 10);
        g.addEdge("B", "D", 15);
        g.addEdge("C", "D", 11);
        g.addEdge("C", "F", 2);
        g.addEdge("D", "E", 6);
        g.addEdge("E", "F", 9);
        return g;
    }

    /**
     * Buchner Seite 45
     */
    public static GraphMatrix getFahrzeitenZweispurigDirected()
    {
        GraphMatrix g = new GraphMatrix(6);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addNode("F");
        // Anlegen der Kanten
        g.addEdge("A", "B", 7);
        g.addEdge("A", "C", 9);
        g.addEdge("A", "F", 14);
        g.addEdge("B", "C", 10);
        g.addEdge("B", "D", 15);
        g.addEdge("C", "D", 11);
        g.addDirectedEdge("E", "D", 6);
        g.addEdge("E", "F", 9);
        g.addDirectedEdge("F", "C", 2);
        return g;
    }

    public static GraphMatrix getNuernbergUlmMuenchenHamburg()
    {
        GraphMatrix g = new GraphMatrix(11);
        // Anlegen der Knoten
        g.addNode("BA");
        g.addNode("BT");
        g.addNode("FD");
        g.addNode("H");
        g.addNode("HH");
        g.addNode("LG");
        g.addNode("M");
        g.addNode("MD");
        g.addNode("N");
        g.addNode("UL");
        g.addNode("WÜ");
        // Anlegen der Kanten
        g.addEdge("M", "N", 143);
        g.addEdge("M", "UL", 144);
        g.addEdge("HH", "H", 150);
        g.addEdge("UL", "WÜ", 196);
        g.addEdge("WÜ", "FD", 101);
        g.addEdge("FD", "H", 261);
        g.addEdge("HH", "LG", 50);
        g.addEdge("LG", "MD", 202);
        g.addEdge("MD", "BA", 342);
        g.addEdge("MD", "BT", 296);
        g.addEdge("FD", "BA", 150);
        g.addEdge("N", "BA", 61);
        g.addEdge("N", "BT", 72);
        g.addEdge("WÜ", "N", 109);
        return g;
    }

    public static void main(String[] args)
    {
        // getBavarianHighwayGraph().startDepthFirstSearch("N");
        // getBavarianHighwayGraph().searchShortestPath("N", "UL");
        // getFahrzeitenZweispurig().searchShortestPath("A", "E");
        // getFahrzeitenZweispurigDirected().searchShortestPath("A", "E");
        // getNuernbergUlmMuenchenHamburg().searchShortestPath("N", "UL");
        // getNuernbergUlmMuenchenHamburg().searchShortestPath("M", "HH");
    }
}
