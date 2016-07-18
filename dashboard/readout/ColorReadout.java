package dashboard.readout;
import java.awt.Color;

import acm.graphics.GRect;

/**
 * Implements a Readout that represents an LED status light.
 * 
 * @author Mark Sabini
 *
 */
public final class ColorReadout extends Readout implements ColorUpdatable
{
	/**
	 * The ColorReadout's colored base
	 */
	private GRect base;
	
	/**
	 * The ColorReadout's rectangular color display
	 */
	private GRect colorDisplay;
	
	/**
	 * Builder for the ColorReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends Readout.Builder<Builder>
	{	
		/**
		 * Creates a Builder specifying a ColorReadout with the given dimensions.
		 * 
		 * @param width the width of the ColorReadout
		 * @param height the height of the ColorReadout
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Creates a new ColorReadout with the Builder's parameters.
		 * 
		 * @return a new ColorReadout with the Builder's parameters
		 */
		@Override
		public ColorReadout build()
		{
			return new ColorReadout(width, height, spacing, baseColor, color); // accentColor not used
		}		
	}
	
	/**
	 * Creates a ColorReadout with the specified dimensions, spacing, base color, and default color.
	 * 
	 * @param width the width of the ColorReadout
	 * @param height the height of the ColorReadout
	 * @param spacing the spacing of the ColorReadout
	 * @param baseColor the base color of the ColorReadout
	 * @param defaultColor the initial color of the ColorReadout's color display, before it is updated
	 */
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
	
	/**
	 * Sets the ColorReadout's color display to the specified Color. If the ColorReadout is frozen,
	 * no action is taken.
	 * 
	 * @param color the color for the ColorReadout's color display
	 */
	@Override
	public void update(Color color)
	{
		if(isFrozen()) return;
		colorDisplay.setFillColor(color);
	}
}
