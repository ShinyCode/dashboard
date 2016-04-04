import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;

import acm.graphics.GObject;


public class MultiIncrementer extends ButtonGrid
{
	public MultiIncrementer(double width, double height, double spacing, Color baseColor)
	{
		super(width, height, 3, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, Color.GREEN, "INCD");
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, Color.RED, "DECD");
		addButton(decButton, 1, 0);
		selButton = new ToggleButton(width, height, Color.ORANGE, "SEL");
		addButton(selButton, 2, 0);
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
		else if(o == selButton)
		{
			
		}
	}
	
	public void setIncrementable(Incrementable inc)
	{
		this.inc = inc;
	}
	
	private Incrementable inc;
	private TouchButton incButton;
	private TouchButton decButton;
	private ToggleButton selButton;
	private List<Incrementable> incrementables;
}
