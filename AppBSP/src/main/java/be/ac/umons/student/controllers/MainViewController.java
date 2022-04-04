package be.ac.umons.student.controllers;

import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.heuristics.*;
import be.ac.umons.student.models.painter.Paintable;
import be.ac.umons.student.models.painter.Painter;
import be.ac.umons.student.models.painter.PainterInteractive;
import be.ac.umons.student.utils.SceneReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    private final Paintable painterInteractive = new PainterInteractive();

    private HeuristicSelector heuristicSelector = new StandardHeuristic();;

    private File sceneFile;
    private SceneReader sceneReader;
    private BSPTree bspTree;

    private Stage stage;
    private GraphicsContext graphicsContext;
    private int translateX;
    private int translateY;

    @FXML public Canvas mainCanvas;

    @FXML
    public void handleClickOnQuitMaySee() {
        stage.close();
    }

    @FXML
    public void handleClickOnOpen() {
        FileChooser fileChooser = new FileChooser();
        this.sceneFile = fileChooser.showOpenDialog(stage);
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
        /*this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        new Painter(bspTree, painterInteractive);*/
    }

    @FXML
    public void handleClickOnOpenEllipsesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesLarge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenEllipsesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesMedium.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenEllipsesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesSmall.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenFirstOctangle() {
        this.sceneFile = new File("src/main/resources/scenes/first/octangle.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenFirstOctogone() {
        this.sceneFile = new File("src/main/resources/scenes/first/octogone.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRandomHuge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomHuge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRandomLarge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomLarge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRandomMedium() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomMedium.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRandomSmall() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomSmall.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRectanglesHuge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesHuge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRectanglesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesLarge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRectanglesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesMedium.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnOpenRectanglesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesSmall.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        this.translateX = this.sceneReader.getxAxisLimit();
        this.translateY = this.sceneReader.getyAxisLimit();
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    @FXML
    public void handleClickOnStandard() {
        this.heuristicSelector = new StandardHeuristic();
    }

    @FXML
    public void handleClickOnRandom() {
        this.heuristicSelector = new RandomHeuristic();
    }

    @FXML
    public void handleClickOnTWBN() {
        this.heuristicSelector = new TWNBHeuristic();
    }

    @FXML
    public void handleClickOnOptimizedRandom() {
        this.heuristicSelector = new OptimizedRandomHeuristic();
    }

    public void drawSegmentsOnMainCanvas(ArrayList<Segment> segments) {
        this.graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        for (Segment segment : segments) {
            drawSegmentOnMainCanvas(segment);
        }
    }

    public void drawSegmentOnMainCanvas(Segment segment) {
        Color color = awtColorToPaintColor(segment.getColor());
        double x1 = segment.getA().getX();
        double y1 = segment.getA().getY();
        double x2 = segment.getB().getX();
        double y2 = segment.getB().getY();
        this.graphicsContext.setStroke(color);
        this.graphicsContext.strokeLine(x1+translateX, y1+translateY, x2+translateX, y2+translateY);
    }

    public static Color awtColorToPaintColor(java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        double opacity = color.getAlpha()/255.0;
        return Color.rgb(red, green, blue, opacity);
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
        this.graphicsContext = mainCanvas.getGraphicsContext2D();
        /*this.mainCanvas.setTranslateX(this.sceneReader.getxAxisLimit());
        this.mainCanvas.setTranslateY(this.sceneReader.getxAxisLimit());*/
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }
}