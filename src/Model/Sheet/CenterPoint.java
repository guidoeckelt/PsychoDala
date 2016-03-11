package Model.Sheet;

import Model.Drawing.DrawPoint;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

/**
 * Created by 67827 on 18.01.2016.
 */
public class CenterPoint extends DrawPoint{

    public CenterPoint(Point2D drawPoint) {
        super(drawPoint, 0, Color.GREENYELLOW.darker());
        visual.setStroke(Color.BLACK);
        visual.setManaged(false);
    }
    public void updatePosition(Point2D newPoint){
        this.setCenterPoint(newPoint);
        this.visual.setLayoutX(newPoint.getX());
        this.visual.setLayoutY(newPoint.getY());
    }

}
