package be.ac.umons.student.models;

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
        double epsilon = 0.0001;

        if (Math.abs(point.x - x) > epsilon) return false;
        return Math.abs(point.y - y) < epsilon;
    }

    @Override
    public String toString() {
        return "Point@" + Integer.toHexString(hashCode()) + "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
