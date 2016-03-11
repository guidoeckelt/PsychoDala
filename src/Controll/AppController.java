package Controll;

import Controll.Action.KeyAction;
import Controll.Action.MouseAction;
import Interface.Controll.KeyCommand;
import Interface.Controll.MouseCommand;
import Interface.Drawing.DrawObject;
import Model.Drawing.DrawAllocator;
import Model.Drawing.DrawSet;
import Model.Sheet.Scene;
import View.Design.Container.MainPanel;
import View.Design.Controls.Window;
import View.Tools.ToolBar.ToolButton;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Dimension2D;
import javafx.geometry.Side;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 67827 on 19.01.2016.
 */
public class AppController {

    private String appName;
    private Window window;

    private MainPanel mainPanel;
    private TabPane tabBrowser;
    private DrawAllocator drawAllocator;
    private DrawSet currentDrawSet = new DrawSet();

    private List<KeyCommand> keyCommandList = new ArrayList<>();
    private List<MouseCommand> mouseCommandList = new ArrayList<>();
    private MouseAction leftButton;
    private MouseAction rightButton;

    public AppController(Stage stage) {
        this.appName = "PsychoDala";
        this.mainPanel = new MainPanel();
        this.tabBrowser = new TabPane();
        this.window = new Window(stage, mainPanel);

        this.loadShortcuts();
        this.loadMouseActions();
        this.loadTools();
        this.loadToolButtons();
        this.mainPanel.autosize();
        this.mainPanel.addListener(currentDrawSet);
        //TabBrowser
        this.tabBrowser.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        this.tabBrowser.getSelectionModel().selectedItemProperty()
                .addListener(mainPanel.getSelectionListener());
        this.tabBrowser.addEventHandler(Tab.CLOSED_EVENT, closeRequest->{
            System.out.println("all "+closeRequest.getTarget().getClass());
            if(this.tabBrowser.getTabs().size() < 12){
                this.tabBrowser.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
                System.out.println("dooo "+closeRequest.getTarget().getClass());
            }
        });
        this.tabBrowser.setSnapToPixel(true);
        this.tabBrowser.setSide(Side.TOP);
        this.mainPanel.setCenter(tabBrowser);
        //drawAllocator
        this.drawAllocator = new DrawAllocator();
    }

