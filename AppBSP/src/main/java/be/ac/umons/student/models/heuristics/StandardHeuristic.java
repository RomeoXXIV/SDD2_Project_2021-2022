package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

/**
 * Implémentation de l'Heuristique Standard prenant simplement le premier élément de la liste de segments.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class StandardHeuristic implements HeuristicSelector {
    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        return segments.get(0);
    }

    @Override
    public String toString() {
        return "StandardHeuristic";
    }
}
