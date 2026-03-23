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
package pi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;

/**
 * Zeigt an, dass diese Methode verkettet werden kann.
 *
 * <p>
 * Method Chaining (deutsch: Methodenverkettung) ist eine Programmiertechnik in
 * der objektorientierten Programmierung, bei der mehrere Methodenaufrufe
 * desselben Objekts in einer einzigen Codezeile hintereinander ausgeführt
 * werden.
 * </p>
 *
 * <p>
 * Die Verkettung wird ermöglicht, indem jede Methode in der Kette einen
 * Rückgabewert liefert, der als Ziel für den nächsten Aufruf dient. In der
 * Regel gibt die Methode das aktuelle Objekt selbst zurück.
 * </p>
 *
 * <p>
 * Durch Method Chaining müssen keine temporären Zwischenvariablen erstellt
 * werden, um den Zustand des Objekts nach jedem Schritt zu speichern. Statt
 * </p>
 *
 * <pre>
 * {@code
 * Image image = new Image("actor.png");
 * image.size(2, 1);
 * image.flippedHorizontally(true);
 * image.anchor(5, 0);
 * }
 * </pre>
 *
 * kann dann geschrieben werden:
 *
 * <pre>
 * {@code
 * new Image("actor.png").size(2, 1).flippedHorizontally(true).anchor(5, 0);
 * }
 * </pre>
 *
 * <p>
 * Dadurch werden unnötige Wiederholungen des Objektnamens vermieden.
 * </p>
 *
 * @author Josef Friedrich
 *
 * @since 0.44.0
 */
@Documented
@Target(
{ ElementType.METHOD })
public @interface ChainableMethod
{

}
