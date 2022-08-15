package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

/**
 * ViewPoint est une classe représentant un point de vue possédant des coordonnées, un angle de vue et un angle de rotation.
 * @author Romeo Ibraimovski
 */
public class ViewPoint {

    private final Point point;
    private final double viewAngle, rotateAngle;

    public ViewPoint(Point point, double viewAngle, double rotateAngle) {
        this.point = point;
        this.viewAngle = viewAngle;
        this.rotateAngle = rotateAngle;
    }

    public ViewPoint() {
        this.point = new Point(0, 0);
        this.viewAngle = 90;
        this.rotateAngle = 0;
    }

    /**
     * Détermine si le point de vue voit un point.
     * @param point Le point à tester.
     * @return true si le point de vue voit le point, false sinon.
     */
    public boolean sees(Point point) {
        if (Line.equals(this.viewAngle, Line.ZERO)) return false;
        Line upperEyelidLine = this.upperEyelidLine();
        if (Line.equals(this.viewAngle, 180.)) {
            return upperEyelidLine.containsNotStrictlyInOpenNegativeHalfSpace(point);
        }
        else {
            Line lowerEyelidLine = this.lowerEyelidLine();
            return upperEyelidLine.containsNotStrictlyInOpenNegativeHalfSpace(point)
                    && lowerEyelidLine.containsNotStrictlyInOpenPositiveHalfSpace(point);
        }
    }

    /**
     * Détermine si le point de vue voit entièrement un segment.
     * @param segment Le segment à tester.
     * @return true si le point de vue voit entièrement le segment, false sinon.
     */
    public boolean seesEntirely(Segment segment) {
        return this.sees(segment.getA()) && this.sees(segment.getB());
    }

    /**
     * Détermine si le point de vue voit un segment sans ses extrémités.
     * @param segment Le segment à tester.
     * @return true si le point de vue voit le segment sans ses extrémités, false sinon.
     */
    public boolean seesWithoutExtremity(Segment segment) {
        Line upperEyelidLine = upperEyelidLine();
        Line lowerEyelidLine = lowerEyelidLine();
        Line segmentLine = segment.toLine();
        if (segmentLine.isSecantTo(upperEyelidLine) && segmentLine.isSecantTo(lowerEyelidLine)) {
            Point upperIntersection = segmentLine.intersection(upperEyelidLine);
            Point lowerIntersection = segmentLine.intersection(lowerEyelidLine);
            return segment.contains(upperIntersection) && segment.contains(lowerIntersection) && !this.seesEntirely(segment);
        }
        return false;
    }

    /**
     * Détermine si le point de vue voit un segment au moins en partie.
     * @param segment Le segment à tester.
     * @return true si le point de vue voit le segment au moins en partie, false sinon.
     */
    public boolean seesPartially(Segment segment) {
        return this.sees(segment.getA()) || this.sees(segment.getB()) || this.seesWithoutExtremity(segment);
    }

    /**
     * Détermine si le point de vue voit un segment que partiellement.
     * @param segment Le segment à tester.
     * @return true si le point de vue voit le segment que partiellement, false sinon.
     */
    public boolean seesOnlyPartially(Segment segment) {
        return (this.sees(segment.getA()) ^ this.sees(segment.getB())) || this.seesWithoutExtremity(segment);
    }

    /**
     * Retourne la droite associée à la paupière du haut
     * @return la droite associée à la paupière du haut
     */
    public Line upperEyelidLine() {
        double upperEyelidLineX = this.point.getX() + 12 * Math.cos(Math.toRadians((viewAngle / 2) + rotateAngle));
        double upperEyelidLineY = this.point.getY() + 12 * Math.sin(Math.toRadians((viewAngle / 2) + rotateAngle));
        return new Line(this.point, new Point(upperEyelidLineX, upperEyelidLineY));
    }

    /**
     * Retourne la droite associée à la paupière du bas
     * @return la droite associée à la paupière du bas
     */
    public Line lowerEyelidLine(){
        double lowerEyelidLineX = this.point.getX() + 12 * Math.cos(Math.toRadians((-viewAngle / 2) + rotateAngle));
        double lowerEyelidLineY = this.point.getY() + 12 * Math.sin(Math.toRadians((-viewAngle / 2) + rotateAngle));
        return new Line(this.point, new Point(lowerEyelidLineX, lowerEyelidLineY));
    }

    public Point getPoint() {
        return point;
    }

    public double getViewAngle() {
        return viewAngle;
    }

    public double getRotateAngle() {
        return rotateAngle;
    }

    @Override
    public String toString() {
        return "ViewPoint@" + Integer.toHexString(hashCode()) + "{" +
                "point=" + point +
                ", viewAngle=" + viewAngle +
                ", rotateAngle=" + rotateAngle +
                '}';
    }
}
