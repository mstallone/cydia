package Seven;

import java.awt.Color;
import info.gridworld.grid.Grid;
import info.gridworld.world.World;

public class ColorDisplay {
	private World<Color> world;

    public ColorDisplay(Grid<Color> gr){
        world = new World<Color>(gr);
    }
    
    public void display(){
        world.show();
    }
}