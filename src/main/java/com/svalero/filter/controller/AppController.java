package com.svalero.filter.controller;


import com.svalero.filter.model.FilterItem;
import com.svalero.filter.utils.FilterFile;
import com.svalero.filter.utils.ShowAlert;
import com.svalero.filter.utils.IsImage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AppController implements Initializable {

    @FXML
    private Button buttonCreateFilter;
    @FXML
    private Button buttonOpenImage;
    @FXML
    private Button historyBtn;
    @FXML
    private Button buttonOpenFolder;
    @FXML
    private ListView filterListView;
    @FXML
    private TabPane tabFilters;
    @FXML
    private Label imagePathLabel;
    @FXML
    private ImageView thumbnailImageView;

    @FXML
    private TableView<FilterItem> tableView;

    @FXML
    private TableColumn<FilterItem, String> nameColumn;

    @FXML
    private TableColumn<FilterItem, String> filtersColumn;

    @FXML
    private TableColumn<FilterItem, String> dateColumn;
    private File file;
    private File selectedDirectory;
    private FilterFile filterFile = new FilterFile();


    public AppController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setVisible(false);

        tabFilters.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        this.filterListView.getItems().addAll("GrayscaleFilter", "BrighterFilter", "SepiaFilter", "InvertColorFilter", "ColorTintFilter");
        this.filterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        filtersColumn.setCellValueFactory(new PropertyValueFactory<>("filters"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    @FXML
    public void openImage(ActionEvent event) {
        Stage stage = (Stage) this.buttonOpenFolder.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:/Users/richa/IdeaProjects/segundodam/filter/src/main/resources/com.svalero.filter/target/image"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            this.file = selectedFile;
            this.imagePathLabel.setText(this.file.getAbsolutePath());

            try {
                Image image = new Image(selectedFile.toURI().toString());
                thumbnailImageView.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
                ShowAlert.showErrorAlert("Error", "Error al cargar la imagen", e.getMessage());
            }
        }
    }

    @FXML
    public void openFolder(ActionEvent event) {
        Stage stage = (Stage) this.buttonOpenImage.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Directory");
        directoryChooser.setInitialDirectory(new File("C:/Users/richa/IdeaProjects/segundodam/filter/src/main/resources/com.svalero.filter/target/image"));

        selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            imagePathLabel.setText(selectedDirectory.getAbsolutePath());
            System.out.println(selectedDirectory);

        }
    }

    @FXML
    private void createFilter(ActionEvent event) throws IOException {

        File sourceFile = new File(imagePathLabel.getText());
        System.out.println(sourceFile + "LA IMAGEN O CARPETA");

        System.out.println(this.filterListView.getSelectionModel().getSelectedItems());
        List<String> selectedFilters = new ArrayList<String>(this.filterListView.getSelectionModel().getSelectedItems());

        if (IsImage.isImage(sourceFile)) {
            createTask(sourceFile, selectedFilters);
        } else if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles(IsImage::isImage);
            if (files != null) {
                for (File file : files) {
                    createTask(file, selectedFilters);
                }
            }
        }
    }

    private void createTask(File sourceFile, List<String> selectedFilters) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.filter/controller/filterPane.fxml"));

        FilterController filterController = new FilterController(sourceFile, selectedFilters);

        loader.setController(filterController);

        String fileName = sourceFile.getName();
        System.out.println(fileName + "  APPController");
        AnchorPane anchorPane = loader.load();
        tabFilters.getTabs().add(new Tab(fileName, anchorPane));

        filterFile.createNewFile();

        String name = fileName;
        List<String> filterType = selectedFilters;
        String joinedFilters = String.join(",", filterType);
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String creationDate = date.format(formatter);

        ObservableList<String> filterData = FXCollections.observableArrayList();
        filterData.add(name);
        filterData.addAll(joinedFilters);
        filterData.add(creationDate);

        filterFile.writeFile(filterData);

        ShowAlert.showAlert(historyBtn.getScene().getWindow(), "Registrado correctamente en el Historial", 3);

        if (selectedDirectory != null) {
            File[] imageFiles = selectedDirectory.listFiles(IsImage::isImage);

            System.out.println(imageFiles);
        }
    }

    @FXML
    void historyTable(ActionEvent event) {

        tableView.setVisible(!tableView.isVisible());
        tableView.getItems().clear();
        String filePath = "C:\\Users\\richa\\IdeaProjects\\segundodam\\filter\\src\\main\\resources\\com.svalero.filter\\target\\filterRegister.txt";
        List<FilterItem> filterItems = filterFile.getFilterItemsFromFile(filePath);
        tableView.getItems().addAll(filterItems);
    }
}
