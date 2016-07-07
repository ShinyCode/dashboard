package dashboard;
import java.awt.Color;

import acm.graphics.*;


public final class BarReadout extends LevelReadout
{
	private GRect base;
	private GRect back;
	private GRect bar;
	private double spacing;
	private int orientation;
	
	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;
	
	public static final class Builder extends LevelReadout.Builder<Builder>
	{	
		private double minValue = 0.0;
		private double maxValue = 100.0;
		private int orientation = VERTICAL;

		// If numDivisions is 0, operate in "continuous" mode.
		// Here, we set numDivisions to the bar height, but since we don't know
		// the spacing until we build(), we defer exact calculation until then.
		public Builder(double width, double height, int numDivisions)
		{
			super(width, height, numDivisions);
		}
		
		public Builder withOrientation(int orientation)
		{
			if(orientation == VERTICAL || orientation == HORIZONTAL) this.orientation = orientation;
			return this;
		}
		
		public BarReadout build()
		{
			return new BarReadout(width, height, spacing, baseColor, color, accentColor, numDivisions, minValue, maxValue, orientation);
		}		
	}
	
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
