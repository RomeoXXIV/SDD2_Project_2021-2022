import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Vector;
import org.junit.jupiter.api.Test;

import static be.ac.umons.student.models.Line.getDirectorVector;
import static be.ac.umons.student.models.Line.getNormalVector;
import static org.junit.jupiter.api.Assertions.*;

public class LineTest {

    private final Line xLine = new Line(0, 1, 0);
    private final Line yLine = new Line(1, 0, 0);
    private final Point origin = new Point(0, 0);


    @Test
    public void test_intersection() {
        assertEquals(origin, xLine.intersection(yLine));
    }

    @Test
    public void test_inOpenNegativeHalfSpace() {
        Point pointInOpenNegativeHalfSpace = new Point(0, -0.00001);
        assertFalse(xLine.inOpenNegativeHalfSpace(origin));
        assertTrue(xLine.inOpenNegativeHalfSpace(pointInOpenNegativeHalfSpace));
    }

    @Test
    public void test_isContained() {
        Point pointJustInXLine = new Point(1, 0);
        assertTrue(xLine.isContained(pointJustInXLine));
        assertFalse(yLine.isContained(pointJustInXLine));
    }

    @Test
    public void test_getDirectorVector() {
        Vector expectedDirectorVector = new Vector(-1, 1);
        Point a = new Point(1, -1);
        Vector directorVector = getDirectorVector(origin, a);

        assertTrue(directorVector.equals(expectedDirectorVector) || directorVector.equals(expectedDirectorVector.opposite()));
    }

    @Test
    public void test_getNormalVector() {
        Vector expectedNormalVector = new Vector(1, 1);
        Point a = new Point(1, -1);
        Vector normalVector = getNormalVector(origin, a);

        assertTrue(normalVector.equals(expectedNormalVector) || normalVector.equals(expectedNormalVector.opposite()));
    }
}
