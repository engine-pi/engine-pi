package de.pirckheimer_gymnasium.engine_pi.dsa.graph;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.pirckheimer_gymnasium.engine_pi.Game;
import de.pirckheimer_gymnasium.engine_pi.Scene;
import de.pirckheimer_gymnasium.engine_pi.Vector;
import de.pirckheimer_gymnasium.engine_pi.actor.LabeledNode;
import de.pirckheimer_gymnasium.engine_pi.event.KeyStrokeListener;
import de.pirckheimer_gymnasium.engine_pi.event.MouseButton;
import de.pirckheimer_gymnasium.engine_pi.event.MouseClickListener;

enum EditState
{
    PLACE_NODE, ADD_NODE_LABEL
}

public class GraphDrawer implements MouseClickListener, KeyStrokeListener
{
    private Scene scene;

    private ArrayList<LabeledNode> nodes;

    private String currentLabel;

    private EditState editState;

    public GraphDrawer(Scene scene)
    {
        this.scene = scene;
        scene.addMouseClickListener(this);
        scene.addKeyStrokeListener(this);
        nodes = new ArrayList<>();
        editState = EditState.PLACE_NODE;
        currentLabel = "";
    }

    private LabeledNode getCurrentNode()
    {
        return nodes.get(nodes.size() - 1);
    }

    private void updateCurrentLabel()
    {
        LabeledNode node = getCurrentNode();
        if (node != null)
        {
            node.setLabel(currentLabel);
        }
    }

    public void onMouseDown(Vector position, MouseButton button)
    {
        if (editState == EditState.PLACE_NODE)
        {
            LabeledNode node = new LabeledNode();
            node.setCenter(position);
            scene.add(node);
            nodes.add(node);
            editState = EditState.ADD_NODE_LABEL;
        }
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        if (editState != EditState.ADD_NODE_LABEL)
            return;

        int code = event.getKeyCode();

        if (code == KeyEvent.VK_SHIFT || code == KeyEvent.VK_CONTROL
                || code == KeyEvent.VK_ALT || code == KeyEvent.VK_META
                || code == KeyEvent.VK_ALT_GRAPH)
        {
            // Die Umschalttasten lösen auch ein Ereignis aus.
            return;
        }

        char character = event.getKeyChar();

        // Mit der Rückschritttaste (Backspace) den letzten Buchstaben löschen.
        if (code == KeyEvent.VK_BACK_SPACE && !currentLabel.isEmpty())
        {
            currentLabel = currentLabel.substring(0, currentLabel.length() - 1);
            updateCurrentLabel();
        }
        // Mit der Eingabetaste die Eingabe beenden.
        else if (code == KeyEvent.VK_ENTER)
        {
            updateCurrentLabel();
            currentLabel = "";
            editState = EditState.PLACE_NODE;
        }
        // Das neue Zeichen zum Bezeichner des aktuellen Knotens hinzufügen.
        else if (!Character.isISOControl(character))
        {
            currentLabel += character;
            updateCurrentLabel();
        }
    }

    public static void main(String[] args)
    {
        Game.start(scene -> {
            new GraphDrawer(scene);
        });
    }

}
