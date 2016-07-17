package dashboard.readout;
import java.awt.Color;

import acm.graphics.GArc;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

/**
 * Implements a Readout representing a dial whose angle scales linearly with input values.
 * 
 * @author Mark Sabini
 *
 */
public final class DialReadout extends LevelReadout
{
	/**
	 * The colored base of the DialReadout
	 */
	private GRect base;
	
	/**
	 * The dial face of the DialReadout
	 */
	private GArc dial;
	
	/**
	 * The indicator which fills the dial according to the level of the DialReadout
	 */
	private GArc indicator;

	/**
	 * The angle across which the dial face sweeps
	 */
	private double sweepAngle;
	
	/**
	 * Builder for the DialReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends LevelReadout.Builder<Builder>
	{	
		/**
		 * The angle from which the dial face starts, measured in degrees, counterclockwise
		 * from the positive x-axis. The startAngle is set to 0.0 by default.
		 */
		private double startAngle = 0.0;
		
		/**
		 * The angle across which the dial face sweeps, measured in degrees, counterclockwise
		 * from the positive x-axis. The sweepAngle is set to 360.0 by default.
		 */
		private double sweepAngle = 360.0;

		/**
		 * Creates a Builder specifying a DialReadout with the given dimensions and number of divisions.
		 * If the number of divisions is set to 0, the DialReadout will operate in continuous mode, setting
		 * numDivisions to the number of pixels in the length of the outer edge of the dial face.
		 * 
		 * @param width the width of the Dialeadout
		 * @param height the height of the DialReadout
		 * @param numDivisions the total number of levels into which the DialReadout should be divided
		 */
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height, numDivisions);
		}
		
		/**
		 * Specifies the start angle of the DialReadout's dial face. The angle measured in degrees,
		 * counterclockwise from the positive x-axis
		 * 
		 * @param startAngle the start angle of the DialReadout's dial face
		 * @return the current Builder
		 */
		public Builder withStartAngle(double startAngle)
		{
			this.startAngle = startAngle;
			return this;
		}
		
		/**
		 * Specifies the sweep angle of the DialReadout's dial face. The angle measured in degrees,
		 * counterclockwise from the positive x-axis
		 * 
		 * @param sweepAngle the sweep angle of the DialReadout's dial face
		 * @return the current Builder
		 */
		public Builder withSweepAngle(double sweepAngle)
		{
			this.sweepAngle = sweepAngle;
			return this;
		}
		
		/**
		 * Creates a new DialReadout with the Builder's parameters.
		 * 
		 * @return a new DialReadout with the Builder's parameters
		 */
		@Override
		public DialReadout build()
		{
			/*
			 * In continuous mode, the number of divisions is set to the circumference
			 * of the arc, so roughly one pixel per increment. We don't know how big the
			 * arc will be exactly though, so we need to defer until the constructor
			 */
			return new DialReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, startAngle, sweepAngle);
		}		
	}
	
	/**
	 * Creates a DialReadout with the specified dimensions, spacing, base color, color, accent color, number of divisions,
	 * minimum and maximum value, start angle, and sweep angle.
	 * 
	 * @param width the width of the DialReadout
	 * @param height the height of the DialReadout
	 * @param spacing the spacing of the DialReadout
	 * @param baseColor the base color of the DialReadout
	 * @param color the color of the DialReadout's dial face
	 * @param accentColor the color of the DialReadout's indicator
	 * @param numDivisions the number of levels into which the DialReadout is divided
	 * @param minValue the real value to which the lowest level corresponds
	 * @param maxValue the real value to which the highest level corresponds
	 * @param startAngle the start angle of the DialReadout
	 * @param sweepAngle the sweep angle of the DialReadout
	 */
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
	
	@Override
	protected void redrawAtLevel(int level)
	{
		indicator.setSweepAngle((level * sweepAngle) / numDivisions);
	}
}
