package Model.Sheet;

import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by 67827 on 18.01.2016.
 */
public class Sheet extends Pane {
    //TODO scalable Sheet class

    private SheetController sheetController;
    private CenterPoint centerPoint;

    public Sheet(){
        Background sheetBackground = new Background
                (new BackgroundFill(Color.TRANSPARENT,new CornerRadii(0),new Insets(0)));
        this.setBackground(sheetBackground);
        centerPoint = new CenterPoint(new Point2D(0,0));//(new Point2D(200,200));
        this.getChildren().add(centerPoint.getVisual());
        this.setLayoutX(0);
        this.setLayoutY(0);
        this.setMinSize(400,400);
    }

    public void updateSize(Dimension2D size){
        this.setWidth(size.getWidth());
        this.setHeight(size.getHeight());
        centerPoint.getVisual().setLayoutX(size.getWidth()/2);
        centerPoint.getVisual().setLayoutY(size.getHeight()/2);
    }

    public void setSheetController(SheetController sheetController) {
        this.sheetController = sheetController;
    }

    public CenterPoint getCenterPoint() {
        return centerPoint;
    }

}
