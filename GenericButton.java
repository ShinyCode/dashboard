
import acm.graphics.*;

import java.awt.Color;
import java.awt.event.*;

public abstract class GenericButton extends MouseWidget
{
	public GenericButton(double width, double height, Color baseColor, String instr)
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
		rect.setColor(baseColor.darker());
	}
	
	public void setInstr(String instr)
	{
		label.setLocation(rect.getWidth() / 2, rect.getHeight() / 2);
		label.setLabel(instr.toUpperCase());
		label.move(-label.getWidth() / 2, label.getAscent() / 2);
	}
	
	public Color getBaseColor()
	{
		return baseColor;
	}
	
	public String getInstr()
	{
		return label.getLabel();
	}
	
	protected GRect rect;
	protected GLabel label;
	protected Color baseColor;
}
