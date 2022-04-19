package be.ac.umons.student.models;

import java.awt.Color;
import java.lang.Math;

/**
 * Représente un segment dans un espace coordonné. Un segment possède une couleur et deux point étant ses extrémités. On peut transformer ce segment
 * en droite.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
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

    /**
     * Retourne vrai si le segment contient le point
     *
     * @param point
     * @return vrai si le segment contient le point, faux sinon
     */
    public boolean contains(Point point){
        Vector segDirector = Line.getDirectorVector(this.getA(), this.getB());
        Segment newSeg = new Segment(this.getA(), point);
        Vector newSegDirector = Line.getDirectorVector(newSeg.getA(), newSeg.getB());
        if (this.getA().equals(point) || this.getB().equals(point)) return true;
        if(newSeg.isPoint())
            return true;
        if(Math.signum(segDirector.getX()) == Math.signum(1) && Math.signum(newSegDirector.getX()) == Math.signum(1))
            return  segDirector.getX() % newSegDirector.getX() == 0 && segDirector.getY() % newSegDirector.getY() == 0;
        if(Math.signum(segDirector.getX()) == Math.signum(-1) && Math.signum(newSegDirector.getX()) == Math.signum(-1))
            return  segDirector.getX() % newSegDirector.getX() == 0 && segDirector.getY() % newSegDirector.getY() == 0;
        return false;
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
