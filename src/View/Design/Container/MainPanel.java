package View.Design.Container;

import Model.Drawing.DrawSet;
import View.Tools.ColorPicker.ColorPicker;
import View.Tools.DrawPointControll.DrawPointCounterSlider;
import View.Tools.LayerControll.LayerControll;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by Guido on 17-Dec-15.
 */
public class MainPanel extends BorderPane{

    //TODO DrawPointWidthChanger, ColorPicker Improvement, Layer Tool
    //TODO Tool Abstraction

    private HBox topRibbon;
    private VBox rightRibbon;
    private HBox botRibbon;
    private VBox leftRibbon;
    private Background background;
    private DrawPointCounterSlider drawPointCounterSlider;
    private ColorPicker colorPicker;
    private LayerControll layerControll;

    public MainPanel(){
        this.background = new Background(new BackgroundFill
                (Color.grayRgb(178),new CornerRadii(0),new Insets(0)));

        this.topRibbon = new HToolbox(background,0);
        this.setTop(topRibbon);
        this.rightRibbon = new VToolbox(background,0);
        this.setRight(rightRibbon);
        this.botRibbon = new HToolbox(background,0);
        this.setBottom(botRibbon);
        this.leftRibbon = new VToolbox(background);
        this.leftRibbon.setMaxWidth(200);
        this.setLeft(leftRibbon);

        this.drawPointCounterSlider = new DrawPointCounterSlider(this.getNodeOrientation(),"DrawPoints");
        this.colorPicker = new ColorPicker();
        this.layerControll = new LayerControll();

        this.leftRibbon.getChildren().addAll
                (drawPointCounterSlider,colorPicker,layerControll);

    }

    public void addListener(DrawSet drawSet){
        drawPointCounterSlider.addListener(drawSet.getDrawPointCounterListener(),drawSet.getWithInnerPointsListener());
        colorPicker.addListener(drawSet.getSelectedColorListener());
    }

    public ChangeListener<Tab> getSelectionListener() {
        return layerControll.getTabChangeListener();
    }

}
