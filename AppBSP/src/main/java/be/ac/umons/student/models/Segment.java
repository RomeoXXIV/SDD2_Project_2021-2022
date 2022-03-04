package be.ac.umons.student.models;

public class Segment {

    private final Point a, b;
    private final double length;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
        this.length = length(a, b);
    }

    /**
     * Retourne la longueur d'un segment à partir de ses extrémités.
     *
     * @param a la première extrémité du segment.
     * @param b la seconde extrémité du segment.
     * @return la longueur du segment d'extrémités a et b.
     */
    public static double length(Point a, Point b) {
        return Math.sqrt(Math.pow(b.getX() - a.getX(), 2) + Math.pow(b.getY() - a.getY(), 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment segment = (Segment) o;
        double epsilon = 0.000001d;

        if (Math.abs(segment.length - length) > epsilon) return false;
        if (!a.equals(segment.a)) return false;
        return b.equals(segment.b);
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public double getLength() {
        return length;
    }
}
