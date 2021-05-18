package MARIE;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SimController implements Initializable {
    private final String[] INPUT_TYPES = {"HEX", "DEC", "OCT", "ASCII"};
    @FXML
    private Menu reloadMenuItem, runMenuItem, stepMenuItem;
    @FXML
    private MenuItem newMenuItem, openMenuItem;

    @FXML
    private ComboBox inputType;
    @FXML
    private TableView<MemoryTableRow> memory;
    @FXML
    private TableView<ClockStepTableRow> registers;
    @FXML
    private TableView<StackTableRow> stackTableView;
    @FXML
    private TextArea console;
    @FXML
    private TextField userInput;

    private File currentlyOpenFile;

    private Stage primaryStage;

    private SimulatorThread marieSimThread;

    private boolean canInput = false;

    MemoryTableRow[] memRow = new MemoryTableRow[256];

    ArrayList<ClockStepTableRow> clockStepTableRows = new ArrayList<>();
    ArrayList<StackTableRow> stackTableRows = new ArrayList<>();

    public SimController() {

    }

    public SimController(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("MARIE Simulator");
        Parent root = FXMLLoader.load(getClass().getResource("/sim_layout.fxml"));
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        marieSimThread = new SimulatorThread(this);
        EditorController.setParent(this);

        inputType.setItems(FXCollections.observableArrayList(INPUT_TYPES));
        inputType.getSelectionModel().selectFirst();

        Label reloadMenuLabel = new Label("Reload");
        reloadMenuLabel.setOnMouseClicked(mouseEvent -> reload());

        Label runMenuLabel = new Label("Run");
        runMenuLabel.setOnMouseClicked(mouseEvent -> runFile());

        Label stepMenuLabel = new Label("Step");
        stepMenuLabel.setOnMouseClicked(mouseEvent -> step());

        reloadMenuItem.setGraphic(reloadMenuLabel);
        runMenuItem.setGraphic(runMenuLabel);
        stepMenuItem.setGraphic(stepMenuLabel);

        newMenuItem.setOnAction(actionEvent -> {
            try {
                new EditorController(SimController.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        openMenuItem.setOnAction(actionEvent -> openFile());

        regTableInit();
        memTableInit();
        stackTableInit();
    }

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Machine Code or Editor File");

        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            //now see if it is an assembly file or an executable file
            if (selectedFile.toString().endsWith(MARIEValues.EXTENSION)) {
                openEditorFile(selectedFile);
            } else if (selectedFile.toString().endsWith(MARIEValues.EXECUTABLE_EXTENSION)) {
                openFile(selectedFile);
            }
        }
    }

    public void openFile(File toOpen) {
        try {
            //first make sure we stop any running simulations, stop method handles checking for us
            stop();
            //then we make sure we can't accidentally start in the middle of updating everything
            marieSimThread.runningSemaphore.acquire();
            marieSimThread.clear();
            int offset = 0;
            Scanner input = new Scanner(toOpen);
            //read first line to get the ORG
            offset = Integer.parseInt(input.nextLine().split(" ")[1], 16);
            marieSimThread.setProgramCtr(offset); //set the program counter to the ORG offset
            currentlyOpenFile = toOpen;

            int i = 0;
            while (input.hasNextLine()) {
                marieSimThread.getMainMemory()[i + offset] = Integer.parseInt(input.nextLine(), 16);
                i++;
            }

            memTableUpdate();
            regTableUpdate(true);
            stackTableUpdate();
        } catch (IOException e) {
            //TODO implement
            e.printStackTrace();
        } catch (InterruptedException e) {
            //TODO implement
            e.printStackTrace();
        } finally {
            marieSimThread.runningSemaphore.release();
        }

    }

    public void openEditorFile(File toOpen) {
        try {
            new EditorController(toOpen);
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
        if(marieSimThread.runningSemaphore.tryAcquire()) {
            new Thread(marieSimThread).start();
            marieSimThread.runningSemaphore.release();
        }

    }

    public void stop() {
        try {
            if(!marieSimThread.runningSemaphore.tryAcquire()) {
                marieSimThread.stopMutex.acquire();
                marieSimThread.stop = true;
            }
            else {
                marieSimThread.runningSemaphore.release(); //immediately release since we successfully acquired it
            }
        } catch (InterruptedException e) {
            //TODO implement
        }
        finally {
            marieSimThread.stopMutex.release();
        }
    }

    @FXML
    public void step() {
        try {
            marieSimThread.haltedMutex.acquire();
            if (!marieSimThread.halted && marieSimThread.runningSemaphore.tryAcquire()) {
                marieSimThread.halted = marieSimThread.clockTick();
                regTableUpdate(false);
                memTableUpdate();
                marieSimThread.runningSemaphore.release();
            }
        } catch (InterruptedException e) {
            //TODO implement
        } finally {
            marieSimThread.haltedMutex.release();
        }

    }

    @FXML
    public void onInputEnter() {
        if (canInput) {
            try {
                marieSimThread.inputBufferMutex.acquire();
                if (inputType.getValue().equals(INPUT_TYPES[0])) {//HEX
                    marieSimThread.inputBuffer = Integer.parseInt(userInput.getText(), 16);
                } else if (inputType.getValue().equals(INPUT_TYPES[1])) {//DEC
                    marieSimThread.inputBuffer = Integer.parseInt(userInput.getText());
                } else if (inputType.getValue().equals(INPUT_TYPES[2])) {//OCT
                    marieSimThread.inputBuffer = Integer.parseInt(userInput.getText(), 8);
                } else { //ASCII
                    if (userInput.getText().length() > 0) {
                        marieSimThread.inputBuffer = userInput.getText().charAt(0);
                    } else {
                        marieSimThread.inputBuffer = 0; //assume they want a null char
                    }
                }
                userInput.setText("");
            } catch (InterruptedException e) {
                //TODO implement
                e.printStackTrace();
            } catch (NumberFormatException e) {
                //TODO implement number exception handling
            } finally {
                marieSimThread.inputBufferMutex.release();
            }
            canInput = false;
        }
        marieSimThread.threadInputSemaphore.release();
    }


    public void input() {
        canInput = true;
    }


    void output(int output) {
        console.setText(console.getText() + output + '\n');
    } //TODO modify

    public void regTableInit() {
        TableColumn<ClockStepTableRow, String> step = new TableColumn<>("Previous Clock Cycle");
        step.setSortable(false);
        TableColumn<ClockStepTableRow, String> prgCtr = new TableColumn<>("Program Counter");
        prgCtr.setSortable(false);
        TableColumn<ClockStepTableRow, String> instrReg = new TableColumn<>("Instruction Register");
        instrReg.setSortable(false);
        TableColumn<ClockStepTableRow, String> ioReg = new TableColumn<>("Input/Output Register");
        ioReg.setSortable(false);
        TableColumn<ClockStepTableRow, String> accumulator = new TableColumn<>("Accumulator");
        accumulator.setSortable(false);
        TableColumn<ClockStepTableRow, String> stackPtr = new TableColumn<>("Stack Pointer");
        stackPtr.setSortable(false);
        TableColumn<ClockStepTableRow, String> memBufReg = new TableColumn<>("Memory Buffer Register");
        memBufReg.setSortable(false);
        TableColumn<ClockStepTableRow, String> memAddrReg = new TableColumn<>("Memory Address Register");
        memAddrReg.setSortable(false);

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
        row.setSortable(false);

        for (int i = 0; i < offsets.length; i++) {
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

        for(TableColumn<MemoryTableRow, String> t : offsets) {
            t.setSortable(false);
        }

        for (int i = 0; i < memRow.length; i++) {
            memRow[i] = new MemoryTableRow(i, marieSimThread.getMainMemory());
            memRow[i].update();
        }

        memory.getColumns().add(row);
        memory.getColumns().addAll(offsets);

        memory.setItems(FXCollections.observableArrayList(memRow));

        memory.refresh();
    }

    public void stackTableInit() {
        TableColumn<StackTableRow, String> stackOffset = new TableColumn<>("Stack Pointer Offset");
        stackOffset.setCellValueFactory(new PropertyValueFactory<>("stackOffsetStr"));
        stackOffset.setSortable(false);

        TableColumn<StackTableRow, String> stackVal = new TableColumn<>("Value");
        stackVal.setCellValueFactory(new PropertyValueFactory<>("valueStr"));
        stackVal.setSortable(false);

        stackOffset.setSortType(TableColumn.SortType.DESCENDING);
        for(int i = 0; i <= marieSimThread.getStackPointer(); i++) {
            stackTableRows.add(new StackTableRow(i, marieSimThread.getMainMemory(), marieSimThread.getStackPointer()));
        }

        stackTableView.getColumns().addAll(stackOffset, stackVal);

        stackTableView.setItems(FXCollections.observableArrayList(stackTableRows));

        stackTableView.refresh();
    }

    public void instrTableInit() {
        //TODO implement
    }

    public void regTableUpdate(Boolean clearTable) {
        if (clearTable) {
            clockStepTableRows.clear();
        } else {
            for (int i = 0; i < clockStepTableRows.size(); i++) {
                clockStepTableRows.get(i).setPreviousStep(clockStepTableRows.get(i).getPreviousStep() + 1);
            }
            clockStepTableRows.add(0, new ClockStepTableRow(marieSimThread.getProgramCtr(), marieSimThread.getInstructionReg(), marieSimThread.getIoReg(), marieSimThread.getAccumulator(), marieSimThread.getStackPointer(), marieSimThread.getMemoryBufferReg(), marieSimThread.getMemoryAddrReg(), 0));
        }


        while (clockStepTableRows.size() > 10) {
            clockStepTableRows.remove(clockStepTableRows.size() - 1);
        }

        registers.setItems(FXCollections.observableArrayList(clockStepTableRows));
        registers.refresh();
    }

    public void memTableUpdate() {
        for (MemoryTableRow m : memRow) {
            m.update();
        }

        memory.setItems(FXCollections.observableArrayList(memRow));
        memory.refresh();
    }

    public void stackTableUpdate() {
        //TODO implement
    }
}
