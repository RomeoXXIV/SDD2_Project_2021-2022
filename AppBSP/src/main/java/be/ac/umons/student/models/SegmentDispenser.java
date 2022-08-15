package be.ac.umons.student.models;

import java.util.ArrayList;

/**
 * SegmentDispenser est une classe effectuant la distribution des segments d'une liste de segments non confondus
 * à une droite dans les demi-espaces ouverts positifs et négatifs définie par rapport à cette droite.
 * @author Romeo Ibraimovski
 */
public class SegmentDispenser {

    private final ArrayList<Segment> segmentsInOpenNegativeHalfSpace;
    private final ArrayList<Segment> segmentsInOpenPositiveHalfSpace;

    public SegmentDispenser(ArrayList<Segment> segmentArrayList, Line line) {
        this.segmentsInOpenNegativeHalfSpace = new ArrayList<>();
        this.segmentsInOpenPositiveHalfSpace = new ArrayList<>();

        for (Segment segment : segmentArrayList) {
            if (line.containsInOpenPositiveHalfSpace(segment))
                this.segmentsInOpenNegativeHalfSpace.add(segment);
            else if (line.containsInOpenNegativeHalfSpace(segment))
                this.segmentsInOpenPositiveHalfSpace.add(segment);
            else { // le segment n'est pas entièrement dans un des demi-espaces ouverts.
                Point intersection = line.intersection(segment);
                if (segment.hasForExtremity(intersection)) { // le segment est en contact avec la droite.
                    if (line.containsInClosePositiveHalfSpace(segment))
                        this.segmentsInOpenNegativeHalfSpace.add(segment);
                    else
                        this.segmentsInOpenPositiveHalfSpace.add(segment);
                }
                else { // le segment est dans les deux demi-espaces ouverts.
                    Segment[] splitSegment = segment.split(intersection);
                    if (line.containsInClosePositiveHalfSpace(splitSegment[0])) {
                        this.segmentsInOpenNegativeHalfSpace.add(splitSegment[0]);
                        this.segmentsInOpenPositiveHalfSpace.add(splitSegment[1]);
                    }
                    else {
                        this.segmentsInOpenPositiveHalfSpace.add(splitSegment[0]);
                        this.segmentsInOpenNegativeHalfSpace.add(splitSegment[1]);
                    }
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
