import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;


public class Incrementer extends ButtonGrid
{
	public Incrementer(double width, double height, double spacing, Color baseColor)
	{
		super(width, height, 2, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, baseColor, "INC");
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, baseColor, "DEC");
		addButton(decButton, 1, 0);
		System.out.println("Added stuff");
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mousePressed(e);
		if(o == incButton)
		{
			if(inc != null) inc.increment();
		}
		else if(o == decButton)
		{
			if(inc != null) inc.decrement();
		}
	}
	
	private Incrementable inc;
	private TouchButton incButton;
	private TouchButton decButton;
}
