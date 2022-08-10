package be.ac.umons.student.models;

import be.ac.umons.student.models.heuristics.HeuristicSelector;

import java.util.ArrayList;

/**
 * BSPTree est une classe représentant un arbre BSP (en anglais, "binary space partition tree").
 * @author Romeo Ibraimovski
 */
public class BSPTree {

    private final ArrayList<Segment> segments;
    private Line split;
    private BSPTree left, right;

    public BSPTree(ArrayList<Segment> segmentArrayList, HeuristicSelector heuristic) {
        if (segmentArrayList.size() <= 1) this.segments = segmentArrayList; // une feuille.
        else { // un noeud interne.
            this.segments = new ArrayList<>();
            Segment selectSegment = heuristic.selectSegment(segmentArrayList);
            // ajouter le segment selectionné a la liste des segments du noeud courant
            // et le supprimer de la liste des segments fournie par noeud parent.
            this.segments.add(segmentArrayList.remove(segmentArrayList.indexOf(selectSegment)));
            this.split = selectSegment.toLine();
            // ajouter les segments confondus à la droite venant de la liste des segments du noeud fournie par noeud parent.
            this.segments.addAll(this.split.contentSegments(segmentArrayList));
            segmentArrayList.removeAll(this.segments);
            // segmentArrayList ne contient plus que les segments à repartir aux sous-arbres.
            SegmentDistribution segmentDistribution = new SegmentDistribution(segmentArrayList, this.split);
            ArrayList<Segment> segmentsForLeft = segmentDistribution.getSegmentsInOpenNegativeHalfSpace();
            ArrayList<Segment> segmentsForRight = segmentDistribution.getSegmentsInOpenPositiveHalfSpace();
            this.left = new BSPTree(segmentsForLeft, heuristic);
            this.right = new BSPTree(segmentsForRight, heuristic);
        }
    }

    /**
     * Retourne la hauteur de l'arbre.
     * @return la hauteur de l'arbre.
     */
    public int height() {
        if (this.isLeaf())
            return 1;
        else
            return 1 + Math.max(this.left.height(), this.right.height());
    }

    /**
     * Retourne la taille de l'arbre.
     * @return la taille de l'arbre.
     */
    public int size() {
        if (this.isLeaf())
            return 1;
        else
            return 1 + this.left.size() + this.right.size();
    }

    /**
     * Détermine si l'arbre est réduit à une feuille.
     * @return true si l'arbre est réduit à une feuille, false sinon.
     */
    public boolean isLeaf() {
        return this.segments.size() <= 1;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public Line getSplit() {
        return split;
    }

    public BSPTree getLeft() {
        return left;
    }

    public BSPTree getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "BSPTree@" + Integer.toHexString(hashCode()) + "{" +
                "segments=" + segments +
                ", split=" + split +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
