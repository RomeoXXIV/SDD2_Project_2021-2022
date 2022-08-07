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
import java.util.Scanner;

/**
 * Application console permetant de choisir un fichier scène, de créer l'arbre BSP correspondant grace aux différentes heuristiques et d'effectuer l'algorithme du
 * peintre sur ces arbres obtenus pour comparer ces heuristiques.
 * @author Romeo Ibraimovski (ux)
 * @author Maxime Nabli (code)
 */

public class TestConsole {

    private final static HeuristicSelector randomHeuristic = new RandomHeuristic();
    private final static HeuristicSelector standardHeuristic = new StandardHeuristic();
    private final static HeuristicSelector twnbHeuristic = new TWNBHeuristic();
    private final static HeuristicSelector optimizedRandomHeuristic = new OptimizedRandomHeuristic();
    private final static HeuristicSelector bonusHeuristic = new BonusHeuristic();

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

    /**
     * Imprime dans la consoles les détails de la construction d'un Arbre BSP avec l'Heuristique demandée. Donne
     * sa hauteur, son nombre de noeud et le temps en ms pour le construire et effectuer l'algorithme du peintre.
     *
     * @param segmentList la liste de segments utilisée pour créer l'Arbre BSP
     * @param heuristic l'heuristique choisie
     */
    public static void printDetails(ArrayList<Segment> segmentList, HeuristicSelector heuristic) {
        Instant buildStart = Instant.now();
        BSPTree bspTree = new BSPTree(segmentList, heuristic);
        Instant buildStop = Instant.now();
        Duration randomElapsedTimeForBuilding = Duration.between(buildStart, buildStop);
        Instant painterStart = Instant.now();
        new Painter(bspTree, painterConsole);
        Instant painterStop = Instant.now();
        Duration randomElapsedTimeForPainter = Duration.between(painterStart, painterStop);
        System.out.printf("| %-20s | %8d | %6d | %11d ms | %13s ms |\n",heuristic.toString(), bspTree.size(),
                bspTree.height(), randomElapsedTimeForBuilding.toMillis(), randomElapsedTimeForPainter.toMillis());
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
    }


    /**
     * Première étape du mode console, choix du type de scene. Le choix de l'utilisateur est récupéré dans la console via un scanner.
     *
     */
    public static String scenesInfos(){
        System.out.println("+-------------------------------------------------+" );
        System.out.println("|              Choix du type de scene             |");
        System.out.println("+-------------------------------------------------+" );
        System.out.println("En cas d'entrée invalide, le fichier randomHuge sera utilisé.\n");
        System.out.println("1. Ellipses\n2. Rectangles\n3. Aléatoires\n4. First\n5. Autres\n");
        Scanner userInput = new Scanner(System.in);
        String choice = userInput.nextLine();
        return sceneSizes(choice);
    }

    /**
     * Deuxième étape du mode console, choix de la taille de la scene.
     *
     * @param choice le choix fait par l'utilisateur dans scenesInfos()
     * @return le path vers un fichier
     */
    public static String sceneSizes(String choice){
        Scanner userInput = new Scanner(System.in);
        if(choice.equals("1")||choice.equals("2")||choice.equals("3")||choice.equals("4")) {
            System.out.println("+-------------------------------------------------+" );
            System.out.println("|          Choix de la taille de la scene         |");
            System.out.println("+-------------------------------------------------+" );
        }
        switch (choice){
            case "1":
                System.out.println("En cas d'entrée invalide, le fichier Small sera utilisé.\n");
                System.out.println("1. Small\n2. Medium\n3. Large\n");
                String chosen = userInput.nextLine();
                return switchEllipse(chosen);
            case "2":
                System.out.println("En cas d'entrée invalide, le fichier Small sera utilisé.\n");
                System.out.println("1. Small\n2. Medium\n3. Large\n4. Huge\n");
                String chosen2 = userInput.nextLine();
                return switchRectangle(chosen2);
            case "3":
                System.out.println("En cas d'entrée invalide, le fichier Small sera utilisé.\n");
                System.out.println("1. Small\n2. Medium\n3. Large\n4. Huge\n");
                String chosen3 = userInput.nextLine();
                return switchRandom(chosen3);
            case "4":
                System.out.println("En cas d'entrée invalide, le fichier Octangle sera utilisé.\n");
                System.out.println("1. Octangle\n2. Octogone\n");
                String chosen4 = userInput.nextLine();
                return switchFirst(chosen4);
            case "5":
                System.out.println("Veuillez écrire le path vers le fichier extérieur que vous voulez utiliser.");
                String filePath = userInput.nextLine();
                return filePath;
            default:
                return pathRandomHuge;
        }
    }

