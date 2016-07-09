package dashboard;

import java.awt.Color;

import acm.graphics.GImage;
import acm.graphics.GLine;
import acm.graphics.GRect;

public final class ImageReadout extends Readout implements ImageUpdatable
{
	private static final double BORDER_WIDTH = 3.0;
	private GRect screen;
	private GRect offScreen;
	private boolean on;
	private GImage image;
	
	private static final int SCREEN_DIVISIONS = 4;
	
	public static final class Builder extends Readout.Builder<Builder>
	{	
		private Color offColor = Color.BLACK;
		public Builder(double width, double height)
		{
			super(width, height);
		}
		
		public Builder withOffColor(Color offColor)
		{
			this.offColor = offColor;
			return this;
		}
		
		public ImageReadout build()
		{
			return new ImageReadout(width, height, spacing, baseColor, color, accentColor, offColor);
		}
	}
	
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
		
		screen = new GRect(width - 2.0 * (spacing + BORDER_WIDTH), height - 2.0 * (spacing + BORDER_WIDTH));
		screen.setFilled(true);
		screen.setFillColor(color);
		add(screen, spacing + BORDER_WIDTH, spacing + BORDER_WIDTH);
		
		for(int i = 1; i < SCREEN_DIVISIONS; ++i)
		{
			GLine line = new GLine(0.0, 0.0, 0.0, screen.getHeight());
			line.setColor(accentColor);
			add(line, screen.getX() + i * screen.getWidth() / SCREEN_DIVISIONS, screen.getY());
		}
		
		for(int i = 1; i < SCREEN_DIVISIONS; ++i)
		{
			GLine line = new GLine(0.0, 0.0, screen.getWidth(), 0.0);
			line.setColor(accentColor);
			add(line, screen.getX(), screen.getY() + i * screen.getHeight() / SCREEN_DIVISIONS);
		}
		
		offScreen = new GRect(screen.getWidth(), screen.getHeight());
		offScreen.setFilled(true);
		offScreen.setFillColor(offColor);
		offScreen.setVisible(false);
		add(offScreen, screen.getX(), screen.getY());

		setOn(true);
	}
	
	@Override
	public void update(GImage image)
	{
		if(isFrozen()) return;
		if(!on || image == null) return;
		// Else, update the image.
		offScreen.setVisible(true);
		// Test code:
		//this.image = new GImage(image.getPixelArray());
		this.image = image;
		image.setSize(screen.getWidth(), screen.getHeight());
		add(image, screen.getX(), screen.getY());
	}

	public void clear()
	{
		if(image != null)
		{
			remove(image);
			image = null;
		}
		offScreen.setVisible(false);
	}
	
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
