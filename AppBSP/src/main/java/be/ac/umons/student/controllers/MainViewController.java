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
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

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
    @FXML public TextField positionXTextField;
    @FXML public TextField positionYTextField;
    @FXML public TextField viewAngleTextfield;
    @FXML public Slider viewAngleSlider;
    @FXML public Button showViewButton;
    @FXML public DoubleTextField rotatorTextfield;
    @FXML public Button rotatorDial;
    @FXML public Button rotatorHandle;
    private final StringProperty sceneFileSelected = new SimpleStringProperty();
    private final StringProperty heuristicSelected = new SimpleStringProperty("Standard");
    private final DoubleProperty rotation = new SimpleDoubleProperty();

    // Painter Canvas Variables
    @FXML public Canvas painterCanvas;
    private GraphicsContext graphicsContextPainterCanvas;


    // MenuBar Handler
    @FXML
    public void handleClickOnQuitMaySee() {
        stage.close();
    }

    @FXML
    public void handleClickOnOpen() {
        FileChooser fileChooser = new FileChooser();
        this.sceneFile = fileChooser.showOpenDialog(stage);
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenEllipsesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesLarge.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenEllipsesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesMedium.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenEllipsesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesSmall.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenFirstOctangle() {
        this.sceneFile = new File("src/main/resources/scenes/first/octangle.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenFirstOctogone() {
        this.sceneFile = new File("src/main/resources/scenes/first/octogone.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomHuge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomHuge.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomLarge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomLarge.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomMedium() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomMedium.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRandomSmall() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomSmall.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesHuge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesHuge.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesLarge.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesMedium.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnOpenRectanglesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesSmall.txt");
        updateContentAppCenterHBox();
    }

    @FXML
    public void handleClickOnStandard() {
        this.heuristicSelector = new StandardHeuristic();
        this.heuristicSelected.set("Standard");
    }

    @FXML
    public void handleClickOnRandom() {
        this.heuristicSelector = new RandomHeuristic();
        this.heuristicSelected.set("Random");
    }

    @FXML
    public void handleClickOnTWBN() {
        this.heuristicSelector = new TWNBHeuristic();
        this.heuristicSelected.set("TWBH");
    }

    @FXML
    public void handleClickOnOptimizedRandom() {
        this.heuristicSelector = new OptimizedRandomHeuristic();
        this.heuristicSelected.set("Optimized Random");
    }

    private void updateContentAppCenterHBox() {
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        String positionXPromptText = "-" + (double) this.sceneReader.getxAxisLimit() + " to " + (double) this.sceneReader.getxAxisLimit();
        String positionYPromptText = "-" + (double) this.sceneReader.getyAxisLimit() + " to " + (double) this.sceneReader.getyAxisLimit();
        this.positionXTextField.setPromptText(positionXPromptText);
        this.positionYTextField.setPromptText(positionYPromptText);
        this.drawSceneOnMainCanvas();
        /*this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        new Painter(bspTree, painterInteractive);*/
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
    public void handlePositionXTextFieldAction(ActionEvent actionEvent) {
        double value = Double.parseDouble(this.positionXTextField.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, - this.sceneReader.getxAxisLimit(), this.sceneReader.getxAxisLimit());
        this.positionXTextField.setText(String.valueOf(rounded));
        this.drawPointOfViewOnSubMainCanvas();
        actionEvent.consume();
    }

    public void handlePositionYTextFieldAction(ActionEvent actionEvent) {
        double value = Double.parseDouble(this.positionYTextField.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, - this.sceneReader.getyAxisLimit(), this.sceneReader.getyAxisLimit());
        this.positionYTextField.setText(String.valueOf(rounded));
        this.drawPointOfViewOnSubMainCanvas();
        actionEvent.consume();
    }

    // View Angle Section
    @FXML
    public void handleViewAngleTextfieldAction(ActionEvent actionEvent) {
        double value = Double.parseDouble(this.viewAngleTextfield.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, 0, 180);
        this.translate(rounded);
        this.viewAngleTextfield.selectAll();
        actionEvent.consume();
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
        double value = Double.parseDouble(this.rotatorTextfield.getText());
        double rounded = this.round(value, 100);
        rounded = this.boundingValue(rounded, 0, 359);
        this.rotate(rounded);
        this.rotatorTextfield.selectAll();
        actionEvent.consume();
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
        this.graphicsContextSubMainCanvas = subMainCanvas.getGraphicsContext2D();
        this.graphicsContextPainterCanvas = painterCanvas.getGraphicsContext2D();
        this.sceneFileTextField.textProperty().bind(sceneFileSelected);
        this.heuristicTextField.textProperty().bind(heuristicSelected);
        this.pointOfViewVBox.setDisable(true);
        this.showViewButton.setDisable(true);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}