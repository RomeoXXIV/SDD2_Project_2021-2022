package be.ac.umons.student.models;

import be.ac.umons.student.models.heuristics.HeuristicSelector;

import java.util.ArrayList;

public class BSPTree {

    private final ArrayList<Segment> segments;
    private Line split;
    private BSPTree left, right;

    public BSPTree(ArrayList<Segment> segments, Line split, BSPTree left, BSPTree right) {
        this.segments = segments;
        this.split = split;
        this.left = left;
        this.right = right;
    }

    public BSPTree(ArrayList<Segment> segmentArrayList, HeuristicSelector heuristic) {
        if (segmentArrayList.size() <= 1) this.segments = segmentArrayList;
        else {
            this.segments = new ArrayList<>();
            Segment selectSegment = heuristic.selectSegment(segmentArrayList);
            this.segments.add(segmentArrayList.remove(segmentArrayList.indexOf(selectSegment)));
            this.split = selectSegment.toLine();
            this.segments.addAll(this.split.getContentSegments(segmentArrayList));
            segmentArrayList.removeAll(this.segments);
            SegmentDistribution segmentDistribution = new SegmentDistribution(segmentArrayList, split);
            ArrayList<Segment> segmentsForLeft = segmentDistribution.getSegmentsInOpenNegativeHalfSpace();
            ArrayList<Segment> segmentsForRight = segmentDistribution.getSegmentsInOpenPositiveHalfSpace();
            this.left = new BSPTree(segmentsForLeft, heuristic);
            this.right = new BSPTree(segmentsForRight, heuristic);
        }
    }

    public BSPTree() {
        this(null, null, null, null);
    }

    /**
     * Retourne la hauteur de l'arbre.
     *
     * @return la hauteur de l'arbre.
     */
    public int height() {
        if (this.isEmpty())
            return 0;
        else if (this.isLeaf())
            return 1;
        else
            return 1 + Math.max(this.left.height(), this.right.height());
    }

    /**
     * Retourne la taille de l'arbre.
     *
     * @return la taille de l'arbre.
     */
    public int size() {
        if (this.isEmpty())
            return 0;
        else if (this.isLeaf())
            return 1;
        else
            return 1 + this.left.size() + this.right.size();
    }

    /**
     * Retourne true si l'arbre est réduit à une feuille.
     *
     * @return true si l'arbre est réduit à une feuille, false sinon.
     */
    public boolean isLeaf() {
        if (this.isEmpty())
            return false;
        return this.segments.size() <= 1 && this.split == null && this.left == null && this.right == null;
    }

    /**
     * Retourne true si l'arbre est vide.
     *
     * @return true si l'arbre est vide, false sinon.
     */
    public boolean isEmpty() {
        return this.segments == null && this.split == null && this.left == null && this.right == null;
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