    public boolean addNewTab(){
        Scene scene = new Scene("Draw" + (tabBrowser.getTabs().size()+1));
        scene.addEventHandler(MouseEvent.ANY, currentDrawSet.getMovementTracker());
        scene.setMinSize(400,400);
        scene.addLayer();
        scene.autosize();
        Tab newTab = new Tab(scene.getName());
        newTab.setContent(scene);
        boolean couldAdd = tabBrowser.getTabs().add(newTab);
        tabBrowser.getSelectionModel().select(newTab);
        if(!couldAdd){
            System.out.println("could not add new Tab");
        }
        if(this.tabBrowser.getTabs().size() >=12){
            //this.tabBrowser.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        }
        return couldAdd;
    }

    public boolean saveAsImage(){

        Tab selectedTab = tabBrowser.getSelectionModel().getSelectedItem();
        Scene toSavingScene = (Scene)selectedTab.getContent();

        WritableImage toSavingImage = toSavingScene.snapshot(new SnapshotParameters(), null);
        FileChooser fileChooser = new FileChooser();
//        fileChooser.setInitialDirectory(new File("C:\\PsychoDala\\"));
        fileChooser.setInitialFileName(toSavingScene.getName()+".png");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG Image",".png"),
                new FileChooser.ExtensionFilter("All Files", "*")
        );
        fileChooser.setTitle("choose filepath to save as image");
        File toSavingFileChosen = fileChooser.showSaveDialog(toSavingScene.getScene().getWindow());
        FileChooser.ExtensionFilter selectedExtensionFilter = fileChooser.getSelectedExtensionFilter();
        String extension;
        if(selectedExtensionFilter != null){
            extension = fileChooser.getSelectedExtensionFilter().getExtensions().get(0).substring(1);
        }else{
            extension = "";
        }
        if(toSavingFileChosen != null) {
            try {
                if (!toSavingFileChosen.createNewFile()) {
                    System.out.println("File existed");
                }
                if (toSavingFileChosen.canExecute()) {
                    if (ImageIO.write(SwingFXUtils.fromFXImage(toSavingImage, null), extension, toSavingFileChosen)) {
                        System.out.println("saved");
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean rename(){
        System.out.println("rename Scene");
        return true;
    }

    public boolean undo(){
        Tab selectedTab = tabBrowser.getSelectionModel().getSelectedItem();
        Scene scene = (Scene)selectedTab.getContent();
        scene.removeLastProcess();
        return true;
    }

    public boolean redo(){
        System.out.println("redo last undoAction");
        return true;
    }

    public boolean createDrawPoint(){
        Scene scene = (Scene) tabBrowser.getSelectionModel().getSelectedItem().getContent();
        currentDrawSet.setCenterPoint(scene.getCenterPoint());
        List<DrawObject> toDrawObjects = drawAllocator.drawPointAllocation(currentDrawSet);
        scene.add(toDrawObjects);
        return true;
    }

    public boolean createDrawLine(){
        Scene scene = (Scene) tabBrowser.getSelectionModel().getSelectedItem().getContent();
        currentDrawSet.setCenterPoint(scene.getCenterPoint());
        List<DrawObject> toDrawObjects = drawAllocator.drawPointAllocation(currentDrawSet);
        scene.add(toDrawObjects);
        return true;
    }

    private void keyActionHandle(KeyEvent keyEvent) {
        if (keyCommandList.size() > 0) {
            for(KeyCommand shortcut : keyCommandList){
                if(shortcut.canExecute(keyEvent)){
                    boolean couldExecute = shortcut.execute();
                    if(!couldExecute){
                        System.out.println("could not Execute");
                    }
                }
            }
        }
    }

    public void mouseActionHandle(MouseEvent mouseEvent){
        if (mouseCommandList.size() > 0) {
            mouseCommandList.forEach(mouseCommand->{
                if(mouseCommand.canExecute(mouseEvent)){
                    boolean couldExecute = true;
                    try{
                        couldExecute = mouseCommand.execute();
                    }catch(IllegalStateException e){

                        System.out.println("could Execute:"+couldExecute+" "+e.getLocalizedMessage()+";"+e.getCause());

                    }catch(NullPointerException nulLEx){

                        System.out.println("could Execute:"+couldExecute+" "+nulLEx.getLocalizedMessage()+";"+nulLEx.getCause());

                    }
                    /*if(!couldExecute){
                        System.out.println("could not Execute");
                    }*/
                }
            });
        }
    }

    private void loadTools() {

    }

    private void loadToolButtons(){
        Font menuFont = Font.font(null, FontWeight.BOLD, 16);
        Font itemFont = Font.font(null, FontWeight.SEMI_BOLD, 18);
        List<Control> toolButtonList = new ArrayList<>();
        //FileMenu
        ToolButton tlbNewTab = new ToolButton();
        tlbNewTab.setOnAction(this::addNewTab);
        Tooltip tooltip = new Tooltip();
        tooltip.setText("New Tab (STRG+T)");
        tooltip.setContentDisplay(ContentDisplay.TEXT_ONLY);
        tooltip.setGraphicTextGap(0);
        tooltip.setTextAlignment(TextAlignment.CENTER);
        tlbNewTab.setTooltip(tooltip);
        Image image = new Image("file:Icon/File/newTab.png");
        tlbNewTab.setGraphic(new ImageView(image));
        toolButtonList.add(tlbNewTab);

        ToolButton tlbSaveAsImage = new ToolButton();
        tlbSaveAsImage.setOnAction(this::saveAsImage);
        tooltip = new Tooltip();
        tooltip.setText("Save as Image (STRG+S)");
        tlbSaveAsImage.setTooltip(tooltip);
        tooltip.setContentDisplay(ContentDisplay.TEXT_ONLY);
        tooltip.setGraphicTextGap(0);
        tooltip.setTextAlignment(TextAlignment.CENTER);
        image = new Image("file:Icon/File/save.png");
        tlbSaveAsImage.setGraphic(new ImageView(image));
        toolButtonList.add(tlbSaveAsImage);

        toolButtonList.add(new Separator());
        //EditMenu

        ToolButton tlbRename = new ToolButton();
        tlbRename.setOnAction(this::rename);
        tlbRename.setDisable(true);
        tooltip = new Tooltip();
        tooltip.setText("Rename (STRG+R)");
        tooltip.setContentDisplay(ContentDisplay.TEXT_ONLY);
        tooltip.setGraphicTextGap(0);
        tooltip.setTextAlignment(TextAlignment.CENTER);
        tlbRename.setTooltip(tooltip);
        image = new Image("file:Icon/Edit/rename.png");
        tlbRename.setGraphic(new ImageView(image));
        toolButtonList.add(tlbRename);

        ToolButton tlbUndo = new ToolButton();
        tlbUndo.setOnAction(this::undo);
        tooltip = new Tooltip();
        tooltip.setText("Undo (STRG+Z)");
        tooltip.setContentDisplay(ContentDisplay.TEXT_ONLY);
        tooltip.setGraphicTextGap(0);
        tooltip.setTextAlignment(TextAlignment.CENTER);
        tlbUndo.setTooltip(tooltip);
        image = new Image("file:Icon/Edit/undo.png");
        tlbUndo.setGraphic(new ImageView(image));
        toolButtonList.add(tlbUndo);

        ToolButton tlbRedo = new ToolButton();
        tlbRedo.setOnAction(this::redo);
        tlbRedo.setDisable(true);
        tooltip = new Tooltip();
        tooltip.setText("Redo (STRG+Y)");
        tooltip.setContentDisplay(ContentDisplay.TEXT_ONLY);
        tooltip.setGraphicTextGap(0);
        tooltip.setTextAlignment(TextAlignment.CENTER);
        tlbRedo.setTooltip(tooltip);
        image = new Image("file:Icon/Edit/redo.png");
        tlbRedo.setGraphic(new ImageView(image));
        toolButtonList.add(tlbRedo);

        window.addToolButtons(toolButtonList);
    }

    private void loadShortcuts(){

        //Add ShortcutHandler
        EventHandler<KeyEvent> keyActionHandler = KeyEvent -> {
            keyActionHandle(KeyEvent);
        };
        this.mainPanel.addEventHandler(KeyEvent.KEY_RELEASED, keyActionHandler);
        this.mainPanel.addEventHandler(KeyEvent.KEY_PRESSED, keyActionHandler);


        KeyCommand newTabShortcut = new KeyAction(KeyEvent.KEY_RELEASED, KeyCode.T, this::addNewTab);
        keyCommandList.add(newTabShortcut);
        KeyCommand saveShortcut = new KeyAction(KeyEvent.KEY_RELEASED,KeyCode.S, this::saveAsImage) ;
        keyCommandList.add(saveShortcut);
        KeyCommand undoShortcut = new KeyAction(KeyEvent.KEY_PRESSED,KeyCode.Z, this::undo);
        keyCommandList.add(undoShortcut);
        KeyCommand redoShortcut = new KeyAction(KeyEvent.KEY_PRESSED, KeyCode.Y, this::redo);
        keyCommandList.add(redoShortcut);
        KeyCommand renameShortcut = new KeyAction(KeyEvent.KEY_RELEASED, KeyCode.R, this::rename);
        keyCommandList.add(renameShortcut);

    }

    private void loadMouseActions(){
        //Add MouseHandler
        EventHandler<MouseEvent> mouseActionHandler = MouseEvent ->{
            mouseActionHandle(MouseEvent);
        };
        this.tabBrowser.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseActionHandler);
        this.tabBrowser.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseActionHandler);
        this.tabBrowser.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseActionHandler);
        leftButton = new MouseAction
                (MouseButton.PRIMARY, MouseEvent.MOUSE_RELEASED, this::createDrawPoint);
        rightButton = new MouseAction
                (MouseButton.SECONDARY, MouseEvent.MOUSE_DRAGGED, this::createDrawLine);
        mouseCommandList.add(leftButton);
        mouseCommandList.add(rightButton);
    }

    public void start(){
        //Window Settings
        this.window.setTitle(appName);
        this.window.show();
        this.addNewTab();
        this.window.updateSize(new Dimension2D(800,600));
        this.window.centerOnScreen();
    }

}
