import java.awt.Color;

import acm.graphics.*;


public final class BarReadout extends Readout implements Incrementable, NumberUpdatable
{
	private int level;
	private int numDivisions;
	private GRect base;
	private GRect back;
	private GRect bar;
	private double spacing;
	private double minValue;
	private double maxValue;
	private int orientation;
	
	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private int numDivisions = 0;
		private double minValue = 0.0;
		private double maxValue = 100.0;
		private int orientation = VERTICAL;

		// If numDivisions is 0, operate in "continuous" mode.
		// Here, we set numDivisions to the bar height, but since we don't know
		// the spacing until we build(), we defer exact calculation until then.
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height);
			if(numDivisions < 0) throw new IllegalArgumentException();
			this.numDivisions = numDivisions;
		}
		
		public Builder withRange(double minValue, double maxValue)
		{
			if(minValue >= maxValue) throw new IllegalArgumentException();
			this.minValue = minValue;
			this.maxValue = maxValue;
			return this;
		}
		
		public Builder withOrientation(int orientation)
		{
			if(orientation == VERTICAL || orientation == HORIZONTAL) this.orientation = orientation;
			return this;
		}
		
		public BarReadout build()
		{
			if(numDivisions == 0)
			{
				if(orientation == HORIZONTAL) numDivisions = (int)(width - 2 * spacing);
				else numDivisions = (int)(height - 2 * spacing); // 1 division per pixel in bar
			}
			return new BarReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, orientation);
		}		
	}
	
	protected BarReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions, double minValue, double maxValue, int orientation)
	{
		this.spacing = spacing;
		this.orientation = orientation;
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(color);
		add(back, spacing, spacing);
		
		this.numDivisions = numDivisions;
		
		if(orientation == HORIZONTAL) bar = new GRect(0, back.getHeight());
		else bar = new GRect(back.getWidth(), 0);
		bar.setFilled(true);
		bar.setFillColor(accentColor);
		if(orientation == HORIZONTAL) add(bar, spacing, spacing);
		else add(bar, spacing, height - spacing);
		
		this.minValue = minValue;
		this.maxValue = maxValue;
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
	
	public void update(double value)
	{
		if(value > maxValue)
		{
			setLevel(numDivisions);
			return;
		}
		if(value < minValue)
		{
			setLevel(0);
			return;
		}
		double range = maxValue - minValue;
		// TODO: Check if want to make long
		setLevel((int)Math.round((value - minValue) * numDivisions / range));
	}
	
	public void setLevel(int level)
	{
		if(level < 0 || level > numDivisions) return;
		if(bar == null) return;
		this.level = level;
		if(orientation == HORIZONTAL)
		{
			double newWidth = ((double) level) * back.getWidth() / numDivisions;
			bar.setSize(newWidth, bar.getHeight());
		}
		else // VERTICAL orientation
		{
			double newHeight = ((double) level) * back.getHeight() / numDivisions;
			bar.setLocation(spacing, base.getHeight() - spacing - newHeight);
			bar.setSize(bar.getWidth(), newHeight);
		}
		return;
	}
}
