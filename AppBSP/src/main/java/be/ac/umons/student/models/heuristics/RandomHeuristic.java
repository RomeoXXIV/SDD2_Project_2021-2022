package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;
import java.util.Random;

/**
 * Implémentation de l'Heuristique Aléatoire prenant, grace à la classe Random de la librairie java, un segment aléatoire de la liste de segments.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class RandomHeuristic implements HeuristicSelector {
    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        return segments.get(new Random().nextInt(segments.size()));
    }

    @Override
    public String toString() {
        return "RandomHeuristic";
    }
}
