package be.ac.umons.student.utils;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Logger;

import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

/**
 * SceneReader est une classe qui vérifie qu'un fichier soit au format d'un fichier scène.
 * ELLe lit et stocke les données d'une scène à partir d'un fichier scène.
 * @author Romeo Ibraimovski
 */
public class SceneReader {

    private static final Logger LOGGER = Logger.getLogger(SceneReader.class.getName());
    private static final Color BROWN = new Color(102,51,0);

    private String fileName;
    private final boolean isSceneFile;
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

    /**
     * Vérifie si le fichier est un fichier scène.
     * @param file Fichier à vérifier.
     * @return True si le fichier est un fichier scène, false sinon.
     */
    public static boolean isSceneFile(File file) {
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
        catch (IOException e) {
            LOGGER.severe(e.getMessage());
            return false;
        }
    }

    /**
     * Vérifie si la ligne est une première ligne d'un fichier scène.
     * @param firstLine Ligne à vérifier.
     * @return True si la ligne est une première ligne d'un fichier scène, false sinon.
     */
    public static boolean isSceneFileFirstLine(String firstLine) {
        String[] split = firstLine.split(" ");
        return split.length == 4 && split[0].equals(">") && isInteger(split[1]) && isInteger(split[2]) && isInteger(split[3]);
    }

    /**
     * Vérifie si la chaîne de caractères est un entier.
     * @param str Chaîne de caractères à vérifier.
     * @return True si la chaîne de caractères est un entier, false sinon.
     */
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

    /**
     * Vérifie si la ligne est une ligne d'un fichier scène.
     * @param line Ligne à vérifier.
     * @return True si la ligne est une ligne d'un fichier scène, false sinon.
     */
    public static boolean isSceneFileLine(String line) {
        String[] split = line.split(" ");
        return split.length == 5 && isSceneFileDouble(split[0]) && isSceneFileDouble(split[1]) && isSceneFileDouble(split[2]) && isSceneFileDouble(split[3]) && isSceneFileColor(split[4]);
    }

    /**
     * Vérifie si la chaîne de caractères est un double au format "(-)##0.000000".
     * @param str Chaîne de caractères à vérifier.
     * @return True si la chaîne de caractères est un double, false sinon.
     */
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

    /**
     * Vérifie si la chaîne de caractères est une couleur disponible.
     * @param str Chaîne de caractères à vérifier.
     * @return True si la chaîne de caractères est une couleur disponible, false sinon.
     */
    public static boolean isSceneFileColor(String str) {
        return str.equals("Rouge") || str.equals("Bleu") || str.equals("Gris") || str.equals("Vert") || str.equals("Orange") || str.equals("Violet") || str.equals("Jaune") || str.equals("Rose") || str.equals("Marron") || str.equals("Noir");
    }

    /**
     * Lit une scène à partir d'un fichier scène.
     * @param sceneFile Fichier à lire.
     */
    public void readSceneFile(File sceneFile) {
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
        catch (IOException e) {
            LOGGER.severe("Erreur lors de la lecture du fichier " + sceneFile.getName());
        }
    }

    /**
     * Convertit une chaîne de caractères en segment.
     * @param str Chaîne de caractères à convertir.
     * @return Segment correspondant à la chaîne de caractères.
     */
    public Segment stringToSegment(String str) {
        String[] split = str.split(" ");
        Point p1 = new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]));
        Point p2 = new Point(Double.parseDouble(split[2]), Double.parseDouble(split[3]));
        Color color = this.stringToColor(split[4]);
        return new Segment(p1, p2, color);
    }

    /**
     * Convertit une chaîne de caractères en couleur.
     * @param color Chaîne de caractères à convertir.
     * @return Couleur correspondant à la chaîne de caractères.
     */
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
