package View.Design.PopUps;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Created by Guido on 26.01.2016.
 */
public class RenameErrorWindow extends Alert{

    public RenameErrorWindow() {
        super(AlertType.ERROR);
        this.setTitle("rename Error occurred");
        this.setHeaderText("Could not rename Layer");
        this.getButtonTypes().setAll(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
    }

}
