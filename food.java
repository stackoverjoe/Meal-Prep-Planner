//Joe Leland
//This class acts as the menus for each object. It is a LLL of arrays. The object has a foodName and price, and an array of Strings that have details about the food

public class food {
    private String name;//name of a food item
    private double price;//price of the item
    private String [] details;//details about the food item in an array of string
    private int arraysize;//size of the array
    private food next;//next pointer to create LLL


    public food(){
        this.name = null;
        this.price = 0;
        this.arraysize = 0;
        this.details = null;
        next = null;
    }


    public food(food be_copied){
        this.name = be_copied.name;
        this.price = be_copied.price;
        this.details = be_copied.details;
        this.next = null;
    }


    //constructor for food that initializes the array and data members with the args passed in. Every food object is a menu item. Every meal plan and restaurant have a food head;
    public food(String name, String special, String notes, String ingredients, double price, int arraysize){
        this.arraysize = arraysize;
        this.details = new String[arraysize];
        details[0] = details [1] = details[2] = null;
        this.details[0] = special;
        this.details[1] = notes;
        this.details[2] = ingredients;
        this.name = name;
        this.price = price;
        next = null;
    }


    //displays the food item and price
    public void display(){
        int i = 0;//index used to print array of strings
        System.out.print(this.name);
        System.out.println(" ~ $" + this.price);
        this.display(i);//calls array display
    }


    //goes through linked list and calls display to print all contents to screen
    public void displayMenu(food head){
        if(head == null){
            return;
        }
        head.display();//display name and price
        if(head.next != null) {
            System.out.println("------------------");
        }
        displayMenu(head.next);
    }


    public void addMenuItem(String name, double price, String special, String notes, String ingredients){
        addMenuItem(this.next, name, price, special, notes, ingredients);
    }


    //function to add a food object to a LLL.
    public food addMenuItem(food head, String name, double price, String special, String notes, String ingredients){
        if(head == null){
            //add new object at the end of the list
            head = new food(name, special, notes, ingredients, price, arraysize);
            return head;
        }
        head.next = addMenuItem(head.next, name, price, special, notes, ingredients);
        return head;
    }

    //retrieve option that takes a double array as a means to report back food price values to be updated
    public food retrieve(food head, String find, double[]tprice){
        if(head == null){
            return null;
        }
        if(head.name.toLowerCase().contains(find.toLowerCase())){
            tprice[0] += head.price;//updates the double array with the price of the food
            return head;
        }
        return retrieve(head.next, find, tprice);
    }


    //Function called by orderManager to add food items to the users order, an array of LLL
    public food addToOrder(food head, food to_add){
        if(head == null){
            head = new food(to_add);//creates a new object as to not disturb the menu and allow for duplicates
            return head;
        }
        head.next = addToOrder(head.next, to_add);
        return head;
    }


    //allows user to remove items from their order before they place it
    public food removeFromOrder(food head, String find){
        if(head == null){
            return null;
        }
        if(head.name.toLowerCase().contains(find.toLowerCase())){
            head = head.next;
            return head;
        }
        head.next = removeFromOrder(head.next, find);
        return head;
    }


    //display contents of the array
    public void display(int i){
        if(i == this.details.length) {
            return;
        }
        //The contents of the array are consistent by design so each index will print the correct prior text
        if(i == 0){
            System.out.print("Description: ");
        }
        else if(i == 1){
            System.out.print("Food sourced from: ");
        }
        else if(i == 2){
            System.out.print("Ingredients: ");
        }
        System.out.println(details[i]);
        display(++i);
    }

}
