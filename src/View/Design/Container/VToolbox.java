package View.Design.Container;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;

/**
 * Created by 67827 on 21.01.2016.
 */
public class VToolbox extends VBox {

    public VToolbox(Background ribbonBackground) {
        this(ribbonBackground,3);
    }

    public VToolbox(Background background,int height) {
        this.setBackground(background);
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinWidth(height);
    }
}
