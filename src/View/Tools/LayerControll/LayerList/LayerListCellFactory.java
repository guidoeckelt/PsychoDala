package View.Tools.LayerControll.LayerList;

import Model.Sheet.Layer;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

/**
 * Created by Guido on 26.01.2016.
 */
public class LayerListCellFactory {

    private static LayerListCellFactory Instance;
    private static Callback<ListView<Layer>,ListCell<Layer>> cellFactory;

    private LayerListCellFactory(){
    }


    public Callback<ListView<Layer>, ListCell<Layer>> getInstance(){
        return this.getInstance(null);
    }

    public static Callback<ListView<Layer>, ListCell<Layer>> getInstance(ContextMenu layerItemContextMenu){
        if(cellFactory == null){
            Instance = new LayerListCellFactory();
            cellFactory= param ->
            {
                LayerListItem cell = new LayerListItem();
                cell.setTextAlignment(TextAlignment.CENTER);
                cell.setAlignment(Pos.CENTER);
                cell.setContextMenu(layerItemContextMenu);
                cell.setCursor(Cursor.HAND);
                return cell;
            };
        }
        return cellFactory;

    }
}
