import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import acm.graphics.GObject;
import acm.graphics.GRect;


public class ButtonGrid extends MouseWidget
{
	public ButtonGrid(double width, double height, int numRows, int numCols, Color baseColor)
	{
		buttons = new ArrayList<GenericButton>(numRows * numCols);
		this.numRows = numRows;
		this.numCols = numCols;
		this.baseColor = baseColor;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	public boolean addButton(GenericButton gb, int row, int col)
	{
		return addButton(gb, row, col, 1, 1);
	}
	
	public boolean addButton(GenericButton gb, int row, int col, int rowSpan, int colSpan)
	{
		if(row < 0 || col < 0 || row >= numRows || col >= numCols) return false; //Invalid row, col dimension
		if(rowSpan <= 0 || colSpan <= 0 || rowSpan > numRows - row || colSpan > numCols - col) return false; //Invalid row/column Span
		
	}
	
	public GenericButton getButton(int row, int col)
	{
		if(row < 0 || col < 0 || row >= numRows || col >= numCols) return null;
		return buttons.get(getIndex(row, col));
	}
	
	private int getIndex(int row, int col)
	{
		return row * numRows + col;
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mouseReleased(e);
	}
	
	private List<GenericButton> buttons;
	private GRect base;
	private Color baseColor;
	private int numRows;
	private int numCols;
}
