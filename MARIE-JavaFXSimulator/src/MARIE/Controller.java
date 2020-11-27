package MARIE;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends MARIEComputer implements Initializable {
    private final String[] INPUT_TYPES = {"HEX", "DEC", "ASCII"};

    @FXML private ComboBox inputType;
    @FXML private TableView<String> memory;
    @FXML private TableView<String> registers;
    @FXML private TextArea console;
    @FXML private TextField userInput;

    private Stage primaryStage;

    public Controller() {

    }

    public Controller(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MARIE Simulator");
        Parent root = FXMLLoader.load(getClass().getResource("layout.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inputType.setItems(FXCollections.observableArrayList(INPUT_TYPES));
        inputType.getSelectionModel().selectFirst();
        regTableInit();
        memTableInit();
    }

    @FXML
    public void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Machine Code File");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile != null) {

        }
    }

    @FXML
    public void runFile(ActionEvent actionEvent) {
        while(!clockTick() && getProgramCtr() <= 0xFFF) {} //run until we reach a halt or until the program counter runs

    }

    @FXML
    public void step(ActionEvent actionEvent) {


    }

    @Override
    int input() {
        //TODO implement
        return 0;
    }

    @Override
    void output(int output) {
        console.setText(console.getText() + output + '\n');
    }

    public void regTableInit() {
        TableColumn<String, String> step = new TableColumn<>("Previous Clock Cycle");
        TableColumn<String, String> prgCtr = new TableColumn<>("Program Counter");
        TableColumn<String, String> instrReg = new TableColumn<>("Instruction Register");
        TableColumn<String, String> ioReg = new TableColumn<>("Input/Output Register");
        TableColumn<String, String> accumulator = new TableColumn<>("Accumulator");
        TableColumn<String, String> stackPtr = new TableColumn<>("Stack Pointer");
        TableColumn<String, String> memBufReg = new TableColumn<>("Memory Buffer Register");
        TableColumn<String, String> memAddrReg = new TableColumn<>("Memory Address Register");


        registers.getColumns().addAll(step, prgCtr, instrReg, ioReg, accumulator, stackPtr, memBufReg, memAddrReg);
        registers.refresh();
    }

    public void memTableInit() {
        TableColumn<String, String>[] offsets = new TableColumn[16];

        for(int i = 0; i < offsets.length; i++) {
            offsets[i] = new TableColumn<>(Integer.toHexString(i).toUpperCase());
        }

        TableRow<String>[] memRow = new TableRow[256];

        for (int row = 0; row < 256; row++) {
            memRow[row] = new TableRow<>();
        }

        memory.getColumns().addAll(offsets);

        memory.refresh();
    }

    public void regTableUpdate(Boolean clearTable) {
        //register data

    }

    public void memTableUpdate() {

    }
}
