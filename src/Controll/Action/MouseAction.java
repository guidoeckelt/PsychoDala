package Controll.Action;

import Interface.Controll.MouseCommand;
import javafx.event.EventType;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.concurrent.Callable;

/**
 * Created by 67827 on 18.01.2016.
 */
public class MouseAction implements MouseCommand{

    private boolean shouldControlDown;
    private MouseButton mouseButton;
    private EventType<MouseEvent> mouseEventType;
    private Callable mouseEventAction;

    public MouseAction(MouseButton mouseButton, EventType<MouseEvent> mouseEventType, Callable toCall){
        this(mouseButton, mouseEventType, toCall, false);
    }

    public MouseAction(MouseButton mouseButton, EventType<MouseEvent> mouseEventType, Callable toCall, boolean shouldControlDown) {
        this.shouldControlDown = shouldControlDown;
        this.mouseButton = mouseButton;
        this.mouseEventType = mouseEventType;
        this.mouseEventAction = toCall;
    }

    @Override
    public boolean canExecute(MouseEvent mouseEvent) {
        if(mouseEventType == mouseEvent.getEventType()){
            if (shouldControlDown == mouseEvent.isControlDown()) {
                if (mouseButton == mouseEvent.getButton()) {
                    return  true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean execute() {
        boolean couldExecute = false;
        try {
            couldExecute = (boolean)mouseEventAction.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return couldExecute;
    }

}
