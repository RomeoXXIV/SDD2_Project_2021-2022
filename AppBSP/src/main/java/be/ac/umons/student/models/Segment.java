package be.ac.umons.student.models;

import java.awt.Color;
import java.lang.Math;

/**
 * Segment est une classe représentant un segment dans un espace coordonné.
 * Un segment possède une couleur et deux point étant ses extrémités.
 * @author Romeo Ibraimovski
 */
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

    public Point midPoint() {
        return new Point((this.a.getX() + this.b.getX()) / 2, (this.a.getY() + this.b.getY()) / 2);
    }

    public Line mediator() {
        Point midPoint = this.midPoint();
        double alpha = this.getB().getX() - this.getA().getX();
        double beta = this.getB().getY() - this.getA().getY();
        double gamma = - alpha * midPoint.getX() - beta * midPoint.getY();
        return new Line(alpha, beta, gamma);
    }

    /**
     * Détermine si le segment contient un point. La longueur du segment doit être strictement supérieur à 0.
     * @param point le point à tester.
     * @return true si le segment contient le point, false sinon.
     */
    public boolean contains(Point point){
        Line line = this.toLine();
        if (line.contains(point))
            return ( Math.min(this.a.getX(), this.b.getX()) <= point.getX() && point.getX() <= Math.max(this.a.getX(), this.b.getX()) )
                    && ( Math.min(this.a.getY(), this.b.getY()) <= point.getY() && point.getY() <= Math.max(this.a.getY(), this.b.getY()) );
        else // Le point n'est même pas sur la droite.
            return false;
    }

    /**
     * Retourne la droite correspondante au segment.
     * @return la droite correspondante au segment.
     */
    public Line toLine() {
        return new Line(this.a, this.b);
    }

    /**
     * Retourne un tableau de deux segments correspondant au partitionnement du segment en deux à partir <u>d'un point du segment</u>.
     * @param point le point appartenant au segment.
     * @return le tableau de deux segments.
     */
    public Segment[] split(Point point) {
        Segment firstSegment = new Segment(this.a, point, this.color);
        Segment secondSegment = new Segment(point, this.b, this.color);
        return new Segment[] {firstSegment, secondSegment};
    }

    /**
     * Détermine si le segment a pour extrémité un point.
     * @param point le point à tester.
     * @return true si le segment a pour extrémité le point, false sinon.
     */
    public boolean hasForExtremity(Point point) {
        return this.a.equals(point) || this.b.equals(point);
    }

    /**
     * Détermine si le segment est un point, c-à-d qu'il ne possède pas deux extrémités distinctes.
     * @return true si le segment est un point, false sinon.
     */
    public Boolean isPoint() {
        return this.length() == 0.;
    }

    /**
     * Retourne la longueur d'un segment à partir de ses extrémités.
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
