package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

/** HeuristicSelector est une interface fonctionnelle permettant de définir la signature d'une méthode qui pourra être
 * utilisée pour adapter l'heuristique lors de la création d'un arbre BSP.
 * @author Romeo Ibraimovski
 */
@FunctionalInterface
public interface HeuristicSelector {
    Segment selectSegment(ArrayList<Segment> segments);
}
