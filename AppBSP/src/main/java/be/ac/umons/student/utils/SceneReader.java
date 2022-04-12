package be.ac.umons.student.utils;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

public class SceneReader {

    public static final Color BROWN = new Color(102,51,0);

    private final ArrayList<Segment> segments;
    private String fileName;
    private int xAxisLimit;
    private int yAxisLimit;
    private int segmentsSize;

    public SceneReader(String pathFile) {
        this.segments = new ArrayList<>();
        this.readSceneFile(pathFile);
    }

    public SceneReader(File file) {
        this.segments = new ArrayList<>();
        this.readSceneFile(file.getPath());
    }

    /** Lis le fichier et donne la limite de l'axe X, de l'axe Y, le nombre et la liste de segments.
     *
     * @param pathFile
     */
    public void readSceneFile(String pathFile) {
        boolean fileExist = false;
        if (checkFileConform(pathFile)) {
            try {
                File sceneFile = new File(pathFile);
                this.fileName = sceneFile.getName();
                try (Scanner sc = new Scanner(sceneFile)) {
                    fileExist = true;
                    sc.next();
                    this.xAxisLimit = sc.nextInt();
                    this.yAxisLimit = sc.nextInt();
                    this.segmentsSize = sc.nextInt();
                    while (sc.hasNext()) {
                        Point x = new Point(Double.parseDouble(sc.next()), Double.parseDouble(sc.next()));
                        Point y = new Point(Double.parseDouble(sc.next()), Double.parseDouble(sc.next()));
                        Color color = stringToColor(sc.next());
                        this.segments.add(new Segment(x, y, color));
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("");
            }
        }
        else{
            if(fileExist == true) {
                System.out.println("Le Format du Fichier " + fileName  + " ne correspond pas\nL'application va être quittée.");
            }
            else{
                System.out.println("Le Fichier " + fileName  + " n'existe pas\nL'application va être quittée.");

            }
            this.xAxisLimit = 0;
            this.yAxisLimit = 0;
            this.segmentsSize = 0;
        }
    }

    /**
     * Retourne vrai si le fichier est au bon format, faux sinon
     * @param pathFile le path du fichier
     * @return vrai ou faux
     */
    public boolean checkFileConform(String pathFile){
        File file = new File(pathFile);
        fileName = file.getName();
        try(Scanner sc = new Scanner(file))
        {
            sc.next();
            try{
                int xAxis = Integer.parseInt(sc.next());
                int yAxis = Integer.parseInt(sc.next());
                int numbOfSegment = Integer.parseInt(sc.next());
                int count = 0;
                while(sc.hasNext()){
                    count++;
                    for(int i = 0; i < 4; i++){
                        try{
                            Double a = Double.parseDouble(sc.next());
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Ligne " + count + ": Un des 4 'doubles' n'en est pas un.\n");
                            return false;
                        }
                    }
                    String color = sc.next();
                    if(!color.equals("Noir") && stringToColor(color) == Color.BLACK)
                    {
                        System.out.println("Ligne " + count + ": Presence d'une couleur inconnue.\n");
                        return false;
                    }

                }
                if(count != numbOfSegment){
                    System.out.println("Le fichier annonce " + numbOfSegment + " lignes mais n'en contient que " + count + ".\n");
                    return false;
                }

            } catch (NumberFormatException e) {
                System.out.println("La première ligne contient des elements qui ne sont pas des entiers.\n");
                return false;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Exception : \"" + e.getMessage() + "\"");
            return false;
        }
        return true;
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

    public ArrayList<Segment> getSegments() {
        return segments;
    }

    public String getFileName() {
        return fileName;
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
        return "Fichier " + fileName +
                ", a une limite en X de " + xAxisLimit +
                ", a une limite en Y de " + yAxisLimit +
                ", contient " + segmentsSize +
                " segments";
    }
}
