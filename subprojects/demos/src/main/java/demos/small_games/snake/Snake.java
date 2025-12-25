package demos.small_games.snake;

import pi.Vector;

/**
 *
 */
public class Snake
{
    SnakeHead head;

    SnakeBodyElement body;

    SnakeScene scene;

    public Snake(SnakeScene scene)
    {
        this.scene = scene;
        head = new SnakeHead();
        scene.add(head);
    }

    /**
     * Die Schlange wächst vom Kopf aus. Der bisherige Kopf wird durch ein
     * Körperelement ersetzt.
     *
     * TODO fix keine neuen Köpfe erzeugen
     *
     * @param dX
     * @param dY
     */
    public void grow(double dX, double dY)
    {
        Vector oldPosition = head.getCenter();
        SnakeBodyElement firstBodyElement = new SnakeBodyElement();
        scene.add(firstBodyElement);
        firstBodyElement.setCenter(oldPosition);
        scene.remove(head);
        SnakeBodyElement oldFirstBodyElement = body;
        body = firstBodyElement;
        firstBodyElement.next = oldFirstBodyElement;
        head = new SnakeHead();
        head.setCenter(oldPosition);
        scene.add(head);
        move(dX, dY);
    }

    public void move(double dX, double dY)
    {
        head.moveBy(dX, dY);

        if (body != null)
        {
            body.moveChildren(new Vector(dX, dY));
        }
    }
}
