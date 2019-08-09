//Joe Leland
//This class contains the function for the meal plan object which is derived from the treeManager class

public class mealPlan extends treeManager {
    private int subscript;//subscrpt variable acts to indicate if the user has a subscription service with the affiliated meal plan

    public mealPlan(){
        super();
        subscript = 0;
    }


    //This constructor takes all the arguments necessary to populate a meal plan, its parent, and one menu item
    public mealPlan(int script, String name, int rating, String foodName, double price, String ingredients, String notes, String special, int size){
        super(name, rating, foodName, price, ingredients, notes, special, size);//parent constructor
        this.subscript = script;
    }


    //Used to create meal plans from user with no arguments
    public mealPlan(int sub, String name, int rating){
        super(name, rating);
        this.subscript = sub;
    }


    //copy constructor calls parent copy constructor first
    public mealPlan(mealPlan copy){
        super(copy);
        this.subscript = copy.subscript;
    }


    //display specific to meal plans contents
    public void display(){
        System.out.print("Meal Plan: ");
        super.display();
        System.out.print("Subscription Status: ");
        if(this.subscript == 1){
            System.out.println("Active.");
        }
        else{
            System.out.println("Not Active.");
        }
        if(this.menu != null) {
            System.out.println("Menu: ");
            System.out.println("------------------");
            super.displayMenu();//parent displays menu if not null
            System.out.println("______________________________");
        }
        else{
            System.out.println("No menu information entered by user yet.");//in case there is no menu
            System.out.println();
        }
    }

}
