package be.ac.umons.student.models;

public class Line {

    private final double alpha, beta, gamma;

    public Line(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    /**
     * Retourne le point d'intersection de deux droites <u>sécantes</u>.
     *
     * @param d1 la première droite.
     * @param d2 la seconde droite.
     * @return le point d'intersection des droites d1 et d2.
     */
    public static Point intersection(Line d1, Line d2) {
        double n1 = d1.beta * d2.gamma - d2.beta * d1.gamma;
        double n2 = d2.alpha * d1.gamma - d1.alpha * d2.gamma;
        double d = d2.alpha * d1.beta - d1.alpha * d2.beta;
        double x = n1/d;
        double y = n2/d;
        return new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;
        double epsilon = 0.000001d;

        if (Math.abs(line.alpha - alpha) > epsilon) return false;
        if (Math.abs(line.beta - beta) > epsilon) return false;
        return Math.abs(line.gamma - gamma) < epsilon;
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
}
