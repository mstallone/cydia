import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import java.util.*;

public class CalculatorGUI
{
    private static boolean postfix;   
    private static JButton buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix,
    buttonSeven, buttonEight, buttonNine, buttonZero, buttonNew, buttonEqual, buttonPlus,
    buttonTimes, buttonMinus, buttonDivide, buttonClear, buttonOpen, buttonClose, buttonPost, buttonIn;
    private static String display = "", number = "";
    private static MJSStack<Integer> postfixStack, prefixNumberStack;
    private static MJSStack<String> prefixOperatorStack;
    private static JLabel labelDisplay, labelType;

    public static void main(){
        JOptionPane.showMessageDialog(null, " Welcome to Matthew Stallone's Calculator. \n \n This calculator is postfix and infix. \n \n Postfix: \n Use the '|' to seperate the numbers on the calculator \n \n Infix: \n Use '(' ')' to seperate the different operations. \n \n The mode of the calculator is above the clear button.");

        postfix = true;

        JFrame frame = new JFrame("Matthew Stallone's Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonOne = new JButton("1"); buttonTwo = new JButton("2"); buttonThree = new JButton("3");
        buttonFour = new JButton("4"); buttonFive = new JButton("5"); buttonSix = new JButton("6");
        buttonSeven = new JButton("7"); buttonEight = new JButton("8"); buttonNine = new JButton("9");
        buttonZero = new JButton("0"); buttonNew = new JButton("|"); buttonEqual = new JButton("=");
        buttonPlus = new JButton("+"); buttonTimes = new JButton("*"); buttonMinus = new JButton("-");
        buttonDivide = new JButton("÷"); buttonClear = new JButton("Clear"); buttonOpen = new JButton("(");
        buttonClose = new JButton(")"); buttonPost = new JButton("Postfix"); buttonIn = new JButton("Infix");

        labelDisplay = new JLabel("0"); 
        if(postfix) labelType = new JLabel("Postfix Notation");
        else labelType = new JLabel("Infix Notation");

        ActionListener buttonAction = new ActionListener(){
                public void actionPerformed(ActionEvent actionEvent) {
                    calculate((JButton)actionEvent.getSource());
                }
            };

        buttonOne.addActionListener(buttonAction); buttonOne.setBounds(10,100,100,50); buttonTwo.addActionListener(buttonAction); buttonTwo.setBounds(115,100,100,50);
        buttonThree.addActionListener(buttonAction); buttonThree.setBounds(220,100,100,50); buttonFour.addActionListener(buttonAction); buttonFour.setBounds(10,155,100,50); 
        buttonFive.addActionListener(buttonAction); buttonFive.setBounds(115,155,100,50); buttonSix.addActionListener(buttonAction); buttonSix.setBounds(220,155,100,50); 
        buttonSeven.addActionListener(buttonAction); buttonSeven.setBounds(10,210,100,50); buttonEight.addActionListener(buttonAction); buttonEight.setBounds(115,210,100,50); 
        buttonNine.addActionListener(buttonAction); buttonNine.setBounds(220,210,100,50); buttonZero.addActionListener(buttonAction); buttonZero.setBounds(10,265,100,50); 
        buttonNew.addActionListener(buttonAction); buttonNew.setBounds(115,265,100,50); buttonEqual.addActionListener(buttonAction); buttonEqual.setBounds(220,265,100,50); 
        buttonPlus.addActionListener(buttonAction); buttonPlus.setBounds(325,100,50,50); buttonTimes.addActionListener(buttonAction); buttonTimes.setBounds(325,155,50,50); 
        buttonMinus.addActionListener(buttonAction); buttonMinus.setBounds(325,210,50,50); buttonDivide.addActionListener(buttonAction); buttonDivide.setBounds(325,265,50,50);
        buttonClear.addActionListener(buttonAction); buttonClear.setBounds(10,25,50,50); buttonOpen.addActionListener(buttonAction); buttonOpen.setBounds(115,265,45,50); 
        buttonClose.addActionListener(buttonAction); buttonClose.setBounds(170,265,45,50); buttonPost.addActionListener(buttonAction); buttonPost.setBounds(275,5,65,15); 
        buttonIn.addActionListener(buttonAction); buttonIn.setBounds(342,5,45,15);

        if(postfix){
            buttonOpen.setVisible(false);
            buttonClose.setVisible(false);
            buttonNew.setVisible(true);
            buttonPost.setEnabled(false);
        }else{
            buttonOpen.setVisible(true);
            buttonClose.setVisible(true);
            buttonNew.setVisible(false);
            buttonIn.setEnabled(false);
        }
        labelDisplay.setBounds(10,10,365,75);
        labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 49));
        labelDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
        labelType.setBounds(12,-15,100,50);
        labelType.setFont(new Font("DIALOG", Font.PLAIN, 10));

