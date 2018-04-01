/*This will be the Item class*/

//imports


//Item class
public class Item {
    //variables
    String name;
    String desc;
    
    //constructors
    //no args constructor
    public Item() {
        this.name = "Item";
        this.desc = "\tThis is an item with no description.\n";
    }
    
    //one arg constructor
    public Item(String name) {
        this.name = name;
        this.desc = "\tThis is an item with no description.\n";
    }
    
    //two args constructor
    public Item(String name, String desc) {
        this.name = name;
        this.desc = desc;
    } 
    
    //This constructor is new in the Java version
    //Item arg constructor
    public Item(Item otherItem) {
        this.name = otherItem.name;
        this.desc = otherItem.desc;
    }
    
    //methods
    //returns the name of the item
    public String getName() {
        return name;
    }
    
    //returns a description of the item
    public String getDesc() {
        return desc;
    }
    
    //sets the name of an item
    public void setName(String name) {
        this.name = name;
    }
    
    //sets the description of an item
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    /*NOTE: C++ version overloads the '==' operator, 
    this cannot be done in Java and will be made up for using the .equals() method for objects*/
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Item)) {
            return false;
        }
        
        Item that = (Item)other;

        //Custom equality check
        return this.name.equals(that.name) && this.desc.equals(that.desc);
    }
}