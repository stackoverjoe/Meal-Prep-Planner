//Joe Leland
//This class contains the functionality for the delivery services. It creates different delivery companies that the user can choose from upon place the order.
//These will be held in a linear linked list

import java.util.Random;
import java.text.DecimalFormat;

public class deliveryServices {
    private String companyName;
    private double deliveryFee;//delivery fee for the company
    private int proximity;//represent their proximity to the user
    private int ETA;//estimated arrival time
    private deliveryServices next;//next pointer
    private Random rand;//random number used to create dynamic delivery results


    deliveryServices(){
        companyName = null;
        deliveryFee = 0;
        proximity = 0;
        ETA = 0;
        next = null;
        rand = new Random();
    }


    //constructor that takes a company name and creates random delivery fees and proximity's and then calculates an ETA based off those values
    deliveryServices(String name){
        rand = new Random();
        this.companyName = name;
        this.deliveryFee = rand.nextDouble() *10;//use Math.random() *9 +1 if this doesn't work
        this.proximity = rand.nextInt((15-2) + 1) +2;//range from 2-15
        this.ETA = this.proximity * rand.nextInt((5-2)+1)+2;//range from 2-5
    }


    //displays the LLL of delivery services
    public void displayOptions(deliveryServices head){
        if(head == null){
            return;
        }
        head.display();
        displayOptions(head.next);
    }


    //adds a service to the end of the List, called by the constructor in orderManager
    public deliveryServices addService(deliveryServices head, deliveryServices to_add){
        if(head == null){
            head = to_add;
            return head;
        }
        head.next = addService(head.next, to_add);
        return head;
    }


    //returns a deliveryService object if find matches the name
    public deliveryServices retrieve(deliveryServices head, String find, double[]tprice){
        if(head == null){
            return null;
        }
        if(head.companyName.toLowerCase().contains(find.toLowerCase())){
            tprice[0] = this.deliveryFee;//update the delivery fee into tprice[0]
            return head;
        }
        return retrieve(head.next, find, tprice);
    }


    //Display a particular delivery service option
    public void display(){
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);//formatted for decimal output
        System.out.println("Company Name: " + this.companyName);
        System.out.print("Delivery Fee: $");
        System.out.println(df.format(this.deliveryFee));
        System.out.println("Distance from you: " + this.proximity + " miles.");
        System.out.println("Approximate wait time : " + this.ETA + " minutes.");
        System.out.println("-------------------------");
    }

}
