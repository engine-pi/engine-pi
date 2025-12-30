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

/**
 * Generiert animierte GIF-Dateien aus PNG-Screenshots mittels ffmpeg.
 *
 * @author Josef Friedrich
 *
 * @since 0.42.0
 */
public class ImagesToVideoConverter
{
    private final String screenshotDir;

    private final String outputFile;

    private int framerate;

    /**
     * Erstellt einen neuen GIF-Generator.
     *
     * @param screenshotDir Das Verzeichnis mit den Screenshot-Dateien
     * @param outputFile Der Pfad zur Ausgabe-GIF-Datei
     * @param framerate Die Anzahl der Bilder pro Sekunde (Standard: 10)
     */
    public ImagesToVideoConverter(String screenshotDir, String outputFile,
            int framerate)
    {
        this.screenshotDir = screenshotDir;
        this.outputFile = outputFile;
        this.framerate = framerate;
    }

    /**
     * Erstellt einen neuen GIF-Generator mit Standard-Framerate von 10 FPS.
     */
    public ImagesToVideoConverter(String screenshotDir, String outputFile)
    {
        this(screenshotDir, outputFile, 10);
    }

    /**
     * Generiert ein animiertes GIF aus allen PNG-Dateien im
     * Screenshot-Verzeichnis.
     *
     * @return true, wenn die GIF-Datei erfolgreich erstellt wurde
     *
     * @throws Exception bei Fehlern während der Konvertierung
     */
    public boolean generate() throws Exception
    {
        // Prüfe, ob ffmpeg verfügbar ist
        if (!isFFmpegAvailable())
        {
            throw new Exception(
                    "ffmpeg ist nicht verfügbar. Bitte installieren Sie ffmpeg.");
        }

        // Prüfe, ob das Screenshot-Verzeichnis existiert
        File dir = new File(screenshotDir);
        if (!dir.exists() || !dir.isDirectory())
        {
            throw new Exception(
                    "Screenshot-Verzeichnis existiert nicht: " + screenshotDir);
        }

        // Zähle die PNG-Dateien
        File[] pngFiles = dir.listFiles((d, name) -> name.endsWith(".png"));
        if (pngFiles == null || pngFiles.length == 0)
        {
            throw new Exception("Keine PNG-Dateien im Verzeichnis gefunden: "
                    + screenshotDir);
        }

        System.out.println(
                "Gefunden: " + pngFiles.length + " Screenshot-Dateien");

        // Erstelle ffmpeg-Befehl
        ProcessBuilder processBuilder = new ProcessBuilder("ffmpeg", "-y",
                "-framerate", String.valueOf(framerate), "-pattern_type",
                "glob", "-i", screenshotDir + "/screenshot_*.png", "-vf",
                "fps=" + framerate + ",scale=iw:ih:flags=lanczos", outputFile);

        processBuilder.directory(new File(screenshotDir));
        processBuilder.redirectErrorStream(true);

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

        int exitCode = process.waitFor();

        if (exitCode == 0)
        {
            System.out.println("GIF erfolgreich erstellt: " + outputFile);
            return true;
        }
        else
        {
            System.err.println(
                    "ffmpeg fehlgeschlagen mit Exit-Code: " + exitCode);
            return false;
        }
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
}
