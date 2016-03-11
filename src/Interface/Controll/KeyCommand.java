package Interface.Controll;


import javafx.scene.input.KeyEvent;

/**
 * Created by 67827 on 18.01.2016.
 */
public interface KeyCommand {
    boolean canExecute(KeyEvent keyEvent);
    boolean execute();

}
