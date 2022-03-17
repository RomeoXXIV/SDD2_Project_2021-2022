import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentTest {

    private final Point origin = new Point(0, 0);
    private final Point a = new Point(1, 0);
    private final Point b = new Point(0, 1);
    private final Segment s_o = new Segment(origin, origin);
    private final Segment s_oa = new Segment(origin, a);
    private final Segment s_ab = new Segment(a, b);

    @Test
    public void test_toLine() {
        Line notLine = new Line(0,0,0);
        Line line_z = s_o.toLine();

        Line d = new Line(1, 1, -1);
        Line d_ab = s_ab.toLine();

        Line xLine = new Line(0, 1, 0);
        Line line_za = s_oa.toLine();

        assertEquals(notLine, line_z);
        assertEquals(d, d_ab);
        assertEquals(xLine, line_za);
    }

    @Test
    public void test_split() {
        Segment [] expectedSplitSegmentOrigin = new Segment[] {s_o, s_o};
        Segment [] splitSegmentOrigin = s_o.split(origin);

        Segment [] expectedSplitSegmentOriginA = new Segment[] {s_o, s_oa};
        Segment [] splitSegmentOriginA = s_oa.split(origin);

        Point demi = new Point(0.5, 0.5);
        Segment a_demi = new Segment(a, demi);
        Segment demi_b = new Segment(demi, b);
        Segment [] expectedSplitSegmentAB = new Segment[] {a_demi, demi_b};
        Segment [] splitSegmentAB = s_ab.split(demi);

        assertEquals(expectedSplitSegmentOrigin[0], splitSegmentOrigin[0]);
        assertEquals(expectedSplitSegmentOrigin[1], splitSegmentOrigin[1]);
        assertEquals(expectedSplitSegmentOriginA[0], splitSegmentOriginA[0]);
        assertEquals(expectedSplitSegmentOriginA[1], splitSegmentOriginA[1]);
        assertEquals(expectedSplitSegmentAB[0], splitSegmentAB[0]);
        assertEquals(expectedSplitSegmentAB[1], splitSegmentAB[1]);
    }

    @Test
    public void test_length() {
        assertEquals(0, s_o.length());
        assertEquals(1, s_oa.length());
        assertEquals(Math.sqrt(2), s_ab.length());
    }
}
