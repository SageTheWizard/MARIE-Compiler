package MARIE;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller extends MARIEComputer implements Initializable {
    private final String[] INPUT_TYPES = {"HEX", "DEC", "ASCII"};
    @FXML private Menu openMenuItem, reloadMenuItem, runMenuItem, stepMenuItem;

    @FXML private ComboBox inputType;
    @FXML private TableView<MemoryTableRow> memory;
    @FXML private TableView<ClockStep> registers;
    @FXML private TextArea console;
    @FXML private TextField userInput;

    private File currentlyOpenFile;

    private Stage primaryStage;

    MemoryTableRow[] memRow = new MemoryTableRow[256];

    ArrayList<ClockStep> clockSteps = new ArrayList<>();

    private boolean halted;

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
            while(input.hasNextLine()) {
                getMainMemory()[i + offset] = Integer.parseInt(input.nextLine(), 16);
                i++;
            }

            memTableUpdate();
            regTableUpdate(true);
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
        while(!(halted = clockTick())) {
            regTableUpdate(false);
            memTableUpdate();
        } //run until we reach a halt or until the program counter runs out

    }

    @FXML
    public void step() {
        if(!halted) {
            halted = clockTick();
            regTableUpdate(false);
            memTableUpdate();
        }

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
        TableColumn<ClockStep, String> step = new TableColumn<>("Previous Clock Cycle");
        TableColumn<ClockStep, String> prgCtr = new TableColumn<>("Program Counter");
        TableColumn<ClockStep, String> instrReg = new TableColumn<>("Instruction Register");
        TableColumn<ClockStep, String> ioReg = new TableColumn<>("Input/Output Register");
        TableColumn<ClockStep, String> accumulator = new TableColumn<>("Accumulator");
        TableColumn<ClockStep, String> stackPtr = new TableColumn<>("Stack Pointer");
        TableColumn<ClockStep, String> memBufReg = new TableColumn<>("Memory Buffer Register");
        TableColumn<ClockStep, String> memAddrReg = new TableColumn<>("Memory Address Register");

        step.setCellValueFactory(new PropertyValueFactory<>("prevStepString"));
        prgCtr.setCellValueFactory(new PropertyValueFactory<>("programCtr"));
        instrReg.setCellValueFactory(new PropertyValueFactory<>("instructionReg"));
        ioReg.setCellValueFactory(new PropertyValueFactory<>("ioReg"));
        accumulator.setCellValueFactory(new PropertyValueFactory<>("accumulator"));
        stackPtr.setCellValueFactory(new PropertyValueFactory<>("stackPointer"));
        memBufReg.setCellValueFactory(new PropertyValueFactory<>("memoryBufferReg"));
        memAddrReg.setCellValueFactory(new PropertyValueFactory<>("memoryAddrReg"));


        registers.getColumns().addAll(step, prgCtr, instrReg, ioReg, accumulator, stackPtr, memBufReg, memAddrReg);



        registers.refresh();
    }

    public void memTableInit() {
        TableColumn<MemoryTableRow, String>[] offsets = new TableColumn[16];

        TableColumn<MemoryTableRow, String> row = new TableColumn<>("Memory Row");
        row.setCellValueFactory(new PropertyValueFactory<>("rowHexString"));

        for(int i = 0; i < offsets.length; i++) {
            offsets[i] = new TableColumn<>(Integer.toHexString(i).toUpperCase());
        }
        offsets[0].setCellValueFactory(new PropertyValueFactory<>("zero"));
        offsets[1].setCellValueFactory(new PropertyValueFactory<>("one"));
        offsets[2].setCellValueFactory(new PropertyValueFactory<>("two"));
        offsets[3].setCellValueFactory(new PropertyValueFactory<>("three"));
        offsets[4].setCellValueFactory(new PropertyValueFactory<>("four"));
        offsets[5].setCellValueFactory(new PropertyValueFactory<>("five"));
        offsets[6].setCellValueFactory(new PropertyValueFactory<>("six"));
        offsets[7].setCellValueFactory(new PropertyValueFactory<>("seven"));
        offsets[8].setCellValueFactory(new PropertyValueFactory<>("eight"));
        offsets[9].setCellValueFactory(new PropertyValueFactory<>("nine"));
        offsets[10].setCellValueFactory(new PropertyValueFactory<>("a"));
        offsets[11].setCellValueFactory(new PropertyValueFactory<>("b"));
        offsets[12].setCellValueFactory(new PropertyValueFactory<>("c"));
        offsets[13].setCellValueFactory(new PropertyValueFactory<>("d"));
        offsets[14].setCellValueFactory(new PropertyValueFactory<>("e"));
        offsets[15].setCellValueFactory(new PropertyValueFactory<>("f"));




        for(int i = 0; i < memRow.length; i++) {
            memRow[i] = new MemoryTableRow(i, getMainMemory());
            memRow[i].update();
        }

        memory.getColumns().add(row);
        memory.getColumns().addAll(offsets);

        memory.setItems(FXCollections.observableArrayList(memRow));

        memory.refresh();
    }

    public void regTableUpdate(Boolean clearTable) {
        if(clearTable) {
            clockSteps.clear();
        }
        else {
            for(int i = 0; i < clockSteps.size(); i++) {
                clockSteps.get(i).setPreviousStep(clockSteps.get(i).getPreviousStep() + 1);
            }
            clockSteps.add(0, new ClockStep(getProgramCtr(), getInstructionReg(), getIoReg(), getAccumulator(), getStackPointer(), getMemoryBufferReg(), getMemoryAddrReg(), 0));
        }



        while(clockSteps.size() > 10) {
            clockSteps.remove(clockSteps.size() - 1);
        }

        registers.setItems(FXCollections.observableArrayList(clockSteps));
        registers.refresh();
    }

    public void memTableUpdate() {
        for(MemoryTableRow m : memRow) {
            m.update();
        }

        memory.setItems(FXCollections.observableArrayList(memRow));
        memory.refresh();
    }
}
