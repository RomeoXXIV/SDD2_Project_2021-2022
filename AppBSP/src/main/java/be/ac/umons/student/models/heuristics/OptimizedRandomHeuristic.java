package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

public class OptimizedRandomHeuristic implements HeuristicSelector{
    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        //TODO à implémenter
        /*int length = segments.size();
        Boolean [] rightEndPoint = new Boolean[length];
        Boolean [] leftEndPoint = new Boolean[length];*/
        return segments.get(0);
    }
}