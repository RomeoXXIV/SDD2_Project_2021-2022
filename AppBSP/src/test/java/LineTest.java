import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.Vector;
import org.junit.jupiter.api.Test;

import static be.ac.umons.student.models.Line.directorVector;
import static be.ac.umons.student.models.Line.normalVector;
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
    public void test_containsInOpenNegativeHalfSpace() {
        Point pointInOpenNegativeHalfSpace = new Point(0, -0.00001);
        assertFalse(xLine.containsInOpenNegativeHalfSpace(origin));
        assertTrue(xLine.containsInOpenNegativeHalfSpace(pointInOpenNegativeHalfSpace));
    }

    @Test
    public void test_contains() {
        Point pointJustInXLine = new Point(1, 0);
        assertTrue(xLine.contains(pointJustInXLine));
        assertFalse(yLine.contains(pointJustInXLine));
    }

    @Test
    public void test_getDirectorVector() {
        Vector expectedDirectorVector = new Vector(-1, 1);
        Point a = new Point(1, -1);
        Vector directorVector = directorVector(origin, a);

        assertTrue(directorVector.equals(expectedDirectorVector) || directorVector.equals(expectedDirectorVector.opposite()));
    }

    @Test
    public void test_getNormalVector() {
        Vector expectedNormalVector = new Vector(1, 1);
        Point a = new Point(1, -1);
        Vector normalVector = normalVector(origin, a);

        assertTrue(normalVector.equals(expectedNormalVector) || normalVector.equals(expectedNormalVector.opposite()));
    }

    @Test
    public void test_isSecantTo(){
        Line identity = new Line(1, -1, 0);
        Line reverseIdentity = new Line(1, 1, 0);
        Line doubleIdentity = new Line(2, -2, 0);
        assertTrue(identity.isSecantTo(reverseIdentity));
        assertFalse(identity.isSecantTo(doubleIdentity));
    }

    @Test
    public void test_isSecantToLineOf(){
        Line identity = new Line(1, -1, 0);
        Segment notSecantSegment = new Segment(new Point(1, 1), new Point(2, 2));
        Segment secantSegment = new Segment(new Point(1, -1), new Point(2, -2));
        assertFalse(identity.isSecantToLineOf(notSecantSegment));
        assertTrue(identity.isSecantToLineOf(secantSegment));


    }
}
