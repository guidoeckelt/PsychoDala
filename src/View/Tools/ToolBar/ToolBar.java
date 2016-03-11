package View.Tools.ToolBar;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by Guido on 29.01.2016.
 */
public class ToolBar extends javafx.scene.control.ToolBar {

    public ToolBar() {
        Border border = new Border(new BorderStroke
            (Color.grayRgb(178), BorderStrokeStyle.SOLID,null,new BorderWidths(2,2,0,2)));

        this.setBackground(new Background(new BackgroundFill
                (Color.TRANSPARENT,null, null)));
        this.setBorder(border);
        this.setPadding(new Insets(0));
        this.setHeight(25);
        this.setManaged(true);
        this.setOrientation(Orientation.HORIZONTAL);
    }

    public void updateWidth(double newWidth){
        this.setWidth(newWidth);
    }
}
