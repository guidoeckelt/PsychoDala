package View.Tools.ColorPicker;

import View.Design.Controls.ButtonBase;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guido on 18-Dec-15.
 */
public class ColorPicker extends HBox{

    private Label lblSelectedColor;
    private List<Color> colorList = new ArrayList<>();
    private List<ButtonBase> lblList = new ArrayList<>();
    private GridPane colorGrid;
    private ButtonBase btnColorPicker;
    private ColorChooser colorChooser;

    public ColorPicker(){
        this.setAlignment(Pos.CENTER_LEFT);

        Label label = new Label("  ");
        this.getChildren().add(label);

        double size = 25;
        this.lblSelectedColor = new Label();
        lblSelectedColor.setMinSize(size,size);
        lblSelectedColor.setMaxSize(size,size);

        Border blackBorder = new Border
                (new BorderStroke(Color.BLACK,null,null,new BorderWidths(5)));

        VBox container = new VBox();
        container.setAlignment(Pos.CENTER);
        container.setBorder(blackBorder);
        container.getChildren().add(lblSelectedColor);
        label = new Label("Picked");
        label.setLabelFor(lblSelectedColor);
        container.getChildren().add(label);
        this.getChildren().add(container);

        label = new Label("  ");
        this.getChildren().add(label);

        colorList.add(Color.BLACK);
        colorList.add(Color.BLUE);
        colorList.add(Color.valueOf("6495ED"));
        colorList.add(Color.BURLYWOOD);
        colorList.add(Color.RED);
        colorList.add(Color.valueOf("00FA9A"));
        colorList.add(Color.valueOf("00CD00"));
        colorList.add(Color.GREEN);

        fillColorGrid();
        Background background = new Background(new BackgroundFill
                (colorList.get(0) ,new CornerRadii(0.5,true),new Insets(0)));
        lblSelectedColor.setBackground(background);

    }

    public void fillColorGrid(){
        if(colorGrid != null){
            this.getChildren().remove(colorGrid);
            this.getChildren().remove(btnColorPicker);
            colorGrid.getChildren().clear();
        }

        Border grayBorder = new Border
            (new BorderStroke(Color.GRAY,null,null,new BorderWidths(5)));
        colorGrid = new GridPane();
        colorGrid.setBorder(grayBorder);
        ButtonBase label;
        Background background;
        CornerRadii roundRadii = new CornerRadii(0.5,true);
        int counter = 0;
        int columns = 4;
        int rows = 2;
        for(int i = 0; i  < rows; i++){
            TilePane row = new TilePane();
            row.setPrefColumns(columns);
            row.setHgap(5);
            row.setPrefTileHeight(20);
            row.setPrefTileWidth(20);
            for(int ii = 0; ii  < columns; ii++){
                label = new ButtonBase();
                background = new Background(new BackgroundFill
                        (colorList.get(counter), roundRadii,new Insets(0)));
                label.setBackground(background);
                label.setCursor(Cursor.HAND);
                label.setMinSize(19,19);

                ContextMenu cMenu = new ContextMenu();
                MenuItem cMenuItem = new MenuItem("Change Color");
                cMenuItem.setOnAction(ActionEvent ->{

                });
                cMenu.getItems().add(cMenuItem);

                label.addEventHandler(MouseEvent.MOUSE_RELEASED
                        ,mouseEvent ->{
                            ButtonBase clickedLbl = (ButtonBase)mouseEvent.getSource();
                            BackgroundFill backgroundFill = clickedLbl.getBackground().getFills().get(0);
                            Paint newColor = backgroundFill.getFill();
                            setSelectColor(newColor);
                        });
                label.addEventHandler(KeyEvent.KEY_RELEASED,keyEvent->{
                    if(keyEvent.getCode() == KeyCode.ENTER){

                        ButtonBase clickedLbl = (ButtonBase)keyEvent.getSource();
                        BackgroundFill backgroundFill = clickedLbl.getBackground().getFills().get(0);
                        Paint newColor = backgroundFill.getFill();
                        setSelectColor(newColor);
                    }
                });
                lblList.add(label);
                row.getChildren().add(label);
                if(counter <= colorList.size())
                    counter++;
            }
            colorGrid.add(row, 0, i);
        }
        this.getChildren().add(colorGrid);
        btnColorPicker = new ButtonBase();
        Image image = new Image("file:Icon/Tools/colorChooser.png");
        btnColorPicker.setGraphic(new ImageView(image));
        btnColorPicker.setOnAction(this::openColorChooser);
        this.getChildren().add(btnColorPicker);
    }

    public boolean openColorChooser(){

        double X = this.getScene().getWindow().getX() + btnColorPicker.getBoundsInParent().getMinX();
        double Y = this.getScene().getWindow().getY() + btnColorPicker.getBoundsInParent().getMinY();

        colorChooser = new ColorChooser(colorList);
        colorChooser.setX(X);
        colorChooser.setY(Y);
        colorChooser.setOwner(this.getScene().getWindow());
        colorChooser.initStyle(StageStyle.UTILITY);
        colorChooser.addSaveHandler(this::changeColor);
        colorChooser.show();
        return true;

    }

    public boolean changeColor(){
        colorList = colorChooser.getSelectedColors();
        fillColorGrid();
        colorChooser.close();
        return true;
    }

    public void addListener(ChangeListener<Background> selectedColorListener){
        this.lblSelectedColor.backgroundProperty().addListener(selectedColorListener);
    }

    private void setSelectColor(Paint selectColor){
        BackgroundFill backgroundFill = lblSelectedColor.getBackground().getFills().get(0);
        Paint oldColor = backgroundFill.getFill();
        if (oldColor != selectColor) {
            CornerRadii oldRadii = backgroundFill.getRadii();
            Insets oldInsets = backgroundFill.getInsets();
            Background newBackground = new Background(new BackgroundFill
                    (selectColor,oldRadii,oldInsets));
            lblSelectedColor.setBackground(newBackground);
        }
    }

}
