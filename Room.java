/*This will be the Room class*/

//imports
import java.util.ArrayList;

//the Room class
public class Room {
    //variables
    int xcoord, ycoord;
    String name;
    String desc;
    ArrayList<Item> loot;
    
    //constructors
    //no args constructor
    public Room() {
        xcoord = 0;
        ycoord = 0;
        name = "";
        desc = "This is a room with no description.";
        loot = new ArrayList<Item>();
    }
    
    //two args constructor
    public Room(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        name = "";
        desc = "This is a room with no description.";
        loot = new ArrayList<Item>();
    }
    
    //three args constructor
    public Room(int xcoord, int ycoord, String name) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.name = name;
        desc = "This is a room with no description.";
        loot = new ArrayList<Item>();
    }
    
    //four args constructor
    public Room(int xcoord, int ycoord, String name, String desc) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.name = name;
        this.desc = desc;
        loot = new ArrayList<Item>();
    }
    
    //five args constructor
    public Room(int xcoord, int ycoord, String name, String desc, Item item) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
        this.name = name;
        this.desc = desc;
        loot = new ArrayList<Item>();
        loot.add(item);
    }
    
    //methods
    //returns the x coord of the room
    public int getXcoord() {
        return xcoord;
    }
    
    //returns the y coord of the room
    public int getYcoord() {
        return ycoord;
    }
    
    //returns the name of the room
    public String getName() {
        return name;
    }
    
    //returns the description of the room
    public String getDesc() {
        return desc;
    }
    
    //returns true if there are no items in the room
    public boolean isEmpty() {
        return this.getLootSize() == 0;
    }
    
    //sets the x coord for the room
    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }
    
    //sets the y coord for the room
    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }
    
    //sets the name for the room
    public void setName(String name) {
        this.name = name;
    }
    
    //sets the description for the room
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    //lists items in the room
    public void listLoot() {
        System.out.println("\t\t< Items in this room >");
        for (int i = 0; i < loot.size(); i++) {
            System.out.println("\t\t<" + (i + 1) + ">\t" + loot.get(i).getName());
        }
        System.out.println("");
    }
    
    //returns the address of the loot ArrayList
    public ArrayList<Item> getLoot() {
        return loot;
    }
    
    //returns the amount of items in the room
    public int getLootSize() {
        return loot.size();
    }
    
    //adds an item to the loot of the room
    public void addItem(Item item) {
        loot.add(item);
    }
    
    //removes an item of loot from the room
    public void removeItem(Item item) {
        for (int i = 0; i < loot.size(); i++) {
            if (loot.get(i).equals(item)) {
                loot.remove(i);
                break;
            }
        }
    }
}