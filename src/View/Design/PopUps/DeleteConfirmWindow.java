package View.Design.PopUps;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

/**
 * Created by Guido on 26.01.2016.
 */
public class DeleteConfirmWindow extends Alert {

    public DeleteConfirmWindow() {
        super(Alert.AlertType.CONFIRMATION);
        this.setTitle("Delete Confirmation Dialog");
        this.setHeaderText("Do You want to delete this Layer?");
        ButtonType btnConfirm = new ButtonType("Yes");
        ButtonType btnCancel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getButtonTypes().setAll(btnConfirm, btnCancel);
    }
}
