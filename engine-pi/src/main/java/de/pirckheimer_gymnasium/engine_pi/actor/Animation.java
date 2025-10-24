/*
 * Source: https://github.com/engine-alpha/engine-alpha/blob/4.x/engine-alpha/src/main/java/ea/actor/Animation.java
 *
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2011 - 2017 Michael Andonie and contributors.
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
package de.pirckheimer_gymnasium.engine_pi.actor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Resources;
import de.pirckheimer_gymnasium.engine_pi.animation.AnimationFrame;
import de.pirckheimer_gymnasium.engine_pi.annotations.API;
import de.pirckheimer_gymnasium.engine_pi.annotations.Internal;
import de.pirckheimer_gymnasium.engine_pi.event.EventListeners;
import de.pirckheimer_gymnasium.engine_pi.event.FrameUpdateListener;
import de.pirckheimer_gymnasium.engine_pi.physics.FixtureBuilder;
import de.pirckheimer_gymnasium.engine_pi.resources.ResourceLoader;
import de.pirckheimer_gymnasium.engine_pi.util.FileUtil;
import de.pirckheimer_gymnasium.engine_pi.util.GifDecoder;

/**
 * Eine Animation ist ein {@link Actor}-Objekt, das aus mehreren
 * <a href="https://de.wikipedia.org/wiki/Einzelbild_(Film)">Einzelbildern</a>
 * besteht. Einzelbilder können auf verschiedene Arten aus Bilddateien
 * eingeladen werden:
 *
 * <ul>
 * <li>Animierte GIFs</li>
 * <li><a href=
 * "https://de.wikipedia.org/wiki/Sprite_(Computergrafik)">Spritesheets</a></li>
 * <li>Einzelne Bilddateien</li>
 * </ul>
 *
 * @author Michael Andonie
 */
@API
public class Animation extends Actor implements FrameUpdateListener
{
    private final AnimationFrame[] frames;

    /**
     * Die Breite der Animation in Meter.
     */
    private final double width;

    /**
     * Die Höhe der Animation in Meter.
     */
    private final double height;

    private transient double currentTime;

    private transient int currentIndex;

    /**
     * Liste aller Runnable, die beim Abschließen des Loops ausgeführt werden.
     */
    private final EventListeners<Runnable> onCompleteListeners = new EventListeners<>();

    private Animation(AnimationFrame[] frames, double width, double height)
    {
        super(() -> {
            if (frames.length < 1)
            {
                throw new RuntimeException(
                        "Eine Animation kann nicht mit einem leeren Frames-Array initialisiert werden.");
            }
            return FixtureBuilder.rectangle(width, height);
        });
        for (AnimationFrame frame : frames)
        {
            if (frame.getDuration() <= 0)
            {
                throw new RuntimeException(
                        "Ein Frame muss länger als 0 Sekunden sein.");
            }
        }
        this.frames = frames.clone();
        this.width = width;
        this.height = height;
        this.currentTime = 0;
        this.currentIndex = 0;
    }

    /**
     * Copy-Konstruktor, damit Vererbung genutzt werden kann.
     *
     * @param animation Animation.
     */
    public Animation(Animation animation)
    {
        this(animation.frames, animation.width, animation.height);
        animation.onCompleteListeners.invoke(this::addOnCompleteListener);
    }

    /**
     * Gibt die Frames dieser Animation aus.
     *
     * @return Die Frames dieser Animation.
     *
     * @hidden
     */
    @Internal
    public AnimationFrame[] getFrames()
    {
        return frames.clone();
    }

    /**
     * Gibt die Breite der Animation in Metern aus.
     *
     * @return Die Breite der Animation in Meter.
     *
     * @see #getHeight()
     */
    @API
    public double getWidth()
    {
        return width;
    }

    /**
     * Gibt die Höhe der Animation in Metern aus.
     *
     * @return Die Höhe der Animation in Meter.
     *
     * @see #getWidth()
     */
    @API
    public double getHeight()
    {
        return height;
    }

