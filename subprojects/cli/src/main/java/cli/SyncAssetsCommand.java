/*
 * Engine Pi ist eine anfängerorientierte 2D-Gaming Engine.
 *
 * Copyright (c) 2026 Josef Friedrich and contributors.
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
package cli;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;

/**
 * Synchronisiert Ressourcen aus {@code assets/} in die Subprojekte.
 *
 * @since 0.48.0
 */
@Command(name = "sync-assets", mixinStandardHelpOptions = true, description = "Syncs asset resources to subprojects using rsync")
public class SyncAssetsCommand implements Callable<Integer>
{
    private static final List<SyncTarget> TARGETS = List.of(
        new SyncTarget("assets/blockly-robot/resources/",
                "subprojects/games/blockly-robot/src/main/resources/"),
        new SyncTarget("assets/demos/resources/",
                "subprojects/demos/src/main/resources/"),
        new SyncTarget("assets/pacman/resources/",
                "subprojects/games/pacman/src/main/resources/"),
        new SyncTarget("assets/tetris/resources/",
                "subprojects/games/tetris/src/main/resources/"),
        new SyncTarget("assets/demos/resources/",
                "subprojects/engine/src/test/resources/demos/"),
        new SyncTarget("assets/tetris/resources/images/image-text/",
                "subprojects/engine/src/test/resources/image-text/"),
        new SyncTarget("assets/tetris/resources/images/image-text/",
                "subprojects/demos/src/main/resources/main-classes/actor/image-text/tetris/"),
        new SyncTarget("assets/pacman/resources/images/image-text/",
                "subprojects/demos/src/main/resources/main-classes/actor/image-text/pacman/"),
        new SyncTarget("assets/space-invaders/resources/images/image-text/",
                "subprojects/demos/src/main/resources/main-classes/actor/image-text/space-invaders/"));

    @Override
    public Integer call()
    {
        Path basePath = Path.of(".").toAbsolutePath().normalize();
        for (SyncTarget target : TARGETS)
        {
            Path source = basePath.resolve(target.source()).normalize();
            Path destination = basePath.resolve(target.destination()).normalize();
            System.out.printf("Synchronizing: %s -> %s%n", target.source(),
                target.destination());
            try
            {
                synchronizeDirectory(source, destination);
            }
            catch (IOException e)
            {
                System.err.printf("Synchronization failed for %s -> %s (%s)%n",
                    target.source(),
                    target.destination(),
                    e.getMessage());
                return 1;
            }
        }

        return 0;
    }

    private static void synchronizeDirectory(Path source, Path destination)
            throws IOException
    {
        if (!Files.isDirectory(source))
        {
            throw new IOException("Source directory does not exist: " + source);
        }
        Files.createDirectories(destination);

        copySourceTree(source, destination);
        deleteExtraneousTargetEntries(source, destination);
    }

    private static void copySourceTree(Path source, Path destination)
            throws IOException
    {
        Files.walkFileTree(source, new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                    BasicFileAttributes attrs) throws IOException
            {
                Path relative = source.relativize(dir);
                Path targetDir = destination.resolve(relative);
                Files.createDirectories(targetDir);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException
            {
                Path relative = source.relativize(file);
                Path targetFile = destination.resolve(relative);
                Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void deleteExtraneousTargetEntries(Path source,
            Path destination) throws IOException
    {
        Files.walkFileTree(destination, new SimpleFileVisitor<>()
        {
            @Override
            public FileVisitResult visitFile(Path file,
                    BasicFileAttributes attrs) throws IOException
            {
                Path relative = destination.relativize(file);
                Path sourceFile = source.resolve(relative);
                if (!Files.exists(sourceFile))
                {
                    Files.delete(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
                    throws IOException
            {
                Path relative = destination.relativize(dir);
                if (relative.getNameCount() == 0)
                {
                    return FileVisitResult.CONTINUE;
                }
                Path sourceDir = source.resolve(relative);
                if (!Files.exists(sourceDir))
                {
                    Files.delete(dir);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private record SyncTarget(String source, String destination)
    {
    }

    public static void main(String[] args) {
        new SyncAssetsCommand().call();
    }
}
