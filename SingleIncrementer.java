import java.awt.Color;

import acm.graphics.GObject;


public class SingleIncrementer extends ButtonGrid
{
	public static class Builder
	{
		private final double width;
		private final double height;
		
		private double spacing = 0;
		private Color baseColor = Color.BLACK;
		private Color buttonColor = Color.BLACK;
		
		public Builder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public Builder withSpacing(double spacing)
		{
			this.spacing = spacing;
			return this;
		}
		
		public Builder withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return this;
		}
		
		public Builder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		public SingleIncrementer build()
		{
			return new SingleIncrementer(this);
		}				
	}
	
	protected SingleIncrementer(Builder builder)
	{
		super(builder.width, builder.height, 2, 1, builder.spacing, builder.baseColor);
		incButton = new TouchButton(builder.width, builder.height, builder.buttonColor, "INC");
		addButton(incButton, 0, 0);
		decButton = new TouchButton(builder.width, builder.height, builder.buttonColor, "DEC");
		addButton(decButton, 1, 0);
	}
	
	public void onMousePressed(GObject o)
	{
		if(o == incButton)
		{
			if(inc != null) inc.increment();
		}
		else if(o == decButton)
		{
			if(inc != null) inc.decrement();
		}
	}
	
	public void setIncrementable(Incrementable inc)
	{
		this.inc = inc;
	}
	
	private Incrementable inc;
	private TouchButton incButton;
	private TouchButton decButton;
}
