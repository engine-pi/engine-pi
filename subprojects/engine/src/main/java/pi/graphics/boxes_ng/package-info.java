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

/**
 * Eine <b>Box</b> ist ein rechteckiges graphisches Element, das in die
 * {@link java.awt.Graphics2D Graphics2D}-API eingezeichnet werden kann.
 *
 * <p>
 * Eine Box kann weitere Boxen enthalten und dadurch entsteht eine rekursive
 * Datenstruktur. So kann zum Beispiel um einen Text ein Rahmen gelegt werden.
 * Jedes einzuzeichnende Element wird in eine rechteckige Box eingebettet. Die
 * grundlegende Maßeinheit sind Pixel. Dieses Paket ist inspiriert von den
 * {@link java.awt.Component}-Klassen.
 * </p>
 *
 * <p>
 * Die Unterklassen lassen sich in drei Kategorien einteilen:
 * </p>
 *
 * <ul>
 * <li>{@link ChildsBox}: Box, die <b>mehrere</b> Kind-Boxen enthalten kann.
 *
 * <ul>
 * <li>{@link GridBox}: Eine <b>Gitter</b>-Box, die mehrere untergeordnete
 * Kinder-Boxen in Zeilen und Spalten anordnet.</li>
 * <li>{@link HorizontalBox}: Eine <b>horizontale</b> Box, die die enthaltenen
 * Kinder-Boxen horizontal von links nach rechts anordnet.</li>
 * <li>{@link VerticalBox}: Eine <b>vertikale</b> Box, die die enthaltenen
 * Kinder-Boxen vertikal von oben nach unten anordnet.</li>
 * </ul>
 *
 * </li>
 *
 * <li>{@link ChildBox}: Box, die nur <b>eine</b> einzige Kind-Box enthalten
 * kann.
 *
 * <ul>
 * <li>{@link BackgroundBox}: Unterlegt eine Kind-Box mit einer
 * <b>Hintergrundfarbe</b>.</li>
 * <li>{@link BorderBox}: Legt einen <b>Rahmen</b> um eine enthaltene
 * Kind-Box.</li>
 * <li>{@link CellBox}: Eine äußere (größere) <b>Behälter</b>-Box, die eine
 * kleinere (innere) Box enthält.</li>
 * <li>{@link InsetBox}: Ein <b>Außenabstand</b> um die enthaltene
 * Kind-Box.</li>
 * </ul>
 *
 * </li>
 *
 * <li>{@link LeafBox}: Box, die <b>keine</b> Kind-Box enthalten kann.
 *
 * <ul>
 * <li>{@link CompassBox}: Ein <b>Kompasspfeil</b>, der in der Mitte eines
 * Quadrats angebracht ist.</li>
 * <li>{@link DimensionBox}: Eine leere Box, die auf eine bestimmte
 * <b>Abmessung</b> gesetzt werden kann.</li>
 * <li>{@link EllipseBox}: Eine <b>Ellipse</b>, bei der die <b>Breite</b> und
 * die <b>Höhe</b> angegeben werden kann.</li>
 * <li>{@link ImageBox}: Ein <b>Bild</b>, dessen Abmessungen gesetzt werden
 * können und das gespiegelt werden kann.text</li>
 * </ul>
 *
 * </li>
 *
 * </ul>
 *
 * <h2>Ähnliche Pakete</h2>
 *
 * <ul>
 * <li><a href="https://horstmann.com/sjsu/graphics/">Simple Java
 * Graphics</a></li>
 * </ul>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
package pi.graphics.boxes_ng;
