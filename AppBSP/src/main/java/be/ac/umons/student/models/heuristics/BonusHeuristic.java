package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.SegmentDistribution;

import java.util.ArrayList;
import java.util.Random;

/**
 * Heuristique bonus issue d'une erreur de lecture de l'énoncé. Pour l'Heuristique H3, on devait minimiser la multiplication du nombre de segments
 * à droite et à gauche a laquelle on soustrayait le nombre de segments intersectés. Ici, on ne soustrait pas le nombre de segments intersectés
 * mais le nombre de segments confondus.
 */
public class BonusHeuristic implements HeuristicSelector {


    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        ArrayList<Segment> usedSegments = new ArrayList<>();
        Segment currentSegment = segments.get(new Random().nextInt(segments.size()));
        ArrayList<Segment> contentCurrentSegment = new ArrayList<>();
        contentCurrentSegment.addAll(currentSegment.toLine().contentSegments(segments));
        int currentRatio = leftAndRightRatio(currentSegment, segments);
        usedSegments.add(currentSegment);
        usedSegments.addAll(contentCurrentSegment);
        int functionResult = functionToMaximize(currentSegment, segments);
        for(int i = 0; i < segments.size(); i++) {
            Segment newSegment = segments.get(new Random().nextInt(segments.size()));
            ArrayList<Segment> contentNewSegment = new ArrayList<>();
            contentNewSegment.addAll(newSegment.toLine().contentSegments(segments));
            int newRatio = leftAndRightRatio(newSegment, segments);
            if (newRatio < currentRatio) {
                if (contentNewSegment.size() < contentCurrentSegment.size()) {
                    if (!usedSegments.contains(newSegment)) {
                        int newResult = functionToMaximize(newSegment, segments);
                        if (newResult > functionResult) {
                            currentSegment = newSegment;
                            functionResult = newResult;
                            currentRatio = newRatio;
                        }
                        usedSegments.add(newSegment);
                        usedSegments.addAll(newSegment.toLine().contentSegments(segments));
                    }
                }
            }
        }

        return currentSegment;
    }

    /** Fonction qui retourne le résultat du calcul
     *
     * @param segment le segment actuel
     * @param segments la liste de segments
     * @return le résultat
     */
    public int functionToMaximize(Segment segment, ArrayList<Segment> segments){
        ArrayList<Segment> copiedSegments = new ArrayList<>(segments);
        Line currentLine = segment.toLine();
        ArrayList<Segment> contentSegment = currentLine.contentSegments(segments);
        copiedSegments.removeAll(contentSegment);
        SegmentDistribution segmentDistribution = new SegmentDistribution(copiedSegments, currentLine);
        ArrayList<Segment> segmentsForLeft = segmentDistribution.getSegmentsInOpenNegativeHalfSpace();
        ArrayList<Segment> segmentsForRight = segmentDistribution.getSegmentsInOpenPositiveHalfSpace();
        return segmentsForLeft.size() * segmentsForRight.size() - contentSegment.size();
    }

    public int leftAndRightRatio(Segment segment, ArrayList<Segment> segments) {
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
        else{
            return segmentsForRight.size();
        }
    }


    @Override
    public String toString() {
        return "BonusHeuristic";
    }
}
