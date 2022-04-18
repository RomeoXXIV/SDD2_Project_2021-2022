package be.ac.umons.student.models;

import java.util.ArrayList;

/**
 * Classe effectuant la distribution des segments d'une liste de segments dans les demi-espaces ouverts positifs et négatifs
 * par rapport à une droite.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class SegmentDistribution {

    private final ArrayList<Segment> segmentsInOpenNegativeHalfSpace;
    private final ArrayList<Segment> segmentsInOpenPositiveHalfSpace;

    public SegmentDistribution(ArrayList<Segment> segmentArrayList, Line line) {
        this.segmentsInOpenNegativeHalfSpace = new ArrayList<>();
        this.segmentsInOpenPositiveHalfSpace = new ArrayList<>();

        for (Segment segment : segmentArrayList) {
            if (line.containsInOpenNegativeHalfSpace(segment))
                this.segmentsInOpenNegativeHalfSpace.add(segment);
            else if (line.containsInOpenPositiveHalfSpace(segment))
                this.segmentsInOpenPositiveHalfSpace.add(segment);
            else {
                Point intersection = line.intersection(segment);
                Segment[] splitSegment = segment.split(intersection);
                if (!splitSegment[0].isPoint() && !splitSegment[1].isPoint()) {
                    if (line.containsInOpenNegativeHalfSpace(splitSegment[0].getA())
                            || line.containsInOpenNegativeHalfSpace(splitSegment[0].getB())) {
                        segmentsInOpenNegativeHalfSpace.add(splitSegment[0]);
                        segmentsInOpenPositiveHalfSpace.add(splitSegment[1]);
                    }
                    else {
                        segmentsInOpenNegativeHalfSpace.add(splitSegment[1]);
                        segmentsInOpenPositiveHalfSpace.add(splitSegment[0]);
                    }
                }
                else if (splitSegment[0].isPoint()) {
                    if (line.containsInOpenNegativeHalfSpace(splitSegment[1].getA())
                            || line.containsInOpenNegativeHalfSpace(splitSegment[1].getB()))
                        segmentsInOpenNegativeHalfSpace.add(splitSegment[1]);
                    else
                        segmentsInOpenPositiveHalfSpace.add(splitSegment[1]);
                }
                else if (splitSegment[1].isPoint()) {
                    if (line.containsInOpenNegativeHalfSpace(splitSegment[0].getA())
                            || line.containsInOpenNegativeHalfSpace(splitSegment[0].getB()))
                        segmentsInOpenNegativeHalfSpace.add(splitSegment[1]);
                    else
                        segmentsInOpenPositiveHalfSpace.add(splitSegment[1]);
                }
            }
        }
    }

    public ArrayList<Segment> getSegmentsInOpenNegativeHalfSpace() {
        return segmentsInOpenNegativeHalfSpace;
    }

    public ArrayList<Segment> getSegmentsInOpenPositiveHalfSpace() {
        return segmentsInOpenPositiveHalfSpace;
    }
}
