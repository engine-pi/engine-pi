package pi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pi.graphics.geom.Direction;

public class ConfigurationTest
{
    Configuration config = Configuration.getInstance();

    @Test
    public void all()
    {
        // Go to
        // file:///home/jf/repos/school/monorepo/inf/java/engine-pi/docs/manual/resources/config.md
        assertEquals(true, config.game.instantMode());

        assertEquals(768, config.graphics.windowWidth());
        assertEquals(576, config.graphics.windowHeight());
        assertEquals(Direction.NONE, config.graphics.windowPosition());
        assertEquals(32.0, config.graphics.pixelPerMeter());
        assertEquals(0.05, config.graphics.zoomChange());
        assertEquals(60, config.graphics.framerate());
        assertEquals("Gnome", config.graphics.colorScheme());
        assertEquals(1, config.graphics.pixelMultiplication());
        assertEquals(2, config.graphics.screenRecordingNFrames());

        assertEquals(0.5, config.sound.soundVolume());
        assertEquals(0.5, config.sound.musicVolume());

        assertEquals(false, config.debug.enabled());
        assertEquals(false, config.debug.verbose());
        assertEquals(true, config.debug.renderActors());
        assertEquals(false, config.debug.actorCoordinates());

        assertEquals(-1, config.coordinatesystem.linesNMeter());
        assertEquals(false, config.coordinatesystem.labelsOnIntersections());
    }
}
