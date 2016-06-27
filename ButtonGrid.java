import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GRect;


public class ButtonGrid extends MouseWidget
{
	private List<Button> buttons;
	private GRect base;
	private int numRows;
	private int numCols;
	private double buttonWidth;
	private double buttonHeight;
	private double spacing;
	
	public static class ButtonGridBuilder
	{
		private final double width;
		private final double height;
		
		private int numRows = 1;
		private int numCols = 1;
		private double spacing = 0;
		private Color baseColor = Color.BLACK;
		
		public ButtonGridBuilder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public ButtonGridBuilder withRowsCols(int numRows, int numCols)
		{
			this.numRows = numRows;
			this.numCols = numCols;
			return this;
		}
		
		public ButtonGridBuilder withSpacing(double spacing)
		{
			this.spacing = spacing;
			return this;
		}
		
		public ButtonGridBuilder withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return this;
		}
		
		public ButtonGrid build()
		{
			return new ButtonGrid(width, height, numRows, numCols, spacing, baseColor);
		}				
	}
	
	private ButtonGrid(double width, double height, int numRows, int numCols, double spacing, Color baseColor)
	{
		buttons = new ArrayList<Button>(numRows * numCols);
		this.numRows = numRows;
		this.numCols = numCols;
		this.spacing = spacing;
		buttonWidth = (width - (numCols + 1) * spacing) / numCols;
		buttonHeight = (height - (numRows + 1) * spacing) / numRows;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
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
		if(row < 0 || col < 0 || row >= numRows || col >= numCols) return null;
		return buttons.get(getIndex(row, col));
	}
	
	private int getIndex(int row, int col)
	{
		return row * numRows + col;
	}
}
