package be.ac.umons.student.models;

import java.awt.Color;

public class Segment {

    private static final Color DEFAULT_COLOR = Color.BLACK;

    private final Point a, b;
    private final Color color;

    public Segment(Point a, Point b, Color color) {
        this.a = a;
        this.b = b;
        this.color = color;
    }

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
        this.color = DEFAULT_COLOR;
    }

    /**
     * Retourne la droite correspondante au segment.
     *
     * @return la droite correspondante au segment.
     */
    public Line toLine() {
        return new Line(this.a, this.b);
    }

    /**
     * Retourne un tableau de deux segments correspondant au partitionnement du segment en deux à partir <u>d'un point du segment</u>.
     *
     * @param point le point appartenant au segment.
     * @return le tableau de deux segments.
     */
    public Segment[] split(Point point) {
        Segment firstSegment = new Segment(this.a, point, this.color);
        Segment secondSegment = new Segment(point, this.b, this.color);
        return new Segment[] {firstSegment, secondSegment};
    }

    /**
     * Retourne true si le segment est un point, c-à-d qu'il ne possède pas deux extrémités distinctes.
     *
     * @return true si le segment est un point, false sinon.
     */
    public Boolean isPoint() {
        return this.length() == 0.;
    }

    /**
     * Retourne la longueur d'un segment à partir de ses extrémités.
     *
     * @return la longueur du segment.
     */
    public double length() {
        return Math.sqrt(Math.pow(this.b.getX() - this.a.getX(), 2) + Math.pow(this.b.getY() - this.a.getY(), 2));
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Segment segment = (Segment) o;

        if (!a.equals(segment.a)) return false;
        if (!b.equals(segment.b)) return false;
        return color.equals(segment.color);
    }

    @Override
    public String toString() {
        return "Segment@" + Integer.toHexString(hashCode()) + "{" +
                "a=" + a +
                ", b=" + b +
                ", color=" + color +
                '}';
    }
}
