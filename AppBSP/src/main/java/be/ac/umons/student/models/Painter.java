package be.ac.umons.student.models;

import java.util.ArrayList;

public class Painter {

    private final ArrayList<Segment> segments;

    public Painter(BSPTree bspTree, Point viewPoint) {
        segments = new ArrayList<>();
        if (bspTree.isLeaf())
            segments.addAll(bspTree.getSegments());
        else if (bspTree.getSplit().containsInOpenPositiveHalfSpace(viewPoint)) {
            segments.addAll(new Painter(bspTree.getLeft(), viewPoint).getSegments());
            segments.addAll(bspTree.getSegments());
            segments.addAll(new Painter(bspTree.getRight(), viewPoint).getSegments());
        }
        else if (bspTree.getSplit().containsInOpenNegativeHalfSpace(viewPoint)) {
            segments.addAll(new Painter(bspTree.getRight(), viewPoint).getSegments());
            segments.addAll(bspTree.getSegments());
            segments.addAll(new Painter(bspTree.getLeft(), viewPoint).getSegments());
        }
        else {
            segments.addAll(new Painter(bspTree.getRight(), viewPoint).getSegments());
            segments.addAll(new Painter(bspTree.getLeft(), viewPoint).getSegments());
        }
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }
}
