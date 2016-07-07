package dashboard;
import java.awt.Color;

import acm.graphics.GCompound;


public abstract class Readout extends GCompound
{
	@SuppressWarnings("rawtypes")
	protected static abstract class Builder<T extends Builder>
	{
		protected final double width;
		protected final double height;
		
		protected double spacing = 0;
		protected Color baseColor = Color.BLACK;
		protected Color color = Color.BLACK;
		protected Color accentColor = Color.BLACK;
		
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public T withSpacing(double spacing)
		{
			this.spacing = spacing;
			return (T)this;
		}
		
		public T withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return (T)this;
		}
		
		public T withColor(Color color)
		{
			this.color = color;
			return (T)this;
		}
		
		public T withAccentColor(Color accentColor)
		{
			this.accentColor = accentColor;
			return (T)this;
		}
		
		public abstract Readout build();			
	}
}
