package dashboard;
import java.awt.Color;

import acm.graphics.*;

/**
 * Implements a Readout representing a bar whose length scales linearly with input values.
 * 
 * @author Mark Sabini
 *
 */
public final class BarReadout extends LevelReadout
{
	/**
	 * The colored base of the BarReadout
	 */
	private GRect base;
	
	/**
	 * The background behind the BarReadout's bar
	 */
	private GRect back;
	
	/**
	 * The bar of the BarReadout
	 */
	private GRect bar;
	
	/**
	 * The spacing of the BarReadout
	 */
	private double spacing;
	
	/**
	 * The orientation of the BarReadout
	 */
	private int orientation;
	
	/**
	 * Used to specify a vertical orientation for the BarReadout, which grows from bottom to top
	 */
	public static final int VERTICAL = 0;
	
	/**
	 * Use to specify a horizontal orientation for the BarReadout, which grows from left to right
	 */
	public static final int HORIZONTAL = 1;
	
	/**
	 * Builder for the BarReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends LevelReadout.Builder<Builder>
	{	
		/**
		 * The orientation of the BarReadout, set to vertical by default
		 */
		private int orientation = VERTICAL;

		/**
		 * Creates a Builder specifying a BarReadout with the given dimensions and number of divisions.
		 * If the number of divisions is set to 0, the BarReadout will operate in continuous mode, setting
		 * numDivisions to the number of pixels in the maximum length of the bar.
		 * 
		 * @param width the width of the BarReadout
		 * @param height the height of the BarReadout
		 * @param numDivisions the total number of levels into which the BarReadout should be divided
		 */
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height, numDivisions);
		}
		
		/**
		 * Specifies the orientation of the BarReadout, which must be either {@link BarReadout#VERTICAL BarReadout.VERTICAL}
		 * or {@link BarReadout#HORIZONTAL BarReadout.HORIZONTAL}.
		 * 
		 * @param orientation the orientation of the BarReadout
		 * @return the current Builder
		 */
		public Builder withOrientation(int orientation)
		{
			if(orientation == VERTICAL || orientation == HORIZONTAL) this.orientation = orientation;
			return this;
		}
		
		/**
		 * Creates a new BarReadout with the Builder's parameters.
		 * 
		 * @return a new BarReadout with the Builder's parameters
		 */
		public BarReadout build()
		{
			return new BarReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, orientation);
		}		
	}
	
	/**
	 * Creates a BarReadout with the specified dimensions, spacing, base color, color, accent color, number of divisions,
	 * minimum and maximum value, and orientation.
	 * 
	 * @param width the width of the BarReadout
	 * @param height the height of the BarReadout
	 * @param spacing the spacing of the BarReadout
	 * @param baseColor the base color of the BarReadout
	 * @param color the color of the BarReadout's bar's background
	 * @param accentColor the color of the BarReadout's bar
	 * @param numDivisions the number of levels into which the BarReadout is divided
	 * @param minValue the real value to which the lowest level corresponds
	 * @param maxValue the real value to which the highest level corresponds
	 * @param orientation the orientation of the BarReadout
	 */
	protected BarReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, int numDivisions, double minValue, double maxValue, int orientation)
	{
		this.spacing = spacing;
		this.orientation = orientation;
		if(numDivisions == 0)
		{
			if(orientation == HORIZONTAL) this.numDivisions = (int)(width - 2 * spacing);
			else this.numDivisions = (int)(height - 2 * spacing); // 1 division per pixel in bar
		}
		else this.numDivisions = numDivisions;
		this.minValue = minValue;
		this.maxValue = maxValue;
		
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		back = new GRect(width - 2 * spacing, height - 2 * spacing);
		back.setFilled(true);
		back.setFillColor(color);
		add(back, spacing, spacing);
		
		if(orientation == HORIZONTAL) bar = new GRect(0, back.getHeight());
		else bar = new GRect(back.getWidth(), 0);
		bar.setFilled(true);
		bar.setFillColor(accentColor);
		if(orientation == HORIZONTAL) add(bar, spacing, spacing);
		else add(bar, spacing, height - spacing);
		
		setLevel(0);
	}
	
	@Override
	public void redrawAtLevel(int level)
	{
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
	}
}
