package Seven;

public interface DrawingGrid {
	void drawPixel(int row, int col);
	void erasePixel(int row, int col);
	void drawVerticalLine(int row, int col, int length);
	void drawHorizontalLine(int row, int col, int length);
	void fillArea(int row, int col);
	void eraseObject(int row, int col);
}
