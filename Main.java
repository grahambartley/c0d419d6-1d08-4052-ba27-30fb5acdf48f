/*This will be the first code segment executed*/

//imports
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.RandomAccessFile;
import java.io.IOException;

//Main class
public class Main {
    //variables
    static final int PLAY = 1;
    static final int STOP = 0;
    static final int MENU = 2;
    static final int END_GAME = 3;
    static int STATE = MENU;
    static Room[][] rooms = new Room[4][5];
    static Player player = new Player();
    static Item[] items = Init.initItems();
    //This is no longer needed in the Java version and was causing problems
    //static ArrayList<Item> inventory = player.getInventory();
    static ArrayList<Integer> flags = Init.initFlags();
    /*
        flags[0] = status of door from 10 to 00 (0 for locked, 1 for unlocked)
        flags[1] = status of door from 23 to 24 (0 for locked, 1 for unlocked)
        flags[2] = status of wall from 24 to 14 (0 for intact, 1 for destroyed)
        flags[3] = status of door from 14 to 04 (0 for locked, 1 for unlocked)
        flags[4] = status of wall from 32 to 31 (0 for sealed, 1 for open)
        flags[5] = status of drain in 12 (0 for key in drain, 1 for no key in drain)
        flags[6] = status of desc for Water Chamber (0 for default, 1 for item picked up)
        flags[7] = status of desc for Wash Room (0 for default, 1 for item picked up)
        flags[8] = status of desc for Unfinished Excavation (0 for default, 1 for item picked up)
        flags[9] = status of desc for Tomb Entrance (0 for default, 1 for item picked up)
        flags[10] = status of desc for Scullery (0 for default, 1 for item picked up)
    */
    
    //methods
    //first method to be executed
    public static void main(String[] args) {
        //print the Castle Escape logo
        printLogo();
        //loop
        while (STATE != STOP) {
            //menu
            if (STATE == MENU) {
                mainMenu();
            } else if (STATE == PLAY) {
                playGame();
            } else if (STATE == END_GAME) {
                Helper.tab(1);
                System.out.println("Error: Wrong state (END_GAME)");
                Helper.sleep(1);
                STATE = MENU;
            }
        }
        
        //close game application
        Helper.newLine(1);
        Helper.tab(1);
        System.out.println("Thank you for playing Castle Escape! <3");
        Helper.sleep(2);        
    }
    
    //prints the castle escape logo
    public static void printLogo() {
        Helper.newLine(2);
        Helper.tab(1);
        System.out.println("    ||||||     ||      ||||||||  ||||||||||  ||        |||||||||");
        Helper.tab(1);
        System.out.println("  ||          ||||    ||             ||      ||        ||");
        Helper.tab(1);
        System.out.println("||           ||  ||   ||             ||      ||        ||");
        Helper.tab(1);
        System.out.println("||          ||    ||   |||||||       ||      ||        |||||||");
        Helper.tab(1);
        System.out.println("||          ||||||||         ||      ||      ||        ||");
        Helper.tab(1);
        System.out.println("  ||        ||    ||         ||      ||      ||        ||");
        Helper.tab(1);
        System.out.println("    ||||||  ||    ||  ||||||||       ||      ||||||||  |||||||||");
        Helper.newLine(2);
        Helper.tab(1);
        System.out.println("|||||||||   ||||||||      ||||||     ||      |||||||   |||||||||");
        Helper.tab(1);
        System.out.println("||         ||           ||          ||||    ||     ||  ||");
        Helper.tab(1);
        System.out.println("||         ||         ||           ||  ||   ||     ||  ||");
        Helper.tab(1);
        System.out.println("|||||||     |||||||   ||          ||    ||  ||||||||   |||||||");
        Helper.tab(1);
        System.out.println("||                ||  ||          ||||||||  ||         ||");
        Helper.tab(1);
        System.out.println("||                ||    ||        ||    ||  ||         ||");
        Helper.tab(1);
        System.out.println("|||||||||  ||||||||       ||||||  ||    ||  ||         |||||||||");
        Helper.newLine(1);
        Helper.sleep(3);
        Helper.tab(4);
        System.out.println("An adventure-puzzle game by Graham Bartley.");
        Helper.newLine(1);
        Helper.sleep(5);        
    }
    
