package be.ac.umons.student.utils;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

/**
 * SceneReader est une classe qui vérifie le format d'un fichier scène, lit une scène à partir d'un fichier.
 *
 * @author Romeo Ibraimovski
 */
public class SceneReader {

    public static final Color BROWN = new Color(102,51,0);

    private String fileName;
    private boolean isSceneFile; // TODO final ?
    private final ArrayList<Segment> segments;
    private int xAxisLimit;
    private int yAxisLimit;
    private int segmentsSize;

    public SceneReader(File file) {
        this.segments = new ArrayList<>();
        this.isSceneFile = isSceneFile(file);
        if (this.isSceneFile)
            this.readSceneFile(file);
    }

    public SceneReader(String pathFile) {
        this(new File(pathFile));
    }

    public static boolean isSceneFile(File file) {
        // TODO Demander un coup de main pour la gestion des exceptions + Gestion des fichiers non trouvés ?
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String firstLine = bufferedReader.readLine();
            if (firstLine == null || !isSceneFileFirstLine(firstLine)) return false;
            int nbrOfSegmentsLeft = Integer.parseInt(firstLine.split(" ")[3]);
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.length() >= 40) {
                    nbrOfSegmentsLeft--;
                    if (!isSceneFileLine(line)) return false;
                    line = bufferedReader.readLine();
                }
                else return false;
            }
            return nbrOfSegmentsLeft == 0;
        } // pas besoin de bufferedReader.close() car on a fait un try with resources
    }

    public static boolean isSceneFileFirstLine(String firstLine) {
        String[] split = firstLine.split(" ");
        return split.length == 4 && split[0].equals(">") && isInteger(split[1]) && isInteger(split[2]) && isInteger(split[3]);
    }

    public static boolean isInteger(String str) {
        if (str == null) return false;
        int length = str.length();
        if (length == 0) return false;
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) return false;
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public static boolean isSceneFileLine(String line) {
        String[] split = line.split(" ");
        return split.length == 5 && isSceneFileDouble(split[0]) && isSceneFileDouble(split[1]) && isSceneFileDouble(split[2]) && isSceneFileDouble(split[3]) && isSceneFileColor(split[4]);
    }

    public static boolean isSceneFileDouble(String str) {
        if (str == null) return false;
        int length = str.length();
        if (length < 8) return false;
        int i = 0;
        if (str.charAt(0) == '-')
            i = 1;
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (i == length - 7) {
                if (c != '.') return false;
            } else if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public static boolean isSceneFileColor(String str) {
        return str.equals("Rouge") || str.equals("Bleu") || str.equals("Gris") || str.equals("Vert") || str.equals("Orange") || str.equals("Violet") || str.equals("Jaune") || str.equals("Rose") || str.equals("Marron") || str.equals("Noir");
    }

    public void readSceneFile(File sceneFile) {
        // TODO Demander un coup de main pour la gestion des exceptions
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(sceneFile))) {
            String firstLine = bufferedReader.readLine();
            String[] split = firstLine.split(" ");
            this.fileName = sceneFile.getName();
            this.xAxisLimit = Integer.parseInt(split[1]);
            this.yAxisLimit = Integer.parseInt(split[2]);
            this.segmentsSize = Integer.parseInt(split[3]);
            String line = bufferedReader.readLine();
            while (line != null) {
                this.segments.add(this.stringToSegment(line));
                line = bufferedReader.readLine();
            }
        } // pas besoin de bufferedReader.close() car on a fait un try with resources
    }

    public Segment stringToSegment(String str) {
        String[] split = str.split(" ");
        Point p1 = new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
        Point p2 = new Point(Double.parseDouble(split[2]), Double.parseDouble(split[3]));
        Color color = this.stringToColor(split[4]);
        return new Segment(p1, p2, color);
    }

    public Color stringToColor(String color){
        switch (color) {
            case "Rouge":
                return Color.RED;
            case "Bleu":
                return Color.BLUE;
            case "Gris":
                return Color.GRAY;
            case "Vert":
                return Color.GREEN;
            case "Orange":
                return Color.ORANGE;
            case "Violet":
                return Color.MAGENTA;
            case "Jaune":
                return Color.YELLOW;
            case "Rose":
                return Color.PINK;
            case "Marron":
                return BROWN;
            default:
                return Color.BLACK;
        }
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isSceneFile() {
        return isSceneFile;
    }

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public int getxAxisLimit() {
        return xAxisLimit;
    }

    public int getyAxisLimit() {
        return yAxisLimit;
    }

    public int getSegmentsSize() {
        return segmentsSize;
    }

    @Override
    public String toString() {
        return "Le fichier de scène " + fileName +
                ", a une limite en X de " + xAxisLimit +
                ", a une limite en Y de " + yAxisLimit +
                " et contient " + segmentsSize +
                " segments";
    }
}
