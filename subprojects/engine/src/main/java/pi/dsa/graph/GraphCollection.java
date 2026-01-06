/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2025 Josef Friedrich and contributors.
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
package pi.dsa.graph;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import pi.dsa.Source;

/**
 * Eine <b>Sammlung an Graphen</b>, die größtenteils aus Schulbüchern stammen.
 *
 * <ul>
 * <li>Informatik 11, Buchner, 2023</li>
 * <li>Informatik 6 Bayern | NTG | Grundlegendes Niveau, Listen | Bäume |
 * Rekursion - Nebenläufigkeit und Threads - Informationssicherheit -
 * Softwareprojekte, Cornelsen, 2024</li>
 * <li>Informatik Oberstufe 1, Datenstrukturen und Softwareentwicklung,
 * Oldenbourg, 2009</li>
 * <li>Informatik 11 spätbeginnend, Algorithmen | Codierung | Kommunikation in
 * Netzwerken | Künstliche Intelligenz, Cornelsen, 2023</li>
 * </ul>
 *
 * @author Josef Friedrich
 *
 * @since 0.36.0
 */
public class GraphCollection
{

    /**
     * Entspricht der Graphdatenbank {@code Abiturfahrt.grdb} aus dem Schulbuch
     * des Cornelsen Verlags „Informatik 6 grundlegendes Niveau“ von 2024.
     */
    @Source(filename = "Abiturfahrt.grdb", title = "Informatik 6 Bayern | NTG | Grundlegendes Niveau", subtitle = "Listen | Bäume | Rekursion - Nebenläufigkeit und Threads - Informationssicherheit - Softwareprojekte", publisher = "Cornelsen", releaseYear = 2024)
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
     * Entspricht der Graphdatenbank {@code Autobahn.grdb} aus dem Schulbuch des
     * Cornelsen Verlags „Informatik 6 grundlegendes Niveau“ von 2024.
     *
     * Auch in Oldenbourg Graph Seite 110, Gewichte nach k10_a3_1.png (Gewichte
     * etwas anders)
     */
    @Source(filename = "Autobahn.grdb", title = "Informatik 6 Bayern | NTG | Grundlegendes Niveau", subtitle = "Listen | Bäume | Rekursion - Nebenläufigkeit und Threads - Informationssicherheit - Softwareprojekte", publisher = "Cornelsen", releaseYear = 2024)
    public static GraphArrayMatrix Cornelsen6Autobahn()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 6.15625, 1.96875); // Augsburg
        g.addNode("FD", 4.03125, 10.3125); // Fulda
        g.addNode("HO", 10.5625, 10.28125); // Hof
        g.addNode("LI", 2.0625, 0.0); // Lindau
        g.addNode("M", 10.03125, 1.09375); // München
        g.addNode("N", 8.78125, 6.40625); // Nürnberg
        g.addNode("R", 13.125, 4.90625); // Regensburg
        g.addNode("RO", 14.03125, 0.40625); // Rosenheim
        g.addNode("S", 0.0, 4.65625); // Stuttgart
        g.addNode("UL", 3.03125, 2.90625); // Ulm
        g.addNode("WÜ", 3.9375, 7.3125); // Würzburg
        // Anlegen der Kanten

        // A
        g.addEdge("A", "M", 64);
        g.addEdge("A", "UL", 59);
        // FD
        g.addEdge("FD", "WÜ", 86);
        // HO
        g.addEdge("HO", "N", 116);
        g.addEdge("HO", "R", 166);
        g.addEdge("HO", "WÜ", 192);
        // LI
        g.addEdge("LI", "UL", 126);
        // M
        g.addEdge("M", "N", 143);
        g.addEdge("M", "R", 117);
        g.addEdge("M", "RO", 60);
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
     * Entspricht der Graphdatenbank {@code ICENetz.grdb} aus dem Schulbuch des
     * Cornelsen Verlags „Informatik 6 grundlegendes Niveau“ von 2024.
     */
    @Source(filename = "ICENetz.grdb", title = "Informatik 6 Bayern | NTG | Grundlegendes Niveau", subtitle = "Listen | Bäume | Rekursion - Nebenläufigkeit und Threads - Informationssicherheit - Softwareprojekte", publisher = "Cornelsen", releaseYear = 2024)
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
     * Entspricht der Graphdatenbank {@code Beispielgraph.grdb} aus dem
     * Schulbuch des Cornelsen Verlags „Informatik 6 grundlegendes Niveau“ von
     * 2024.
     */
    @Source(filename = "Beispielgraph.grdb", title = "Informatik 6 Bayern | NTG | Grundlegendes Niveau", subtitle = "Listen | Bäume | Rekursion - Nebenläufigkeit und Threads - Informationssicherheit - Softwareprojekte", publisher = "Cornelsen", releaseYear = 2024)
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
     * Entspricht der Graphdatenbank {@code Beispielgraph2.grdb} aus dem
     * Schulbuch des Cornelsen Verlags „Informatik 6 grundlegendes Niveau“ von
     * 2024.
     */
    @Source(filename = "Beispielgraph2.grdb", title = "Informatik 6 Bayern | NTG | Grundlegendes Niveau", subtitle = "Listen | Bäume | Rekursion - Nebenläufigkeit und Threads - Informationssicherheit - Softwareprojekte", publisher = "Cornelsen", releaseYear = 2024)
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

    /**
     * Ein Graph, der wie das Haus des Nikolaus aufgebaut ist.
     */
    @Source(filename = "k11_a2_1.png", title = "Informatik Oberstufe 1", subtitle = "Datenstrukturen und Softwareentwicklung", publisher = "Oldenbourg", releaseYear = 2009)
    public static GraphArrayMatrix HausDesNikolaus()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(5);
        // Anlegen der Knoten
        // Anlegen der Knoten
        g.addNode("A", 1.49, 1.69);
        g.addNode("B", 1.80, 6.84);
        g.addNode("C", 5.11, 11.16);
        g.addNode("D", 8.21, 7.06);
        g.addNode("E", 8.02, 1.44);
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
     *
     * @see #Cornelsen6Autobahn()
     */
    @Source(filename = "k10_a1_2.png", title = "Informatik Oberstufe 1", subtitle = "Datenstrukturen und Softwareentwicklung", publisher = "Oldenbourg", releaseYear = 2009)
    public static GraphArrayMatrix OldenbourgHighway()
    {
        GraphArrayMatrix g = Cornelsen6Autobahn();
        // Anlegen der Knoten
        g.addNode("F");

        g.addNode("KA");

        g.addNode("PA");

        // Anlegen der Kanten
        g.addEdge("KA", "F", 127);
        g.addEdge("F", "WÜ", 131);
        g.addEdge("R", "PA", 72);
        g.addEdge("S", "KA", 53);
        return g;
    }

    @Source(filename = "k10_a1_2.png", title = "Informatik Oberstufe 1", subtitle = "Datenstrukturen und Softwareentwicklung", publisher = "Oldenbourg", releaseYear = 2009)
    public static GraphArrayMatrix OldenbourgFlightRoute()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("AMS", 6.31, 15.43);
        g.addNode("ARN", 10.63, 18.33);
        g.addNode("BKK", 22.16, 3.93);
        g.addNode("CAI", 16.28, 0.77);
        g.addNode("CDG", 4.13, 8.96);
        g.addNode("DEL", 21.81, 1.43);
        g.addNode("DME", 20.94, 15.24);
        g.addNode("FRA", 10.94, 10.83);
        g.addNode("GRU", 3.13, 3.36);
        g.addNode("JFK", 3.41, 12.11);
        g.addNode("JNB", 7.59, 0.86);
        g.addNode("LHR", 8.22, 17.08);
        g.addNode("MAD", 7.78, 5.80);
        g.addNode("MEX", 1.47, 5.80);
        g.addNode("MUC", 11.53, 3.49);
        g.addNode("NRT", 22.78, 9.14);
        g.addNode("ORD", 1.75, 15.21);
        g.addNode("PEK", 22.13, 12.18);
        g.addNode("SFO", 0.53, 10.36);
        g.addNode("SIN", 22.59, 6.43);
        g.addNode("TXL", 13.78, 14.58);
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
    @Source(filename = "k10_a2_1.png", title = "Informatik Oberstufe 1", subtitle = "Datenstrukturen und Softwareentwicklung", publisher = "Oldenbourg", releaseYear = 2009)
    public static GraphArrayMatrix OldenbourgICE()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 10.90, 1.58);
        g.addNode("B", 13.15, 17.86);
        g.addNode("D", 2.15, 14.71);
        g.addNode("DD", 16.12, 12.49);
        g.addNode("F", 4.94, 9.74);
        g.addNode("F2", 2.25, 8.71);
        g.addNode("FD", 7.59, 11.02);
        g.addNode("H", 7.09, 16.71);
        g.addNode("HH", 7.09, 20.86);
        g.addNode("K", 0.34, 11.80);
        g.addNode("L", 12.62, 13.64);
        g.addNode("M", 13.81, 0.80);
        g.addNode("MA", 3.12, 5.30);
        g.addNode("N", 12.72, 5.18);
        g.addNode("S", 6.56, 2.68);
        g.addNode("WÜ", 9.12, 7.33);
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
     * S- und U-Bahn-Verbindungen
     */
    @Source(filename = "k10_a4_1.png", title = "Informatik Oberstufe 1", subtitle = "Datenstrukturen und Softwareentwicklung", publisher = "Oldenbourg", releaseYear = 2009)
    public static GraphArrayMatrix OldenburgSubway()
    {
        // Erzeugen eines Graphenobjekts g für 41 Knoten
        GraphArrayMatrix g = new GraphArrayMatrix(41);
        // Anlegen der Knoten
        g.addNode("Altomünster", 3.73, 19.96);
        g.addNode("Arabellapark", 23.64, 16.37);
        g.addNode("Dachau", 6.55, 15.90);
        g.addNode("Donnersbergerbrücke", 11.08, 12.71);
        g.addNode("Ebersberg", 32.73, 2.12);
        g.addNode("Erding", 29.61, 19.21);
        g.addNode("Feldmoching", 10.20, 17.40);
        g.addNode("Flughafen", 27.42, 21.75);
        g.addNode("Freising", 17.76, 22.46);
        g.addNode("Fürstenried", 9.73, 4.18);
        g.addNode("Garching", 20.58, 19.81);
        g.addNode("Geltendorf", 1.01, 3.31);
        g.addNode("Giesing", 22.17, 8.34);
        g.addNode("Großhadern", 7.83, 6.96);
        g.addNode("Harras", 11.61, 6.84);
        g.addNode("Hauptbahnhof", 15.11, 12.25);
        g.addNode("Heimeranplatz", 11.67, 9.62);
        g.addNode("Herrsching", 3.23, 1.31);
        g.addNode("Holzkirchen", 21.45, 1.84);
        g.addNode("Innsbrucker Ring", 26.51, 9.12);
        g.addNode("Karlsplatz", 17.64, 12.50);
        g.addNode("Kreuzstraße", 27.20, 1.84);
        g.addNode("Laim", 8.17, 12.50);
        g.addNode("Laimer Platz", 8.45, 9.78);
        g.addNode("Mangfallplatz", 19.17, 5.71);
        g.addNode("Mammendorf", 0.95, 18.37);
        g.addNode("Marienplatz", 20.51, 12.68);
        g.addNode("Messe", 32.26, 11.87);
        g.addNode("Neufahrn", 13.92, 20.81);
        g.addNode("Neuperlach", 26.70, 5.78);
        g.addNode("Odeonsplatz", 20.26, 15.28);
        g.addNode("OEZ", 12.76, 15.71);
        g.addNode("Ostbahnhof", 24.86, 12.50);
        g.addNode("Pasing", 4.83, 12.43);
        g.addNode("Petershausen", 6.48, 20.18);
        g.addNode("Scheidplatz", 16.67, 17.12);
        g.addNode("Sendlinger Tor", 17.64, 9.03);
        g.addNode("Trudering", 29.48, 10.90);
        g.addNode("Tutzing", 6.58, 1.28);
        g.addNode("Wolfratshausen", 13.20, 1.21);
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
        g.addEdge("Sendlinger Tor", "Mangfallplatz");
        g.addEdge("Trudering", "Ebersberg");
        g.addEdge("Trudering", "Innsbrucker Ring");
        g.addEdge("Trudering", "Messe");
        g.addEdge("Trudering", "Ostbahnhof");
        g.addEdge("Tutzing", "Pasing");
        g.addEdge("Wolfratshausen", "Harras");
        return g;
    }

    @Source(filename = "k10_a7_3.png", title = "Informatik Oberstufe 1", subtitle = "Datenstrukturen und Softwareentwicklung", publisher = "Oldenbourg", releaseYear = 2009)
    public static GraphArrayMatrix OldenbourgKapitel10Aufgabe7Nr3()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 5.16, 1.89);
        g.addNode("B", 5.22, 8.08);
        g.addNode("C", 14.94, 5.02);
        g.addNode("D", 11.28, 8.39);
        g.addNode("E", 11.28, 1.96);
        g.addNode("F", 1.12, 5.20);
        g.addEdge("A", "B");
        g.addEdge("A", "D");
        g.addEdge("A", "E");
        g.addEdge("A", "F");
        g.addEdge("B", "D");
        g.addEdge("B", "E");
        g.addEdge("B", "F");
        g.addEdge("C", "D");
        g.addEdge("C", "E");
        g.addEdge("D", "E");
        return g;
    }

    /**
     * Buchner Seite 45
     */
    @Source(page = 45, title = "Informatik 11", publisher = "Buchner", releaseYear = 2023)
    public static GraphArrayMatrix Buchner11FahrzeitenZweispurig()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 9.27, 12.59);
        g.addNode("B", 12.86, 4.71);
        g.addNode("C", 8.61, 1.30);
        g.addNode("D", 1.58, 4.59);
        g.addNode("E", 3.39, 12.40);
        g.addNode("F", 4.64, 6.27);
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
    @Source(page = 45, title = "Informatik 11", publisher = "Buchner", releaseYear = 2023)
    public static GraphArrayMatrix Buchner11FahrzeitenZweispurigDirected()
    {
        GraphArrayMatrix g = new GraphArrayMatrix();
        // Anlegen der Knoten
        g.addNode("A", 9.27, 12.59);
        g.addNode("B", 12.86, 4.71);
        g.addNode("C", 8.61, 1.30);
        g.addNode("D", 1.58, 4.59);
        g.addNode("E", 3.39, 12.40);
        g.addNode("F", 4.64, 6.27);
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

    /**
     * Seite 58 Aufgabe Nr. 4
     */
    @Source(page = 58, title = "Informatik 11 spätbeginnend", subtitle = "Algorithmen | Codierung | Kommunikation in Netzwerken | Künstliche Intelligenz", publisher = "Cornelsen", releaseYear = 2023)
    public static GraphArrayMatrix Cornelsen11SpbNuernbergUlmMuenchenHamburg()
    {
        GraphArrayMatrix g = new GraphArrayMatrix(11);
        // Anlegen der Knoten
        g.addNode("BA", 7.79, 8.32);
        g.addNode("BT", 11.82, 9.19);
        g.addNode("FD", 2.79, 10.01);
        g.addNode("H", 2.36, 13.26);
        g.addNode("HH", 3.39, 16.26);
        g.addNode("LG", 6.89, 14.79);
        g.addNode("M", 11.11, 0.98);
        g.addNode("MD", 11.14, 11.54);
        g.addNode("N", 10.98, 3.88);
        g.addNode("UL", 3.04, 3.19);
        g.addNode("WÜ", 3.29, 6.51);
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
     * Gibt die Namen aller öffentlichen, statischen Methoden dieser Klasse
     * zurück, mit Ausnahme dieser Methode.
     *
     * @return Ein Array von Strings, das die Namen der Methoden enthält.
     */
    public static String[] getMethodNames()
    {
        ArrayList<String> names = new ArrayList<>();
        try
        {
            Method[] methods = GraphCollection.class.getDeclaredMethods();
            for (Method method : methods)
            {
                String name = method.getName();
                if (Modifier.isStatic(method.getModifiers())
                        && Modifier.isPublic(method.getModifiers())
                        && !name.equals("getMethodNames"))
                {
                    names.add(name);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return names.toArray(new String[0]);
    }

}
