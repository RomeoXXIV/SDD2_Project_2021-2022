package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

public class Painter {

    public Painter(BSPTree bspTree, ViewPoint viewPoint, Paintable paintable) {
        if (bspTree.isLeaf())
            drawSegments(bspTree.getSegments(), viewPoint, paintable);
        else if (bspTree.getSplit().containsInOpenPositiveHalfSpace(viewPoint.getPoint())) {
            new Painter(bspTree.getLeft(), viewPoint, paintable);
            drawSegments(bspTree.getSegments(), viewPoint, paintable);
            new Painter(bspTree.getRight(), viewPoint, paintable);
        }
        else if (bspTree.getSplit().containsInOpenNegativeHalfSpace(viewPoint.getPoint())) {
            new Painter(bspTree.getRight(), viewPoint, paintable);
            drawSegments(bspTree.getSegments(), viewPoint, paintable);
            new Painter(bspTree.getLeft(), viewPoint, paintable);
        }
        else {
            new Painter(bspTree.getRight(), viewPoint, paintable);
            new Painter(bspTree.getLeft(), viewPoint, paintable);
        }
    }

    public Painter(BSPTree bspTree, Paintable paintable) {
        this(bspTree, new ViewPoint(), paintable);
    }

    public void drawSegments(ArrayList<Segment> segments, ViewPoint viewPoint, Paintable paintable) {
        for (Segment segment : segments) {
            paintable.drawSegment(segment, viewPoint);
        }
    }
}
