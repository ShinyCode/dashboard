
import java.awt.Color;
import java.util.List;

import acm.graphics.*;

public class ButtonGroup extends GCompound
{
	public ButtonGroup(double width, double height)
	{
		add(new TouchButton(100, 50, Color.RED, "RCL"), 10, 10);
		add(new ToggleButton(100, 50, Color.ORANGE, "SEND"), 10, 70);
	}
	
	public void mousePressed(double x, double y)
	{
		GObject o = getElementAt(x, y);
		if(o instanceof TouchButton)
		{
			((TouchButton) o).pressButton();
		}
		else if(o instanceof ToggleButton)
		{
			((ToggleButton) o).toggleButton();
		}
	}
	
	public void mouseReleased(double x, double y)
	{
		GObject o = getElementAt(x, y);
		if(o instanceof TouchButton)
		{
			((TouchButton) o).releaseButton();
		}
	}
	
	private List<GenericButton> buttons;
}
