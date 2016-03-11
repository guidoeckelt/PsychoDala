package Model.Drawing;

import Interface.Drawing.DrawObject;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Guido on 15-Dec-15.
 */
public class DrawPoint extends DrawObject {

    private Point2D centerPoint;
    private double radius = 2;
    private Color color = Color.BLACK;

    public DrawPoint( Point2D drawPoint, double radius, Color color){
        super(drawPoint);
        this.centerPoint = new Point2D(drawPoint.getX(), drawPoint.getY());
        if(radius >= this.radius){
            this.radius = radius;
        }
        if(color != null){
            this.color = color;
        }
        this.visual = new Circle(centerPoint.getX(), centerPoint.getY(), this.radius, this.color);
    }

    public void setCenterPoint(Point2D centerPoint) {
        this.centerPoint = centerPoint;
        this.visual.setLayoutX(centerPoint.getX());
        this.visual.setLayoutY(centerPoint.getY());
    }

    public Point2D getCenterPoint() {
        return centerPoint;
    }
}
