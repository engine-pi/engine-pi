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
package pi.actor;

/**
 * Die verschiedenen Arten einer <b>Pfeilspitze</b>.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public enum ArrowType
{
    // https://google.github.io/guice/api-docs/4.2.2/javadoc/index.html?com/google/inject/grapher/graphviz/ArrowType.html

    // https://docs.yworks.com/yfiles-html/api/ArrowType.html

    /**
     * <b>Keine</b> Pfeilspitze
     */
    NONE,

    /**
     * Eine Pfeilspitze in Form eines <b>Winkels</b> (nach dem französischen
     * Wort Chevron (französisch chevron = „Winkel“, „Sparren“,
     * „Zickzackleiste“) )
     */
    CHEVERON,

    /**
     * Eine Pfeilspitze in Form eines <b>Dreiecks</b>.
     */
    TRIANGLE
}
