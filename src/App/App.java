package App;
/**
 * Created by Guido on 13-Dec-15.
 */

import Controll.AppController;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        AppController appController = new AppController(primaryStage);
        appController.start();
    }

}
