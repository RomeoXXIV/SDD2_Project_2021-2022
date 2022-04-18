package be.ac.umons.student.models;

/**
 * Représente un vecteur (x, y) , vecteur directeur ou normal de droite dans le plan. x et y étant des doubles.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class Vector {

    private final double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point a, Point b) {
        this.x = b.getX() - a.getX();
        this.y = b.getY() - a.getY();
    }

    /**
     * Retourne la norme d'un vecteur.
     *
     * @return la norme du vecteur.
     */
    public double norm() {
        return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    /**
     * Retourne le vecteur opposé d'un vecteur.
     *
     * @return le vecteur opposé du vecteur.
     */
    public Vector opposite() {
        return this.multiplyByScalar(-1);
    }

    /**
     * Retourne le vecteur résultant de la somme de deux vecteurs.
     *
     * @return le vecteur résultant de la somme des vecteurs.
     */
    public Vector sum(Vector v) {
        return new Vector(this.x + v.getX(), this.y + v.getY());
    }

    /**
     * Retourne le vecteur résultant de la différence de deux vecteurs.
     *
     * @return le vecteur résultant de la différence des vecteurs.
     */
    public Vector difference(Vector v) {
        return new Vector(this.x - v.getX(), this.y - v.getY());
    }

    /**
     * Retourne le vecteur résultant de la multiplication d'un vecteur par un scalaire.
     *
     * @return le vecteur résultant de la multiplication du vecteur par le scalaire.
     */
    public Vector multiplyByScalar(double scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
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

        Vector vector = (Vector) o;
        double epsilon = 0.0001;

        if (Math.abs(vector.x - x) > epsilon) return false;
        return Math.abs(vector.y - y) < epsilon;
    }

    @Override
    public String toString() {
        return "Vector@" + Integer.toHexString(hashCode()) + "{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
