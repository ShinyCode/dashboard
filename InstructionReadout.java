
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.*;

public class InstructionReadout extends GCompound implements Incrementable
{
	public InstructionReadout(double width, double height, double spacing, Color baseColor, Color backColor, Color barColor)
	{
		messages = new ArrayList<GLabel>();
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(backColor);
		add(back, spacing, spacing);
		
		
	}
	
	public void increment()
	{
		
	}
	
	public void decrement()
	{
		
	}
	
	public void setFrozen(boolean flag)
	{
		
	}
	
	public void clear()
	{
		
	}
	
	public void appendMessage(String msg)
	{
		
	}
	
	private void refresh()
	{
		
	}
	
	private List<GLabel> messages;
	private GRect base, back, bar;
}
