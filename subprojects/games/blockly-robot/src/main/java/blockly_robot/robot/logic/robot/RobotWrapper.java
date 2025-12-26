package blockly_robot.robot.logic.robot;

/**
 * Klasse, die den Roboter, einpackt und umhüllt, damit alle Methoden des
 * Roboters standardmäßig versteckt sind. Diese Klasse soll vererbt werden und
 * die Roboter der einzelnen Trainingsklassen greifen dann auf die Methoden zu,
 * die benötigt werden und die erlaubt sind, um die Aufgabe lösen zu können.
 */
public class RobotWrapper
{
    public Robot actor;
}
