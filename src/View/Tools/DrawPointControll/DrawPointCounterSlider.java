package View.Tools.DrawPointControll;

import javafx.beans.value.ChangeListener;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Guido on 18-Dec-15.
 */
public class DrawPointCounterSlider extends HBox {

    private Label label;
    private Slider drawPointCounterSlider;
    private CheckBox withInnerPoints;

    public DrawPointCounterSlider(NodeOrientation orientation) {
        this.setAlignment(Pos.CENTER_LEFT);
        drawPointCounterSlider = new Slider();
        drawPointCounterSlider.setCursor(Cursor.HAND);
        drawPointCounterSlider.setShowTickLabels(true);
        drawPointCounterSlider.setShowTickMarks(true);
        drawPointCounterSlider.setMajorTickUnit(1);
        drawPointCounterSlider.setMinorTickCount(0);
        drawPointCounterSlider.setSnapToTicks(true);
        drawPointCounterSlider.setNodeOrientation(orientation);
        drawPointCounterSlider.setMin(1);
        drawPointCounterSlider.setMax(4);

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        label = new Label("");
        label.setLabelFor(drawPointCounterSlider);
        container.getChildren().add(label);
        withInnerPoints = new CheckBox();
        withInnerPoints.setText("Inner");
        withInnerPoints.setSelected(false);
        withInnerPoints.setCursor(Cursor.HAND);
        container.getChildren().add(withInnerPoints);
        this.getChildren().add(container);

        this.getChildren().add(drawPointCounterSlider);


    }

    public DrawPointCounterSlider(NodeOrientation orientation, String labelText){
        this(orientation);
        if(labelText != null){
            label.setText(labelText);
        }

    }

    public void addListener(ChangeListener<Number> drawPointCounterListener, ChangeListener<Boolean> withInnerPointsListener){
        this.drawPointCounterSlider.valueProperty().addListener(drawPointCounterListener);
        this.withInnerPoints.selectedProperty().addListener(withInnerPointsListener);
    }

}
