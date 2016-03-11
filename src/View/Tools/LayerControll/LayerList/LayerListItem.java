package View.Tools.LayerControll.LayerList;

import Model.Sheet.Layer;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

/**
 * Created by Guido on 26.01.2016.
 */
public class LayerListItem extends ListCell<Layer> {

    public LayerListItem() {

    }

    @Override
    public void updateItem(Layer item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        }else if(item != null){
            setText(item.getName());
            Font font = new Font(16);
            setFont(font);
            setContentDisplay(ContentDisplay.CENTER);
        }else{
            setText(null);
            setGraphic(null);
        }
    }

}
