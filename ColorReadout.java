import java.awt.Color;

import acm.graphics.GRect;


public final class ColorReadout extends Readout
{
	private GRect base;
	private GRect colorDisplay;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public ColorReadout build()
		{
			return new ColorReadout(width, height, spacing, baseColor, color); // accentColor not used
		}		
	}
	
	protected ColorReadout(double width, double height, double spacing, Color baseColor, Color defaultColor)
	{
		base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		colorDisplay = new GRect(width - 2 * spacing, height - 2 * spacing);
		colorDisplay.setFilled(true);
		colorDisplay.setFillColor(defaultColor);
		add(colorDisplay, spacing, spacing);
	}
	
	public void update(Color newColor)
	{
		colorDisplay.setFillColor(newColor);
	}
}
