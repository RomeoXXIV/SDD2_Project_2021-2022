package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.SegmentDistribution;

import java.util.ArrayList;

/**
 * Implémentation de l'Heuristique H3: l'Heuristique TWNB (Thibault William & Naylor Bruce), cherchant à minimiser la fonction suivante:
 * (fd+)*(fd-) - w*fd
 * Fd+ étant le nombre de segments dans le demi-espace ouvert positif, Fd- étant le nombre de segments ans le demi-espace ouvert négatif,
 * w un poid ici fixé à 7 et fd le nombre de segment que la droite de coupe intersecte.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class TWNBHeuristic implements HeuristicSelector {


    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        ArrayList<Segment> usedSegments = new ArrayList<>();
        Segment currentSegment = segments.get(0);
        int currentRatio = leftAndRightRatio(currentSegment, segments);
        usedSegments.add(currentSegment);
        usedSegments.addAll(currentSegment.toLine().contentSegments(segments));
        ArrayList<Segment> currentIntersectedList = intersectionList(currentSegment, segments);
        int functionResult = functionToMaximize(currentSegment, segments, currentIntersectedList.size());
        for(int i = 0; i < segments.size(); i++) {
            Segment newSegment = segments.get(0);
            if (!usedSegments.contains(newSegment)) {
                usedSegments.add(newSegment);
                usedSegments.addAll(newSegment.toLine().contentSegments(segments));
                ArrayList<Segment> newIntersectedList = intersectionList(newSegment, segments);
                int newRatio = leftAndRightRatio(newSegment, segments);
                if (newRatio < currentRatio) {
                    if (newIntersectedList.size() < currentIntersectedList.size()) {
                        int newResult = functionToMaximize(newSegment, segments, newIntersectedList.size());
                        if (newResult > functionResult) {
                            currentSegment = newSegment;
                            functionResult = newResult;
                            currentRatio = newRatio;
                        }
                    }
                }
            }
        }
        return currentSegment;
    }

    /**
     * Fonction qui retourne la liste de tous les segments intersectés par un segment donné
     *
     * @param segment le segment actuel
     * @param segments la liste des segments de l'arbre
     * @return une ArrayList contenant tous les segments intersecté par le segment actuel
     */
    public ArrayList<Segment> intersectionList(Segment segment, ArrayList<Segment> segments)
    {
        ArrayList<Segment> copiedSegments = new ArrayList<>(segments);
        ArrayList<Segment> intersectedSegments = new ArrayList<>();
        Line line = segment.toLine();
        copiedSegments.removeAll(line.contentSegments(segments));
        for(int i = 0; i < segments.size(); i++)
        {
            if(!line.containsInOpenNegativeHalfSpace(segments.get(i))){
                if(!line.containsInOpenPositiveHalfSpace(segments.get(i)))
                {
                    intersectedSegments.add(segments.get(i));
                }
            }
        }
        return intersectedSegments;
    }

    /**
     * Fonction donnant le ratio entre les segments dans le demi-plan gauche et droit crée par notre segment
     *
     * @param segment le segment donné
     * @param segments la liste des segments
     * @return la valeur absolue de la division du nombre de segments à gauche par celui à droite.
     */
    public int leftAndRightRatio(Segment segment, ArrayList<Segment> segments){
        Line segmentLine = segment.toLine();
        SegmentDistribution segmentDistribution = new SegmentDistribution(segments, segmentLine);
        ArrayList<Segment> segmentsForLeft = segmentDistribution.getSegmentsInOpenNegativeHalfSpace();
        ArrayList<Segment> segmentsForRight = segmentDistribution.getSegmentsInOpenPositiveHalfSpace();
        if (segmentsForRight.size() != 0 && segmentsForLeft.size() != 0) {
            return Math.abs((int) (segmentsForLeft.size() / segmentsForRight.size()));
        }
        else if(segmentsForRight.size() == 0 && segmentsForLeft.size() != 0)
        {
            return segmentsForLeft.size();
        }
        else if(segmentsForRight.size() != 0 && segmentsForLeft.size() == 0){
            return segmentsForRight.size();
        }
        else{return 1;}
    }

    /**
     * Notre fonction maximiser pour l'heuristique
     *
     * @param segment le segment actuel
     * @param segments la liste des segments
     * @param intersectionListSize le nombre d'intersections qu'a le segment avec d'autres
     * @return
     */
    public int functionToMaximize(Segment segment, ArrayList<Segment> segments, int intersectionListSize){
        ArrayList<Segment> copiedSegments = new ArrayList<>(segments);
        Line currentLine = segment.toLine();
        copiedSegments.removeAll(currentLine.contentSegments(segments));
        SegmentDistribution segmentDistribution = new SegmentDistribution(copiedSegments, currentLine);
        ArrayList<Segment> segmentsForLeft = segmentDistribution.getSegmentsInOpenNegativeHalfSpace();
        ArrayList<Segment> segmentsForRight = segmentDistribution.getSegmentsInOpenPositiveHalfSpace();
        return segmentsForLeft.size() * segmentsForRight.size() - intersectionListSize*7;
    }

    @Override
    public String toString() {
        return "TWNBHeuristic";
    }
}