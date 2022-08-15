package be.ac.umons.student.models;

import java.util.ArrayList;

/**
 * Line est une classe représentant une droite comme alpha * x + beta * y + c = 0 dans un espace coordonné.
 * @author Romeo Ibraimovski
 */
public class Line {

    public static final double EPSILON = 0.00000001;
    public static final double ZERO = 0.0;

    private final double alpha, beta, gamma;

    public Line(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = (equals(alpha, ZERO) && equals(beta, ZERO)) ? 0 : gamma;
    }

    public Line(Point a, Point b) {
        Vector normalVector = normalVector(a, b);
        if (equals(normalVector.getX(), ZERO) && equals(normalVector.getY(), ZERO))
            this.alpha = this.beta = this.gamma = 0;
        else {
            this.alpha = normalVector.getX();
            this.beta = normalVector.getY();
            this.gamma = -normalVector.getX() * a.getX() - normalVector.getY() * a.getY();
        }
    }

    /**
     * Retourne le point d'intersection de deux droites <u>sécantes</u>.
     * @param line la seconde droite.
     * @return le point d'intersection de la droite avec la seconde droite.
     */
    public Point intersection(Line line) {
        double x;
        double y;
        if (!this.isVertical() && !line.isVertical()) {
            // Aucune des droites n'est verticale.
            double thisSlope = this.slope();
            double lineSlope = line.slope();
            double thisOrdinate = this.ordinate();
            double lineOrdinate = line.ordinate();
            x = (lineOrdinate - thisOrdinate) / (thisSlope - lineSlope);
            y = thisSlope * x + thisOrdinate;
        }
        else if (this.isVertical()) {
            // La droite this est verticale.
            x = - this.gamma / this.alpha;
            y = line.slope() * x + line.ordinate();
        }
        else { // La droite line est verticale.
            x = - line.gamma / line.alpha;
            y = this.slope() * x + this.ordinate();
        }
        return new Point(x, y);
    }

    /**
     * Retourne le point d'intersection d'une droite avec un segment <u>sécant</u>.
     * @param segment le segment.
     * @return le point d'intersection de la droite avec le segment.
     */
    public Point intersection(Segment segment) {
        return this.intersection(segment.toLine());
    }

    /**
     * Retourne la pente d'une droite non verticale.
     * @return la pente d'une droite non verticale.
     */
    public double slope() {
        return - this.alpha / this.beta;
    }

    /**
     * Retourne l'ordonnée à l'origine d'une droite non verticale.
     * @return l'ordonnée à l'origine d'une droite non verticale.
     */
    public double ordinate() {
        return - this.gamma / this.beta;
    }

    /**
     * Détermine si la droite est verticale.
     * @return true si la droite est verticale, false sinon.
     */
    public boolean isVertical() {
        return equals(this.beta, ZERO);
    }

    /**
     * Détermine si la droite est sécante à une autre droite.
     * @param line la seconde droite.
     * @return true si les droites sont sécantes, false sinon.
     */
    public boolean isSecantTo(Line line) {
        if (!this.isVertical() && !line.isVertical()) {
            // Aucune des droites n'est verticale.
            double thisSlope = this.slope();
            double lineSlope = line.slope();
            return !equals(thisSlope, lineSlope);
        }
        else
            // Une des droites est verticale et l'autre non.
            return !this.isVertical() || !line.isVertical();
    }

    /**
     * Détermine si la droite est sécante à la droite correspondante au segment.
     * @param segment le segment.
     * @return true si la droite est sécante à la droite correspondante au segment, false sinon.
     */
    public boolean isSecantToLineOf(Segment segment) {
        return this.isSecantTo(segment.toLine());
    }

    /**
     * Détermine si le segment se trouve dans le demi-espace négatif ouvert par rapport à la droite.
     * @param segment le segment.
     * @return true si le segment se trouve dans le demi-espace négatif ouvert, false sinon.
     */
    public Boolean containsInOpenPositiveHalfSpace(Segment segment) {
        return this.containsInOpenPositiveHalfSpace(segment.getA()) && this.containsInOpenPositiveHalfSpace(segment.getB());
    }

    /**
     * Détermine si le segment se trouve dans le demi-espace négatif ouvert non strictement par rapport à la droite.
     * @param segment le segment.
     * @return true si le segment se trouve dans le demi-espace négatif ouvert non strictement, false sinon.
     */
    public Boolean containsInClosePositiveHalfSpace(Segment segment) {
        return this.containsInClosePositiveHalfSpace(segment.getA()) && this.containsInClosePositiveHalfSpace(segment.getB());
    }

    /**
     * Détermine si le point se trouve dans le demi-espace négatif ouvert par rapport à la droite.
     * @param point le point.
     * @return true si le point se trouve dans le demi-espace négatif ouvert, false sinon.
     */
    public Boolean containsInOpenPositiveHalfSpace(Point point) {
        return this.alpha * point.getX() + this.beta * point.getY() + this.gamma > 0;
    }

    /**
     * Détermine si le point se trouve dans le demi-espace négatif ouvert non strictement par rapport à la droite.
     * @param point le point.
     * @return true si le point se trouve dans le demi-espace négatif ouvert non strictement, false sinon.
     */
    public Boolean containsInClosePositiveHalfSpace(Point point) {
        return containsInOpenPositiveHalfSpace(point) || equals(this.alpha * point.getX() + this.beta * point.getY() + this.gamma, Line.ZERO);
    }

