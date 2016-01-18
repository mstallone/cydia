/**
 * Write a description of class MultiLineStore here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MultiLineStore
{
    public static void openTheStore() { 
        MJSQueue<Customer> lineOne = new MJSQueue<Customer>();
        MJSQueue<Customer> lineTwo = new MJSQueue<Customer>();
        Customer registerOne = null;
        Customer registerTwo = null;
        int oneTime = -1; int twoTime = -1;
        int totalCustomer = 0; int maxLengthA = 0; int maxLengthB = 0; int averageLengthA = 0; int averageLengthB = 0; int averageProccessTime = 0;
        for (int time = 0; time <= 36000; time++){
            if(Math.random() < 0.06) {
                Customer customer = new Customer(time);
                if(lineOne.size() <= lineTwo.size()) {
                    lineOne.add(customer);
                    System.out.println("Customer #" + customer.getCustomerNumber() + " entered line #1 at " + time);
                    printLine(lineOne.size(), lineTwo.size());
                }else{
                    lineTwo.add(customer);
                    System.out.println("Customer #" + customer.getCustomerNumber() + " entered line #2 at " + time);
                    printLine(lineOne.size(), lineTwo.size());
                }
                totalCustomer++;
            }
            if(registerOne == null && lineOne.size() > 0) {
                registerOne = lineOne.remove();
                oneTime = registerOne.getTimeCheckout() + time;
                System.out.println("Customer #" + registerOne.getCustomerNumber() + " is checking out at register #2 at " + time);
                printLine(lineOne.size(), lineTwo.size());
                averageProccessTime += (oneTime - registerOne.getTimeArrived());
            }if(registerTwo == null && lineTwo.size() > 0) {
                registerTwo = lineTwo.remove();
                twoTime = registerTwo.getTimeCheckout() + time;
                System.out.println("Customer #" + registerTwo.getCustomerNumber() + " is checking out at register #2 at " + time);
                printLine(lineOne.size(), lineTwo.size());
                averageProccessTime += (twoTime - registerTwo.getTimeArrived());
            }

            if(oneTime == time){
                System.out.println("Customer #" + registerOne.getCustomerNumber() + " is finished checkingout from register #1 at " + time + " and took " + (oneTime - registerOne.getTimeArrived()));
                registerOne = null;
                printLine(lineOne.size(), lineTwo.size());
            }
            if(twoTime == time){
                System.out.println("Customer #" + registerTwo.getCustomerNumber() + " is finished checkingout from register #2 at " + time + " and took " + (twoTime - registerTwo.getTimeArrived()));
                registerTwo = null;
                printLine(lineOne.size(), lineTwo.size());
            }
           
            if(maxLengthA < lineOne.size()) maxLengthA = lineOne.size();
            if(maxLengthB < lineTwo.size()) maxLengthB = lineTwo.size();
            averageLengthA += lineOne.size();
            averageLengthB += lineTwo.size();
        }
        Customer.resetTotalCustomers();
        System.out.println("We serverd " + totalCustomer + " customers");
        System.out.println("Line one has a maximum length of: " + maxLengthA);
        System.out.println("Line two has a maximum length of: " + maxLengthB);
        System.out.println("Line one has an average length of: " + (double)averageLengthA/36000);
        System.out.println("Line two has an average length of: " + (double)averageLengthB/36000);
        System.out.println("Average procces time is " + (double)averageProccessTime/totalCustomer);
    }

    private static void printLine(int one, int two){
        System.out.println("Line one has: " + one + ". Line two has: " + two);
    }
}
