package be.ac.umons.student.models;

import java.util.ArrayList;

public class Painter {

    public Painter(BSPTree bspTree, Point viewPoint) {
        if (bspTree.isLeaf())
            scanConvert(bspTree.getSegments());
        else if (bspTree.getSplit().containsInOpenPositiveHalfSpace(viewPoint)) {
            new Painter(bspTree.getLeft(), viewPoint);
            scanConvert(bspTree.getSegments());
            new Painter(bspTree.getRight(), viewPoint);
        }
        else if (bspTree.getSplit().containsInOpenNegativeHalfSpace(viewPoint)) {
            new Painter(bspTree.getRight(), viewPoint);
            scanConvert(bspTree.getSegments());
            new Painter(bspTree.getLeft(), viewPoint);
        }
        else {
            new Painter(bspTree.getRight(), viewPoint);
            new Painter(bspTree.getLeft(), viewPoint);
        }
    }

    public void scanConvert(ArrayList<Segment> segmentArrayList) {
        //TODO
    }
}
