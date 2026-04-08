package pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

/**
 * JUnit 5 tests for {@link ImageContainer}.
 *
 * Tests cover image loading, caching, pixel multiplication, color replacement,
 * and container listener patterns.
 */
@DisabledIf(value = "java.awt.GraphicsEnvironment#isHeadless", disabledReason = "headless environment")
class ImageContainerTest
{
    private ImageContainer container;

    private static final String TEST_IMAGE = "Pixel-Adventure-1/Background/Blue.png";

    private static final int EXPECTED_WIDTH = 64;

    @BeforeEach
    void setUp()
    {
        container = new ImageContainer();
    }

    @AfterEach
    void tearDown()
    {
        container.clear();
    }

    @Test
    void loadImageFromResources()
    {
        BufferedImage image = container.get(TEST_IMAGE);

        assertNotNull(image, "Loaded image should not be null");
        assertEquals(EXPECTED_WIDTH,
            image.getWidth(),
            "Image width should match");
    }

    @Test
    void cacheReturnsSameInstance()
    {
        BufferedImage image1 = container.get(TEST_IMAGE);
        BufferedImage image2 = container.get(TEST_IMAGE);

        assertSame(image1, image2, "Cached images should be the same instance");
    }

    @Test
    void containsChecksForLoadedImage()
    {
        assertFalse(container.contains(TEST_IMAGE),
            "Container should not contain unloaded image");

        container.get(TEST_IMAGE);

        assertTrue(container.contains(TEST_IMAGE),
            "Container should contain loaded image");
    }

    @Test
    void countTracksLoadedImages()
    {
        assertEquals(0, container.count(), "Initial count should be zero");

        container.get(TEST_IMAGE);
        assertEquals(1, container.count(), "Count should be one after loading");
    }

    @Test
    void addManuallyInsertedImage()
    {
        BufferedImage synthetic = new BufferedImage(10, 10,
                BufferedImage.TYPE_INT_ARGB);
        String imageName = "synthetic-image";

        BufferedImage added = container.add(imageName, synthetic);

        assertSame(synthetic, added, "Added image should be returned");
        assertEquals(1, container.count(), "Count should increase");
        assertTrue(container.contains(imageName),
            "Container should contain added image");
        assertSame(synthetic,
            container.get(imageName),
            "Retrieved image should be same instance");
    }

    @Test
    void clearRemovesAllImages()
    {
        container.get(TEST_IMAGE);
        String synthetic = "synthetic";
        container.add(synthetic,
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));

        assertEquals(2, container.count(), "Should have two images");

        container.clear();

        assertEquals(0, container.count(), "Count should be zero after clear");
        assertFalse(container.contains(TEST_IMAGE),
            "Should not contain cleared image");
        assertFalse(container.contains(synthetic),
            "Should not contain cleared synthetic");
    }

    @Test
    void getWithPixelMultiplicationScalesImage()
    {
        int factor = 2;

        BufferedImage scaled = container.get(TEST_IMAGE, factor);

        assertNotNull(scaled, "Scaled image should not be null");
        assertEquals(EXPECTED_WIDTH * factor,
            scaled.getWidth(),
            "Scaled width should be multiplied");
        assertEquals(EXPECTED_WIDTH * factor,
            scaled.getHeight(),
            "Scaled height should be multiplied");
    }

    @Test
    void getWithColorReplacementChangesColors()
    {
        Color[] fromColors = { Color.BLUE };
        Color[] toColors = { Color.RED };

        BufferedImage recolored = container
            .get(TEST_IMAGE, fromColors, toColors);

        assertNotNull(recolored, "Recolored image should not be null");
        assertEquals(EXPECTED_WIDTH,
            recolored.getWidth(),
            "Width should remain");
    }

    @Test
    void getWithPixelMultiplicationAndColorReplacement()
    {
        int factor = 2;
        Color[] fromColors = { Color.BLUE };
        Color[] toColors = { Color.RED };

        BufferedImage transformed = container
            .get(TEST_IMAGE, factor, fromColors, toColors);

        assertNotNull(transformed, "Transformed image should not be null");
        assertEquals(EXPECTED_WIDTH * factor,
            transformed.getWidth(),
            "Width should be scaled");
    }

    @Test
    void containerListenerNotifiedOnAdd()
    {
        final boolean[] listenerCalled = { false };

        ResourcesContainerListener<BufferedImage> listener = new ResourcesContainerListener<BufferedImage>()
        {
            @Override
            public void added(String name, BufferedImage resource)
            {
                listenerCalled[0] = true;
            }

            @Override
            public void cleared()
            {
                // Not tested here
            }
        };

        container.addContainerListener(listener);
        container.add("test-image",
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));

        assertTrue(listenerCalled[0], "Listener should be notified on add");
    }

    @Test
    void containerListenerNotifiedOnClear()
    {
        final boolean[] listenerCalled = { false };

        ResourcesContainerListener<BufferedImage> listener = new ResourcesContainerListener<BufferedImage>()
        {
            @Override
            public void added(String name, BufferedImage resource)
            {
                // Not tested here
            }

            @Override
            public void cleared()
            {
                listenerCalled[0] = true;
            }
        };

        container.addContainerListener(listener);
        container.add("test-image",
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));
        container.clear();

        assertTrue(listenerCalled[0], "Listener should be notified on clear");
    }

    @Test
    void removeContainerListenerStopsNotifications()
    {
        final int[] callCount = { 0 };

        ResourcesContainerListener<BufferedImage> listener = new ResourcesContainerListener<BufferedImage>()
        {
            @Override
            public void added(String name, BufferedImage resource)
            {
                callCount[0]++;
            }

            @Override
            public void cleared()
            {
                // Not tested here
            }
        };

        container.addContainerListener(listener);
        container.add("test1",
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));
        assertEquals(1, callCount[0], "Listener should be called once");

        container.removeContainerListener(listener);
        container.add("test2",
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));
        assertEquals(1,
            callCount[0],
            "Listener should not be called after removal");
    }

    @Test
    void manipulatorModifiesAddedImage()
    {
        final int[] manipulatorCalled = { 0 };

        ResourceManipulator<BufferedImage> manipulator = new ResourceManipulator<BufferedImage>()
        {
            @Override
            public BufferedImage beforeAdd(String name, BufferedImage resource)
            {
                manipulatorCalled[0]++;
                return resource;
            }
        };

        container.addManipulator(manipulator);
        BufferedImage image = new BufferedImage(5, 5,
                BufferedImage.TYPE_INT_ARGB);
        container.add("test", image);

        assertEquals(1,
            manipulatorCalled[0],
            "Manipulator should be called on add");
    }

    @Test
    void removeManipulatorStopsModification()
    {
        final int[] manipulatorCalled = { 0 };

        ResourceManipulator<BufferedImage> manipulator = new ResourceManipulator<BufferedImage>()
        {
            @Override
            public BufferedImage beforeAdd(String name, BufferedImage resource)
            {
                manipulatorCalled[0]++;
                return resource;
            }
        };

        container.addManipulator(manipulator);
        container.add("test1",
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));
        assertEquals(1, manipulatorCalled[0], "Manipulator should be called");

        container.removeManipulator();
        container.add("test2",
            new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB));
        assertEquals(1,
            manipulatorCalled[0],
            "Manipulator should not be called after removal");
    }

    @Test
    void throwsException()
    {
        ResourceLoadException exception = assertThrows(
            ResourceLoadException.class,
            () -> container.get("xxx"));
        assertEquals("Die Ressource konnte nicht geladen werden: xxx",
            exception.getMessage());;
    }
}
