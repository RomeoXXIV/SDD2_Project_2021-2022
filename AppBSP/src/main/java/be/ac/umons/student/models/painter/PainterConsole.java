package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;

/** Implémentation de l'interface Paintable pour le mode console. On ne fait "rien", on incrémente juste un compteur.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
public class PainterConsole implements Paintable {
    private static int cptr = 0;
    @Override
    public void drawSegment(Segment segment, ViewPoint viewPoint) {
        cptr++;
    }
}
