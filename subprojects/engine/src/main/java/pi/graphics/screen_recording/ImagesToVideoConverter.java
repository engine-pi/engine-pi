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

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

enum SupportedAnimatedFormat
{

    GIF("gif"), APNG("apng"), WEBP("webp"), MP4("mp4"), WEBM("webm");

    private String extension;

    SupportedAnimatedFormat(String extension)
    {
        this.extension = extension;
    }

    public String extension()
    {
        return extension;
    }
}

/**
 * Generiert animierte Bild- und Video-Dateien aus PNG-Bildschirmfotos mittels
 * ffmpeg.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class ImagesToVideoConverter
{
    private final String screenshotDir;

    private final String outputPath;

    private int framerate = 30;

    /**
     * Erstellt einen neuen GIF-Generator.
     *
     * @param screenshotDir Das Verzeichnis mit den Screenshot-Dateien
     * @param outputPath Der Pfad zur Ausgabe-GIF-Datei
     * @param framerate Die Anzahl der Bilder pro Sekunde (Standard: 10)
     */
    public ImagesToVideoConverter(String screenshotDir, String outputPath)
    {
        this.screenshotDir = screenshotDir;
        this.outputPath = outputPath;
    }

    /**
     * Prüft, ob ffmpeg im System verfügbar ist.
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
            // -vf filtergraph (output) Create the filtergraph specified by
            // filtergraph and use it to filter the stream. This is an alias
            // for -filter:v, see the -filter option.
            command.add("-vf");
            command.add("fps=" + framerate + ",scale=iw:ih:flags=lanczos");
            break;

        case WEBP:
            command.add("-vcodec");
            command.add("webp");
            command.add("-pix_fmt");
            command.add("yuva420p");
            break;

        case APNG:
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
            // Führe ffmpeg aus
            System.out.println("Starte ffmpeg...");
            Process process = processBuilder.start();

            // Lese die Ausgabe
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())))
            {
                String line;
                while ((line = reader.readLine()) != null)
                {
                    System.out.println(line);
                }
            }

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

    public void generate(SupportedAnimatedFormat... format)
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

        System.out.println(
                "Gefunden: " + pngFiles.length + " Screenshot-Dateien");

        for (SupportedAnimatedFormat supportedAnimatedFormat : format)
        {
            generateFormat(supportedAnimatedFormat);
        }

    }

    public void generate()
    {
        generate(SupportedAnimatedFormat.GIF, SupportedAnimatedFormat.APNG,
                SupportedAnimatedFormat.WEBP, SupportedAnimatedFormat.MP4,
                SupportedAnimatedFormat.WEBM);
    }

    public static void main(String[] args)
    {
        new ImagesToVideoConverter("/home/jf/engine-pi",
                "/home/jf/Downloads/screen-recording").generate();
    }
}
