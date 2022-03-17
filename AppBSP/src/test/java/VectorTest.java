import be.ac.umons.student.models.Vector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VectorTest {

    private final Vector nullVector = new Vector(0, 0);
    private final Vector uxVector = new Vector(1, 0);
    private final Vector uyVector = new Vector(0, 1);
    private final Vector vector = new Vector(1, 1);


    @Test
    public void test_norm() {
        assertEquals(0, nullVector.norm());
        assertEquals(1, uxVector.norm());
        assertEquals(Math.sqrt(2), vector.norm());
    }

    @Test
    public void test_opposite() {
        Vector opVector = new Vector(-1, -1);

        assertEquals(nullVector, nullVector.opposite());
        assertEquals(opVector, vector.opposite());
    }

    @Test
    public void test_sum() {
        Vector sumVector = uxVector.sum(uyVector);

        assertEquals(vector, sumVector);
    }

    @Test
    public void test_difference() {
        Vector diffVector = vector.difference(uyVector);

        assertEquals(uxVector, diffVector);
    }

    @Test
    public void test_multiplyByScalar() {
        double scalar = 42;
        Vector expectedVector = new Vector(scalar, scalar);
        Vector mbsVector = vector.multiplyByScalar(scalar);

        assertEquals(expectedVector, mbsVector);
    }
}
