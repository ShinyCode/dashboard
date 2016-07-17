package dashboard.readout;
import java.awt.Color;

import acm.graphics.GCompound;

/**
 * Provides the base functionality for a graphical widget that can display data. In order to
 * allow subclasses to be updated in different ways, subclasses of Readout usually implement
 * at least one of the interfaces ending in "Updatable".
 * 
 * @author Mark Sabini
 *
 */
public abstract class Readout extends GCompound
{
	/**
	 * Whether the Readout is frozen, i.e. whether it should respond to updates
	 */
	private boolean frozen = false;
	
	/**
	 * Builder for the Readout class, to be extended by all subclasses of Readout.
	 * 
	 * @author Mark Sabini
	 *
	 * @param <T> dummy parameter to enable the subclasses to return their exact type
	 */
	@SuppressWarnings("rawtypes")
	protected static abstract class Builder<T extends Builder>
	{
		/**
		 * The width of the Readout
		 */
		protected final double width;
		
		/**
		 * The height of the Readout
		 */
		protected final double height;
		
		/**
		 * The spacing to use for the Readout, set to 0.0 by default
		 */
		protected double spacing = 0.0;
		
		/**
		 * The color of the Readout's base, set to black by default
		 */
		protected Color baseColor = Color.BLACK;
		
		/**
		 * The color of the Readout's display, set to black by default
		 */
		protected Color color = Color.BLACK;
		
		/**
		 * The color of the Readout's trim and accents, set to black by default
		 */
		protected Color accentColor = Color.BLACK;
		
		/**
		 * Creates a Builder specifying a Readout with the given dimensions.
		 * 
		 * @param width the width of the Readout
		 * @param height the height of the Readout
		 */
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		/**
		 * Specifies the spacing of the Readout, which is usually the width of the padding from the 
		 * Readout's display to the edge of its base.
		 * 
		 * @param spacing the spacing of the Readout
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withSpacing(double spacing)
		{
			this.spacing = spacing;
			return (T)this;
		}
		
		/**
		 * Specifies the color of the Readout's base.
		 * 
		 * @param baseColor the color of the Readout's base
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return (T)this;
		}
		
		/**
		 * Specifies the color of the Readout's display.
		 * 
		 * @param color the color of the Readout's display
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withColor(Color color)
		{
			this.color = color;
			return (T)this;
		}
		
		/**
		 * Specifies the color of the Readout's trim and accents.
		 * 
		 * @param accentColor the color of the Readout's trim and accents
		 * @return the current Builder
		 */
		@SuppressWarnings("unchecked")
		public T withAccentColor(Color accentColor)
		{
			this.accentColor = accentColor;
			return (T)this;
		}
		
		/**
		 * Creates a new Readout with the Builder's parameters. Internally, the function should call
		 * the Readout's protected constructor.
		 * 
		 * @return a new Readout with the Builder's parameters
		 */
		public abstract Readout build();			
	}
	
	/**
	 * Sets whether the Readout is frozen, i.e. whether it should respond to updates.
	 * 
	 * @param flag whether the Readout should be frozen
	 */
	public void setFrozen(boolean flag)
	{
		frozen = flag;
	}
	
	/**
	 * Returns whether the Readout is frozen.
	 * @return whether the Readout is frozen
	 */
	public boolean isFrozen()
	{
		return frozen;
	}
}