    //prints the main menu
    public static void mainMenu() {
        Scanner scan = new Scanner(System.in);
        
        Helper.newLine(1);
        Helper.tab(2);
        System.out.println(" ________________________ ");
        Helper.tab(2);
        System.out.println("|-|--------------------|-|");
        Helper.tab(2);
        System.out.println("|-|   < Main Menu >    |-|");
        Helper.tab(2);
        System.out.println("|-|                    |-|");
        Helper.tab(2);
        System.out.println("|-|   <1>  New Game    |-|");
        Helper.tab(2);
        System.out.println("|-|   <2>  Load Game   |-|");
        Helper.tab(2);
        System.out.println("|-|   <3>  Help        |-|");
        Helper.tab(2);
        System.out.println("|-|   <4>  Exit        |-|");
        Helper.tab(2);
        System.out.println("|-|                    |-|");
        Helper.tab(2);
        System.out.println("|-|--------------------|-|");
        Helper.tab(2);
        Helper.newLine(1);

        Helper.tab(1);
        System.out.println("Choose an option from the menu");
        Helper.tab(1);
        System.out.print("> ");
        int choice = scan.nextInt();
        
        if (choice == 1) {
            //New Game
            //initialize player and rooms to defaults
            Init.initPlayer(player);
            Init.initMap(rooms);
            STATE = PLAY;
        } else if (choice == 2) {
            //Load Game
            Helper.newLine(1);
            Helper.tab(1);
            //If a save file exists, load it...
            if ((new File("./cesaves/save0.dat")).exists()) {
                System.out.println("Loading...");
                Helper.sleep(3);
                Helper.newLine(1);
                //initialize player and rooms to defaults
                player.reset();
                Init.initMap(rooms);
                try {
                    //RandomAccessFile is used to read and write to a binary file and can be opened as such
                    RandomAccessFile myFile = new RandomAccessFile("./cesaves/save0.dat", "r");           
                    /*
                    This array will hold the int values read in from the save file:
                        [0] : xcoord
                        [1] : ycoord
                        [2] : inventory size
                        [3] : name length
                        [4] : flags[0]
                        [5] : flags[1]
                        [6] : flags[2]
                        [7] : flags[3]
                        [8] : flags[4]
                        [9] : flags[5]
                        [10]: flags[6]
                        [11]: flags[7]
                        [12]: flags[8]
                        [13]: flags[9]
                        [14]: flags[10]
                    */
                    //An array to store read in ints
                    int[] intValues = new int[15];
                    int i = 0;
                    //read in ints from save file
                    while (myFile.getFilePointer() < intValues.length * 4) {
                        intValues[i] = myFile.readInt();
                        i++;
                    }
                    
                    //Store these ints in appropriate variables
                    int xcoord, ycoord, inventorySize, nameLength;
                    xcoord = intValues[0];
                    ycoord = intValues[1];
                    inventorySize = intValues[2];
                    nameLength = intValues[3];
                    int[] flagsArray = {intValues[4], intValues[5], intValues[6], intValues[7], intValues[8],
                                        intValues[9], intValues[10], intValues[11], intValues[12], intValues[13], intValues[14]};                
                    
                    //Store current filePointer
                    long filePointerVar = myFile.getFilePointer();
                    
                    //This array will hold the characters of the player name
                    char[] nameChars = new char[nameLength];                
                    i = 0;
                    //read in chars of player name from save file
                    while (myFile.getFilePointer() < filePointerVar + (nameLength * 2)) {
                        nameChars[i] = myFile.readChar();
                        i++;
                    }
                    String playerName = new String(nameChars);
                    
                    //Store current filePointer
                    filePointerVar = myFile.getFilePointer();                
                    
                    //int array for storing itemIDs read in from save file
                    int[] itemIDs = new int[intValues[2]];
                    i = 0;
                    //read in itemIDs
                    while (myFile.getFilePointer() < filePointerVar + intValues[2] * 4) {
                        itemIDs[i] = myFile.readInt();
                        i++;
                    }
                    
                    //Finished reading from the save file.
                    myFile.close();
                    
                    //Set data in game to data retrieved from save file.
                    //Set flags
                    for (int j = 0; j < flagsArray.length; j++) {
                        flags.set(j, flagsArray[j]);
                    }
                    
                    //Set the player position
                    player.setPos(xcoord, ycoord);
                    
                    //Set the player name
                    player.setName(playerName);
                    
                    //Set the player inventory
                    for (int j = 0; j < inventorySize; j++) {
                        player.addItem(getItemFromItemID(itemIDs[j]));
                    }
                    
                    //Update room descriptions (if necessary)
                    if (flags.get(0) == 1) {
                        //Set the desc for the Tomb of a Queen
                        rooms[1][0].setDesc("This burial chamber is highly decorative, but lacking in treasures...\n\tThere are markings in the dust of where items of value once were.\n\tA stone coffin with signs of attempted burglary lays sealed in the centre of the tomb.\n\tA rusted iron door stands open in the western wall.");
                    }
                    if (flags.get(1) == 1) {
                        //Set the desc for the Dining Room
                        rooms[2][3].setDesc("A large dining table spans the centre of the room with benches either side of it.\n\tAn old family crest can be seen on the table.\n\tThere's an open door to the north leading to the Kitchen.");    
                    }
                    if (flags.get(2) == 1) {
                        //Set the desc for the Kitchen
                        rooms[2][4].setDesc("An old kitchen with antique counter-tops and pots and pans lining the walls.\n\tThe western wall of the room has been knocked down and rubble clutters the floor...");                    
                    }                
                    if (flags.get(3) == 1) {
                        //Set the desc for the Old Kitchen
                        rooms[1][4].setDesc("This kitchen was decommissioned long before the other.\n\tIt was sealed off by the wall that stood to the east, which is now a pile of rubble.\n\tThere is hardly anything left of what this kitchen once was.\n\tThere are broken jars in the doorway to the west.");                    
                    }
                    if (flags.get(4) == 1) {
                        //Set the desc for the Throne Room
                        rooms[3][2].setDesc("The walls are decorated with torn banners showing an old family crest.\n\tThe room is aglow with intense, never-fading torchlight.\n\tThere is a passage in the southern wall which was previously blocked by a throne.");                    
                    }
                    if (flags.get(5) == 1) {
                        //Set the desc for the Thief's Den and Wash Room
                        rooms[1][2].setDesc("This den appears to have been home to a burglar.\n\tThere are rags hanging from corner-to-corner of the roof and a tent against the south wall.\n\tThe rest of the room is packed with useless junk.\n\tThere is an old water drain in the ground against the east wall.");
                        rooms[2][2].setDesc("There is an antique bathtub against the wall and small steps leading to a broken sink.\n\tAn old pipe is exposed which was once underground and none of the plumbing works.\n\tA golden glow can be seen coming from the exposed pipe...\n\tA passage to the south leads to the Old Sewers.");                                            
                    }
                    if (flags.get(6) == 1) {
                        //Set the desc for the Water Chamber
                        rooms[0][0].setDesc("A small, square room with a pedastal in the centre.\n\tA basin of water sits upon it.");
                    }
                    if (flags.get(7) == 1) {
                        //Set the desc for the Wash Room
                        rooms[2][2].setDesc("There is an antique bathtub against the wall and small steps leading to a broken sink.\n\tAn old pipe is exposed which was once underground and none of the plumbing works.\n\tA passage to the south leads to the Old Sewers.");
                    }
                    if (flags.get(8) == 1) {
                        //Set the desc for the Unfinished Excavation
                        rooms[3][4].setDesc("This excavation was abandoned mid-creation and it's creator lies against the wall to the west.\n\tThe ceiling is supported by rotting wooden shafts and looks as though it could collapse at any moment.\n\tWhat did he discover that led him to such a fate?");
                    }
                    if (flags.get(9) == 1) {
                        //Set the desc for the Tomb Entrance
                        rooms[1][1].setDesc("Two large, ornate doors stand wide-open to the south... the air grows colder as you approach them.\n\tIn the north-eastern corner of the room there are some skeletons.");
                    }
                    if (flags.get(10) == 1) {
                        //Set the desc for the Scullery
                        rooms[0][3].setDesc("Broken plates and goblets lay piled up on the counters and in cabinets on the walls.\n\tThere is a rather large dried-blood stain on the ground next to the eastern wall.\n\tThis appears to be a dead end.");
                    }
                }
                catch (IOException e) {
                    Helper.tab(1);
                    System.out.println("Oh no! Something went wrong...");
                    Helper.newLine(1);
                    Helper.sleep(3);
                    e.printStackTrace();
                }
                //once everything is loaded, play the game...
                STATE = PLAY;
            } else {
                //If no save file is found...
                System.out.println("No save file found!");
                Helper.sleep(3);
                Helper.newLine(1);
            }
        } else if (choice == 3) {
            //Help
            Helper.newLine(1);
            Helper.tab(1);
            System.out.println("Castle Escape is a text-based adventure-puzzle game.");
            Helper.sleep(2);
            Helper.newLine(1);
            Helper.tab(1);
            System.out.println("The objective of the game is to escape the castle you find yourself in.");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("There are various different rooms to the castle which you can travel through on your adventure.");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("You may find items as you go along which you can collect. These will aid you in your escape!");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("Items can only be used under specific circumstances, so think about when each item may be needed.");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("It can be very useful to draw a map as you journey through the castle as it's easy to get lost.");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("Pay close attention to room and item descriptions as they may provide hints on what to do!");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("Also be careful to only provide input when asked for it, otherwise glitches may occur which cause you to lose progress!");
            Helper.sleep(2);
            Helper.tab(1);
            System.out.println("Other than that, it's up to you! Good luck escaping the castle!");
            Helper.sleep(3);
            mainMenu();
        } else if (choice == 4) {
            //Exit Application
            STATE = STOP;
        } else {
            //Invalid Input
            Helper.newLine(1);
            Helper.tab(1);
            System.out.println("Invalid input.");
            Helper.sleep(1);
            mainMenu();            
        }
    }

