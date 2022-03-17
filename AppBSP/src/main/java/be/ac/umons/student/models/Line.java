package be.ac.umons.student.models;

import java.util.ArrayList;

public class Line {

    private final double alpha, beta, gamma;

    public Line(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = (alpha == 0 && beta == 0) ? 0 : gamma;
    }

    public Line(Point a, Point b) {
        Vector normalVector = getNormalVector(a, b);
        if (normalVector.getX() == 0 && normalVector.getY() == 0)
            this.alpha = this.beta = this.gamma = 0;
        else {
            this.alpha = normalVector.getX();
            this.beta = normalVector.getY();
            this.gamma = -normalVector.getX() * a.getX() - normalVector.getY() * a.getY();
        }
    }

    /**
     * Retourne le point d'intersection de deux droites <u>sécantes</u>.
     *
     * @param line la seconde droite.
     * @return le point d'intersection de la droite avec la seconde droite.
     */
    public Point intersection(Line line) {
        double n1 = this.beta * line.gamma - line.beta * this.gamma;
        double n2 = line.alpha * this.gamma - this.alpha * line.gamma;
        double d = line.alpha * this.beta - this.alpha * line.beta;
        double x = n1/d;
        double y = n2/d;
        return new Point(x, y);
    }

    /**
     * Retourne le point d'intersection d'une droite avec un segment <u>sécant</u>.
     *
     * @param segment le segment.
     * @return le point d'intersection de la droite avec le segment.
     */
    public Point intersection(Segment segment) {
        return this.intersection(segment.toLine());
    }

    /**
     * Retourne true si le segment se trouve dans le demi-espace négatif ouvert par rapport à la droite.
     *
     * @param segment le segment.
     * @return true si le segment se trouve dans le demi-espace négatif ouvert, false sinon.
     */
    public Boolean inOpenNegativeHalfSpace(Segment segment) {
        return this.inOpenNegativeHalfSpace(segment.getA()) && this.inOpenNegativeHalfSpace(segment.getB());
    }

    /**
     * Retourne true si le point se trouve dans le demi-espace négatif ouvert par rapport à la droite.
     *
     * @param point le point.
     * @return true si le point se trouve dans le demi-espace négatif ouvert, false sinon.
     */
    public Boolean inOpenNegativeHalfSpace(Point point) {
        return this.alpha * point.getX() + this.beta * point.getY() + this.gamma < 0;
    }

    /**
     * Retourne une liste de segments confondus à la droite parmi une liste de segments.
     *
     * @param segments une liste de segments.
     * @return la liste de segments confondus à la droite.
     */
    public ArrayList<Segment> contains(ArrayList<Segment> segments) {
        ArrayList<Segment> segmentsInLine = new ArrayList<>();
        for (Segment segment: segments)
            if (this.isContained(segment))
                segmentsInLine.add(segment);
        return segmentsInLine;
    }

    /**
     * Retourne true si le segment appartient à la droite.
     *
     * @param segment le segment.
     * @return true si le segment appartient à la droite, false sinon.
     */
    public Boolean isContained(Segment segment) {
        return this.isContained(segment.getA()) && this.isContained(segment.getB());
    }

    /**
     * Retourne true si le point appartient à la droite.
     *
     * @param point le point.
     * @return true si le point appartient à la droite, false sinon.
     */
    public Boolean isContained(Point point) {
        double epsilon = 0.000001d;
        return Math.abs(this.alpha * point.getX() + this.beta * point.getY() + this.gamma) < epsilon;
    }

    /**
     * Retourne un vecteur directeur d’une droite passant par les points a et b.
     *
     * @param a le premier point où passe la droite.
     * @param b le second point où passe la droite.
     * @return un vecteur directeur de la droite.
     */
    public static Vector getDirectorVector(Point a, Point b) {
        double x_d = a.getX() - b.getX();
        double y_d = a.getY() - b.getY();
        return new Vector(x_d, y_d);
    }

    /**
     * Retourne un vecteur normal d’une droite passant par les points a et b.
     *
     * @param a le premier point où passe la droite.
     * @param b le second point où passe la droite.
     * @return un vecteur normal de la droite.
     */
    public static Vector getNormalVector(Point a, Point b) {
        Vector directorVector = getDirectorVector(a, b);
        double x_n = -directorVector.getY();
        double y_n = directorVector.getX();
        return new Vector(x_n, y_n);
    }

    public double getAlpha() {
        return alpha;
    }

    public double getBeta() {
        return beta;
    }

    public double getGamma() {
        return gamma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;
        double epsilon = 0.000001d;

        if(Math.abs(gamma - 0) < epsilon && Math.abs(line.gamma - 0) < epsilon) {
            return ((Math.abs(line.alpha - alpha) < epsilon) && (Math.abs(line.beta - beta) < epsilon))
                    || ((Math.abs(line.alpha + alpha) < epsilon) && (Math.abs(line.beta + beta) < epsilon));
        }
        else {
            if (Math.abs(line.alpha - alpha) > epsilon) return false;
            if (Math.abs(line.beta - beta) > epsilon) return false;
            return Math.abs(line.gamma - gamma) < epsilon;
        }
    }

    @Override
    public String toString() {
        return "Line@" + Integer.toHexString(hashCode()) + "{" +
                "alpha=" + alpha +
                ", beta=" + beta +
                ", gamma=" + gamma +
                '}';
    }
}
