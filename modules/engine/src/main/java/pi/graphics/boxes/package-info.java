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
 * Eine rekursive Datenstruktur, um graphische Elemente ineinander verschachteln
 * zu können und dann die Graphics2D-API einzuzeichen.
 *
 * Die grundlegende Maßeinheit sind Pixel.
 *
 * <p>
 * So kann zum Beispiel um einen Text ein Rahmen gelegt werden.
 * </p>
 *
 * <p>
 * Inspiriert von den {@link java.awt.Component}-Klassen.
 * </p>
 *
 * @see <a href="https://horstmann.com/sjsu/graphics/">Simple Java Graphics</a>
 *
 * @author Josef Friedrich
 *
 * @since 0.38.0
 */
package pi.graphics.boxes;
