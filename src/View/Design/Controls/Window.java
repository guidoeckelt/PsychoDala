package View.Design.Controls;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import javafx.application.Platform;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Guido on 31.01.2016.
 */
public class Window extends AnchorPane {

    private Stage primaryStage;
    private double xOffset = 0;
    private double yOffset = 0;
    private double height = 25;
    private BorderPane headerContainer;
    private Label lblTitle;
    private HBox toolBarContainer;
    private View.Tools.ToolBar.ToolBar toolBar;
    private HBox btnContainer;
    private ButtonBase btnMinimize;
    private ButtonBase btnMaximize;
    private ButtonBase btnExit;

    public Window(Stage stage, Parent node) {
        super();
        this.primaryStage = stage;
        this.primaryStage.setMinWidth(800);
        this.primaryStage.setMinHeight(600);
        this.primaryStage.setMaximized(false);
        this.primaryStage.initStyle(StageStyle.UNDECORATED);
        this.setPadding(new Insets(0, 0, 0, 0));
        //header
        this.headerContainer = new BorderPane();
        this.headerContainer.setPadding(new Insets(0));
        this.headerContainer.setBackground(new Background(new BackgroundFill
                (Color.WHITE,null,null)));
        this.headerContainer.setMinHeight(height);
        this.headerContainer.setMaxHeight(height);
        //Title
        this.lblTitle = new Label("Title");
        this.lblTitle.setPadding(new Insets(1,10,1,10));
        this.lblTitle.setBackground(new Background(new BackgroundFill
                (Color.TRANSPARENT,null,null)));
        this.lblTitle.setMinHeight(height);
        this.lblTitle.setMaxHeight(height);
        this.headerContainer.setLeft(lblTitle);
        //WindowBTN
        this.btnContainer = new HBox();
        this.btnContainer.setMinHeight(height);
        this.btnContainer.setMaxHeight(height);
        this.btnContainer.setBackground(new Background(new BackgroundFill
                (Color.TRANSPARENT,null,null)));
        this.headerContainer.setRight(btnContainer);

        double btnWidth = 35;
        btnMaximize = buildButton("", btnWidth, this::maximize);
        JavaFXSceneBuilder builder = new JavaFXSceneBuilder();
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(0.0,0.0, 12.0,0.0, 12.0,12.0, 0.0,12.0);
        polygon.setFill(null);
        polygon.setStroke(Color.BLACK);
        primaryStage.maximizedProperty().addListener(((observable, oldValue, newValue) -> {
            if(newValue.booleanValue()){
                Polygon minPoly = new Polygon();
                minPoly.getPoints().addAll(0.0,0.0, 12.0,0.0, 12.0,12.0, 0.0,12.0);
                minPoly.setFill(null);
                minPoly.setStroke(Color.BLACK);
                btnMaximize.setGraphic(new Label("K"));
            }else{
                Polygon maxPoly = new Polygon();
                maxPoly.getPoints().addAll(0.0,0.0, 12.0,0.0, 12.0,12.0, 0.0,12.0);
                maxPoly.setFill(null);
                maxPoly.setStroke(Color.BLACK);
                btnMaximize.setGraphic(maxPoly);
            }
        }));
        btnMaximize.setGraphic(polygon);
        btnExit = buildButton("X", btnWidth, this::exit);
        btnMinimize = buildButton("_", btnWidth, this::minimize);
        this.btnContainer.getChildren().addAll(btnMinimize,btnMaximize,btnExit);
        //HeaderContent
        this.toolBarContainer = new HBox();
        this.toolBarContainer.setAlignment(Pos.CENTER_LEFT);
        this.toolBarContainer.setBackground(new Background(new BackgroundFill
                (Color.TRANSPARENT,null, null)));
        this.toolBarContainer.prefWidth(headerContainer.getWidth()-btnContainer.getWidth());
        this.toolBar = new View.Tools.ToolBar.ToolBar();
        this.toolBarContainer.getChildren().add(toolBar);
        this.headerContainer.setCenter(toolBarContainer);
        AnchorPane.setRightAnchor(headerContainer, 0.0);
        AnchorPane.setTopAnchor(headerContainer, 0.0);
        AnchorPane.setTopAnchor(node, height);
        AnchorPane.setRightAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setBottomAnchor(node, 0.0);

        this.getChildren().addAll(node,headerContainer);
        this.primaryStage.widthProperty().addListener((o,old,newV)->{
            headerContainer.setMinWidth(newV.doubleValue());
            headerContainer.setMaxWidth(newV.doubleValue());
            double width =
                    headerContainer.getMinWidth()-btnContainer.widthProperty().get()
                        -lblTitle.widthProperty().get();
            toolBarContainer.setPrefWidth(width);
        });
        this.addMovingListener(lblTitle);
        this.addMovingListener(toolBarContainer);
    }

    public void setTitle(String title){
        this.lblTitle.setText(title);
    }

    public void addToolButtons(List<Control> toolButtonList){
        this.toolBar.getItems().addAll(toolButtonList);
    }

    public void updateSize(Dimension2D size){
        this.primaryStage.setWidth(size.getWidth());
        this.primaryStage.setHeight(size.getHeight());
    }

    public boolean minimize(){
        this.primaryStage.setIconified(!primaryStage.isIconified());
        return true;
    }

    public boolean maximize(){
        this.primaryStage.setMaximized(!primaryStage.isMaximized());
        return true;
    }

    public boolean exit(){
        Platform.exit();
        return true;
    }

    private void addMovingListener(Node node){
        node.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        node.setOnMouseDragged((MouseEvent event) -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
    }

    public void show() {
        javafx.scene.Scene scene = new javafx.scene.Scene(this);
        scene.setFill(null);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }

    public void centerOnScreen() {
        this.primaryStage.centerOnScreen();
    }

    private ButtonBase buildButton(String text,double width, Callable onAction) {
        ButtonBase buttonBase = new ButtonBase();
        buttonBase.setFocusTraversable(false);
        buttonBase.setText(text);
        buttonBase.setOnAction(onAction);
        buttonBase.setMinSize(width,height);
        buttonBase.setMaxSize(width,height);
        return buttonBase;
    }
}
