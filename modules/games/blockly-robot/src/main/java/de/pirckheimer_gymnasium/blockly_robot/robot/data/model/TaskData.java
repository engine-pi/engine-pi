package de.pirckheimer_gymnasium.blockly_robot.robot.data.model;

import de.pirckheimer_gymnasium.blockly_robot.robot.logic.level.Difficulty;

public class TaskData
{
    /**
     * Quelle HTML head title oder In h1 HTML-Tags im HTML-Quelltext. Zum
     * Beispiel: „Kerzen anzünden“
     */
    public String title;

    /**
     * Quelle HTML DIV class taskIntro
     *
     * Zeilenumbrüche mit \n
     *
     * Zum Beispiel: „Programmiere den Roboter: Der Roboter soll alle Kerzen
     * anzünden.“
     */
    public String intro;

    public IntrosByDifficultyData intros;

    /**
     * HTML DIV id=menu
     */
    public String[] menu;

    /**
     * Links zu den Quellen der JSON-Datei:
     * https://jwinf.de/tasks/jwinf/jwinf-aufgaben/2020/20-DE-13-Kerzen-einfach/index_new.html?channelId=task
     * https://jwinf.de/tasks/jwinf/jwinf-aufgaben/2020/20-DE-13-Kerzen-einfach/task_new.js
     */
    public String[] sources;

    /**
     * Zum Beispiel: „19-DE-12-stay-on-the-road“
     */
    public String id;

    /**
     * Zum Beispiel: „de.01“
     */
    public String version;

    /**
     * Zum Beispiel: „Jens Beuckenhauer“
     */
    public String authors;

    public String[] translators;

    public GridInfosData gridInfos;

    public LevelCollectionData data;

    public LevelData getLevel(Difficulty difficulty, int test)
    {
        return data.getLevel(difficulty, test);
    }

    public LevelData getLevel(Difficulty difficulty)
    {
        return getLevel(difficulty, 0);
    }

    public LevelData getLevel(int difficulty)
    {
        return getLevel(Difficulty.indexOf(difficulty), 0);
    }
}
