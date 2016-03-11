package Interface.Drawing;

import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;

/**
 * Created by Guido on 16-Dec-15.
 */
public class DrawObject {
    //TODO Some more Things in here maybe
    protected Shape visual;
    protected int processNumber;

    public DrawObject(Point2D drawPoint){

    }

    public int getProcessNumber() {
        return processNumber;
    }

    public Shape getVisual() {
        return visual;
    }

    public void setProcessNumber(int processNumber) {
        this.processNumber = processNumber;
    }

}
