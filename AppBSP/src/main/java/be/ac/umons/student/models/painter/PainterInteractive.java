package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Line;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Implémentation de l'interface Paintable pour le mode graphique, permetant donc d'afficher à l'écran ce que l'oeil voit.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class PainterInteractive implements Paintable { // TODO to complete

    private final Canvas canvas;

    public PainterInteractive(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void drawSegment(Segment segment, ViewPoint viewPoint) {
        // TODO to finish/verify
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        Segment drawingSegment;
        if (viewPoint.seesPartially(segment)) {
            if (viewPoint.seesEntirely(segment)) {
                drawingSegment = this.segmentProcessing(segment, viewPoint);
                this.drawSegmentOnCanvas(graphicsContext, drawingSegment);
            }
            else {
                Line segmentLine = segment.toLine();
                Line upperEyelidLine = viewPoint.getUpperEyelidLine();
                Line lowerEyelidLine = viewPoint.getLowerEyelidLine();
                Segment splitSegment;
                if (viewPoint.seesWithoutExtremity(segment)) {
                    splitSegment = new Segment(segmentLine.intersection(upperEyelidLine), segmentLine.intersection(lowerEyelidLine), segment.getColor());
                    drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                    this.drawSegmentOnCanvas(graphicsContext, drawingSegment);
                }
                else { // really sees partially
                    if (segmentLine.isSecantTo(upperEyelidLine) && segmentLine.isSecantTo(lowerEyelidLine)) {
                        Point upperIntersection = segmentLine.intersection(upperEyelidLine);
                        Point lowerIntersection = segmentLine.intersection(lowerEyelidLine);
                        if (segment.contains(upperIntersection)) {
                            if (upperEyelidLine.containsInOpenNegativeHalfSpace(segment.getA())) {
                                splitSegment = new Segment(upperIntersection, segment.getA(), segment.getColor());
                            }
                            else splitSegment = new Segment(upperIntersection, segment.getB(), segment.getColor());
                        }
                        else { // segment.contains(lowerIntersection)
                            if (lowerEyelidLine.containsInOpenPositiveHalfSpace(segment.getA())) {
                                splitSegment = new Segment(segment.getA(), lowerIntersection, segment.getColor());
                            }
                            else splitSegment = new Segment(segment.getB(), lowerIntersection, segment.getColor());
                        }
                        drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                        this.drawSegmentOnCanvas(graphicsContext, drawingSegment);
                    }
                    else if (segmentLine.isSecantTo(upperEyelidLine)) {
                        Point upperIntersection = segmentLine.intersection(upperEyelidLine);
                        if (upperEyelidLine.containsInOpenNegativeHalfSpace(segment.getA())) {
                            splitSegment = new Segment(upperIntersection, segment.getA(), segment.getColor());
                        }
                        else splitSegment = new Segment(upperIntersection, segment.getB(), segment.getColor());
                        drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                        this.drawSegmentOnCanvas(graphicsContext, drawingSegment);
                    }
                    else { // segmentLine.isSecantTo(lowerEyelidLine)
                        Point lowerIntersection = segmentLine.intersection(lowerEyelidLine);
                        if (lowerEyelidLine.containsInOpenPositiveHalfSpace(segment.getA())) {
                            splitSegment = new Segment(segment.getA(), lowerIntersection, segment.getColor());
                        }
                        else splitSegment = new Segment(segment.getB(), lowerIntersection, segment.getColor());
                        drawingSegment = this.segmentProcessing(splitSegment, viewPoint);
                        this.drawSegmentOnCanvas(graphicsContext, drawingSegment);
                    }
                }
            }
        }
    }

        public Segment segmentProcessing(Segment segment, ViewPoint viewPoint) {
        // TODO to optimize code
        Point pov = viewPoint.getPoint();
        double povX = pov.getX();
        double povY = pov.getY();
        double viewAngle = viewPoint.getViewAngle();
        double rotateAngle = viewPoint.getRotateAngle();

        double projectionLinePointAPosX = (povX + 12) * Math.cos(Math.toRadians((-viewAngle / 2) + rotateAngle));
        double projectionLinePointAPosY = (povY + 12) * Math.sin(Math.toRadians((-viewAngle / 2) + rotateAngle));
        double projectionLinePointBPosX = (povX + 12) * Math.cos(Math.toRadians((viewAngle / 2) + rotateAngle));
        double projectionLinePointBPosY = (povY + 12) * Math.sin(Math.toRadians((viewAngle / 2) + rotateAngle));
        Point projectionLinePointA = new Point(projectionLinePointAPosX, projectionLinePointAPosY);
        Point projectionLinePointB = new Point(projectionLinePointBPosX, projectionLinePointBPosY);
        Line projectionLine = new Line(projectionLinePointA, projectionLinePointB);

        Line povToPointALine = new Line(viewPoint.getPoint(), segment.getA());
        Line povToPointBLine = new Line(viewPoint.getPoint(), segment.getB());
        Point intersectPointOfProjectionAndPovToPointA = projectionLine.intersection(povToPointALine);
        Point intersectPointOfProjectionAndPovToPointB = projectionLine.intersection(povToPointBLine);
        Segment projectedSegment = new Segment(intersectPointOfProjectionAndPovToPointA, intersectPointOfProjectionAndPovToPointB, segment.getColor());

        Line upperEyelidLine = viewPoint.getUpperEyelidLine();

        Point intersectPointOfProjectionAndUpperEyelidLine = projectionLine.intersection(upperEyelidLine);

        double drawingSegmentPointAPosX = (new Segment(intersectPointOfProjectionAndPovToPointA, intersectPointOfProjectionAndUpperEyelidLine)).length();
        double drawingSegmentPointBPosX = projectedSegment.length();
        Point drawingSegmentPointA = new Point(drawingSegmentPointAPosX, 0);
        Point drawingSegmentPointB = new Point(drawingSegmentPointBPosX, 0);
        return new Segment(drawingSegmentPointA, drawingSegmentPointB, segment.getColor());
    }

    public void drawSegmentOnCanvas(GraphicsContext graphicsContext, Segment segment) {
        // TODO to check widthCanvas and heightCanvas after drawSegment()
        double widthCanvas = this.canvas.getWidth();
        double heightCanvas = this.canvas.getHeight();
        Color color = awtColorToPaintColor(segment.getColor());
        double x1 = segment.getA().getX() + widthCanvas / 2.;
        //double x1 = segment.getA().getX() ;
        double y1 = segment.getA().getY() + heightCanvas / 2.;
        //double y1 = segment.getA().getY() ;
        double x2 = segment.getB().getX() + widthCanvas / 2.;
        //double x2 = segment.getB().getX() ;
        double y2 = segment.getB().getY() + heightCanvas / 2.;
        //double y2 = segment.getB().getY();
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }

    public static Color awtColorToPaintColor(java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        double opacity = color.getAlpha() / 255.0;
        return Color.rgb(red, green, blue, opacity);
    }
}
