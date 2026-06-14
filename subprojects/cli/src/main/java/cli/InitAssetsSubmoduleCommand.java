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

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import picocli.CommandLine.Command;

/**
 * Initialisiert das Git-Submodul {@code assets}.
 *
 * @since 0.49.0
 */
@Command(name = "init-assets-submodule", mixinStandardHelpOptions = true, description = "Initializes the git submodule 'assets' using JGit")
public class InitAssetsSubmoduleCommand implements Callable<Integer>
{
    private static final String SUBMODULE_PATH = "assets";

    @Override
    public Integer call()
    {
        try
        {
            FileRepositoryBuilder builder = new FileRepositoryBuilder()
                .readEnvironment()
                .findGitDir(new File("."));

            if (builder.getGitDir() == null)
            {
                System.err
                    .println("No git repository found from current directory.");
                return 1;
            }

            try (Repository repository = builder.build();
                    Git git = new Git(repository))
            {
                Collection<String> initialized = git.submoduleInit()
                    .addPath(SUBMODULE_PATH)
                    .call();

                if (initialized.isEmpty())
                {
                    System.out.println("Submodule '" + SUBMODULE_PATH
                            + "' was already initialized or is not configured.");
                }
                else
                {
                    System.out.println("Initialized submodule path(s): "
                            + String.join(", ", initialized));
                }

                Collection<String> updated = git.submoduleUpdate()
                    .addPath(SUBMODULE_PATH)
                    .call();

                if (updated.isEmpty())
                {
                    System.out.println(
                        "Submodule '" + SUBMODULE_PATH + "' was not updated.");
                }
                else
                {
                    System.out.println("Updated submodule path(s): "
                            + String.join(", ", updated));
                }
            }
            return 0;
        }
        catch (IOException | GitAPIException e)
        {
            System.err.println(
                "Failed to initialize 'assets' submodule: " + e.getMessage());
            return 1;
        }
    }

    public static void main(String[] args)
    {
        new InitAssetsSubmoduleCommand().call();
    }
}
