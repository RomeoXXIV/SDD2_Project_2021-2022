package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Point;

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
