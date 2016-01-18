package com.matthewstallone;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        Map<Integer, Integer> length = new HashMap<Integer, Integer>();
        length.put((int)'B', 450); length.put((int)'C', 140); length.put((int)'D', 120);
        length.put((int)'E', 320); length.put((int)'F', 250); length.put((int)'G', 80);

        for(int i = 0; i < 5; i++) {
            String[] input = new BufferedReader(new InputStreamReader(System.in)).readLine().split(",");

            int currentPath = (int)input[0].charAt(0), distance = 0;
            double time = 0.00, gas = 0.00;

            /* DISTANCE */

            while(currentPath < (int)input[1].charAt(0)) {
                currentPath++;
                distance += length.get(currentPath);
            }

            /* DURATION */

            switch((int)input[3].charAt(0)){
                case ((int)'I'):
                    time = (double)distance/65;
                    break;
                case ((int)'H'):
                    time = (double)distance/60;
                    break;
                case ((int)'M'):
                    time = (double)distance/55;
                    break;
                case ((int)'S'):
                    time = (double)distance/45;
                    break;
                default:
                    break;
            }

            String duration;
            if(time < 10) duration = "0" + (int)time + ":";
            else duration = (int)time + ":";

            if((time - (int)time) < (1/60)) duration += "0" + Math.round((time - (int)time)*60);
            else duration += Math.round((time - (int)time)*60);

            /* PRICE */

            switch((int)input[2].charAt(0)){
                case ((int)'C'):
                    gas = (double)distance/28;
                    break;
                case ((int)'M'):
                    gas = (double)distance/25;
                    break;
                case ((int)'F'):
                    gas = (double)distance/22;
                    break;
                case ((int)'V'):
                    gas = (double)distance/20;
                    break;
                default:
                    break;
            }

            System.out.println(distance + "," + duration + ",$" + (Math.round(gas*Double.parseDouble(input[4])*100.00)/100.00));
        }
    }
}