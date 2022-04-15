package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;

@FunctionalInterface
public interface Paintable {
    void drawSegment(Segment segment, ViewPoint viewPoint);
}
