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
 * @see <a href="https://horstmann.com/sjsu/graphics/">Simple Java Graphics</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
package pi.graphics.boxes;
