package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

public class TWNBHeuristic implements HeuristicSelector{
    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        //TODO à implémenter
        return segments.get(0);
    }
    /*d éterminer la droite d qui supporte le segment s;
    evaluer la “qualit ́e” de d selon les crit`eres de l’heuristique.*/

    @Override
    public String toString() {
        return "TWNBHeuristic";
    }
}
