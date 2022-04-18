package be.ac.umons.student.models.heuristics;

import be.ac.umons.student.models.Segment;

import java.util.ArrayList;

/** Interface permetant d'implémenter les différentes heuristiques
 * @author Romeo Ibraimovski
 * @author Maxime Nabli
 */
@FunctionalInterface
public interface HeuristicSelector {
    Segment selectSegment(ArrayList<Segment> segments);
}
