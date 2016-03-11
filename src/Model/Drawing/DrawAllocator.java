package Model.Drawing;

import Interface.Drawing.DrawObject;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guido on 13-Dec-15.
 */
public class DrawAllocator {

    public List<DrawObject> drawPointAllocation(DrawSet currentDrawSet){
        List<DrawObject> toDrawingObjects = new ArrayList<>();
        Point2D toDrawingPoint = Point2D.ZERO;
        DrawObject toDrawingObject;
        Point2D centerPoint = currentDrawSet.getCenterPoint();
        Point2D vektor2DCenterToMouse = currentDrawSet.getMousePoint().subtract(centerPoint);
        //TODO drawPointAllocation improvement Angle calculation
        for(int i = 0; i < currentDrawSet.getDrawPointsCounter();i++){
            int switchCounter = (i%4)+1;
            switch(switchCounter){
                case 1:
                    toDrawingPoint = centerPoint.add
                            (vektor2DCenterToMouse.getX()*(1)
                                    , vektor2DCenterToMouse.getY()*(1));
                    break;
                case 2:
                    toDrawingPoint = centerPoint.add
                            (vektor2DCenterToMouse.getX()*(-1)
                                    , vektor2DCenterToMouse.getY()*(-1));
                    break;
                case 3:
                    toDrawingPoint = centerPoint.add
                            (vektor2DCenterToMouse.getX()*(-1)
                                    , vektor2DCenterToMouse.getY()*(1));
                    break;
                case 4:
                    toDrawingPoint = centerPoint.add
                            (vektor2DCenterToMouse.getX()*(1)
                                    , vektor2DCenterToMouse.getY()*(-1));
                    break;
                default:
                    System.out.println("default: "+switchCounter);
            }

            toDrawingObject = new DrawPoint(toDrawingPoint , 0, currentDrawSet.getSelectedColor());
            toDrawingObjects.add(toDrawingObject);
            if(currentDrawSet.getWithInnerPoints()){
                Point2D vektor = toDrawingPoint.subtract(centerPoint);
                Point2D innerPoint = centerPoint.subtract(vektor.getX()/2,vektor.getY()/2);
                DrawObject innerObject = new DrawPoint(innerPoint,0,currentDrawSet.getSelectedColor());
                toDrawingObjects.add(innerObject);
            }
        }
        return toDrawingObjects;
    }

}
