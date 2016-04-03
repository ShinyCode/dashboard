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
		this.baseColor = baseColor;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
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
}
