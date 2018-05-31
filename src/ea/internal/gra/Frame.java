package ea.internal.gra;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import ea.internal.ano.NoExternalUse;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Beschreibt einen Frame einer {@link ea.actor.Animation}.
 * @author Nicklas Keller
 */
@NoExternalUse
public class Frame {
    /**
     * Das Bild, das zu diesem Frame gehört.
     */
    private final BufferedImage image;
    /**
     * Die Dauer (in ms), die dieser Frame aktiv bleibt.
     */
    private int duration;

    /**
     * Erstellt einen Frame.
     * @param image     Das Bild für den Frame.
     * @param duration  Die Dauer, die dieser Frame aktiv bleibt.
     */
    @NoExternalUse
    public Frame(BufferedImage image, int duration) {
        this.image = image;
        this.duration = duration;
    }

    @NoExternalUse
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @NoExternalUse
    public BufferedImage getImage() {
        return image;
    }

    @NoExternalUse
    public int getDuration() {
        return duration;
    }

    /**
     * Rendert den Frame (an der entsprechenden Position des Graphics Objekts)
     * @param g2d   Das Graphics Objekt
     */
    @NoExternalUse
    public void render(Graphics2D g2d, boolean flipHorizontal, boolean flipVertical) {
        g2d.drawImage(image,
                flipHorizontal ? image.getWidth() : 0,
                flipVertical ? image.getHeight() : 0,
                (flipHorizontal ? -1 : 1)*image.getWidth(),
                (flipVertical ? -1 : 1)*image.getHeight(),
                null);
    }
}
