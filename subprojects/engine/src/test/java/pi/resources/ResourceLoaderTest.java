package pi.resources;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ResourceLoaderTest
{
    @TempDir
    Path tempDir;

    @Test
    void loadReadsBytesFromFileSystem() throws IOException
    {
        Path file = tempDir.resolve("data.bin");
        byte[] content = new byte[] { 1, 2, 3, 4 };
        Files.write(file, content);

        byte[] loaded = ResourceLoader.load(file.toString());

        assertArrayEquals(content, loaded);
    }

    @Test
    void loadAsStreamReadsFileSystemFile() throws IOException
    {
        Path file = tempDir.resolve("stream.txt");
        Files.writeString(file, "hello", StandardCharsets.UTF_8);

        try (InputStream stream = ResourceLoader.loadAsStream(file.toString()))
        {
            assertNotNull(stream);
            assertEquals("hello",
                new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    @Test
    void loadAsFileReturnsExistingFile() throws IOException
    {
        Path file = tempDir.resolve("asset.txt");
        Files.writeString(file, "engine", StandardCharsets.UTF_8);

        File loaded = ResourceLoader.loadAsFile(file.toString());

        assertTrue(loaded.exists());
        assertEquals(file.toFile().getCanonicalPath(),
            loaded.getCanonicalPath());
    }

    @Test
    void getWrapsNonMarkableStreams() throws IOException
    {
        Path file = tempDir.resolve("wrapped.txt");
        Files.writeString(file, "abc", StandardCharsets.UTF_8);

        URL location = ResourceLoader.location(file.toString());
        try (InputStream stream = ResourceLoader.get(location))
        {
            assertNotNull(stream);
            assertTrue(stream instanceof BufferedInputStream);
            assertEquals("abc",
                new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        }
    }

    @Test
    void readWithCharsetReadsContent() throws IOException
    {
        Path file = tempDir.resolve("latin1.txt");
        byte[] bytes = "gr\u00FCn".getBytes(StandardCharsets.ISO_8859_1);
        Files.write(file, bytes);

        String content = ResourceLoader.read(file.toString(),
            StandardCharsets.ISO_8859_1);

        assertEquals("gr\u00FCn", content);
    }

    @Test
    void readReturnsNullForMissingFile()
    {
        String missing = tempDir.resolve("missing.txt").toString();

        assertThrows(ResourceLoadException.class,
            () -> ResourceLoader.read(missing));
    }
}
