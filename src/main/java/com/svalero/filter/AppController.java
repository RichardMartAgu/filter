package com.svalero.filter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML
    private Button buttonOpenImage;
    @FXML
    private ListView filterListView;
    @FXML
    private TabPane tabFilters;
    @FXML
    private Label imagePathLabel;
    ObservableList<String> selectedItems;
    private File file;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabFilters.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabFilters.setTabClosingPolicy((TabPane.TabClosingPolicy.ALL_TABS));
        this.filterListView.getItems().addAll("GrayscaleFilter", "ColorTintFilter", "GrayscaleFilter", "InvertColorFilter", "SepiaFilter");
        this.filterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }
    @FXML
    public void openImage (ActionEvent event) {
        Stage stage = (Stage) this.buttonOpenImage.getScene().getWindow();
        FileChooser fc = new FileChooser();
        this.file = fc.showOpenDialog(stage);
        this.imagePathLabel.setText(this.file.getName());
    }

}
