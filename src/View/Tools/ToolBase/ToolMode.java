package View.Tools.ToolBase;

/**
 * Created by Guido on 31.01.2016.
 */
public enum ToolMode {
    Pinned("pinned"),
    Windowed("windowed");

    private String value;
    ToolMode(String value) {
        this.value = value;
    }
}
