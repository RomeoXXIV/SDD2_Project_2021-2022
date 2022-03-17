package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

@FunctionalInterface
public interface HeuristicSelector {
    Segment selectSegment(ArrayList<Segment> segments);
}
