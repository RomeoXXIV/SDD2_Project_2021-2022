package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

public class Painter {

    public Painter(BSPTree bspTree, Point viewPoint, Paintable paintable) {
        if (bspTree.isLeaf())
            drawSegments(bspTree.getSegments(), paintable);
        else if (bspTree.getSplit().containsInOpenPositiveHalfSpace(viewPoint)) {
            new Painter(bspTree.getLeft(), viewPoint, paintable);
            drawSegments(bspTree.getSegments(), paintable);
            new Painter(bspTree.getRight(), viewPoint, paintable);
        }
        else if (bspTree.getSplit().containsInOpenNegativeHalfSpace(viewPoint)) {
            new Painter(bspTree.getRight(), viewPoint, paintable);
            drawSegments(bspTree.getSegments(), paintable);
            new Painter(bspTree.getLeft(), viewPoint, paintable);
        }
        else {
            new Painter(bspTree.getRight(), viewPoint, paintable);
            new Painter(bspTree.getLeft(), viewPoint, paintable);
        }
    }

    public Painter(BSPTree bspTree, Paintable paintable) {
        this(bspTree, new Point(0, 0), paintable);
    }

    public void drawSegments(ArrayList<Segment> segments, Paintable paintable) {
        for (Segment segment : segments) {
            paintable.drawSegment(segment);
        }
    }
}
