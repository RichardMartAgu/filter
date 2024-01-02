package com.svalero.filter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;

import java.net.URL;
import java.util.ResourceBundle;



public class AppController implements Initializable {


    @FXML
    private ListView filterListView;
    @FXML
    private TabPane tabFilters;
    public void initialize(URL location, ResourceBundle resources) {
        tabFilters.setTabClosingPolicy((TabPane.TabClosingPolicy.ALL_TABS));
        this.filterListView.getItems().addAll("GrayscaleFilter");
        this.filterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    }
}
