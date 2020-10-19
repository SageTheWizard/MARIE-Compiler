package MARIE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Controller {
    @FXML private TextArea simBox;
    @FXML private Button stepper;
    @FXML private TextArea runBox;
    @FXML private TextArea codeBox;
    @FXML private TextField filename;

    private String[] lineByLine;
    private int debugCounter;

    public Controller() {

    }
    public Controller(Stage primaryStage) throws IOException {
        primaryStage.show();
        debugCounter = 0;
    }


    @FXML
    private void saveFile() {
        String marieCode = codeBox.getText();
        try {
            String fn = filename.getText();
            if (fn.equals("")) {
                fn = "untitled.mre";
            }
            FileWriter fileWriter = new FileWriter(fn);
            fileWriter.write(marieCode);
            fileWriter.close();
        } catch (IOException ioe) {
            System.err.println("ERROR SAVING FILE");
        }
    }

    @FXML
    private void openFile() {
        String fn = "/home/jacob/CMPSC496/MARIE-Compiler/";
        fn += filename.getText();

        if (fn.equals("/home/jacob/CMPSC496/MARIE-Compiler/")) {
            // Lol do some error here but no popup or w/e :) and it will be a file browser anyway
            // eventually so it doesn't matter so I might not even implement this error
            fn += "untitled.mre";
            filename.setText("untitled.mre");
        }
        // Maybe do an else if not the right file extension I dunno lol

        try {
            Scanner reader = new Scanner(new File(fn));
            codeBox.clear();
            while (reader.hasNext()) {
                codeBox.appendText(reader.nextLine());
                codeBox.appendText("\n");
            }
        } catch (FileNotFoundException fnfe) {
            System.err.println("oopsys");
            System.err.println(fn);
            return;
        }
    }

    @FXML
    private void runCode() {
        String code = codeBox.getText();
        runBox.clear();
        lineByLine = code.split("\n");

        for (String line : lineByLine) {
            if (line.toUpperCase().equals("MARIE")) {
                runBox.appendText("MARIE IS COOL");
            }
            else {
                runBox.appendText(line);
            }
            runBox.appendText("\n");
        }
    }

    @FXML
    private void debugCode() {
        String code = codeBox.getText();
        debugCounter = 0;
        runBox.clear();
        lineByLine = code.split("\n");
        runBox.appendText("***** Use the Stepper to Step through the code *******\n");
        runBox.appendText("------------------------------------------------------\n");
        stepper.setDisable(false);

    }

    @FXML
    private void stepCode() {
        String line = lineByLine[debugCounter];
        debugCounter++;
        if (line.toUpperCase().equals("MARIE")) {
            runBox.appendText("------DEBUGGER----> [" + line + "] CONVERTED TO [MARIE IS COOL]\n");
            runBox.appendText("MARIE IS COOL");
        }
        else {
            runBox.appendText(line);
        }
        runBox.appendText("\n");

        if (debugCounter >= lineByLine.length) {
            runBox.appendText("STOPPING DEBUGGING! Reached End of File.");
            stepper.setDisable(true);
        }
    }


}
