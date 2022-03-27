import be.ac.umons.student.models.BSPTree;
import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;
import be.ac.umons.student.models.heuristics.HeuristicSelector;
import be.ac.umons.student.models.heuristics.StandardHeuristic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BSPTreeTest {

    private final HeuristicSelector heuristic = new StandardHeuristic();
    private final BSPTree emptyBspTree = new BSPTree();
    private final Point origin = new Point(0, 0);
    private final Point a = new Point(1, 1);
    private final Point b = new Point(0, 1);
    private final Point c = new Point(1, 0);
    private final Segment segment_oa = new Segment(origin, a);
    private final Segment segment_ob = new Segment(origin, b);
    private final Segment segment_oc = new Segment(origin, c);

    private final ArrayList<Segment> oneSegmentArrayList = new ArrayList<>();
    private final ArrayList<Segment> segmentArrayList = new ArrayList<>();

    @AfterEach
    void resetSegmentArrayList() {
        oneSegmentArrayList.clear();
        segmentArrayList.clear();
    }

    @Test
    public void test_height() {
        oneSegmentArrayList.add(segment_oa);
        BSPTree nodeBspTree = new BSPTree(oneSegmentArrayList, heuristic);

        segmentArrayList.add(segment_oa);
        segmentArrayList.add(segment_ob);
        segmentArrayList.add(segment_oc);
        BSPTree bspTree = new BSPTree(segmentArrayList, heuristic);

        assertEquals(0,  emptyBspTree.height());
        assertEquals(1, nodeBspTree.height());
        assertEquals(2, bspTree.height());
    }

    @Test
    public void test_size() {
        oneSegmentArrayList.add(segment_oa);
        BSPTree nodeBspTree = new BSPTree(oneSegmentArrayList, heuristic);

        segmentArrayList.add(segment_oa);
        segmentArrayList.add(segment_ob);
        segmentArrayList.add(segment_oc);
        BSPTree bspTree = new BSPTree(segmentArrayList, heuristic);

        assertEquals(0,  emptyBspTree.size());
        assertEquals(1, nodeBspTree.size());
        assertEquals(3, bspTree.size());
    }

    @Test
    public void test_isLeaf() {
        oneSegmentArrayList.add(segment_oa);
        BSPTree nodeBspTree = new BSPTree(oneSegmentArrayList, heuristic);

        segmentArrayList.add(segment_oa);
        segmentArrayList.add(segment_ob);
        segmentArrayList.add(segment_oc);
        BSPTree bspTree = new BSPTree(segmentArrayList, heuristic);

        assertFalse(emptyBspTree.isLeaf());
        assertTrue(nodeBspTree.isLeaf());
        assertFalse(bspTree.isLeaf());
        assertTrue(bspTree.getLeft().isLeaf());
        assertTrue(bspTree.getRight().isLeaf());
    }

    @Test
    public void test_isEmpty() {
        oneSegmentArrayList.add(segment_oa);
        BSPTree nodeBspTree = new BSPTree(oneSegmentArrayList, heuristic);

        segmentArrayList.add(segment_oa);
        segmentArrayList.add(segment_ob);
        segmentArrayList.add(segment_oc);
        BSPTree bspTree = new BSPTree(segmentArrayList, heuristic);

        assertTrue(emptyBspTree.isEmpty());
        assertFalse(nodeBspTree.isEmpty());
        assertFalse(bspTree.isEmpty());
        assertFalse(bspTree.getLeft().isEmpty());
        assertFalse(bspTree.getRight().isEmpty());
    }
}