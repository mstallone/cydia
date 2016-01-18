import java.util.*;
/**
 * Write a description of class Customer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Customer
{
    private static int totalCustomers = 0;
    private int customerNumber, timeArrived, timeCheckout;

    public Customer(int inputTimeArrived){
        totalCustomers++;
        customerNumber = totalCustomers;
        timeArrived = inputTimeArrived;
        Random rnd = new Random();
        timeCheckout = Math.abs((int)((rnd.nextGaussian() * 11) + 30));
        if(timeCheckout < 5) timeCheckout = 5;
    }

    public int getTotalCustomers() {
        return totalCustomers;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public int getTimeArrived() {
        return timeArrived;
    }

    public int getTimeCheckout() {
        return timeCheckout;
    }

    public static void resetTotalCustomers(){
        totalCustomers = 0;
    }
}
