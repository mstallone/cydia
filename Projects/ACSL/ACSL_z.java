package com.company;

import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws Exception {
        URL url = new URL("ACSL URL");
        Scanner s = new Scanner(url.openStream());
        s.nextLine(); s.nextLine(); s.nextLine();

        ArrayList<land> plots = new ArrayList<land>();
        while(s.hasNext()) {
            String[] input = s.nextLine().split(" \" ");
            plots.add(new land(
                    input[0],
                    Long.parseLong(input[1]),
                    Long.parseLong(input[2]) * Long.parseLong(input[3])));

        }

        //for(land l : plots) System.out.println(l.getName() + " " + l.getPrice() + " " + l.getArea());

        double ratio = 0.00;
        land plot1 = null, plot2 = null;
        for(land l1 : plots) {
            for(land l2 : plots) {
                if(!l1.getName().equals(l2.getName())){

                    if(l1.getArea()/l1.getPrice() + l2.getArea()/l2.getPrice() > ratio) {
                        plot1 = l1;
                        plot2 = l2;
                        ratio = l1.getArea()/l1.getPrice() + l2.getArea()/l2.getPrice();
                    }
                }
            }
        }

        System.out.println(plot1.getName() + "; "+ plot2.getName());
    }

    public class land {
        private String name;
        private long price, area;

        public land(String name, long price, long area){
            this.name = name;
            this.price = price;
            this.area = area;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public long getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }
    }
}
