package MARIE;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setTitle("MARIE ASM SIMULATOR");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMaximized(true);
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.getScene().getStylesheets().add(String.valueOf(getClass().getResource("darkTheme.css")));
        Controller controller = new Controller(primaryStage);
        fxmlLoader.setController(controller);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
