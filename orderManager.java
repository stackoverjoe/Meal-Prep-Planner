//Joe Leland
//CS202
//Program 5
//This class manages the BST of all of the restaurants and meal plans, It is the class used by main to utilize all of the functionality of the treeManager class

import java.text.DecimalFormat;
import java.util.Scanner;
import java.io.*;

public class orderManager {

    private inputReader scan;
    //Scanner input = null;//Scanner object for user input
    private Scanner read = null;//Scanner object for reading data.txt file
    private Scanner read2 = null;//Scanner object for reading menu.txt file
    private Scanner read3 = null;//Scanner object for reading delivery.txt
    private treeManager root;//root of tree meal plans and restaurants
    private food myOrder, tail;//this will be the head to users order in program 5
    private deliveryServices options;//A "head" pointer to a list of delivery service options the user can select from to buy their food
    private double orderPrice;//keeps a rolling total of the order price
    private int size;//String array size for food objects


    //default constructor automatically reads in from 3 files in order to populate the BST and LLL of arrays, and delivery options
    public orderManager() {
        size = 3;
        //input = new Scanner(System.in);
        scan = new inputReader();
        root = null;
        myOrder = tail = null;
        options = null;
        orderPrice = 0;
        try {
            //create new Scanner objects for both files
            read = new Scanner(new File("data.txt"));
            read2 = new Scanner(new File("menu.txt"));
            read3 = new Scanner(new File("delivery.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("No file.");
        }

        //temp variables used to pass to constructors to make the objects
        int choice, spec, rating, size = 0;
        String name, foodName, ingredients, notes, special;
        double price;
        treeManager temp;
        int i = 0;
        read.useDelimiter(",");
        read2.useDelimiter(",");

        //first while loop goes through menu.txt and extracts elements from each line, until file is done
        while (read.hasNext()) {
            int l = 0;//l variable is reset to 0 at the beginning of each loop and only used in the second while loop
            choice = read.nextInt();
            spec = read.nextInt();
            name = read.next();
            rating = read.nextInt();
            foodName = read.next();
            price = read.nextDouble();
            ingredients = read.next();
            notes = read.next();
            read.skip(",");//get rid of weird instance of a comma getting in to string
            special = read.nextLine();
            //create appropriate object based off the variable "choice" gotten from the menu.txt file
            if (choice == 1) {
                temp = new restaurant(spec, name, rating, foodName, price, ingredients, notes, special, 3);
            } else {
                temp = new mealPlan(spec, name, rating, foodName, price, ingredients, notes, special, 3);
            }
            //second while loop is used to grab two lines from menu.txt (dictated by the l variable), these lines represent menu items and are added to the newly created
            //objects menu. The read2 buffer will go two lines, exit and then when we get back to it, the buffer will be on line 3, ready to take the next two menu items and add them
            //and so on until the file is exhausted. Will add 10 items to 5 restaurants at the moment.
            while (read2.hasNext() && l <= 1) {
                foodName = read2.next();
                special = read2.next();
                notes = read2.next();
                ingredients = read2.next();
                try {
                    price = Double.parseDouble(read2.nextLine());//puts buffer on next line waiting for next iteration
                } catch (NumberFormatException e) {
                    //e.printStackTrace();
                }
                temp.addMenuItem(foodName, price, special, notes, ingredients);//append temps menu
                ++l;//move a line, will happen twice each main while loop iterations
            }
            if (this.root == null) {
                this.root = temp;//if root is null, reference the object
            } else {
                root.insert(this.root, temp, choice);//other wise call insert with the object
            }
        }
        //read3 populates the LLL of delivery services available
        while(read3.hasNext()){
            name = read3.nextLine();
            deliveryServices temp1 = new deliveryServices(name);
            if(this.options == null){
                this.options = temp1;
            }
            else{
                this.options.addService(this.options, temp1);
            }
        }
        read.close();//close file
        read2.close();
        read3.close();
    }


    //function that allows user to add menu items from restaurants or meal plans to their order
    public void addToOrder(){
        boolean success = false;//bool used to allow multiple additions of items
        double [] tprice = new double[1];//double array of size one is used to get price information back from recursive funtions
        String temp = null;
        treeManager grab = null;
        food item = new food();
        System.out.println("What restaurant would you like to order from?");
        temp = scan.readString();
        grab = root.search(this.root, temp);
        if(grab == null){
            System.out.println(temp + " was not found.");
        }
        else{
            while(success != true) {
                grab.display();
                System.out.println("What menu item would you like to add to your order?(Q to stop entering menu items.)");
                temp = scan.readString();
                if(temp.charAt(0) == 'Q' || temp.charAt(0) == 'q'){
                    return;
                }
                item = new food();
                item = item.retrieve(grab.menu, temp, tprice);
                if (item == null) {
                    System.out.println("Menu item not found.");
                    //return;
                }
                else {
                    this.orderPrice += tprice[0];//update order price
                    tprice[0] = 0;
                    System.out.println();
                    System.out.println(">>Successfully added to your order!<<");
                    System.out.println();
                    if (myOrder == null) {
                        myOrder = item.addToOrder(this.myOrder, item);//become the head of the order
                    } else {
                        myOrder.addToOrder(this.myOrder, item);//else append LLL of arrays
                    }
                }
            }
        }
    }


    //function to remove an item from an order
    public void removeFromOrder(){
        if(this.myOrder == null){
            System.out.println("Nothing in your order!.");
            System.out.println();
            return;
        }
        String temp = null;//temp for finding items
        double [] tprice = new double[1];//used to decrement price if we remove something
        food grab = null;
        System.out.println();
        displayOrder();
        System.out.println("What item would you like to remove?");
        temp = scan.readString();
        grab = myOrder.retrieve(this.myOrder, temp, tprice);//see if it exists, and decrement if it does
        if(grab == null){
            System.out.println("Did not find any food that matched that in your order.");
            System.out.println();
        }
        else{
            this.orderPrice -= tprice[0];//update order price
            if(grab == this.myOrder){
                this.myOrder = myOrder.removeFromOrder(this.myOrder, temp);//update myOrder head if needed
            }
            else{
                this.myOrder.removeFromOrder(this.myOrder, temp);//remove otherwise, calls food remove function
            }
            System.out.println();
            System.out.println(">>Successfully removed from your order!<<");
            System.out.println();
        }
    }


    //display users current order
    public void displayOrder(){
        if(this.myOrder == null){
            System.out.println("Nothing in your order!");
            System.out.println();
            return;
        }
        System.out.println();
        DecimalFormat df = new DecimalFormat();//used to format decimal to only out put 2 digits
        df.setMaximumFractionDigits(2);
        System.out.println("Current Order: ");
        System.out.println("_____________________________");
        myOrder.displayMenu(myOrder);
        System.out.println("--------");
        System.out.print("Total order price: $");
        System.out.println(df.format(this.orderPrice));//formatted double output
        System.out.println();
    }


    //Function fo place and finalize order
    public void placeOrder(){
        double [] tprice = new double[1];
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);//set decimal format for price display
        String temp = null;
        deliveryServices grab = null;
        if(this.myOrder == null){
            System.out.println("You have not added anything to your order yet!");
            return;
        }
        localMap map = new localMap();//create a mab object that acts as if it know your location.. hopefully you are in portland
        map.display();//display the state and city
        System.out.println();
        map.displayStats();//calculates and displays approx delays given traffic volume
        System.out.println();
        int option = 0;//user option
        System.out.println("Would you like to display the map of your local delivery area?(1. yes, 2. no)");//Map can be displayed via ASCII values (sloppily), or by .jpg popup, ASCII image was produced by using a
        //program written by Animesh Shaw. His source code can be found here: https://codehackersblog.blogspot.com/2015/06/image-to-ascii-art-converter-in-java.html
        option = scan.readInt(1, 2);
        if(option == 1){
            System.out.println("Would you like to display this map on the command line, or see a more readable pop up?(1. Command Line(ASCII), 2. GUI Map(.jpg in new window)");
            option = scan.readInt(1, 2);
            if(option == 1){
                //ASCII print to screen
                map.displayMap();
            }
            else{
                //Pop up window with .jpg of portland
                map.displayMapWindow();
            }
        }
        System.out.println("Before we pick a delivery service, does everything on your order look right? Or would you like to head back to the main menu?.");
        displayOrder();
        System.out.println("1. Order looks good, 2. Go back");
        option = scan.readInt(1, 2);
        if(option == 2){
            return;
        }
        System.out.println("Almost time to eat, just pick from the delivery services below to get your food.");
        options.displayOptions(this.options);//display all delivery options
        System.out.print("Total Price: ");
        System.out.println(df.format(this.orderPrice));
        boolean success = false;
        while(success != true) {//while loop to allow for user to mistype delivery name
            System.out.println();
            System.out.println("Which service would you like to order from?(Q to call the whole thing off.)");
            temp = scan.readString();
            if(temp.charAt(0) == 'q' || temp.charAt(0) == 'Q'){
                return;//q goes back to menu and preserves order
            }
            grab = options.retrieve(this.options, temp, tprice);//calls deliveryServices retrieve to make the order
            this.orderPrice += tprice[0];
            if (grab != null) {
                System.out.println("Perfect! Your food will be delivered by the following service.");
                System.out.println("______________________________________________________________");
                grab.display();
                System.out.print("Your total price, with delivery fees included, will be: $");
                System.out.println(df.format(this.orderPrice));
                System.out.println("Thanks for using us to place your order!");
                this.myOrder = null;
                this.orderPrice = 0;
                success = true;//stops loop
            } else {
                System.out.println("That service was not found!");
            }
        }
        System.out.println();
    }


    //wrapper function for main to call function with args and add menu items
    public void addMenuItem(){
        treeManager grab = null;
        String find = null;
        System.out.println("What is the name of the restaurant or meal plan you would like to add a menu item to?: ");
        find = scan.readString();
        grab = root.search(this.root, find);
        if(grab != null){
            addMenuItem(grab);
        }
        else{
            System.out.println(find + " not found.");
            System.out.println();
        }
    }


    //Function to add food objects to a restaurant or meal plan
    public treeManager addMenuItem(treeManager to_add) {
        //variables used to pass as arguments
        String tfoodName, tingredients, tnotes, tspecial = null;
        double tprice = 0;
        char tmore = 'y';//variable used to allow for as many items to be added as desired
            while (tmore != 'n' && tmore != 'N') {
                System.out.println("Name of the food item?: ");
                tfoodName = scan.readString();
                System.out.println("Description?: ");
                tspecial = scan.readString();
                System.out.println("Where are the ingredients sourced?: ");
                tnotes = scan.readString();
                System.out.println("What are the ingredients?: ");
                tingredients = scan.readString();
                System.out.println("What is the price?: ");
                tprice = scan.readDouble();
                to_add.addMenuItem(tfoodName, tprice, tspecial, tnotes, tingredients);//adds the menu item to the passed in object
                System.out.println("Would you like to add another menu item?(Y or N) ");
                tmore = scan.readString().charAt(0);
            }
        return to_add;//returns that object to caller
    }


    //Function to insert a new restaurant to the tree
    public void insert(){
        treeManager temp = null;
        int choice = 0;
        int flag = 0;
        int rating, miles = 0;
        String name = null;
        String foodName, ingredients, notes, special = null;
        double price = 0;
        char more = '0';
        System.out.println("1. To add a restaurant.\n2. To add a meal plan.");
        choice = scan.readInt(1, 2);//input that enforces a range from x - y
        System.out.println(choice);
        //get general data name and rating
        System.out.println("Name?: ");
        name = scan.readString();
        System.out.println("Rating(1-5): ");
        rating = scan.readInt(1, 5);
        //get unique data and create correct object
        if (choice == 1) {
            System.out.println("How many miles away is " + name);
            miles = scan.readInt();
            //input.nextLine();
            temp = new restaurant(miles, name, rating);//creates a restaurant with no menu
            System.out.println("Would you like to add any menu items for " + name + "?(y or n)");
            more = scan.readString().charAt(0);
            //input.nextLine();
            if (more == 'y' || more == 'Y') {
                temp = addMenuItem(temp);//if user wants to add food items to the menu, addMenuItem is called
            }
        }
        //for meal plans
        else {
            System.out.println("Do you have a subscription for " + name + " (1.yes 2.no)");
            miles = scan.readInt();
            //input.nextLine();
            temp = new mealPlan(miles, name, rating);
            System.out.println("Would you like to add any menu items for " + name + "?(Y or N)");
            more = scan.readString().charAt(0);
            //input.nextLine();
            if (more == 'y' || more == 'Y') {
                temp = addMenuItem(temp);
            }
        }
        if (this.root == null) {
            this.root = temp;//avoids null exception by referencing when null
        } else {
            root.insert(this.root, temp, flag);//insert when not null
        }

    }


    //Displays a specific object, searched for by name
    public void displayOne(){
        if(this.root == null){
            System.out.println("No data to display.");
            System.out.println();
            return;
        }
        treeManager grab = null;//used to catch return of functions
        String temp;
        String type = null;
        System.out.println("What is the name of restaurant or meal plan you wish to search for?");
        temp = scan.readString();
        grab = root.search(this.root, temp);//calls search function which will return the match (if there is one)
        //identify type for output pleasantries
        if(grab instanceof restaurant){
            type = "restaurant";
        }
        else if(grab instanceof mealPlan){
            type = "meal plan";
        }
        if(grab != null){
            System.out.println("Successfully found the following " + type + " using the keyword: \"" + temp + "\":");
            System.out.println("___________________________");
            grab.display();//display desired
        }
        else{
            System.out.println(temp + " not found.");
            System.out.println();
        }


        //if duplicates have been added at this node, the user can decide to print those;
        if(grab.duplicates != null){
            System.out.println("This restaurant has duplicates, would you like to display those?(Y or N)");
            temp = scan.readString();
            if(temp.charAt(0) == 'Y' || temp.charAt(0) == 'y'){
                grab.displayAll(grab.duplicates);
            }
        }
    }


    //function used to display all objects of either restaurant or meal plan, not both
    public void displayType(){
        if(this.root == null){
            System.out.println("No data to display.");
            System.out.println();
            return;
        }
        int choice = 0;
        //get correct, valid input
        System.out.println("1. To display all restaurants.\n2. To display all meal plans.\n3. Back");
        choice = scan.readInt(1, 3);
        if(choice == 3){
            System.out.println("Going back to menu.");
            return;
        }
        root.displayType(this.root, choice);//calls tree manager displayType function which traverses the structure and prints accordingly
    }


    //function to prompt the user for which object they would like to remove
    public void remove(){
        if(this.root == null){
            System.out.println("Nothing to remove!");
            System.out.println();
            return;
        }
        treeManager temp;
        String find;
        System.out.println("What do you want to delete?(must type full name, not case sensitive):(Q to go back to menu.) ");
        find = scan.readString();
        if(find.charAt(0) == 'q' || find.charAt(0) == 'Q'){
            return;
        }
        temp = root.search(this.root, find);//temp retrieves the nodes to be removed
        if(temp == null){
            System.out.println(find + " not found.");//if null, not in tree
            return;
        }
        if (this.root.compare(this.root, find) == 0) {
            this.root = root.removeItem(this.root, find);//if root is to be deleted, the return value will be the new true root
        } else {
            root.removeItem(root, find);//else called without return
        }
    }


    //displayAll calls treeManager displayAll
    public void displayAll(){
            if(root == null) {
                System.out.println("There is nothing to display!");
                System.out.println();
                return;
            }
            root.displayAll(root);
    }


    public void destroy(){
        this.root = null;//destroys the tree
    }
}
