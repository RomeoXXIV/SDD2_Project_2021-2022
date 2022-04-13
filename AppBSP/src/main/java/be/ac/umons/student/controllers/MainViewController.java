package be.ac.umons.student.controllers;

import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.heuristics.*;
import be.ac.umons.student.models.painter.Paintable;
import be.ac.umons.student.models.painter.PainterInteractive;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML public VBox rootVBox;
    @FXML public VBox inSplitPaneVBox;
    private Stage stage;

    // Not Yet Used
    private final Paintable painterInteractive = new PainterInteractive();
    private HeuristicSelector heuristicSelector = new StandardHeuristic();
    private BSPTree bspTree;

    // MenuBar Variables
    private File sceneFile;
    private SceneReader sceneReader;

    // Canvas Variables
    @FXML public ScrollPane scrollPane;
    @FXML public Canvas mainCanvas;
    private GraphicsContext graphicsContext;
    private double widthMainCanvas;
    private double heightMainCanvas;

    // Menu Point Of View Variables
    @FXML public TextField sceneFileTextField;
    @FXML public TextField heuristicTextField;
    @FXML public VBox pointOfViewVBox;
    @FXML public TextField viewAngleTextfield;
    @FXML public Slider viewAngleSlider;
    @FXML public Button buttonShowView;
    @FXML public DoubleTextField rotatorTextfield;
    @FXML public Button rotatorDial;
    @FXML public Button rotatorHandle;
    private final StringProperty sceneFileSelected = new SimpleStringProperty();
    private final StringProperty heuristicSelected = new SimpleStringProperty("Standard");
    private final DoubleProperty translation = new SimpleDoubleProperty();
    private final DoubleProperty rotation = new SimpleDoubleProperty();


    // MenuBar Handler
    @FXML
    public void handleClickOnQuitMaySee() {
        stage.close();
    }

    @FXML
    public void handleClickOnOpen() {
        FileChooser fileChooser = new FileChooser();
        this.sceneFile = fileChooser.showOpenDialog(stage);
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
        /*this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        new Painter(bspTree, painterInteractive);*/
    }

    @FXML
    public void handleClickOnOpenEllipsesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesLarge.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenEllipsesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesMedium.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenEllipsesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesSmall.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenFirstOctangle() {
        this.sceneFile = new File("src/main/resources/scenes/first/octangle.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenFirstOctogone() {
        this.sceneFile = new File("src/main/resources/scenes/first/octogone.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomHuge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomHuge.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomLarge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomLarge.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomMedium() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomMedium.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomSmall() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomSmall.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesHuge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesHuge.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesLarge.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesMedium.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesSmall.txt");
        this.sceneFileSelected.set(sceneFile.getName());
        this.pointOfViewVBox.setDisable(false);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.drawSceneOnMainCanvas();
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

    // Canvas handler
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
            mainCanvas.setScaleY(mainCanvas.getScaleY() * zoomFactor);
        }
    }

    private void drawSceneOnMainCanvas() {
        this.graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        this.widthMainCanvas = this.sceneReader.getxAxisLimit() * 2.5;
        this.heightMainCanvas = this.sceneReader.getyAxisLimit() * 2.5;
        mainCanvas.setWidth(widthMainCanvas);
        mainCanvas.setHeight(heightMainCanvas);
        this.drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    private void drawSegmentsOnMainCanvas(ArrayList<Segment> segments) {
        for (Segment segment : segments) {
            this.drawSegmentOnMainCanvas(segment);
        }
    }

    private void drawSegmentOnMainCanvas(Segment segment) {
        Color color = awtColorToPaintColor(segment.getColor());
        double x1 = segment.getA().getX() + this.widthMainCanvas / 2.;
        double y1 = segment.getA().getY() + this.heightMainCanvas / 2.;
        double x2 = segment.getB().getX() + this.widthMainCanvas / 2.;
        double y2 = segment.getB().getY() + this.heightMainCanvas / 2.;
        this.graphicsContext.setStroke(color);
        this.graphicsContext.strokeLine(x1, y1, x2, y2);
    }

    private static Color awtColorToPaintColor(java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        double opacity = color.getAlpha()/255.0;
        return Color.rgb(red, green, blue, opacity);
    }

    // Menu Point Of View Handler
    //View Angle Section
    @FXML
    public void handleViewAngleTextfieldAction(ActionEvent actionEvent) {
        double value = Double.parseDouble(this.viewAngleTextfield.getText());
        double rounded = this.round(value, 100);
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
        this.viewAngleTextfield.setText(Double.toString(this.viewAngleSlider.getValue()));
    }

    private void translate(double value) {
        double rounded = this.round(value, 100);
        this.viewAngleSlider.setValue(rounded);
        this.viewAngleTextfield.setText(Double.toString(this.round(rounded, 100)));
    }

    // Rotator Section
    @FXML
    public void handleRotatorTextfieldAction(ActionEvent actionEvent) {
        double value = Double.parseDouble(this.rotatorTextfield.getText());
        double rounded = this.round(value, 100);
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
    }

    private double round(double value, int roundingFactor) {
        double doubleRounded = (double)Math.round(value * (double)roundingFactor);
        return doubleRounded / (double)roundingFactor;
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
        this.graphicsContext = mainCanvas.getGraphicsContext2D();
        this.sceneFileTextField.textProperty().bind(sceneFileSelected);
        this.heuristicTextField.textProperty().bind(heuristicSelected);
        this.pointOfViewVBox.setDisable(true);
        this.buttonShowView.setDisable(true);
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}