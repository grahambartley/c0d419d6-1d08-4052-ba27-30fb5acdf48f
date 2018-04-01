/*This will be the Player class*/

//imports
import java.util.ArrayList;

//Player class
public class Player {
    //variables
    String name;
    int xcoord, ycoord;
    ArrayList<Item> inventory;
    
    //constructors
    //no args constructor
    public Player() {
        name = "Bob";
        //spawn at pos 2,1
        xcoord = 2;
        ycoord = 1;
        inventory = new ArrayList<Item>();        
    }
    
    //one arg constructor
    public Player(String name) {
        this.name = name;
        //spawn at pos 2,1
        xcoord = 2;
        ycoord = 1;
        inventory = new ArrayList<Item>();
    }
    
    //methods
    //returns the player name
    public String getName() {
        return name;
    }
    
    //returns the player xcoord
    public int getXcoord() {
        return xcoord;
    }
    
    //returns the player ycoord
    public int getYcoord() {
        return ycoord;
    }
    
    /*  THIS METHOD WAS USED IN THE C++ VERSION
    //returns the address of the player's inventory ArrayList
    public ArrayList<Item> getInventory() {
        return inventory;
    }
    */
    
    //returns an Item object representation of the Item located at a given index of the player inventory
    //This method was not needed in the C++ version which utalized pointers for this purpose in the main
    public Item getItemFromInventory(int index) {
        return new Item(inventory.get(index));
    }
    
    //returns the amount of items in the player's inventory
    public int getInventorySize() {
        return inventory.size();
    }
    
    //list the player's inventory
    public void listInventory() {
        System.out.println("\t\t< Inventory >");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("\t\t<" + (i + 1) + ">\t" + inventory.get(i).getName());
        }
    }
    
    //set the player name
    public void setName(String name) {
        this.name = name;
    }
    
    //set the player position
    public void setPos(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }
    
    //add an Item to player inventory
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    //remove an Item from player inventory
    public void removeItem(Item item) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).equals(item)) {
                inventory.remove(i);
                break;
            }
        }    
    }
    
    //resets the player data to default constructor data
    public void reset() {
        name = "Bob";
        //spawn at pos 2,1
        xcoord = 2;
        ycoord = 1;
        inventory = new ArrayList<Item>();
    }
    
    //moves the player north
    public boolean moveNorth(ArrayList<Integer> flags) {
        //if not going outside the map
        if (ycoord != 4) {
            if (xcoord == 1 && ycoord == 3) {
                return false;
            } else if ((xcoord == 2 && ycoord == 3) && flags.get(1) == 0) {
                return false;
            } else if (xcoord == 0 && ycoord == 2) {
                return false;
            } else if (xcoord == 1 && ycoord == 1) {
                return false;
            } else if ((xcoord == 3 && ycoord == 1) && flags.get(4) == 0) {
                return false;
            } else if (xcoord == 0 && ycoord == 0) {
                return false;
            } else {
                ycoord++;
                return true;
            }
        } else {
            return false;
        }        
    }
    
    //moves the player south
    public boolean moveSouth(ArrayList<Integer> flags) {
        //if not going outside the map
        if (ycoord != 0) {
            if (xcoord == 1 && ycoord == 4) {
                return false;
            } else if ((xcoord == 2 && ycoord == 4) && flags.get(1) == 0) {
                return false;
            } else if (xcoord == 0 && ycoord == 3) {
                return false;
            } else if (xcoord == 1 && ycoord == 2) {
                return false;
            } else if ((xcoord == 3 && ycoord == 2) && flags.get(4) == 0) {
                return false;
            } else if (xcoord == 0 && ycoord == 1) {
                return false;
            } else {
                ycoord--;
                return true;  
            }
        } else {
            return false;
        }
    }
    
    //moves the player east
    public boolean moveEast(ArrayList<Integer> flags) {
        //if not going outside the map
        if (xcoord != 3) {
            if ((xcoord == 0 && ycoord == 4) && flags.get(3) == 0) {
                return false;
            } else if ((xcoord == 1 && ycoord == 4) && flags.get(2) == 0) {
                return false;
            } else if (xcoord == 2 && ycoord == 4) {
                return false;
            } else if (xcoord == 0 && ycoord == 3) {
                return false;
            } else if (xcoord == 1 && ycoord == 2) {
                return false;
            } else if (xcoord == 1 && ycoord == 1) {
                return false;
            } else if (xcoord == 2 && ycoord == 1) {
                return false;
            } else if ((xcoord == 0 && ycoord == 0) && flags.get(0) == 0) {
                return false;
            } else if (xcoord == 1 && ycoord == 0) {
                return false;
            } else if (xcoord == 2 && ycoord == 0) {
                return false;
            } else {
                xcoord++;
                return true;
            }
        } else {
            return false;
        }
    }
    
    //moves the player west
    public boolean moveWest(ArrayList<Integer> flags) {
        //if not going outside the map
        if (xcoord != 0) {
            if ((xcoord == 1 && ycoord == 4) && flags.get(3) == 0) {
                return false;
            } else if ((xcoord == 2 && ycoord == 4) && flags.get(2) == 0) {
                return false;
            } else if (xcoord == 3 && ycoord == 4) {
                return false;
            } else if (xcoord == 1 && ycoord == 3) {
                return false;
            } else if (xcoord == 2 && ycoord == 2) {
                return false;
            } else if (xcoord == 2 && ycoord == 1) {
                return false;
            } else if (xcoord == 3 && ycoord == 1) {
                return false;
            } else if ((xcoord == 1 && ycoord == 0) && flags.get(0) == 0) {
                return false;
            } else if (xcoord == 2 && ycoord == 0) {
                return false;
            } else if (xcoord == 3 && ycoord == 0) {
                return false;
            } else {
                xcoord--;
                return true;
            }
        } else {
            return false;
        }        
    } 
}