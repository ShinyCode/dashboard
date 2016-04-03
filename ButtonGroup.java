
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;

import acm.graphics.*;

public class ButtonGroup extends MouseWidget
{
	public ButtonGroup(double width, double height)
	{
		add(new TouchButton(100, 50, Color.RED, "RCL"), 10, 10);
		add(new ToggleButton(100, 50, Color.GREEN, "SEND"), 10, 70);
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
}
