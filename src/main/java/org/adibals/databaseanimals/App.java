package org.adibals.databaseanimals;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("Animal Database");
        stage.setScene(scene);
        stage.show();
    }

    public static void genFiles() {
        new File(XmlLoader.HOME + "/DataMaze/data/animal-data.xml").mkdirs();
        new File(XmlLoader.HOME + "/DataMaze/images").mkdirs();
    }

    public static void main(String[] args) {
        launch();
    }
}