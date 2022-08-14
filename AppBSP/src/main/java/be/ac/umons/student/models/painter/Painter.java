package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

/** Painter est une classe implémentant l'algorithme du peintre, permettant de traiter les segments d'un arbre BSP
 * dans le bon ordre selon deux comportements :
 * - un comportement de dessin pour l'interface graphique.
 * - un comportement de calcul pour la console.
 * @author Romeo Ibraimovski
 */
public class Painter {

    public static void painter(BSPTree bspTree, ViewPoint viewPoint, Paintable paintable) {
        if (bspTree.isLeaf()) {
            drawSegments(bspTree.getSegments(), viewPoint, paintable);}
        else if (bspTree.getSplit().containsInOpenPositiveHalfSpace(viewPoint.getPoint())) {
            painter(bspTree.getLeft(), viewPoint, paintable);
            drawSegments(bspTree.getSegments(), viewPoint, paintable);
            painter(bspTree.getRight(), viewPoint, paintable);
        }
        else if (bspTree.getSplit().containsInOpenNegativeHalfSpace(viewPoint.getPoint())) {
            painter(bspTree.getRight(), viewPoint, paintable);
            drawSegments(bspTree.getSegments(), viewPoint, paintable);
            painter(bspTree.getLeft(), viewPoint, paintable);
        }
        else {
            painter(bspTree.getRight(), viewPoint, paintable);
            painter(bspTree.getLeft(), viewPoint, paintable);
        }
    }

    public static void painter(BSPTree bspTree, Paintable paintable) {
        painter(bspTree, new ViewPoint(), paintable);
    }

    public static void drawSegments(ArrayList<Segment> segments, ViewPoint viewPoint, Paintable paintable) {
        for (Segment segment : segments) {
            paintable.drawSegment(segment, viewPoint);
        }
    }

}
