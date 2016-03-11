package View.Design.Controls;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.concurrent.Callable;

/**
 * Created by Guido on 29.01.2016.
 */
public class ButtonBase extends Button {

    private Border commonBorder;
    private Border hoverBorder;
    private Insets commonPadding;
    private Insets hoverPadding;
    private Background commonBackground;
    private Background disabledBackground;
    private Callable onAction;

    public ButtonBase() {
        BorderWidths borderWidth = new BorderWidths(1);
        commonBorder = null;
        hoverBorder = new Border(new BorderStroke
                (Color.BLACK, BorderStrokeStyle.SOLID,
                        new CornerRadii(0),borderWidth));
        commonPadding = new Insets(2,3,2,3);
        hoverPadding = new Insets(
                commonPadding.getTop()-borderWidth.getTop(),
                commonPadding.getRight()-borderWidth.getRight(),
                commonPadding.getBottom()-borderWidth.getBottom(),
                commonPadding.getLeft()-borderWidth.getLeft());
        commonBackground = new Background(new BackgroundFill
                (Color.TRANSPARENT,null,null));
        disabledBackground = new Background(new BackgroundFill
                (Color.grayRgb(80,0.20),null,commonPadding));

        this.addEventHandler
                (MouseEvent.MOUSE_ENTERED,mouseEvent-> onHover());
        this.addEventHandler
                (MouseEvent.MOUSE_EXITED,mouseEvent-> onHoverEnd());
        this.focusedProperty().addListener((
                (obs, oldValue, newValue) -> onFocusChanged(newValue)));
        this.disabledProperty().addListener(
                ((obs, oldValue, newValue) -> onDisabledChanged(newValue)));

        this.setBackground(commonBackground);
        this.setBorder(commonBorder);
        this.setPadding(commonPadding);
        this.setCursor(Cursor.HAND);
    }

    private void onHover(){
        this.setPadding(hoverPadding);
        this.setBorder(hoverBorder);

    }

    private void onHoverEnd(){
        if(!this.isFocused()){
            this.setPadding(commonPadding);
            this.setBorder(commonBorder);
        }
    }

    private void onFocusChanged(boolean newValue){
        if(newValue){
            onHover();
        }else {
            onHoverEnd();
        }
    }

    private void onDisabledChanged(Boolean newValue) {
        if(newValue){
            this.setBackground(disabledBackground);
        }else{
            this.setBackground(commonBackground);
        }
    }

    public void setOnAction(Callable onAction){
        this.onAction = onAction;
        this.addEventHandler(KeyEvent.KEY_RELEASED,KeyEvent->{
            if(KeyEvent.getCode().equals(KeyCode.ENTER)){
                try {
                    this.onAction.call();
                } catch (Exception e) {
                    System.out.println("ButtonBase OnAction:"+e.getMessage());
                }
            }
        });
        this.setOnAction(ActionEvent -> {
            try {
                this.onAction.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
