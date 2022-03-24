import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.heuristics.HeuristicSelector;
import be.ac.umons.student.models.heuristics.StandardHeuristic;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BSPTreeTest {

    HeuristicSelector heuristic = new StandardHeuristic();
    ArrayList<Segment> segmentArrayList = new ArrayList<>();
    BSPTree bspTree = new BSPTree(segmentArrayList, heuristic);

    @Test
    /**
     * hauteur 0
     * hauteur 1
     * hauteur n
     */
    public void test_height() {
        
        // TODO
    }

    @Test
    /**
     * size 0
     * size 1
     * size n
     */
    public void test_size() {
        // TODO
    }

    @Test
    /**
     * true
     * false
     */
    public void test_isLeaf() {
        // TODO
    }

    @Test
    /**
     * true
     * false
     */
    public void test_isEmpty() {
        // TODO
    }
}