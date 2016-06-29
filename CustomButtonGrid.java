import java.awt.Color;
import java.util.ArrayList;

import acm.graphics.GRect;


public final class CustomButtonGrid extends ButtonGrid
{
	protected CustomButtonGrid(double width, double height, int numRows, int numCols, double spacing, Color baseColor)
	{
		super(width, height, numRows, numCols, spacing, baseColor);
	}
	
	public boolean addButton(Button gb, int row, int col)
	{
		return addButton(gb, row, col, 1, 1);
	}
	
	public boolean addButton(Button gb, int row, int col, int rowSpan, int colSpan)
	{
		if(row < 0 || col < 0 || row >= numRows || col >= numCols) return false; //Invalid row, col dimension
		if(rowSpan <= 0 || colSpan <= 0 || rowSpan > numRows - row || colSpan > numCols - col) return false; //Invalid row/column Span
		gb.resize(colSpan * buttonWidth + (colSpan - 1) * spacing, rowSpan * buttonHeight + (rowSpan - 1) * spacing);
		add(gb, col * buttonWidth + (col + 1) * spacing, row * buttonHeight + (row + 1) * spacing);
		return true;
	}
	
	public Button getButton(int row, int col)
	{
		return super.getButton(row, col);
	}
}
