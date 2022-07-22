package be.ac.umons.student;

import be.ac.umons.student.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

/**
 * Application graphique intéractive permettant de choisir un fichier scène, un point de vue et de montrer visuellement le comportement de l'algorithme du peintre
 * sur la scène par rapport au point de vue.
 *
 * @author Romeo Ibraimovski
 */
public class TestInteractive extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/MainView.fxml").toURI().toURL());

        Parent root = fxmlLoader.load();

        ((MainController) fxmlLoader.getController()).setStage(primaryStage);

        Scene scene = new Scene(root);

        primaryStage.setTitle("MaySee Application");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
