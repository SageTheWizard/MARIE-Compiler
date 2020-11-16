package MARIE;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller extends MARIEComputer{
    @FXML private TableView memory;
    @FXML private TableView registers;

    public Controller() {

    }
    public Controller(Stage primaryStage) throws IOException {
        primaryStage.setTitle("MARIE Simulator");
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @FXML
    public void openFile(ActionEvent actionEvent) {
    }

    @FXML
    public void runFile(ActionEvent actionEvent) {
        while(!clockTick() && getProgramCtr() <= 0xFFF) { //run until we reach a halt or until the program counter runs

        }
    }

    @FXML
    public void step(ActionEvent actionEvent) {


    }

    @Override
    int input() {
        return 0;
    }

    @Override
    void output(int output) {

    }

    public void dummyData() {
        //register data
        TableColumn step = new TableColumn("Previous Data");
        TableColumn reg1 = new TableColumn("Register 1");
        TableColumn reg2 = new TableColumn("Register 2");

        registers.getColumns().addAll(step, reg1, reg2);
        registers.refresh();
    }
}
