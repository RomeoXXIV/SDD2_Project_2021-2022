package be.ac.umons.student.models;

import be.ac.umons.student.models.heuristics.HeuristicSelector;

import java.util.ArrayList;

public class BSPTree {

    private final ArrayList<Segment> segments;
    private Line split;
    private BSPTree left, right;
    private HeuristicSelector heuristic;

    public BSPTree(ArrayList<Segment> segments, Line split, BSPTree left, BSPTree right, HeuristicSelector heuristic) {
        this.segments = segments;
        this.split = split;
        this.left = left;
        this.right = right;
        this.heuristic = heuristic;
    }

    public BSPTree(ArrayList<Segment> segments, HeuristicSelector heuristic) {
        if (segments.size() <= 1) this.segments = segments;
        else {
            this.segments = new ArrayList<>();
            ArrayList<Segment> segmentsForLeft = new ArrayList<>();
            ArrayList<Segment> segmentsForRight = new ArrayList<>();
            Segment selectSegment = heuristic.selectSegment(segments);
            this.segments.add(segments.remove(segments.indexOf(selectSegment)));
            this.split = selectSegment.toLine();
            this.segments.addAll(this.split.contains(segments));
            segments.removeAll(this.segments);
            for (Segment segment : segments) {
                if (this.split.inOpenNegativeHalfSpace(segment))
                    segmentsForLeft.add(segment);
                else if (this.split.inOpenNegativeHalfSpace(segment.getA())
                        || this.split.inOpenNegativeHalfSpace(segment.getB())) {
                    Point intersection = this.split.intersection(segment);
                    Segment[] splitSegment = segment.split(intersection);
                    if (this.split.inOpenNegativeHalfSpace(splitSegment[0].getA())
                            || this.split.inOpenNegativeHalfSpace(splitSegment[0].getB())) {
                        segmentsForLeft.add(splitSegment[0]);
                        segmentsForRight.add(splitSegment[1]);
                    }
                    else {
                        segmentsForLeft.add(splitSegment[1]);
                        segmentsForRight.add(splitSegment[0]);
                    }
                }
                else
                    segmentsForRight.add(segment);
            }
            this.left = new BSPTree(segmentsForLeft, heuristic);
            this.right = new BSPTree(segmentsForRight, heuristic);
        }
    }

    public BSPTree() {
        this(null, null, null, null, null);
    }

    /**
     * Retourne la hauteur de l'arbre.
     *
     * @return la hauteur de l'arbre.
     */
    public int height() {
        if (this.isLeaf())
            return 1;
        else
            return 1 + Math.max(left.height(), right.height());
    }

    /**
     * Retourne la taille de l'arbre.
     *
     * @return la taille de l'arbre.
     */
    public int size() {
        if (this.isLeaf())
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
        return this.segments.size() <= 1 && this.left.isEmpty() && this.right.isEmpty();
    }

    /**
     * Retourne true si l'arbre est vide.
     *
     * @return true si l'arbre est vide, false sinon.
     */
    public boolean isEmpty() {
        return this.segments == null && this.split == null && this.left == null && this.right == null && this.heuristic == null;
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

    public HeuristicSelector getHeuristic() {
        return heuristic;
    }
}
