package be.ac.umons.student.controllers;

import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.heuristics.*;
import be.ac.umons.student.models.painter.Painter;
import be.ac.umons.student.models.painter.PainterInteractive;
import be.ac.umons.student.models.painter.ViewPoint;
import be.ac.umons.student.utils.SceneReader;
import com.oracle.javafx.scenebuilder.kit.util.control.paintpicker.DoubleTextField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML public HBox appCenterHBox;
    private Stage stage;

    // Not Yet Used
    @FXML public VBox rootVBox;
    @FXML public VBox inAppCenterHBoxVBox;
    private PainterInteractive painterInteractive;
    private HeuristicSelector heuristicSelector = new StandardHeuristic();
    private BSPTree bspTree;
    private ViewPoint viewPoint;

    // MenuBar Variables
    private File sceneFile;
    private SceneReader sceneReader;

    // Main Canvas Variables
    @FXML public ScrollPane scrollPane;
    @FXML public Canvas mainCanvas;
    private GraphicsContext graphicsContextMainCanvas;
    private double widthMainCanvas;
    private double heightMainCanvas;

    // Sub Main Canvas Variables
    @FXML public Canvas subMainCanvas;
    private GraphicsContext graphicsContextSubMainCanvas;

    // Menu Point Of View Variables
    @FXML public TextField sceneFileTextField;
    @FXML public TextField heuristicTextField;
    @FXML public VBox pointOfViewVBox;
    @FXML public DoubleTextField positionXTextField;
    @FXML public DoubleTextField positionYTextField;
    @FXML public DoubleTextField viewAngleTextfield;
    @FXML public Slider viewAngleSlider;
    @FXML public Button showViewButton;
    @FXML public DoubleTextField rotatorTextfield;
    @FXML public Button rotatorDial;
    @FXML public Button rotatorHandle;
    private final StringProperty sceneFileSelected = new SimpleStringProperty();
    private final StringProperty heuristicSelected = new SimpleStringProperty("Standard");
    //private final FocusProperty focusProperty = new FocusProperty();
    private final DoubleProperty rotation = new SimpleDoubleProperty();

    // Painter Canvas Variables
    @FXML public Canvas painterCanvas;
    private GraphicsContext graphicsContextPainterCanvas;


    // MenuBar Handler
    @FXML
    public void handleClickOnAboutMaySee() throws Exception {
        Stage aboutMaySeeStage = new Stage();
        aboutMaySeeStage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(new File("src/main/resources/fxml/AboutMaySeeView.fxml").toURI().toURL());
        Parent root = fxmlLoader.load();
        ((AboutMaySeeController) fxmlLoader.getController()).setStage(aboutMaySeeStage);
        Scene scene = new Scene(root);

        aboutMaySeeStage.setTitle("About MaySee");
        aboutMaySeeStage.setScene(scene);
        aboutMaySeeStage.setResizable(false);
        aboutMaySeeStage.showAndWait();
    }

    @FXML
    public void handleClickOnGitHubRepository() throws IOException, URISyntaxException {
        Desktop.getDesktop().browse(new URL("https://github.com/RomeoXXIV/SDD2_Project_2021-2022").toURI());
    }

    @FXML
    public void handleClickOnQuitMaySee() {
        this.stage.close();
    }

    @FXML
    public void handleClickOnOpen() {
        FileChooser fileChooser = new FileChooser();
        this.sceneFile = fileChooser.showOpenDialog(stage);
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenEllipsesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesLarge.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenEllipsesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesMedium.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenEllipsesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesSmall.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenFirstOctangle() {
        this.sceneFile = new File("src/main/resources/scenes/first/octangle.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenFirstOctogone() {
        this.sceneFile = new File("src/main/resources/scenes/first/octogone.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomHuge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomHuge.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomLarge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomLarge.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomMedium() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomMedium.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomSmall() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomSmall.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesHuge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesHuge.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesLarge.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesMedium.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesSmall.txt");
        this.updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnStandard() {
        HeuristicSelector oldHeuristicSelector = this.heuristicSelector;
        this.heuristicSelector = new StandardHeuristic();
        this.heuristicSelected.set("Standard");
        if (this.sceneFile != null && this.sceneReader.isSceneFile()
                && !oldHeuristicSelector.getClass().equals(this.heuristicSelector.getClass())) {
            System.out.println("Works ! But need to fix BSP Tree");
            //this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        }
    }

    @FXML
    public void handleClickOnRandom() {
        HeuristicSelector oldHeuristicSelector = this.heuristicSelector;
        this.heuristicSelector = new RandomHeuristic();
        this.heuristicSelected.set("Random");
        if (this.sceneFile != null && this.sceneReader.isSceneFile()
                && !oldHeuristicSelector.getClass().equals(this.heuristicSelector.getClass())) {
            System.out.println("Works ! But need to fix BSP Tree");
            //this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        }
    }

    @FXML
    public void handleClickOnTWBN() {
        HeuristicSelector oldHeuristicSelector = this.heuristicSelector;
        this.heuristicSelector = new TWNBHeuristic();
        this.heuristicSelected.set("TWBH");
        if (this.sceneFile != null && this.sceneReader.isSceneFile()
                && !oldHeuristicSelector.getClass().equals(this.heuristicSelector.getClass())) {
            System.out.println("Works ! But need to fix BSP Tree");
            //this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        }
    }

    @FXML
    public void handleClickOnOptimizedRandom() {
        HeuristicSelector oldHeuristicSelector = this.heuristicSelector;
        this.heuristicSelector = new OptimizedRandomHeuristic();
        this.heuristicSelected.set("Optimized Random");
        if (this.sceneFile != null && this.sceneReader.isSceneFile()
                && !oldHeuristicSelector.getClass().equals(this.heuristicSelector.getClass())) {
            System.out.println("Works ! But need to fix BSP Tree");
            //this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        }
    }

    private void updateContentAppCenterHBox() {
        this.sceneReader = new SceneReader(this.sceneFile);
        if (this.sceneReader.isSceneFile()) {
            this.sceneFileSelected.set(sceneFile.getName());
            this.pointOfViewVBox.setDisable(false);
            String positionXPromptText = "-" + (double) this.sceneReader.getxAxisLimit() + " to " + (double) this.sceneReader.getxAxisLimit();
            String positionYPromptText = "-" + (double) this.sceneReader.getyAxisLimit() + " to " + (double) this.sceneReader.getyAxisLimit();
            this.positionXTextField.setPromptText(positionXPromptText);
            this.positionYTextField.setPromptText(positionYPromptText);
            this.drawSceneOnMainCanvas();
            // TODO : fix BSP Tree
            // this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        }
    }


    // Main Canvas handler
    @FXML
    public void handleScrollOnMainCanvas(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            scrollEvent.consume();
            double zoomFactor = 1.1;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2. - zoomFactor;
            }
            mainCanvas.setScaleX(mainCanvas.getScaleX() * zoomFactor);
            subMainCanvas.setScaleX(subMainCanvas.getScaleX() * zoomFactor);
            mainCanvas.setScaleY(mainCanvas.getScaleY() * zoomFactor);
            subMainCanvas.setScaleY(subMainCanvas.getScaleY() * zoomFactor);
        }
    }

    public void setStartTextMainCanvas(String text) {
        this.graphicsContextMainCanvas.setFill(Color.gray(0.33));
        this.graphicsContextMainCanvas.setTextAlign(TextAlignment.CENTER);
        this.graphicsContextMainCanvas.setTextBaseline(VPos.CENTER);
        this.graphicsContextMainCanvas.fillText(text, Math.round(mainCanvas.getWidth() / 2), Math.round(mainCanvas.getHeight() / 2));
    }

    private void drawSceneOnMainCanvas() {
        this.graphicsContextMainCanvas.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        this.widthMainCanvas = this.sceneReader.getxAxisLimit() * 2.5;
        this.heightMainCanvas = this.sceneReader.getyAxisLimit() * 2.5;
        this.mainCanvas.setWidth(widthMainCanvas);
        this.mainCanvas.setHeight(heightMainCanvas);
        this.subMainCanvas.setWidth(widthMainCanvas);
        this.subMainCanvas.setHeight(heightMainCanvas);
        this.drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    private void drawSegmentsOnMainCanvas(ArrayList<Segment> segments) {
        for (Segment segment : segments) {
            this.drawSegmentOnMainCanvas(segment);
        }
    }

    private void drawSegmentOnMainCanvas(Segment segment) {
        Color color = PainterInteractive.awtColorToPaintColor(segment.getColor());
        double x1 = segment.getA().getX() + this.widthMainCanvas / 2.;
        double y1 = segment.getA().getY() + this.heightMainCanvas / 2.;
        double x2 = segment.getB().getX() + this.widthMainCanvas / 2.;
        double y2 = segment.getB().getY() + this.heightMainCanvas / 2.;
        this.graphicsContextMainCanvas.setStroke(color);
        this.graphicsContextMainCanvas.strokeLine(x1, y1, x2, y2);
    }

    // Sub Main Canvas handler
    @FXML
    public void handleScrollOnSubMainCanvas(ScrollEvent scrollEvent) {
        this.handleScrollOnMainCanvas(scrollEvent);
    }

    private void drawPointOfViewOnSubMainCanvas() {
        if (!this.positionXTextField.getText().equals("")
            && !this.positionYTextField.getText().equals("")) {
            this.graphicsContextSubMainCanvas.clearRect(0, 0, subMainCanvas.getWidth(), subMainCanvas.getHeight());
            double x = Double.parseDouble(this.positionXTextField.getText()) + this.subMainCanvas.getWidth() / 2.;
            double y = Double.parseDouble(this.positionYTextField.getText()) + this.subMainCanvas.getHeight() / 2.;
            double length = 20.;
            double viewAngle = Double.parseDouble(this.viewAngleTextfield.getText());
            double rotateAngle = Double.parseDouble(this.rotatorTextfield.getText());
            Rotate r = new Rotate(rotateAngle, x, y);
            this.graphicsContextSubMainCanvas.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
            this.graphicsContextSubMainCanvas.strokeLine(x, y,
                    x + length * Math.cos(Math.toRadians(-viewAngle / 2)),
                    y + length * Math.sin(Math.toRadians(-viewAngle / 2)));
            this.graphicsContextSubMainCanvas.strokeLine(x, y,
                    x + length * Math.cos(Math.toRadians(viewAngle / 2)),
                    y + length * Math.sin(Math.toRadians(viewAngle / 2)));

            this.graphicsContextSubMainCanvas.setFill(Color.TRANSPARENT);
            this.graphicsContextSubMainCanvas.fillArc(x - 20, y - 20, 40, 40, - viewAngle / 2, viewAngle, ArcType.ROUND);

            this.graphicsContextSubMainCanvas.setFill(Color.BLACK);
            this.graphicsContextSubMainCanvas.fillOval(x + 8, y - 5, 10, 10);
            if (this.showViewButton.isDisable()) this.showViewButton.setDisable(false);
        }
    }


    // Menu Point Of View Handler
    public double boundingValue(double value, double min, double max) {
        if (value < min) return min;
        return Math.min(value, max);
    }

    private double round(double value, int roundingFactor) {
        double doubleRounded = (double)Math.round(value * (double)roundingFactor);
        return doubleRounded / (double)roundingFactor;
    }

    // Position XY Section
    @FXML
    public void handlePositionXTextFieldAction(ActionEvent actionEvent) {
        this.pointOfViewVBox.requestFocus();
    }

    public void handlePositionXTextFieldAction() {
        double value = Double.parseDouble(this.positionXTextField.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, - this.sceneReader.getxAxisLimit(), this.sceneReader.getxAxisLimit());
        this.positionXTextField.setText(String.valueOf(rounded));
        this.drawPointOfViewOnSubMainCanvas();
    }

    @FXML
    public void handlePositionYTextFieldAction(ActionEvent actionEvent) {
        this.pointOfViewVBox.requestFocus();
    }

    public void handlePositionYTextFieldAction() {
        double value = Double.parseDouble(this.positionYTextField.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, - this.sceneReader.getyAxisLimit(), this.sceneReader.getyAxisLimit());
        this.positionYTextField.setText(String.valueOf(rounded));
        this.drawPointOfViewOnSubMainCanvas();
    }

    // View Angle Section
    @FXML
    public void handleViewAngleTextfieldAction(ActionEvent actionEvent) {
        this.pointOfViewVBox.requestFocus();
    }

    public void handleViewAngleTextfieldAction() {
        double value = Double.parseDouble(this.viewAngleTextfield.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, 0, 180);
        this.translate(rounded);
    }

    @FXML
    public void handleViewAngleSliderMousePressed(MouseEvent mouseEvent) {
        this.handleViewAngleSliderMouseDragged(mouseEvent);
    }

    @FXML
    public void handleViewAngleSliderMouseDragged(MouseEvent mouseEvent) {
        this.translate(this.viewAngleSlider.getValue());
    }

    private void translate(double value) {
        double rounded = this.round(value, 100);
        this.viewAngleSlider.setValue(rounded);
        this.viewAngleTextfield.setText(Double.toString(this.round(rounded, 100)));
        this.drawPointOfViewOnSubMainCanvas();
    }

    // Rotator Section
    @FXML
    public void handleRotatorTextfieldAction(ActionEvent actionEvent) {
        this.pointOfViewVBox.requestFocus();
    }

    public void handleRotatorTextfieldAction() {
        double value = Double.parseDouble(this.rotatorTextfield.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, 0, 360);
        this.rotate(rounded);
    }

    @FXML
    public void handleRotatorMousePressed(MouseEvent mouseEvent) {
        this.handleRotatorMouseDragged(mouseEvent);
    }

    @FXML
    public void handleRotatorMouseDragged(MouseEvent mouseEvent) {
        Parent p = this.rotatorDial.getParent();
        Bounds b = this.rotatorDial.getLayoutBounds();
        double centerX = b.getMinX() + b.getWidth() / 2.0D;
        double centerY = b.getMinY() + b.getHeight() / 2.0D;
        Point2D center = p.localToParent(centerX, centerY);
        Point2D mouse = p.localToParent(mouseEvent.getX(), mouseEvent.getY());
        double deltaX = mouse.getX() - center.getX();
        double deltaY = mouse.getY() - center.getY();
        double radians = Math.atan2(deltaY, deltaX);
        this.rotate(Math.toDegrees(radians));
    }

    private void rotate(Double value) {
        double rounded = this.round(value, 100);
        this.rotation.set(rounded);
        this.rotatorHandle.setRotate(rounded);
        if(rounded < 0) rounded += 360;
        this.rotatorTextfield.setText(Double.toString(this.round(rounded, 100)));
        drawPointOfViewOnSubMainCanvas();
    }

    // Button Show View
    @FXML
    public void handleShowViewButtonAction(ActionEvent actionEvent) {
        this.drawOnPainterCanvas();
    }


    // Painter Canvas handler
    @FXML
    public void handleScrollOnPainterCanvas(ScrollEvent scrollEvent) {
        if (scrollEvent.isControlDown()) {
            scrollEvent.consume();
            double zoomFactor = 1.1;
            double deltaY = scrollEvent.getDeltaY();
            if (deltaY < 0) {
                zoomFactor = 2. - zoomFactor;
            }
            painterCanvas.setScaleX(painterCanvas.getScaleX() * zoomFactor);
            painterCanvas.setScaleY(painterCanvas.getScaleY() * zoomFactor);
        }
    }

    private void drawOnPainterCanvas() {
        this.drawingPreparation();
        this.drawSegmentsPainterCanvas();
    }

    private void drawingPreparation() {
        // TODO to verify
        double widthPainterCanvas = painterCanvas.getWidth();
        double heightMainCanvas = painterCanvas.getHeight();
        this.graphicsContextPainterCanvas.clearRect(0, 0, widthPainterCanvas, heightMainCanvas);
        this.painterCanvas.setWidth(widthPainterCanvas); // Utilité ?
        this.painterCanvas.setHeight(heightMainCanvas); // Utilité ?
        this.bspTree = new BSPTree(this.sceneReader.getSegments(), heuristicSelector);
        double x = Double.parseDouble(positionXTextField.getText());
        double y = Double.parseDouble(positionYTextField.getText());
        double viewAngle = Double.parseDouble(viewAngleTextfield.getText());
        double rotatorAngle = Double.parseDouble(rotatorTextfield.getText());
        this.viewPoint = new ViewPoint(new Point(x, y), viewAngle, rotatorAngle);
        this.painterInteractive = new PainterInteractive(this.painterCanvas);
    }

    private void drawSegmentsPainterCanvas() {
        new Painter(this.bspTree, this.viewPoint, this.painterInteractive);
    }


    // initialize + setter
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
        this.graphicsContextMainCanvas = mainCanvas.getGraphicsContext2D();
        this.setStartTextMainCanvas("Select a scene file to get started");
        this.graphicsContextSubMainCanvas = subMainCanvas.getGraphicsContext2D();
        this.graphicsContextPainterCanvas = painterCanvas.getGraphicsContext2D();
        this.sceneFileTextField.textProperty().bind(sceneFileSelected);
        this.heuristicTextField.textProperty().bind(heuristicSelected);
        this.positionXTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) // when focus is lost
                this.handlePositionXTextFieldAction();
        });
        this.positionYTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) // when focus is lost
                this.handlePositionYTextFieldAction();
        });
        this.viewAngleTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) // when focus is lost
                this.handleViewAngleTextfieldAction();
        });
        this.rotatorTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) // when focus is lost
                this.handleRotatorTextfieldAction();
        });
        this.pointOfViewVBox.setDisable(true);
        this.showViewButton.setDisable(true);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}