package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

/**
 * Eine Sammlung an Graphen. Die Graphen stammen größtenteils aus Schulbüchern.
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphCollection
{

    /**
     * Ein Graph, der wie das Haus des Nikolaus aufgebaut ist.
     */
    public static GraphArrayMatrix HausDesNikolaus()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(5);
        // Anlegen der Knoten
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        // Anlegen der Kanten
        g.addEdge("A", "B");
        g.addEdge("A", "D");
        g.addEdge("A", "E");
        g.addEdge("B", "C");
        g.addEdge("B", "D");
        g.addEdge("B", "E");
        g.addEdge("C", "D");
        g.addEdge("D", "E");
        return g;
    }

    /**
     * Heißt im Schulbuch: <code>Arbeit.AusfuehrenAutobahn()</code>.
     */
    public static GraphArrayMatrix OldenburgHighway()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(14);
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

    public static GraphArrayMatrix OldenburgFlightRoute()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(21);
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
     * ICE-Verbindungen
     */
    public static GraphArrayMatrix OldenburgICE()
    {
        // Erzeugen eines Graphenobjekts g für 16 Knoten
        GraphArrayMatrix g = new GraphArrayMatrix(16);
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
    public static GraphArrayMatrix OldenburgBavarianHighwayGraph()
    {
        // Erzeugen eines Graphenobjekts g für 11 Knoten
        GraphArrayMatrix g = new GraphArrayMatrix(11);
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
     * Autobahnkartenausschnitt
     */
    public static GraphArrayMatrix OldenburgHighwaySelection()
    {
        // Erzeugen eines Graphenobjekts g für 14 Knoten
        GraphArrayMatrix g = new GraphArrayMatrix(14);
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
     * S- und U-Bahn-Verbindungen
     */
    public static GraphArrayMatrix OldenburgSubway()
    {
        // Erzeugen eines Graphenobjekts g für 41 Knoten
        GraphArrayMatrix g = new GraphArrayMatrix(41);
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
        g.addEdge("Dachau", "Altomünster");
        g.addEdge("Harras", "Großhadern");
        g.addEdge("Hauptbahnhof", "Donnersbergerbrücke");
        g.addEdge("Heimeranplatz", "Donnersbergerbrücke");
        g.addEdge("Heimeranplatz", "Harras");
        g.addEdge("Heimeranplatz", "Hauptbahnhof");
        g.addEdge("Holzkirchen", "Giesing");
        g.addEdge("Innsbrucker Ring", "Giesing");
        g.addEdge("Karlsplatz", "Hauptbahnhof");
        g.addEdge("Laim", "Dachau");
        g.addEdge("Laim", "Donnersbergerbrücke");
        g.addEdge("Laim", "Feldmoching");
        g.addEdge("Laimer Platz", "Heimeranplatz");
        g.addEdge("Marienplatz", "Karlsplatz");
        g.addEdge("Neufahrn", "Feldmoching");
        g.addEdge("Neufahrn", "Flughafen");
        g.addEdge("Neufahrn", "Freising");
        g.addEdge("Neuperlach", "Giesing");
        g.addEdge("Neuperlach", "Innsbrucker Ring");
        g.addEdge("Neuperlach", "Kreuzstraße");
        g.addEdge("Odeonsplatz", "Arabellapark");
        g.addEdge("Odeonsplatz", "Garching");
        g.addEdge("Odeonsplatz", "Karlsplatz");
        g.addEdge("Odeonsplatz", "Marienplatz");
        g.addEdge("OEZ", "Hauptbahnhof");
        g.addEdge("Ostbahnhof", "Erding");
        g.addEdge("Ostbahnhof", "Flughafen");
        g.addEdge("Ostbahnhof", "Giesing");
        g.addEdge("Ostbahnhof", "Innsbrucker Ring");
        g.addEdge("Ostbahnhof", "Marienplatz");
        g.addEdge("Ostbahnhof", "Odeonsplatz");
        g.addEdge("Pasing", "Geltendorf");
        g.addEdge("Pasing", "Herrsching");
        g.addEdge("Pasing", "Laim");
        g.addEdge("Pasing", "Mammendorf");
        g.addEdge("Petershausen", "Dachau");
        g.addEdge("Scheidplatz", "Feldmoching");
        g.addEdge("Scheidplatz", "Hauptbahnhof");
        g.addEdge("Scheidplatz", "OEZ");
        g.addEdge("Sendlinger Tor", "Fürstenried");
        g.addEdge("Sendlinger Tor", "Giesing");
        g.addEdge("Sendlinger Tor", "Harras");
        g.addEdge("Sendlinger Tor", "Hauptbahnhof");
        g.addEdge("Sendlinger Tor", "Marienplatz");
        g.addEdge("Trudering", "Ebersberg");
        g.addEdge("Trudering", "Innsbrucker Ring");
        g.addEdge("Trudering", "Messe");
        g.addEdge("Trudering", "Ostbahnhof");
        g.addEdge("Tutzing", "Pasing");
        g.addEdge("Wolfratshausen", "Harras");
        return g;
    }

    /**
     * Buchner Seite 45
     */
    public static GraphArrayMatrix Buchner11FahrzeitenZweispurig()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(6);
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
    public static GraphArrayMatrix Buchner11FahrzeitenZweispurigDirected()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(6);
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
        g.addEdge("E", "D", 6, true);
        g.addEdge("E", "F", 9);
        g.addEdge("F", "C", 2, true);
        return g;
    }

    public static GraphArrayMatrix getNuernbergUlmMuenchenHamburg()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(11);
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

    /**
     * {@code Abiturfahrt.grdb}
     */
    public static GraphArrayMatrix Cornelsen6Abiturfahrt()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("Bologna", 1.6875, 0.0);
        g.addNode("Innsbruck", 1.5, 6.65625);
        g.addNode("München", 0.9375, 8.875);
        g.addNode("Nürnberg", 0.03125, 11.8125);
        g.addNode("Salzburg", 4.125, 8.4375);
        g.addNode("Venedig", 3.59375, 2.65625);
        g.addNode("Verona", 0.0, 2.5625);
        // Anlegen der Kanten
        g.addEdge("Nürnberg", "München", 170);
        g.addEdge("München", "Innsbruck", 150);
        g.addEdge("Innsbruck", "Verona", 270);
        g.addEdge("Verona", "Bologna", 140);
        g.addEdge("Verona", "Venedig", 110);
        g.addEdge("Venedig", "Salzburg", 450);
        g.addEdge("München", "Salzburg", 145);
        return g;
    }

    /**
     * {@code Autobahn.grdb}
     */
    public static GraphArrayMatrix Cornelsen6Autobahn()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 6.15625, 1.96875);
        g.addNode("FD", 4.03125, 10.3125);
        g.addNode("HO", 10.5625, 10.28125);
        g.addNode("LI", 2.0625, 0.0);
        g.addNode("M", 10.03125, 1.09375);
        g.addNode("N", 8.78125, 6.40625);
        g.addNode("R", 13.125, 4.90625);
        g.addNode("RO", 14.03125, 0.40625);
        g.addNode("S", 0.0, 4.65625);
        g.addNode("UL", 3.03125, 2.90625);
        g.addNode("WÜ", 3.9375, 7.3125);
        // Anlegen der Kanten
        g.addEdge("WÜ", "FD", 86);
        g.addEdge("S", "UL", 103);
        g.addEdge("UL", "WÜ", 165);
        g.addEdge("UL", "LI", 126);
        g.addEdge("UL", "A", 59);
        g.addEdge("A", "M", 64);
        g.addEdge("M", "N", 163);
        g.addEdge("WÜ", "N", 104);
        g.addEdge("N", "HO", 116);
        g.addEdge("N", "R", 80);
        g.addEdge("R", "M", 117);
        g.addEdge("M", "RO", 60);
        g.addEdge("HO", "R", 166);
        g.addEdge("WÜ", "HO", 192);
        return g;
    }

    /**
     * {@code ICENetz.grdb}
     */
    public static GraphArrayMatrix Cornelsen6ICENetz()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 9.5625, 1.1875);
        g.addNode("B", 10.96875, 13.96875);
        g.addNode("D", 1.59375, 11.71875);
        g.addNode("DD", 14.3125, 9.3125);
        g.addNode("F", 4.03125, 7.375);
        g.addNode("F2", 2.125, 6.5);
        g.addNode("FD", 6.5, 9.25);
        g.addNode("H", 5.65625, 12.9375);
        g.addNode("HH", 5.6875, 15.53125);
        g.addNode("K", 0.0, 8.8125);
        g.addNode("L", 10.75, 10.8125);
        g.addNode("M", 12.34375, 0.0);
        g.addNode("MA", 2.78125, 3.65625);
        g.addNode("N", 10.65625, 4.25);
        g.addNode("S", 5.625, 2.53125);
        g.addNode("WÜ", 7.9375, 6.3125);
        // Anlegen der Kanten
        g.addEdge("HH", "H", 74);
        g.addEdge("B", "H", 98);
        g.addEdge("H", "D", 100);
        g.addEdge("D", "K", 77);
        g.addEdge("K", "F2", 67);
        g.addEdge("F2", "F", 10);
        g.addEdge("F", "FD", 52);
        g.addEdge("FD", "H", 88);
        g.addEdge("FD", "L", 151);
        g.addEdge("L", "B", 67);
        g.addEdge("L", "DD", 73);
        g.addEdge("FD", "WÜ", 34);
        g.addEdge("F", "WÜ", 70);
        g.addEdge("WÜ", "N", 58);
        g.addEdge("L", "N", 189);
        g.addEdge("F2", "MA", 30);
        g.addEdge("MA", "S", 37);
        g.addEdge("S", "A", 98);
        g.addEdge("A", "N", 62);
        g.addEdge("N", "M", 71);
        g.addEdge("M", "A", 40);
        g.addEdge("HH", "B", 143);
        return g;
    }

    /**
     * {@code Beispielgraph.grdb}
     */
    public static GraphArrayMatrix Cornelsen6Beispielgraph()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 0.0, 0.0);
        g.addNode("B", 2.34375, 0.46875);
        g.addNode("C", 6.65625, 1.15625);
        g.addNode("D", 8.625, 2.0625);
        g.addNode("E", 12.875, 2.84375);
        g.addNode("F", 15.8125, 1.0);
        g.addNode("G", 19.9375, 1.875);
        g.addNode("H", 19.59375, 4.375);
        g.addNode("I", 0.6875, 4.09375);
        g.addNode("J", 3.375, 4.5625);
        g.addNode("K", 6.59375, 4.125);
        g.addNode("L", 0.8125, 6.78125);
        g.addNode("M", 5.75, 6.5);
        g.addNode("N", 9.65625, 6.1875);
        g.addNode("O", 14.84375, 7.09375);
        g.addNode("P", 2.3125, 9.5);
        g.addNode("Q", 7.8125, 9.1875);
        g.addNode("R", 11.34375, 9.3125);
        g.addNode("S", 16.3125, 9.5);
        g.addNode("T", 0.65625, 10.96875);
        g.addNode("U", 4.96875, 11.84375);
        g.addNode("V", 10.1875, 11.5);
        g.addNode("W", 19.21875, 10.90625);
        // Anlegen der Kanten
        g.addEdge("I", "A");
        g.addEdge("A", "B");
        g.addEdge("B", "I");
        g.addEdge("I", "J");
        g.addEdge("J", "C");
        g.addEdge("B", "C");
        g.addEdge("J", "K");
        g.addEdge("K", "C");
        g.addEdge("C", "D");
        g.addEdge("D", "E");
        g.addEdge("E", "F");
        g.addEdge("F", "G");
        g.addEdge("E", "H");
        g.addEdge("G", "H");
        g.addEdge("L", "I");
        g.addEdge("M", "K");
        g.addEdge("K", "N");
        g.addEdge("E", "O");
        g.addEdge("O", "H");
        g.addEdge("P", "L");
        g.addEdge("M", "P");
        g.addEdge("M", "Q");
        g.addEdge("N", "Q");
        g.addEdge("N", "R");
        g.addEdge("R", "S");
        g.addEdge("S", "O");
        g.addEdge("T", "P");
        g.addEdge("P", "U");
        g.addEdge("U", "T");
        g.addEdge("U", "V");
        g.addEdge("V", "R");
        g.addEdge("S", "W");
        g.addEdge("W", "H");
        g.addEdge("N", "E");
        return g;
    }

    /**
     * {@code Beispielgraph2.grdb}
     */
    public static GraphArrayMatrix Cornelsen6Beispielgraph2()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        g.addNode("A", 0.0, 0.0);
        g.addNode("B", 2.34375, 0.46875);
        g.addNode("C", 6.65625, 1.15625);
        g.addNode("D", 8.625, 2.0625);
        g.addNode("E", 12.875, 2.84375);
        g.addNode("F", 15.8125, 1.0);
        g.addNode("G", 19.9375, 1.875);
        g.addNode("H", 19.59375, 4.375);
        g.addNode("I", 0.6875, 4.09375);
        g.addNode("J", 3.375, 4.5625);
        g.addNode("K", 6.59375, 4.125);
        g.addNode("L", 0.8125, 6.78125);
        g.addNode("M", 5.75, 6.5);
        g.addNode("N", 9.65625, 6.1875);
        g.addNode("O", 14.84375, 7.09375);
        g.addNode("P", 2.3125, 9.5);
        g.addNode("Q", 7.8125, 9.1875);
        g.addNode("R", 11.34375, 9.3125);
        g.addNode("S", 16.3125, 9.5);
        g.addNode("T", 0.65625, 10.96875);
        g.addNode("U", 4.96875, 11.84375);
        g.addNode("V", 10.1875, 11.5);
        g.addNode("W", 19.21875, 10.90625);
        g.addEdge("I", "A");
        g.addEdge("A", "B");
        g.addEdge("B", "I");
        g.addEdge("I", "J");
        g.addEdge("J", "C");
        g.addEdge("B", "C");
        g.addEdge("J", "K");
        g.addEdge("K", "C");
        g.addEdge("C", "D");
        g.addEdge("E", "F");
        g.addEdge("F", "G");
        g.addEdge("E", "H");
        g.addEdge("G", "H");
        g.addEdge("L", "I");
        g.addEdge("M", "K");
        g.addEdge("K", "N");
        g.addEdge("E", "O");
        g.addEdge("O", "H");
        g.addEdge("P", "L");
        g.addEdge("M", "P");
        g.addEdge("M", "Q");
        g.addEdge("N", "Q");
        g.addEdge("N", "R");
        g.addEdge("S", "O");
        g.addEdge("T", "P");
        g.addEdge("P", "U");
        g.addEdge("U", "T");
        g.addEdge("U", "V");
        g.addEdge("V", "R");
        g.addEdge("S", "W");
        g.addEdge("W", "H");
        return g;
    }
}
