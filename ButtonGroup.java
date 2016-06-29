
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ButtonGrid.Builder;
import acm.graphics.*;

public abstract class ButtonGroup extends MouseWidget
{
	private Map<String, Button> buttons;
	private GRect base;
	
	public static class Builder<T extends Builder>
	{
		private final double width;
		private final double height;
		
		private Color baseColor = Color.BLACK; // Default color
		
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public Builder withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return this;
		}
		
		public ButtonGroup build()
		{
			return new ButtonGroup(width, height, baseColor);
		}
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
