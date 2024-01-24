package com.svalero.filter.controller;

import com.svalero.filter.task.FilterTask;
import com.svalero.filter.utils.ShowAlert;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
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
    private Button save;
    @FXML
    private Button moreFilters;
    @FXML
    private Button undoButton;
    @FXML
    private Button redoButton;

    private File sourceImage;
    private List<String> selectedFilters;
    private FilterTask filterTask;
    private BufferedImage outputImage;
    private AppController appController;

    public FilterController(File sourceImage, List<String> selectedFilters, AppController appController) {
        this.sourceImage = sourceImage;
        this.selectedFilters = selectedFilters;
        this.appController = appController;
        this.outputImage = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        InputStream stream;
        try {
            System.out.println(sourceImage + "FILTER");
            stream = new FileInputStream(sourceImage.getAbsolutePath());
            System.out.println(stream + "STREAM");
            Image image = new Image(stream);

            this.sourceImageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            filterTask = new FilterTask(this.sourceImage, this.selectedFilters);

            pbProgress.progressProperty().unbind();
            pbProgress.progressProperty().bind(filterTask.progressProperty());

            save.setDisable(true);
            undoButton.setDisable(true);
            moreFilters.setDisable(true);
            redoButton.setDisable(true);

            filterTask.stateProperty().addListener((observableValue, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    ShowAlert.showInformationAlert("Información", "Información", "El filtro se ha realizado con éxito");

                }
            });

            filterTask.setOnSucceeded(event -> {
                this.outputImage = filterTask.getValue();
                Image image = SwingFXUtils.toFXImage(outputImage, null);

                this.targetImageView.setImage(image);
                save.setDisable(false);
                undoButton.setDisable(false);
                moreFilters.setDisable(false);

            });

            filterTask.messageProperty().addListener((observableValue, oldValue, newValue) -> {
                lbStatus.setText(newValue);
            });

            new Thread(filterTask).start();

        } catch (MalformedParametersException murle) {
            murle.printStackTrace();
        }
    }

    @FXML
    private void saveFilteredImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");

        fileChooser.setInitialFileName("imagen_editada");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos PNG (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File("C:/Users/richa/IdeaProjects/segundodam/filter/src/main/resources/com.svalero.filter/target/image"));
        Stage stage = (Stage) this.save.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        try {

            ImageIO.write(this.outputImage, "png", file);
            ShowAlert.showInformationAlert("Información", "Información", "La imagen se ha guardado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
            ShowAlert.showErrorAlert("Error", "Error al guardar la imagen", e.getMessage());
        }
    }

    @FXML
    private void undoFilters() {
        try {
            BufferedImage originalImage = ImageIO.read(new FileInputStream(this.sourceImage));
            Image image = SwingFXUtils.toFXImage(originalImage, null);
            this.targetImageView.setImage(image);
            this.undoButton.setDisable(true);
            this.redoButton.setDisable(false);
            this.outputImage = originalImage;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void redoFilters() {

        this.outputImage = filterTask.getValue();
        Image image = SwingFXUtils.toFXImage(outputImage, null);
        this.targetImageView.setImage(image);
        this.redoButton.setDisable(true);
        this.undoButton.setDisable(false);

    }

    @FXML
    private void copyImageToAppController() {

        if (this.outputImage != null) {
            try {
                File tempFile = File.createTempFile("filtered_image", ".png");
                ImageIO.write(this.outputImage, "png", tempFile);
                String tempFilePath = tempFile.getAbsolutePath();
                appController.setImage(SwingFXUtils.toFXImage(this.outputImage, null), tempFilePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



