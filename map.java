//Joe Leland
//CS202
//Program 5
//This module is an abstract base class map, that is designed to be able to support city maps of the users location
import java.util.Random;

public abstract class map {
    private String state;//name of the state
    private int cars;//amount of cars on the road
    private int time;//time calculated from traffic volume to report expected delays

    map(){
        state = null;
        cars = 0;
        time = 0;
    }

    map(String name){
        this.state = name;//Will be oregon in this program
        Random rand = new Random();
        cars = rand.nextInt((100-1)+1);//a number between 1 and 100 to represent traffic volume
        time = 2 * cars;//time value updated 2 times the traffic
    }


    //display for state
    public void display(){
        System.out.println("State: " + this.state);
    }


    //checks time range to report back to called expected delays given the data "collected" from the map.
    void displayStats(){
        if(time < 40){
            System.out.println("Traffic is light, no delivery delays expected");
        }
        else if(40 < time && 100 < time){
            System.out.println("Traffic is moderate expect delays of about 5-10 minutes on all delivery times.");
        }
        else{
            System.out.println("Traffic is heavy expect delays of about 10-15 minutes on all delivery times.");
        }
    }
}
