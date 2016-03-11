package View.Tools.ToolBase;

import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 * Created by Guido on 31.01.2016.
 */
public class ToolBase {

    private ToolMode toolMode;
    private PinnedPos pinnedPos;
    private Point2D windowPosition;
    private Node toolContainer;

    public ToolBase() {
        toolMode = ToolMode.Pinned;
        pinnedPos = PinnedPos.Left;
        windowPosition = Point2D.ZERO;
    }

    public Node getToolContainer() {
        return toolContainer;
    }

}
