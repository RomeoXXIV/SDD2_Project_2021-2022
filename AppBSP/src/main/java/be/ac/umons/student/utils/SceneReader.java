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

    public void readSceneFile(String pathFile) {
        if (checkFileConform(pathFile)) {
            try {
                File sceneFile = new File(pathFile);
                this.fileName = sceneFile.getName();
                try (Scanner sc = new Scanner(sceneFile)) {
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
                System.out.println("Exception : \"" + e.getMessage() + "\"");
            }
        }
        else{
            System.out.println("Le Format du Fichier ne correspond pas");
        }
    }


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
                            return false;
                        }
                    }
                    String color = sc.next();
                    if(!color.equals("Noir") && stringToColor(color) == Color.BLACK)
                    {
                        return false;
                    }

                }
                if(count != numbOfSegment){
                    return false;
                }

            } catch (NumberFormatException e) {
                return false;
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Exception : \"" + e.getMessage() + "\"");
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
        return "SceneReader{" +
                "fileName='" + fileName + '\'' +
                ", xAxisLimit=" + xAxisLimit +
                ", yAxisLimit=" + yAxisLimit +
                ", segmentsSize=" + segmentsSize +
                '}';
    }
}
