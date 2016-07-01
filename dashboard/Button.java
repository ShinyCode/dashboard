
import acm.graphics.*;

import java.awt.Color;

public abstract class Button extends MouseWidget
{
	public Button(double width, double height, Color baseColor, String instr)
	{
		rect = new GRect(0, 0, width, height);
		rect.setFilled(true);
		setBaseColor(baseColor);
		label = new GLabel("");
		label.setFont("Consolas-*-16");
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
	
	public Color getBaseColor()
	{
		return baseColor;
	}
	
	public String getInstr()
	{
		return label.getLabel();
	}
	
	public void resize(double width, double height)
	{
		rect.setSize(width, height);
		setInstr(label.getLabel());
	}
	
	protected GRect rect;
	protected GLabel label;
	protected Color baseColor;
}