    //plays the game
    public static void playGame() {
        int choice = -1;
        
        while (STATE == PLAY) {
            printStatus();
            choice = getPlayerChoice();
            
            //Use an item function
            if (choice == 1) {
                useItem();
            //Look for items in the room function
            } else if (choice == 2) {        
                searchForItems();
            //Move north function
            } else if (choice == 3) {
                movePlayerNorth();
            //Move south function
            } else if (choice == 4) {
                movePlayerSouth();
            //Move east function
            } else if (choice == 5) {
                movePlayerEast();
            //Move west function
            } else if (choice == 6) {
                movePlayerWest();
            //Open inventory function
            } else if (choice == 7) {
                accessInventory();
            //Return to Main Menu function
            } else if (choice == 8) {
                if (saveGame()) {
                    Helper.tab(1);
                    System.out.println("Save successful!");
                } else {
                    Helper.tab(1);
                    System.out.println("Save unsuccessful! Please try again later.");
                }
                Helper.sleep(3);
                Helper.newLine(1);
            } else if (choice == 9) {
                STATE = MENU;
            }
            
            //if the Castle Grounds are reached, the game is over
            if (player.getXcoord() == 3 && player.getYcoord() == 0) {
                STATE = END_GAME;
            }
        }

        while (STATE == END_GAME) {
            Helper.tab(1);
            System.out.println("The stairs lead you outside to the Castle Grounds where you will make your escape...\n\tBut not before taking some of the treasure with you!");      
            Helper.sleep(4);
            Helper.newLine(1);
            Helper.tab(1);
            System.out.println("Congratulations, " + player.getName() + ", you beat the game!");
            Helper.sleep(4);
            STATE = MENU;
        }
        
        //return to the menu
        STATE = MENU;
        Helper.sleep(1);
    }
    
