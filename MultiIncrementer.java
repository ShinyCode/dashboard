import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
		incrementables = new ArrayList<Incrementable>();
	}
	
	public void mousePressed(MouseEvent e)
	{
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) ((MouseWidget) o).mousePressed(e);
		if(!incrementables.isEmpty())
		{
			if(o == incButton)
			{
				if(selButton.isOn()) index = (index + 1) % incrementables.size();
				else incrementables.get(index).increment();
			}
			else if(o == decButton)
			{
				if(selButton.isOn()) index = (index - 1 + incrementables.size()) % incrementables.size();
				else incrementables.get(index).decrement();
			}
		}
	}
	
	public void addIncrementable(Incrementable inc)
	{
		if(inc != null) incrementables.add(inc);
	}
	
	private int index = 0;
	private TouchButton incButton;
	private TouchButton decButton;
	private ToggleButton selButton;
	private List<Incrementable> incrementables;
}
