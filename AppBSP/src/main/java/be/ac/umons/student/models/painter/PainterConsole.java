package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;

/** PainterConsole est une classe définissant le comportement de la méthode drawSegment de l'algorithme du peintre pour
 * la version console du programme.
 * @author Romeo Ibraimovski
 */
public class PainterConsole implements Paintable {
    private static int cptr = 0;
    @Override
    public void drawSegment(Segment segment, ViewPoint viewPoint) {
        cptr++;
    }
}
