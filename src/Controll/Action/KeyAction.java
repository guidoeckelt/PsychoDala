package Controll.Action;

import Interface.Controll.KeyCommand;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.concurrent.Callable;

/**
 * Created by Guido on 17-Dec-15.
 */
public class KeyAction implements KeyCommand{

    private KeyCode key;
    private boolean shouldControlDown;
    private EventType<KeyEvent> keyEventType;
    private Callable keyEventAction;

    public KeyAction(EventType<KeyEvent> eventType, KeyCode keyCode, Callable toCall){
        this(eventType, keyCode, toCall, true);
    }

    public KeyAction(EventType<KeyEvent> eventType, KeyCode keyCode, Callable toCall, boolean shouldControlDown){
        this.keyEventAction = toCall;
        this.keyEventType = eventType;
        this.key = keyCode;
        this.shouldControlDown = shouldControlDown;
    }

    public boolean canExecute(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if(keyEventType == keyEvent.getEventType()){
            if (shouldControlDown == keyEvent.isControlDown()) {
                if (key == keyCode) {
                    return  true;
                }
            }
        }
        return false;
    }

    public boolean execute(){
        boolean couldExecute = false;
        try {
            couldExecute = (boolean)keyEventAction.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return couldExecute;
    }

    public KeyCode getKey(){ return this.key; }

    public void setShouldControlDown(boolean newValue){
        this.shouldControlDown = newValue;
    }

    public EventType<KeyEvent> getKeyEventType(){ return this.keyEventType; }

}
