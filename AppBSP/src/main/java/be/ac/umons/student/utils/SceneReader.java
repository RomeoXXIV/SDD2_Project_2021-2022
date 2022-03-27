package be.ac.umons.student.utils;
import javax.naming.directory.InvalidAttributesException;
import java.awt.Color;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import be.ac.umons.student.models.Point;
import be.ac.umons.student.models.Segment;

public class SceneReader {
    private int sceneLength;
    private int sceneHeight;
    private int numbOfSegment;
    private ArrayList<Segment> segmentList;
    public static final Color BROWN = new Color(102,51,0);


    public SceneReader(){
        this.sceneLength = 0;
        this.sceneHeight = 0;
        this.numbOfSegment = 0;
        this.segmentList = new ArrayList<Segment>();
    }

    public int getSceneLength(){return this.sceneLength;}

    public int getSceneHeight(){return this.sceneHeight;}

    public int getNumbOfSegment(){return this.numbOfSegment;}

    public ArrayList<Segment> getSegmentList() {return segmentList;}

    public void read(String fileDirectory) throws FileNotFoundException, InvalidAttributesException{
        fileDirectory = Paths.get(fileDirectory).toString();
        File scene = new File(fileDirectory);
        try(Scanner scan = new Scanner(scene)){
            if(scan.hasNextLine()){
                scan.next();
                this.sceneLength = scan.nextInt();
                this.sceneHeight = scan.nextInt();
                this.numbOfSegment = scan.nextInt();
            }
            else{
                throw new InvalidAttributesException("The selected file doesn't have the format of a Scene");
            }
            while(scan.hasNext()){
                Point x = new Point(Double.parseDouble(scan.next()), Double.parseDouble(scan.next()));
                Point y = new Point(Double.parseDouble(scan.next()), Double.parseDouble(scan.next()));
                Color color = stringToColor(scan.next());
                Segment segment = new Segment(x, y, color);
                this.segmentList.add(segment);
            }
        }
    }


    public Color stringToColor(String color){
        if(Objects.equals(color, "Rouge")){return Color.RED;}
        else if(Objects.equals(color, "Bleu")){return Color.BLUE;}
        else if(Objects.equals(color, "Gris")){return Color.GRAY;}
        else if(Objects.equals(color, "Vert")){return Color.GREEN;}
        else if(Objects.equals(color, "Orange")){return Color.ORANGE;}
        else if(Objects.equals(color, "Violet")){return Color.MAGENTA;}
        else if(Objects.equals(color, "Jaune")){return Color.YELLOW;}
        else if(Objects.equals(color, "Noir")){return Color.BLACK;}
        else if(Objects.equals(color, "Rose")){return Color.PINK;}
        else if(Objects.equals(color, "Marron")){return BROWN;}
        else{return Color.BLACK;}
    }
}
