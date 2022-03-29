package be.ac.umons.student;


import be.ac.umons.student.models.*;
import be.ac.umons.student.models.heuristics.*;
import be.ac.umons.student.utils.SceneReader;

import javax.naming.directory.InvalidAttributesException;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;


public class TestConsole {

    private final static HeuristicSelector randomHeuristic = new RandomHeuristic();
    private final static HeuristicSelector standardHeuristic = new StandardHeuristic();
    private final static HeuristicSelector twnbHeuristic = new TWNBHeuristic();
    private final static HeuristicSelector optimizedRandomHeuristic = new OptimizedRandomHeuristic();

    private final static String pathEllipsesLarge = "../Scenes/ellipses/ellipsesLarge.txt";
    private final static String pathEllipsesMedium = "../Scenes/ellipses/ellipsesMedium.txt";
    private final static String pathEllipsesSmall = "../Scenes/ellipses/ellipsesSmall.txt";

    private final static String pathFirstOctangle = "../Scenes/first/octangle.txt";
    private final static String pathFirstOctogone = "../Scenes/first/octogone.txt";

    private final static String pathRandomHuge = "../Scenes/random/randomHuge.txt";
    private final static String pathRandomLarge = "../Scenes/random/randomLarge.txt";
    private final static String pathRandomMedium = "../Scenes/random/randomMedium.txt";
    private final static String pathRandomSmall = "../Scenes/random/randomSmall.txt";
    private final static String pathRectangle = "../Scenes/rectangles/rectangles.txt";

    private final static String pathRectangleHuge = "../Scenes/rectangles/rectanglesHuge.txt";
    private final static String pathRectangleLarge = "../Scenes/rectangles/rectanglesLarge.txt";
    private final static String pathRectangleMedium = "../Scenes/rectangles/rectanglesMedium.txt";
    private final static String pathRectangleSmall = "../Scenes/rectangles/rectanglesSmall.txt";


    public static void printDetails(ArrayList<Segment> segmentList, HeuristicSelector heuristic)
    {
        Instant start = Instant.now();
        BSPTree tree = new BSPTree(segmentList, heuristic);
        Instant stop = Instant.now();
        Duration randomTimeElapsed = Duration.between(start, stop);
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
        System.out.printf("| %-20s | %8s | %6s | %14s | %15s |\n","Heuristic","Size","Height","Build CPU Time","Painter CPU Time");
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
        System.out.printf("| %-20s | %8d | %6d | %14d | %15s |\n",heuristic.toString(), tree.size(), tree.height(), randomTimeElapsed.toMillis(),"Painter CPU Time");
        System.out.println("+----------------------+----------+--------+----------------+------------------+");

    }

    public static void main(String[] args) {
        SceneReader sceneReader = new SceneReader(pathEllipsesLarge);
        ArrayList<Segment> segmentArrayList = sceneReader.getSegments();
        System.out.println(segmentArrayList.size());
        //printDetails(segmentArrayList, randomHeuristic);
        printDetails(segmentArrayList, standardHeuristic);
    }
}
