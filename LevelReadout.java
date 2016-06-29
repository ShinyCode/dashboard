import java.awt.Color;

import acm.graphics.*;


public final class LevelReadout extends Readout implements Incrementable, Updatable
{
	public LevelReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions)
	{
		this.spacing = spacing;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(color);
		add(back, spacing, spacing);
		
		//Need to check for bad input to numDivisions
		this.numDivisions = numDivisions;
		
		bar = new GRect(back.getWidth(), 0);
		bar.setFilled(true);
		bar.setFillColor(accentColor);
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
		if(bar == null) return false;
		this.level = level;
		double newHeight = ((double) level) * back.getHeight() / numDivisions;
		bar.setLocation(spacing, base.getHeight() - spacing - newHeight);
		bar.setSize(bar.getWidth(), newHeight);
		return true;
	}
	
	public void update(int data)
	{
		setLevel(data);
	}
	
	int level;
	int numDivisions;
	GRect base;
	GRect back;
	GRect bar;
	double spacing;
	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		
	}
}
