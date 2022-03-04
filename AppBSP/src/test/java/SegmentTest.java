import be.ac.umons.student.models.Point;
import org.junit.jupiter.api.Test;

import static be.ac.umons.student.models.Segment.length;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SegmentTest {

    @Test
    public void test_length() {
        Point zero = new Point(0, 0);
        Point a = new Point(1, 0);
        Point b = new Point(0, 1);
        assertEquals(0, length(zero, zero));
        assertEquals(1, length(zero, a));
        assertEquals(Math.sqrt(2), length(a, b));
    }
}
