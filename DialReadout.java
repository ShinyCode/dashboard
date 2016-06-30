import java.awt.Color;

import acm.graphics.GRect;


public final class DialReadout extends Readout
{
	private int level;
	private int numDivisions;
	private GRect base;

	private double spacing;
	private double minValue;
	private double maxValue;
	private double startAngle;
	private double sweepAngle;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private int numDivisions = 0;
		private double minValue = 0.0;
		private double maxValue = 100.0;
		private double startAngle = 0;
		private double sweepAngle = 360.0; // Angles measured in degrees

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
		
		public Builder withStartAngle(double startAngle)
		{
			this.startAngle = startAngle;
			return this;
		}
		
		public Builder withSweepAngle(double sweepAngle)
		{
			this.sweepAngle = sweepAngle;
			return this;
		}
		
		public DialReadout build()
		{
			if(numDivisions == 0)
			{
				// TODO: Determine what happens in continuous mode
			}
			return new DialReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, startAngle, sweepAngle);
		}		
	}
	
	protected DialReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions, double minValue, double maxValue, double startAngle, double sweepAngle)
	{
		this.spacing = spacing;
		this.numDivisions = numDivisions;
		this.minValue = minValue;
		this.maxValue = maxValue;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		// TODO: Draw the rest of the components
		
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
		this.level = level;
		// TODO: Redraw the disc
		return;
	}
}
