import java.awt.Color;

import acm.graphics.GObject;


public class SingleIncrementer extends ButtonGrid
{
	
	public static class SingleIncrementerBuilder
	{
		private final double width;
		private final double height;
		
		private double spacing = 0;
		private Color baseColor = Color.BLACK;
		private Color buttonColor = Color.BLACK;
		
		public SingleIncrementerBuilder(double width, double height)
		{
			this.width = width;
			this.height = height;
		}
		
		public SingleIncrementerBuilder withSpacing(double spacing)
		{
			this.spacing = spacing;
			return this;
		}
		
		public SingleIncrementerBuilder withBaseColor(Color baseColor)
		{
			this.baseColor = baseColor;
			return this;
		}
		
		public SingleIncrementerBuilder withButtonColor(Color buttonColor)
		{
			this.buttonColor = buttonColor;
			return this;
		}
		
		public SingleIncrementer build()
		{
			return new SingleIncrementer(width, height, spacing, baseColor, buttonColor);
		}				
	}
	
	protected SingleIncrementer(double width, double height, double spacing, Color baseColor, Color buttonColor)
	{
		super(width, height, 2, 1, spacing, baseColor);
		incButton = new TouchButton(width, height, buttonColor, "INC");
		addButton(incButton, 0, 0);
		decButton = new TouchButton(width, height, buttonColor, "DEC");
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
