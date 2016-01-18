package Seven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GridDriver {
	public static void main(String[] args) throws IOException{
		DrawingGrid g = null;
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Would you like a color (c) grid or a text (t) grid? 'c' or 't'");
		String gridType = bf.readLine();
		if(gridType.equals("c")) g = new ColorGrid(10, 10);
		else if(gridType.equals("t")) g = new TextGrid(10, 10);
		else System.out.println("Error");
		while(true){
			System.out.println("What would you like to do?");
			System.out.println("Draw Pixel: 'dp10,10'");
			System.out.println("Erase Pixel: 'ep10,10'");
			System.out.println("Draw Object: 'do10,10'");
			System.out.println("Erase Object: 'eo10,10'");
			System.out.println("Draw V-Line: 'vl10,10,10'");
			System.out.println("Draw H-Line: 'hl10,10,10'");
			String command = bf.readLine();
			if(command.contains("dp")){
				String[] position = command.substring(2).split(",");
				g.drawPixel(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			}else if(command.contains("ep")){
				String[] position = command.substring(2).split(",");
				g.erasePixel(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			}else if(command.contains("do")){
				String[] position = command.substring(2).split(",");
				g.fillArea(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			}else if(command.contains("eo")){
				String[] position = command.substring(2).split(",");
				g.eraseObject(Integer.parseInt(position[0]), Integer.parseInt(position[1]));
			}else if(command.contains("vl")){
				String[] position = command.substring(2).split(",");
				g.drawVerticalLine(Integer.parseInt(position[0]), Integer.parseInt(position[1]), Integer.parseInt(position[2]));
			}else if(command.contains("hl")){
				String[] position = command.substring(2).split(",");
				g.drawHorizontalLine(Integer.parseInt(position[0]), Integer.parseInt(position[1]), Integer.parseInt(position[2]));
			}else System.out.println("Unknown Command");
		}
	}
}
