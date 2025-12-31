/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/util/io/FileUtilities.java
 *
 * MIT License
 *
 * Copyright (c) 2016 - 2024 Gurkenlabs
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package pi.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import pi.resources.ResourceLoader;

/**
 * Hilfsklasse für <b>Datei</b>- und Verzeichnisverwaltungsoperationen.
 *
 * <p>
 * Diese Klasse bietet statische Methoden zum Löschen von Verzeichnissen, Suchen
 * von Dateien nach Erweiterung oder Namen, Extrahieren von Dateinamen und
 * Erweiterungen, Pfadverwaltung sowie Konvertierung von Dateigröße in lesbare
 * Formate.
 * </p>
 *
 * <p>
 * Die Klasse ist nicht instanziierbar und kann nur über ihre statischen
 * Methoden verwendet werden.
 * </p>
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public final class FileUtil
{
    private static final Logger log = Logger
            .getLogger(FileUtil.class.getName());

    private static final String[] DIR_BLACKLIST = new String[] { "\\bin",
            "\\screenshots" };

    private static final String FILE_SEPARATOR_WIN = "\\";

    private static final String FILE_SEPARATOR = "/";

    private FileUtil()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * <b>Löscht</b> ein <b>Verzeichnis</b> und seinen gesamten Inhalt rekursiv.
     *
     * <p>
     * Diese Methode entfernt das angegebene Verzeichnis sowie alle darin
     * enthaltenen Dateien und Unterverzeichnisse. Der Löschvorgang erfolgt
     * rekursiv von unten nach oben.
     * </p>
     *
     * @param dir Das zu löschende Verzeichnis.
     */
    public static void deleteDir(final File dir)
    {
        if (dir.isDirectory())
        {
            final String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                deleteDir(new File(dir, children[i]));
            }
        }
        try
        {
            Files.delete(dir.toPath().toAbsolutePath());
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * <b>Löscht</b> ein <b>Verzeichnis</b> und seinen gesamten Inhalt rekursiv.
     *
     * <p>
     * Diese Methode entfernt das angegebene Verzeichnis sowie alle darin
     * enthaltenen Dateien und Unterverzeichnisse. Der Löschvorgang erfolgt
     * rekursiv von unten nach oben.
     * </p>
     *
     * @param dir Das zu löschende Verzeichnis als <b>Zeichenkette</b>.
     *
     * @since 0.42.0
     */
    public static void deleteDir(String dir)
    {
        deleteDir(new File(dir));
    }

    /**
     * Sucht rekursiv nach Dateien mit einer bestimmten Dateiendung in einem
     * Verzeichnis.
     *
     * <p>
     * Die Methode durchsucht das angegebene Verzeichnis und alle
     * Unterverzeichnisse (außer blacklisteten Verzeichnissen) nach Dateien, die
     * mit der angegebenen Endung übereinstimmen. Gefundene Dateien werden zur
     * übergebenen Liste hinzugefügt.
     * </p>
     *
     * @param fileNames Die Liste, in der gefundene Dateipfade gesammelt werden.
     *     Diese Liste wird mit absoluten Pfaden ergänzt.
     * @param dir Das Startverzeichnis, ab dem die Suche beginnt
     * @param extension Die gesuchte Dateiendung (z.B. ".java", ".txt"). Der
     *     Vergleich erfolgt mit {@code endsWith()}
     *
     * @return Die übergebene {@code fileNames} Liste mit allen gefundenen
     *     Dateien. Bei Fehler beim Lesen des Verzeichnisses wird die teilweise
     *     gefüllte Liste zurückgegeben und der Fehler geloggt
     *
     * @see #isBlackListedDirectory(Path)
     */
    public static List<String> findFilesByExtension(
            final List<String> fileNames, final Path dir,
            final String extension)
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
            for (final Path path : stream)
            {
                if (path.toFile().isDirectory())
                {
                    if (isBlackListedDirectory(path))
                    {
                        continue;
                    }
                    findFilesByExtension(fileNames, path, extension);
                }
                else if (path.toAbsolutePath().toString().endsWith(extension))
                {
                    fileNames.add(path.toAbsolutePath().toString());
                }
            }
        }
        catch (final IOException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return fileNames;
    }

    /**
     * <b>Durchsucht</b> rekursiv ein <b>Verzeichnis</b> und seine
     * Unterverzeichnisse nach Dateien mit bestimmten Namen.
     *
     * @param fileNames Eine Liste, in der die gefundenen Dateipfade gespeichert
     *     werden.
     * @param dir Das Startverzeichnis, das durchsucht werden soll.
     * @param files Eine beliebige Anzahl von Dateinamen oder Dateisuffixen,
     *     nach denen gesucht werden soll.
     *
     * @return die übergebene Liste {@code fileNames}, gefüllt mit den absoluten
     *     Pfaden der gefundenen Dateien.
     */
    public static List<String> findFiles(final List<String> fileNames,
            final Path dir, final String... files)
    {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir))
        {
            for (final Path path : stream)
            {
                if (path.toFile().isDirectory())
                {
                    if (isBlackListedDirectory(path))
                    {
                        continue;
                    }
                    findFiles(fileNames, path, files);
                }
                else
                {
                    for (final String file : files)
                    {
                        if (path.toAbsolutePath().toString().endsWith(file))
                        {
                            fileNames.add(path.toAbsolutePath().toString());
                        }
                    }
                }
            }
        }
        catch (final IOException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return fileNames;
    }

    /**
     * Ermittelt die <b>Dateiendung</b> einer Datei.
     *
     * @param file Die Datei, deren Endung bestimmt werden soll.
     *
     * @return Die Dateiendung der angegebenen Datei.
     */
    public static String getExtension(final File file)
    {
        return getExtension(file.getAbsolutePath());
    }

    /**
     * Extrahiert die Dateiendung aus einem gegebenen Dateipfad.
     *
     * @param path Der Dateipfad, aus dem die Endung extrahiert werden soll.
     *
     * @return Die Dateiendung ohne den Punkt (z.B. "txt", "java"), oder eine
     *     leere Zeichenkette, falls keine Endung vorhanden ist oder ein Fehler
     *     bei der Verarbeitung auftritt.
     */
    public static String getExtension(final String path)
    {
        final String fileName = getFileName(path, true);
        if (!fileName.contains("."))
        {
            return "";
        }
        try
        {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        catch (final Exception e)
        {
            return "";
        }
    }

    /**
     * Gibt den Dateinamen eines Pfades mit oder ohne Dateierweiterung zurück.
     *
     * <p>
     * Endet der Pfad mit einem Pfadtrennzeichen, so wird eine leere
     * Zeichenkette zurück gegeben.
     * </p>
     *
     * @param path Der Dateipfad angegeben als Zeichenkette.
     * @param extension Ob die Dateierweiterung im Dateinamen erhalten bleiben
     *     soll oder nicht.
     *
     * @return Der Dateiname mit oder ohne Dateierweiterung je nach
     *     Eingabeparameter.
     */
    public static String getFileName(final String path, boolean extension)
    {
        if (path == null || path.isEmpty() || path.endsWith(FILE_SEPARATOR_WIN)
                || path.endsWith(FILE_SEPARATOR))
        {
            return "";
        }
        String name = path;
        if (!extension)
        {
            final int pos = name.lastIndexOf('.');
            if (pos > 0)
            {
                name = name.substring(0, pos);
            }
        }
        final int lastBackslash = name.lastIndexOf(FILE_SEPARATOR);
        if (lastBackslash != -1)
        {
            name = name.substring(lastBackslash + 1);
        }
        else
        {
            final int lastForwardSlash = name.lastIndexOf(FILE_SEPARATOR_WIN);
            if (lastForwardSlash != -1)
            {
                name = name.substring(lastForwardSlash + 1);
            }
        }
        return name;
    }

    /**
     * Gibt den Dateinamen eines Pfades ohne die Dateierweiterung zurück.
     *
     * <p>
     * Endet der Pfad mit einem Pfadtrennzeichen, so wird eine leere
     * Zeichenkette zurück gegeben.
     * </p>
     *
     * @param path Der Dateipfad angegeben als Zeichenkette.
     *
     * @return Der Dateiname ohne Dateierweiterung.
     */
    public static String getFileName(final String path)
    {
        return getFileName(path, false);
    }

    /**
     * Gibt den Dateinamen eines Pfades ohne Dateierweiterung zurück.
     *
     * <p>
     * Endet der Pfad mit einem Pfadtrennzeichen, so wird eine leere
     * Zeichenkette zurück gegeben.
     * </p>
     *
     * @param path Der Dateipfad.
     *
     * @return Der Dateiname ohne Dateierweiterung.
     */
    public static String getFileName(Path path)
    {
        return getFileName(path.toString());
    }

    /**
     * Gibt den Dateinamen eines Pfades ohne Dateierweiterung zurück.
     *
     * <p>
     * Endet der Pfad mit einem Pfadtrennzeichen, so wird eine leere
     * Zeichenkette zurück gegeben.
     * </p>
     *
     * @param path Der Dateipfad.
     *
     * @return Der Dateiname ohne Dateierweiterung.
     */
    public static String getFileName(URL path)
    {
        return getFileName(path.getPath());
    }

    /**
     * Gibt den Pfad des übergeordneten Verzeichnisses für den angegebenen URI
     * oder Dateipfad zurück.
     *
     * <p>
     * Die Methode versucht zunächst, den Input als URI zu interpretieren. Falls
     * dies fehlschlägt, wird der Input als Dateipfad behandelt.
     * </p>
     *
     * @param uri Der URI oder Dateipfad, dessen übergeordnetes Verzeichnis
     *     bestimmt werden soll.
     *
     * @return Der Pfad des übergeordneten Verzeichnisses mit nachgestelltem
     *     Dateitrenner, oder der ursprüngliche Input, falls dieser null oder
     *     leer ist.
     *
     * @throws NullPointerException wenn das übergeordnete Verzeichnis null ist
     */
    public static String getParentDirPath(final String uri)
    {
        if (uri == null || uri.isEmpty())
        {
            return uri;
        }
        try
        {
            return getParentDirPath(new URI(uri));
        }
        catch (URISyntaxException e)
        {
            String parent = new File(uri).getParent();
            parent += File.separator;
            return parent;
        }
    }

    /**
     * Gibt den Pfad des übergeordneten Verzeichnisses einer URI zurück.
     *
     * <p>
     * Wenn der Pfad der URI mit einem Datei-Trennzeichen endet, wird das
     * übergeordnete Verzeichnis durch Auflösen von ".." ermittelt. Ansonsten
     * wird das aktuelle Verzeichnis durch Auflösen von "." verwendet.
     * </p>
     *
     * @param uri Die URI, deren übergeordnetes Verzeichnis ermittelt werden
     *     soll.
     *
     * @return Der Pfad des übergeordneten Verzeichnisses als Zeichenkette.
     */
    public static String getParentDirPath(final URI uri)
    {
        URI parent = uri.getPath().endsWith(FILE_SEPARATOR) ? uri.resolve("..")
                : uri.resolve(".");
        return parent.toString();
    }

    /**
     * Diese Methode kombiniert den angegebenen Pfad mit den als Argumente
     * übergebenen Pfadsegmenten.
     *
     * <p>
     * Der zurückgegeben Pfad verwendet das Pfadtrennzeichen des aktuellen
     * Systems. Diese Methode normalisiert Windows-Pfadtrennzeichen zu
     * Unix-Trennzeichen.
     * </p>
     *
     * @param basePath der Basispfad, der als Ausgangspunkt dient (nicht null)
     * @param paths variable Anzahl von Pfadkomponenten, die kombiniert werden
     *     sollen. Null-Werte werden ignoriert.
     *
     * @return der kombinierte Pfad als String. Bei Auftreten einer
     *     URISyntaxException wird der Basispfad zurückgegeben.
     */
    public static String combine(String basePath, final String... paths)
    {
        basePath = basePath.replace(FILE_SEPARATOR_WIN, FILE_SEPARATOR);
        try
        {
            URI uri = new URI(basePath.replace(" ", "%20"));
            for (String path : paths)
            {
                if (path == null)
                {
                    continue;
                }
                path = path.replace(FILE_SEPARATOR_WIN, FILE_SEPARATOR);
                uri = uri.resolve(path.replace(" ", "%20"));
            }
            return uri.toString().replace("%20", " ");
        }
        catch (URISyntaxException e)
        {
            log.log(Level.SEVERE, e.getMessage(), e);
            return basePath;
        }
    }

    /**
     * Überprüft, ob ein Verzeichnispfad in der Blacklist enthalten ist.
     *
     * <p>
     * Diese Methode durchsucht die {@link #DIR_BLACKLIST} nach Einträgen und
     * prüft, ob der absolute Pfad des gegebenen Verzeichnisses einen der
     * blackgelisteten Strings enthält.
     * </p>
     *
     * @param path der zu überprüfende Verzeichnispfad
     *
     * @return {@code true} wenn das Verzeichnis blackgelistet ist,
     *     {@code false} andernfalls
     */
    private static boolean isBlackListedDirectory(Path path)
    {
        for (final String black : DIR_BLACKLIST)
        {
            if (path.toAbsolutePath().toString().contains(black))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Wandelt eine Anzahl von Bytes in eine menschenlesbare Darstellung um.
     *
     * @param bytes die Anzahl der Bytes, die formatiert werden sollen
     *
     * @return eine menschenlesbare String-Darstellung der Bytegröße (z.B. "1.5
     *     KB", "2.3 MB")
     */
    public static String humanReadableByteCount(long bytes)
    {
        return humanReadableByteCount(bytes, false);
    }

    /**
     * Konvertiert eine <b>Byte-Anzahl</b> in eine <b>menschenlesbare</b>
     * Darstellung mit entsprechenden Einheiten.
     *
     * @param bytes Die Anzahl der Bytes, die formatiert werden sollen
     * @param decimal Wenn true, werden Dezimalpräfixe (1000er-Basis) verwendet
     *     (KB, MB, GB, etc.), wenn false, werden Binärpräfixe (1024er-Basis)
     *     verwendet (KiB, MiB, GiB, etc.)
     *
     * @return Eine formatierte Zeichenkette mit der Byte-Anzahl und der
     *     entsprechenden Einheit. Beispiele: "1.5 KB", "2.3 GiB", "512 bytes"
     */
    public static String humanReadableByteCount(long bytes, boolean decimal)
    {
        int unit = decimal ? 1000 : 1024;
        if (bytes < unit)
            return bytes + " bytes";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = new String[] { "K", "M", "G", "T", "P", "E" }[exp - 1];
        pre = decimal ? pre : pre + "i";
        return String.format(Locale.ROOT, "%.1f %sB",
                bytes / Math.pow(unit, exp), pre);
    }

    /**
     * Ersetzt im gegebenen Dateipfad alle Schrägstriche (Slashes) und
     * Gegenschrägstriche (Backslashes) mit dem Zeichen des Attributs
     * {@link File#separator}.
     *
     * @author Michael Andonie
     * @author Niklas Keller
     *
     * @param path Ein Dateipfad, der Schrägstriche oder Gegenschrägstriche
     *     enthalten kann.
     *
     * @return Der normalisierte Dateipfad.
     */
    public static String normalizePath(String path)
    {
        return path.replace("\\", File.separator).replace("/", File.separator);
    }

    /**
     * Überprüft, ob eine Datei am angegebenen Dateipfad <b>existiert</b>.
     *
     * @param filePath Der Pfad zur zu überprüfenden Datei.
     *
     * @return {@code true}, wenn die Datei existiert; {@code false}, wenn die
     *     Datei nicht existiert oder ein Fehler beim Laden der Resource
     *     aufgetreten ist
     */
    public static boolean exists(String filePath)
    {
        if (filePath == null)
        {
            return false;
        }
        if (filePath.equals(""))
        {
            return false;
        }
        try
        {
            return Files.exists(
                    Paths.get(ResourceLoader.getLocation(filePath).toURI()));
        }
        catch (URISyntaxException e)
        {
            return false;
        }
    }

    /**
     * Gibt das <b>Heimverzeichnis</b> des aktuellen Benutzers zurück.
     *
     * @return Das <b>Heimverzeichnis</b> des aktuellen Benutzers als
     *     Zeichenkette
     */
    public static String getHomeDir()
    {
        return System.getProperty("user.home");
    }

    /**
     * Gibt den Pfad zum <b>temporären Verzeichnis</b> des Systems zurück.
     *
     * @return Der absolute Pfad zum <b>temporären Verzeichnis</b> als
     *     Zeichenkette.
     *
     * @since 0.42.0
     */
    public static String getTmpDir()
    {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * Erstellt eine <b> temporäre Datei </b> im Standard-Temporärverzeichnis
     * des Systems.
     *
     * @return Der absolute Pfad zur erstellten temporären Datei als String, zum
     *     Beispiel {@code "/tmp/5601360254473891177.tmp"}
     *
     * @throws RuntimeException wenn ein {@link IOException} beim Erstellen der
     *     Datei auftritt
     */
    public static String createTmpFile()
    {
        try
        {
            return Files.createTempFile(null, null).toString();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ermittelt den Pfad zum <b>Bilderordner</b> des Benutzers.
     *
     * <p>
     * Überprüft zunächst, ob ein <em>„Pictures“</em> oder <em>„Bilder“</em>
     * Ordner im Benutzerverzeichnis existiert. Falls keiner der beiden Ordner
     * existiert, wird automatisch ein <em>„Pictures“</em> Ordner erstellt.
     * </p>
     *
     * @return Der absolute Pfad zum Bilderordner des Benutzers
     *
     * @since 0.42.0
     */
    public static String getPicturesDir()
    {
        String english = getHomeDir() + FILE_SEPARATOR + "Pictures";
        if (exists(english))
        {
            return english;
        }

        String german = getHomeDir() + FILE_SEPARATOR + "Bilder";
        if (exists(german))
        {
            return german;
        }

        createDir(english);
        return english;
    }

    /**
     * Ermittelt das <b>Verzeichnis für Videos</b> im Home-Verzeichnis des
     * Benutzers.
     *
     * <p>
     * Diese Methode überprüft, ob ein "Videos"-Ordner im Home-Verzeichnis
     * existiert. Falls das Verzeichnis nicht vorhanden ist, wird es automatisch
     * erstellt.
     * </p>
     *
     * @return Der absolute Pfad zum Videos-Verzeichnis als Zeichenkette.
     *
     * @since 0.42.0
     */
    public static String getVideosDir()
    {
        String english = getHomeDir() + FILE_SEPARATOR + "Videos";

        if (exists(english))
        {
            return english;
        }

        createDir(english);
        return english;
    }

    /**
     * <b>Erstellt</b> ein <b>Verzeichnis</b> unter dem angegebenen Pfad, falls
     * dieses noch nicht existiert.
     *
     * <p>
     * Wenn das Verzeichnis bereits vorhanden ist, wird keine Aktion ausgeführt.
     * </p>
     *
     * @param dirpath Der Pfad des zu erstellenden Verzeichnisses als
     *     Zeichenkette.
     */
    public static void createDir(String dirpath)
    {
        Path path = Paths.get(dirpath);
        if (!Files.exists(path))
        {
            try
            {
                Files.createDirectories(path);
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
