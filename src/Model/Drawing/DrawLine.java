package Model.Drawing;

import Interface.Drawing.DrawObject;
import javafx.geometry.Point2D;

import java.util.LinkedList;

/**
 * Created by Guido on 16-Dec-15.
 */
public class DrawLine extends DrawObject {
    // TODO implement this class to Draw Controller
    LinkedList<DrawPoint> linePoints = new LinkedList<>();

    public DrawLine(int processNumber){
        super(Point2D.ZERO);
        //super(processNumber);
    }

    public void setStartPoint(DrawPoint startPoint){
        linePoints.addFirst(startPoint);
    }

}
