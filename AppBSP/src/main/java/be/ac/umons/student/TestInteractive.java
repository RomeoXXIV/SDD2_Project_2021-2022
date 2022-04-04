package be.ac.umons.student;

import be.ac.umons.student.controllers.MainViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class TestInteractive extends Application {

    Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/MainView.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();

        MainViewController mainViewController = fxmlLoader.getController();
        mainViewController.setStage(this.primaryStage);

        Scene scene = new Scene(root);

        this.primaryStage.setTitle("MaySee Application");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
    }
}
