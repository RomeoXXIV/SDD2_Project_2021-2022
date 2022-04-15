package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;

public class PainterConsole implements Paintable {
    private static int cptr = 0;
    @Override
    public void drawSegment(Segment segment, ViewPoint viewPoint) {
        cptr++;
    }
}
