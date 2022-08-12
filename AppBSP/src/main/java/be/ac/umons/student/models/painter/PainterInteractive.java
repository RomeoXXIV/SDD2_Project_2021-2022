package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.Vector;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * PainterInteractive est une classe définissant le comportement de dessin de l'algorithme du peintre
 * pour l'interface graphique.
 * @author Romeo Ibraimovski
 */
public class PainterInteractive implements Paintable {

    private final Canvas canvas;

    public PainterInteractive(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void drawSegment(Segment segment, ViewPoint viewPoint) {
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        Segment drawingSegment;
        if (viewPoint.seesPartially(segment)) { // viewPoint voit un segment au moins en partie.
            if (viewPoint.seesEntirely(segment)) { // viewPoint voit entièrement un segment.
                drawingSegment = this.segmentProcessing(segment, viewPoint);
            }
            else { // viewPoint voit partiellement un segment.
                Line segmentLine = segment.toLine();
                Line upperEyelidLine = viewPoint.upperEyelidLine();
                Line lowerEyelidLine = viewPoint.lowerEyelidLine();
                Segment splitSegment; // le segment de la partie visible de segmentLine.
                if (viewPoint.seesWithoutExtremity(segment)) { // viewPoint voit un segment sans voir ses extremités.
                    splitSegment = new Segment(segmentLine.intersection(upperEyelidLine), segmentLine.intersection(lowerEyelidLine), segment.getColor());
                    drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                }
                else { // viewPoint voit un segment sans une seule de ses extremités.
                    if (segmentLine.isSecantTo(upperEyelidLine) && segmentLine.isSecantTo(lowerEyelidLine)) {
                        // segmentLine n'est pas parallèle à upperEyelidLine et lowerEyelidLine.
                        Point upperIntersection = segmentLine.intersection(upperEyelidLine);
                        Point lowerIntersection = segmentLine.intersection(lowerEyelidLine);
                        if (segment.contains(upperIntersection)) { // l'intersection avec la paupière du haut est dans segment.
                            if (upperEyelidLine.containsInOpenNegativeHalfSpace(segment.getA())) {
                                splitSegment = new Segment(upperIntersection, segment.getA(), segment.getColor());
                            }
                            else splitSegment = new Segment(upperIntersection, segment.getB(), segment.getColor());
                        }
                        else { // l'intersection avec la paupière du bas est dans segment.
                            if (lowerEyelidLine.containsInOpenPositiveHalfSpace(segment.getA())) {
                                splitSegment = new Segment(segment.getA(), lowerIntersection, segment.getColor());
                            }
                            else splitSegment = new Segment(segment.getB(), lowerIntersection, segment.getColor());
                        }
                        drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                    } // segmentLine est parallèle soit à upperEyelidLine ou soit à lowerIntersection.
                    else if (segmentLine.isSecantTo(upperEyelidLine)) {
                        // segmentLine est parallèle à lowerIntersection.
                        Point upperIntersection = segmentLine.intersection(upperEyelidLine);
                        if (upperEyelidLine.containsInOpenNegativeHalfSpace(segment.getA())) {
                            splitSegment = new Segment(upperIntersection, segment.getA(), segment.getColor());
                        }
                        else splitSegment = new Segment(upperIntersection, segment.getB(), segment.getColor());
                        drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                    }
                    else { // segmentLine est parallèle à upperIntersection.
                        Point lowerIntersection = segmentLine.intersection(lowerEyelidLine);
                        if (lowerEyelidLine.containsInOpenPositiveHalfSpace(segment.getA())) {
                            splitSegment = new Segment(segment.getA(), lowerIntersection, segment.getColor());
                        }
                        else splitSegment = new Segment(segment.getB(), lowerIntersection, segment.getColor());
                        drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                    }
                }
            }
            if (!drawingSegment.isPoint() )
                this.drawSegmentOnCanvas(graphicsContext, drawingSegment);
        }
    }

    public Segment segmentProcessing(Segment segment, ViewPoint viewPoint) {
        Point povPoint = viewPoint.getPoint();
        double segmentLength = segment.length();
        double lengthFromPovPointToPointA = (new Segment(povPoint, segment.getA())).length();
        double lengthFromPovPointToPointB = (new Segment(povPoint, segment.getB())).length();
        if (this.isRealTriangle(lengthFromPovPointToPointB, lengthFromPovPointToPointA, segmentLength)) {
            double angleC = angleC(lengthFromPovPointToPointB, lengthFromPovPointToPointA, segmentLength);
            double viewAngle = viewPoint.getViewAngle();
            double angleBeforeStartProjection;
            if (Line.equals(angleC, viewAngle))
                angleBeforeStartProjection = 0.0;
            else { // angleC < viewAngle
                Line upperEyelidLine = viewPoint.upperEyelidLine();
                if (upperEyelidLine.contains(segment.getA()) || upperEyelidLine.contains(segment.getB())) {
                    angleBeforeStartProjection = 0.0;
                }
                else { // le segment n'est pas en contact avec la paupière du haut.
                    Line lowerEyelidLine = viewPoint.lowerEyelidLine();
                    if (lowerEyelidLine.contains(segment.getA()) || lowerEyelidLine.contains(segment.getB())) {
                        // le segment est en contact avec la paupière du bas.
                        angleBeforeStartProjection = viewAngle - angleC;
                    }
                    else { // le segment n'est pas en contact avec les paupières.
                          // trouvons le point de départ du segment à projeter.
                        Point nearestPoint;
                        if (!upperEyelidLine.isSecantToLineOf(segment)) { // le segment est parallèle à upperEyelidLine.
                            // le point de départ du segment à projeter est le point le plus éloigné du povPoint.
                            if (lengthFromPovPointToPointB < lengthFromPovPointToPointA ) {
                                nearestPoint = segment.getA();
                            }
                            else
                                nearestPoint = segment.getB();
                            Point intersectionOfUpperEyelidLineAndSegmentMediator = segment.mediator().intersection(upperEyelidLine);
                            double lengthFromNearestPointToIntersection = (new Segment(nearestPoint, intersectionOfUpperEyelidLineAndSegmentMediator).length());
                            double lengthFromPovPointToNearestPoint = (new Segment(povPoint, nearestPoint).length());
                            double lengthFromPovPointToIntersection = (new Segment(povPoint, intersectionOfUpperEyelidLineAndSegmentMediator).length());
                            angleBeforeStartProjection = angleC(lengthFromPovPointToIntersection, lengthFromPovPointToNearestPoint, lengthFromNearestPointToIntersection);
                        }
                        else { // le segment n'est pas parallèle à upperEyelidLine.
                            // le point de départ du segment à projeter est le point le plus proche d'upperEyelidLine.
                            nearestPoint = upperEyelidLine.nearestPointTo(segment);
                            Point intersectionOfUpperEyelidLineAndSegmentLine = upperEyelidLine.intersection(segment.toLine());
                            double lengthFromNearestPointToIntersection = (new Segment(nearestPoint, intersectionOfUpperEyelidLineAndSegmentLine).length());
                            double lengthFromPovPointToNearestPoint = (new Segment(povPoint, nearestPoint).length());
                            double lengthFromPovPointToIntersection = (new Segment(povPoint, intersectionOfUpperEyelidLineAndSegmentLine).length());
                            angleBeforeStartProjection = angleC(lengthFromPovPointToIntersection, lengthFromPovPointToNearestPoint, lengthFromNearestPointToIntersection);
                        }
                    }
                }
            }
            return new Segment(new Point(angleBeforeStartProjection, 0.0)
                            , new Point(angleBeforeStartProjection + angleC, 0.0)
                            , segment.getColor());
        }
        else // Les points A, B et PovPoint sont colinéaires et peut-être que A et B ne sont pas distincts.
            return new Segment(povPoint, povPoint);
    }

    /**
     * Détermine si le triangle ABC est un "vrai" triangle en utilisant les inégalités triangulaires.
     * @param BC la longueur de BC.
     * @param AC la longueur de AC.
     * @param AB la longueur de AB.
     * @return true si le triangle est un "vrai" triangle, false sinon.
     */
    public boolean isRealTriangle(double BC, double AC, double AB) {
        return (BC + AC > AB) && (BC + AB > AC) && (AC + AB > BC);
    }

    /**
     * Retourne la valeur en degrés de l'angle C opposé au segment d'extrémité AB dans un triangle ABC.
     * @param BC la longueur de segment BC.
     * @param AC la longueur de segment AC.
     * @param AB la longueur de segment AB.
     * @return la valeur en degrés de l'angle opposé au segment d'extrémité AB dans un triangle ABC.
     */
    public double angleC(double BC, double AC, double AB) {
        double angle = Math.acos((BC * BC + AC * AC - AB * AB) / (2 * BC * AC));
        return Math.toDegrees(angle);
    }

    public void drawSegmentOnCanvas(GraphicsContext graphicsContext, Segment segment) {
        double widthCanvas = this.canvas.getWidth();
        double heightCanvas = this.canvas.getHeight();
        Color color = awtColorToPaintColor(segment.getColor());
        double xA = 140 + segment.getA().getX() * 5.555555;
        double yA = 61; // segment.getA().getY() + heightCanvas / 2.
        double xB = 140 + segment.getB().getX() * 5.555555;
        double yB = 61; //segment.getB().getY() + heightCanvas / 2.
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(xA, yA, xB, yB);
    }

    public static Color awtColorToPaintColor(java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        double opacity = color.getAlpha() / 255.0;
        return Color.rgb(red, green, blue, opacity);
    }
}
