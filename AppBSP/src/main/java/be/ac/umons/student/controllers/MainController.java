package be.ac.umons.student.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public AnchorPane MainAnchorPane;
    private double progress = 0;

    @FXML
    private ProgressBar DownloadBar;

    @FXML
    protected void handlePressDownloadButton(ActionEvent actionEvent) {
        new Thread(() -> {
            for (int i = 1; i <= 1000000; i++) {
                final int counter = i;
                Platform.runLater(() -> DownloadBar.setProgress(counter / 1000000.0));
            }
        }).start();
    }

    private void updateProgressBar(double progress) {
        DownloadBar.setProgress(progress);
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateProgressBar(progress);
    }
}