    /**
     * Fügt einen Listener hinzu. Die <code>run()</code>-Methode wird immer
     * wieder ausgeführt, sobald der <b>letzte Zustand der Animation
     * abgeschlossen wurde</b>.
     *
     * @param listener Ein Runnable, dessen run-Methode ausgeführt werden soll,
     *     sobald die Animation abgeschlossen ist (wird ausgeführt, bevor der
     *     Loop von Vorne beginnt).
     */
    @API
    public void addOnCompleteListener(Runnable listener)
    {
        onCompleteListeners.add(listener);
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void onFrameUpdate(double pastTime)
    {
        this.currentTime += pastTime;
        AnimationFrame currentFrame = this.frames[currentIndex];
        while (this.currentTime > currentFrame.getDuration())
        {
            this.currentTime -= currentFrame.getDuration();
            if (this.currentIndex + 1 == this.frames.length)
            {
                onCompleteListeners.invoke(Runnable::run);
                this.currentIndex = 0;
            }
            else
            {
                this.currentIndex += 1;
            }
        }
    }

    /**
     * @hidden
     */
    @Internal
    @Override
    public void render(Graphics2D g, double pixelPerMeter)
    {
        this.frames[currentIndex].render(g, width * pixelPerMeter,
                height * pixelPerMeter, false, false);
    }

    /**
     * Erzeugt eine Animation durch Angabe eines Spritesheets.
     *
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     * @param image Die bereits in den Speicher geladene Bilddatei des
     *     Spritesheets.
     * @param x Die Anzahl an Sprites in x-Richtung.
     * @param y Die Anzahl an Sprites in y-Richtung.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     *
     *
     * @since 0.25.0
     */
    @API
    public static Animation createFromSpritesheet(double frameDuration,
            BufferedImage image, int x, int y, double width, double height)
    {
        if (frameDuration <= 0)
        {
            throw new RuntimeException("Frame-Länge muss größer als 0 sein");
        }
        if (image.getWidth() % x != 0)
        {
            throw new RuntimeException(String.format(
                    "Spritesheet hat nicht die richtigen Maße (Breite: %d) um es auf %d Elemente in getX-Richtung aufzuteilen.",
                    image.getWidth(), x));
        }
        if (image.getHeight() % y != 0)
        {
            throw new RuntimeException(String.format(
                    "Spritesheet hat nicht die richtigen Maße (Höhe: %d) um es auf %d Elemente in getY-Richtung aufzuteilen.",
                    image.getHeight(), y));
        }
        int imageWidth = image.getWidth() / x;
        int imageHeight = image.getHeight() / y;
        List<AnimationFrame> frames = new LinkedList<>();
        for (int j = 0; j < y; j++)
        {
            for (int i = 0; i < x; i++)
            {
                frames.add(new AnimationFrame(image.getSubimage(i * imageWidth,
                        j * imageHeight, imageWidth, imageHeight),
                        frameDuration));
            }
        }
        return new Animation(frames.toArray(new AnimationFrame[0]), width,
                height);
    }

    /**
     * Erzeugt eine Animation durch Angabe eines Spritesheets.
     *
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     * @param filePath Der Dateipfad des Spritesheets.
     * @param x Die Anzahl an Sprites in x-Richtung.
     * @param y Die Anzahl an Sprites in y-Richtung.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     */
    @API
    public static Animation createFromSpritesheet(double frameDuration,
            String filePath, int x, int y, double width, double height)
    {
        return createFromSpritesheet(frameDuration,
                Resources.IMAGES.get(filePath), x, y, width, height);
    }

    /**
     * Erzeugt eine Animation durch Angabe eines Spritesheets.
     *
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     * @param filePath Der Dateipfad des Spritesheets.
     * @param spriteWidth Die Breite des Sprites in Pixel.
     * @param spriteHeight Die Höhe des Sprites in Pixel.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     *
     * @since 0.25.0
     *
     * @see Game#getPixelMultiplication()
     */
    @API
    public static Animation createFromSpritesheet(double frameDuration,
            String filePath, double width, double height, int spriteWidth,
            int spriteHeight)
    {
        BufferedImage image = Resources.IMAGES.get(filePath);
        return createFromSpritesheet(frameDuration, image,
                image.getWidth()
                        / (spriteWidth * Game.getPixelMultiplication()),
                image.getHeight()
                        / (spriteHeight * Game.getPixelMultiplication()),
                width, height);
    }

    /**
     * Erzeugt eine Animation durch Angabe der einzelnen Dateipfade der zu
     * verwendenden Einzelbilder.
     *
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     * @param images Die Einzelbilder.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     *
     * @since 0.26.0
     */
    @API
    public static Animation createFromImages(double frameDuration, double width,
            double height, BufferedImage... images)
    {
        if (frameDuration <= 0)
        {
            throw new RuntimeException("Frame-Länge muss größer als 1 sein.");
        }
        Collection<AnimationFrame> frames = new LinkedList<>();
        for (BufferedImage filepath : images)
        {
            frames.add(new AnimationFrame(filepath, frameDuration));
        }
        return new Animation(frames.toArray(new AnimationFrame[0]), width,
                height);
    }

    /**
     * Erzeugt eine Animation durch Angabe der einzelnen Dateipfade der zu
     * verwendenden Einzelbilder.
     *
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     * @param filePaths Die einzelnen Dateipfade der zu verwendenden
     *     Einzelbilder.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     */
    @API
    public static Animation createFromImages(double frameDuration, double width,
            double height, String... filePaths)
    {
        return createFromImages(frameDuration, width, height,
                Resources.IMAGES.getMultiple(filePaths));
    }

    /**
     * Lädt alle Bilddateien mit einem bestimmten <b>Präfix</b> in einem
     * bestimmten Verzeichnis in eine Animation.
     *
     * @param frameDuration Die Dauer in Sekunden, die die Einzelbilder aktiv
     *     bleiben.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     * @param directoryPath Der Pfad zum Verzeichnis, in dem die einzuladenden
     *     Bilder liegen.
     * @param prefix Das Pfad-Präfix. Diese Funktion sucht <a>alle Dateien mit
     *     dem gegebenen Präfix</a> (im angegebenen Ordner) und fügt sie in
     *     aufsteigender Reihenfolge der Animation hinzu.
     *
     * @return Eine Animation aus allen Dateien, die mit dem Pfadpräfix
     *     beginnen.
     */
    @API
    public static Animation createFromImagesPrefix(double frameDuration,
            double width, double height, String directoryPath, String prefix)
    {
        // Liste mit den Pfaden aller qualifizierten Dateien
        ArrayList<String> allPaths = new ArrayList<>();
        File directory = ResourceLoader.loadAsFile(directoryPath);
        if (!directory.isDirectory())
        {
            throw new RuntimeException(
                    "Der angegebene Pfad war kein Verzeichnis: "
                            + directoryPath);
        }
        File[] children = directory.listFiles();
        if (children != null)
        {
            for (File file : children)
            {
                if (!file.isDirectory() && file.getName().startsWith(prefix))
                {
                    allPaths.add(file.getAbsolutePath());
                }
            }
        }
        allPaths.sort(Comparator.naturalOrder());
        if (allPaths.isEmpty())
        {
            throw new RuntimeException("Konnte keine Bilder mit Präfix \""
                    + prefix + "\" im Verzeichnis \"" + directoryPath
                    + "\" finden.");
        }
        return createFromImages(frameDuration, width, height,
                allPaths.toArray(new String[0]));
    }

    /**
     * Erzeugt eine Animation aus einer animierter GIF-Datei.
     *
     * @param filepath Der Dateipfad der GIF-Datei.
     * @param width Die Breite der Animation in Meter.
     * @param height Die Höhe der Animation in Meter.
     *
     * @return Eine mit Einzelbildern bestückte Animation.
     */
    @API
    public static Animation createFromAnimatedGif(String filepath, double width,
            double height)
    {
        GifDecoder gifDecoder = new GifDecoder();
        if (!FileUtil.exists(filepath))
        {
            throw new RuntimeException(
                    "Der Dateipfad existiert nicht: " + filepath);
        }
        gifDecoder.read(filepath);
        int frameCount = gifDecoder.getFrameCount();
        AnimationFrame[] frames = new AnimationFrame[frameCount];
        for (int i = 0; i < frameCount; i++)
        {
            BufferedImage frame = gifDecoder.getFrame(i);
            int durationInMillis = gifDecoder.getDelay(i);
            frames[i] = new AnimationFrame(frame, durationInMillis / 1000.0);
        }
        return new Animation(frames, width, height);
    }
}
