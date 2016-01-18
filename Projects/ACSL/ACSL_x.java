package com.matthewstallone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 5; i++) {
            String rawInput = bf.readLine();

            Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(rawInput);

            ArrayList<String> input = null;
            String mod = rawInput.split(" ")[rawInput.split(" ").length - 1];

            while(m.find()) {
                if(m.group(1).charAt(0) == '(') {
                    input = new ArrayList<String>();
                    input.add(m.group(1) + ")");
                    while(m.find()) input.add("(" + m.group(1) + ")");
                }else input = new ArrayList<String>(Arrays.asList(m.group(1).split(" ")));
            }

            switch(i){
                case 0: {
                    String output = "\'(";
                    for (int j = input.size() - 1; j > -1; j--) output += (input.get(j) + " ");
                    System.out.println(output.substring(0, output.length() - 1) + ")");
                    } break;
                case 1: {
                    String output = "\'(";
                    for (int j = 0; j < input.size(); j++) {
                        int staticIndex = j;
                        int count = 1;
                        while((j + 1) < input.size() && input.get(staticIndex).equals(input.get(j + 1))){
                            j++;
                            count++;
                        }
                        output += "(" + count + " " + input.get(staticIndex) + ") ";
                    }
                    System.out.println(output.substring(0, output.length() - 1) + ")");
                    } break;
                case 2: {
                    String output = "\'(";
                    for (int j = 0; j < input.size(); j++) {
                        int staticIndex = j;
                        int count = 1;
                        while((j + 1) < input.size() && input.get(staticIndex).equals(input.get(j + 1))){
                            j++;
                            count++;
                        }
                        if(count == 1) output += input.get(staticIndex) + " ";
                        else output += "(" + count + " " + input.get(staticIndex) + ") ";
                    }
                    System.out.println(output.substring(0, output.length() - 1) + ")");
                    } break;
                case 3: {
                    String output = "\'(";
                    int index = Integer.parseInt(mod.substring(0, mod.length() - 1)) - 1;
                    ArrayList<String> outputList = new ArrayList<String>(input);
                    for (int j = index; j < outputList.size(); j += index) outputList.remove(j);
                    for (String st : outputList) output += st + " ";
                    System.out.println(output + (index + 1) + ")");
                    }break;
                case 4: {
                    String output = "\'(";
                    int index = Integer.parseInt(mod.substring(0, mod.length() - 1));
                    for (int j = 0; j < index; j++) output += input.get(j) + " ";
                    output = output.substring(0, output.length() - 1) + ") \'(";
                    for (int j = index; j < input.size(); j++) output += input.get(j) + " ";
                    System.out.println(output + index + ")");
                    }break;
                default:
                    break;
            }
        }
    }
}