    /**
     * Fonction Switch pour les fichiers d'ellipse. Selon le choix de l'utilisateur, retourne le path vers le bon fichier
     *
     * @param choice input de l'utilisateur
     * @return path vers le bon fichier
     */
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

    /**
     * Fonction Switch pour les fichiers rectangles. Selon le choix de l'utilisateur, retourne le path vers le bon fichier
     *
     * @param choice input de l'utilisateur
     * @return path vers le bon fichier
     */
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

    /**
     * Fonction Switch pour les fichiers aléatoires. Selon le choix de l'utilisateur, retourne le path vers le bon fichier
     *
     * @param choice input de l'utilisateur
     * @return path vers le bon fichier
     */
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

    /**
     * Fonction Switch pour les fichiers "first" Selon le choix de l'utilisateur, retourne le path vers le bon fichier
     *
     * @param choice input de l'utilisateur
     * @return path vers le bon fichier
     */
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
        String scene = scenesInfos();
        printScene(scene);
    }

    /**
     * Imprime dans la console tous les détails de la scene choisie par l'utilisateur avec toutes les heuristiques. Ensuite, propose de recommencer ou pas.
     *  Pour recommencer, soit on recommence avec le meme fichier (En relançant donc cette meme fonction) , soit a l'étape du choix de scene.
     * @param scene la scene choisie dans sceneInfos()
     */
    public static void printScene(String scene){
        SceneReader sceneReader = new SceneReader(scene);
        if(sceneReader.getSegmentsSize() == 0)
        {
            return;
        }
        System.out.println(sceneReader);
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
        System.out.printf("| %-20s | %8s | %6s | %14s | %15s |\n","Heuristic","Size","Height","Build CPU Time","Painter CPU Time");
        System.out.println("+----------------------+----------+--------+----------------+------------------+");
        ArrayList<Segment> segmentArrayList = sceneReader.getSegments();
        printDetails(segmentArrayList, randomHeuristic);
        printDetails(segmentArrayList, standardHeuristic);
        printDetails(segmentArrayList, twnbHeuristic);
        //printDetails(segmentArrayList, optimizedRandomHeuristic);
        //printDetails(segmentArrayList, bonusHeuristic);
        System.out.println("+-------------------------------------------------+" );
        System.out.println("|                   Menu de fin                   |");
        System.out.println("+-------------------------------------------------+" );
        System.out.println("Que voulez-vous faire?\nEn cas d'entrée invalide, l'application s'arrêtera.\n1. Recommencer avec le même fichier\n2. Recommencer avec un fichier différent\n3. Quitter\n");
        Scanner userChoice = new Scanner(System.in);
        String choice = userChoice.nextLine();
        if(choice.equals("1")){printScene(scene);}
        else if(choice.equals("2")){consoleInterface();}
        else{return;}

    }

    public static void main(String[] args){
        System.out.println(
                "* * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                        "*                                                 *\n" +
                        "* Bienvenue dans le mode console de l'application *\n" +
                        "*                                                 *\n" +
                        "* * * * * * * * * * * * * * * * * * * * * * * * * *\n\n" +
                        "Vous pourrez comparer nos différentes heuristiques utilisées dans la construction d'arbres BSP.");
        System.out.println("Pour cela, veillez à entrer le chiffre correspondant au choix qui vous intéresse dans les différents menus.\n");
        consoleInterface();
    }
}