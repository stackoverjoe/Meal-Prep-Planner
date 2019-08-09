//Joe Leland
//This class is derived from map, and is specific to the city, Portland in this case.
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.nio.file.*;
import javax.swing.*;
import javax.imageio.*;

public class localMap extends map{
    private Scanner mapReader;
    private String mapData;
    private String city;

    //Constructor sets city and state to Portland, OR
    localMap(){
        super("Oregon");
        this.city = "Portland";
        try{
            mapData = new String(Files.readAllBytes(Paths.get("map.txt")));//opens map.txt, which ASCII art of a map of portland that can be displayed at the terminal
        }catch(IOException e){

        }
    }


    public void displayMap(){
        System.out.println(mapData);
    }//displays ASCII map to command line


    //This function utilizes available java libraries to create a new pop up frame with an image of it and display it to the user
    public void displayMapWindow(){
        try {
            BufferedImage img = ImageIO.read(new File("C:\\Users\\Joe\\IdeaProjects\\Program4\\src\\porltandMap2.jpg"));//image file, picture of a map of portland
            ImageIcon i = new ImageIcon(img);//ImageIcon object
            JFrame frame = new JFrame();//create new JFrame
            frame.setLayout(new FlowLayout());
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//pop up closed when filed terminates
            frame.setSize(1300, 900);//set the pop up windows size
            JLabel lb = new JLabel();//Jlabel used to displace image Icon "i"
            lb.setIcon(i);//set icon
            frame.add(lb);//add image to the frame
            frame.setLocationRelativeTo(null);//centers the pop up to the middle of the screen
            frame.setVisible(true);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Error.");
        }
    }

    //Display to output what we are a map of
    public void display(){
        super.display();
        System.out.println("Your city: " + this.city);
    }
}








