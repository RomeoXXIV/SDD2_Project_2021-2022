package be.ac.umons.student;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class TestInteractive extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent layout = FXMLLoader.load(new File("src/main/resources/fxml/View.fxml").toURI().toURL());

        Scene scene = new Scene(layout);

        primaryStage.setTitle("BSP Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