        postfixStack = new MJSStack<Integer>();prefixNumberStack = new MJSStack<Integer>();prefixOperatorStack = new MJSStack<String>();

        frame.getContentPane().setLayout(null);frame.getContentPane().add(buttonOne);frame.getContentPane().add(buttonTwo);frame.getContentPane().add(buttonThree);
        frame.getContentPane().add(buttonFour);frame.getContentPane().add(buttonFive);frame.getContentPane().add(buttonSix);frame.getContentPane().add(buttonSeven);
        frame.getContentPane().add(buttonEight);frame.getContentPane().add(buttonNine);frame.getContentPane().add(buttonZero);frame.getContentPane().add(buttonNew);
        frame.getContentPane().add(buttonEqual);frame.getContentPane().add(buttonPlus);frame.getContentPane().add(buttonMinus);frame.getContentPane().add(buttonDivide);
        frame.getContentPane().add(buttonTimes);frame.getContentPane().add(buttonClear);frame.getContentPane().add(buttonOpen);frame.getContentPane().add(buttonClose);
        frame.getContentPane().add(buttonIn);frame.getContentPane().add(buttonPost);frame.getContentPane().add(labelDisplay);frame.getContentPane().add(labelType);
        frame.setSize(390, 350);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private static void calculate(JButton b){
        if(b.getText().equals("Postfix")){
            buttonOpen.setVisible(false);
            buttonClose.setVisible(false);
            buttonNew.setVisible(true);
            buttonIn.setEnabled(true);
            buttonPost.setEnabled(false);
            labelType.setText("Postfix Notation");
            labelDisplay.setText("0");
            display = ""; number = "";
            postfix = true;
            while(!postfixStack.empty()) postfixStack.pop();
            while(!prefixNumberStack.empty()) prefixNumberStack.pop();
            while(!prefixOperatorStack.empty()) prefixOperatorStack.pop();
        }else if(b.getText().equals("Infix")){
            buttonOpen.setVisible(true);
            buttonClose.setVisible(true);
            buttonNew.setVisible(false);
            buttonIn.setEnabled(false);
            buttonPost.setEnabled(true);
            labelType.setText("Infix Notation");
            labelDisplay.setText("0");
            display = ""; number = "";
            postfix = false;
            while(!postfixStack.empty()) postfixStack.pop();
            while(!prefixNumberStack.empty()) prefixNumberStack.pop();
            while(!prefixOperatorStack.empty()) prefixOperatorStack.pop();
        }if(postfix){
            if(b.getText().equals("|")){                                      
                postfixStack.push(Integer.parseInt(number));
                display = display + number + " ";
                number = "";
                labelDisplay.setText(display);
                buttonNew.setEnabled(false);
            }else if(b.getText().equals("+")){ 
                if(!number.equals(""))postfixStack.push(Integer.parseInt(number));
                if(postfixStack.size() < 2) JOptionPane.showMessageDialog(null, "You entered the wrong postfix syntax!");
                else{
                    display = display + number + "+";
                    number = "";
                    postfixStack.push(postfixStack.pop() + postfixStack.pop());
                    labelDisplay.setText(display);
                }
                buttonNew.setEnabled(false);
            }else if(b.getText().equals("-")){                                      
                if(!number.equals(""))postfixStack.push(Integer.parseInt(number));
                if(postfixStack.size() < 2) JOptionPane.showMessageDialog(null, "You entered the wrong postfix syntax!");
                else{
                    display = display + number + "-";
                    number = "";
                    int second = postfixStack.pop();
                    postfixStack.push(postfixStack.pop() - second);
                    labelDisplay.setText(display);
                }
                buttonNew.setEnabled(false);
            }else if(b.getText().equals("*")){                                      
                if(!number.equals(""))postfixStack.push(Integer.parseInt(number));
                if(postfixStack.size() < 2) JOptionPane.showMessageDialog(null, "You entered the wrong postfix syntax!");
                else{
                    display = display + number + "*";
                    number = "";
                    postfixStack.push(postfixStack.pop() * postfixStack.pop());
                    labelDisplay.setText(display);
                }
                buttonNew.setEnabled(false);
            }else if(b.getText().equals("÷")){                                      
                if(!number.equals(""))postfixStack.push(Integer.parseInt(number));
                if(postfixStack.size() < 2) JOptionPane.showMessageDialog(null, "You entered the wrong postfix syntax!");
                else{
                    int second = postfixStack.pop();
                    if (second == 0){
                        while(!postfixStack.empty()) postfixStack.pop();
                        JOptionPane.showMessageDialog(null, "You divided by zero!");
                        number = "";
                        display = "";
                        labelDisplay.setText("0");
                    }
                    else{
                        display = display + number + "÷";
                        number = "";
                        postfixStack.push(postfixStack.pop() / second);
                        labelDisplay.setText(display);
                    }
                }
                buttonNew.setEnabled(false);
            }else if(b.getText().equals("Clear")){                                      
                while(!postfixStack.empty()) postfixStack.pop();
                number = "";
                display = "";
                labelDisplay.setText("0");
                buttonNew.setEnabled(false);
            }else if(b.getText().equals("=")){
                if(!number.equals("")){
                    JOptionPane.showMessageDialog(null, "You entered the wrong postfix syntax!");
                    while(!postfixStack.empty()) postfixStack.pop();
                    number = "";
                    display = "";
                    labelDisplay.setText("0");
                }if(postfixStack.size() == 0){
                    number = "";
                    display = "";
                    labelDisplay.setText("0");
                }
                else labelDisplay.setText(postfixStack.pop().toString());
                buttonNew.setEnabled(false);
            }else if (!b.getText().equals("Postfix") && !b.getText().equals("Infix")){                     
                number = number + b.getText();
                labelDisplay.setText(display + number);
                buttonNew.setEnabled(true);
            }
        }else{
            if(b.getText().equals("Clear")){                                      
                while(!prefixNumberStack.empty()) prefixNumberStack.pop();
                while(!prefixOperatorStack.empty()) prefixOperatorStack.pop();
                number = "";
                display = "";
                labelDisplay.setText("0");
            }else if(b.getText().equals("(")){
                if(!number.equals("")) prefixNumberStack.push(Integer.parseInt(number));
                display = display + number + "(";
                labelDisplay.setText(display);
                number = "";
            }else if(b.getText().equals(")")){
                if(!number.equals("")) prefixNumberStack.push(Integer.parseInt(number));
                int second = prefixNumberStack.pop();
                int first = prefixNumberStack.pop();
                String op = prefixOperatorStack.pop();
                if(op.equals("÷")) prefixNumberStack.push(second/first);
                else if(op.equals("*")) prefixNumberStack.push(second*first);
                else if(op.equals("+")) prefixNumberStack.push(second+first);
                else if(op.equals("-")) prefixNumberStack.push(second-first);
                display = display + number + ")";
                number = "";
                labelDisplay.setText(display);
            }else if(b.getText().equals("+")){
                if(!number.equals("")) prefixNumberStack.push(Integer.parseInt(number));
                prefixOperatorStack.push(b.getText());
                display = display + number + "+";
                labelDisplay.setText(display);
                number = "";
            }else if(b.getText().equals("-")){
                if(!number.equals("")) prefixNumberStack.push(Integer.parseInt(number));
                prefixOperatorStack.push(b.getText());
                display = display + number + "-";
                labelDisplay.setText(display);
                number = "";
            }else if(b.getText().equals("÷")){
                if(!number.equals("")) prefixNumberStack.push(Integer.parseInt(number));
                prefixOperatorStack.push(b.getText());
                display = display + number + "/";
                labelDisplay.setText(display);
                number = "";
            }else if(b.getText().equals("*")){
                prefixOperatorStack.push(b.getText());
                display = display + number + "*";
                labelDisplay.setText(display);
                number = "";
            }else if(b.getText().equals("=")){
                display = "";
                number = "";
                labelDisplay.setText(prefixNumberStack.pop().toString());
                
            }else if (!b.getText().equals("Postfix") && !b.getText().equals("Infix")){                     
                number = number + b.getText();
                labelDisplay.setText(display + number);
            }
        }
        screenAdjust();
    }

    private static void screenAdjust(){
        if(labelDisplay.getText().length() < 10) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 49));
        else if(labelDisplay.getText().length() == 10) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 43));
        else if(labelDisplay.getText().length() == 12) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 37));
        else if(labelDisplay.getText().length() == 14) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 31));
        else if(labelDisplay.getText().length() == 16) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 27));
        else if(labelDisplay.getText().length() == 19) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 23));
        else if(labelDisplay.getText().length() == 21) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 20));
        else if(labelDisplay.getText().length() == 25) labelDisplay.setFont(new Font("DIALOG", Font.PLAIN, 17));
    }
}
