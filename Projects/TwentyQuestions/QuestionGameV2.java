import java.util.*;

public class QuestionGameV2 {
    private static TreeNode<String> top = new TreeNode<String>("Is it alive?", new TreeNode<String>("Is it a cube of butter?"), new TreeNode<String>("Is it a dog?"));
    private static Scanner scanner = new Scanner(System.in);
    private static boolean run;

    public static void main(String[] args){
        System.out.println("Welcome to the question game!"); System.out.println();
        TreeNode<String> temp = top;
        run = true;
        while(run){
            if(temp.getConnection(0) != null && temp.getConnection(1) != null){
                System.out.println(temp.getValue());
                String answer = scanner.next();
                System.out.println();
                if(answer.equals("yes")) temp = temp.getConnection(1);
                else if(answer.equals("no")) temp = temp.getConnection(0);    
            }else{
                System.out.println(temp.getValue());
                String answer = scanner.next();
                System.out.println();
                if(answer.equals("yes")){
                    System.out.println("I won! Would you like to play again?");
                    answer = scanner.next();
                    if(answer.equals("yes")){
                        temp = top;
                        answer = null;
                        System.out.println();
                    }else if(answer.equals("no"))  run = false;
                }else if(answer.equals("no")){
                    System.out.println("I give up! What was it?");
                    answer = scanner.next();
                    System.out.println();
                    System.out.println("What question would you ask for 'YES' for "+ answer + " and 'NO' for " + temp.getValue().substring(temp.getValue().lastIndexOf(" ")+1));
                    scanner.nextLine();
                    String question = scanner.nextLine();
                    String tempQuestion = temp.getValue();
                    temp.setValue(question);
                    temp.setConnection(1, new TreeNode<String>("Is it a " + answer));
                    temp.setConnection(0, new TreeNode<String>("Is it a " + tempQuestion.substring(tempQuestion.lastIndexOf(" ")+1)));
                    System.out.println(); System.out.println("Would you like to play again?");
                    answer = scanner.next();
                    if(answer.equals("yes")){
                        temp = top;
                        answer = null;
                        System.out.println();
                    }else if(answer.equals("no"))  run = false;
                }
            }
        }
    }
}
