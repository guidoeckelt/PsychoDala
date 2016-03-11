package View.Tools.ColorPicker;

import View.Design.Controls.ButtonBase;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Guido on 26.01.2016.
 */
public class ColorChooser extends Stage {
    private Pane base;
    private ButtonBase btnSave;
    private List<ColorPicker> colorPickerList = new ArrayList<>();

    public ColorChooser(List<Color> currentColors) {

        double pickerWidth = 40;
        double pickerHeight = 24;
        double X = 0;
        double Y = 0;

        this.setMinWidth(176);
        this.setMinHeight(112);
        this.setMaxWidth(176);
        this.setMaxHeight(112);
        this.setIconified(false);

        base = new Pane();
        base.setMinSize(170, 85);
        base.setMaxSize(170, 85);
        base.autosize();
        int size = currentColors.size();
        for(int i = 0; i < size;i++){
            Color color = currentColors.get(i);
            javafx.scene.control.ColorPicker colorPicker = new javafx.scene.control.ColorPicker(color);
            colorPicker.setPrefSize(pickerWidth, pickerHeight);
            colorPicker.setLayoutX(X);
            colorPicker.setLayoutY(Y);
            if(i < (size/2)-1){
                X += pickerWidth;
            }else if(i == (size/2)-1){
                X = 0;
                Y += pickerHeight;
            }else if(i > (size/2)-1){
                X += pickerWidth;
            }
            base.getChildren().add(colorPicker);
            colorPickerList.add(colorPicker);
        }

        btnSave = new ButtonBase();
        btnSave.setText("save");
        btnSave.setMinSize(40,24);
        btnSave.setLayoutX(60);
        btnSave.setLayoutY(48);
        base.getChildren().add(btnSave);
        this.setScene(new Scene(base));
    }

    public List<Color> getSelectedColors() {
        List<Color> returnList = new ArrayList<>();
        colorPickerList.forEach(colorPicker -> {
            returnList.add(colorPicker.getValue());
        });
        return returnList;
    }

    public void addSaveHandler(Callable saveAction){
        btnSave.setOnAction(saveAction);
    }

    public void setOwner(Window parentWindow){
        this.initOwner(parentWindow);
        this.initModality(Modality.WINDOW_MODAL);
    }

}
