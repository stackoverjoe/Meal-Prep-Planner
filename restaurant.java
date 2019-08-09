//Joe Leland
//This class contains the functions for the restaurant class which is derived from the tree manager class

public class restaurant extends treeManager{
    private int miles;//miles to represent how far away a restaurant is from the user


    public restaurant(){
        super();
        rating = miles = 0;
    }


    //constructor used by the file reading insertion that takes enough arguments to populate a restaurants data and one menu item
    public restaurant(int miles, String name, int rating, String foodName, double price, String ingredients, String notes, String special, int size){
        super(name, rating, foodName, price, ingredients, notes, special, size);
        this.miles = miles;
    }


    //constructor for user additions with no menu
    public restaurant(int miles, String name, int rating){
       super(name, rating);//parent constructor
       this.miles = miles;
    }


    //copy constructor, calls parents copy constructor
    public restaurant(restaurant copy){
        super(copy);//parent copy constructor
        this.miles = copy.miles;
    }


    //display function for all of restaurants data
    public void display(){
        System.out.println();
        System.out.print("Restaurant: ");
        super.display();
        System.out.println("Restaurant Proximity: " + this.miles + " miles.");
        if(this.menu != null) {
            System.out.println("Menu: ");
            System.out.println("------------------");
            super.displayMenu();//if there is a menu, parents display menu function is called
            System.out.println("______________________________");
        }
        else{
            System.out.println("No menu information entered by user yet.");
            System.out.println();
        }
    }
}
