package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

/**
 * Implémentation du point de vue qui a un angle de vue, des coordonnées et un angle de rotation.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
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
        this.viewAngle = 100;
        this.rotateAngle = 0;
    }

    /**
     * Retourne vrai si le point de vue voit le point , faux sinon
     *
     * @param point
     * @return vrai si le point de vue voit le point, faux sinon
     */
    public boolean sees(Point point) {
        // TODO JavaDoc + Testing
        if (this.viewAngle == 0.) return false;
        double upperEyelidLineX = 12 * Math.cos(Math.toRadians((-viewAngle / 2) + rotateAngle));
        double upperEyelidLineY = 12 * Math.sin(Math.toRadians((-viewAngle / 2) + rotateAngle));
        Line upperEyelidLine = new Line(this.point, new Point(upperEyelidLineX, upperEyelidLineY));
        if (this.viewAngle == 180.) {
            return upperEyelidLine.contains(point) || upperEyelidLine.containsInOpenNegativeHalfSpace(point);
        }
        else {
            double lowerEyelidLineX = 12 * Math.cos(Math.toRadians((viewAngle / 2) + rotateAngle));
            double lowerEyelidLineY = 12 * Math.sin(Math.toRadians((viewAngle / 2) + rotateAngle));
            Line lowerEyelidLine = new Line(this.point, new Point(lowerEyelidLineX, lowerEyelidLineY));
            return (upperEyelidLine.contains(point) || upperEyelidLine.containsInOpenNegativeHalfSpace(point))
                    && (lowerEyelidLine.contains(point) || lowerEyelidLine.containsInOpenPositiveHalfSpace(point));
        }
    }

    /**
     * Retourne vrai si le point de vue voit un segment mais pas ses extrémités faux sinon
     *
     * @param segment
     * @return vrai si le point de vue voit le segment sans ses extrémités, faut sinon
     */
    public boolean seesWithoutExtremity(Segment segment) {
        // TODO JavaDoc + Testing note : && !this.seesEntirely(segment) removed from the condition check when testing
        Line upperEyelidLine = getUpperEyelidLine();
        Line lowerEyelidLine = getLowerEyelidLine();
        Line segmentLine = segment.toLine();
        if (segmentLine.isSecantTo(upperEyelidLine) && segmentLine.isSecantTo(lowerEyelidLine)) {
            Point upperIntersection = segmentLine.intersection(upperEyelidLine);
            Point lowerIntersection = segmentLine.intersection(lowerEyelidLine);
            return segment.contains(upperIntersection) && segment.contains(lowerIntersection);
        }
        return false;
    }


    /**
     * Retourne vrai si le point de vue voit entièrement le segment, faux sinon
     *
     * @param segment
     * @return vrai si le point de vue voit entièrement le segment faux sinon
     */
    public boolean seesEntirely(Segment segment) {
        return this.sees(segment.getA()) && this.sees(segment.getB());
    }

    /**
     * Retourne vrai si le point de vue voit le segment en partie
     *
     * @param segment
     * @return vrai si le point de vue voit le segment en partie
     */
    public boolean seesPartially(Segment segment) {
        return this.sees(segment.getA()) || this.sees(segment.getB()) || this.seesWithoutExtremity(segment);
    }

    /**
     * Retourne la droite associée à la paupière du haut
     *
     * @return la droite associée à la paupière du haut
     */
    public Line getUpperEyelidLine() {
        double upperEyelidLineX = 12 * Math.cos(Math.toRadians((-viewAngle / 2) + rotateAngle));
        double upperEyelidLineY = 12 * Math.sin(Math.toRadians((-viewAngle / 2) + rotateAngle));
        return new Line(this.point, new Point(upperEyelidLineX, upperEyelidLineY));
    }

    /**
     * Retourne la droite associée à la paupière du bas
     *
     * @return la droite associée à la paupière du bas
     */
    public Line getLowerEyelidLine(){
        double lowerEyelidLineX = 12 * Math.cos(Math.toRadians((viewAngle / 2) + rotateAngle));
        double lowerEyelidLineY = 12 * Math.sin(Math.toRadians((viewAngle / 2) + rotateAngle));
        return  new Line(this.point, new Point(lowerEyelidLineX, lowerEyelidLineY));
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
