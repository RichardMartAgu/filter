package com.svalero.filter;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
    private File file;

    public AppController(){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabFilters.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
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
    @FXML
    private void createFilter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/filterPane.fxml"));
            System.out.println(this.filterListView.getSelectionModel().getSelectedItems());
            List<String> selectedFilters = new ArrayList<String>(this.filterListView.getSelectionModel().getSelectedItems());
            FilterController filterController = new FilterController (file, selectedFilters);
            loader.setController(filterController);
            AnchorPane root = loader.load();

            String fileName = file.getName();
            System.out.println(fileName);
            tabFilters.getTabs().add(new Tab(fileName, root));

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

    }

}
