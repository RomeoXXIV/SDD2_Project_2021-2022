package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

public class StandardHeuristic implements HeuristicSelector{


    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        return segments.get(0);
    }
}
