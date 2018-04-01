/*This file contains helper methods for use in other files in the application*/
//imports
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;

//Helper class
public class Helper {
    //methods   
    //prints a varying amount of new lines
    public static void newLine(int num) {
        for (int i = 0; i < num; i++) {
            System.out.println();
        }
    }
    
    //prints a varying amount of tab chars
    public static void tab(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print('\t');
        }
    }
    
    //sleep for a varying amount of seconds
    public static void sleep(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}