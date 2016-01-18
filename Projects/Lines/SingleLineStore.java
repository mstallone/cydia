
/**
 * Write a description of class SingleLineStore here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SingleLineStore
{
    public static void openTheStore() {
        MJSQueue<Customer> line = new MJSQueue<Customer>();
        Customer registerOne = null;
        Customer registerTwo = null;
        int oneTime = -1; int twoTime = -1;
        int totalCustomer = 0; int maxLength = 0; int averageLength = 0; int averageProccessTime = 0;
        for (int time = 0; time <= 36000; time++){
            if(Math.random() < 0.055) {
                Customer customer = new Customer(time);
                line.add(customer);
                System.out.println("Customer #" + customer.getCustomerNumber() + " entered line at " + time);
                totalCustomer++;
                printLine(line.size());
            }
            if(registerOne == null && line.size() > 0) {
                registerOne = line.remove();
                oneTime = registerOne.getTimeCheckout() + time;
                System.out.println("Customer #" + registerOne.getCustomerNumber() + " is checking out at register #1 at " + time);
                averageProccessTime += (oneTime - registerOne.getTimeArrived());
                printLine(line.size());
            }
            if(registerTwo == null && line.size() > 0) {
                registerTwo = line.remove();
                twoTime = registerTwo.getTimeCheckout() + time;
                System.out.println("Customer #" + registerTwo.getCustomerNumber() + " is checking out at register #2 at " + time);
                averageProccessTime += (twoTime - registerTwo.getTimeArrived());
                printLine(line.size());
            }
            if(oneTime == time){
                System.out.println("Customer #" + registerOne.getCustomerNumber() + " is finished checkingout from register #1 at " + time + " and took " + (oneTime - registerOne.getTimeArrived()));
                registerOne = null;
                printLine(line.size());
            }
            if(twoTime == time){
                System.out.println("Customer #" + registerTwo.getCustomerNumber() + " is finished checkingout from register #2 at " + time + " and took " + (twoTime - registerTwo.getTimeArrived()));
                registerTwo = null;
                printLine(line.size());
            }
            if(maxLength < line.size()) maxLength = line.size();
            averageLength += line.size();
        }
        Customer.resetTotalCustomers();
        System.out.println("We serverd " + totalCustomer + " customers");
        System.out.println("Line has a maximum length of: " + maxLength);
        System.out.println("Line has an average length of: " + (double)averageLength/36000);
        System.out.println("Average procces time is " + (double)averageProccessTime/totalCustomer);
    }
    
    private static void printLine(int one){
        System.out.println("Line has: " + one);
    }
}



