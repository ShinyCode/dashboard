import java.awt.Color;

import acm.graphics.*;


public final class BarReadout extends Readout implements Incrementable, Updatable
{
	private int level;
	private int numDivisions;
	private GRect base;
	private GRect back;
	private GRect bar;
	private double spacing;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private int numDivisions;
		private int mode;
		
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public Builder withNumDivisions(int numDivisions)
		{
			if(numDivisions <= 0) throw new IllegalArgumentException();
			return this;
		}
		
		public BarReadout build()
		{
			return new BarReadout(width, height, spacing, baseColor, color, accentColor, numDivisions);
		}		
	}
	
	protected BarReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions)
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
	
	@Override
	public void update(String msg) {
		// TODO Auto-generated method stub
		
	}
}
