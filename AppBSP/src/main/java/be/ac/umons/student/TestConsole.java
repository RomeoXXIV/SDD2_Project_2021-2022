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
import java.util.Objects;
import java.util.Scanner;

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

    public static String sceneChoice(){
        System.out.println("Voici les types de scenes que vous pouvez choisir:\n");
        return scenesInfos();
    }

    public static String scenesInfos(){
        System.out.println("1.Ellipses\n2.Rectangles\n3.Aleatoires\n4.First\n5.Autres\n");
        Scanner userInput = new Scanner(System.in);
        System.out.println("Veuillez entrer le chiffre du type de scene qui vous interesse.\n");
        System.out.println("En cas d'input invalide, le fichier randomHuge sera utilise.\n");
        String choice = userInput.nextLine();
        return sceneSizes(choice);
    }

    public static String sceneSizes(String choice){
        Scanner userInput = new Scanner(System.in);
        switch (choice){
            case "1":
                System.out.println("1.Small\n2.Medium\n3.Large\n");
                System.out.println("En cas d'input non valide, le fichier Small sera utilise.\n");
                String chosen = userInput.nextLine();
                return switchEllipse(chosen);
            case "2":
                System.out.println("1.Small\n2.Medium\n3.Large\n4.Huge\n");
                System.out.println("En cas d'input non valide, le fichier Small sera utilise.\n");
                String chosen2 = userInput.nextLine();
                return switchRectangle(chosen2);
            case "3":
                System.out.println("1.Small\n2.Medium\n3.Large\n4.Huge\n");
                System.out.println("En cas d'input non valide, le fichier Small sera utilise.\n");
                String chosen3 = userInput.nextLine();
                return switchRandom(chosen3);
            case "4":
                System.out.println("1.Octangle\n2.Octogone\n");
                System.out.println("En cas d'input non valide, le fichier Octangle sera utilise.\n");
                String chosen4 = userInput.nextLine();
                return switchFirst(chosen4);
            case "5":
                System.out.println("Veuillez ecrire le path vers le fichier exterieur que vous voulez utiliser.");
                String filePath = userInput.nextLine();
                return filePath;
            default:
                return pathRandomHuge;
        }
    }

    public static String switchEllipse(String choice){
        switch (choice){
            case "1":
                return pathEllipsesSmall;
            case "2":
                return pathEllipsesMedium;
            case "3":
                return pathEllipsesLarge;
            default:
                return pathEllipsesSmall;
        }
    }

    public static String switchRectangle(String  choice){
        switch (choice){
            case "1":
                return pathRectangleSmall;
            case "2":
                return pathRectangleMedium;
            case "3":
                return pathRectangleLarge;
            case "4":
                return pathRectangleHuge;
            default:
                return pathRectangleSmall;
        }
    }

    public static String switchRandom(String  choice){
        switch (choice){
            case "1":
                return pathRandomSmall;
            case "2":
                return pathRandomMedium;
            case "3":
                return pathRandomLarge;
            case "4":
                return pathRandomHuge;
            default:
                return pathRandomSmall;
        }
    }

    public static String switchFirst(String choice){
        switch (choice){
            case "1":
                return pathFirstOctangle;
            case "2":
                return pathFirstOctogone;
            default:
                return pathFirstOctangle;
        }
    }

    public static void consoleInterface(){
        String scene = sceneChoice();
        SceneReader sceneReader = new SceneReader(scene);
        System.out.println(sceneReader);
        ArrayList<Segment> segmentArrayList = sceneReader.getSegments();
        printDetails(segmentArrayList, randomHeuristic);
        printDetails(segmentArrayList, standardHeuristic);
        //printDetails(segmentArrayList, twnbHeuristic);
        //printDetails(segmentArrayList, optimizedRandomHeuristic);
        System.out.println("Voulez-vous recommencer?\n1.Oui\n2.Non\nEn cas d'input invalide, l'application s'arretera.");
        Scanner userChoice = new Scanner(System.in);
        if (Objects.equals(userChoice.nextLine(), "1")){
            consoleInterface();
        }
    }

    public static void main(String[] args) {
        System.out.println("Bienvenue dans le mode console de l'application.\nIci, vous pourrez choisir un fichier et l'utiliser pour creer un Arbre BSP et voir ses informations.\n");
        consoleInterface();
    }
}
