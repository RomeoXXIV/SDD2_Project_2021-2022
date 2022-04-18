import be.ac.umons.student.models.painter.ViewPoint;
import be.ac.umons.student.models.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ViewPointTest {

    private final ViewPoint notNullPov = new ViewPoint();
    private final ViewPoint nullPov = new ViewPoint(new Point(0, 0), 0, 0);
    private final ViewPoint hundEightyPov = new ViewPoint(new Point(0, 0), 180, 0);
    private final Point point = new Point(1, 1);
    private final Point unseenPoint = new Point(-1, 0);

    @Test
    public void test_sees(){
        assertFalse(nullPov.sees(point));
        assertTrue(notNullPov.sees(point));
        assertFalse(notNullPov.sees(unseenPoint));
        double upperEyeX = 12 * Math.cos(Math.toRadians((- notNullPov.getViewAngle() / 2) + notNullPov.getRotateAngle()));
        double upperEyeY = 12 * Math.sin(Math.toRadians((- notNullPov.getViewAngle() / 2) + notNullPov.getRotateAngle()));
        Point upperEyeLid = new Point(upperEyeX, upperEyeY);
        assertTrue(notNullPov.sees(upperEyeLid));
    }

}
