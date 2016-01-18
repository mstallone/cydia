package Seven;

public class TextGrid implements DrawingGrid {
	private char[][] canvas;
	
	public TextGrid(int row, int col) {
		canvas = new char[row][col];
		for(int r = 0; r < canvas.length; r++) for(int c = 0; c < canvas[0].length; c++) canvas[r][c] = 'O'; 
		printGrid();
	}
	
	private boolean pixelManager(int row, int col, int type){
		row--; col--;
		if(row >= 0 && col >= 0 && row < canvas.length && col < canvas[0].length){
			if(type == 0) canvas[row][col] = 'O';
			else canvas[row][col] = 'X';
			return true;
		}else return false;
	}
	
	public void printGrid(){
		for(int r = 0; r < canvas.length; r++) {
			for(int c = 0; c < canvas[0].length; c++) System.out.print(canvas[r][c] + " ");
			System.out.println();
		}
		for(int c = 0; c < canvas[0].length; c++) System.out.print("- ");
		System.out.println();
	}

	@Override
	public void drawPixel(int row, int col) {
		pixelManager(row, col, 1);
	}

	@Override
	public void erasePixel(int row, int col) {
		pixelManager(row, col, 0);
	}
	
	@Override
	public void drawVerticalLine(int row, int col, int length) {
		if(length < 0){
			length = -length;
			for(int i = 0; i < length; i++){
				pixelManager(row, col, 1);
				row--;
			}
		}else{
			for(int i = 0; i < length; i++){
				pixelManager(row, col, 1);
				row++;
			}
		}
	}

	@Override
	public void drawHorizontalLine(int row, int col, int length) {
		if(length < 0){
			length = -length;
			for(int i = 0; i < length; i++){
				pixelManager(row, col, 1);
				col--;
			}
		}else{
			for(int i = 0; i < length; i++){
				pixelManager(row, col, 1);
				col++;
			}
		}
	}

	@Override
	public void eraseObject(int row, int col) {
		if(row > 0 && col > 0 && row <= canvas.length && col <= canvas[0].length && canvas[row - 1][col - 1] == 'X'){
			pixelManager(row, col, 0);
			eraseObject(row + 1, col); 
			eraseObject(row - 1, col); 
			eraseObject(row, col + 1); 
			eraseObject(row, col - 1);
		}
	}
	
	@Override
	public void fillArea(int row, int col) {
		if(row > 0 && col > 0 && row <= canvas.length && col <= canvas[0].length && canvas[row - 1][col - 1] == 'O'){
			pixelManager(row, col, 1);
			fillArea(row + 1, col); 
			fillArea(row - 1, col); 
			fillArea(row, col + 1); 
			fillArea(row, col - 1);
		}
	}
}