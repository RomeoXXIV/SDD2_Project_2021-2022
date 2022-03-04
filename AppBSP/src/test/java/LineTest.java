import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static be.ac.umons.student.models.Line.intersection;

public class LineTest {

    @Test
    public void test_intersection() {
        Line d1 = new Line(1, 0, 0);
        Line d2 = new Line(0, 1, 0);
        Point point = intersection(d1, d2);
        Point test = new Point(0, 0);
        assertEquals(test, point);
    }
}