    //prints the current game status
    public static void printStatus() {
        Helper.tab(1);
        System.out.println(player.getName());
        Helper.tab(1);
        System.out.println("Location: " + rooms[player.getXcoord()][player.getYcoord()].getName());
        Helper.sleep(2);
        Helper.newLine(1);
        Helper.tab(1);
        System.out.println(rooms[player.getXcoord()][player.getYcoord()].getDesc());
        Helper.newLine(1);
        Helper.sleep(3);
    }
    
    //lists options and gets choice of the player based on position & items
    public static int getPlayerChoice() {
        Scanner scan = new Scanner(System.in);
        
        Helper.tab(2);
        System.out.println("< Your Options >");
        Helper.tab(2);
        System.out.println("<1>\t Use an item.");
        Helper.tab(2);
        System.out.println("<2>\t Look for items in the room.");
        Helper.tab(2);
        System.out.println("<3>\t Walk north.");
        Helper.tab(2);
        System.out.println("<4>\t Walk south.");
        Helper.tab(2);
        System.out.println("<5>\t Walk east.");
        Helper.tab(2);
        System.out.println("<6>\t Walk west.");
        Helper.tab(2);
        System.out.println("<7>\t Open inventory.");
        Helper.tab(2);
        System.out.println("<8>\t Save game.");
        Helper.tab(2);
        System.out.println("<9>\t Return to the Main Menu. (Unsaved progress will be lost!)");
        
        Helper.newLine(1);
        Helper.tab(1);
        System.out.println("What would you like to do?");
        Helper.tab(1);
        System.out.print("> ");
        int choice = scan.nextInt();
        Helper.newLine(1);
        Helper.sleep(1);
        return choice;
    }
    
