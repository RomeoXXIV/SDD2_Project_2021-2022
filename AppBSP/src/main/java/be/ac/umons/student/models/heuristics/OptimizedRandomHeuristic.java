package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

public class OptimizedRandomHeuristic implements HeuristicSelector {
    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        //TODO à implémenter
        return segments.get(0);
    }

    @Override
    public String toString() {
        return "OptimizedRandomHeuristic";
    }
}
