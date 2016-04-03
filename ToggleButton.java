
import acm.graphics.*;

import java.awt.Color;
import java.awt.event.*;

public class ToggleButton extends GCompound
{
	public ToggleButton(double width, double height, Color baseColor, String instr)
	{
		rect = new GRect(0, 0, width, height);
		rect.setFilled(true);
		setBaseColor(baseColor);
		label = new GLabel("");
		setInstr(instr);
		add(rect);
		add(label);
	}
	
	public void setBaseColor(Color baseColor)
	{
		this.baseColor = baseColor;
		rect.setColor(baseColor);
	}
	
	public void setInstr(String instr)
	{
		label.setLocation(rect.getWidth() / 2, rect.getHeight() / 2);
		label.setLabel(instr.toUpperCase());
		label.move(-label.getWidth() / 2, label.getAscent() / 2);
	}
	
	public void pressButton()
	{
		rect.setColor(baseColor.brighter());
	}
	
	public void releaseButton()
	{
		rect.setColor(baseColor);
	}
	
	private GRect rect;
	private GLabel label;
	private Color baseColor;
}
