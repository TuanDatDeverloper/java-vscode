import java.util.*;
public class Exercise4 {
    public static void main(String[] args) {
        Date d1 = new Date();
        System.out.println("Current date is " + d1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d1);
        System.out.println("Current hour is " + calendar.get(Calendar.HOUR_OF_DAY));
        
        //Put your code for other methods from here 
        //.......
    }
}
