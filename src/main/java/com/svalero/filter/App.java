package com.svalero.filter;

import com.svalero.filter.controller.AppController;
import com.svalero.filter.controller.SplashScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {

    @Override
    public void init() throws Exception {
        System.out.println("Starting JavaFX Application");
        super.init();
    }

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.filter/controller/splashScreen.fxml"));
        loader.setController(new SplashScreenController());
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            javafx.application.Platform.runLater(() -> {
                stage.close();
                try {
                    showMainStage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }).start();

    }

    public void stop() throws Exception {
        System.out.println("Goodbye!");
        super.stop();
    }

    private void showMainStage() throws IOException {

        Stage mainStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com.svalero.filter/controller/mainWindow.fxml"));
        loader.setController(new AppController());
        Scene scene = new Scene(loader.load());
        mainStage.setScene(scene);
        mainStage.setTitle("Filters Aplication");
        mainStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