    //gets itemID of a given Item object
    public static int getItemIDFromItem(Item item) {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(item)) {
                return i;
            }
        }
        //If looking for an item that doesn't exist...
        return -1;
    }
    
    //gets an Item from a given itemID
    public static Item getItemFromItemID(int itemID) {
        return items[itemID];
    }
    
    //use item function
    public static void useItem() {
        Scanner scan = new Scanner(System.in);
        int itemChoice = -1;
        //if the player's inventory is not empty
        if (player.getInventorySize() > 0) {
            player.listInventory();
            Helper.newLine(1);
            Helper.tab(1);
            System.out.println("Enter an item number to use that item or enter 0 to return to options.");
            Helper.tab(1);
            System.out.print("> ");
            itemChoice = scan.nextInt();
            Helper.newLine(1);
            Helper.sleep(1);
            //to use an item if needed.
            if (itemChoice > 0 && itemChoice <= player.getInventorySize()) {
                if(player.getItemFromInventory(itemChoice - 1).getName() == "Rusted key" && player.getXcoord() == 1 && player.getYcoord() == 0 && flags.get(0) == 0) {
                    flags.set(0, 1);
                    rooms[player.getXcoord()][player.getYcoord()].setDesc("This burial chamber is highly decorative, but lacking in treasures...\n\tThere are markings in the dust of where items of value once were.\n\tA stone coffin with signs of attempted burglary lays sealed in the centre of the tomb.\n\tA rusted iron door stands open in the western wall.");
                    System.out.println("\tYou place the Rusted key in the equally-rusted keyhole of the door and turn it.\n\tThe keyhole crumbles to dust and you open the door with ease.");
                } else if (player.getItemFromInventory(itemChoice - 1).getName() == "Steel key" && player.getXcoord() == 2 && player.getYcoord() == 3 && flags.get(1) == 0) {
                    flags.set(1, 1);
                    rooms[player.getXcoord()][player.getYcoord()].setDesc("A large dining table spans the centre of the room with benches either side of it.\n\tAn old family crest can be seen on the table.\n\tThere's an open door to the north leading to the Kitchen.");                        
                    System.out.println("\tYou place the Steel key in the keyhole of the wooden door and turn it slowly.\n\tThe locking mechanism grinds open and you push the door forward revealing the kitchen.");
                } else if (player.getItemFromInventory(itemChoice - 1).getName() == "Bucket" && player.getXcoord() == 0 && player.getYcoord() == 0) {
                    player.removeItem(new Item("Bucket", "\tUsed for carrying liquids and such.\n"));
                    player.addItem(new Item("Bucket of water", "\tA wooden bucket full of clear water.\n"));
                    System.out.println("\tYou fill your bucket with water from the basin.");
                } else if (player.getItemFromInventory(itemChoice - 1).getName() == "Bucket of water" && player.getXcoord() == 1 && player.getYcoord() == 2 && flags.get(5) == 0) {
                    flags.set(5, 1);
                    player.removeItem(new Item("Bucket of water", "\tA wooden bucket full of clear water.\n"));
                    player.addItem(new Item("Bucket", "\tUsed for carrying liquids and such.\n"));
                    rooms[2][2].addItem(new Item("Golden key", "An expertly-made golden key with gems down it's side."));
                    rooms[player.getXcoord()][player.getYcoord()].setDesc("This den appears to have been home to a burglar.\n\tThere are rags hanging from corner-to-corner of the roof and a tent against the south wall.\n\tThe rest of the room is packed with useless junk.\n\tThere is an old water drain in the ground against the east wall.");
                    rooms[2][2].setDesc("There is an antique bathtub against the wall and small steps leading to a broken sink.\n\tAn old pipe is exposed which was once underground and none of the plumbing works.\n\tA golden glow can be seen coming from the exposed pipe...\n\tA passage to the south leads to the Old Sewers.");                        
                    System.out.println("\tYou pour the water from your bucket down the drain and hear the clinking of metal in underground pipes...");
                } else if (player.getItemFromInventory(itemChoice - 1).getName() == "Pickaxe" && player.getXcoord() == 2 && player.getYcoord() == 4 && flags.get(2) == 0) {
                    flags.set(2, 1);
                    rooms[2][4].setDesc("An old kitchen with antique counter-tops and pots and pans lining the walls.\n\tThe western wall of the room has been knocked down and rubble clutters the floor...");
                    System.out.println("\tYou hack away at the damaged western wall of the Kitchen...\n\tIt's not long before you break through and the wall falls to pieces.\n\tA room exists beyond the wall!");
                } else if (player.getItemFromInventory(itemChoice - 1).getName() == "Unlit torch" && player.getXcoord() == 3 && player.getYcoord() == 2 && flags.get(4) == 0) {
                    flags.set(4, 1);
                    player.removeItem(new Item("Unlit torch", "\tA worn-looking wooden torch, although it's not lit now there are signs that it once was.\n"));
                    rooms[3][2].setDesc("The walls are decorated with torn banners showing an old family crest.\n\tThe room is aglow with intense, never-fading torchlight.\n\tThere is a passage in the southern wall which was previously blocked by a throne.");
                    System.out.println("\tYou place the Unlit torch in the empty sconce on the wall.\n\tSuddenly, it lights as if struck by an invisible match and the flames of all of the torches intesify...\n\tYou hear the grinding of gears and the throne begins to lower slowly into the flooring\n\tA hidden passage is revealed where it stood.");
                } else if (player.getItemFromInventory(itemChoice - 1).getName() == "Golden key" && player.getXcoord() == 1 && player.getYcoord() == 4 && flags.get(3) == 0) {
                    flags.set(3, 1);
                    rooms[1][4].setDesc("This kitchen was decommissioned long before the other.\n\tIt was sealed off by the wall that stood to the east, which is now a pile of rubble.\n\tThere is hardly anything left of what this kitchen once was.\n\tThere are broken jars in the doorway to the west.");
                    System.out.println("\tYou unlock the door using the Golden key.\n\tOld broken jars fall through the door as you open it.");
                } else {
                    System.out.println("\tNothing interesting happens...");
                }
                Helper.sleep(4);
                Helper.newLine(1);
            }                
        } else {
            Helper.tab(2);
            System.out.println("< Inventory >");
            Helper.tab(2);
            System.out.println("Your inventory is empty.");
            Helper.newLine(1);
        }
    }
    
    //function to search for items in a room and (maybe) pick them up
    public static void searchForItems() {
        Scanner scan = new Scanner(System.in);
        Room currentRoom = rooms[player.getXcoord()][player.getYcoord()];
        int itemChoice = 1;
        while (itemChoice > 0) {
            //if room is not empty, it can be listed
            if (currentRoom.getLootSize() > 0) {
                currentRoom.listLoot();
                Helper.newLine(1);
                Helper.tab(1);
                System.out.println("Enter an item number to pick up that item or enter 0 to not pick up anything.");
                Helper.tab(1);
                System.out.print("> ");
                itemChoice = scan.nextInt();
                Helper.newLine(1);
                Helper.sleep(1);
                //to put items in player inventory and remove them from room loot if needed
                if (itemChoice > 0 && itemChoice <= currentRoom.getLootSize()) {
                    ArrayList<Item> loot = currentRoom.getLoot();
                    //If the Steel key is being picked up, the desc for the Water Chamber must change
                    if (loot.get(itemChoice - 1).getName() == "Steel key" && rooms[0][0].getDesc() != "A small, square room with a pedastal in the centre.\n\tA basin of water sits upon it.") {
                        flags.set(6, 1);
                        rooms[0][0].setDesc("A small, square room with a pedastal in the centre.\n\tA basin of water sits upon it.");
                    }
                    //If the Golden key is being picked up, the desc for the Wash Room must change
                    if (loot.get(itemChoice - 1).getName() == "Golden key" && rooms[2][2].getDesc() != "There is an antique bathtub against the wall and small steps leading to a broken sink.\n\tAn old pipe is exposed which was once underground and none of the plumbing works.\n\tA passage to the south leads to the Old Sewers.") {
                        flags.set(7, 1);
                        rooms[2][2].setDesc("There is an antique bathtub against the wall and small steps leading to a broken sink.\n\tAn old pipe is exposed which was once underground and none of the plumbing works.\n\tA passage to the south leads to the Old Sewers.");
                    }
                    //If the Pickaxe is being picked up, the desc for the Unfinished Excavation must change
                    if (loot.get(itemChoice - 1).getName() == "Pickaxe" && rooms[3][4].getDesc() != "This excavation was abandoned mid-creation and it's creator lies against the wall to the west.\n\tThe ceiling is supported by rotting wooden shafts and looks as though it could collapse at any moment.\n\tWhat did he discover that led him to such a fate?") {
                        flags.set(8, 1);
                        rooms[3][4].setDesc("This excavation was abandoned mid-creation and it's creator lies against the wall to the west.\n\tThe ceiling is supported by rotting wooden shafts and looks as though it could collapse at any moment.\n\tWhat did he discover that led him to such a fate?");
                    }
                    //If the Bucket is being picked up, the desc for the Tomb Entrance must change
                    if (loot.get(itemChoice - 1).getName() == "Bucket" && rooms[1][1].getDesc() != "Two large, ornate doors stand wide-open to the south... the air grows colder as you approach them.\n\tIn the north-eastern corner of the room there are some skeletons.") {
                        flags.set(9, 1);
                        rooms[1][1].setDesc("Two large, ornate doors stand wide-open to the south... the air grows colder as you approach them.\n\tIn the north-eastern corner of the room there are some skeletons.");
                    }
                    //If the Unlit torch is being picked up, the desc for the Scullery must change
                    if (loot.get(itemChoice - 1).getName() == "Unlit torch" && rooms[0][3].getDesc() != "Broken plates and goblets lay piled up on the counters and in cabinets on the walls.\n\tThere is a rather large dried-blood stain on the ground next to the eastern wall.\n\tThis appears to be a dead end.") {
                        flags.set(10, 1);
                        rooms[0][3].setDesc("Broken plates and goblets lay piled up on the counters and in cabinets on the walls.\n\tThere is a rather large dried-blood stain on the ground next to the eastern wall.\n\tThis appears to be a dead end.");
                    }
                    if (loot.get(itemChoice - 1).getName() == "Treasures") {
                        Helper.tab(1);
                        System.out.println("Maybe you should investigate the staircase to the south first.");
                        Helper.newLine(1);
                        Helper.sleep(2);                             
                    } else {
                        player.addItem(loot.get(itemChoice - 1));
                        Helper.tab(1);
                        System.out.println("You pick up the " + loot.get(itemChoice - 1).getName() + ".");
                        Helper.newLine(1);
                        currentRoom.removeItem(loot.get(itemChoice - 1));
                        Helper.sleep(2);
                    }
                }
            //otherwise room is empty, so print this and return                
            } else {
                Helper.tab(2);
                System.out.println("< Items in this room >");
                Helper.tab(2);
                System.out.println("There are no items in this room.");
                Helper.newLine(1);
                itemChoice = 0;
            }
        }
    }

    //inventory function (list, get item descriptions, drop items)
    public static void accessInventory() {
        Scanner scan = new Scanner(System.in);
        //int functionChoice = 1;
        //while (functionChoice > 0) {
            //if inventory is not empty, it can be listed
            if (player.getInventorySize() > 0) {
                /*
                I removed the drop item functionality for now as it's not included in save files
                
                player.listInventory();
                Helper.newLine(1);
                Helper.tab(1);
                System.out.println("Enter 1 for item descriptions, enter 2 to drop an item, or enter 0 to exit inventory.");
                Helper.tab(1);
                System.out.print("> ");
                functionChoice = scan.nextInt();
                Helper.newLine(1);
                Helper.sleep(1);                    
                //enter item description mode
                if (functionChoice == 1) {
                */
                    int itemChoice = 1;
                    //while items are being chosen for description
                    while (itemChoice > 0) {
                        player.listInventory();
                        Helper.newLine(1);
                        Helper.tab(1);
                        System.out.println("Enter an item number for a description of that item or enter 0 to exit the inventory.");
                        Helper.tab(1);
                        System.out.print("> ");
                        itemChoice = scan.nextInt();
                        Helper.newLine(1);
                        Helper.sleep(1);
                        //to print item descriptions if needed.
                        if (itemChoice > 0 && itemChoice <= player.getInventorySize()) {
                            System.out.println(player.getItemFromInventory(itemChoice - 1).getDesc());
                            Helper.sleep(2);
                        }
                    }
                    /*
                }
                //enter item removal mode
                if (functionChoice == 2) {
                    int itemChoice = 1;
                    while (itemChoice > 0) {
                        //if inventory is not empty, it can be listed (check since inventory can be changed in this mode)
                        if (player.getInventorySize() > 0) {
                            player.listInventory();
                            Helper.newLine(1);
                            Helper.tab(1);
                            System.out.println("Enter an item number to drop that item or enter 0 to exit to inventory.");
                            Helper.tab(1);
                            System.out.print("> ");
                            itemChoice = scan.nextInt();
                            Helper.newLine(1);
                            Helper.sleep(1);
                            //to drop an item if needed.
                            if (itemChoice > 0 && itemChoice <= player.getInventorySize()) {                              
                                //add the item to the current room
                                Room currentRoom = rooms[player.getXcoord()][player.getYcoord()];
                                currentRoom.addItem(player.getItemFromInventory(itemChoice - 1));
                                //print message
                                Helper.tab(1);
                                System.out.println("You lay the " + player.getItemFromInventory(itemChoice - 1).getName() + " down on the floor.");
                                Helper.newLine(1);
                                //remove the item from the player's inventory
                                player.removeItem(player.getItemFromInventory(itemChoice - 1));
                                Helper.sleep(2);
                            }
                            
                        } else {
                            Helper.tab(2);
                            System.out.println("< Inventory >");
                            Helper.tab(2);
                            System.out.println("Your inventory is empty.");
                            Helper.newLine(1);
                            itemChoice = 0;
                            functionChoice = 0;
                        }
                    }
                }
                */
            //otherwise inventory is empty, so print this and return
            } else {
                Helper.tab(2);
                System.out.println("< Inventory >");
                Helper.tab(2);
                System.out.println("Your inventory is empty.");
                Helper.newLine(1);
                //functionChoice = 0;
            }
        //}
    }

    //function used to save the game progress to a file
    public static boolean saveGame() {
        File saveDir = new File("./cesaves");
        //if the save directory doesn't exist, create it
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        //Then deal with the file itself
        File saveFile = new File("./cesaves/save0.dat");
        try {
            saveFile.createNewFile();
            RandomAccessFile saveFileO = new RandomAccessFile(saveFile, "rw");
            
            //write int data to save file
            saveFileO.writeInt(player.getXcoord());
            saveFileO.writeInt(player.getYcoord());
            saveFileO.writeInt(player.getInventorySize());
            saveFileO.writeInt(player.getName().length());
            for (int i = 0; i < flags.size(); i++) {
                saveFileO.writeInt(flags.get(i));
            }
            
            //write player name to save file as a String
            saveFileO.writeChars(player.getName());
            
            //write inventory to save file as int itemIDs
            for (int i = 0; i < player.getInventorySize(); i++) {
                saveFileO.writeInt(getItemIDFromItem(player.getItemFromInventory(i)));
            }
            
            //finished writing to the save file
            saveFileO.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //If return true wasn't reached, saving failed
        return false;
    }
    
    //move north function
    public static void movePlayerNorth() {
        Helper.tab(1);
        if (player.moveNorth(flags)) {
            if (player.getXcoord() == 2 && player.getYcoord() == 4) {
                System.out.println("You walk north through the door and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                                        
            } else {
                System.out.println("You walk north through a passage and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            }
        } else {
            if (player.getXcoord() == 2 && player.getYcoord() == 3) {
                System.out.println("You try to open the door to the north but it's locked tight.");
            } else {
                System.out.println("A wall blocks your way. You cannot walk north from here.");
            }
        }
        Helper.sleep(3);
        Helper.newLine(1);
    }
    
    //move south function
    public static void movePlayerSouth() {
        Helper.tab(1);
        if (player.moveSouth(flags)) {
            if (player.getXcoord() == 2 && player.getYcoord() == 3) {
                System.out.println("You walk south through the door and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                                        
            } else {
                System.out.println("You walk south through a passage and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            }
        } else {
            if (player.getXcoord() == 2 && player.getYcoord() == 4) {
                System.out.println("You try to open the door to the south but it's locked tight.");
            } else {
                System.out.println("A wall blocks your way. You cannot walk south from here.");
            }
        }
        Helper.sleep(3);
        Helper.newLine(1);
    }    
    
    //move east function
    public static void movePlayerEast() {
        Helper.tab(1);
        if (player.moveEast(flags)) {
            if (player.getXcoord() == 1 && player.getYcoord() == 4) {
                System.out.println("You walk east through the door and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } else if (player.getXcoord() == 1 && player.getYcoord() == 0) {
                System.out.println("You walk east through the door and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } else if (player.getXcoord() == 2 && player.getYcoord() == 4) {
                System.out.println("You walk east through the broken wall and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } else {
                System.out.println("You walk east through a passage and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } 
        } else {
            if (player.getXcoord() == 0 && player.getYcoord() == 4) {
                System.out.println("You try to open the door to the east but it's locked tight.");
            } else if (player.getXcoord() == 0 && player.getYcoord() == 0) {
                System.out.println("You try to open the door to the east but it's locked tight.");
            } else {
                System.out.println("A wall blocks your way. You cannot walk east from here.");
            }              
        }
        Helper.sleep(3);
        Helper.newLine(1);
    }    
    
    //move west function
    public static void movePlayerWest() {
        Helper.tab(1);
        if (player.moveWest(flags)) {
            if (player.getXcoord() == 0 && player.getYcoord() == 4) {
                System.out.println("You walk west through the door and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } else if (player.getXcoord() == 0 && player.getYcoord() == 0) {
                System.out.println("You walk west through the door and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } else if (player.getXcoord() == 1 && player.getYcoord() == 4) {
                System.out.println("You walk west through the broken wall and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            } else {
                System.out.println("You walk west through a passage and reach the " + rooms[player.getXcoord()][player.getYcoord()].getName() + ".");                    
            }                
        } else {
            if (player.getXcoord() == 1 && player.getYcoord() == 4) {
                System.out.println("You try to open the door to the west but it's locked tight.");
            } else if (player.getXcoord() == 1 && player.getYcoord() == 0) {
                System.out.println("You try to open the door to the west but it's locked tight.");
            } else {
                System.out.println("A wall blocks your way. You cannot walk west from here.");
            }
        }
        Helper.sleep(3);
        Helper.newLine(1);
    }
}