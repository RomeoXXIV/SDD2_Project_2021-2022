package be.ac.umons.student.models.painter;

import be.ac.umons.student.models.Segment;

/** Paintable est une interface fonctionnelle permettant de définir la signature d’une méthode qui pourra être utilisée
 * pour adapter l'algorithme du Peintre à la version console ou interactive du programme.
 * @author Romeo Ibraimovski
 */
@FunctionalInterface
public interface Paintable {
    void drawSegment(Segment segment, ViewPoint viewPoint);
}
