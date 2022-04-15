package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PainterInteractive implements Paintable { // TODO to complete

    private Canvas canvas;

    public PainterInteractive(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void drawSegment(Segment segment, ViewPoint viewPoint) {
        // TODO to finish
        GraphicsContext graphicsContext = this.canvas.getGraphicsContext2D();
        double widthCanvas = this.canvas.getWidth();
        double heightCanvas = this.canvas.getHeight();
        graphicsContext.clearRect(0, 0, widthCanvas, heightCanvas);

        Color color = awtColorToPaintColor(segment.getColor());
        double x1 = segment.getA().getX() + widthCanvas / 2.;
        double y1 = segment.getA().getY() + heightCanvas / 2.;
        double x2 = segment.getB().getX() + widthCanvas / 2.;
        double y2 = segment.getB().getY() + heightCanvas / 2.;
        graphicsContext.setStroke(color);
        graphicsContext.strokeLine(x1, y1, x2, y2);
    }

    public static Color awtColorToPaintColor(java.awt.Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        double opacity = color.getAlpha()/255.0;
        return Color.rgb(red, green, blue, opacity);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
