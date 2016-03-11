package Model.Drawing;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;

/**
 * Created by 67827 on 21.01.2016.
 */
public class DrawSet {

    private Point2D mousePoint;
    private Point2D centerPoint;

    private int drawPointsCounter = 1;
    private Color selectedColor = Color.BLACK;
    private boolean withInnerPoints = false;

    private EventHandler<MouseEvent> movementTracker;
    private ChangeListener<Number> drawPointCounterListener;
    private ChangeListener<Background> selectedColorListener;
    private ChangeListener<Boolean> withInnerPointsListener;

    public DrawSet() {

        movementTracker = MouseEvent ->{
            Point2D currentMousePosition = new Point2D(MouseEvent.getX(),MouseEvent.getY());
            if(mousePoint != currentMousePosition){
                mousePoint = currentMousePosition;
            }
        };
        drawPointCounterListener = (observable,oldValue,newValue)->{
            drawPointsCounter = newValue.intValue();
        };
        selectedColorListener = (observable,oldValue,newValue)->{
            selectedColor = (Color)newValue.getFills().get(0).getFill();
        };
        withInnerPointsListener = (observable, oldValue, newValue)->{
            withInnerPoints = newValue.booleanValue();
        };

    }

    public Point2D getMousePoint() {
        return mousePoint;
    }

    public EventHandler<MouseEvent> getMovementTracker() {
        return movementTracker;
    }

    public ChangeListener<Number> getDrawPointCounterListener() {
        return drawPointCounterListener;
    }

    public ChangeListener<Background> getSelectedColorListener() {
        return selectedColorListener;
    }

    public ChangeListener<Boolean> getWithInnerPointsListener() {
        return withInnerPointsListener;
    }

    public void setMousePoint(Point2D mousePoint) {
        this.mousePoint = mousePoint;
    }

    public Point2D getCenterPoint() {
        return centerPoint;
    }

    public void setCenterPoint(Point2D centerPoint) {
        this.centerPoint = centerPoint;
    }

    public int getDrawPointsCounter() {
        return drawPointsCounter;
    }

    public void setDrawPointsCounter(int drawPointsCounter) {
        this.drawPointsCounter = drawPointsCounter;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public boolean getWithInnerPoints() {
        return withInnerPoints;
    }

    public void setWithInnerPoints(boolean withInnerPoints) {
        this.withInnerPoints = withInnerPoints;
    }
}
