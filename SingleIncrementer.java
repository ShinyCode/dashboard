import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;


public class SingleIncrementer extends ButtonGrid
{
	public SingleIncrementer(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 2, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
		addButton(decButton, 1, 0);
	}
	
	public String mousePressed(MouseEvent e)
	{
		/*
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);*/
		String cmd = super.mousePressed(e);
		if(cmd != null) cmd = getName() + "." + cmd;
		if(o == incButton)
		{
			if(inc != null) inc.increment();
		}
		else if(o == decButton)
		{
			if(inc != null) inc.decrement();
		}
		return cmd;
	}
	
	public void setIncrementable(Incrementable inc)
	{
		this.inc = inc;
	}
	
	private Incrementable inc;
	private TouchButton incButton;
	private TouchButton decButton;
}