    /**
     * Détermine si le segment se trouve dans le demi-espace positif ouvert par rapport à la droite.
     * @param segment le segment.
     * @return true si le segment se trouve dans le demi-espace positif ouvert, false sinon.
     */
    public Boolean containsInOpenNegativeHalfSpace(Segment segment) {
        return this.containsInOpenNegativeHalfSpace(segment.getA()) && this.containsInOpenNegativeHalfSpace(segment.getB());
    }

    /**
     * Détermine si le segment se trouve dans le demi-espace positif ouvert non strictement par rapport à la droite.
     * @param segment le segment.
     * @return true si le segment se trouve dans le demi-espace positif ouvert non strictement, false sinon.
     */
    public Boolean containsInCloseNegativeHalfSpace(Segment segment) {
        return this.containsInCloseNegativeHalfSpace(segment.getA()) && this.containsInCloseNegativeHalfSpace(segment.getB());
    }

    /**
     * Détermine si le point se trouve dans le demi-espace positif ouvert par rapport à la droite.
     * @param point le point.
     * @return true si le point se trouve dans le demi-espace positif ouvert, false sinon.
     */
    public Boolean containsInOpenNegativeHalfSpace(Point point) {
        return this.alpha * point.getX() + this.beta * point.getY() + this.gamma < 0;
    }

    /**
     * Détermine si le point se trouve dans le demi-espace positif ouvert non strictement par rapport à la droite.
     * @param point le point.
     * @return true si le point se trouve dans le demi-espace positif ouvert non strictement, false sinon.
     */
    public Boolean containsInCloseNegativeHalfSpace(Point point) {
        return containsInOpenNegativeHalfSpace(point) || equals(this.alpha * point.getX() + this.beta * point.getY() + this.gamma, Line.ZERO);
    }

    /**
     * Retourne une liste de segments confondus à la droite parmi une liste de segments.
     * @param segments une liste de segments.
     * @return la liste de segments confondus à la droite.
     */
    public ArrayList<Segment> contentSegments(ArrayList<Segment> segments) {
        ArrayList<Segment> segmentsInLine = new ArrayList<>();
        for (Segment segment: segments)
            if (this.contains(segment))
                segmentsInLine.add(segment);
        return segmentsInLine;
    }

    /**
     * Détermine si le segment appartient à la droite.
     * @param segment le segment.
     * @return true si le segment appartient à la droite, false sinon.
     */
    public Boolean contains(Segment segment) {
        return this.contains(segment.getA()) && this.contains(segment.getB());
    }

    /**
     * Détermine si le point appartient à la droite.
     * @param point le point.
     * @return true si le point appartient à la droite, false sinon.
     */
    public Boolean contains(Point point) {
        return equals(this.alpha * point.getX() + this.beta * point.getY() + this.gamma, ZERO);
    }

    /**
     * Retourne la distance entre la droite et un point.
     * @param point le point.
     * @return la distance entre la droite et le point.
     */
    public double distanceTo(Point point) {
        return Math.abs(this.alpha * point.getX() + this.beta * point.getY() + this.gamma) / Math.sqrt(this.alpha * this.alpha + this.beta * this.beta);
    }

    /**
     * Retourne le point le plus proche de la droite d'un segment qui n'est pas parallèle avec la droite.
     * @param segment le segment.
     * @return le point le plus proche de la droite d'un segment.
     */
    public Point nearestPointTo(Segment segment) {
        double distanceA = this.distanceTo(segment.getA());
        double distanceB = this.distanceTo(segment.getB());
        if (distanceA < distanceB)
            return segment.getA();
        else
            return segment.getB();
    }

    /**
     * Retourne un vecteur directeur d’une droite passant par les points a et b.
     * @param a le premier point où passe la droite.
     * @param b le second point où passe la droite.
     * @return un vecteur directeur de la droite.
     */
    public static Vector directorVector(Point a, Point b) {
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
    public static Vector normalVector(Point a, Point b) {
        Vector directorVector = directorVector(a, b);
        double x_n = -directorVector.getY();
        double y_n = directorVector.getX();
        return new Vector(x_n, y_n);
    }

    /**
     * Détermine si les doubles a et b sont égales à EPSILON = 10e-9 près.
     * @param a un double.
     * @param b un double.
     * @return true si les doubles a et b sont égales à EPSILON = 10e-9 près, false sinon.
     */
    public static boolean equals(Double a, Double b) {
        return Math.abs(a - b) < EPSILON;
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
        if(equals(gamma, ZERO) && equals(line.gamma, ZERO)) {
            return (equals(alpha, line.alpha) && equals(beta, line.beta))
                    || (equals(-alpha, line.alpha) && equals(-beta, line.beta));
                    // au cas ou, anciennement -> ((Math.abs(line.alpha + alpha) < EPSILON) && (Math.abs(line.beta + beta) < EPSILON));
        }
        else {
            if (!equals(alpha, line.alpha)) return false;
            if (!equals(beta, line.beta)) return false;
            return equals(gamma, line.gamma);
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
