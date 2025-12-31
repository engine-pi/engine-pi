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
package pi.graphics.screen_recording;

import pi.annotations.Getter;

/**
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
enum SupportedAnimatedFormat
{
    /**
     * <a href="https://de.wikipedia.org/wiki/Graphics_Interchange_Format">Das
     * Graphics Interchange Format</a> (englisch Grafikaustausch-Format), kurz
     * GIF, ist ein Grafikformat für Bilder mit Farbpalette (Farbpalette mit
     * max. 256 Farben, inkl. einer „Transparenzfarbe“). Es erlaubt eine
     * verlustfreie Kompression der Bilder. Darüber hinaus können mehrere
     * (übereinanderliegende) Einzelbilder in einer Datei abgespeichert werden,
     * die von geeigneten Betrachtungsprogrammen wie Webbrowsern als Animationen
     * interpretiert werden.
     */
    GIF("gif"),

    /**
     * <a href=
     * "https://de.wikipedia.org/wiki/Animated_Portable_Network_Graphics">Animated
     * Portable Network Graphics</a> (APNG, engl. bewegte portable
     * Netzwerkgrafik) ist eine Erweiterung des Grafikformats PNG. Es wurde als
     * einfache Alternative zum MNG-Format konzipiert, um wie bei GIF Bilder und
     * Animationen in nur einem Dateiformat speichern zu können. APNG soll nach
     * den Angaben seiner Entwickler die häufigsten Anforderungen an bewegte
     * Bilder im Internet erfüllen.
     */
    APNG("apng"),

    /**
     * <a href="https://de.wikipedia.org/wiki/WebP">WebP</a> ist ein
     * Grafikformat für verlustbehaftete oder verlustfreie Bildkompression für
     * statische oder animierte Bilder. Als weiterer Abkömmling des 2010
     * freigegebenen Video-Codecs VP8 ist es ein Schwesterprojekt des
     * Videoformates WebM.
     */
    WEBP("webp"),

    /**
     * <a href="https://de.wikipedia.org/wiki/WebM">WebM</a> ist ein von Google
     * gesponsortes, audiovisuelles Containerformat für freie Codecs wie VP9 und
     * damit eine Alternative zu MPEG mit Codecs wie H.264 und dem
     * Containerformat MP4, entwickelt für Webseiten im HTML5-Standard.
     */
    WEBM("webm"),

    /**
     * <a href="https://de.wikipedia.org/wiki/MP4">MP4</a> ist ein
     * Video-Containerformat, das von der MPEG für MPEG-4-Inhalte vorgesehen
     * ist.
     */
    MP4("mp4");

    private String extension;

    SupportedAnimatedFormat(String extension)
    {
        this.extension = extension;
    }

    @Getter
    public String extension()
    {
        return extension;
    }
}
