package Interface.Controll;


import javafx.scene.input.MouseEvent;

/**
 * Created by 67827 on 18.01.2016.
 */
public interface MouseCommand {

    boolean canExecute(MouseEvent keyEvent);
    boolean execute();

}
