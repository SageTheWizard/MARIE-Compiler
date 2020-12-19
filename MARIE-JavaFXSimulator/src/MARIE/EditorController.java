package MARIE;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class EditorController implements Initializable {

    final String END_TOKEN = "\nEND";

    private static SimController parent;

    private Stage editorStage;

    @FXML
    private MenuItem open, save, saveas, assemble, saveAndLoad;

    @FXML
    private TextArea editor;

    File currentFile = null;
    File currentExecutable = null;

    public EditorController() {

    }

    public EditorController(SimController parent) throws IOException {
        this.parent = parent;
        editorStage = new Stage();
        editorStage.setTitle("MARIE Editor");
        Parent root = FXMLLoader.load(getClass().getResource("editor_layout.fxml"));
        editorStage.setScene(new Scene(root, 300, 275));
        editorStage.show();
    }

    public EditorController(File toOpen) throws IOException {
        this.parent = parent;
        this.currentFile = toOpen;
        editorStage = new Stage();
        editorStage.setTitle("MARIE Editor");
        Parent root = FXMLLoader.load(getClass().getResource("editor_layout.fxml"));
        editorStage.setScene(new Scene(root, 300, 275));
        editorStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        open.setOnAction(actionEvent -> openFile());

        save.setOnAction(actionEvent -> {
            if (currentFile == null) {
                saveFile();
            } else {
                saveFile(currentFile);
            }
        });

        saveas.setOnAction(actionEvent -> saveFile());

        assemble.setOnAction(actionEvent -> saveAndAssmble());

        saveAndLoad.setOnAction(actionEvent -> {
            saveAndAssmble();
            parent.openFile(currentExecutable);
        });

        if (currentFile != null) {
            openFile(currentFile);
        }
    }

    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Machine Code or Editor File");

        File selectedFile = fileChooser.showOpenDialog(editorStage);
        if (selectedFile != null) {
            openFile(selectedFile);
        }
    }

    public void openFile(File toOpen) {
        try {
            currentFile = toOpen;
            Scanner assemblyFile = new Scanner(toOpen);

            while (assemblyFile.hasNextLine()) {
                editor.setText(editor.getText() + assemblyFile.nextLine() + '\n');
            }
        } catch (IOException e) {
            //TODO implement
            e.printStackTrace();
        }
    }

    public void saveFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Save Assembly Code");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("." + MARIEValues.EXTENSION, "marie assembly file"));

        File selectedFile = fileChooser.showSaveDialog(editorStage);
        if (selectedFile != null) {
            saveFile(selectedFile);
        }
    }

    public void saveFile(File toSave) {
        if (!toSave.toString().endsWith("." + MARIEValues.EXTENSION)) {
            toSave = new File(toSave + "." + MARIEValues.EXTENSION);
        }
        currentFile = toSave;
        PrintWriter out = null;
        try {
            out = new PrintWriter(toSave);
        } catch (FileNotFoundException e) {
            //TODO implement
        }

        out.print(editor.getText());
        out.close();
    }

    public void saveAndAssmble() {
        if (currentFile == null) {
            saveFile();
        } else {
            saveFile(currentFile);
        }


        try {
            String[] assembledOutput = MARIEAssembler.assemble(currentFile);
            if (assembledOutput != null) {
                currentExecutable = new File(currentFile.toString().substring(0, (int) (currentFile.toString().length() - (MARIEValues.EXTENSION.length() + 1))) + "." + MARIEValues.EXECUTABLE_EXTENSION);
                PrintWriter out = new PrintWriter(currentExecutable);
                for (String s : assembledOutput) {
                    out.println(s);
                }
                out.close();
            } else {
                //TODO implement
            }
        } catch (FileNotFoundException e) {
            //TODO implement
            e.printStackTrace();
        }
    }

    public SimController getParent() {
        return parent;
    }

    public static void setParent(SimController newParent) {
        parent = newParent;
    }

    public File getCurrentFile() {
        return currentFile;
    }

    public void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
}
