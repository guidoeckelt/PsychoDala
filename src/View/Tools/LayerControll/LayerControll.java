package View.Tools.LayerControll;

import Model.Sheet.Layer;
import Model.Sheet.Scene;
import View.Design.Controls.ButtonBase;
import View.Design.PopUps.DeleteConfirmWindow;
import View.Design.PopUps.RenameWindow;
import View.Tools.LayerControll.LayerList.LayerItemContextMenu;
import View.Tools.LayerControll.LayerList.LayerListCellFactory;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Optional;

/**
 * Created by Guido on 26.01.2016.
 */
public class LayerControll extends VBox{

    private ListView<Layer> layerListView;
    private LayerItemContextMenu layerItemContextMenu;
    private Scene selectedScene;
    private ChangeListener<Tab> tabChangeListener;
    private ChangeListener<Layer> selectionListener;
    private ButtonBase btnAdd;

    public LayerControll() {
        tabChangeListener = (observable, oldValue, newValue)->{
            Scene newSelectedScene = (Scene)newValue.getContent();
            if(selectedScene != newSelectedScene){
                onNewSelectedScene(newSelectedScene);
            }
        };
        selectionListener = (observable, oldValue, newValue)->{
            if (selectedScene.getCurrentLayer() != newValue){
                selectedScene.setCurrentLayer(newValue);
            }
        };
        layerItemContextMenu = new LayerItemContextMenu();
        layerItemContextMenu.setOnDelete(ActionEvent -> deleteLayer());
        layerItemContextMenu.setOnRename(ActionEvent -> renameLayer());
        layerItemContextMenu.setOnRiseLayer(ActiveEvent -> riseLayer());
        layerItemContextMenu.setOnLowerLayer(ActiveEvent -> lowerLayer());


        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_RIGHT);
        btnAdd = new ButtonBase();
        Image image = new Image("file:Icon/Tools/Layer/new.png");
        btnAdd.setGraphic(new ImageView(image));

        Label label = new Label("LayerControll");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setPrefWidth(180);
        label.setAlignment(Pos.CENTER);
        Font font = new Font(18);
        label.setFont(font);
        container.getChildren().addAll(label, btnAdd);
        this.getChildren().add(container);

        layerListView = new ListView();
        label.setLabelFor(layerListView);
        layerListView.setMaxSize(200,200);
        layerListView.setEditable(true);
        layerListView.getSelectionModel().selectedItemProperty().addListener(selectionListener);
        layerListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        layerListView.setCellFactory(LayerListCellFactory.getInstance(layerItemContextMenu));
        layerListView.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent->{
            if(keyEvent.getCode() == KeyCode.ENTER){
                Layer layer = layerListView.getSelectionModel().getSelectedItem();
                //layerItemContextMenu.show(layerListView);
                System.out.println("LayerListItem Clicked(Enter)"+ keyEvent.getSource().getClass());
            }
        });
        this.getChildren().add(layerListView);
    }

    public void deleteLayer(){
        Layer toDeletingLayer = layerListView.getSelectionModel().getSelectedItem();
        DeleteConfirmWindow deleteConfirmWindow= new DeleteConfirmWindow();
        deleteConfirmWindow.setContentText(toDeletingLayer.getName());
        BorderPane container = new BorderPane();
        Label lblName = new Label(toDeletingLayer.getName());
        Image layerSnapshot = toDeletingLayer.getSheet().snapshot(new SnapshotParameters(),null);
        container.setLeft(lblName);
        container.setCenter(new ImageView(layerSnapshot));
        Optional<ButtonType> result = deleteConfirmWindow.showAndWait();
        if (result.get().getText().equalsIgnoreCase("Yes")){
            System.out.println(result.get().getText()+", Delete this "+toDeletingLayer.getName());
        }else {
            System.out.println(result.get().getText()+", DO NOT delete this "+toDeletingLayer.getName());
        }
    }

    public void renameLayer(){
        Layer toRenamingLayer = layerListView.getSelectionModel().getSelectedItem();
        RenameWindow renameWindow = new RenameWindow(toRenamingLayer.getName());
        Optional<String> result = renameWindow.showAndWait();
        result.ifPresent(name -> {
            if(renameWindow.canRename(selectedScene.getLayerList(),name)){
                toRenamingLayer.setName(name);
                layerListView.refresh();
            }
        });
    }

    public void lowerLayer(){
        Layer toLowerLayer = layerListView.getSelectionModel().getSelectedItem();
        System.out.println("lower Layer:"+toLowerLayer.getName());
    }

    public void riseLayer(){
        Layer toRisingLayer = layerListView.getSelectionModel().getSelectedItem();
        System.out.println("rise Layer:"+toRisingLayer.getName());
    }

    public void onNewSelectedScene(Scene newSelectedScene){
        selectedScene = newSelectedScene;
        btnAdd.setOnAction(selectedScene::addLayer);
        layerListView.setItems(selectedScene.getLayerList());
        if(selectedScene.getLayerList().size() > 0){
            layerListView.getSelectionModel().selectFirst();
            layerListView.getFocusModel().focus(0);
        }
    }

    public ChangeListener<Tab> getTabChangeListener() {
        return tabChangeListener;
    }

}
