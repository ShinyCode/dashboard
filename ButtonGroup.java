
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import acm.graphics.*;

public class ButtonGroup extends MouseWidget
{
	public ButtonGroup(double width, double height, Color baseColor)
	{
		buttons = new HashMap<String, Button>();
		this.baseColor = baseColor;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	public boolean addButton(Button gb, double x, double y)
	{
		if(buttons.containsKey(gb.getInstr())) return false;
		buttons.put(gb.getInstr(), gb);
		add(gb, x, y);
		gb.addParentToPath(getName());
		return true;
	}
	
	public Button getButton(String instr)
	{
		if(!buttons.containsKey(instr.toUpperCase())) return null;
		return buttons.get(instr);
	}
	
	public String mousePressed(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		return cmd;
	}
	
	public String mouseReleased(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		return cmd;
	}
	
	private Map<String, Button> buttons;
	private Color baseColor;
	private GRect base;
}
