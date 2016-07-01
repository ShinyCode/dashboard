import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

/*
public final class DialReadout extends Readout implements Incrementable, NumberUpdatable
{
	private int level;
	private int numDivisions;
	private GRect base;
	private GArc dial;
	private GArc indicator;

	private double minValue;
	private double maxValue;
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
			// In continuous mode, the number of divisions is set to the circumference
			// of the arc, so roughly one pixel per increment.
			// We don't know how big the arc will be exactly though, so we need to defer until
			// the constructor
			return new DialReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, startAngle, sweepAngle);
		}		
	}
	
	protected DialReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions, double minValue, double maxValue, double startAngle, double sweepAngle)
	{
		// We defer setting numDivisions until after resizing the arc
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.sweepAngle = sweepAngle;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		// For the dial, first create the dial but don't display it.
		double dialDiameter = Math.min(width - 2 * spacing, height - 2 * spacing);
		dial = new GArc(dialDiameter, dialDiameter, startAngle, sweepAngle);
		dial.setFilled(true);
		dial.setFillColor(color);
		
		// Get the bounding box, and use that to scale/reposition the dial to fit snugly in
		// the base
		double yScaleFactor = (height - 2 * spacing) / dial.getHeight();
		double xScaleFactor = (width - 2 * spacing) / dial.getWidth();
		double scaleFactor = Math.min(xScaleFactor, yScaleFactor);
		dial.setFrameRectangle(dial.getX(), dial.getY(), dialDiameter * scaleFactor, dialDiameter * scaleFactor);
		
		// Calculate where the dial should go.
		// In the final step, we have to subtract a bias since dial.getX() gets the x-coordinate of the 
		// frame rectangle, not the bounding box.
		GRectangle bounds = dial.getBounds();
		double xDisp = (width - bounds.getWidth()) / 2; // Displacement from left edge of base
		double yDisp = (height - bounds.getHeight()) / 2; // Displacement from top edge of base
		add(dial, xDisp - (bounds.getX() - dial.getX()), yDisp - (bounds.getY() - dial.getY()));
		
		// Draw the indicator arc
		GRectangle frame = dial.getFrameRectangle();
		indicator = new GArc(frame.getWidth(), frame.getHeight(), startAngle, 0.0);
		indicator.setFilled(true);
		indicator.setFillColor(accentColor);
		add(indicator, frame.getX(), frame.getY());
		
		// Draw the middle arc
		GArc centerCover = new GArc(frame.getWidth() / 2, frame.getHeight() / 2, startAngle, sweepAngle);
		centerCover.setFilled(true);
		centerCover.setFillColor(color);
		add(centerCover, frame.getX() + frame.getWidth() / 4, frame.getY() + frame.getHeight() / 4);
		
		// Set up divisions
		if(numDivisions == 0)
		{
			this.numDivisions = (int)(Math.PI * frame.getWidth() * sweepAngle / 360.0);
		}
		else this.numDivisions = numDivisions;
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
		indicator.setSweepAngle((level * sweepAngle) / numDivisions);
	}
}
*/

public final class DialReadout extends NumberReadout
{
	private GRect base;
	private GArc dial;
	private GArc indicator;

	private double sweepAngle;
	
	public static final class Builder extends NumberReadout.Builder<Builder>
	{	
		private double startAngle = 0;
		private double sweepAngle = 360.0; // Angles measured in degrees

		// If numDivisions is 0, operate in "continuous" mode.
		// Here, we set numDivisions to the bar height, but since we don't know
		// the spacing until we build(), we defer exact calculation until then.
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height, numDivisions);
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
			// In continuous mode, the number of divisions is set to the circumference
			// of the arc, so roughly one pixel per increment.
			// We don't know how big the arc will be exactly though, so we need to defer until
			// the constructor
			return new DialReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, startAngle, sweepAngle);
		}		
	}
	
	protected DialReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions, double minValue, double maxValue, double startAngle, double sweepAngle)
	{
		// We defer setting numDivisions until after resizing the arc
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.sweepAngle = sweepAngle;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		// For the dial, first create the dial but don't display it.
		double dialDiameter = Math.min(width - 2 * spacing, height - 2 * spacing);
		dial = new GArc(dialDiameter, dialDiameter, startAngle, sweepAngle);
		dial.setFilled(true);
		dial.setFillColor(color);
		
		// Get the bounding box, and use that to scale/reposition the dial to fit snugly in
		// the base
		double yScaleFactor = (height - 2 * spacing) / dial.getHeight();
		double xScaleFactor = (width - 2 * spacing) / dial.getWidth();
		double scaleFactor = Math.min(xScaleFactor, yScaleFactor);
		dial.setFrameRectangle(dial.getX(), dial.getY(), dialDiameter * scaleFactor, dialDiameter * scaleFactor);
		
		// Calculate where the dial should go.
		// In the final step, we have to subtract a bias since dial.getX() gets the x-coordinate of the 
		// frame rectangle, not the bounding box.
		GRectangle bounds = dial.getBounds();
		double xDisp = (width - bounds.getWidth()) / 2; // Displacement from left edge of base
		double yDisp = (height - bounds.getHeight()) / 2; // Displacement from top edge of base
		add(dial, xDisp - (bounds.getX() - dial.getX()), yDisp - (bounds.getY() - dial.getY()));
		
		// Draw the indicator arc
		GRectangle frame = dial.getFrameRectangle();
		indicator = new GArc(frame.getWidth(), frame.getHeight(), startAngle, 0.0);
		indicator.setFilled(true);
		indicator.setFillColor(accentColor);
		add(indicator, frame.getX(), frame.getY());
		
		// Draw the middle arc
		GArc centerCover = new GArc(frame.getWidth() / 2, frame.getHeight() / 2, startAngle, sweepAngle);
		centerCover.setFilled(true);
		centerCover.setFillColor(color);
		add(centerCover, frame.getX() + frame.getWidth() / 4, frame.getY() + frame.getHeight() / 4);
		
		// Set up divisions
		if(numDivisions == 0)
		{
			this.numDivisions = (int)(Math.PI * frame.getWidth() * sweepAngle / 360.0);
		}
		else this.numDivisions = numDivisions;
	}
	
	public void setLevel(int level)
	{
		if(level < 0 || level > numDivisions) return;
		this.level = level;
		indicator.setSweepAngle((level * sweepAngle) / numDivisions);
	}
}
