package dashboard.generator;

import java.awt.Color;
import java.util.Random;

import dashboard.readout.ColorUpdatable;
import dashboard.readout.Readout;

/**
 * Implements a Generator that generates random Colors. Intended to be used
 * with {@link ColorUpdatable ColorUpdatables}.
 * 
 * @author Mark Sabini
 *
 */
public final class ColorGenerator extends Generator
{
	/**
	 * Max value of RGB values, exclusive
	 */
	private static final int MAX_COLOR_VALUE = 256;
	
	/**
	 * Random generator to generate colors
	 */
	private Random random;
	
	/**
	 * The color mode, either RGB or HSB
	 */
	private int mode;
	
	/**
	 * Used to set the ColorGenerator to generate RGB colors
	 */
	public static final int RGB = 0;
	
	/**
	 * Used to set the ColorGenerator to generate HSB colors
	 */
	public static final int HSB = 1;
	
	/**
	 * Creates a ColorGenerator with the specified timing interval, operating in HSB mode.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public ColorGenerator(int interval)
	{
		this(interval, HSB);
	}
	
	/**
	 * Creates a ColorGenerator with the specified timing interval and color mode.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 * @param mode the color mode, either RGB or HSB
	 * @throws IllegalArgumentException if an invalid mode is specified
	 */
	public ColorGenerator(int interval, int mode)
	{
		super(interval);
		if(mode != RGB && mode != HSB) throw new IllegalArgumentException();
		this.mode = mode;
		random = new Random();
	}
	
	/**
	 * Generates a random color formed by selecting random red, green, and blue components.
	 * 
	 * @return a random RGB color
	 */
	private Color generateRandomColorRGB()
	{
		int r = random.nextInt(MAX_COLOR_VALUE);
		int g = random.nextInt(MAX_COLOR_VALUE);
		int b = random.nextInt(MAX_COLOR_VALUE);
		return new Color(r, g, b);
	}
	
	/**
	 * Generates a random bright color formed by selecting a random hue. The saturation
	 * and brightness are set to 1.
	 * 
	 * @return a random bright HSB color
	 */
	private Color generateRandomColorHSB()
	{
		float h = random.nextFloat();
		float s = 1.0f;
		float b = 1.0f;
		return Color.getHSBColor(h, s, b);
	}
	
	/**
	 * Updates all the ColorUpdatables associated with the ColorGenerator. Note that each
	 * ColorUpdatable will receive a different color.
	 */
	@Override
	public void generate()
	{
		for(String key : readouts.keySet())
		{
			Readout readout = readouts.get(key);
			if(readout instanceof ColorUpdatable)
			{
				Color color = ((mode == RGB) ? generateRandomColorRGB() : generateRandomColorHSB());
				((ColorUpdatable)readout).update(color);
			}
		}
	}
}
