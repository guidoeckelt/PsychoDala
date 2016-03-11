package View.Design.Container;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;

/**
 * Created by 67827 on 21.01.2016.
 */
public class HToolbox extends HBox {

    public HToolbox(Background ribbonBackground) {
        this(ribbonBackground,3);
    }

    public HToolbox(Background ribbonBackground, int height) {
        this.setBackground(ribbonBackground);
        this.setAlignment(Pos.CENTER_LEFT);
        this.setMinHeight(height);
    }
}
