package com.svalero.filter;

import com.svalero.filter.task.FilterTask;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.MalformedParametersException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    @FXML
    private Label lbStatus;
    @FXML
    private ProgressBar pbProgress;
    @FXML
    private ImageView sourceImageView;
    @FXML
    private ImageView targetImageView;
    @FXML
    private Button saveButton;

    private File sourceImage;
    private List<String> selectedFilters;
    private FilterTask filterTask;
    private BufferedImage outputImage;

    public FilterController(File sourceImage, List<String> selectedFilters) {
        this.sourceImage = sourceImage;
        this.selectedFilters = selectedFilters;
        this.outputImage = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InputStream stream;
        try {
            stream = new FileInputStream(sourceImage.getAbsolutePath());
            Image image = new Image(stream);
            this.sourceImageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            filterTask = new FilterTask(this.sourceImage, this.selectedFilters);

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(filterTask.progressProperty());

            saveButton.setDisable(true);

            filterTask.stateProperty().addListener((observableValue, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("El filtro ha finalizado con éxito");
                    alert.show();
                    saveButton.setDisable(false);
                }
            });

            filterTask.setOnSucceeded(event -> {
                this.outputImage = filterTask.getValue();
                Image image = SwingFXUtils.toFXImage(outputImage, null);

                this.targetImageView.setImage(image);
            });

            filterTask.messageProperty().addListener((observableValue, oldValue, newValue) -> {
                lbStatus.setText(newValue);
            });

            new Thread(filterTask).start();

        } catch (MalformedParametersException murle) {
            murle.printStackTrace();
        }
    }



    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
