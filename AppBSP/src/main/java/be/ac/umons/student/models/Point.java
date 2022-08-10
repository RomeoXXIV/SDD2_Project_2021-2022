package be.ac.umons.student.models;

/**
 * Point est une classe représentant un emplacement dans le plan par une coordonnée X et une coordonnée Y permettant de le situer.
 * @author Ibraimovski Romeo
 */
public class Point {

    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (!Line.equals(x, point.x)) return false;
        return Line.equals(y, point.y);
    }

    @Override
    public String toString() {
        return "Point@" + Integer.toHexString(hashCode()) + "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
