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

    public static void printDetails(ArrayList<Segment> segmentList, HeuristicSelector heuristic)
    {
        //Instant start = Instant.now();
        BSPTree tree = new BSPTree(segmentList, heuristic);
        //Instant stop = Instant.now();
        //Duration randomTimeElapsed = Duration.between(start, stop);
        System.out.println("Taille de l'arbre cree: " + tree.size());
        System.out.println("Hauteur de l'arbre cree: " + tree.height());
        //System.out.println("Temps CPU pour construire l'Arbre: " + randomTimeElapsed);
        System.out.println("Temps CPU pour effectuer l'Algorithme du Peintre sur l'arbre: ");
    }

    public static void main(String[] args) {
        System.out.println("Pour ce test dans la console, nous utilisons le fichier randomHuge.txt");
        String pathRandomHugeScene = "../Scenes/random/randomHuge.txt";
        SceneReader sr = new SceneReader(pathRandomHugeScene);
        ArrayList<Segment> segmentArrayList = sr.getSegments();
        System.out.println("Ce fichier contient " + sr.getSegmentsSize() + " segments");
        HeuristicSelector random = new RandomHeuristic();
        HeuristicSelector standard = new StandardHeuristic();
        //HeuristicSelector twnb = new TWNBHeuristic();
        //HeuristicSelector optiRandom = new OptimizedRandomHeuristic();

        //System.out.println("Heuristique Aleatoire:\n");
        //printDetails(segmentArrayList, random);
        System.out.println("Heuristique Standard:\n");
        printDetails(segmentArrayList, standard);
    }
}
