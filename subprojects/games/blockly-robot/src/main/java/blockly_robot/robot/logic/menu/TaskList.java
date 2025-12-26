package blockly_robot.robot.logic.menu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import blockly_robot.robot.logic.Task;
import pi.resources.ResourceLoader;

public class TaskList
{
    private List<String> relPaths;

    private int current = 0;

    public TaskList(List<String> relPaths)
    {
        this.relPaths = relPaths;
    }

    public static TaskList readFromResources() throws IOException
    {
        try (Stream<Path> stream = Files
                .walk(ResourceLoader.loadAsFile("data/tasks").toPath()))
        {
            var ids = stream.filter((path) -> {
                File file = path.toFile();
                return !file.isDirectory()
                        && !file.getName().equals("_template.json");
            }).map(Path::toAbsolutePath).map(Path::toString)
                    .map((String fileName) -> fileName.replace(".json", ""))
                    .map((absPath) -> absPath.replaceAll(".*data/tasks/", ""))
                    .collect(Collectors.toList());
            ids.sort(String::compareTo);
            return new TaskList(ids);
        }
    }

    public static TaskList readFromMenu()
    {
        Menu menu = new Menu();
        List<String> relPaths = new ArrayList<>();
        menu.getMain().forEach((main, sub) -> {
            sub.forEach((subMenu, relPath) -> {
                if (relPath != null)
                {
                    relPaths.add(relPath);
                }
            });
        });
        return new TaskList(relPaths);
    }

    public int size()
    {
        return relPaths.size();
    }

    public List<String> getRelPaths()
    {
        return relPaths;
    }

    public String getId(int index)
    {
        return relPaths.get(current);
    }

    public Task get(int index)
    {
        return Task.loadByTaskPath(getId(index));
    }

    public void reset()
    {
        current = 0;
    }

    public String previous()
    {
        if (current == 0)
        {
            current = relPaths.size() - 1;
        }
        else
        {
            current--;
        }
        return relPaths.get(current);
    }

    public String next()
    {
        if (current == relPaths.size() - 1)
        {
            current = 0;
        }
        else
        {
            current++;
        }
        return relPaths.get(current);
    }
}
