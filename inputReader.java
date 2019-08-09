//Joe Leland
//This modules was made to deal with all user input in the program, it takes input and deals with exceptions, removing them from being all over the program

import java.util.InputMismatchException;
import java.util.Scanner;

public class inputReader {

    public Scanner input;
    inputReader(){
        input = new Scanner(System.in);
    }


    //Function read in an int
    public int readInt(){
        int temp = 0;
        try {
            temp = input.nextInt();
            input.nextLine();
            return temp;
        }
        catch(InputMismatchException e){//if not int is entered the function is called again until valid input is met
            input.nextLine();
            System.out.println("Invalid Input. Please only enter an Integer.");
            return readInt();
        }
    }


    //read int function that allows for two int arguments x = lower bound and y = higher bound. This function enforces the value entered is within the range x to y, and protects against bad input
    //does not support negative numbers
    public int readInt(int x, int y){
        int temp = y+1;//ensures that loop is always entered no matter what range is given
        while(x < temp && y < temp) {
            try {
                temp = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please only enter an Integer.");
                input.nextLine();
                readInt();
            }
            if(temp <= 0){
                System.out.println("Please select a number between " + x + " and " + y +".");
                temp = y+1;
            }
            else if((x < temp) && (y < temp)){
                System.out.println("Please select a number between " + x + " and " + y +".");
                temp = y+1;
            }
            else{
                input.nextLine();
                return temp;//return the int
            }
        }
        return 0;
    }


    //Function to read a double and protect against bad input
    public double readDouble(){
        double temp = 0;
        try {
            temp = input.nextDouble();
            input.nextLine();
            return temp;
        }
        catch(InputMismatchException e){
            input.nextLine();
            System.out.println("Please enter only a number.");
            return readDouble();
        }
    }


    //simple function to read a string
    public String readString(){
        return input.nextLine();
    }

}













