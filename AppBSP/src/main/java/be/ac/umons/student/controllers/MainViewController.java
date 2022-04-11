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
import javafx.scene.input.ScrollEvent;
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
    private double widthMainCanvas;
    private double heightMainCanvas;

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
        drawSceneOnMainCanvas();
        /*this.bspTree = new BSPTree(this.sceneReader.getSegments(), this.heuristicSelector);
        new Painter(bspTree, painterInteractive);*/
    }

    @FXML
    public void handleClickOnOpenEllipsesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesLarge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenEllipsesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesMedium.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenEllipsesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/ellipses/ellipsesSmall.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenFirstOctangle() {
        this.sceneFile = new File("src/main/resources/scenes/first/octangle.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenFirstOctogone() {
        this.sceneFile = new File("src/main/resources/scenes/first/octogone.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomHuge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomHuge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomLarge() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomLarge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomMedium() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomMedium.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRandomSmall() {
        this.sceneFile = new File("src/main/resources/scenes/random/randomSmall.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesHuge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesHuge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesLarge() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesLarge.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesMedium() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesMedium.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
    }

    @FXML
    public void handleClickOnOpenRectanglesSmall() {
        this.sceneFile = new File("src/main/resources/scenes/rectangles/rectanglesSmall.txt");
        this.sceneReader = new SceneReader(this.sceneFile);
        drawSceneOnMainCanvas();
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

    public void drawSceneOnMainCanvas() {
        this.graphicsContext.clearRect(0, 0, mainCanvas.getWidth(), mainCanvas.getHeight());
        this.widthMainCanvas = this.sceneReader.getxAxisLimit() * 2.5;
        this.heightMainCanvas = this.sceneReader.getyAxisLimit() * 2.5;
        mainCanvas.setWidth(widthMainCanvas);
        mainCanvas.setHeight(heightMainCanvas);
        drawSegmentsOnMainCanvas(this.sceneReader.getSegments());
    }

    public void drawSegmentsOnMainCanvas(ArrayList<Segment> segments) {
        for (Segment segment : segments) {
            drawSegmentOnMainCanvas(segment);
        }
    }

    public void drawSegmentOnMainCanvas(Segment segment) {
        Color color = awtColorToPaintColor(segment.getColor());
        double x1 = segment.getA().getX() + this.widthMainCanvas / 2.;
        double y1 = segment.getA().getY() + this.heightMainCanvas / 2.;
        double x2 = segment.getB().getX() + this.widthMainCanvas / 2.;
        double y2 = segment.getB().getY() + this.heightMainCanvas / 2.;
        this.graphicsContext.setStroke(color);
        this.graphicsContext.strokeLine(x1, y1, x2, y2);
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