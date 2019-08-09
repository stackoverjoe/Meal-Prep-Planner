//Joe Leland
//This class contains all of the functionality to create and manage BST, and for each object to have its own unique BST to store duplicate data
//The classes mealPlan and restaurant are derived from this class. This will be the referenced used by clients to take advantage of upcasting.

public class treeManager {
    protected treeManager duplicates = null;//this acts as a "root" pointer so that every node can have its own tree, where duplicates will be handled.
    protected treeManager right = null;
    protected treeManager left = null;
    protected food menu = null;//"head" pointer to a food object, which is a LLL of arrays.
    protected String name = null;//The name of either a meal plan or a restaurant, depending on what object is created
    protected int rating = 0;//same for the rating
    protected int size = 3;//the size of the String array attached to each food object, with details about the menu item


    public treeManager() {
        duplicates = right = left = null;
        name = null;
        rating = 0;
        menu = null;
        size = 3;
    }


    //constructor that instantiates the base data, and created a menu item for the establishment
    public treeManager(String name, int rating, String foodName, double price, String ingredients, String notes, String special, int size){
        this.name = name;
        this.rating = rating;
        this.menu = new food(foodName, special, notes, ingredients, price, size);
        this.size = size;
        duplicates = right = left = null;
    }


    //constructor to set data, excluding the menu
    public treeManager(String name, int rating){
        this.name = name;
        this.rating = rating;
        duplicates = right = left = null;
        menu = null;
    }


    //copy constructor
    public treeManager(treeManager copy){
        this.name = copy.name;
        this.rating = copy.rating;
        this.menu = copy.menu;
        this.duplicates = copy.duplicates;
        this.left = this.right = null;
        //this.size = size;
    }


    //add menu function that calls a food addMenuItem function to append a LLL of arrays
    public void addMenuItem(String name, double price, String special, String notes, String ingredients){
        if(menu == null){
            this.menu = new food(name, special, notes, ingredients, price, size);
        }
        else{
             this.menu.addMenuItem(this.menu, name, price, special, notes, ingredients);
        }
    }


    //display function called by derived classes display so that treeManager takes care of its own display
    public void display(){
        System.out.println(" " + this.name);
        System.out.println("Rating: " + this.rating);
    }


    //Function to call food object function to display it's menu
    public void displayMenu(){
        this.menu.displayMenu(this.menu);
    }


    //Search function used to retrieve an object from the tree, it is returned to the caller for operations to be performed, will return if a keyword is found in the strings
    public treeManager search(treeManager root, String find){
        if(root == null){
            return root;
        }
        if(root.name.toLowerCase().contains(find.toLowerCase())){//toLower is used to ignore casing of letters when sorting, contains is used to achieve a keyword match functionality
            return root;
        }
        if(compare(root, find) > 0){
            return search(root.left, find);
        }
        else{
            return search(root.right, find);
        }
    }


    //compare function that can compare two treeManager objects by their name value, not case sensitive
    public int compare(treeManager one, treeManager two){
        int test = one.name.toLowerCase().compareTo(two.name.toLowerCase());
        return test;
    }


    //function that compares a treeManager object's name, against a string, not case sensitive
    public int compare(treeManager one, String two){
        int test = one.name.toLowerCase().compareTo(two.toLowerCase());
        return test;
    }


    //Function used to return the inorder successor when removing an object from the BST that has two children
    public treeManager findIOS(treeManager root){
        if(root == null){
            return root;
        }
        if(root.left == null){
            return root;
        }
        else {
            return findIOS(root.left);
        }
    }


    //recursive removal function
    public treeManager removeItem(treeManager root, String find){
        if(root == null){
            return null;
        }
        if(compare(root, find) > 0){
            root.left = removeItem(root.left, find);
        }
        else if(compare(root, find) < 0){
            root.right = removeItem(root.right, find);
        }
        else{
            //if the object to delete has 2 children it must be replaced by the inorder successor
            if(root.left != null && root.right != null){
                treeManager temp2 = root.left;//set a temp reference to where the new roots left will need to point to
                treeManager watch = findIOS(root.right);//get IOS - the value to "copy" in to root
                //this casting is used so that the correct copy constructors get invoked. I am creating a new object of appropriate type
                //and then setting it's left and right
                if(watch instanceof restaurant){
                    root = new restaurant((restaurant)watch);
                }
                if(watch instanceof mealPlan){
                    root = new mealPlan((mealPlan)watch);
                }
                //if the IOS does not have a left child, then we will grab its right subtree
                if(watch.left == null){
                    root.right = watch.right;
                }
                //otherwise the new roots right will be the right child of the IOS
                else {
                    root.right = removeItem(root.right, root.name);//get rid of the IOS
                }
                root.left = temp2;//set new roots left
                return root;
            }
            else if(root.right == null){
                return root.left;//wire around desired removal
            }
            else if(root.left == null) {
                return root.right;//likewise
            }
            else{
                return null;
            }
        }
        return root;
    }


    //Recursive insert function //opt is used so that when storing duplicates into the second tree, it does not loop forever
    public treeManager insert(treeManager root, treeManager to_add, int opt) {
        if (root == null) {
            root = to_add;//references ("adds") node at null
            return root;
        }
        if(compare(root, to_add)==0 && opt == 0){//if the new object has the same name, it will be added to the BST of which it is a copy
            System.out.println(to_add.name + " was found on the list and added to duplicates.");
            System.out.println();
            if(duplicates == null) {
                root.duplicates = insert(duplicates, to_add, ++opt);//adds to object own root pointer called duplicates
            }
            else{
                insert(duplicates, to_add, ++opt);//inserts at objects duplicate bst pointer
            }
        }
        else if(compare(root, to_add) > 0){
            root.left = insert(root.left, to_add, opt);//go left
        }
        else{
            root.right = insert(root.right, to_add, opt);//go right
        }
        return root;
    }


    //Function to display either all of the restaurants or all of the meal plans, decided by choice
    public void displayType(treeManager root, int choice){
        if(root == null){
            return;
        }
        //in order traversal
        displayType(root.left, choice);
        //depending on choice see if root is referencing a meal plan or restaurant
        if(choice == 1){
            if(root instanceof restaurant){
                root.display();
            }
        }
        else if(choice == 2){
            if(root instanceof mealPlan){
                root.display();
            }
        }
        displayType(root.right, choice);
    }


    //Displays everything in the BST
    public void displayAll(treeManager root){
        if(root == null){
            return;
        }
        displayAll(root.left);
        root.display();
        displayAll(root.right);
    }

}




