package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;

/** Interface visant à adapter l'Algorithme du Peintre au mode console ou graphique.
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
@FunctionalInterface
public interface Paintable {
    void drawSegment(Segment segment, ViewPoint viewPoint);
}
