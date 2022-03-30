package be.ac.umons.student;

import be.ac.umons.student.models.*;
import be.ac.umons.student.models.heuristics.*;
import be.ac.umons.student.models.painter.Paintable;
import be.ac.umons.student.models.painter.Painter;
import be.ac.umons.student.models.painter.PainterConsole;
import be.ac.umons.student.utils.SceneReader;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class TestConsole {

    private final static HeuristicSelector randomHeuristic = new RandomHeuristic();
    private final static HeuristicSelector standardHeuristic = new StandardHeuristic();
    private final static HeuristicSelector twnbHeuristic = new TWNBHeuristic();
    private final static HeuristicSelector optimizedRandomHeuristic = new OptimizedRandomHeuristic();

    private final static Paintable painterConsole = new PainterConsole();

    private final static String pathEllipsesLarge = "../Scenes/ellipses/ellipsesLarge.txt";
    private final static String pathEllipsesMedium = "../Scenes/ellipses/ellipsesMedium.txt";
    private final static String pathEllipsesSmall = "../Scenes/ellipses/ellipsesSmall.txt";

    private final static String pathFirstOctangle = "../Scenes/first/octangle.txt";
    private final static String pathFirstOctogone = "../Scenes/first/octogone.txt";

    private final static String pathRandomHuge = "../Scenes/random/randomHuge.txt";
    private final static String pathRandomLarge = "../Scenes/random/randomLarge.txt";
    private final static String pathRandomMedium = "../Scenes/random/randomMedium.txt";
    private final static String pathRandomSmall = "../Scenes/random/randomSmall.txt";

    private final static String pathRectangleHuge = "../Scenes/rectangles/rectanglesHuge.txt";
    private final static String pathRectangleLarge = "../Scenes/rectangles/rectanglesLarge.txt";
    private final static String pathRectangleMedium = "../Scenes/rectangles/rectanglesMedium.txt";
    private final static String pathRectangleSmall = "../Scenes/rectangles/rectanglesSmall.txt";

    public static void printDetails(ArrayList<Segment> segmentList, HeuristicSelector heuristic) {
        Instant buildStart = Instant.now();
        BSPTree bspTree = new BSPTree(segmentList, heuristic);
        Instant buildStop = Instant.now();
        Duration randomElapsedTimeForBuilding = Duration.between(buildStart, buildStop);
        Instant painterStart = Instant.now();
        new Painter(bspTree, painterConsole);
        Instant painterStop = Instant.now();
        Duration randomElapsedTimeForPainter = Duration.between(painterStart, painterStop);
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
        System.out.printf("| %-20s | %8s | %6s | %14s | %15s |\n","Heuristic","Size","Height","Build CPU Time","Painter CPU Time");
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
        System.out.printf("| %-20s | %8d | %6d | %11d ms | %13s ms |\n",heuristic.toString(), bspTree.size(),
                bspTree.height(), randomElapsedTimeForBuilding.toMillis(), randomElapsedTimeForPainter.toMillis());
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
    }

    public static void main(String[] args) {
        SceneReader sceneReader = new SceneReader(pathRandomHuge);
        System.out.println(sceneReader);
        ArrayList<Segment> segmentArrayList = sceneReader.getSegments();
        printDetails(segmentArrayList, randomHeuristic);
        printDetails(segmentArrayList, standardHeuristic);
        //printDetails(segmentArrayList, twnbHeuristic);
        //printDetails(segmentArrayList, optimizedRandomHeuristic);
    }
}
