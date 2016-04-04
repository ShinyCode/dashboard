import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;


public class SingleIncrementer extends ButtonGrid
{
	public SingleIncrementer(double width, double height, double spacing, Color baseColor)
	{
		super(width, height, 2, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, Color.GREEN, "INCD");
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, Color.RED, "DECD");
		addButton(decButton, 1, 0);
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
	
	public void setIncrementable(Incrementable inc)
	{
		this.inc = inc;
	}
	
	private Incrementable inc;
	private TouchButton incButton;
	private TouchButton decButton;
}
