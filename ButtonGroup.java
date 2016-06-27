
import java.awt.Color;
import java.util.HashMap;

import acm.graphics.*;

public class ButtonGroup extends MouseWidget
{
	private HashMap<String, Button> buttons;
	private GRect base;
	
	public static class Builder
	{
		
	}
	
	private ButtonGroup(double width, double height, Color baseColor)
	{
		buttons = new HashMap<String, Button>();
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
		return true;
	}
	
	public Button getButton(String instr)
	{
		if(!buttons.containsKey(instr.toUpperCase())) return null;
		return buttons.get(instr);
	}
	
	
}
