package be.ac.umons.student.models;

public class Painter {

    private BSPTree bspTree;
    private Point viewPoint;

    public Painter(BSPTree bspTree, Point viewPoint) {
        this.bspTree = bspTree;
        this.viewPoint = viewPoint;
    }
}
