package MARIE;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller extends MARIEComputer implements Initializable {
    private final String[] INPUT_TYPES = {"HEX", "DEC", "ASCII"};
    @FXML private Menu openMenuItem, reloadMenuItem, runMenuItem, stepMenuItem;

    @FXML private ComboBox inputType;
    @FXML private TableView<String> memory;
    @FXML private TableView<String> registers;
    @FXML private TextArea console;
    @FXML private TextField userInput;

    private File currentlyOpenFile;

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

        Label openMenuLabel = new Label("Open");
        openMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                openFile();
            }
        });

        Label reloadMenuLabel = new Label("Reload");
        reloadMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                reload();
            }
        });

        Label runMenuLabel = new Label("Run");
        runMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                runFile();
            }
        });

        Label stepMenuLabel = new Label("Step");
        stepMenuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                step();
            }
        });

        openMenuItem.setGraphic(openMenuLabel);
        reloadMenuItem.setGraphic(reloadMenuLabel);
        runMenuItem.setGraphic(runMenuLabel);
        stepMenuItem.setGraphic(stepMenuLabel);


        regTableInit();
        memTableInit();
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Machine Code File");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile != null) {
            openFile(selectedFile);
        }
    }

    public void openFile(File toOpen) {
        try {
            int offset = 0;
            Scanner input = new Scanner(toOpen);
            //read first line to get the ORG
            offset = Integer.parseInt(input.nextLine().split(" ")[1], 16);
            setProgramCtr(offset); //set the program counter to the ORG offset
            currentlyOpenFile = toOpen;

            int i = 0;
            String hexCodeLine;
            while(input.hasNextLine()) {
                getMainMemory()[i + offset] = Integer.parseInt(input.nextLine(), 16);
                i++;
            }
        } catch (IOException e) {
            //TODO implement
            e.printStackTrace();
        }

    }

    public void reload() {
        openFile(currentlyOpenFile);
    }

    @FXML
    public void runFile() {
        while(!clockTick()) {} //run until we reach a halt or until the program counter runs out

    }

    @FXML
    public void step() {


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
