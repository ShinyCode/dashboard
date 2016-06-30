import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.graphics.GRectangle;


public final class DialReadout extends Readout
{
	private int level;
	private int numDivisions;
	private GRect base;
	private GArc dial;

	private double spacing;
	private double minValue;
	private double maxValue;
	private double startAngle;
	private double sweepAngle;
	private double dialRadius;
	
	private GPoint dialCenter;
	private GLine needle;
	
	private static final double needleLength = 0.75; // As fraction of dial radius
	
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
		this.startAngle = startAngle;
		this.sweepAngle = sweepAngle;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		// TODO: Draw the rest of the components
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
		dialRadius = dialDiameter * scaleFactor / 2.0;
		// Calculate where the dial should go.
		// In the final step, we have to subtract a bias since dial.getX() gets the x-coordinate of the 
		// frame rectangle, not the bounding box.
		GRectangle bounds = dial.getBounds();
		double xDisp = (width - bounds.getWidth()) / 2; // Displacement from left edge of base
		double yDisp = (height - bounds.getHeight()) / 2; // Displacement from top edge of base
		add(dial, xDisp - (bounds.getX() - dial.getX()), yDisp - (bounds.getY() - dial.getY()));
		
		// Draw the dot to mark the start of the dial
		GRectangle frame = dial.getFrameRectangle();
		dialCenter = new GPoint(frame.getX() + frame.getWidth() / 2, frame.getY() + frame.getHeight() / 2);
		// GOval dot = new GOval(spacing / 2, spacing / 2);
		// dot.setFilled(true);
		// dot.setFillColor(color);
		// double dotX = dialCenter.getX() - dot.getWidth() / 2 + (frame.getWidth() + spacing) * Math.cos(Math.toRadians(startAngle)) / 2;
		// double dotY = dialCenter.getY() - dot.getHeight() / 2 - (frame.getHeight() + spacing) * Math.sin(Math.toRadians(startAngle)) / 2;
		// add(dot, dotX, dotY);
		// setLevel(0);
		
		// Draw the needle on the dial face
		needle = new GLine(dialCenter.getX(),
						   dialCenter.getY(),
						   dialCenter.getX() + dialRadius * needleLength * Math.cos(Math.toRadians(startAngle)),
						   dialCenter.getY() - dialRadius * needleLength * Math.sin(Math.toRadians(startAngle)));
		add(needle);
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
		// TODO: Redraw the needle
		double newAngle = startAngle + (level * sweepAngle) / numDivisions;
		double newX = dialCenter.getX() + dialRadius * needleLength * Math.cos(Math.toRadians(newAngle));
		double newY = dialCenter.getY() - dialRadius * needleLength * Math.sin(Math.toRadians(newAngle));
				           
		return;
	}
}
