package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {
    public Pane node;
    public Label fileLabel;
    public TextArea textarea;
    public Button decrypt;

    private String fileTextString;
    private final int shift = 1;

    public void onButtonChooseClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        boolean isDecrypt = ((Button) actionEvent.getTarget()).getId().equals("decrypt");

        File file = fileChooser.showOpenDialog(node.getScene().getWindow());

        try (FileReader reader = new FileReader(file)) {
            int c;
            StringBuilder fileText = new StringBuilder();

            while ((c = reader.read()) != -1) {
                if (isDecrypt) {
                    c -= shift;
                }

                fileText.append((char) c);
            }

            fileTextString = fileText.toString();
            textarea.setText(fileTextString);

            fileLabel.setText(file.getAbsolutePath());
        } catch (Exception ex) {
            fileLabel.setText("Cannot open file");
            System.out.println(ex.getMessage());
        }
    }

    public void onButtonSaveClick() {
        FileChooser fileChooser = new FileChooser();

        File file = fileChooser.showSaveDialog(node.getScene().getWindow());

        try (FileWriter writer = new FileWriter(file, false)) {
            char[] chars = fileTextString.toCharArray();

            for (char c : chars) {
                c += shift;
                writer.append(c);
            }

            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
