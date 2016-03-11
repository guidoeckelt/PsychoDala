package View.Design.PopUps;

import Model.Sheet.Layer;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Guido on 26.01.2016.
 */
public class RenameWindow extends TextInputDialog {

    public RenameWindow(String initValue) {
        super(initValue);
        this.setTitle("rename Layer");
        Image image = new Image("file:Icon/Edit/rename.png");
        this.setGraphic(new ImageView(image));
        this.setHeaderText(null);
        this.setContentText("Enter new name for "+initValue+":");
    }

    public boolean canRename(ObservableList<Layer> layerList, String wantedName){
        boolean canRename = true;
        if(wantedName.equals(null) && wantedName.equals("")){
            canRename = false;
        }
        Layer layer = null;
        for(int i = 0; i < layerList.size();i++){
            layer = layerList.get(i);
            if(layer.getName().equalsIgnoreCase(wantedName)){
                canRename = false;
                break;
            }
        }
        if(!canRename){
            RenameErrorWindow renameErrorWindow = new RenameErrorWindow();
            renameErrorWindow.setContentText(layer.getName());
            renameErrorWindow.show();
        }
        return  canRename;
    }

}
