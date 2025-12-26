package blockly_robot.robot.logic.log;

import java.util.ArrayList;
import java.util.List;

public class ActionLog
{
    private List<Action> actions;

    public ActionLog(List<Action> actions)
    {
        this.actions = actions;
    }

    public ActionLog()
    {
        actions = new ArrayList<Action>();
    }

    public void add(Action action)
    {
        actions.add(action);
    }

    public String[] toArray()
    {
        String[] result = new String[actions.size()];
        for (int i = 0; i < actions.size(); i++)
        {
            result[i] = actions.get(i).getName();
        }
        return result;
    }

    public void printActions()
    {
        System.out.println("\"" + String.join("\", \"", toArray()) + "\"");
    }
}
