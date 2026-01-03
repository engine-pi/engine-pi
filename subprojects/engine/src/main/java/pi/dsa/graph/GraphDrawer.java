package pi.dsa.graph;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import pi.Game;
import pi.Scene;
import pi.Vector;
import pi.actor.LabeledNode;
import pi.event.KeyStrokeListener;
import pi.event.MouseButton;
import pi.event.MouseClickListener;

enum EditState
{
    PLACE_NODE, ADD_NODE_LABEL
}

/**
 * Per Mausklick und Tastatureingaben einen Graphen zeichnen, der dann als
 * Java-Code exportiert werden kann. So können Graphen-Bilder (z. B. aus
 * Schulbüchern) nachgezeichnet werden und dadurch die Koordinaten bestimmt
 * werden.
 *
 * <ol>
 * <li>Erzeugen eines Knotens durch Mausklick.</li>
 * <li>Eingabe des Knoten-Bezeichners über die Tastatur. Durch Betätigung der
 * Eingabetaste kann ein neuer Knoten plaziert werden.</li>
 * <li>{@code Strg + s} exportiert den Graphen als Java-Code auf der
 * Konsole.</li>
 * </ol>
 */
public class GraphDrawer implements MouseClickListener, KeyStrokeListener
{
    private Scene scene;

    private Graph graph;

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
        graph = new GraphArrayMatrix();
    }

    private int getCurrentNodexIndex()
    {
        return nodes.size() - 1;
    }

    private LabeledNode getCurrentLabeledNode()
    {
        return nodes.get(getCurrentNodexIndex());
    }

    private GraphNode getCurrentGraphNode()
    {
        return graph.node(getCurrentNodexIndex());
    }

    private void updateCurrentNodeLabel()
    {
        LabeledNode labeledNode = getCurrentLabeledNode();
        if (labeledNode != null)
        {
            labeledNode.label(currentLabel);
        }

        GraphNode graphNode = getCurrentGraphNode();
        if (graphNode != null)
        {
            graphNode.label(currentLabel);
        }
    }

    /**
     * Erzeugt und plaziert einen neuen Knoten.
     *
     * @param position
     */
    private void placeNode(Vector position)
    {
        LabeledNode node = new LabeledNode();
        node.center(position);
        scene.add(node);
        nodes.add(node);
        graph.addNode("", position.x(), position.y());
    }

    public void onMouseDown(Vector position, MouseButton button)
    {
        if (editState == EditState.PLACE_NODE)
        {
            placeNode(position);
            editState = EditState.ADD_NODE_LABEL;
        }
    }

    @Override
    public void onKeyDown(KeyEvent event)
    {
        int code = event.getKeyCode();

        // Strg + s exportiert den Java-Code
        if (code == KeyEvent.VK_S && event.isControlDown())
        {
            System.out.println(graph.generateJavaCode());
        }

        if (editState != EditState.ADD_NODE_LABEL)
            return;

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
            updateCurrentNodeLabel();
        }
        // Mit der Eingabetaste die Eingabe beenden.
        else if (code == KeyEvent.VK_ENTER)
        {
            updateCurrentNodeLabel();
            currentLabel = "";
            editState = EditState.PLACE_NODE;
        }
        // Das neue Zeichen zum Bezeichner des aktuellen Knotens hinzufügen.
        else if (!Character.isISOControl(character))
        {
            currentLabel += character;
            updateCurrentNodeLabel();
        }
    }

    public static void main(String[] args)
    {
        Game.start(scene -> {
            new GraphDrawer(scene);
        });
    }

}
