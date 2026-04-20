/*
 * Source: https://github.com/gurkenlabs/litiengine/blob/main/litiengine/src/main/java/de/gurkenlabs/litiengine/sound/Sound.java
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
package pi.resources.sound;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import pi.annotations.API;
import pi.annotations.Getter;
import pi.debug.ToStringFormatter;
import pi.resources.ResourceLoader;
import pi.util.FileUtil;
import pi.util.StreamUtilities;

// Go to file:///data/school/repos/inf/java/engine-pi/subprojects/demos/src/main/java/demos/classes/resources/sound/SoundDemo.java

class SoundException extends RuntimeException
{
    SoundException(Throwable throwable)
    {
        super(throwable);
    }
}

/**
 * Ein <b>Klang</b> stellt eine Audio-Datei dar.
 *
 * <p>
 * Diese Klasse implementiert die notwendige Funktionalität, um Klänge aus dem
 * Dateisystem zu laden und einen Stream bereitzustellen, der später für die
 * Wiedergabe verwendet werden kann.
 * </p>
 *
 * @author Steffen Wilke
 * @author Matthias Wilke
 * @author Josef Friedrich
 */
public final class Sound
{
    private AudioInputStream stream;

    /**
     * Erstellt eine neue Sound-Instanz aus dem angegebenen Eingabestrom. Die
     * Sounddaten werden in ein Byte-Array geladen und Informationen über das
     * Audioformat ermittelt.
     *
     * @param is Der Eingabestrom, aus dem der Sound geladen wird.
     * @param filePath Der Name dieser Sounddatei.
     */
    public Sound(InputStream is, URL filePath)
    {
        this.filePath = filePath;
        this.name = FileUtil.getFileName(filePath);
        data = StreamUtilities.getBytes(is);

        try
        {
            AudioInputStream in = AudioSystem.getAudioInputStream(is);
            if (in != null)
            {
                final AudioFormat baseFormat = in.getFormat();
                final AudioFormat decodedFormat = outFormat(baseFormat);
                // Liefert einen AudioInputStream, der durch das zugrunde
                // liegende
                // VorbisSPI dekodiert wird.
                in = AudioSystem.getAudioInputStream(decodedFormat, in);
                stream = in;
                streamData = StreamUtilities.getBytes(stream);
                format = stream.getFormat();
            }
        }
        catch (UnsupportedAudioFileException | IOException e)
        {
            throw new SoundException(e);
        }
    }

    /**
     * Erstellt eine neue Sound-Instanz aus dem angegeben <b>Dateipfad</b>.
     *
     * @param filePath Der Dateipfad der Audiodatei.
     *
     * @since 0.48.0
     */
    public Sound(String filePath)
    {
        this(ResourceLoader.get(filePath), FileUtil.toURL(filePath));
    }

    /* filePath */

    /**
     * Der Dateipfad der Audio-Datei.
     */
    private final URL filePath;

    /**
     * Liefert den Dateipfad der Audio-Datei.
     *
     * @return Der Dateipfad der Audio-Datei.
     *
     * @since 0.47.0
     */
    @API
    @Getter
    public URL filePath()
    {
        return filePath;
    }

    /* name */

    /**
     * Der <b>Dateiname</b> der Audio-Datei ohne Dateiendung.
     */
    private final String name;

    /**
     * Liefert den <b>Dateinamen</b> der Audio-Datei ohne Dateiendung.
     *
     * @return Der <b>Dateiname</b> der Audio-Datei ohne Dateiendung.
     */
    @API
    @Getter
    public String name()
    {
        return name;
    }

    /* Format */

    private AudioFormat format;

    /**
     * Liefert das <b>Audioformat</b> dieser Sound-Instanz.
     *
     * @return Das Audioformat dieser Instanz.
     */
    @API
    @Getter
    public AudioFormat format()
    {
        return format;
    }

    /* data */

    private byte[] data;

    /**
     * Liefert die <b>Rohdaten</b> dieses Sounds als Byte-Array.
     *
     * <p>
     * Diese Daten werden bei der Serialisierung von Ressourcen verwendet.
     * </p>
     *
     * @return Die Rohdaten dieses Sounds als Byte-Array.
     */
    public byte[] rawData()
    {
        return data;
    }

    /* streamData */

    private byte[] streamData;

    /**
     * Liefert die dekodierten Stream-Daten dieses Sounds als geklontes
     * Byte-Array.
     *
     * @return Die Stream-Daten oder ein leeres Array, falls keine Daten
     *     vorhanden sind.
     */
    @Getter
    byte[] streamData()
    {
        if (streamData == null)
        {
            return new byte[0];
        }
        return streamData.clone();
    }

    /**
     * Erzeugt das Ziel-Audioformat für die Dekodierung in PCM_SIGNED mit 16
     * Bit.
     *
     * @param inFormat Das Quell-Audioformat.
     *
     * @return Das Ziel-Audioformat für die Dekodierung.
     */
    @Getter
    private static AudioFormat outFormat(final AudioFormat inFormat)
    {
        final int ch = inFormat.getChannels();
        final float rate = inFormat.getSampleRate();
        return new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch,
                ch * 2, rate, false);
    }

    /**
     * @hidden
     */
    @Override
    public String toString()
    {
        var formatter = new ToStringFormatter(this);
        formatter.append("filePath", filePath);
        formatter.append("name", name);
        formatter.append("format", format);
        return formatter.format();
    }
}
