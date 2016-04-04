import java.awt.Color;
import acm.graphics.*;


public class LevelReadout extends GCompound implements Incrementable
{
	public LevelReadout(double width, double height, double spacing, int numDivisions, Color baseColor, Color backColor, Color barColor)
	{
		this.spacing = spacing;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(backColor);
		add(back, spacing, spacing);
		
		//Need to check for bad input to numDivisions
		this.numDivisions = numDivisions;
		
		bar = new GRect(back.getWidth(), 0);
		bar.setFilled(true);
		bar.setFillColor(barColor);
		add(bar, spacing, height - spacing);
		
		setLevel(0);
	}
	
	public void increment()
	{
		setLevel(level + 1);
	}
	
	public void decrement()
	{
		setLevel(level - 1);
	}
	
	public boolean setLevel(int level)
	{
		if(level < 0 || level > numDivisions) return false;
		if(bar == null) return 0;
		this.level = level;
		double newHeight = ((double) level) * back.getHeight() / numDivisions;
		bar.setLocation(spacing, base.getHeight() - spacing - newHeight);
		bar.setSize(bar.getWidth(), newHeight);
		return true;
	}
	
	int level;
	int numDivisions;
	GRect base;
	GRect back;
	GRect bar;
	double spacing;
}
