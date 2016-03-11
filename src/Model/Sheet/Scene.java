package Model.Sheet;

import Interface.Drawing.DrawObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guido on 15-Dec-15.
 */
public class Scene extends Pane {
    //Scene
    private String name;
    private ObservableList<Layer> layerList = FXCollections.observableArrayList();
    private Layer currentLayer;
    private List<DrawObject> drawObjectList = new ArrayList<>();
    private int processCounter = 1;

    public Scene(String name){
        this.name = name;
        Border blackBorder = new Border
                (new BorderStroke(Color.BLACK,null,null,new BorderWidths(5)));

        this.setBorder(blackBorder);
        //this.getChildren().add(base);
        Background sheetBackground = new Background(new BackgroundFill(Color.WHITE,new CornerRadii(0),new Insets(0)));
        this.setBackground(sheetBackground);

    }

    // Layer System
    public boolean addLayer(){
        boolean canAdd;
        Layer newLayer = new Layer(layerList.size());
        //newLayer.getSheet().updateSize(new Dimension2D(this.getWidth(),this.getHeight()));
        this.widthProperty().addListener(newLayer.getSheetController().getWidthListener());
        this.heightProperty().addListener(newLayer.getSheetController().getHeightListener());
        canAdd = layerList.add(newLayer);
        if (canAdd){
            canAdd = this.getChildren().add(newLayer.getSheet());
        }
        if(newLayer.getOrderNumber() == 0){
            currentLayer = newLayer;
        }
        return canAdd;
    }

    public  boolean removeLayer(String layerName){
        Layer toDeletigLayer = null;
        layerList.forEach(layer -> {
            if (layer.getName() == layerName) {
                //toDeletigLayer = layer;
            }
        });
        return false;
    }

    //TODO moveLayers
    //TODO remove Layers
    //TODO snapshot AllLayers?
    //TODO only see FrontCenter, Only See what you want
    //TODO Tool

    public void add(List<DrawObject> toDrawingPoints) {
        for (DrawObject toDrawingObject : toDrawingPoints) {
            toDrawingObject.setProcessNumber(processCounter);
            drawObjectList.add(toDrawingObject);
            currentLayer.add(toDrawingObject);
        }
        processCounter++;
        layerList.forEach(layer->{
            System.out.println(layer.getName()+" : "+layer.getSheet().getChildren().size());
        });
    }

    public void remove(List<DrawObject> toRemovingPoints){
        if(drawObjectList.size() > 0){
            List<DrawObject> list = new ArrayList<>(drawObjectList);
            int size = drawObjectList.size();
            for(int i = 0; 0 < size; i++){
                DrawObject toRemovingDrawObject = list.get(i);
                if (toRemovingPoints.contains(toRemovingDrawObject)){
                    currentLayer.remove(toRemovingDrawObject);
                    drawObjectList.remove(toRemovingDrawObject);
                }
            }
        }
    }

    public void removeLastProcess() {
        if(drawObjectList.size() > 0){
            List<DrawObject> list = new ArrayList<>(drawObjectList);
            int size = drawObjectList.size();
            int processNumber = processCounter-1;
            for(int i = 0; i < size;i++){
                DrawObject drawObject = list.get(i);
                if(drawObject.getProcessNumber() == processNumber){
                    currentLayer.remove(drawObject);
                    drawObjectList.remove(drawObject);
                }
            }
            if(drawObjectList.size() < size){
                processCounter--;
            }
        }
    }

    public void setCurrentLayer(Layer currentLayer) {
        this.currentLayer = currentLayer;
    }

    public String getName() { return name; }

    public Layer getCurrentLayer() {
        return currentLayer;
    }

    public Point2D getCenterPoint(){
        return currentLayer.getSheet().getCenterPoint().getCenterPoint();
    }

    public ObservableList<Layer> getLayerList() {
        return layerList;
    }
}
