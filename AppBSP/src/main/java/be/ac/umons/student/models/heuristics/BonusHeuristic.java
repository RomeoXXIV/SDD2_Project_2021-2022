package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.SegmentDistribution;

import java.util.ArrayList;
import java.util.Random;

public class BonusHeuristic implements HeuristicSelector {


    @Override
    public Segment selectSegment(ArrayList<Segment> segments) {
        //TODO à implémenter
        ArrayList<Segment> usedSegments = new ArrayList<>();
        Segment currentSegment = segments.get(new Random().nextInt(segments.size()));
        ArrayList<Segment> contentCurrentSegment = new ArrayList<>();
        contentCurrentSegment.addAll(currentSegment.toLine().getContentSegments(segments));
        int currentRatio = leftAndRightRatio(currentSegment, segments);
        usedSegments.add(currentSegment);
        usedSegments.addAll(contentCurrentSegment);
        int functionResult = functionToMaximize(currentSegment, segments);
        for(int i = 0; i < segments.size(); i++) {
            Segment newSegment = segments.get(new Random().nextInt(segments.size()));
            ArrayList<Segment> contentNewSegment = new ArrayList<>();
            contentNewSegment.addAll(newSegment.toLine().getContentSegments(segments));
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
                        usedSegments.addAll(newSegment.toLine().getContentSegments(segments));
                    }
                }
            }
        }

        return currentSegment;
    }

    public int functionToMaximize(Segment segment, ArrayList<Segment> segments){
        ArrayList<Segment> copiedSegments = new ArrayList<>(segments);
        Line currentLine = segment.toLine();
        ArrayList<Segment> contentSegment = currentLine.getContentSegments(segments);
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
    /*d éterminer la droite d qui supporte le segment s;
    evaluer la “qualit ́e” de d selon les crit`eres de l’heuristique.*/

    @Override
    public String toString() {
        return "BonusHeuristic";
    }
}
