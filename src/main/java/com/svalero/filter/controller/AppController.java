package com.svalero.filter.controller;


import com.svalero.filter.utils.ShowAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML
    private Button buttonCreateFilter;
    @FXML
    private Button buttonOpenImage;
    @FXML
    private ListView filterListView;
    @FXML
    private TabPane tabFilters;
    @FXML
    private Label imagePathLabel;
    @FXML
    private ImageView thumbnailImageView;
    private File file;

    public AppController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabFilters.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        this.filterListView.getItems().addAll("GrayscaleFilter","BrighterFilter","SepiaFilter", "InvertColorFilter", "ColorTintFilter");
        this.filterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    @FXML
    public void openImage (ActionEvent event) {
        Stage stage = (Stage) this.buttonOpenImage.getScene().getWindow();
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(stage);
        if (selectedFile != null) {
            this.file = selectedFile;
            this.imagePathLabel.setText(this.file.getName());

        try {
            Image image = new Image(selectedFile.toURI().toString());
            thumbnailImageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            ShowAlert.showAlert("Error", "Error al cargar la imagen", e.getMessage());
        }
    }
    }

    @FXML
    private void createFilter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.filter/controller/filterPane.fxml"));
            System.out.println(this.filterListView.getSelectionModel().getSelectedItems());
            List<String> selectedFilters = new ArrayList<String>(this.filterListView.getSelectionModel().getSelectedItems());
            FilterController filterController = new FilterController (file, selectedFilters);
            loader.setController(filterController);
            AnchorPane anchorPane = loader.load();

            String fileName = file.getName();
            System.out.println(fileName);
            tabFilters.getTabs().add(new Tab(fileName, anchorPane));

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

}
