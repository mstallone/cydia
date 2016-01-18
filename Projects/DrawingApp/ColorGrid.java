package Seven;

import java.awt.Color;

import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class ColorGrid implements DrawingGrid {
	private Grid<Color> grid;
	private ColorDisplay screen;
	
	public ColorGrid(int rows, int cols) {
		grid = new BoundedGrid<Color>(rows, cols); 
		screen = new ColorDisplay(grid); 
		screen.display();
	}

	@Override
	public void drawPixel(int row, int col) {
		if(row > 0 && col > 0 && row <= grid.getNumRows() && col <= grid.getNumCols()){
			grid.put(new Location(row - 1, col - 1), Color.RED);
			screen.display();	
		}
	}

	@Override
	public void erasePixel(int row, int col) {
		if(row > 0 && col > 0 && row <= grid.getNumRows() && col <= grid.getNumCols()){
			grid.remove(new Location(row - 1, col - 1));
			screen.display();	
		}
	}

	@Override
	public void drawVerticalLine(int row, int col, int length) {
		if(length < 0){
			length = -length;
			for(int i = 0; i < length; i++){
				drawPixel(row, col);
				row--;
			}
		}else{
			for(int i = 0; i < length; i++){
				drawPixel(row, col);
				row++;
			}
		}
	}

	@Override
	public void drawHorizontalLine(int row, int col, int length) {
		if(length < 0){
			length = -length;
			for(int i = 0; i < length; i++){
				drawPixel(row, col);
				col--;
			}
		}else{
			for(int i = 0; i < length; i++){
				drawPixel(row, col);
				col++;
			}
		}
	}

	@Override
	public void fillArea(int row, int col) {
		if(row > 0 && col > 0 && row <= grid.getNumRows() && col <=  grid.getNumCols() && grid.get(new Location(row - 1, col - 1)) == null){
			drawPixel(row, col);
			fillArea(row + 1, col); 
			fillArea(row - 1, col); 
			fillArea(row, col + 1); 
			fillArea(row, col - 1);
		}
	}

	@Override
	public void eraseObject(int row, int col) {
		if(row > 0 && col > 0 && row <= grid.getNumRows() && col <=  grid.getNumCols() && grid.get(new Location(row - 1, col -1)) != null){
			erasePixel(row, col);
			eraseObject(row + 1, col); 
			eraseObject(row - 1, col); 
			eraseObject(row, col + 1); 
			eraseObject(row, col - 1);
		}
	}
}
