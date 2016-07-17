package dashboard.readout;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GLine;
import acm.graphics.GRect;

/**
 * Implements a Readout representing a video screen. Currently, the ImageReadout will
 * resize a GImage to match its own dimensions, so the aspect ratio may not be preserved.
 * 
 * @author Mark Sabini
 *
 */
public final class ImageReadout extends Readout implements ImageUpdatable
{
	/**
	 * The screen of the ImageReadout when it is on
	 */
	private GRect onScreen;
	
	/**
	 * The screen of the ImageReadout when it is off
	 */
	private GRect offScreen;
	
	/**
	 * Whether the ImageReadout is on
	 */
	private boolean on;
	
	/**
	 * The image that the ImageReadout is displaying
	 */
	private GImage image;
	
	/**
	 * The width of the border around the ImageReadout's screen (different from spacing)
	 */
	private static final double BORDER_WIDTH = 3.0;
	
	/**
	 * The number of segments the screen is divided into horizontally and vertically by a cosmetic grid
	 */
	private static final int SCREEN_DIVISIONS = 4;
	
	/**
	 * Builder for the ImageReadout class.
	 * 
	 * @author Mark Sabini
	 *
	 */
	public static final class Builder extends Readout.Builder<Builder>
	{	
		/**
		 * The color of the ImageReadout's screen when it is off, set to black by default
		 */
		private Color offColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying an ImageReadout with the given dimensions.
		 * 
		 * @param width the width of the ImageReadout
		 * @param height the height of the ImageReadout
		 */
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		/**
		 * Specifies the color of the ImageReadout's screen when turned off.
		 * 
		 * @param offColor the color of the ImageReadout's screen when it is off
		 * @return the current Builder
		 */
		public Builder withOffColor(Color offColor)
		{
			this.offColor = offColor;
			return this;
		}
		
		/**
		 * Creates a new ImageReadout with the Builder's parameters.
		 * 
		 * @return a new ImageReadout with the Builder's parameters
		 */
		@Override
		public ImageReadout build()
		{
			return new ImageReadout(width, height, spacing, baseColor, color, accentColor, offColor);
		}
	}
	
	/**
	 * Creates an ImageReadout with the specified dimensions, spacing, base color, color, accent color, and off color.
	 * @param width the width of the ImageReadout
	 * @param height the height of the ImageReadout
	 * @param spacing the spacing of the ImageReadout
	 * @param baseColor the base color of the ImageReadout
	 * @param color the color of the ImageReadout's screen when on
	 * @param accentColor the color of the border of the ImageReadout's screen
	 * @param offColor the color of the ImageReadout's screen when off
	 */
	protected ImageReadout(double width, double height, double spacing, Color baseColor, Color color, Color accentColor, Color offColor)
	{
		GRect base = new GRect(width, height);
		base.setFilled(true);
		base.setFillColor(baseColor);
		add(base, 0, 0);
		
		GRect border = new GRect(width - 2.0 * spacing, height - 2.0 * spacing);
		border.setFilled(true);
		border.setFillColor(accentColor);
		add(border, spacing, spacing);
		
		onScreen = new GRect(width - 2.0 * (spacing + BORDER_WIDTH), height - 2.0 * (spacing + BORDER_WIDTH));
		onScreen.setFilled(true);
		onScreen.setFillColor(color);
		add(onScreen, spacing + BORDER_WIDTH, spacing + BORDER_WIDTH);
		
		for(int i = 1; i < SCREEN_DIVISIONS; ++i)
		{
			GLine line = new GLine(0.0, 0.0, 0.0, onScreen.getHeight());
			line.setColor(accentColor);
			add(line, onScreen.getX() + i * onScreen.getWidth() / SCREEN_DIVISIONS, onScreen.getY());
		}
		
		for(int i = 1; i < SCREEN_DIVISIONS; ++i)
		{
			GLine line = new GLine(0.0, 0.0, onScreen.getWidth(), 0.0);
			line.setColor(accentColor);
			add(line, onScreen.getX(), onScreen.getY() + i * onScreen.getHeight() / SCREEN_DIVISIONS);
		}
		
		offScreen = new GRect(onScreen.getWidth(), onScreen.getHeight());
		offScreen.setFilled(true);
		offScreen.setFillColor(offColor);
		offScreen.setVisible(false);
		add(offScreen, onScreen.getX(), onScreen.getY());

		setOn(true);
	}
	
	/**
	 * Sets the ImageReadout to display the specified image. Currently, the ImageReadout will
	 * resize a GImage to match its own dimensions, so the aspect ratio may not be preserved.
	 * 
	 * @param image the image with which to update the ImageReadout
	 */
	@Override
	public void update(GImage image)
	{
		if(isFrozen()) return;
		if(!on || image == null) return;
		// Else, update the image.
		offScreen.setVisible(true);
		this.image = image;
		image.setSize(onScreen.getWidth(), onScreen.getHeight());
		add(image, onScreen.getX(), onScreen.getY());
	}

	/**
	 * Clears the image currently being displayed by the ImageReadout, making it display
	 * a blank screen.
	 */
	public void clear()
	{
		if(image != null)
		{
			remove(image);
			image = null;
		}
		offScreen.setVisible(false);
	}
	
	/**
	 * Sets whether the ImageReadout is on.
	 * 
	 * @param on whether the ImageReadout is on
	 */
	public void setOn(boolean on)
	{
		if(this.on == on) return;
		this.on = on;
		offScreen.setVisible(!on);
		if(!on && image != null)
		{
			remove(image);
			image = null;
		}
	}
}
