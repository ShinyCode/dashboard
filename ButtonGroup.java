
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
		buttons = new HashMap<String, GenericButton>();
		this.baseColor = baseColor;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base);
	}
	
	public boolean addButton(GenericButton gb, double x, double y)
	{
		if(buttons.containsKey(gb.getInstr())) return false;
		buttons.put(gb.getInstr(), gb);
		add(gb, x, y);
		return true;
	}
	
	public GenericButton getButton(String instr)
	{
		if(!buttons.containsKey(instr.toUpperCase())) return null;
		return buttons.get(instr);
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
	
	private Map<String, GenericButton> buttons;
	private Color baseColor;
	private GRect base;
	private GRect outline;
	private Color lineColor;
}
