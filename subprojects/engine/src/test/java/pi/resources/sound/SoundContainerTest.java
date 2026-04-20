package pi.resources.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pi.resources.ResourceLoadException;

import pi.resources.ResourcesContainerListener;

/**
 * JUnit 5 tests for {@link SoundContainer}.
 */
class SoundContainerTest
{
    private SoundContainer container;

    private static final String TEST_SOUND = "demos/tetris/sounds/Block_move.wav";

    @BeforeEach
    void setUp()
    {
        container = new SoundContainer();
    }

    @AfterEach
    void tearDown()
    {
        container.clear();
    }

    @Test
    void loadSoundFromResources()
    {
        assertNotNull(container.get(TEST_SOUND),
            "Loaded sound should not be null");
    }

    @Test
    void throwsException()
    {
        assertThrows(ResourceLoadException.class,
            () -> container.get("xxxxxxx"));
    }

    @Test
    void loadedSoundHasName()
    {
        Sound sound = container.get(TEST_SOUND);

        assertEquals("Block_move",
            sound.name(),
            "Sound name should match file name without extension");
    }

    @Test
    void loadedSoundHasRawData()
    {
        Sound sound = container.get(TEST_SOUND);

        assertNotNull(sound.rawData(), "Raw data should not be null");
        assertTrue(sound.rawData().length > 0, "Raw data should not be empty");
    }

    @Test
    void cacheReturnsSameInstance()
    {
        Sound sound1 = container.get(TEST_SOUND);
        Sound sound2 = container.get(TEST_SOUND);

        assertSame(sound1, sound2, "Cached sounds should be the same instance");
    }

    @Test
    void containsChecksForLoadedSound()
    {
        assertFalse(container.contains(TEST_SOUND),
            "Container should not contain unloaded sound");

        container.get(TEST_SOUND);

        assertTrue(container.contains(TEST_SOUND),
            "Container should contain loaded sound");
    }

    @Test
    void countTracksLoadedSounds()
    {
        assertEquals(0, container.count(), "Initial count should be zero");

        container.get(TEST_SOUND);

        assertEquals(1, container.count(), "Count should be one after loading");
    }

    @Test
    void clearRemovesAllSounds()
    {
        container.get(TEST_SOUND);
        assertEquals(1,
            container.count(),
            "Should have one sound before clear");

        container.clear();

        assertEquals(0, container.count(), "Count should be zero after clear");
        assertFalse(container.contains(TEST_SOUND),
            "Should not contain cleared sound");
    }

    @Test
    void containerListenerNotifiedOnAdd()
    {
        final boolean[] listenerCalled = { false };

        ResourcesContainerListener<Sound> listener = new ResourcesContainerListener<Sound>()
        {
            @Override
            public void added(String name, Sound resource)
            {
                listenerCalled[0] = true;
            }

            @Override
            public void cleared()
            {
                // not tested here
            }
        };

        container.addContainerListener(listener);
        container.get(TEST_SOUND);

        assertTrue(listenerCalled[0], "Listener should be notified on add");
    }

    @Test
    void containerListenerNotifiedOnClear()
    {
        final boolean[] listenerCalled = { false };

        ResourcesContainerListener<Sound> listener = new ResourcesContainerListener<Sound>()
        {
            @Override
            public void added(String name, Sound resource)
            {
                // not tested here
            }

            @Override
            public void cleared()
            {
                listenerCalled[0] = true;
            }
        };

        container.addContainerListener(listener);
        container.get(TEST_SOUND);
        container.clear();

        assertTrue(listenerCalled[0], "Listener should be notified on clear");
    }

    @Test
    void removeContainerListenerStopsNotifications()
    {
        final int[] callCount = { 0 };

        ResourcesContainerListener<Sound> listener = new ResourcesContainerListener<Sound>()
        {
            @Override
            public void added(String name, Sound resource)
            {
                callCount[0]++;
            }

            @Override
            public void cleared()
            {
                // not tested here
            }
        };

        container.addContainerListener(listener);
        container.get(TEST_SOUND);
        assertEquals(1, callCount[0], "Listener should be called once");

        container.removeContainerListener(listener);
        container.clear();
        container.get(TEST_SOUND);
        assertEquals(1,
            callCount[0],
            "Listener should not be called after removal");
    }

    @Test
    void forceReloadFetchesNewInstance()
    {
        Sound first = container.get(TEST_SOUND);
        Sound reloaded = container.get(TEST_SOUND, true);

        assertNotNull(reloaded, "Force-reloaded sound should not be null");
        assertEquals(first.name(),
            reloaded.name(),
            "Reloaded sound should have the same name");
    }
}
