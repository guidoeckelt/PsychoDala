package View.Tools.LayerControll.LayerList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Guido on 26.01.2016.
 */
public class LayerItemContextMenu extends ContextMenu {

    private MenuItem btnDelete;
    private MenuItem btnRename;
    private MenuItem btnLowerLayer;
    private MenuItem btnRiseLayer;

    public LayerItemContextMenu() {

        btnRename = new MenuItem("Rename");
        Image image = new Image("file:Icon/Edit/rename.png");
        btnRename.setGraphic(new ImageView(image));
        this.getItems().add(btnRename);

        this.getItems().add(new SeparatorMenuItem());

        btnLowerLayer = new MenuItem("Lower Layer");
        image = new Image("file:Icon/Tools/Layer/lower.png");
        btnLowerLayer.setGraphic(new ImageView(image));
        btnLowerLayer.setDisable(true);
        this.getItems().add(btnLowerLayer);

        btnRiseLayer = new MenuItem("Rise Lower");
        image = new Image("file:Icon/Tools/Layer/rise.png");
        btnRiseLayer.setGraphic(new ImageView(image));
        btnRiseLayer.setDisable(true);
        this.getItems().add(btnRiseLayer);

        btnDelete = new MenuItem("Delete");
        image = new Image("file:Icon/Tools/Layer/delete.png");
        btnDelete.setGraphic(new ImageView(image));
        btnDelete.setDisable(true);
        this.getItems().add(btnDelete);
    }

    public void setOnDelete(EventHandler<ActionEvent> deleteHandler){
        btnDelete.setOnAction(deleteHandler);
    }

    public void setOnRename(EventHandler<ActionEvent> renameHandler){
        btnRename.setOnAction(renameHandler);
    }

    public void setOnRiseLayer(EventHandler<ActionEvent> riseHandler){
        btnRiseLayer.setOnAction(riseHandler);
    }

    public void setOnLowerLayer(EventHandler<ActionEvent> lowerHandler){
        btnLowerLayer.setOnAction(lowerHandler);
    }

}
