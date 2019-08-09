//Joe Leland
//CS202
//Program 5
//This module interacts with the user and communicates to the order manager class they would like to do

import java.util.InputMismatchException;

public class Main {
    private inputReader input;
    public Main(){input = new inputReader();}//constructor for Main
    public static void main(String[] args) {
        orderManager order = new orderManager();
        int option = 0;
        //char option = 'o';//option for program control and switch statement
        Main obj = new Main();
        System.out.println("Welcome to the food ordering assistance app! Please select from the following:");
        try {
            while (option != 99) {
                System.out.println("1. Display restaurants and meal plans\n" +
                        "2. Add a restaurant or meal plan.\n" +
                        "3. Remove a restaurant or meal plan.\n" +
                        "4. Search for a restaurant or Meal Plan.\n" +
                        "5. Display only restaurants or meal plans.\n" +
                        "6. Add menu item to a restaurant or meal plan.\n" +
                        "7. Delete all\n" +
                        "8. Add item to your order.\n" +
                        "9. Display current order.\n" +
                        "10. Remove an item from your order\n" +
                        "11. Place Order.\n" +
                        "99. Quit");
                option = obj.input.readInt();
                switch (option) {
                    //All available functions for the user to call
                    case 1:
                        order.displayAll();
                        break;
                    case 2:
                        order.insert();
                        break;
                    case 3:
                        order.remove();
                        break;
                    case 4:
                        order.displayOne();
                        break;
                    case 5:
                        order.displayType();
                        break;
                    case 6:
                        order.addMenuItem();
                        break;
                    case 7:
                        order.destroy();
                        break;
                    case 8:
                        order.addToOrder();
                        break;
                    case 9:
                        //localMap temp = new localMap();
                        order.displayOrder();
                        break;
                    case 10:
                        order.removeFromOrder();
                        break;
                    case 11:
                        order.placeOrder();
                        break;
                    case 99:
                        System.out.println("Goodbye.");
                        continue;//continues as to not print invalid option upon exit
                    default:
                        System.out.println("Invalid option.");
                        break;
                }
            }
        }
        catch(InputMismatchException e){
            System.out.println("Invalid input.");
        }

    }
}
