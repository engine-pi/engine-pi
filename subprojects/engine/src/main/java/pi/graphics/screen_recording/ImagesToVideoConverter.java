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

import java.io.File;
import java.util.List;

/**
 * Generiert verschiedene animierte Bild- und Video-Dateien aus
 * PNG-Bildschirmfotos mittels
 * <a href="https://de.wikipedia.org/wiki/FFmpeg">ffmpeg</a>.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
class ImagesToVideoConverter
{
    /**
     * Das Verzeichnis mit den Bildschirmfoto-Dateien im PNG-Format.
     */
    private final String screenshotDir;

    /**
     * Der Datei-Pfad er resultierenden Video-Dateien (ohne Dateiendung).
     */
    private final String outputPath;

    /**
     * Die Engine hat im Auslieferungszustand eine Framerate von 60 Bilder pro
     * Sekunde. Standardmäßig wird jedes zweite Einzelbild. Das würde dann einen
     * Framerate von 30 ergeben.
     */
    private int framerate;

    /**
     * Erstellt einen neuen Bilder-zu-Video-Konvertierer.
     *
     * @param screenshotDir Das Verzeichnis mit den Bildschirmfoto-Dateien im
     *     PNG-Format.
     * @param outputPath Der Datei-Pfad er resultierenden Video-Dateien (ohne
     *     Dateiendung).
     */
    ImagesToVideoConverter(String screenshotDir, String outputPath,
            int framerate)
    {
        this.screenshotDir = screenshotDir;
        this.outputPath = outputPath;
        this.framerate = framerate;
    }

    /**
     * Prüft, ob <a href="https://de.wikipedia.org/wiki/FFmpeg">ffmpeg</a> im
     * System verfügbar ist.
     */
    private boolean isFFmpegAvailable()
    {
        try
        {
            ProcessBuilder pb = new ProcessBuilder("ffmpeg", "-version");
            Process process = pb.start();
            int exitCode = process.waitFor();
            return exitCode == 0;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * Generiert ein animiertes Bild- bzw. Video aus allen PNG-Dateien im
     * Screenshot-Verzeichnis.
     */
    private void generateFormat(SupportedAnimatedFormat format)
    {
        // Erstelle ffmpeg-Befehl
        ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg",
                // -y (global) Overwrite output files without asking.
                "-y",
                // -framerate Set the grabbing frame rate. Default is ntsc,
                // corresponding to a frame rate of 30000/1001.
                "-framerate", String.valueOf(framerate),
                // When importing an image sequence, -i also supports expanding
                // shell-like wildcard patterns (globbing) internally, by
                // selecting the image2-specific -pattern_type glob option.
                "-pattern_type", "glob",
                // -i url (input) input file url
                "-i", screenshotDir + "/screenshot_*.png",
                // https://www.ffmpeg.org/ffmpeg-all.html#gif-2
                // loop bool Set the number of times to loop the output. Use -1
                // for no loop, 0 for looping indefinitely (default).
                "-loop", "0");

        List<String> command = processBuilder.command();

        switch (format)
        {
        case GIF:
            /* @formatter:off
             *
             * ffmpeg -h encoder=gif:
             *
             * Encoder gif [GIF (Graphics Interchange Format)]:
             *     General capabilities: dr1
             *     Threading capabilities: none
             *     Supported pixel formats: rgb8 bgr8 rgb4_byte bgr4_byte gray pal8
             * GIF encoder AVOptions:
             *   -gifflags          <flags>      E..V....... set GIF flags (default offsetting+transdiff)
             *      offsetting                   E..V....... enable picture offsetting
             *      transdiff                    E..V....... enable transparency detection between frames
             *   -gifimage          <boolean>    E..V....... enable encoding only images per frame (default false)
             *   -global_palette    <boolean>    E..V....... write a palette to the global gif header where feasible (default true)
             *
             * @formatter:on */

            // -vf filtergraph (output) Create the filtergraph specified by
            // filtergraph and use it to filter the stream. This is an alias
            // for -filter:v, see the -filter option.
            command.add("-vf");
            command.add("fps=" + framerate + ",scale=iw:ih:flags=lanczos");
            break;

        case WEBP:
            /* @formatter:off
             *
             * ffmpeg -h encoder=webp:
             *
             * libwebp encoder AVOptions:
             * -lossless          <int>        E..V....... Use lossless mode (from 0 to 1) (default 0)
             * -preset            <int>        E..V....... Configuration preset (from -1 to 5) (default none)
             *     none            -1           E..V....... do not use a preset
             *     default         0            E..V....... default preset
             *     picture         1            E..V....... digital picture, like portrait, inner shot
             *     photo           2            E..V....... outdoor photograph, with natural lighting
             *     drawing         3            E..V....... hand or line drawing, with high-contrast details
             *     icon            4            E..V....... small-sized colorful images
             *     text            5            E..V....... text-like
             * -cr_threshold      <int>        E..V....... Conditional replenishment threshold (from 0 to INT_MAX) (default 0)
             * -cr_size           <int>        E..V....... Conditional replenishment block size (from 0 to 256) (default 16)
             * -quality           <float>      E..V....... Quality (from 0 to 100) (default 75)
             *
             * Encoder libwebp [libwebp WebP image]:
             *     General capabilities: dr1
             *     Threading capabilities: none
             *     Supported pixel formats: bgra yuv420p yuva420p
             * libwebp encoder AVOptions:
             * -lossless          <int>        E..V....... Use lossless mode (from 0 to 1) (default 0)
             * -preset            <int>        E..V....... Configuration preset (from -1 to 5) (default none)
             *     none            -1           E..V....... do not use a preset
             *     default         0            E..V....... default preset
             *     picture         1            E..V....... digital picture, like portrait, inner shot
             *     photo           2            E..V....... outdoor photograph, with natural lighting
             *     drawing         3            E..V....... hand or line drawing, with high-contrast details
             *     icon            4            E..V....... small-sized colorful images
             *     text            5            E..V....... text-like
             * -cr_threshold      <int>        E..V....... Conditional replenishment threshold (from 0 to INT_MAX) (default 0)
             * -cr_size           <int>        E..V....... Conditional replenishment block size (from 0 to 256) (default 16)
             * -quality           <float>      E..V....... Quality (from 0 to 100) (default 75)
             *
             * @formatter:on */

            command.add("-vcodec");
            command.add("webp");
            command.add("-lossless"); // Macht komische Streifen
            command.add("1"); // 0 ist aus, 1 ist an

            // ausprobiert -> Streifen gehen nicht weg
            // command.add("-preset");
            // command.add("drawing");

            // ausprobiert -> Streifen gehen nicht weg
            // command.add("-quality");
            // command.add("100");
            command.add("-pix_fmt");
            command.add("bgra");
            break;

        case APNG:
            /* @formatter:off
             *
             * ffmpeg -h encoder=apng:
             *
             * Encoder apng [APNG (Animated Portable Network Graphics) image]:
             *     General capabilities: dr1 delay
             *     Threading capabilities: none
             *     Supported pixel formats: rgb24 rgba rgb48be rgba64be pal8 gray ya8 gray16be ya16be
             * (A)PNG encoder AVOptions:
             *   -dpi               <int>        E..V....... Set image resolution (in dots per inch) (from 0 to 65536) (default 0)
             *   -dpm               <int>        E..V....... Set image resolution (in dots per meter) (from 0 to 65536) (default 0)
             *   -pred              <int>        E..V....... Prediction method (from 0 to 5) (default none)
             *      none            0            E..V.......
             *      sub             1            E..V.......
             *      up              2            E..V.......
             *      avg             3            E..V.......
             *      paeth           4            E..V.......
             *      mixed           5            E..V.......
             *
             * @formatter:on */

            // -plays repetitions: specify how many times to play the content, 0
            // causes an infinite loop, with 1 there is no loop
            command.add("-plays");
            command.add("0");
            break;

        case WEBM:
            command.add("-c:v");
            command.add("libvpx-vp9");
            command.add("-pix_fmt");
            command.add("yuva420p");
            break;

        case MP4:
            command.add("-c:v");
            command.add("libvpx-vp9");
            command.add("-pix_fmt");
            command.add("yuva420p");
            break;

        default:
            break;
        }

        command.add(outputPath + "." + format.extension());

        processBuilder.directory(new File(screenshotDir));
        processBuilder.redirectErrorStream(true);

        int exitCode = -1;
        try
        {
            Process process = processBuilder.start();
            exitCode = process.waitFor();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        if (exitCode != 0)
        {
            throw new RuntimeException(
                    "ffmpeg fehlgeschlagen mit Exit-Code: " + exitCode);
        }
    }

    private void generate(SupportedAnimatedFormat... format)
    {
        // Prüfe, ob ffmpeg verfügbar ist
        if (!isFFmpegAvailable())
        {
            throw new RuntimeException(
                    "ffmpeg ist nicht verfügbar. Bitte installieren Sie ffmpeg.");
        }

        // Prüfe, ob das Screenshot-Verzeichnis existiert
        File dir = new File(screenshotDir);
        if (!dir.exists() || !dir.isDirectory())
        {
            throw new RuntimeException(
                    "Screenshot-Verzeichnis existiert nicht: " + screenshotDir);
        }

        // Zähle die PNG-Dateien
        File[] pngFiles = dir.listFiles((d, name) -> name.endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0)
        {
            throw new RuntimeException(
                    "Keine PNG-Dateien im Verzeichnis gefunden: "
                            + screenshotDir);
        }
        for (SupportedAnimatedFormat supportedAnimatedFormat : format)
        {
            generateFormat(supportedAnimatedFormat);
        }

    }

    void generate()
    {
        generate(
                // Sehr große Dateien
                // SupportedAnimatedFormat.GIF,

                // Verursacht flackern
                // SupportedAnimatedFormat.APNG,

                // Lossless keinen Streifen
                SupportedAnimatedFormat.WEBP,

                // Bestes Resultat
                SupportedAnimatedFormat.MP4

        // MP4 ist besser
        // SupportedAnimatedFormat.WEBM

        );
    }
}
