package de.pirckheimer_gymnasium.blockly_robot.jwinf.en.tasks;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import de.pirckheimer_gymnasium.blockly_robot.robot.Solver;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty;
import de.pirckheimer_gymnasium.blockly_robot.robot.logic.navigation.Coords;

public class TaskTester<T>
{
    Solver<T> solver;

    public TaskTester(Solver<T> solver)
    {
        this.solver = solver;
    }

    /**
     * Asserts the route of an actor in a specific difficulty and test case.
     * Compares the actor's reported route with the provided arguments. Also
     * checks if the actor's end position matches the specified row and column.
     *
     * @param difficulty the difficulty level of the test case
     * @param test the test case number
     * @param endRow the expected row of the actor's end position
     * @param endCol the expected column of the actor's end position
     * @param args the expected route of the actor
     *
     * @throws Exception if an error occurs during the assertion
     */
    public void assertActions(Difficulty difficulty, int test, int endRow,
            int endCol, String... args) throws Exception
    {
        var w = solver.solveVirtual(difficulty, test);
        assertArrayEquals(w.actor.reportActions(), args,
                "\"" + String.join("\", \"", w.actor.reportActions()) + "\"");
        Coords p = w.actor.getCoords();
        assertEquals(p.getRow(), endRow, "row");
        assertEquals(p.getCol(), endCol, "col");
    }
}
