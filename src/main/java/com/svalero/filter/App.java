package com.svalero.filter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("Starting JavaFX Application");
        super.init();
    }

    @Override
    public void start(Stage stage) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainWindow.fxml"));
            loader.setController(new AppController());
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Filters Aplication");
            stage.show();
    }


    public void stop() throws Exception {
        System.out.println("Goodbye!");
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}
