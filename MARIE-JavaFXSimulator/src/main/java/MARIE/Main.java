package MARIE;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        SimController controller = new SimController(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
