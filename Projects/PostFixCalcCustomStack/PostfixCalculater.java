import java.util.*;
import java.io.*;

/**
 * A postfix calculator using an custom stack
 * 
 * @author Matthew J. Stallone
 * @version 0.0.1
 */
public class PostfixCalculater
{
    public static void calculate(){
        Scanner scan = new Scanner(System.in);
        System.out.print("Postfix equation: ");
        String equation = scan.nextLine();
        MJSStack<Integer> stack = new MJSStack<Integer>();
        
        for(int i = 0; i < equation.length(); i++){
            if(equation.substring(i,i+1).equals("+")) stack.push(stack.pop() + stack.pop());
            else if(equation.substring(i,i+1).equals("-")) {
                int second = stack.pop();
                stack.push(stack.pop() - second);
            }
            else if(equation.substring(i,i+1).equals("*")) stack.push(stack.pop() * stack.pop());
            else if(equation.substring(i,i+1).equals("/")) {
                int second = stack.pop();
                if (second == 0) throw new ArithmeticException("Divide by zero");
                else stack.push(stack.pop() / second);
            }
            else stack.push(Integer.parseInt(equation.substring(i,i+1)));
        }
        
        System.out.println(stack.pop());
    }
}
